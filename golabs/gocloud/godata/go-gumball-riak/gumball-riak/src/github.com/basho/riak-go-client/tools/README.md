# Riak Client Library Tools

These scripts can be used to configure and start Riak for use in integration testing your Riak client library.

[![Build Status](https://travis-ci.org/basho/riak-client-tools.svg?branch=master)](https://travis-ci.org/basho/riak-client-tools)

## Usage

* Either install Riak using your package manager, or build Riak from source. Here is the general sequence of commands to build a `rel` release, which will create a single-node:

    ```
    mkdir -p $HOME/Projects/basho
    cd $HOME/Projects/basho
    git clone git://github.com/basho/riak.git
    cd riak
    make locked-deps
    make rel
    ```

    More info can be found in [the documentation](http://docs.basho.com/riak/kv/latest/setup/installing/source/)

* Then, use the `setup-riak` script to configure and start Riak. If you installed Riak from a package, no arguments should be necessary. Otherwise, the script assumes that a `rel` is available at `$HOME/basho/riak/rel`. This can be changed via the `-p` argument:

    ```
    ./setup-riak -p Path/to/riak/rel
    ./setup-riak -p Path/to/riak/dev
    ```

    If your packaging system installs the `riak.conf` somewhere other than `/etc/riak/riak.conf`, use the `-f` argument:

    ```
    ./setup-riak -f /opt/riak/etc/riak.conf
    ```

## Help Output 

```sh
./setup-riak: set up a Riak cluster (one or several nodes)

Usage:

setup-riak [-p <riak rel or devrel path>]
           [-f <riak.conf from installed pkg>]
           [-l PB:HTTP] [-d <secs>] [-clsxz]

-p  Riak devrel or rel path (Default: "$HOME/riak/rel")
-f  riak.conf from package (Default: "/etc/riak/riak.conf")
-d  Delay Riak operations (Default: 0 seconds)
-c  Set up cluster for Strong Consistency (NB: use at least 5 nodes)
-s  Set up cluster to use Riak Security
-x  Shut down any nodes and clean up directories.
-z  Set up cluster WITHOUT Yokozuna Search (Default: on)

-l  PB:HTTP Use PB as PB port (Default: 8087),
            HTTP as HTTP port (Default: 8098)
```
