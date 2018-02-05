package com.cloudstong.platform.core.dialect;


public class SQLServer2005Dialect extends Dialect
{
  public boolean supportsLimit()
  {
    return true;
  }

  public boolean supportsLimitOffset()
  {
    return true;
  }
  
  public String topN(String sql, int n) {
		return sql + " top " + n;
	}

  public String getLimitString(String querySqlString, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
  {
    int start = offset + 1;
    StringBuffer pagingBuilder = new StringBuffer();
    String orderby = getOrderByPart(querySqlString);
    String distinctStr = "";
    String loweredString = querySqlString.toLowerCase();
    String sqlPartString = querySqlString;
    if (loweredString.trim().startsWith("select"))
    {
      int index = 6;
      if (loweredString.startsWith("select distinct"))
      {
        distinctStr = "DISTINCT ";
        index = 15;
      }
      sqlPartString = sqlPartString.substring(index);
    }
    pagingBuilder.append(sqlPartString);

    if ((orderby == null) || (orderby.length() == 0))
    {
      orderby = "ORDER BY CURRENT_TIMESTAMP";
    }
    StringBuffer result = new StringBuffer();
    result.append("WITH query AS (SELECT ").append(distinctStr).append("TOP 100 PERCENT ").append(" ROW_NUMBER() OVER (").append(orderby).append(") as __row_number__, ").append(pagingBuilder).append(") SELECT * FROM query WHERE __row_number__ BETWEEN ").append(start).append(" AND ").append(
      offset + limit).append(" ORDER BY __row_number__");
    return result.toString();
  }

  static String getOrderByPart(String sql)
  {
    String loweredString = sql.toLowerCase();
    int orderByIndex = loweredString.indexOf("order by");
    if (orderByIndex != -1)
    {
      return sql.substring(orderByIndex);
    }

    return "";
  }

  public String getCountSql(String sql)
  {
    String sqlCount = sql;
//    ITableOperator tableOperator = (ITableOperator)
//      AppUtil.getBean(ITableOperator.class);
//    if (tableOperator.getDbType().equals("mssql")) {
//      sqlCount = sqlCount.trim();
//      Pattern pattern = Pattern.compile("^SELECT(\\s+(ALL|DISTINCT))?", 
//        2);
//      Matcher matcher = pattern.matcher(sqlCount);
//      if (matcher.find()) {
//        String matStr = matcher.group();
//        sqlCount = sqlCount.toUpperCase().replaceFirst(matStr.toUpperCase(), 
//          matStr.toUpperCase() + " TOP 100 PERCENT");
//      } else {
//        throw new UnsupportedOperationException("SQL语句拼接出现错误！");
//      }
//    }
    sqlCount = "select count(*) amount from (" + sqlCount + ") A";
    return sqlCount;
  }
}