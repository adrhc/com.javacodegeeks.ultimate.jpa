mvn archetype:generate -DarchetypeArtifactId=maven-archetype-quickstart -DgroupId=com.javacodegeeks.ultimate -DartifactId=jpa
http://www.javacodegeeks.com/2015/02/jpa-tutorial.html

JDBC URL jdbc:h2:~/Projects/com.javacodegeeks.ultimate.jpa/jpa tells H2 to
create the database file ~/Projects/com.javacodegeeks.ultimate.jpa/jpa.h2.db.

# check usage of the correct h2 jar:
# mvn dependency:tree | grep h2
# then use h2 console:
java -cp ~/.m2/repository/com/h2database/h2/1.3.176/h2-1.3.176.jar org.h2.tools.Shell -url jdbc:h2:~/Projects/com.javacodegeeks.ultimate.jpa/jpa
select * from T_PERSON;

CREATE DATABASE jpa;
GRANT ALL ON jpa.* TO 'jpa'@'%' IDENTIFIED BY 'jpa' WITH GRANT OPTION;
FLUSH PRIVILEGES;
