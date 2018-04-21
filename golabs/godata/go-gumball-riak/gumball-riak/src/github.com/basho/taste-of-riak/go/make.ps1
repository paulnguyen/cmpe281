<#
.SYNOPSIS
    Powershell script to run Taste of Riak for Go on Windows
.DESCRIPTION
    This script will run 'go' correctly depending on parameters passed to this script.
.PARAMETER Target
    Target to build. Can be one of the following:
        * Format          - run *.go files through 'go fmt'
        * Run             - Run all examples
.PARAMETER Verbose
    Use to increase verbosity.
.EXAMPLE
    C:\Users\Bashoman> cd $env:GOPATH\src\github.com\basho\taste-of-riak\go
    C:\Users\Bashoman\go\src\github.com\basho\taste-of-riak\go>.\make.ps1 -Target Run -Verbose
.NOTES
    Author: Luke Bakken
    Date:   August 26, 2015
#>
[CmdletBinding()]
Param(
    [Parameter(Mandatory=$False, Position=0)]
    [ValidateSet('Format', 'Run', IgnoreCase = $True)]
    [string]$Target = 'Run'
)

Set-StrictMode -Version Latest

$package = 'github.com/basho/taste-of-riak/go'

$IsDebug = $DebugPreference -ne 'SilentlyContinue'
$IsVerbose = $VerbosePreference -ne 'SilentlyContinue'

# Note:
# Set to Continue to see DEBUG messages
if ($IsVerbose) {
    $DebugPreference = 'Continue'
}

trap
{
    Write-Error -ErrorRecord $_
    exit 1
}

function Get-ScriptPath {
    $scriptDir = Get-Variable PSScriptRoot -ErrorAction SilentlyContinue | ForEach-Object { $_.Value }
    if (!$scriptDir) {
        if ($MyInvocation.MyCommand.Path) {
            $scriptDir = Split-Path $MyInvocation.MyCommand.Path -Parent
        }
    }
    if (!$scriptDir) {
        if ($ExecutionContext.SessionState.Module.Path) {
            $scriptDir = Split-Path (Split-Path $ExecutionContext.SessionState.Module.Path)
        }
    }
    if (!$scriptDir) {
        $scriptDir = $PWD
    }
    return $scriptDir
}

function Execute($cmd, $argz) {
    Write-Verbose "$cmd $argz"
    & $cmd $argz
    if ($? -ne $True) {
        throw "'$cmd $argz' failed: $LastExitCode"
    }
    Write-Debug "'$cmd $argz' exit code: $LastExitCode"
}

function Do-Format {
    $script_path = Get-ScriptPath
    $cmd = 'gofmt'
    $argz = '-s', '-w', $script_path
    Execute $cmd $argz
}

function Do-InstallDeps {
    $cmd = 'go.exe'
    $argz = 'get', '-t', './...'
    Execute $cmd $argz
}

function Do-Vet {
    $cmd = 'go.exe'
    $script_path = Get-ScriptPath
    $argz = 'tool', 'vet', '-shadow=true', '-shadowstrict=true', $script_path
    Execute $cmd $argz
    $argz = 'vet', "$package/..."
    Execute $cmd $argz
}

function Do-Run {
    $cmd = 'go.exe'
    $argz = 'run',  './ch01/ch01.go'
    Execute $cmd $argz
    $argz = 'run',  './ch02/ch02.go'
    Execute $cmd $argz
    $argz = 'run',  './ch03/ch03.go'
    Execute $cmd $argz
}

Write-Debug "Target: $Target"

switch ($Target)
{
    'Format' { Do-Format }
    'Run' { Do-InstallDeps; Do-Vet; Do-Run; }
     default { throw "Unknown target: $Target" }
}

exit 0
