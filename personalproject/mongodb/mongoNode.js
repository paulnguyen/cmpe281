
  var MongoClient = require('mongodb').MongoClient,
    f = require('util').format,
    fs = require('fs');
  var mongo_uri='mongodb://admin:admin123@54.145.195.42:27017,3.81.242.72:27017,'+
  '3.209.66.95:27017,3.85.252.30:27017,52.202.192.206:27017/?replicaSet=cmpe281&authSource=admin';
   var options = {
  useNewUrlParser: true,
   server: { 
          autoReconnect: true,
          connectWithNoPrimary: true,
          reconnectTries : 100,
          socketOptions: { keepAlive: 1, connectTimeoutMS: 60000,socketTimeoutMS:90000 } 
          },
    replSet:{
      socketOptions: { keepAlive: 1, connectTimeoutMS: 60000,socketTimeoutMS:90000 },
      connectWithNoPrimary: true
    }};
    var database;
    var col = "people";
    var db = "test";
  module.exports = {

connectDatabase: function(callback){
     MongoClient.connect(mongo_uri,options, function(err, db){
     if(err) {callback('error');}
     database = db;
     callback('replicaset connected'); 
  })
  },
  insert: function(callback){
  	  var test = database.db("test");
      var obj = test.collection("bios");
      var docArray = [{'id':'1','name':'ABC'},{'id':'2','name':'DEF'},'id':'3','name':'ABC'},{'id':'4','name':'DEF'}];
      obj.insertMany(docArray,function(err,inserted){
                if (err) throw err;
                callback(docArray,'inserted');
      });  
  },
  update: function(callback){
 	  var test = database.db("test");
      var obj = test.collection("bio");
      obj.updateOne({'id':'1'},{$set:{'name':'America'}},function(err,result){
                if (err) throw err;
                callback('updated');
      }); 
  },
  get: function(callback){
 	  var test = database.db("test");
      var obj = test.collection("bio");
      obj.find({}).toArray(function(err,res){
                if (err) throw err;
                var doc = res;
                callback(doc,'retrieved');
      }); 
  }

};



