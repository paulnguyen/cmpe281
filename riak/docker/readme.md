
# Docker Hub Image

* https://hub.docker.com/r/basho/riak-kv/

# Docker Networking

* https://runnable.com/docker/basic-docker-networking
* https://runnable.com/docker/docker-compose-networking

# Riak Cluster Management

* http://docs.basho.com/riak/kv/2.2.3/using/running-a-cluster/

# Default Ports for Riak

* http://docs.basho.com/dataplatform/1.0.0/configuring/default-ports/

# Riak Bucket Setup

	riak ping
	riak-admin test

	riak-admin bucket-type create gumball '{"props":{"search_index":"orders"}}'
	riak-admin bucket-type activate gumball	

	riak-admin bucket-type create bios '{"props":{"search_index":"name"}}'
	riak-admin bucket-type activate bios	



