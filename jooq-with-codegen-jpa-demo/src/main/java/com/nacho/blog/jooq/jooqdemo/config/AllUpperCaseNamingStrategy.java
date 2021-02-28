package com.nacho.blog.jooq.jooqdemo.config;

import java.io.Serializable;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class AllUpperCaseNamingStrategy implements PhysicalNamingStrategy, Serializable {

  private static final long serialVersionUID = -2316823831893830058L;

  @Override
  public Identifier toPhysicalCatalogName(final Identifier name, final JdbcEnvironment jdbcEnvironment) {
    return toUpperCase(name);
  }

  @Override
  public Identifier toPhysicalSchemaName(final Identifier name, final JdbcEnvironment jdbcEnvironment) {
    return toUpperCase(name);
  }

  @Override
  public Identifier toPhysicalTableName(final Identifier name, final JdbcEnvironment jdbcEnvironment) {
    return toUpperCase(name);
  }

  @Override
  public Identifier toPhysicalSequenceName(final Identifier name, final JdbcEnvironment jdbcEnvironment) {
    return toUpperCase(name);
  }

  @Override
  public Identifier toPhysicalColumnName(final Identifier name, final JdbcEnvironment jdbcEnvironment) {
    return toUpperCase(name);
  }

  private Identifier toUpperCase(final Identifier name) {
    if (name == null) {
      return null;
    }
    return Identifier.toIdentifier(name.getText().toUpperCase());
  }
}