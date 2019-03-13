
var MongoClient = require('mongodb').MongoClient,
  f = require('util').format,
  fs = require('fs');

// Connect validating the returned certificates from the server
var mongo_uri='mongodb://admin:admin123@54.145.195.42:27017,3.81.242.72:27017,3.209.66.95:27017,3.85.252.30:27017,52.202.192.206:27017/?replicaSet=cmpe281&authSource=admin';
var options = {
  useNewUrlParser: true,
 server: { 
        autoReconnect: true,
        connectWithNoPrimary: true,
        reconnectTries : 10000,
        socketOptions: { keepAlive: 1, connectTimeoutMS: 600000,socketTimeoutMS:90000 } 
        },
  replSet:{
    socketOptions: { keepAlive: 1, connectTimeoutMS: 600000,socketTimeoutMS:90000 },
    connectWithNoPrimary: true
  }};
var id =0;

MongoClient.connect(mongo_uri, options, function(err, db){
    if(err){
        console.log(err);
    } 
    db.topology.on('left', data => console.log('-> left', data));
    db.topology.on('joined', data => console.log('-> joined', data));

      function insertDocument(){
          id++;
          var test = db.db("test");
          var products = test.collection("products");
          products.insertOne({'id':id,'name':"product"+id},function(err,inserted){
              //if (err) throw err;
              console.log("[INFO] - Successfully inserted "+id+" document.");
          });  
        console.log("[INFO] - Dispatched insert operation..."+ id);
        // Delayed for 1 sec then dispatch another insert.
        setTimeout(insertDocument, 1000);
  }

  insertDocument();
});

