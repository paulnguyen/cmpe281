%%% Please copy/paste into your erl REPL.

%% Starting Client
{ok, Pid} = riakc_pb_socket:start_link("127.0.0.1", 8087).

%% Note: Use this line instead of the former if using a local devrel cluster
%%{ok, Pid} = riakc_pb_socket:start_link("127.0.0.1", 10017).

%% Creating Objects In Riak
io:fwrite("Creating Objects In Riak...~n").

MyBucket = <<"test">>.

Val1 = 1.
Obj1 = riakc_obj:new(MyBucket, <<"one">>, Val1).
riakc_pb_socket:put(Pid, Obj1).

Val2 = "two".
Obj2 = riakc_obj:new(MyBucket, <<"two">>, Val2).
riakc_pb_socket:put(Pid, Obj2).

Val3 = {value, 3}.
Obj3 = riakc_obj:new(MyBucket, <<"three">>, Val3).
riakc_pb_socket:put(Pid, Obj3).

%% Reading Objects From Riak
io:fwrite("Reading Objects From Riak...~n").

{ok, Fetched1} = riakc_pb_socket:get(Pid, MyBucket, <<"one">>).
{ok, Fetched2} = riakc_pb_socket:get(Pid, MyBucket, <<"two">>).
{ok, Fetched3} = riakc_pb_socket:get(Pid, MyBucket, <<"three">>).

Val1 =:= binary_to_term(riakc_obj:get_value(Fetched1)).
Val2 =:= binary_to_term(riakc_obj:get_value(Fetched2)).
Val3 =:= binary_to_term(riakc_obj:get_value(Fetched3)).

%% Updating Objects In Riak
io:fwrite("Updating Objects In Riak...~n").

NewVal3 = setelement(2, Val3, 42).
UpdatedObj3 = riakc_obj:update_value(Fetched3, NewVal3).
{ok, NewestObj3} = riakc_pb_socket:put(Pid, UpdatedObj3, [return_body]).

rp(binary_to_term(riakc_obj:get_value(NewestObj3))).

%% Deleting Objects From Riak
io:fwrite("Deleting Objects From Riak...~n").

riakc_pb_socket:delete(Pid, MyBucket, <<"one">>).
riakc_pb_socket:delete(Pid, MyBucket, <<"two">>).
riakc_pb_socket:delete(Pid, MyBucket, <<"three">>).

{error,notfound} =:= riakc_pb_socket:get(Pid, MyBucket, <<"one">>).
{error,notfound} =:= riakc_pb_socket:get(Pid, MyBucket, <<"two">>).
{error,notfound} =:= riakc_pb_socket:get(Pid, MyBucket, <<"three">>).

%% Working With Complex Objects
io:fwrite("Working With Complex Objects...~n").

rd(book, {title, author, body, isbn, copies_owned}).

MobyDickBook = #book{title="Moby Dick", 
                     isbn="1111979723", 
                     author="Herman Melville", 
                     body="Call me Ishmael. Some years ago...", 
                     copies_owned=3}.

MobyObj = riakc_obj:new(<<"books">>, 
                        list_to_binary(MobyDickBook#book.isbn), 
                        MobyDickBook).

riakc_pb_socket:put(Pid, MobyObj).

{ok, FetchedBook} = riakc_pb_socket:get(Pid, 
                                        <<"books">>, 
                                        <<"1111979723">>).

io:fwrite("Serialized Object:~n").
rp(riakc_obj:get_value(FetchedBook)).

riakc_pb_socket:delete(Pid, <<"books">>, <<"1111979723">>).
riakc_pb_socket:stop(Pid).

