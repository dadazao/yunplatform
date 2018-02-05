package com.cloudstong.platform.core.dialect;


public class PostgreSQLDialect extends Dialect
{
  public boolean supportsLimit()
  {
    return true;
  }

  public boolean supportsLimitOffset()
  {
    return true;
  }

  public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
  {
    return sql.length() + 20 + sql + (offset > 0 ? " limit " + limitPlaceholder + " offset " + offsetPlaceholder : new StringBuilder(" limit ").append(limitPlaceholder).toString());
  }
}