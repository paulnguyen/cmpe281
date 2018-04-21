package managers

import (
	"fmt"
	"time"

	models "github.com/basho/taste-of-riak/go/ch03/models"
	repos "github.com/basho/taste-of-riak/go/ch03/repositories"
	util "github.com/basho/taste-of-riak/go/util"
)

type TimelineManager struct {
	trepo *repos.TimelineRepository
	mrepo *repos.MsgRepository
}

func NewTimelineManager(t *repos.TimelineRepository, m *repos.MsgRepository) *TimelineManager {
	return &TimelineManager{
		trepo: t,
		mrepo: m,
	}
}

func (t *TimelineManager) PostMsg(msg *models.Msg) error {
	savedMsg, err := t.mrepo.Save(msg)
	if err != nil {
		return err
	}

	msg = savedMsg.(*models.Msg)

	err = addToTimeline(t, msg, models.TimelineType_INBOX, savedMsg.GetId())
	if err != nil {
		return err
	}

	err = addToTimeline(t, msg, models.TimelineType_SENT, savedMsg.GetId())
	if err != nil {
		return err
	}

	return nil
}

func (t *TimelineManager) GetTimeline(ownerUsername string, ttype models.TimelineType, date time.Time) (*models.Timeline, error) {
	timelineKey := generateKey(ownerUsername, ttype, date)
	model, err := t.trepo.Get(timelineKey, false)
	return model.(*models.Timeline), err
}

func addToTimeline(t *TimelineManager, msg *models.Msg, ttype models.TimelineType, msgKey string) error {
	timelineKey := generateKeyFromMsg(msg, ttype)

	m, err := t.trepo.Get(timelineKey, true)
	if err != nil {
		return err
	}

	var timeline *models.Timeline
	if m == nil {
		timeline = createNewTimeline(timelineKey, msgKey)
	} else {
		tl := m.(*models.Timeline)
		timeline = addToExistingTimeline(tl, msgKey)
	}

	_, err = t.trepo.Save(timeline)
	return err
}

func createNewTimeline(timelineKey, msgKey string) *models.Timeline {
	newTimeline := models.NewTimeline(timelineKey)
	newTimeline.AddMsg(msgKey)
	return newTimeline
}

func addToExistingTimeline(timeline *models.Timeline, msgKey string) *models.Timeline {
	timeline.AddMsg(msgKey)
	return timeline
}

func generateKeyFromMsg(msg *models.Msg, ttype models.TimelineType) string {
	owner := getOwner(msg, ttype)
	return generateKey(owner, ttype, msg.Created)
}

func getOwner(msg *models.Msg, ttype models.TimelineType) string {
	switch ttype {
	case models.TimelineType_INBOX:
		return msg.Recipient
	case models.TimelineType_SENT:
		return msg.Sender
	}
	return msg.Recipient
}

func generateKey(ownerUserName string, ttype models.TimelineType, date time.Time) string {
	isoDate := util.Iso8601(date)
	return fmt.Sprintf("%s_%d_%s", ownerUserName, ttype, isoDate[0:10])
}
