#!/bin/sh

javac -cp ../SharedLib/*.jar src/SipOfRiak/*.java
java -ea -cp "../SharedLib/*:src" SipOfRiak.SipOfRiak
