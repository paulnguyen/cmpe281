package main

import (
	"encoding/json"
	"errors"
	"fmt"
	"reflect"
	"sync"
	"time"

	riak "github.com/basho/riak-go-client"
	util "github.com/basho/taste-of-riak/go/util"
)

const (
	timeFmt              = "2006-01-02 15:04:05"
	customersBucket      = "Customers"
	ordersBucket         = "Orders"
	orderSummariesBucket = "OrderSummaries"
)

type Customer struct {
	Name        string
	Address     string
	City        string
	State       string
	Zip         string
	Phone       string
	CreatedDate time.Time
}

type Order struct {
	Id            string
	CustomerId    string
	SalespersonId string
	Items         []*OrderItem
	Total         float32
	Date          time.Time
}

type OrderItem struct {
	Id    string
	Title string
	Price float32
}

type OrderSummary struct {
	CustomerId string
	Summaries  []*OrderSummaryItem
}

type OrderSummaryItem struct {
	Id    string
	Total float32
	Date  time.Time
}

func main() {
	var err error
	var customerId string

	util.Log.Println("Creating Data")

	var cd time.Time
	cd, err = time.Parse(timeFmt, "2013-10-01 14:30:26")
	if err != nil {
		util.ErrExit(err)
	}

	customer := &Customer{
		Name:        "John Smith",
		Address:     "123 Main Street",
		City:        "Columbus",
		State:       "Ohio",
		Zip:         "43210",
		Phone:       "+1-614-555-5555",
		CreatedDate: cd,
	}

	util.Log.Printf("customer: %v", customer)

	util.Log.Println("Starting Client")

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

	util.Log.Println("Storing Customer")

	var cmd riak.Command
	var customerJson []byte

	customerJson, err = json.Marshal(customer)
	if err != nil {
		util.ErrExit(err)
	}

	obj := &riak.Object{
		Bucket:      customersBucket,
		ContentType: "application/json",
		Value:       customerJson,
	}

	cmd, err = riak.NewStoreValueCommandBuilder().
		WithContent(obj).
		WithReturnBody(true).
		Build()
	if err != nil {
		util.ErrExit(err)
	}
	if eerr := c.Execute(cmd); eerr != nil {
		util.ErrExit(eerr)
	}

	svc := cmd.(*riak.StoreValueCommand)
	customerId = svc.Response.GeneratedKey
	if customerId == "" {
		util.ErrExit(errors.New("expected generated customer Id"))
	} else {
		util.Log.Println("Customer ID:", customerId)
	}

	util.Log.Println("Storing Data")

	var orders []*Order
	orders, err = createOrders(customerId)
	if err != nil {
		util.ErrExit(err)
	}

	var orderSummary *OrderSummary
	var orderSummaryJson []byte
	orderSummary = createOrderSummary(customerId, orders)

	ccmds := 1 + len(orders)
	cmds := make([]riak.Command, ccmds)

	// command to store OrderSummary
	orderSummaryJson, err = json.Marshal(orderSummary)
	if err != nil {
		util.ErrExit(err)
	}
	obj = &riak.Object{
		Bucket:      orderSummariesBucket,
		Key:         customerId,
		ContentType: "application/json",
		Value:       orderSummaryJson,
	}
	cmds[0], err = riak.NewStoreValueCommandBuilder().
		WithContent(obj).
		Build()
	if err != nil {
		util.ErrExit(err)
	}

	for i, order := range orders {
		// command to store Order
		var orderJson []byte
		orderJson, err = json.Marshal(order)
		if err != nil {
			util.ErrExit(err)
		}
		obj = &riak.Object{
			Bucket:      ordersBucket,
			Key:         order.Id,
			ContentType: "application/json",
			Value:       orderJson,
		}
		cmds[i+1], err = riak.NewStoreValueCommandBuilder().
			WithContent(obj).
			Build()
		if err != nil {
			util.ErrExit(err)
		}
	}

	errored := false
	wg := &sync.WaitGroup{}
	for _, cmd := range cmds {
		a := &riak.Async{
			Command: cmd,
			Wait:    wg,
		}
		if eerr := c.ExecuteAsync(a); eerr != nil {
			errored = true
			util.ErrLog.Println(eerr)
		}
	}
	wg.Wait()
	if errored {
		util.ErrExit(errors.New("error, exiting!"))
	}

	util.Log.Println("Fetching related data by shared key")

	cmds = cmds[:0]

	// fetch customer
	cmd, err = riak.NewFetchValueCommandBuilder().
		WithBucket(customersBucket).
		WithKey(customerId).
		Build()
	if err != nil {
		util.ErrExit(err)
	}
	cmds = append(cmds, cmd)

	// fetch OrderSummary
	cmd, err = riak.NewFetchValueCommandBuilder().
		WithBucket(orderSummariesBucket).
		WithKey(customerId).
		Build()
	if err != nil {
		util.ErrExit(err)
	}
	cmds = append(cmds, cmd)

	doneChan := make(chan riak.Command)
	errored = false
	for _, cmd := range cmds {
		a := &riak.Async{
			Command: cmd,
			Done:    doneChan,
		}
		if eerr := c.ExecuteAsync(a); eerr != nil {
			errored = true
			util.ErrLog.Println(eerr)
		}
	}
	if errored {
		util.ErrExit(errors.New("error, exiting!"))
	}

	for i := 0; i < len(cmds); i++ {
		select {
		case d := <-doneChan:
			if fv, ok := d.(*riak.FetchValueCommand); ok {
				obj := fv.Response.Values[0]
				switch obj.Bucket {
				case customersBucket:
					util.Log.Printf("Customer     1: %v", string(obj.Value))
				case orderSummariesBucket:
					util.Log.Printf("OrderSummary 1: %v", string(obj.Value))
				}
			} else {
				util.ErrExit(fmt.Errorf("unknown response command type: %v", reflect.TypeOf(d)))
			}
		case <-time.After(5 * time.Second):
			util.ErrExit(errors.New("fetch operations took too long"))
		}
	}

	util.Log.Println("Adding Index Data")

	// fetch orders to add index data
	cmds = cmds[:0]

	for _, order := range orders {
		cmd, err = riak.NewFetchValueCommandBuilder().
			WithBucket(ordersBucket).
			WithKey(order.Id).
			Build()
		if err != nil {
			util.ErrExit(err)
		}
		cmds = append(cmds, cmd)
	}

	errored = false
	for _, cmd := range cmds {
		a := &riak.Async{
			Command: cmd,
			Done:    doneChan,
		}
		if eerr := c.ExecuteAsync(a); eerr != nil {
			errored = true
			util.ErrLog.Println(eerr)
		}
	}
	if errored {
		util.ErrExit(errors.New("error, exiting!"))
	}

	errored = false
	for i := 0; i < len(cmds); i++ {
		select {
		case d := <-doneChan:
			if fv, ok := d.(*riak.FetchValueCommand); ok {
				obj := fv.Response.Values[0]
				switch obj.Key {
				case "1":
					obj.AddToIntIndex("SalespersonId_int", 9000)
					obj.AddToIndex("OrderDate_bin", "2013-10-01")
				case "2":
					obj.AddToIntIndex("SalespersonId_int", 9001)
					obj.AddToIndex("OrderDate_bin", "2013-10-15")
				case "3":
					obj.AddToIntIndex("SalespersonId_int", 9000)
					obj.AddToIndex("OrderDate_bin", "2013-11-03")
				}
				scmd, serr := riak.NewStoreValueCommandBuilder().
					WithContent(obj).
					Build()
				if serr != nil {
					util.ErrExit(serr)
				}
				a := &riak.Async{
					Command: scmd,
					Wait:    wg,
				}
				if eerr := c.ExecuteAsync(a); eerr != nil {
					errored = true
					util.ErrLog.Println(eerr)
				}
			} else {
				util.ErrExit(fmt.Errorf("unknown response command type: %v", reflect.TypeOf(d)))
			}
		case <-time.After(5 * time.Second):
			util.ErrExit(errors.New("fetch operations took too long"))
		}
	}

	if errored {
		util.ErrExit(errors.New("error, exiting!"))
	}

	wg.Wait()
	close(doneChan)

	util.Log.Println("Index Queries")

	cmd, err = riak.NewSecondaryIndexQueryCommandBuilder().
		WithBucket(ordersBucket).
		WithIndexName("SalespersonId_int").
		WithIndexKey("9000").
		Build()
	if err != nil {
		util.ErrExit(err)
	}

	if eerr := c.Execute(cmd); eerr != nil {
		util.ErrExit(eerr)
	}

	qcmd := cmd.(*riak.SecondaryIndexQueryCommand)
	for _, rslt := range qcmd.Response.Results {
		util.Log.Println("Jane's Orders, key: ", string(rslt.ObjectKey))
	}

	cmd, err = riak.NewSecondaryIndexQueryCommandBuilder().
		WithBucket(ordersBucket).
		WithIndexName("OrderDate_bin").
		WithRange("2013-10-01", "2013-10-31").
		Build()
	if err != nil {
		util.ErrExit(err)
	}

	if eerr := c.Execute(cmd); eerr != nil {
		util.ErrExit(eerr)
	}

	qcmd = cmd.(*riak.SecondaryIndexQueryCommand)
	for _, rslt := range qcmd.Response.Results {
		util.Log.Println("October's Orders, key: ", string(rslt.ObjectKey))
	}
}

func createOrders(customerId string) ([]*Order, error) {
	o := make([]*Order, 3)

	d, err := time.Parse(timeFmt, "2013-10-01 14:42:26")
	if err != nil {
		return nil, err
	}
	o[0] = &Order{
		Id:            "1",
		CustomerId:    customerId,
		SalespersonId: "9000",
		Items: []*OrderItem{
			{
				Id:    "TCV37GIT4NJ",
				Title: "USB 3.0 Coffee Warmer",
				Price: 15.99,
			},
			{
				Id:    "PEG10BBF2PP",
				Title: "eTablet Pro, 24GB; Grey",
				Price: 399.99,
			},
		},
		Total: 415.98,
		Date:  d,
	}

	d, err = time.Parse(timeFmt, "2013-10-15 16:43:16")
	if err != nil {
		return nil, err
	}
	o[1] = &Order{
		Id:            "2",
		CustomerId:    customerId,
		SalespersonId: "9001",
		Items: []*OrderItem{
			{
				Id:    "OAX19XWN0QP",
				Title: "GoSlo Digital Camera",
				Price: 359.99,
			},
		},
		Total: 359.99,
		Date:  d,
	}

	d, err = time.Parse(timeFmt, "2013-11-03 17:45:28")
	if err != nil {
		return nil, err
	}
	o[2] = &Order{
		Id:            "3",
		CustomerId:    customerId,
		SalespersonId: "9000",
		Items: []*OrderItem{
			{
				Id:    "WYK12EPU5EZ",
				Title: "Call of Battle : Goats - Gamesphere 4",
				Price: 69.99,
			},
			{
				Id:    "TJB84HAA8OA",
				Title: "Bricko Building Blocks",
				Price: 4.99,
			},
		},
		Total: 74.98,
		Date:  d,
	}

	return o, nil
}

func createOrderSummary(customerId string, orders []*Order) *OrderSummary {

	s := &OrderSummary{
		CustomerId: customerId,
		Summaries:  make([]*OrderSummaryItem, len(orders)),
	}

	for i, o := range orders {
		s.Summaries[i] = &OrderSummaryItem{
			Id:    o.Id,
			Total: o.Total,
			Date:  o.Date,
		}
	}

	return s
}
