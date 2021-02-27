# QueryDSL with JPA

A simple spring-boot application using [Mysema QueryDSL](http://www.querydsl.com/).

This application generates query-dsl code based on the JPA standard annotations.
The good thing about this is that it doesn't need a DB connection like [JOOQ with codegen](https://github.com/nacho270/db-libraries-demos/tree/master/jooq-with-codegen-demo).

You'll notice that it abstracts you from writing plain SQL or HQL with a nice DSL api and leverages the mapping to JPA which makes everything easier.

After booting, the app will insert a sample set of products.

## Caveats

- I had to use `String` as the `UUID` doesn't behave well with MySQL and JPA out of the box and i'd rather go with something simple.
- This library is more limited than JOOQ as it only targets DML expressions, not DDL.

## What you'll see

- Insert/query examples at `src/main/java/com/nacho/blog/querydsl/service/ProductRepositoryImpl.java` and `src/main/java/com/nacho/blog/jooq/jooqdemo/service/ShipmentRepositoryImpl.java`
	- See the special casy of `LAZY` collection `ShipmentRepositoryImpl.getById`
- Count and Delete examples at `src/main/java/com/nacho/blog/querydsl/repository/impl/ShipmentRepositoryImpl.java`

## How to run

Perform a `mvn clean install -DskipTests`, this will generate the required classes in `target/generated-sources/java`.

### From IDE
- Pull mysql: `docker run --name jooq -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:latest`
- connect to the db and run the `init.sql` script.
- Run the app with maven `mvn spring-boot:run` or through the main class here: `src/main/java/com/nacho/blog/querydsl/QueryDSLJPADemoApplication.java`
- After booting, the app will create the tables and insert a sample set of products.

### Docker

- cd into `querydsl-jpa-demo`
- `docker-compose up`

This will start a dockerized `mysql` alongside the app.

### Tests

- Run `src/test/java/com/nacho/blog/querydsl/service/ShipmentServiceTest.java`


### Sample curls

- `curl localhost:8080/product`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/product -d '{"name":"keyboard","price":30.5}'`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/shipment -d '{"items":[{"product":"e82658bf-cd8b-471c-9711-c0ea77733bdb","quantity":2}, {"product":"db909c72-7aaa-4805-be7b-b1579a9cb2c0","quantity":1}]}'`
- `curl localhost:8080/shipment/20f57155-cf88-4527-ab30-a5c079f9d358`
