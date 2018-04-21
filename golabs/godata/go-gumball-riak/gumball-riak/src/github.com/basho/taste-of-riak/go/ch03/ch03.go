package main

import (
	"time"

	mgrs "github.com/basho/taste-of-riak/go/ch03/managers"
	models "github.com/basho/taste-of-riak/go/ch03/models"
	repos "github.com/basho/taste-of-riak/go/ch03/repositories"

	riak "github.com/basho/riak-go-client"
	util "github.com/basho/taste-of-riak/go/util"
)

func main() {
	var err error

	// un-comment-out to enable debug logging
	// riak.EnableDebugLogging = true

	util.Log.Println("Starting Client")

	o := &riak.NewClientOptions{
		RemoteAddresses: util.GetRiakAddresses(),
	}

	var client *riak.Client
	client, err = riak.NewClient(o)
	if err != nil {
		util.ErrExit(err)
	}

	defer func() {
		if err := client.Stop(); err != nil {
			util.ErrExit(err)
		}
	}()

	userRepo := repos.NewUserRepository(client)
	msgRepo := repos.NewMsgRepository(client)
	timelineRepo := repos.NewTimelineRepository(client)
	timelineMgr := mgrs.NewTimelineManager(timelineRepo, msgRepo)

	util.Log.Println("Creating and saving users")

	marleen := models.NewUser("marleenmgr", "Marleen Manager", "marleen.manager@basho.com")
	joe := models.NewUser("joeuser", "Joe User", "joe.user@basho.com")

	var m models.Model
	m, err = userRepo.Save(marleen)
	if err != nil {
		util.ErrExit(err)
	}
	marleen = m.(*models.User)

	m, err = userRepo.Save(joe)
	if err != nil {
		util.ErrExit(err)
	}
	joe = m.(*models.User)

	util.Log.Println("Posting message")

	msg := models.NewMsg(marleen.UserName, joe.UserName, "Welcome to the company!")
	if terr := timelineMgr.PostMsg(msg); terr != nil {
		util.ErrExit(terr)
	}

	util.Log.Println("Getting Joe's inbox for today")

	// Get Joe's inbox for today, get first message
	now := time.Now()
	joe_tl, terr := timelineMgr.GetTimeline(joe.UserName, models.TimelineType_INBOX, now)
	if terr != nil {
		util.ErrExit(terr)
	}

	for _, msgKey := range joe_tl.MsgKeys {
		m, err = msgRepo.Get(msgKey, false)
		if err != nil {
			util.ErrExit(err)
		}
		tl_msg := m.(*models.Msg)
		util.Log.Println("From: ", tl_msg.Sender)
		util.Log.Println("Msg: ", tl_msg.Text)
	}
}
