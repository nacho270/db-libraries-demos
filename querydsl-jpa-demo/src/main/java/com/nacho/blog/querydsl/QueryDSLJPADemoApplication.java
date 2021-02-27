package com.nacho.blog.querydsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class QueryDSLJPADemoApplication {

  public static void main(final String[] args) {
    SpringApplication.run(QueryDSLJPADemoApplication.class, args);
  }

}
