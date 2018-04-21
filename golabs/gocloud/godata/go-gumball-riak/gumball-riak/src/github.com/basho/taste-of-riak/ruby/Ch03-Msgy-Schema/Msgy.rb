# Encoding: utf-8

require 'riak'
require 'hashie'
require 'time'

MultiJson.use 'json_gem'
Riak.json_options[:symbolize_keys] = true

class User < Hashie::Dash
  property :user_name
  property :full_name
  property :email
end

class Msg < Hashie::Dash
  property :from
  property :to
  property :created
  property :text
end

class Timeline < Hashie::Dash
  property :owner
  property :type
  property :msgs
end

class UserRepository
  BUCKET = 'Users'

  def initialize(client)
    @client = client
  end

  def save(user)
    users = @client.bucket(BUCKET)
    key = user.user_name

    riak_obj = users.get_or_new(key)
    riak_obj.data = user
    riak_obj.content_type = 'application/json'
    riak_obj.store
  end

  def get(user_name)
    riak_obj = @client.bucket(BUCKET)[user_name]
    User.new(riak_obj.data)
  end
end

class MsgRepository
  BUCKET = 'Msgs'

  def initialize(client)
    @client = client
  end

  def save(msg)
    msgs = @client.bucket(BUCKET)
    key = generate_key(msg)

    return msgs.get(key) if msgs.exists?(key)
    riak_obj = msgs.new(key)
    riak_obj.data = msg
    riak_obj.content_type = 'application/json'
    riak_obj.prevent_stale_writes = true
    riak_obj.store(returnbody: true)
  end

  def get(key)
    riak_obj = @client.bucket(BUCKET).get(key)
    Msg.new(riak_obj.data)
  end

  def generate_key(msg)
    msg.from + '_' + msg.created.utc.iso8601(6)
  end
end

class TimelineRepository
  BUCKET = 'Timelines'
  SENT = 'Sent'
  INBOX = 'Inbox'

  def initialize(client)
    @client = client
    @msg_repo = MsgRepository.new(client)
  end

  def post_message(msg)
    # Save the cannonical copy
    saved_message = @msg_repo.save(msg)
    # Post to sender's Sent timeline
    add_to_timeline(msg, SENT, saved_message.key)
    # Post to recipient's Inbox timeline
    add_to_timeline(msg, INBOX, saved_message.key)
  end

  def get_timeline(owner, type, date)
    riak_obj = @client.bucket(BUCKET).get(generate_key(owner, type, date))
    Timeline.new(riak_obj.data)
  end

  private

  def add_to_timeline(msg, type, msg_key)
    timeline_key = generate_key_from_msg(msg, type)
    riak_obj = nil

    if @client.bucket(BUCKET).exists?(timeline_key)
      riak_obj = add_to_existing_timeline(timeline_key, msg_key)
    else
      riak_obj = create_new_timeline(timeline_key, msg, type, msg_key)
    end

    riak_obj.store
  end

  def create_new_timeline(key, msg, type, msg_key)
    owner = get_owner(msg, type)
    riak_obj = @client.bucket(BUCKET).new(key)
    riak_obj.data = Timeline.new(owner: owner,
                                 type: type,
                                 msgs: [msg_key])
    riak_obj.content_type = 'application/json'
    riak_obj
  end

  def add_to_existing_timeline(key, msg_key)
    riak_obj = @client.bucket(BUCKET).get(key)
    timeline = Timeline.new(riak_obj.data)
    timeline.msgs << msg_key
    riak_obj.data = timeline
    riak_obj
  end

  def get_owner(msg, type)
    type == INBOX ? msg.to : msg.from
  end

  def generate_key_from_msg(msg, type)
    owner = get_owner(msg, type)
    generate_key(owner, type, msg.created)
  end

  def generate_key(owner, type, date)
    owner + '_' + type + '_' + date.utc.strftime('%F')
  end
end

# Setup our repositories
client = Riak::Client.new(pb_port: 10017)
user_repo = UserRepository.new(client)
msgs_repo = MsgRepository.new(client)
timeline_repo = TimelineRepository.new(client)

# Create and save users
marleen = User.new(user_name: 'marleenmgr',
                   full_name: 'Marleen Manager',
                   email: 'marleen.manager@basho.com')

joe = User.new(user_name: 'joeuser',
               full_name: 'Joe User',
               email: 'joe.user@basho.com')

user_repo.save(marleen)
user_repo.save(joe)

# Create new Msg, post to timelines
msg = Msg.new(from: marleen.user_name,
              to: joe.user_name,
              created: Time.now,
              text: 'Welcome to the company!')

timeline_repo.post_message(msg)

# Get Joe's inbox for today, get first message
joes_inbox_today = timeline_repo.get_timeline(joe.user_name, 'Inbox', Time.now)
joes_first_message = msgs_repo.get(joes_inbox_today.msgs.first)

puts "From: #{joes_first_message.from}\nMsg : #{joes_first_message.text}"

# Cleanup, never ever use list keys.  Ever.
# Riak.disable_list_keys_warnings = true
# ['Users', 'Timelines', 'Msgs'].each { |b|
#     client.bucket(b).keys.each {|k| client[b].delete(k)}
# }
# Riak.disable_list_keys_warnings = false
