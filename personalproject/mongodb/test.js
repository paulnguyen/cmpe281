var assert = require('assert');
var mongo = require('./mongoNode');
var db;
describe('Database Tests', function() {
  //Before starting the test, create a sandboxed database connection
  //Once a connection is established invoke done()
});


beforeEach('connect to db', function(done){
  mongo.connectDatabase(function(result){
  	assert(result,'replicaset connected');
  	done();
  })
});

describe('insert',function(){
it('inserting 2 documents', function(done){
	mongo.insert(function(doc,result){
		console.log(doc);
		assert.equal(result,'inserted');
		done();
	})
});
});

describe('update',function(){
it('updating', function(done){
	mongo.update(function(result){
		assert.equal(result,'updated');
		done();
	})
});
});

describe('retrieve',function(){
it('retreiving', function(done){
	mongo.get(function(doc,result){
		assert.equal(result,'retrieved');
		console.log(doc);
		done();
	})
});
});
