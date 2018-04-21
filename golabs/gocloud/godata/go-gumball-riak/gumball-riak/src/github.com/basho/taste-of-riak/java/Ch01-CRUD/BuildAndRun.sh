#!/bin/sh

javac -cp ../SharedLib/*.jar src/TasteOfRiak/TasteOfRiak.java
java -ea -cp "../SharedLib/*:src" TasteOfRiak.TasteOfRiak
