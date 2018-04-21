-module(user_repository).
-export([save_user/2, 
         get_user/2]).
-include("msgy.hrl").

-spec save_user(pid(), user()) -> riakc_obj:riakc_obj().
save_user(ClientPid, User) -> 
    RUser = riakc_obj:new(?USER_BUCKET, 
                          list_to_binary(User#user.user_name), 
                          User),
    riakc_pb_socket:put(ClientPid, RUser).

-spec get_user(pid(), user_name()) -> user().
get_user(ClientPid, UserName) -> 
    {ok, RUser} = riakc_pb_socket:get(ClientPid, 
                                      ?USER_BUCKET, 
                                      list_to_binary(UserName)),
    binary_to_term(riakc_obj:get_value(RUser)).