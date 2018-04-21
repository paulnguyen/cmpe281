package models

import (
	"fmt"
	"time"

	util "github.com/basho/taste-of-riak/go/util"
)

type Msg struct {
	modelImpl
	Sender    string
	Recipient string
	Text      string
	Created   time.Time
}

func NewMsg(sender, recipient, text string) *Msg {
	m := &Msg{
		Sender:    sender,
		Recipient: recipient,
		Text:      text,
		Created:   time.Now(),
	}
	m.SetId(m.GetId())
	return m
}

func (m *Msg) GetId() string {
	return fmt.Sprintf("%s_%v", m.Sender, util.Iso8601(m.Created))
}
