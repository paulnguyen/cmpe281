-module(timeline_repository).
-export([post_msg/2, 
         get_timeline/4]).
-include("msgy.hrl").

-spec post_msg(pid(), msg()) -> atom().
post_msg(ClientPid, Msg) -> 
     %% Save the cannonical copy
    SavedMsg = save_msg(ClientPid, Msg),
    MsgKey = binary_to_list(riakc_obj:key(SavedMsg)),

    %% Post to sender's Sent timeline
    add_to_timeline(ClientPid, Msg, sent, MsgKey),

    %% Post to recipient's Inbox timeline
    add_to_timeline(ClientPid, Msg, inbox, MsgKey),
    ok.

-spec get_timeline(pid(), 
               user_name(),
               msg_type(),
               date()) -> timeline().
get_timeline(ClientPid, Owner, MsgType, Date) -> 
    TimelineKey = generate_key(Owner, MsgType, Date),
    {ok, RTimeline} = riakc_pb_socket:get(ClientPid, 
                                          ?TIMELINE_BUCKET, 
                                          list_to_binary(TimelineKey)),
    binary_to_term(riakc_obj:get_value(RTimeline)).

%% --------------------------------------------------------------------

%% @private
-spec save_msg(pid(), msg()) -> riakc_obj:riakc_obj().
save_msg(ClientPid, Msg) -> 
    MsgKey = Msg#msg.sender ++ "_" ++ Msg#msg.created,
    ExistingMsg = riakc_pb_socket:get(ClientPid, 
                                      ?MSG_BUCKET, 
                                      list_to_binary(MsgKey)),
    SavedMsg = case ExistingMsg of
        {error, notfound} ->
            NewMsg = riakc_obj:new(?MSG_BUCKET, list_to_binary(MsgKey), Msg),
            {ok, NewSaved} = riakc_pb_socket:put(ClientPid, 
                                                 NewMsg, 
                                                 [if_none_match, return_body]),
            NewSaved;
        {ok, Existing} -> Existing
    end,
    SavedMsg.

%% @private
-spec add_to_timeline(pid(), msg(), msg_type(), key_string()) -> riakc_obj:riakc_obj().
add_to_timeline(ClientPid, Msg, MsgType, MsgKey) ->
    TimelineKey = generate_key_from_msg(Msg, MsgType),
    ExistingTimeline = riakc_pb_socket:get(ClientPid, 
                                           ?TIMELINE_BUCKET, 
                                           list_to_binary(TimelineKey)),
    UpdatedTimeline = case ExistingTimeline of
        {error, notfound} ->
            create_new_timeline(Msg, MsgType, MsgKey, TimelineKey);
        {ok, Existing} -> 
            add_to_existing_timeline(Existing, MsgKey)
    end,

    {ok, SavedTimeline} = riakc_pb_socket:put(ClientPid, 
                                              UpdatedTimeline, 
                                              [return_body]),
    SavedTimeline.

%% @private
-spec create_new_timeline(msg(), msg_type(), key_string(), key_string()) -> riakc_obj:riakc_obj().
create_new_timeline(Msg, MsgType, MsgKey, TimelineKey) ->
    Owner = get_owner(Msg, MsgType),
    Timeline = #timeline{owner=Owner,
                         msg_type=MsgType,
                         msgs=[MsgKey]},
    riakc_obj:new(?TIMELINE_BUCKET, list_to_binary(TimelineKey), Timeline).

%% @private
-spec add_to_existing_timeline(msg(), key_string()) -> riakc_obj:riakc_obj().
add_to_existing_timeline(ExistingRiakObj, MsgKey) ->
    ExistingTimeline = binary_to_term(riakc_obj:get_value(ExistingRiakObj)),
    ExistingMsgList = ExistingTimeline#timeline.msgs,
    UpdatedTimeline = ExistingTimeline#timeline{msgs=[MsgKey|ExistingMsgList]},
    riakc_obj:update_value(ExistingRiakObj, UpdatedTimeline).

%% @private 
-spec get_owner(msg(), msg_type()) -> user_name().
get_owner(Msg, inbox) ->  Msg#msg.recipient;
get_owner(Msg, sent) ->  Msg#msg.sender.

%% @private
-spec generate_key_from_msg(msg(), msg_type()) -> key_string().
generate_key_from_msg(Msg, MsgType) ->
    Owner = get_owner(Msg, MsgType),
    generate_key(Owner, MsgType, Msg#msg.created).

%% @private
-spec generate_key(user_name(), msg_type(), date()|datetimestamp()) -> key_string().
generate_key(Owner, MsgType, Date) when is_tuple(Date) ->
    DateString = get_iso_datestamp_from_date(Date),
    generate_key(Owner, MsgType, DateString);

generate_key(Owner, MsgType, Datetimestamp) ->
    DateString = get_iso_datestamp_from_iso_timestamp(Datetimestamp),
    MsgTypeString = case MsgType of
        inbox -> ?INBOX;
        sent -> ?SENT
    end,
    Owner ++ "_" ++ MsgTypeString ++ "_" ++ DateString.

%% @private 
-spec get_iso_datestamp_from_date(date()) -> nonempty_string().
get_iso_datestamp_from_date(Date) ->
    {Year,Month,Day} = Date,
    lists:flatten(io_lib:format("~4..0B-~2..0B-~2..0B", [Year, Month, Day])).

%% @private
-spec get_iso_datestamp_from_iso_timestamp(datetimestamp()) -> nonempty_string().
get_iso_datestamp_from_iso_timestamp(CreatedString) ->
    {Date, _} = lists:split(10,CreatedString),
    Date.
