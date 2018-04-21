package repositories

import (
	riak "github.com/basho/riak-go-client"
	models "github.com/basho/taste-of-riak/go/ch03/models"
)

type UserRepository struct {
	repositoryImpl
}

func NewUserRepository(c *riak.Client) *UserRepository {
	r := &UserRepository{}
	r.client = c
	return r
}

func (u *UserRepository) Get(key string, notFoundOk bool) (models.Model, error) {
	return get(u, key, notFoundOk)
}

func (u *UserRepository) Save(m models.Model) (models.Model, error) {
	return save(u, m)
}

func (u *UserRepository) getBucketName() string {
	return "Users"
}

func (u *UserRepository) getModel() models.Model {
	return &models.User{}
}
