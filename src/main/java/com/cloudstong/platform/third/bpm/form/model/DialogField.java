package com.cloudstong.platform.third.bpm.form.model;

public class DialogField
{
  private String fieldName = "";

  private String comment = "";

  private String condition = "";

  private String fieldType = "";

  private int defaultType = 1;

  private String defaultValue = "";

  public String getFieldName()
  {
    return fieldName;
  }
  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }
  public String getComment() {
    return comment;
  }
  public void setComment(String comment) {
    this.comment = comment;
  }
  public String getCondition() {
    return condition;
  }
  public void setCondition(String condition) {
    this.condition = condition;
  }
  public String getFieldType() {
    return fieldType;
  }
  public void setFieldType(String fieldType) {
    this.fieldType = fieldType;
  }
  public int getDefaultType() {
    return defaultType;
  }
  public void setDefaultType(int defaultType) {
    this.defaultType = defaultType;
  }
  public String getDefaultValue() {
    return defaultValue;
  }
  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }
}