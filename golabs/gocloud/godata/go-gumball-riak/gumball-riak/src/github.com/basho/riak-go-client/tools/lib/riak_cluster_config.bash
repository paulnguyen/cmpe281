function riak_cluster_config
{
    declare -r riak_admin="$1"
    declare -ir http_port="$2"
    declare -r strong_consistency="${3:-false}"
    declare -r use_security="${4:-false}"

    $riak_admin bucket-type create plain
    $riak_admin bucket-type create search_type
    $riak_admin bucket-type create mr
    $riak_admin bucket-type create no_siblings '{"props":{"allow_mult":"false"}}'
    $riak_admin bucket-type create maps '{"props":{"datatype":"map"}}'
    $riak_admin bucket-type create sets '{"props":{"datatype":"set"}}'
    $riak_admin bucket-type create counters '{"props":{"datatype":"counter"}}'
    $riak_admin bucket-type create other_counters '{"props":{"datatype":"counter","allow_mult":true}}'
    $riak_admin bucket-type create yokozuna '{"props":{}}'

    set +o errexit
    $riak_admin bucket-type create hlls '{"props":{"datatype":"hll"}}'
    $riak_admin bucket-type create gsets '{"props":{"datatype":"gset"}}'
    $riak_admin bucket-type create write_once '{"props":{"write_once":true}}'
    $riak_admin bucket-type create GeoCheckin '{"props": {"n_val": 3, "table_def": "CREATE TABLE GeoCheckin (geohash varchar not null, user varchar not null, time timestamp not null, weather varchar not null, temperature double, PRIMARY KEY((geohash, user, quantum(time, 15, m)), geohash, user, time))"}}'
    $riak_admin bucket-type create GeoCheckin_Wide '{"props": {"n_val": 3, "table_def": "CREATE TABLE GeoCheckin_Wide (geohash varchar not null, user varchar not null, time timestamp not null, weather varchar not null, temperature double, uv_index sint64, observed boolean not null, PRIMARY KEY((geohash, user, quantum(time, 15, 'm')), geohash, user, time))"}}'
    $riak_admin bucket-type create 'GeoCheckin_Wide_1_5' '{"props": {"n_val": 3, "table_def": "CREATE TABLE GeoCheckin_Wide_1_5 (geohash varchar not null, user varchar not null, time timestamp not null, weather varchar not null, temperature double, uv_index sint64, observed boolean not null, sensor_data blob, PRIMARY KEY((geohash, user, quantum(time, 15, 'm')), geohash, user, time))"}}'
    $riak_admin bucket-type create WeatherByRegion '{"props": {"n_val": 3, "table_def": "CREATE TABLE WeatherByRegion (region varchar not null, state varchar not null, time timestamp not null, weather varchar not null, temperature double, uv_index sint64, observed boolean not null, PRIMARY KEY((region, state, quantum(time, 15, 'm')), region, state, time))"}}'
    $riak_admin bucket-type create BLURB '{"props": {"n_val": 3, "table_def": "CREATE TABLE BLURB (blurb blob not null, time timestamp not null, PRIMARY KEY((blurb, quantum(time, 15, 'm')), blurb, time))"}}'
    set -o errexit

    $riak_admin bucket-type activate plain
    $riak_admin bucket-type activate search_type
    $riak_admin bucket-type activate mr
    $riak_admin bucket-type activate no_siblings
    $riak_admin bucket-type activate maps
    $riak_admin bucket-type activate sets
    $riak_admin bucket-type activate counters
    $riak_admin bucket-type activate other_counters
    $riak_admin bucket-type activate yokozuna

    set +o errexit
    $riak_admin bucket-type activate hlls
    $riak_admin bucket-type activate gsets
    $riak_admin bucket-type activate write_once
    $riak_admin bucket-type activate GeoCheckin
    $riak_admin bucket-type activate GeoCheckin_Wide
    $riak_admin bucket-type activate GeoCheckin_Wide_1_5
    $riak_admin bucket-type activate WeatherByRegion
    $riak_admin bucket-type activate BLURB
    set -o errexit

    if [[ $strong_consistency == 'true' ]]
    then
        $riak_admin bucket-type create consistent '{"props":{"consistent":true,"n_val":5}}'
        $riak_admin bucket-type activate consistent
    fi

    set +o errexit
    echo -n 'Setting properties on test_multi_bucket HTTP CODE:'
    curl -4so /dev/null -w "%{http_code}" -XPUT -H 'Content-type: application/json' localhost:$http_port/buckets/test_multi_bucket/props -d '{"props":{"allow_mult":true,"last_write_wins":false}}'
    echo ' ...DONE'
    set -o errexit

    if [[ $use_security == 'true' ]]
    then
        # NB: don't exit on error due to 2.0.7
        # TODO: set -o errexit when all Riak versions >= 2.1.4
        set +o errexit
        $riak_admin security enable
        $riak_admin security add-group test

        # cert auth users
        $riak_admin security add-user riakuser 'groups=test'
        $riak_admin security add-source riakuser 0.0.0.0/0 certificate
        $riak_admin security add-user certuser 'groups=test'
        $riak_admin security add-source certuser 0.0.0.0/0 certificate

        # password auth users
        $riak_admin security add-user riakpass 'password=Test1234' 'groups=test'
        $riak_admin security add-user user 'password=password' 'groups=test'
        $riak_admin security add-source riakpass 0.0.0.0/0 password
        $riak_admin security add-source user 0.0.0.0/0 password

        # trust auth users
        $riak_admin security add-user riak_trust_user password=riak_trust_user
        $riak_admin security add-source riak_trust_user 0.0.0.0/0 trust

        # NB: Riak 2.0.7 does not support chaining grants via commas
        $riak_admin security grant riak_kv.get on any to all
        $riak_admin security grant riak_kv.get_preflist on any to all
        $riak_admin security grant riak_kv.put on any to all
        $riak_admin security grant riak_kv.delete on any to all
        $riak_admin security grant riak_kv.index on any to all
        $riak_admin security grant riak_kv.list_keys on any to all
        $riak_admin security grant riak_kv.list_buckets on any to all
        $riak_admin security grant riak_kv.mapreduce on any to all

        $riak_admin security grant riak_core.get_bucket on any to all
        $riak_admin security grant riak_core.set_bucket on any to all
        $riak_admin security grant riak_core.get_bucket_type on any to all
        $riak_admin security grant riak_core.set_bucket_type on any to all

        $riak_admin security grant search.admin on any to all
        $riak_admin security grant search.query on any to all

        $riak_admin security grant riak_ts.get on any to all
        $riak_admin security grant riak_ts.put on any to all
        $riak_admin security grant riak_ts.delete on any to all
        $riak_admin security grant riak_ts.list_keys on any to all
        $riak_admin security grant riak_ts.coverage on any to all
        $riak_admin security grant riak_ts.create_table on any to all
        $riak_admin security grant riak_ts.query_select on any to all
        $riak_admin security grant riak_ts.describe_table on any to all
        $riak_admin security grant riak_ts.show_tables on any to all
        set -o errexit
    fi

    return 0
}
