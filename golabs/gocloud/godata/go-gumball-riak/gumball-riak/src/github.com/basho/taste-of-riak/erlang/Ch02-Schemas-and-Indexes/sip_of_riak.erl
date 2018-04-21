%%% Please copy/paste into your erl REPL.

%% Define Record Format 
rd(customer, {customer_id, name, address, city, state, zip, phone, created_date}).
rd(item, {item_id, title, price}).
rd(order, {order_id, customer_id, salesperson_id, items, total, order_date}).
rd(order_summary_entry, {order_id, total, order_date}).
rd(order_summary, {customer_id, summaries}).


%% Creating Data
Customer = #customer{ customer_id= 1,
                      name= "John Smith",
                      address= "123 Main Street",
                      city= "Columbus",
                      state= "Ohio",
                      zip= "43210",
                      phone= "+1-614-555-5555",
                      created_date= {{2013,10,1},{14,30,26}}}.

Orders =  [ #order{  
              order_id= 1,
              customer_id= 1,
              salesperson_id= 9000,
              items= [
                #item{
                  item_id= "TCV37GIT4NJ",
                  title= "USB 3.0 Coffee Warmer",
                  price= 15.99 },
                #item{
                  item_id= "PEG10BBF2PP",
                  title= "eTablet Pro, 24GB, Grey",
                  price= 399.99 }],
              total= 415.98,
              order_date= {{2013,10,1},{14,42,26}}},

            #order{
              order_id= 2,
              customer_id= 1,
              salesperson_id= 9001,
              items= [
                #item{
                  item_id= "OAX19XWN0QP",
                  title= "GoSlo Digital Camera",
                  price= 359.99 }],
              total= 359.99,
              order_date= {{2013,10,15},{16,43,16}}},

            #order {
              order_id= 3,
              customer_id= 1,
              salesperson_id= 9000,
              items= [
                #item{
                  item_id= "WYK12EPU5EZ",
                  title= "Call of Battle= Goats - Gamesphere 4",
                  price= 69.99 },
                #item{
                  item_id= "TJB84HAA8OA",
                  title= "Bricko Building Blocks",
                  price= 4.99 }],
              total= 74.98,
              order_date= {{2013,11,3},{17,45,28}}}
          ].

OrderSummary =  #order_summary{
                  customer_id= 1,
                  summaries= [
                    #order_summary_entry{
                      order_id= 1,
                      total= 415.98,
                      order_date= {{2013,10,1},{14,42,26}}
                    },
                    #order_summary_entry{
                      order_id= 2,
                      total= 359.99,
                      order_date= {{2013,10,15},{16,43,16}}
                    },
                    #order_summary_entry{
                      order_id= 3,
                      total= 74.98,
                      order_date= {{2013,11,3},{17,45,28}}}]}.


%% Remember to replace the ip and port parameters with those that match your cluster.
{ok, Pid} = riakc_pb_socket:start_link("127.0.0.1", 10017).


%% Creating Buckets and Storing Data
CustomerBucket = <<"Customers">>.
OrderBucket = <<"Orders">>.
OrderSummariesBucket = <<"OrderSummaries">>.

CustObj = riakc_obj:new(CustomerBucket, 
                        list_to_binary(
                          integer_to_list(
                            Customer#customer.customer_id)), 
                        Customer).

riakc_pb_socket:put(Pid, CustObj).

StoreOrder = fun(Order) ->
  OrderObj = riakc_obj:new(OrderBucket, 
                           list_to_binary(
                             integer_to_list(
                               Order#order.order_id)), 
                           Order),
  riakc_pb_socket:put(Pid, OrderObj)
end.

lists:foreach(StoreOrder, Orders).


OrderSummaryObj = riakc_obj:new(OrderSummariesBucket, 
                                list_to_binary(
                                  integer_to_list(
                                    OrderSummary#order_summary.customer_id)), 
                                OrderSummary).

riakc_pb_socket:put(Pid, OrderSummaryObj).


%% Fetching related data by shared key
{ok, FetchedCustomer} = riakc_pb_socket:get(Pid, 
                                            CustomerBucket, 
                                            <<"1">>).
{ok, FetchedSummary} = riakc_pb_socket:get(Pid, 
                                           OrderSummariesBucket, 
                                           <<"1">>).
rp({binary_to_term(riakc_obj:get_value(FetchedCustomer)),
    binary_to_term(riakc_obj:get_value(FetchedSummary))}).


%% Adding Index Data
FormatDate = fun(DateTime) ->
  {{Year, Month, Day}, {Hour, Min, Sec}} = DateTime,
  lists:concat([Year,Month,Day,Hour,Min,Sec])
end.

AddIndicesToOrder = fun(OrderKey) ->
  {ok, Order} = riakc_pb_socket:get(Pid, OrderBucket, 
                                    list_to_binary(integer_to_list(OrderKey))),

  OrderData = binary_to_term(riakc_obj:get_value(Order)),
  OrderMetadata = riakc_obj:get_update_metadata(Order),

  MD1 = riakc_obj:set_secondary_index(OrderMetadata,
                                      [{{binary_index, "order_date"},
                                        [FormatDate(OrderData#order.order_date)]}]),

  MD2 = riakc_obj:set_secondary_index(MD1,
                                      [{{integer_index, "salesperson_id"},
                                        [OrderData#order.salesperson_id]}]),

  Order2 = riakc_obj:update_metadata(Order,MD2),
  riakc_pb_socket:put(Pid,Order2)
end.

lists:foreach(AddIndicesToOrder, [1,2,3]).


%%% 2i queries
%% Query for orders where the SalespersonId index is set to 9000
riakc_pb_socket:get_index_eq(Pid, OrderBucket, {integer_index, "salesperson_id"}, 9000).

%% Query for orders where the OrderDate index
%% is between 2013-10-01 and 2013-10-31
riakc_pb_socket:get_index_range(Pid, OrderBucket, 
                                {binary_index, "order_date"}, 
                                <<"20131001">>, <<"20131031">>).
