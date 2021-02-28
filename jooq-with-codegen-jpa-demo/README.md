# Spring + JOOQ (With codegen and JPA)

A simple spring-boot application using JOOQ with JPA.

Instead of just using the standard JOOQ libraries, it uses:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-jooq</artifactId>
</dependency>
```

This (almost) the same as using:

```xml
<dependency>
  <groupId>org.jooq</groupId>
  <artifactId>jooq</artifactId>
</dependency>
<dependency>
  <groupId>org.jooq</groupId>
  <artifactId>jooq-meta</artifactId>
</dependency>
<dependency>
  <groupId>org.jooq</groupId>
  <artifactId>jooq-codegen</artifactId>
</dependency>
```

In addition to that, you'll need another dependency:

```xml
<dependency>
	<groupId>org.jooq</groupId>
	<artifactId>jooq-meta-extensions-hibernate</artifactId>
	<version>3.14.7</version>
</dependency>
```

This version makes use of the JOOQ codegen capabilities combined with JPA annotations and you'll see that's easier than the standard codegen version [here](https://github.com/nacho270/db-libraries-demos/tree/master/jooq-with-codegen-demo) as it doesn't need a DB connection.

After booting, the app will insert a sample set of products.

## Caveats

- You need to add some method from the [JOOQ website](https://www.jooq.org/doc/3.14/manual/sql-execution/alternative-execution-models/using-jooq-with-jpa/using-jooq-with-jpa-entities/) to allow the JPA mapping combined with JOOQ.
- Effectively, JOOQ runs a native query and passes the parameters
- For the codegen stage, JOOQ spins up a H2 DB in memory, creates the tables and then reverse engieers them in order to create JOOQ objects.
- Couldn't find a way to fetch the lazy collection so i had to an hibernate hack to fetch the lazy list of items when getting the shipment by id.
- While doing this i had too many problems with case sensitivity for tables. While JOOQ generates table names with uppercase, hibernete perfoms the queries with lower case.
	
	- For this, i had to create `com.nacho.blog.jooq.jooqdemo.config.AllUpperCaseNamingStrategy` to force hibernate to create tables with upper case and i had to tweak `com.nacho.blog.jooq.jooqdemo.repository.impl.BasicRespository` to run every query with upper case.
	- This happened because i couldn't find a way for JOOQ to generate the code with lower case table names ... i tried all i could find.
	

## What you'll see

- Insert/query examples at `src/main/java/com/nacho/blog/jooq/jooqdemo/service/ProductRepositoryImpl.java` and `src/main/java/com/nacho/blog/jooq/jooqdemo/service/ShipmentRepositoryImpl.java`
- Count and Delete examples at `src/main/java/com/nacho/blog/jooq/jooqdemo/repository/impl/ShipmentRepositoryImpl.java`

## How to run

Perform a `mvn clean install -DskipTests`, this will generate the required classes in `target/generated-sources/jooq`.

### From IDE
- Pull mysql: `docker run --name jooq -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:latest`
- connect to the db and run the `init.sql` script.
- Run the app with maven `mvn spring-boot:run` or through the main class here: `src/main/java/com/nacho/blog/jooq/jooqdemo/JooqCodeGenJPADemoApplication.java`
- After booting, the app will create the tables and insert a sample set of products.

### Docker

- cd into `jooq-with-codegen-jpa-demo`
- `docker-compose up`

This will start a dockerized `mysql` with the schema and tables created by the app.

### Tests

- Run `src/test/java/com/nacho/blog/jooq/jooqdemo/service/ShipmentServiceTest.java`

### Sample curls

- `curl localhost:8080/product`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/product -d '{"name":"keyboard","price":30.5}'`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/shipment -d '{"items":[{"product":"e82658bf-cd8b-471c-9711-c0ea77733bdb","quantity":2}, {"product":"db909c72-7aaa-4805-be7b-b1579a9cb2c0","quantity":1}]}'`
- `curl localhost:8080/shipment/20f57155-cf88-4527-ab30-a5c079f9d358`
