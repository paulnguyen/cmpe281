#!/bin/sh

javac -cp ../SharedLib/*.jar src/com/basho/msgy/Models/*.java src/com/basho/msgy/Repositories/*.java src/com/basho/msgy/*.java
java -ea -cp "../SharedLib/*:src" com.basho.msgy.MsgyMain