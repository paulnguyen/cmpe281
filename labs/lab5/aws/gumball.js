
// Hello Mongo
mongo                       // connect to default test db
db.test.find()              // find docs in test collection
db.test.save( { a : 1 } )   // save simple document
db.test.find()              // find docs again

// Create "cmpe281" Database
mongo cmpe281

// Display current Database
db

// Show Other Databases
show dbs

// Switch to Database
use cmpe281

// Display Collections in current DB
show collections


// MongoDB (Localhost) Connection

Host:   localhost
Port:   27017

// Add Mongodb Admin User
// See:  https://docs.mongodb.com/manual/reference/method/db.createUser/

 use test
 db.createUser(
    {
      user: "cmpe281",
      pwd: "cmpe281",
      roles: [ "readWrite", "dbAdmin" ]
    }
 )


// Gumball MongoDB Collection (Create Document)

db.gumball.insert(
{ 
  id: 1,
  countGumballs: 8,
  modelNumber: 'M102988',
  serialNumber: '1234998871109' 
}
) ;

// Gumball MongoDB Collection - Find Gumball Document

db.gumball.find( { id: 1 } ) ;


// Gumball Machine Inventory 

db.gumball.find()

{
    "_id" : ObjectId("54741c01fa0bd1f1cdf71312"),
    "id" : 1,
    "countGumballs" : 8,
    "modelNumber" : "M102988",
    "serialNumber" : "1234998871109"
}

db.gumball.update( 
    { id: 1 }, 
    { $set : { countGumballs : 10 } },
    { multi : false } 
)

// Gumball Orders 

db.gumballorders.insert( { OrdNum: '6447451112210432', OrdStatus: 'Submitted', } )

db.gumballorders.insert( 
  {   OrdNum: '6447451112210432',
      OrdStatus: 'Submitted',
  }
)

db.gumballorders.find(
  {
      "_id" : ObjectId("55439cea302ed2c804b0fe10")
  }
)

db.gumballorders.find(
  {
      "OrdNum" : "6737166575075328"
  }
)

db.gumballorders.find()

{
    "_id" : ObjectId("547d6512365294180dd1d365"),
    "OrdNum" : "5222465777172480",
    "OrdStatus" : "Backorder",
    "__v" : 0
}

{
    "_id" : ObjectId("547d65544fc50d1b0d7c864a"),
    "OrdNum" : "5843778627698688",
    "OrdStatus" : "Backorder",
    "__v" : 0
}

{
    "_id" : ObjectId("547d6a3c98a4a4240ddc0f11"),
    "OrdNum" : "7209957814435840",
    "OrdStatus" : "Submitted",
    "__v" : 0
}

{
    "_id" : ObjectId("547d6a4198a4a4240ddc0f12"),
    "OrdNum" : "5343800146788352",
    "OrdStatus" : "Submitted",
    "__v" : 0
}


// Stored MongoDB JavaScript Function
// http://docs.mongodb.org/manual/tutorial/store-javascript-function-on-server/

function processGumballOrders() {
    gm = db.gumball.find( { id : 1 } ) ;
    cnt = gm.next().countGumballs ;
    print( "Current Inventory: " + cnt ) ;
    cursor = db.gumballorders.find( { OrdStatus: { $ne: "Shipped" } } );
    cursor.snapshot() ; // force read isolation
    while ( cursor.hasNext() ) {
        order = cursor.next() ;        
        printjson( order );
        if ( cnt > 0 ) {
            cnt-- ;
            db.gumballorders.update( 
                { _id: order._id }, 
                { $set : { OrdStatus : "Shipped" } },
                { multi : false } 
            );
        }
        else {
            db.gumballorders.update( 
                { _id: order._id }, 
                { $set : { OrdStatus : "Backorder" } },
                { multi : false } 
            );
        }
    }   
    db.gumball.update ( 
        { id: 1 }, 
        { $set : { countGumballs : cnt } },
        { multi : false } 
    )
}

processGumballOrders() ;

