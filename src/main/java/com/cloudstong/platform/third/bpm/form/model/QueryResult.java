package com.cloudstong.platform.third.bpm.form.model;

import java.util.ArrayList;
import java.util.List;

public class QueryResult
{
  private List list = new ArrayList();

  private int isPage = 0;

  private int pageSize = 10;

  private int page = 1;

  private String errors = "";

  private int totalCount = 0;

  private int totalPage = 0;

  public List getList() {
    return list;
  }

  public void setList(List list) {
    this.list = list;
  }

  public int getIsPage() {
    return isPage;
  }

  public void setIsPage(int isPage) {
    this.isPage = isPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public String getErrors() {
    return errors;
  }

  public void setErrors(String errors) {
    this.errors = errors;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }
}