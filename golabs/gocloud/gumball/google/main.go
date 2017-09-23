package main

import (
	"net/http"
	"github.com/codegangsta/negroni"
	"github.com/gorilla/mux"
	"github.com/unrolled/render"
 // "google.golang.org/appengine"
 //	"google.golang.org/appengine/log"	
)

// Google App Engine Entry Point
func init() {
	formatter := render.New(render.Options{
		IndentJSON: true,
	})
	n := negroni.Classic()
	mx := mux.NewRouter()
	initRoutes(mx, formatter)
	n.UseHandler(mx)
    http.Handle("/", mx)
}