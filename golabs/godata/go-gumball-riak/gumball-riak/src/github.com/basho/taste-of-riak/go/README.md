Taste of Riak - Go
==================

# Setup

Clone [this repository](https://github.com/basho/taste-of/riak) to
`$GOPATH/src/github.com/basho/taste-of-riak`. You can then `cd` to the `go`
subdirectory and use `go run` on individual files to run the examples.

# Cluster Setup

When running these examples against a Riak `devrel`, you can use the following
command to set up the cluster to use the `leveldb` backend by default:

```bash
./tools/devrel/setup-dev-cluster -b leveldb
```

Other arguments may be necessary depending on your Riak environment.

