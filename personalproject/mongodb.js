
  var MongoClient = require('mongodb').MongoClient,
    f = require('util').format,
    fs = require('fs');

  // Creating URL 
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
  var id =0;

  // Connecting to MongoDB
  MongoClient.connect(mongo_uri, options, function(err, db){
      if(err) console.log(err); 

      // Checking nodes joining and leaving replicaset.
      db.topology.on('left', data => console.log('-> left', data));
      db.topology.on('joined', data => console.log('-> joined', data));


      var test = db.db("test");
      var products = test.collection("products");

      function insertDocument(){  
          ++id;
            products.insertOne({'id':id,'name':"product"+id},function(err,inserted){
                if (err) throw err;
                console.log("[INFO] - Successfully inserted "+id+" document.");
            });  
         
          // Delayed for 1 sec then dispatch another insert
          if(id<30){
           setTimeout(insertDocument, 1000);
          }
          else if(id>=30){         
            setTimeout(function(){
            products.count(function(err, result) {
              if(err) throw err;
              console.log("Data count inDB....");
              console.log(result);
            })},1000);
          
          }
        }
        setTimeout(insertDocument, 1000);

  });

