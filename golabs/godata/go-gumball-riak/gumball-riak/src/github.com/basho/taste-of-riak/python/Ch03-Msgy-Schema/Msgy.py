from datetime import datetime
import string
import riak


class UserRepository:
    BUCKET = 'Users'

    def __init__(self, client):
        self.client = client

    def save(self, user):
        riak_obj = self.client.bucket(self.BUCKET).get(user['user_name'])
        riak_obj.data = user
        return riak_obj.store()

    def get(self, user_name):
        riak_obj = self.client.bucket(self.BUCKET).get(user_name)
        return riak_obj.data


class MsgRepository:
    BUCKET = 'Msgs'

    def __init__(self, client):
        self.client = client

    def save(self, msg):
        msgs = self.client.bucket(self.BUCKET)
        key = self._generate_key(msg)

        riak_obj = msgs.get(key)

        if not riak_obj.exists:
            riak_obj.data = msg
            riak_obj.store(if_none_match=True)

        return riak_obj

    def get(self, key):
        riak_obj = self.client.bucket(self.BUCKET).get(key)
        return riak_obj.data

    def _generate_key(self, msg):
        return msg['sender'] + '_' + msg['created']


class TimelineRepository:
    BUCKET = 'Timelines'
    SENT = 'Sent'
    INBOX = 'Inbox'

    def __init__(self, client):
        self.client = client
        self.msg_repo = MsgRepository(client)

    def post_message(self, msg):
        # Save the cannonical copy
        saved_message = self.msg_repo.save(msg)
        msg_key = saved_message.key

        # Post to sender's Sent timeline
        self._add_to_timeline(msg, self.SENT, msg_key)

        # Post to recipient's Inbox timeline
        self._add_to_timeline(msg, self.INBOX, msg_key)

    def get_timeline(self, owner, msg_type, date):
        key = self._generate_key(owner, msg_type, date)
        riak_obj = self.client.bucket(self.BUCKET).get(key)
        return riak_obj.data

    def _add_to_timeline(self, msg, msg_type, msg_key):
        timeline_key = self._generate_key_from_msg(msg, msg_type)
        riak_obj = self.client.bucket(self.BUCKET).get(timeline_key)

        if riak_obj.exists:
            riak_obj = self._add_to_existing_timeline(riak_obj,
                                                      msg_key)
        else:
            riak_obj = self._create_new_timeline(riak_obj,
                                                 msg, msg_type,
                                                 msg_key)

        return riak_obj.store()

    def _create_new_timeline(self, riak_obj, msg, msg_type, msg_key):
        owner = self._get_owner(msg, msg_type)
        new_timeline = {'owner': owner,
                        'msg_type': msg_type,
                        'msgs': [msg_key]}

        riak_obj.data = new_timeline
        return riak_obj

    def _add_to_existing_timeline(self, riak_obj, msg_key):
        riak_obj.data['msgs'].append(msg_key)
        return riak_obj

    def _get_owner(self, msg, msg_type):
        if msg_type == self.INBOX:
            return msg['recipient']
        else:
            return msg['sender']

    def _generate_key_from_msg(self, msg, msg_type):
        owner = self._get_owner(msg, msg_type)
        return self._generate_key(owner, msg_type, msg['created'])

    def _generate_key(self, owner, msg_type, datetimestr):
        dateString = string.split(datetimestr, 'T', 1)[0]
        return owner + '_' + msg_type + '_' + dateString


client = riak.RiakClient(pb_port=10017, protocol='pbc')
userRepo = UserRepository(client)
msgsRepo = MsgRepository(client)
timelineRepo = TimelineRepository(client)

marleen = {'user_name': 'marleenmgr',
           'full_name': 'Marleen Manager',
           'email': 'marleen.manager@basho.com'}

joe = {'user_name': 'joeuser',
       'full_name': 'Joe User',
       'email': 'joe.user@basho.com'}

userRepo.save(marleen)
userRepo.save(joe)

msg = {'sender': marleen['user_name'],
       'recipient': joe['user_name'],
       'created': datetime.utcnow().isoformat(),
       'text': 'Welcome to the company!'}

timelineRepo.post_message(msg)

joes_inbox_today = timelineRepo.get_timeline(
    joe['user_name'],
    'Inbox',
    datetime.utcnow().isoformat())

joes_first_message = msgsRepo.get(joes_inbox_today['msgs'][0])

print 'From: {0}\nMsg : {1}\n\n'.format(
    joes_first_message['sender'],
    joes_first_message['text'])
