# Spring + JOOQ (No codegen)

A simple spring-boot application using JOOQ.

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
```

However, you'll to add more dependencies for to handle transactions.

This version doesn't use any sort of the codegen capabilities of JOOQ and you'll see that's way more difficult than the codegen version [here](https://github.com/nacho270/db-libraries-demos/tree/master/jooq-with-codegen-demo).

You'll notice that on one side it does abstract you from writing plain SQL with a nice DSL fashion using the a `dialect` under the hood. However, it does tend to be tedious to write the queries and then having to map it's result to objects.


## What you'll see

- DDL examples at `src/main/java/com/nacho/blog/jooq/jooqdemo/config/CreateDBListener.java`
- Insert/query examples at `src/main/java/com/nacho/blog/jooq/jooqdemo/service/ProductService.java` and `src/main/java/com/nacho/blog/jooq/jooqdemo/service/ShipmentService.java`
- Count and Delete examples at `src/main/java/com/nacho/blog/jooq/jooqdemo/repository/impl/ShipmentRepositoryImpl.java`

## How to run

### From IDE / Maven
- Pull mysql: `docker run --name jooq -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:latest`
- connect to the db and run `create schema jooq;`
- Run the app with maven `mvn spring-boot:run` or through the main class here: `src/main/java/com/nacho/blog/jooq/jooqdemo/JooqDemoApplication.java`
- After booting, the app will create the tables and insert a sample set of products.

### Docker

- cd into `jooq-no-codegen-demo`
- `docker-compose up`

This will start a dockerized `mysql` with the schema already created and the app that will create the tables and insert sample producs.

### Tests

- Run `src/test/java/com/nacho/blog/jooq/jooqdemo/service/ShipmentServiceTest.java`


### Sample curls

- `curl localhost:8080/product`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/product -d '{"name":"keyboard","price":30.5}'`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/shipment -d '{"items":[{"product":"e82658bf-cd8b-471c-9711-c0ea77733bdb","quantity":2}, {"product":"db909c72-7aaa-4805-be7b-b1579a9cb2c0","quantity":1}]}'`
- `curl localhost:8080/shipment/20f57155-cf88-4527-ab30-a5c079f9d358`
