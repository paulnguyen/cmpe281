package util

import (
	"encoding/json"
	"fmt"
	"log"
	"os"
	"strconv"
	"time"
)

const iso8601format = "2006-01-02T15:04:05"

var Log = log.New(os.Stdout, "", log.LstdFlags)
var ErrLog = log.New(os.Stderr, "", log.LstdFlags)

func Iso8601(t time.Time) string {
	return t.Format(iso8601format)
}

func ErrExit(err error) {
	ErrLog.Println(err)
	os.Exit(1)
}

func GetRiakPort() uint16 {
	riakPort := uint16(10017)
	if portEnvVar := os.ExpandEnv("$RIAK_PORT"); portEnvVar != "" {
		if portNum, err := strconv.Atoi(portEnvVar); err == nil {
			riakPort = uint16(portNum)
		}
	}
	return riakPort
}

func GetRiakHost() string {
	riakHost := "riak-test"
	if hostEnvVar := os.ExpandEnv("$RIAK_HOST"); hostEnvVar != "" {
		riakHost = hostEnvVar
	}
	return riakHost
}

func GetRiakAddress() string {
	return fmt.Sprintf("%s:%d", GetRiakHost(), GetRiakPort())
}

func GetRiakAddresses() []string {
	// Assume that a 4-node devrel is being used where PB port numbers
	// increase by 10
	host := GetRiakHost()
	basePort := GetRiakPort()
	count := uint16(4)
	addrs := make([]string, count)
	for i := uint16(0); i < count; i++ {
		port := basePort + (i * 10)
		addrs[i] = fmt.Sprintf("%s:%d", host, port)
	}
	return addrs
}

func JsonDump(val interface{}) {
	if val == nil {
		Log.Println("[JsonDump]", "NIL VAL")
	} else {
		if json, err := json.MarshalIndent(val, "", "  "); err != nil {
			ErrLog.Printf("[JsonDump ERROR] %s", err.Error())
		} else {
			Log.Println(string(json))
		}
	}
}
