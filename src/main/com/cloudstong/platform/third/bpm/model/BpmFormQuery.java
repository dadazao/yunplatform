package com.cloudstong.platform.third.bpm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.core.util.StringUtil;

public class BpmFormQuery extends EntityBase {
	protected Long id = Long.valueOf(0L);

	protected String name = "";

	protected String alias = "";

	protected String objName = "";

	protected Long needpage = Long.valueOf(0L);

	protected String conditionfield = "";

	protected String resultfield = "";

	protected String dsname = "";

	protected String dsalias = "";

	protected Long pagesize = Long.valueOf(10L);

	protected Integer isTable = Integer.valueOf(1);

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getObjName() {
		return objName;
	}

	public void setNeedpage(Long needpage) {
		this.needpage = needpage;
	}

	public Long getNeedpage() {
		return needpage;
	}

	public void setConditionfield(String conditionfield) {
		this.conditionfield = conditionfield;
	}

	public String getConditionfield() {
		return conditionfield;
	}

	public void setResultfield(String resultfield) {
		this.resultfield = resultfield;
	}

	public String getResultfield() {
		return resultfield;
	}

	public void setDsname(String dsname) {
		this.dsname = dsname;
	}

	public String getDsname() {
		return dsname;
	}

	public void setDsalias(String dsalias) {
		this.dsalias = dsalias;
	}

	public String getDsalias() {
		return dsalias;
	}

	public void setPagesize(Long pagesize) {
		this.pagesize = pagesize;
	}

	public Long getPagesize() {
		return pagesize;
	}

	public Integer getIsTable() {
		return isTable;
	}

	public void setIsTable(Integer isTable) {
		this.isTable = isTable;
	}

//	public List<DialogField> getConditionList() {
//		if (StringUtil.isEmpty(conditionfield)) {
//			return new ArrayList();
//		}
//		List list = new ArrayList();
//		JSONArray jsonArray = JSONArray.fromObject(conditionfield);
//		for (Iterator localIterator = jsonArray.iterator(); localIterator.hasNext();) {
//			Object obj = localIterator.next();
//			JSONObject jsonObj = (JSONObject) obj;
//			String field = jsonObj.getString("field");
//			String comment = jsonObj.getString("comment");
//			String condition = jsonObj.getString("condition");
//			String dbType = jsonObj.getString("dbType");
//
//			Integer defaultType = Integer.valueOf(1);
//			Object objDefaultType = jsonObj.get("defaultType");
//			if (objDefaultType != null) {
//				defaultType = Integer.valueOf(Integer.parseInt(objDefaultType.toString()));
//			}
//
//			String defaultValue = (String) jsonObj.get("defaultValue");
//			if (defaultValue == null) {
//				defaultValue = "";
//			}
//
//			DialogField dialogField = new DialogField();
//			dialogField.setFieldName(field);
//			dialogField.setComment(comment);
//			dialogField.setCondition(condition);
//			dialogField.setFieldType(dbType);
//			dialogField.setDefaultType(defaultType.intValue());
//			dialogField.setDefaultValue(defaultValue);
//			list.add(dialogField);
//		}
//		return list;
//	}
//
//	public List<DialogField> getReturnList() {
//		if (StringUtil.isEmpty(resultfield)) {
//			return new ArrayList();
//		}
//		List list = new ArrayList();
//		JSONArray jsonArray = JSONArray.fromObject(resultfield);
//		for (Iterator localIterator = jsonArray.iterator(); localIterator.hasNext();) {
//			Object obj = localIterator.next();
//			JSONObject jsonObj = (JSONObject) obj;
//			String field = jsonObj.getString("field");
//			String comment = jsonObj.getString("comment");
//			DialogField dialogField = new DialogField();
//			dialogField.setFieldName(field);
//			dialogField.setComment(comment);
//			list.add(dialogField);
//		}
//		return list;
//	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormQuery)) {
			return false;
		}
		BpmFormQuery rhs = (BpmFormQuery) object;
		return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(alias, rhs.alias).append(objName, rhs.objName)
				.append(needpage, rhs.needpage).append(conditionfield, rhs.conditionfield).append(resultfield, rhs.resultfield)
				.append(dsname, rhs.dsname).append(dsalias, rhs.dsalias).append(pagesize, rhs.pagesize).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(name).append(alias).append(objName).append(needpage)
				.append(conditionfield).append(resultfield).append(dsname).append(dsalias).append(pagesize).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("name", name).append("alias", alias).append("objName", objName)
				.append("needpage", needpage).append("conditionfield", conditionfield).append("resultfield", resultfield).append("dsname", dsname)
				.append("dsalias", dsalias).append("pagesize", pagesize).toString();
	}
	
}