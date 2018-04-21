function strong_consistency_conf
{
    local out_file="$1"
    cat >> "$out_file" <<'EOT'
strong_consistency = on
EOT

    return 0
}
