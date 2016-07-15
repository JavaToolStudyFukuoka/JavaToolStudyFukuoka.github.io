#!/bin/sh

# build
mvn package

ls -l ~/.m2/repository/com/h2database/h2/1.4.192/h2-1.4.192.jar
ls -l ~/.m2/repository/org/seasar/doma/doma/2.12.1/doma-2.12.1.jar

# path
CLASSPATH=target/helloworld-1.0-SNAPSHOT.jar:~/.m2/repository/com/h2database/h2/1.4.192/h2-1.4.192.jar:~/.m2/repository/org/seasar/doma/doma/2.12.1/doma-2.12.1.jar
export CLASSPATH

# exec
java com.example.domalt.App

