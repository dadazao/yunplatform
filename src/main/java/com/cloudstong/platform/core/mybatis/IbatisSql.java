package com.cloudstong.platform.core.mybatis;


public class IbatisSql {
  private String sql;
  private Object[] parameters;
  private Class resultClass;

  public Class getResultClass()
  {
    return resultClass;
  }

  public void setResultClass(Class resultClass) {
    this.resultClass = resultClass;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public String getSql() {
    return sql;
  }

  public void setParameters(Object[] parameters) {
    this.parameters = parameters;
  }

  public Object[] getParameters() {
    return parameters;
  }

  public String getCountSql()
  {
    String sqlCount = sql;
    int pos = sqlCount.indexOf("order by");
    if(pos != -1) {
    	sqlCount = sqlCount.substring(0,pos);
    }
//    ITableOperator tableOperator = (ITableOperator)
//      AppUtil.getBean(ITableOperator.class);
//    if (tableOperator.getDbType().equals("mssql")) {
//      sqlCount = sqlCount.trim();
//      Pattern pattern = Pattern.compile("^SELECT(\\s+(ALL|DISTINCT))?", 2);
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