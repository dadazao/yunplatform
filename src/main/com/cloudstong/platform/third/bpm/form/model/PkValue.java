package com.cloudstong.platform.third.bpm.form.model;

public class PkValue
{
  private String name = "";
  private Object value;
  private boolean isAdd = false;

  public PkValue()
  {
  }

  public PkValue(String pkField, Object value)
  {
    name = pkField;
    this.value = value;
  }

  public boolean getIsAdd()
  {
    return isAdd;
  }

  public void setIsAdd(boolean isAdd) {
    this.isAdd = isAdd;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }
}