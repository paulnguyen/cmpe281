function gen_riak_conf
{
    local -r out_file="$1"
    local -ir http_port="$2"
    local -ir pb_port="$3"
    local -ir https_port="${4:-0}"
    local -r security_cacert_file="$5"
    local -r security_cert_file="$6"
    local -r security_key_file="$7"
    local -ir node_count="${8:-1}"
    local -r search="${9:-on}"

    local -i start_percent=90
    if [[ $search == 'on' ]]
    then
        start_percent=60
    fi

    local -ir leveldb_memory_percent="$((start_percent / node_count))"

    cat >> $out_file <<EOT
ring_size = 64
search = $search
storage_backend = leveldb
leveldb.maximum_memory.percent = $leveldb_memory_percent
anti_entropy = passive

tls_protocols.sslv3 = off
tls_protocols.tlsv1 = on
tls_protocols.tlsv1.1 = on
tls_protocols.tlsv1.2 = on
check_crl = off

listener.http.internal = 0.0.0.0:$http_port
listener.protobuf.internal = 0.0.0.0:$pb_port
search.solr.start_timeout = 60s

EOT

    if (( https_port > 0 )) && \
    [[ -f $security_cacert_file && -f $security_cert_file && -f $security_key_file ]]
    then
        cat >> $out_file <<EOT
listener.https.internal = 0.0.0.0:$https_port
ssl.cacertfile = $security_cacert_file
ssl.certfile = $security_cert_file
ssl.keyfile = $security_key_file
EOT
    fi

    return 0
}
