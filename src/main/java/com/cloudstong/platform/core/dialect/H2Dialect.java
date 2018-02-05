package com.cloudstong.platform.core.dialect;


public class H2Dialect extends Dialect
{
  public boolean supportsLimit()
  {
    return true;
  }

  public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
  {
    return sql.length() + 40 + sql + (offset > 0 ? " limit " + limitPlaceholder + " offset " + offsetPlaceholder : new StringBuilder(" limit ").append(limitPlaceholder).toString());
  }

  public boolean supportsLimitOffset()
  {
    return true;
  }
}