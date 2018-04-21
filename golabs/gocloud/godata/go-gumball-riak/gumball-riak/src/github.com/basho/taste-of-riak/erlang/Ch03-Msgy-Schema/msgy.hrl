-type key_string() :: nonempty_string().
-type msg_type() :: 'inbox' | 'sent'.
-type user_name() :: nonempty_string().
-type full_name() :: nonempty_string().
-type datetimestamp() :: nonempty_string().
-type text() :: nonempty_string().
-type email() :: nonempty_string().
-type year() :: non_neg_integer().
-type month() :: non_neg_integer().
-type day() :: non_neg_integer().
-type date() :: {year(), month(), day()}.

-record(user, {user_name :: user_name(), 
               full_name :: full_name(), 
               email :: email()}).

-record(msg, {sender :: user_name(), 
              recipient :: user_name(), 
              created :: datetimestamp(), 
              text :: nonempty_string()}).

-record(timeline, {owner :: user_name(), 
                   msg_type :: msg_type(), 
                   msgs = [] :: [key_string()]}).

-type user() :: #user{}.
-type msg() :: #msg{}.
-type timeline() :: #timeline{}.

-define(USER_BUCKET, <<"Users">>).
-define(MSG_BUCKET, <<"Msgs">>).
-define(TIMELINE_BUCKET, <<"Timelines">>).
-define(INBOX, "Inbox").
-define(SENT, "Sent").

-export_type([user/0, msg/0, timeline/0, key_string/0, msg_type/0,
			  user_name/0, full_name/0, datetimestamp/0, text/0,
			  email/0, year/0, month/0, day/0, date/0]).
