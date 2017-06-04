package com.cloudstong.platform.third.bpm.form.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.core.util.StringUtil;

public class BpmFormDialog extends EntityBase
{
  public static final String Page = "p";
  public static final String PageSize = "z";
  protected Long id = Long.valueOf(0L);

  protected String name = "";

  protected String alias = "";

  protected Integer style = Integer.valueOf(0);

  protected Integer width = Integer.valueOf(400);

  protected Integer height = Integer.valueOf(300);

  protected Integer issingle = Integer.valueOf(1);

  protected Integer needpage = Integer.valueOf(1);

  protected Integer istable = Integer.valueOf(1);

  protected String objname = "";

  protected String displayfield = "";

  protected String conditionfield = "";

  protected String resultfield = "";

  protected String dsname = "";

  protected String dsalias = "";

  private List<Map<String, Object>> list = new ArrayList();

  private Integer pagesize = Integer.valueOf(10);

  public Map<String, String> getTreeField()
  {
    if (style.intValue() == 0) {
      return null;
    }
    Map map = new HashMap();
    JSONObject jsonObject = JSONObject.fromObject(displayfield);
    map.put("id", jsonObject.get("id").toString());
    map.put("pid", jsonObject.get("pid").toString());
    map.put("displayName", jsonObject.get("displayName").toString());
    return map;
  }

  public List<DialogField> getDisplayList()
  {
    if (style.intValue() == 1) {
      return null;
    }
    if (StringUtil.isEmpty(displayfield)) {
      return new ArrayList();
    }
    List list = new ArrayList();
    JSONArray jsonArray = JSONArray.fromObject(displayfield);
    for (Iterator localIterator = jsonArray.iterator(); localIterator.hasNext(); ) { Object obj = localIterator.next();
      JSONObject jsonObj = (JSONObject)obj;
      String field = jsonObj.getString("field");
      String comment = jsonObj.getString("comment");

      DialogField dialogField = new DialogField();
      dialogField.setFieldName(field);
      dialogField.setComment(comment);
      list.add(dialogField);
    }
    return list;
  }

  public List<DialogField> getConditionList()
  {
    if (StringUtil.isEmpty(conditionfield)) {
      return new ArrayList();
    }
    List list = new ArrayList();
    JSONArray jsonArray = JSONArray.fromObject(conditionfield);
    for (Iterator localIterator = jsonArray.iterator(); localIterator.hasNext(); ) { Object obj = localIterator.next();
      JSONObject jsonObj = (JSONObject)obj;
      String field = jsonObj.getString("field");
      String comment = jsonObj.getString("comment");
      String condition = jsonObj.getString("condition");
      String dbType = jsonObj.getString("dbType");

      Integer defaultType = Integer.valueOf(1);
      Object objDefaultType = jsonObj.get("defaultType");
      if (objDefaultType != null) {
        defaultType = Integer.valueOf(Integer.parseInt(objDefaultType.toString()));
      }

      String defaultValue = (String)jsonObj.get("defaultValue");
      if (defaultValue == null) {
        defaultValue = "";
      }

      DialogField dialogField = new DialogField();
      dialogField.setFieldName(field);
      dialogField.setComment(comment);
      dialogField.setCondition(condition);
      dialogField.setFieldType(dbType);
      dialogField.setDefaultType(defaultType.intValue());
      dialogField.setDefaultValue(defaultValue);
      list.add(dialogField);
    }
    return list;
  }

  public List<DialogField> getReturnList()
  {
    if (StringUtil.isEmpty(resultfield)) {
      return new ArrayList();
    }
    List list = new ArrayList();
    JSONArray jsonArray = JSONArray.fromObject(resultfield);
    for (Iterator localIterator = jsonArray.iterator(); localIterator.hasNext(); ) { Object obj = localIterator.next();
      JSONObject jsonObj = (JSONObject)obj;
      String field = jsonObj.getString("field");
      String comment = jsonObj.getString("comment");
      DialogField dialogField = new DialogField();
      dialogField.setFieldName(field);
      dialogField.setComment(comment);
      list.add(dialogField);
    }
    return list;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId()
  {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getAlias()
  {
    return alias;
  }

  public void setStyle(Integer style) {
    this.style = style;
  }

  public Integer getStyle()
  {
    return style;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getWidth()
  {
    return width;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public Integer getHeight()
  {
    return height;
  }

  public void setIssingle(Integer issingle) {
    this.issingle = issingle;
  }

  public Integer getIssingle()
  {
    return issingle;
  }

  public void setNeedpage(Integer needpage) {
    this.needpage = needpage;
  }

  public Integer getNeedpage()
  {
    return needpage;
  }

  public void setIstable(Integer istable) {
    this.istable = istable;
  }

  public Integer getIstable()
  {
    return istable;
  }

  public void setObjname(String objname) {
    this.objname = objname;
  }

  public String getObjname()
  {
    return objname;
  }

  public void setDisplayfield(String displayfield) {
    this.displayfield = displayfield;
  }

  public String getDisplayfield()
  {
    return displayfield;
  }

  public void setConditionfield(String conditionfield) {
    this.conditionfield = conditionfield;
  }

  public String getConditionfield()
  {
    return conditionfield;
  }

  public String getReturnFields() {
    String s = "";

    JSONArray jArray = JSONArray.fromObject(resultfield);
    for (Iterator localIterator = jArray.iterator(); localIterator.hasNext(); ) { Object ob = localIterator.next();
      JSONObject jObject = JSONObject.fromObject(ob);
      s = s + jObject.get("field");
      s = s + ",";
    }
    return s;
  }

  public void setResultfield(String resultfield) {
    this.resultfield = resultfield;
  }

  public String getResultfield()
  {
    return resultfield.trim();
  }

  public void setDsname(String dsname) {
    this.dsname = dsname;
  }

  public String getDsname()
  {
    return dsname;
  }

  public void setDsalias(String dsalias) {
    this.dsalias = dsalias;
  }

  public String getDsalias()
  {
    return dsalias;
  }

  public Integer getPagesize()
  {
    return pagesize;
  }

  public void setPagesize(Integer pagesize)
  {
    this.pagesize = pagesize;
  }

  public List<Map<String, Object>> getList() {
    return list;
  }

  public void setList(List<Map<String, Object>> list) {
    this.list = list;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof BpmFormDialog)) {
      return false;
    }
    BpmFormDialog rhs = (BpmFormDialog)object;
    return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(alias, rhs.alias).append(style, rhs.style)
      .append(width, rhs.width).append(height, rhs.height).append(issingle, rhs.issingle).append(needpage, rhs.needpage)
      .append(istable, rhs.istable).append(objname, rhs.objname).append(displayfield, rhs.displayfield)
      .append(conditionfield, rhs.conditionfield).append(resultfield, rhs.resultfield).append(dsname, rhs.dsname)
      .append(dsalias, rhs.dsalias).isEquals();
  }

  public int hashCode()
  {
    return new HashCodeBuilder(-82280557, -700257973).append(id).append(name).append(alias).append(style).append(width)
      .append(height).append(issingle).append(needpage).append(istable).append(objname).append(displayfield)
      .append(conditionfield).append(resultfield).append(dsname).append(dsalias).toHashCode();
  }

  public String toString()
  {
    return new ToStringBuilder(this).append("id", id).append("name", name).append("alias", alias).append("style", style)
      .append("width", width).append("height", height).append("issingle", issingle).append("needpage", needpage)
      .append("istable", istable).append("objname", objname).append("displayfield", displayfield)
      .append("conditionfield", conditionfield).append("resultfield", resultfield).append("dsname", dsname)
      .append("dsalias", dsalias).toString();
  }
}