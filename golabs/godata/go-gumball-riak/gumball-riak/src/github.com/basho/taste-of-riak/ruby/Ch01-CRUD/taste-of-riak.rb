# Encoding: utf-8

require 'riak'
require 'pp'

# Starting Client
client = Riak::Client.new protocol: 'pbc', pb_port: 8087

# Note: Use this line instead of the former if using a local devrel cluster
# client = Riak::Client.new protocol: 'pbc', pb_port: 10017

# Creating Objects In Riak
puts 'Creating Objects In Riak...'

my_bucket = client.bucket('test')

val1 = 1
obj1 = my_bucket.new('one')
obj1.data = val1
obj1.store

val2 = 'two'
obj2 = my_bucket.new('two')
obj2.data = val2
obj2.store

val3 = { my_value: 3 }
obj3 = my_bucket.new('three')
obj3.data = val3
obj3.store

# Reading Objects From Riak
puts 'Reading Objects From Riak...'

fetched1 = my_bucket.get('one')
fetched2 = my_bucket.get('two')
fetched3 = my_bucket.get('three')

raise 'Assert Failed' unless val1 == fetched1.data
raise 'Assert Failed' unless val2 == fetched2.data
raise 'Assert Failed' unless val3.to_json == fetched3.data.to_json

# Updating Objects In Riak
puts 'Updating Objects In Riak...'

fetched3.data['myValue'] = 42
fetched3.store

# Deleting Objects From Riak
puts 'Deleting Objects From Riak...'

my_bucket.delete('one')
obj2.delete
obj3.delete

# Working With Complex Objects
puts 'Working With Complex Objects...'

book = {
    isbn: '1111979723',
    title: 'Moby Dick',
    author: 'Herman Melville',
    body: 'Call me Ishmael. Some years ago...',
    copies_owned: 3
}

books_bucket = client.bucket('books')
new_book = books_bucket.new(book[:isbn])
new_book.data = book
new_book.store

fetched_book = books_bucket.get(book[:isbn])
puts 'Serialized Object:'
puts fetched_book.raw_data

new_book.delete
