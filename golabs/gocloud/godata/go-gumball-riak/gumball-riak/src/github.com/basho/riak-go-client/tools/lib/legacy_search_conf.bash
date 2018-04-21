function legacy_search_conf
{
    local riak_conf="$1"
    local adv_conf="$2"

    sed -i -e "s/search = on/search = off/g" $riak_conf

    cat > $adv_conf <<'EOT'
[
    {riak_kv, [
        {delete_mode, immediate}
    ]},
    {riak_search, [
        {enabled, true}
    ]}
].
EOT

    return 0
}
