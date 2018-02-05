package com.cloudstong.platform.third.bpm.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.core.util.UniqueIdUtil;

@XmlRootElement(name = "bpmNodeButton")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNodeButton extends EntityBase {

	@XmlAttribute
	protected Long id = Long.valueOf(0L);

	@XmlAttribute
	protected String actdefid;

	@XmlAttribute
	protected Long defId = Long.valueOf(0L);

	@XmlAttribute
	protected Integer isstartform = Integer.valueOf(0);

	@XmlAttribute
	protected String nodeid;

	@XmlAttribute
	protected String btnname;

	@XmlAttribute
	protected String iconclsname;

	@XmlAttribute
	protected Integer operatortype = Integer.valueOf(0);

	@XmlAttribute
	protected String prevscript;

	@XmlAttribute
	protected String afterscript;

	@XmlAttribute
	protected Integer nodetype = Integer.valueOf(-1);

	@XmlAttribute
	protected Long sn = Long.valueOf(0L);

	public BpmNodeButton() {
	}

	public BpmNodeButton(String actdefid, Long defId, String nodeid, String btnname, Integer operatortype, Integer nodetype) throws Exception {
		id = Long.valueOf(UniqueIdUtil.genId());
		this.actdefid = actdefid;
		this.defId = defId;
		this.nodeid = nodeid;
		this.btnname = btnname;
		this.operatortype = operatortype;
		this.nodetype = nodetype;
		isstartform = Integer.valueOf(0);
		sn = id;
	}

	public BpmNodeButton(String actdefid, Long defId, String btnname, Integer operatortype) throws Exception {
		id = Long.valueOf(UniqueIdUtil.genId());
		this.actdefid = actdefid;
		this.defId = defId;
		this.btnname = btnname;
		this.operatortype = operatortype;
		isstartform = Integer.valueOf(1);
		sn = id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setActdefid(String actdefid) {
		this.actdefid = actdefid;
	}

	public String getActdefid() {
		return actdefid;
	}

	public Long getDefId() {
		return defId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public void setIsstartform(Integer isstartform) {
		this.isstartform = isstartform;
	}

	public Integer getIsstartform() {
		return isstartform;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setBtnname(String btnname) {
		this.btnname = btnname;
	}

	public String getBtnname() {
		return btnname;
	}

	public void setIconclsname(String iconclsname) {
		this.iconclsname = iconclsname;
	}

	public String getIconclsname() {
		return iconclsname;
	}

	public void setOperatortype(Integer operatortype) {
		this.operatortype = operatortype;
	}

	public Integer getOperatortype() {
		return operatortype;
	}

	public void setPrevscript(String prevscript) {
		this.prevscript = prevscript;
	}

	public String getPrevscript() {
		return prevscript;
	}

	public void setAfterscript(String afterscript) {
		this.afterscript = afterscript;
	}

	public String getAfterscript() {
		return afterscript;
	}

	public void setNodetype(Integer nodetype) {
		this.nodetype = nodetype;
	}

	public Integer getNodetype() {
		return nodetype;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public Long getSn() {
		return sn;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeButton)) {
			return false;
		}
		BpmNodeButton rhs = (BpmNodeButton) object;
		return new EqualsBuilder().append(id, rhs.id).append(actdefid, rhs.actdefid).append(isstartform, rhs.isstartform).append(nodeid, rhs.nodeid)
				.append(btnname, rhs.btnname).append(iconclsname, rhs.iconclsname).append(operatortype, rhs.operatortype)
				.append(prevscript, rhs.prevscript).append(afterscript, rhs.afterscript).append(nodetype, rhs.nodetype).append(sn, rhs.sn).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(actdefid).append(isstartform).append(nodeid).append(btnname)
				.append(iconclsname).append(operatortype).append(prevscript).append(afterscript).append(nodetype).append(sn).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("actdefid", actdefid).append("isstartform", isstartform).append("nodeid", nodeid)
				.append("btnname", btnname).append("iconclsname", iconclsname).append("operatortype", operatortype).append("prevscript", prevscript)
				.append("afterscript", afterscript).append("nodetype", nodetype).append("sn", sn).toString();
	}
}