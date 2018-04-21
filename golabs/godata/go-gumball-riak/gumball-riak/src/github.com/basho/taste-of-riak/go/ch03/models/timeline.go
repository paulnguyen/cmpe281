package models

type Timeline struct {
	modelImpl
	MsgKeys []string
}

type TimelineType byte

const (
	TimelineType_INBOX TimelineType = iota
	TimelineType_SENT
)

func NewTimeline(id string) *Timeline {
	t := &Timeline{}
	t.id = id
	return t
}

func (t *Timeline) AddMsg(msgKey string) {
	t.MsgKeys = append(t.MsgKeys, msgKey)
}

func (t *Timeline) GetId() string {
	return t.id
}
