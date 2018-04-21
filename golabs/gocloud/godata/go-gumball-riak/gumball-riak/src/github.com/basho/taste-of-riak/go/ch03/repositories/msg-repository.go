package repositories

import (
	riak "github.com/basho/riak-go-client"
	models "github.com/basho/taste-of-riak/go/ch03/models"
)

type MsgRepository struct {
	repositoryImpl
}

func NewMsgRepository(c *riak.Client) *MsgRepository {
	m := &MsgRepository{}
	m.client = c
	return m
}

func (m *MsgRepository) Get(key string, notFoundOk bool) (models.Model, error) {
	return get(m, key, notFoundOk)
}

func (m *MsgRepository) Save(model models.Model) (models.Model, error) {
	return save(m, model)
}

func (m *MsgRepository) getBucketName() string {
	return "Msgs"
}

func (m *MsgRepository) getModel() models.Model {
	return &models.Msg{}
}
