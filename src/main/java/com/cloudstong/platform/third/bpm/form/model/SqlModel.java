package com.cloudstong.platform.third.bpm.form.model;

public class SqlModel
{
  private String sql = "";
  private Object[] values;

  public SqlModel()
  {
  }

  public SqlModel(String sql, Object[] values)
  {
    this.sql = sql;
    this.values = values;
  }

  public String getSql()
  {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public Object[] getValues() {
    return values;
  }

  public void setValues(Object[] values) {
    this.values = values;
  }
}