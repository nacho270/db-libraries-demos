# Spring + JOOQ 

A simple spring-boot application using JOOQ.

This version doesn't use any sort of the codegen capabilities of JOOQ and you'll see that's way more difficult than the codegen version here (LINK)

After booting, the app will create the tables and insert a sample set of products.

## What you'll see

- DDL examples at `src/main/java/com/nacho/blog/jooq/jooqdemo/config/CreateDBListener.java`
- Insert/query examples at `src/main/java/com/nacho/blog/jooq/jooqdemo/service/ProductService.java` and `src/main/java/com/nacho/blog/jooq/jooqdemo/service/ShipmentService.java`
- Count and Delete examples at `src/main/java/com/nacho/blog/jooq/jooqdemo/repository/impl/ShipmentRepositoryImpl.java`

## How to run

### From IDE
- Pull mysql: `docker run --name jooq -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:latest`
- connect to the db and run `create schema jooq;`
- Run the app

### Docker

- cd into `jooq-demo`
- `docker-compose up`

This will start a dockrized `mysql` with the schema already created and the app that will create the tables

### Tests



### Sample curls

- `curl localhost:8080/product`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/product -d '{"name":"keyboard","price":30.5}'`
- `curl -XPOST -H 'Content-type:application/json' localhost:8080/shipment -d '{"items":[{"product":"e82658bf-cd8b-471c-9711-c0ea77733bdb","quantity":2}, {"product":"db909c72-7aaa-4805-be7b-b1579a9cb2c0","quantity":1}]}'`
- `curl localhost:8080/shipment/20f57155-cf88-4527-ab30-a5c079f9d358`
