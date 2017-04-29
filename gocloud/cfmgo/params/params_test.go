package params_test

import (
	"encoding/json"
	"io/ioutil"
	"log"
	"net/http"
	"net/http/httptest"

	"github.com/gorilla/mux"
	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
	. "github.com/pivotal-pez/cfmgo/params"
	"gopkg.in/mgo.v2/bson"
)

var _ = Describe("ExtractQueryParams", func() {
	Context("when the handler is called with no query params", func() {
		mx := mux.NewRouter()
		mx.HandleFunc("/", func(w http.ResponseWriter, req *http.Request) {
			params := Extract(req.URL.Query())
			Formatter().JSON(w, http.StatusOK, &params)
			return
		}).Methods("GET")
		server := httptest.NewServer(mx)
		defer server.Close()

		url := server.URL + "/"
		res, err := http.Get(url)
		if err != nil {
			log.Fatal(err)
		}

		payload, err := ioutil.ReadAll(res.Body)
		res.Body.Close()
		if err != nil {
			log.Fatal(err)
		}

		var rp RequestParams
		err = json.Unmarshal(payload, &rp)
		if err != nil {
			log.Fatal(err)
		}

		It("should have a default values", func() {
			Expect(rp.Q).To(Equal(bson.M{}))
			Expect(rp.S).To(Equal(bson.M{}))
			Expect(rp.L).To(Equal(10))
			Expect(rp.F).To(Equal(0))
			Expect(rp.Selector()).To(Equal(rp.Q))
			Expect(rp.Scope()).To(Equal(rp.S))
			Expect(rp.Limit()).To(Equal(rp.L))
			Expect(rp.Offset()).To(Equal(rp.F))
		})
	})
	Context("when the handler is called with parameters", func() {
		mx := mux.NewRouter()
		mx.HandleFunc("/", func(w http.ResponseWriter, req *http.Request) {
			params := Extract(req.URL.Query())
			Formatter().JSON(w, http.StatusOK, &params)
			return
		}).Methods("GET")
		server := httptest.NewServer(mx)
		defer server.Close()

		url := server.URL + "/?_id=1&limit=15&offset=30&scope=_id,status&status=available"
		res, err := http.Get(url)
		if err != nil {
			log.Fatal(err)
		}

		payload, err := ioutil.ReadAll(res.Body)
		res.Body.Close()
		if err != nil {
			log.Fatal(err)
		}

		var rp RequestParams
		err = json.Unmarshal(payload, &rp)
		if err != nil {
			log.Fatal(err)
		}

		It("the request parameters object should be correctly populated", func() {
			Expect(rp.Q["_id"]).NotTo(BeNil())
			Expect(rp.Q["_id"].(string)).To(Equal("1"))
			Expect(rp.Q["status"]).NotTo(BeNil())
			Expect(rp.Q["status"].(string)).To(Equal("available"))
			Expect(rp.S["_id"]).NotTo(BeNil())
			Expect(rp.S["_id"].(float64)).To(Equal(float64(1)))
			Expect(rp.S["status"]).NotTo(BeNil())
			Expect(rp.S["status"].(float64)).To(Equal(float64(1)))
			Expect(rp.L).To(Equal(15))
			Expect(rp.F).To(Equal(30))
			Expect(rp.Selector()).To(Equal(rp.Q))
			Expect(rp.Scope()).To(Equal(rp.S))
			Expect(rp.Limit()).To(Equal(rp.L))
			Expect(rp.Offset()).To(Equal(rp.F))
		})
	})
})
