package repositories

import (
	riak "github.com/basho/riak-go-client"
	models "github.com/basho/taste-of-riak/go/ch03/models"
)

type TimelineRepository struct {
	repositoryImpl
}

func NewTimelineRepository(c *riak.Client) *TimelineRepository {
	t := &TimelineRepository{}
	t.client = c
	return t
}

func (t *TimelineRepository) Get(key string, notFoundOk bool) (models.Model, error) {
	return get(t, key, notFoundOk)
}

func (t *TimelineRepository) Save(m models.Model) (models.Model, error) {
	return save(t, m)
}

func (t *TimelineRepository) getBucketName() string {
	return "Timelines"
}

func (t *TimelineRepository) getModel() models.Model {
	return &models.Timeline{}
}
