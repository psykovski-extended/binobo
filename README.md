# Bionische Roboterhand: Binobo

This project aims at simulating a bionic robotic hand at as many degrees of freedom as possible.
In this source code, there is also the functionality of registration embedded as well as a blog functionality.

One thing you must consider when cloning this git repository is, that you have to add an `application.properties` file
at `src/main/resources/` with the following lines inserted:
```properties
# creates SPRING_SESSION db
spring.session.store-type=jdbc
spring.session.jdbc.initialize-schema=always

# setup MySQL Database
spring.datasource.url=jdbc:mysql://localhost:3306/binobo_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=<your-root-pw>
spring.jpa.hibernate.ddl-auto=update
# this can be ommited
spring.jpa.show-sql=true

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect

# mail config for email-verification
# here you can also change this to any other smtp server
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your.email@something.co
spring.mail.password=<your-pw>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

This project is completely open-source, so if you want to change anything in this source-code, just do so by creating a new branch and making a pull-request.

