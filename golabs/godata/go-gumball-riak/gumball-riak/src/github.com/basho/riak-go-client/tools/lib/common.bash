set -o errexit
set -o nounset

declare -r debug='false'
declare -ir _default_transfer_timeout=300

function make_temp_dir
{
    local template="${1:-tmp-$$}"
    if [[ $template != *XXXXXX ]]
    then
        template="$template.XXXXXX"
    fi
    mktemp -d -t "$template"
}

function make_temp_file
{
    local template="${1:-tmp-$$}"
    if [[ $template != *XXXXXX ]]
    then
        template="$template.XXXXXX"
    fi
    mktemp -t "$template"
}

function now
{
    date '+%Y-%m-%d %H:%M:%S'
}

function pwarn
{
    echo "$(now) [warning]: $@" 1>&2
}

function perr
{
    echo "$(now) [error]: $@" 1>&2
}

function pinfo
{
    echo "$(now) [info]: $@"
}

function pinfo_n
{
    echo -n "$(now) [info]: $@"
}

function pdebug
{
    if [[ $debug == 'true' ]]
    then
        echo "$(now) [debug]: $@"
    fi
}

function errexit
{
    perr "$@"
    exit 1
}

function onexit
{
    if (( ${#DIRSTACK[*]} > 1 ))
    then
        popd >/dev/null 2>&1
    fi
    return 0
}

trap onexit EXIT

function transfers_in_progress
{
    local retval='in_progress'
    local transfers_out="$(make_temp_file riak-admin-transfers)"

    $riak_admin transfers > $transfers_out 2>&1
    if grep -iqF 'Node is not running' $transfers_out
    then
        perr 'Riak transfers did not complete. Error!'
        retval='error' # Return error
    elif grep -iqF 'No transfers active' $transfers_out
    then
        retval='done' # No longer in progress
    else
        retval='in_progress' # Still in progress
    fi
    rm -f $transfers_out
    echo $retval
}

function wait_for_transfers
{
    local -i transfer_timeout="${1:-$_default_transfer_timeout}"
    local -i start_secs="$(date '+%s')"
    local -i now_secs="$start_secs"
    local transfer_status="$(transfers_in_progress)"
    while [[ $transfer_status == 'in_progress' ]]
    do
        pinfo 'Transfers in progress.'
        sleep 5
        transfer_status="$(transfers_in_progress)"
        now_secs="$(date '+%s')"
        if (( now_secs - start_secs > transfer_timeout ))
        then
            perr "Transfers did not finish within $transfer_timeout seconds"
            return 1
        fi
    done
    pinfo "Transfer status: $transfer_status"

    if [[ $transfer_status == 'error' ]]
    then
        perr 'Transfers errored!'
        return 1
    fi
    return 0
}

declare -i node_count=0

function get_node_count
{
    # NB: this means it's a "rel" build
    if [[ -d ./riak ]]
    then
        node_count=1
        # NB: very important, used later to iterate over
        # directories to configure. There is only one such
        # dir in a 'rel' build
        riak_dir_glob='riak'
    else
        node_count="$(ls -1 . | grep '^dev[0-9]\+$' | wc -l)"
        # NB: very important, used later to iterate over
        # directories to configure. There may be several
        # dirs in a 'devrel'
        riak_dir_glob='dev*'
    fi

    if (( node_count == 0 ))
    then
        perr "No Riak rel or devrel directories found in $cluster_path"
        return 1
    fi

    return 0
}

declare -i delay_riak_ops=0

function maybe_sleep
{
    if (( delay_riak_ops > 0 ))
    then
        sleep $delay_riak_ops
    fi

    return 0
}
