
// Hello Mongo
mongo                       // connect to default db
db.test.find()              // find docs in test collection
db.test.save( { a : 1 } )   // save simple document
db.test.find()              // find docs again

// Create "hello" Database
mongo hello

// Display current Database
db

// Show Other Databases
show dbs

// Switch to Database
use hello

// Create a "towns" Collection
db.towns.insert(
{
    "name": "New York",
    "population": 22200000,
    "last_census": ISODate("2009-07-31"),
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

// Display Collections in current DB
show collections

// Display collection content
// See MongoDB Ref:  http://docs.mongodb.org/manual/reference/method/
db.towns.find()

// Display Help
db.help()
db.towns.help()

// Object Reflection | typeof Operator
typeof db
typeof db.towns
typeof db.towns.insert

// Call a Method without params to see the source code
db.towns.insert

// Create a function 
function insertCity(
  name, population, last_census,
  famous_for, mayor_info
) {
  db.towns.insert({
    name:name, 
    population:population,
    last_census: ISODate(last_census),
    famous_for:famous_for,
    mayor : mayor_info 
  });
}

// Insert using function
insertCity("Punxsutawney", 6200, '2008-01-31',
  ["phil the groundhog"], { name : "Jim Wehrle" }
)
	
insertCity("Portland", 582000, '2007-09-20',
  ["beer", "food"], { name : "Sam Adams", party : "D" }
)

// Find Document by ID
db.towns.find({ "_id" : ObjectId("5adb7eedb7a52b72fb10f5cd") })

// Find Document by ID selecting/excluding fields
db.towns.find({ "_id" : ObjectId("5adb7eedb7a52b72fb10f5cd") },
    { name : 1, population : 1 }
)

// Find Document by ID selecting/excluding fields
// Cond. Operator format:   field : { $op : value }
db.towns.find(
    { name : /^P/, population : { $lt : 10000 }},
    { name : 1, population : 1 }
)


