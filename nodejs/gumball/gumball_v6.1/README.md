The OpenShift `nodejs` cartridge documentation can be found at:

http://openshift.github.io/documentation/oo_cartridge_guide.html#nodejs

MongoDB 2.4 Provisioned:

MongoDB 2.4 database added.  Please make note of these credentials:

   Root User:     admin
   Root Password: NRTkmQ9Vfajn
   Database Name: mongo

Connection URL: mongodb://$OPENSHIFT_MONGODB_DB_HOST:$OPENSHIFT_MONGODB_DB_PORT/

https://blog.openshift.com/getting-started-with-mongodb-on-nodejs-on-openshift/

// default to a 'localhost' configuration:
var connection_string = '127.0.0.1:27017/YOUR_APP_NAME';

// if OPENSHIFT env variables are present, use the available connection info:
if(process.env.OPENSHIFT_MONGODB_DB_PASSWORD){
  connection_string = process.env.OPENSHIFT_MONGODB_DB_USERNAME + ":" +
  process.env.OPENSHIFT_MONGODB_DB_PASSWORD + "@" +
  process.env.OPENSHIFT_MONGODB_DB_HOST + ':' +
  process.env.OPENSHIFT_MONGODB_DB_PORT + '/' +
  process.env.OPENSHIFT_APP_NAME;
}

// Sample Output
Node IP: 127.11.64.129
Node Port: 8080
MongoDB: admin:NRTkmQ9Vfajn@127.11.64.130:27017/mongo

// Error Notes:
http://stackoverflow.com/questions/16704703/not-authorized-for-query-on-admin-system-namespaces-on-mongodb/25085830#25085830
http://stackoverflow.com/questions/22245704/mongoerror-not-authorized-for-insert-query-stackato
http://stackoverflow.com/questions/22067612/mongodb-not-authorized-for-query-admin-system-users

// RockMongo AdminUI
Please make note of these MongoDB credentials:
  RockMongo User: admin
  RockMongo Password: NRTkmQ9Vfajn
URL: https://mongo-cloudlabs.rhcloud.com/rockmongo/

// Setup New User
db.addUser(
{  user:"adminuser", 
   pwd:"password", 
   roles: ["clusterAdmin", "readWriteAnyDatabase", "dbAdminAnyDatabase", "userAdminAnyDatabase"] 
} )

// Test Create a Document
db.towns.insert(
{
    "name": "New York",
    "population": 22200000,
    "famous_for": [
        "statue of liberty",
        "food"
    ],
    "mayor": {
        "name": "Michael Bloomberg",
        "party": "I"
    }
}
)

// Site Admin
mongo -uadmin -pNRTkmQ9Vfajn 127.11.64.130:27017/mongo --authenticationDatabase admin

// for MongoDB 2.4
use mongo ;
db.addUser( { user: "adminuser", pwd: "password", roles: ["readWrite"] } )

// for MongoDB 3.0
use mongo
db.createUser(
    {
      user: "adminuser",
      pwd: "password",
      roles: [
         { role: "readWrite", db: "mongo" }
      ]
    }
)


/**

Mighty Gumball, Inc.

NodeJS-Enabled Standing Gumball
Model# M102988
Serial# 1234998871109

-- SSH into OpenShift

ssh 55471c1e5973ca4280000006@mongo-cloudlabs.rhcloud.com

-- Login to MongoDB

mongo -uadmin -pNRTkmQ9Vfajn 127.11.64.130:27017/mongo 

-- Gumball MongoDB Collection (Create Document)

db.gumball.insert(
{ 
  id: 1,
  countGumballs: 8,
  modelNumber: 'M102988',
  serialNumber: '1234998871109' 
}
) ;

-- Gumball MongoDB Collection - Find Gumball Document

db.gumball.find( { id: 1 } ) ;

-- Gumball Order Insert New

db.gumballorders.insert( { OrdNum: 129801, OrdStatus: "Backorder" } ) ;  

-- Gumball Order Find

db.gumballorders.find();


**/


