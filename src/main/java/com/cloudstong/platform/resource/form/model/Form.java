package com.cloudstong.platform.resource.form.model;

import java.util.List;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.core.util.EncryptUtil;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:表单
 */
public class Form extends EntityBase {

	/**
	 * 主键
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
	private String systemTeam = "bus";
	
	/**
	 * 表单名
	 */
	private String formName;

	/**
	 * 表单主表ID
	 */
	private Long tableId;
	
	/**
	 * 表单主表名称
	 */
	private String tableName;
	
	/**
	 * 主表的简短标识
	 */
	private String simpleModel;

	/**
	 * 表单主表中文名称
	 */
	private String tableChName;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 权限
	 */
	private String right;

	/**
	 * 是否有上传文件，1代表是，0代表否
	 */
	private Integer isUploadFile = 0;

	/**
	 * 功能说明
	 */
	private String remarks;

	/**
	 * 备注
	 */
	private String desc;

	/**
	 * 主表
	 */
	private String mainTable;

	/**
	 * 选项卡列表
	 */
	private List<Tab> tabs;

	/**
	 * 按钮列表
	 */
	private List<FormButton> formButtons;

	/**
	 * 表单元素列表
	 */
	private List<FormColumnExtend> formColumnExtends;

	/**
	 * 列表元素列表
	 */
	private List<FormColumnExtend> tabulationColumnExtends;
	
	/**
	 * 表单宽度
	 */
	private Integer width;
	
	/**
	 * 表单高度
	 */
	private Integer height;
	
	/**
	 * 新建标题
	 */
	private String xjTitle;
	
	/**
	 * 维护标题
	 */
	private String whTitle;
	
	/**
	 * 脚本设置
	 */
	private String jiaoben;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public Integer getIsUploadFile() {
		return isUploadFile;
	}

	public void setIsUploadFile(Integer isUploadFile) {
		this.isUploadFile = isUploadFile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}

	public List<FormButton> getFormButtons() {
		return formButtons;
	}

	public void setFormButtons(List<FormButton> formButtons) {
		this.formButtons = formButtons;
	}

	public List<FormColumnExtend> getFormColumnExtends() {
		return formColumnExtends;
	}

	public void setFormColumnExtends(List<FormColumnExtend> formColumnExtends) {
		this.formColumnExtends = formColumnExtends;
	}

	public List<FormColumnExtend> getTabulationColumnExtends() {
		return tabulationColumnExtends;
	}

	public void setTabulationColumnExtends(
			List<FormColumnExtend> tabulationColumnExtends) {
		this.tabulationColumnExtends = tabulationColumnExtends;
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

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableChName() {
		return tableChName;
	}

	public void setTableChName(String tableChName) {
		this.tableChName = tableChName;
	}

	public String getMainTable() {
		return mainTable;
	}

	public void setMainTable(String mainTable) {
		this.mainTable = mainTable;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getJiaoben() {
		return jiaoben;
	}

	public void setJiaoben(String jiaoben) {
		this.jiaoben = jiaoben;
	}

	public String getXjTitle() {
		return xjTitle;
	}

	public void setXjTitle(String xjTitle) {
		this.xjTitle = xjTitle;
	}

	public String getWhTitle() {
		return whTitle;
	}

	public void setWhTitle(String whTitle) {
		this.whTitle = whTitle;
	}

	public String getSimpleModel() {
		if(tableName != null) {
			return tableName;
		}else{
			return null;
		}
	}

	public void setSimpleModel(String simpleModel) {
		this.simpleModel = simpleModel;
	}
	

}
