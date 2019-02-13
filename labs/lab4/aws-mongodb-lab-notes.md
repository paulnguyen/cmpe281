### Sample Replica Set Host File

```
35.161.230.96		primary
35.163.130.91		secondary1
52.32.154.99		secondary2
```

### Create DB Admin User

```
db.createUser( {
        user: "admin",
        pwd: "******",
        roles: [{ role: "root", db: "admin" }]
    });
```	
	
### Test MongoDB Queries

```
use test 
db.test.save( { a : 1 } )   // save simple document
db.test.find()              // find document

db.test.find().forEach (	
	function (item) {
		db.test.update( 
			{ _id: item._id }, 
			{ $set: { a: 2 } } 
		)
	}
);
```

### Connecting Directly to Servers

```
mongo -u admin -p ***** -host primary --authenticationDatabase admin
mongo -u admin -p ***** -host secondary1 --authenticationDatabase admin
mongo -u admin -p ***** -host secondary2 --authenticationDatabase admin
```

### Connecting to the Replica Set

```
mongo -u admin -p ***** --authenticationDatabase admin mongodb://primary:27017,secondary1:27017,secondary2:27017/?replicaSet=cmpe281
```

### Connecting to Secondary with Stale Reads

```
mongo -u admin -p ***** -host secondary1 --authenticationDatabase admin

db.getMongo().setSlaveOk()
```

	


