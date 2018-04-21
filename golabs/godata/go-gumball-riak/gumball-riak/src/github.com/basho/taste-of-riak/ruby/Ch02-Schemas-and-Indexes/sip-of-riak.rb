# Encoding: utf-8

require 'riak'
require 'pp'

# Starting Client
client = Riak::Client.new protocol: 'pbc', pb_port: 10017

# Creating Data
customer = {
    customer_id: 1,
    name: 'John Smith',
    address: '123 Main Street',
    city: 'Columbus',
    state: 'Ohio',
    zip: '43210',
    phone: '+1-614-555-5555',
    created_date: Time.parse('2013-10-1 14:30:26')
}

orders = [
  {
      order_id: 1,
      customer_id: 1,
      salesperson_id: 9000,
      items: [
          {
            item_id: 'TCV37GIT4NJ',
            title: 'USB 3.0 Coffee Warmer',
            price: 15.99
          },
          {
            item_id: 'PEG10BBF2PP',
            title: 'eTablet Pro, 24GB, Grey',
            price: 399.99
          }
      ],
      total: 415.98,
      order_date: Time.parse('2013-10-1 14:42:26')
  },
  {
      order_id: 2,
      customer_id: 1,
      salesperson_id: 9001,
      items: [
          {
            item_id: 'OAX19XWN0QP',
            title: 'GoSlo Digital Camera',
            price: 359.99
          }
      ],
      total: 359.99,
      order_date: Time.parse('2013-10-15 16:43:16')
  },
  {
      order_id: 3,
      customer_id: 1,
      salesperson_id: 9000,
      items: [
          {
            item_id: 'WYK12EPU5EZ',
            title: 'Call of Battle: Goats - Gamesphere 4',
            price: 69.99
          },
          {
            item_id: 'TJB84HAA8OA',
            title: 'Bricko Building Blocks',
            price: 4.99
          }
      ],
      total: 74.98,
      order_date: Time.parse('2013-11-3 17:45:28')
  }]

order_summary = {
    customer_id: 1,
    summaries: [
        {
            order_id: 1,
            total: 415.98,
            order_date: Time.parse('2013-10-1 14:42:26')
        },
        {
            order_id: 2,
            total: 359.99,
            order_date: Time.parse('2013-10-15 16:43:16')
        },
        {
            order_id: 3,
            total: 74.98,
            order_date: Time.parse('2013-11-3 17:45:28')
        }
    ]
}

# Creating Buckets and Storing Data
customer_bucket = client.bucket('Customers')
cr = customer_bucket.new(customer[:customer_id].to_s)
cr.data = customer
cr.store

order_bucket = client.bucket('Orders')
orders.each do |order|
  order_riak = order_bucket.new(order[:order_id].to_s)
  order_riak.data = order
  order_riak.store
end

order_summary_bucket = client.bucket('OrderSummaries')
os = order_summary_bucket.new(order_summary[:customer_id].to_s)
os.data = order_summary
os.store

### Fetching related data by shared key

shared_key = '1'
customer = customer_bucket.get(shared_key).data
customer[:order_summary] = order_summary_bucket.get(shared_key).data
puts "#Combined Customer and Order Summary: "
pp customer

### Adding Index Data

(1..3).each do |i|
  order = order_bucket.get(i.to_s)
  # Initialize our secondary indices
  order.indexes['salesperson_id_int'] = []
  order.indexes['order_date_bin'] = []

  order.indexes['salesperson_id_int'] <<  order.data['salesperson_id']
  order.indexes['order_date_bin'] << Time.parse(order.data['order_date'])
                                         .strftime('%Y%m%d')
  order.store
end

### 2i queries

# Query for orders where the SalespersonId index is set to 9000
puts "#Jane's Orders: "
pp order_bucket.get_index('salesperson_id_int', 9000)

# Query for orders where the OrderDate index
# is between 2013-10-01 and 2013-10-31
puts "#October's Orders: "
pp order_bucket.get_index('order_date_bin', '20131001'..'20131031')
