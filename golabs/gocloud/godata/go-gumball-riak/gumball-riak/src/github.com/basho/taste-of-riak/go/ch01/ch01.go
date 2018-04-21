package main

import (
	"encoding/binary"
	"encoding/json"
	"sync"

	riak "github.com/basho/riak-go-client"
	util "github.com/basho/taste-of-riak/go/util"
)

type Book struct {
	ISBN        string
	Title       string
	Author      string
	Body        string
	CopiesOwned uint16
}

func main() {
	var err error

	// un-comment-out to enable debug logging
	// riak.EnableDebugLogging = true

	o := &riak.NewClientOptions{
		RemoteAddresses: []string{util.GetRiakAddress()},
	}

	var c *riak.Client
	c, err = riak.NewClient(o)
	if err != nil {
		util.ErrExit(err)
	}

	defer func() {
		if err := c.Stop(); err != nil {
			util.ErrExit(err)
		}
	}()

	val1 := uint32(1)
	val1buf := make([]byte, 4)
	binary.LittleEndian.PutUint32(val1buf, val1)

	val2 := "two"

	val3 := struct{ MyValue int }{3} // NB: ensure that members are exported (i.e. capitalized)
	var val3json []byte
	val3json, err = json.Marshal(val3)
	if err != nil {
		util.ErrExit(err)
	}

	bucket := "test"

	util.Log.Println("Creating Objects In Riak...")

	objs := []*riak.Object{
		{
			Bucket:      bucket,
			Key:         "one",
			ContentType: "application/octet-stream",
			Value:       val1buf,
		},
		{
			Bucket:      bucket,
			Key:         "two",
			ContentType: "text/plain",
			Value:       []byte(val2),
		},
		{
			Bucket:      bucket,
			Key:         "three",
			ContentType: "application/json",
			Value:       val3json,
		},
	}

	var cmd riak.Command
	wg := &sync.WaitGroup{}

	for _, o := range objs {
		cmd, err = riak.NewStoreValueCommandBuilder().
			WithContent(o).
			Build()
		if err != nil {
			util.ErrLog.Println(err)
			continue
		}
		a := &riak.Async{
			Command: cmd,
			Wait:    wg,
		}
		if err := c.ExecuteAsync(a); err != nil {
			util.ErrLog.Println(err)
		}
	}

	wg.Wait()

	util.Log.Println("Reading Objects From Riak...")

	d := make(chan riak.Command, len(objs))

	for _, o := range objs {
		cmd, err = riak.NewFetchValueCommandBuilder().
			WithBucket(bucket).
			WithKey(o.Key).
			Build()
		if err != nil {
			util.ErrLog.Println(err)
			continue
		}
		a := &riak.Async{
			Command: cmd,
			Wait:    wg,
			Done:    d,
		}
		if err := c.ExecuteAsync(a); err != nil {
			util.ErrLog.Println(err)
		}
	}

	wg.Wait()
	close(d)

	var obj3 *riak.Object

	for done := range d {
		f := done.(*riak.FetchValueCommand)
		/* un-comment to dump fetched object as JSON
		if json, jerr := json.MarshalIndent(f.Response, "", "  "); err != nil {
			util.ErrLog.Println(jerr)
		} else {
			util.Log.Println("fetched value: ", string(json))
		}
		*/
		obj := f.Response.Values[0]
		switch obj.Key {
		case "one":
			if actual, expected := binary.LittleEndian.Uint32(obj.Value), val1; actual != expected {
				util.ErrLog.Printf("key: %s, actual %v, expected %v", obj.Key, actual, expected)
			}
		case "two":
			if actual, expected := string(obj.Value), val2; actual != expected {
				util.ErrLog.Printf("key: %s, actual %v, expected %v", obj.Key, actual, expected)
			}
		case "three":
			obj3 = obj
			val3.MyValue = 0
			if jerr := json.Unmarshal(obj.Value, &val3); jerr != nil {
				util.ErrLog.Println(jerr)
			} else {
				if actual, expected := val3.MyValue, int(3); actual != expected {
					util.ErrLog.Printf("key: %s, actual %v, expected %v", obj.Key, actual, expected)
				}
			}
		default:
			util.ErrLog.Printf("unrecognized key: %s", obj.Key)
		}
	}

	util.Log.Println("Updating Object Three In Riak...")

	val3.MyValue = 42
	obj3.Value, err = json.Marshal(val3)
	if err != nil {
		util.ErrExit(err)
	}

	cmd, err = riak.NewStoreValueCommandBuilder().
		WithContent(obj3).
		WithReturnBody(true).
		Build()
	if err != nil {
		util.ErrLog.Println(err)
	} else {
		if err := c.Execute(cmd); err != nil {
			util.ErrLog.Println(err)
		}
	}

	svcmd := cmd.(*riak.StoreValueCommand)
	svrsp := svcmd.Response
	obj3 = svrsp.Values[0]
	val3.MyValue = 0
	if jerr := json.Unmarshal(obj3.Value, &val3); jerr != nil {
		util.ErrLog.Println(jerr)
	} else {
		if actual, expected := val3.MyValue, int(42); actual != expected {
			util.ErrLog.Printf("key: %s, actual %v, expected %v", obj3.Key, actual, expected)
		}
	}
	util.Log.Println("updated object key: ", obj3.Key)
	util.Log.Println("updated object value: ", val3.MyValue)

	book := &Book{
		ISBN:        "1111979723",
		Title:       "Moby Dick",
		Author:      "Herman Melville",
		Body:        "Call me Ishmael. Some years ago...",
		CopiesOwned: 3,
	}

	var jbook []byte
	jbook, err = json.Marshal(book)
	if err != nil {
		util.ErrExit(err)
	}

	bookObj := &riak.Object{
		Bucket:      "books",
		Key:         book.ISBN,
		ContentType: "application/json",
		Value:       jbook,
	}

	cmd, err = riak.NewStoreValueCommandBuilder().
		WithContent(bookObj).
		WithReturnBody(false).
		Build()
	if err != nil {
		util.ErrLog.Println(err)
	} else {
		if err := c.Execute(cmd); err != nil {
			util.ErrLog.Println(err)
		}
	}

	cmd, err = riak.NewFetchValueCommandBuilder().
		WithBucket("books").
		WithKey(book.ISBN).
		Build()
	if err != nil {
		util.ErrExit(err)
	}
	if err := c.Execute(cmd); err != nil {
		util.ErrLog.Println(err)
	}

	fcmd := cmd.(*riak.FetchValueCommand)
	bookObj = fcmd.Response.Values[0]
	util.Log.Println("serialized object: ", string(bookObj.Value))

	util.Log.Println("Deleting Objects From Riak...")

	objs = append(objs, bookObj)
	for _, o := range objs {
		cmd, err = riak.NewDeleteValueCommandBuilder().
			WithBucket(o.Bucket).
			WithKey(o.Key).
			Build()
		if err != nil {
			util.ErrLog.Println(err)
			continue
		}
		a := &riak.Async{
			Command: cmd,
			Wait:    wg,
		}
		if err := c.ExecuteAsync(a); err != nil {
			util.ErrLog.Println(err)
		}
	}

	wg.Wait()
}
