function gen_adv_conf
{
    local out_file="$1"
    cat > $out_file <<'EOT'
[
    {riak_kv, [
        {allow_strfun, true},
        {delete_mode, immediate},
        {test, true}
    ]},
    {riak_core, [
        {target_n_val, 5}
    ]}
].
EOT

    return 0
}
