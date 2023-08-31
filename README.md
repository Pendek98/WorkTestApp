# WorkTestApp
List all Github repositories, which are not forks.

Recive response code 404 for unexisting user and 406 for application/xml header in such a format: 

{

“status”: ${responseCode}

“Message”: ${whyHasItHappened}

}

# Technologies
Java 17
Spring Boot 3.1.3
Maven 3.9.4

# Launch
./mvnw spring-boot:run 
