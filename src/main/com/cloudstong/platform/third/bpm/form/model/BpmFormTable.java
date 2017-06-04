package com.cloudstong.platform.third.bpm.form.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.dom4j.Document;
import org.dom4j.Element;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.core.util.Dom4jUtil;
import com.cloudstong.platform.core.util.StringUtil;

@XmlRootElement(name = "table")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmFormTable extends EntityBase {
	public static String ParElmName = "table";

	public static String SubElmName = "subTable";

	@XmlAttribute
	protected Long tableId = Long.valueOf(0L);

	@XmlAttribute
	protected String tableName = "";

	@XmlAttribute
	protected String tableDesc = "";

	@XmlAttribute
	protected Short isMain = Short.valueOf((short) 1);

	protected Long mainTableId = Long.valueOf(0L);

	@XmlAttribute
	protected Short isPublished = Short.valueOf((short) 0);

	protected String publishedBy = "";
	protected Date publishTime;
	protected int isExternal = 0;

	protected String dsAlias = "";

	protected String dsName = "";

	protected String relation = "";

	protected Short keyType = Short.valueOf((short) 0);

	protected String keyValue = "";

	protected String pkField = "";

	protected String listTemplate = "";

	protected String detailTemplate = "";

	protected Integer hasForm = Integer.valueOf(0);

	protected Integer genByForm = Integer.valueOf(0);

	protected Set<String> hashSet = new HashSet();

	protected List<BpmFormField> fieldList = new ArrayList();

	protected List<BpmFormTable> subTableList = new ArrayList();
	protected Long parentId;
	protected Short isRoot;
	protected Map<String, String> variable = new HashMap();

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Short getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(Short isRoot) {
		this.isRoot = isRoot;
	}

	public Map<String, String> getVariable() {
		return variable;
	}

	public void setVariable(Map<String, String> variable) {
		this.variable = variable;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setIsMain(Short isMain) {
		this.isMain = isMain;
	}

	public Short getIsMain() {
		return isMain;
	}

	public void setMainTableId(Long mainTableId) {
		this.mainTableId = mainTableId;
	}

	public Long getMainTableId() {
		return mainTableId;
	}

	public void setIsPublished(Short isPublished) {
		this.isPublished = isPublished;
	}

	public Short getIsPublished() {
		return isPublished;
	}

	public void setPublishedBy(String publishedBy) {
		this.publishedBy = publishedBy;
	}

	public String getPublishedBy() {
		return publishedBy;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public int getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(int isExternal) {
		this.isExternal = isExternal;
	}

	public String getDsAlias() {
		return dsAlias;
	}

	public void setDsAlias(String dsAlias) {
		this.dsAlias = dsAlias;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public Short getKeyType() {
		return keyType;
	}

	public void setKeyType(Short keyType) {
		this.keyType = keyType;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getPkField() {
		return pkField;
	}

	public void setPkField(String pkField) {
		this.pkField = pkField;
	}

	public String getListTemplate() {
		return listTemplate;
	}

	public void setListTemplate(String listTemplate) {
		this.listTemplate = listTemplate;
	}

	public String getDetailTemplate() {
		return detailTemplate;
	}

	public void setDetailTemplate(String detailTemplate) {
		this.detailTemplate = detailTemplate;
	}

	public Integer getHasForm() {
		return hasForm;
	}

	public void setHasForm(Integer hasForm) {
		this.hasForm = hasForm;
	}

	public TableRelation getTableRelation() {
		if (isExternal == 0)
			return null;
		if (StringUtil.isEmpty(relation))
			return null;
		return getRelationsByXml(relation);
	}

	public static TableRelation getRelationsByXml(String relationXml) {
		if (StringUtil.isEmpty(relationXml))
			return null;
		Document dom = Dom4jUtil.loadXml(relationXml);
		Element root = dom.getRootElement();
		String pk = root.attributeValue("pk");
		TableRelation tableRelation = new TableRelation(pk);
		Iterator tbIt = root.elementIterator();
		while (tbIt.hasNext()) {
			Element elTb = (Element) tbIt.next();
			String tbName = elTb.attributeValue("name");
			String fk = elTb.attributeValue("fk");
			tableRelation.addRelation(tbName, fk);
		}
		return tableRelation;
	}

	public static String removeTable(String relationXml, String tbName) {
		Document dom = Dom4jUtil.loadXml(relationXml);
		Element root = dom.getRootElement();
		List<Element> list = root.elements();
		for (Element el : list) {
			String name = el.attributeValue("name");
			if (name.equals(tbName)) {
				root.remove(el);
				break;
			}
		}
		list = root.elements();
		if (list.size() == 0)
			return "";
		return root.asXML();
	}

	public boolean addField(BpmFormField field) {
		String fieldName = field.getFieldName().toLowerCase();
		short controlType = field.getControlType().shortValue();

		if (hashSet.contains(fieldName)) {
			if ((controlType == 13) || (controlType == 14)) {
				return true;
			}
			return false;
		}
		hashSet.add(fieldName);
		fieldList.add(field);
		return true;
	}

	public List<BpmFormField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<BpmFormField> fieldList) {
		this.fieldList = fieldList;
	}

	public List<BpmFormTable> getSubTableList() {
		return subTableList;
	}

	public void setSubTableList(List<BpmFormTable> subTableList) {
		this.subTableList = subTableList;
	}

	public Integer getGenByForm() {
		return genByForm;
	}

	public void setGenByForm(Integer genByForm) {
		this.genByForm = genByForm;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormTable)) {
			return false;
		}
		BpmFormTable rhs = (BpmFormTable) object;
		return new EqualsBuilder().append(tableId, rhs.tableId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(tableId).append(tableName).append(tableDesc).append(isMain).append(mainTableId)
				.append(isPublished).append(publishedBy).append(publishTime).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("tableId", tableId).append("tableName", tableName).append("tableDesc", tableDesc)
				.append("isMain", isMain).append("mainTableId", mainTableId).append("isPublished", isPublished).append("publishedBy", publishedBy)
				.append("publishTime", publishTime).toString();
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub need to be implemented.
		
	}
}