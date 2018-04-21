-module(msg_repository).
-export([create_msg/3, 
         get_msg/2]).
-include("msgy.hrl").

-spec create_msg(user_name(), user_name(), text()) -> msg().
create_msg(Sender, Recipient, Text) ->
    #msg{sender=Sender,
         recipient=Recipient,
         created=get_current_iso_timestamp(),
         text = Text}.

-spec get_msg(pid(), riakc_obj:key()) -> msg().
get_msg(ClientPid, MsgKey) -> 
    {ok, RMsg} = riakc_pb_socket:get(ClientPid, 
                                     ?MSG_BUCKET, 
                                     MsgKey),
    binary_to_term(riakc_obj:get_value(RMsg)).

%% @private
-spec get_current_iso_timestamp() -> datetimestamp().
get_current_iso_timestamp() ->
    {_,_,MicroSec} = DateTime = erlang:now(),
    {{Year,Month,Day},{Hour,Min,Sec}} = calendar:now_to_universal_time(DateTime),
    lists:flatten(
        io_lib:format("~4..0B-~2..0B-~2..0BT~2..0B:~2..0B:~2..0B.~6..0B",
            [Year, Month, Day, Hour, Min, Sec, MicroSec])).
