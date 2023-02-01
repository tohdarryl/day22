# Day22 Workshop

## Dependencies
Spring Initializr
```
1. Spring Web
2. Spring Boot DevTools
3. Lombok
4. MySQL driver
5. Spring Data JPA 
```

Add in
```
	<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jdbc -->
	<dependency>
    	<groupId>org.springframework.boot</groupId>
   	 	<artifactId>spring-boot-starter-jdbc</artifactId>
   		 <version>3.0.2</version>
	</dependency>
```

## application.properties
```
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_example
spring.datasource.username=springuser
spring.datasource.password=ThePassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## Lombok (under Model)
```
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
}
```
