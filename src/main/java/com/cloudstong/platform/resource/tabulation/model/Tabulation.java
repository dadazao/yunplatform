package com.cloudstong.platform.resource.tabulation.model;

import java.util.List;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.resource.listcontrol.model.ListControl;
import com.cloudstong.platform.resource.querycontrol.model.AdvanceQueryControl;
import com.cloudstong.platform.resource.querycontrol.model.QueryControl;

/**
 * @author michael
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表
 */
public class Tabulation extends EntityBase {
	
	private static final long serialVersionUID = 2807750080162007022L;

	/**
	 * 唯一标示ID
	 */
	private Long id;

	/**
	 * 创建人名字
	 */
	private String userName;

	/**
	 * 修改人名字
	 */
	private String updateName;

	/**
	 * 所属分组
	 */
	private String systemTeam;
	
	/**
	 * 列表名
	 */
	private String tabulationName;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 权限
	 */
	private String right;

	/**
	 * 功能说明
	 */
	private String remarks;

	/**
	 * 备注
	 */
	private String desc;

	/**
	 * 所属列表模版ID
	 */
	private Integer templateListId;

	/**
	 * 所属表ID
	 */
	private Long tableId;

	/**
	 * 所属表名
	 */
	private String tableName;

	/**
	 * 所属表中文名
	 */
	private String tableZhName;

	/**
	 * 所属列表模版名称
	 */
	private String templateListName;

	/**
	 * 列表模板文件名
	 */
	private String templateFileName;

	/**
	 * 是否选择 1：是 0：否
	 */
	private String isSelect = "1";

	/**
	 * 是否维护 1：是 0：否
	 */
	private String isModify = "1"; 
	/**
	 * 是否有序号 1：是 0：否
	 */
	private String isNumber = "1";

	/**
	 * 维护字段名称
	 */
	private String modifyName = "维护";

	/**
	 * 关联表单ID
	 */
	private Long formId;

	/**
	 * 关联表单名
	 */
	private String formName;

	/**
	 * 总记录条数
	 */
	private Integer totalCount = 0;

	/**
	 * 列表路径
	 */
	private String tabulationPath;

	/**
	 * 关联的列表组件
	 */
	private Long listControlId;

	/**
	 * 关联的查询组件
	 */
	private Long queryControlId;
	
	/**
	 * 关联的查询组件
	 */
	private Long advanceQueryControlId;

	/**
	 * 列表操作按钮
	 */
	private List<TabulationOpt> tabulationOpts;

	/**
	 * 按钮或按钮组
	 */
	private List<TabulationButton> tabulationButtons;

	/**
	 * 表头字段
	 */
	private List<FormColumnExtend> tabulationColumnExtends;
	
	/**
	 * 高级查询字段
	 */
	private List<FormColumnExtend> queryColumnExtends;

	/**
	 * 数据列表
	 */
	private List domains;

	/**
	 * 列表组件
	 */
	private ListControl listControl;

	/**
	 * 查询组件
	 */
	private QueryControl queryControl;
	
	/**
	 * 高级查询组件
	 */
	private AdvanceQueryControl advanceQueryControl;

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public List<TabulationOpt> getTabulationOpts() {
		return tabulationOpts;
	}

	public void setTabulationOpts(List<TabulationOpt> tabulationOpts) {
		this.tabulationOpts = tabulationOpts;
	}

	public List<TabulationButton> getTabulationButtons() {
		return tabulationButtons;
	}

	public void setTabulationButtons(List<TabulationButton> tabulationButtons) {
		this.tabulationButtons = tabulationButtons;
	}

	public List getDomains() {
		return domains;
	}
	
	public AdvanceQueryControl getAdvanceQueryControl() {
		return advanceQueryControl;
	}

	public void setAdvanceQueryControl(AdvanceQueryControl advanceQueryControl) {
		this.advanceQueryControl = advanceQueryControl;
	}

	public void setDomains(List domains) {
		this.domains = domains;
	}

	public Tabulation() {
		super();
	}

	public String getTabulationPath() {
		return tabulationPath;
	}

	public void setTabulationPath(String tabulationPath) {
		this.tabulationPath = tabulationPath;
	}

	public String getTemplateListName() {
		return templateListName;
	}

	public void setTemplateListName(String templateListName) {
		this.templateListName = templateListName;
	}

	public String getTableZhName() {
		return tableZhName;
	}

	public void setTableZhName(String tableZhName) {
		this.tableZhName = tableZhName;
	}

	public Integer getTemplateListId() {
		return templateListId;
	}

	public void setTemplateListId(Integer templateListId) {
		this.templateListId = templateListId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTabulationName() {
		return tabulationName;
	}

	public void setTabulationName(String tabulationName) {
		this.tabulationName = tabulationName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public List<FormColumnExtend> getTabulationColumnExtends() {
		return tabulationColumnExtends;
	}

	public void setTabulationColumnExtends(
			List<FormColumnExtend> tabulationColumnExtends) {
		this.tabulationColumnExtends = tabulationColumnExtends;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getIsNumber() {
		return isNumber;
	}

	public void setIsNumber(String isNumber) {
		this.isNumber = isNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getSystemTeam() {
		return systemTeam;
	}

	public void setSystemTeam(String systemTeam) {
		this.systemTeam = systemTeam;
	}

	public ListControl getListControl() {
		return listControl;
	}

	public void setListControl(ListControl listControl) {
		this.listControl = listControl;
	}

	public QueryControl getQueryControl() {
		return queryControl;
	}

	public void setQueryControl(QueryControl queryControl) {
		this.queryControl = queryControl;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getListControlId() {
		return listControlId;
	}

	public void setListControlId(Long listControlId) {
		this.listControlId = listControlId;
	}

	public Long getQueryControlId() {
		return queryControlId;
	}

	public void setQueryControlId(Long queryControlId) {
		this.queryControlId = queryControlId;
	}

	public Long getAdvanceQueryControlId() {
		return advanceQueryControlId;
	}

	public void setAdvanceQueryControlId(Long advanceQueryControlId) {
		this.advanceQueryControlId = advanceQueryControlId;
	}

	public List<FormColumnExtend> getQueryColumnExtends() {
		return queryColumnExtends;
	}

	public void setQueryColumnExtends(List<FormColumnExtend> queryColumnExtends) {
		this.queryColumnExtends = queryColumnExtends;
	}

}
