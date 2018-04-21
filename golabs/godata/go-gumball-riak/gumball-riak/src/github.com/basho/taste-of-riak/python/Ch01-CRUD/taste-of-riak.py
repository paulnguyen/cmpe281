import riak

# Starting Client
myClient = riak.RiakClient(pb_port=8087, protocol='pbc')

# Note: Use this line instead of the former if using a local devrel cluster
#myClient = riak.RiakClient(pb_port=10017, protocol='pbc')

# Creating Objects In Riak
print 'Creating Objects In Riak...'

myBucket = myClient.bucket('test')

val1 = 1
key1 = myBucket.new('one', data=val1)
key1.store()

val2 = "two"
key2 = myBucket.new('two', data=val2)
key2.store()

val3 = {"myValue": 3}
key3 = myBucket.new('three', data=val3)
key3.store()

# Reading Objects From Riak
print 'Reading Objects From Riak...'

fetched1 = myBucket.get('one')
fetched2 = myBucket.get('two')
fetched3 = myBucket.get('three')

assert val1 == fetched1.data
assert val2 == fetched2.data
assert val3 == fetched3.data

# Updating Objects In Riak
print 'Updating Objects In Riak...'

fetched3.data["myValue"] = 42
fetched3.store()

# Deleting Objects From Riak
print 'Deleting Objects From Riak...'

fetched1.delete()
fetched2.delete()
fetched3.delete()

assert myBucket.get('one').exists is False
assert myBucket.get('two').exists is False
assert myBucket.get('three').exists is False

# Working With Complex Objects
print 'Working With Complex Objects...'

book = {
    'isbn': "1111979723",
    'title': "Moby Dick",
    'author': "Herman Melville",
    'body': "Call me Ishmael. Some years ago...",
    'copies_owned': 3
}

booksBucket = myClient.bucket('books')
newBook = booksBucket.new(book['isbn'], data=book)
newBook.store()

fetchedBook = booksBucket.get(book['isbn'])
print 'Serialized Object:'
print fetchedBook.encoded_data

fetchedBook.delete()
