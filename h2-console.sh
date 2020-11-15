#!/bin/bash
# check usage of the correct h2 jar:
# mvn dependency:tree | grep h2
java -cp ~/.m2/repository/com/h2database/h2/1.3.176/h2-1.3.176.jar org.h2.tools.Shell -url jdbc:h2:./jpa-db
# case insensitive queries!
# select * from T_PERSON;
# select * from T_GEEK;
# select * from T_ID_CARD;
