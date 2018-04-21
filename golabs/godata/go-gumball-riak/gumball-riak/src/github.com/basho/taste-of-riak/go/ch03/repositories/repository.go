package repositories

import (
	"encoding/json"
	"errors"

	riak "github.com/basho/riak-go-client"
	models "github.com/basho/taste-of-riak/go/ch03/models"
)

var ErrUnexpectedSiblings = errors.New("Unexpected siblings in response!")

type Repository interface {
	Get(key string, notFoundOk bool) (models.Model, error)
	Save(models.Model) (models.Model, error)
	getBucketName() string
	getModel() models.Model
	getClient() *riak.Client
}

type repositoryImpl struct {
	client *riak.Client
}

func (ri *repositoryImpl) getClient() *riak.Client {
	return ri.client
}

func get(r Repository, key string, notFoundOk bool) (models.Model, error) {
	client := r.getClient()
	bucket := r.getBucketName()
	cmd, err := riak.NewFetchValueCommandBuilder().
		WithBucket(bucket).
		WithKey(key).
		WithNotFoundOk(notFoundOk).
		Build()
	if err != nil {
		return nil, err
	}
	if err = client.Execute(cmd); err != nil {
		return nil, err
	}

	fcmd := cmd.(*riak.FetchValueCommand)

	if notFoundOk && len(fcmd.Response.Values) == 0 {
		return nil, nil
	}

	if len(fcmd.Response.Values) > 1 {
		// Siblings present that need resolution
		// Here we'll just return an unexpected error
		return nil, ErrUnexpectedSiblings
	} else {
		return buildModel(r.getModel(), fcmd.Response.Values[0])
	}
}

func save(r Repository, m models.Model) (models.Model, error) {
	client := r.getClient()
	bucket := r.getBucketName()
	key := m.GetId()

	cmd, err := riak.NewFetchValueCommandBuilder().
		WithBucket(bucket).
		WithKey(key).
		WithNotFoundOk(true).
		Build()
	if err != nil {
		return nil, err
	}
	if err = client.Execute(cmd); err != nil {
		return nil, err
	}

	modelJson, err := json.Marshal(m)
	if err != nil {
		return nil, err
	}

	var objToInsertOrUpdate *riak.Object
	fcmd := cmd.(*riak.FetchValueCommand)
	if len(fcmd.Response.Values) > 1 {
		// Siblings present that need resolution
		// Here we'll just assume the first sibling is the "correct" one
		// with which to update with the new Model data
		// A conflict resolver can also be part of the options to fetchValue above
		objToInsertOrUpdate = fcmd.Response.Values[0]
		objToInsertOrUpdate.Value = modelJson
	} else {
		objToInsertOrUpdate = &riak.Object{
			Bucket:      bucket,
			Key:         key,
			ContentType: "application/json",
			Charset:     "utf8",
			Value:       modelJson,
		}
	}

	cmd, err = riak.NewStoreValueCommandBuilder().
		WithContent(objToInsertOrUpdate).
		WithReturnBody(true).
		Build()
	if err != nil {
		return nil, err
	}
	if err = client.Execute(cmd); err != nil {
		return nil, err
	}

	scmd := cmd.(*riak.StoreValueCommand)
	if len(scmd.Response.Values) > 1 {
		return nil, ErrUnexpectedSiblings
	}
	obj := scmd.Response.Values[0]
	return buildModel(r.getModel(), obj)
}

func buildModel(m models.Model, obj *riak.Object) (models.Model, error) {
	err := json.Unmarshal(obj.Value, m)
	m.SetId(obj.Key)
	return m, err
}
