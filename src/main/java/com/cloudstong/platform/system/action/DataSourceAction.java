/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.action;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.metadata.service.CompexDomainService;
import com.cloudstong.platform.system.model.DataSourcePojo;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.DataSourceService;

/**
 * @author liuqi
 * 
 *         Created on 2014-10-9 15:14:38
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/system/dataSource")
@Results(value = { 
	@Result(name = "list", location = "/WEB-INF/view/system/dataSource/datasourceList.jsp"),
	@Result(name = "add", location = "/WEB-INF/view/system/dataSource/datasourceEdit.jsp"),
	@Result(name = "edit", location = "/WEB-INF/view/system/dataSource/datasourceEdit.jsp"),
	@Result(name = "view", location = "/WEB-INF/view/system/dataSource/datasourceView.jsp")
})
public class DataSourceAction extends BaseAction {

	@Resource
	protected CompexDomainService compexDomainService;
	
	@Resource
	private DataSourceService idataSourceService;
	
	@Resource
	private DictionaryService dictionaryService;
	
	private DataSourcePojo dataSourcePojo;
	
	private Long[] dataSourceIDs;
	
	private List<Dictionary> dbTypes;
	
	private List<Dictionary> dbEncodings;
	
	
	@Action("save")
	public String save() throws Exception{
	    try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			Long id = idataSourceService.doSaveDataSource(dataSourcePojo,user);
			printJSON("success", false, id.toString());
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
	    return NONE;
	}
	
	@Action("edit")
	public String edit() {
	    this.dataSourcePojo = idataSourceService.findDataSourceById(dataSourcePojo.getId());
	    dbTypes = dictionaryService.queryByParent(541570363L);
		dbEncodings = dictionaryService.queryByParent(101505931269L);
		return "edit";
	}
	
	@Action("delete")
	public String delete() throws IOException {
		try {
			if (dataSourceIDs != null) {
				idataSourceService.doDeleteDataSources(dataSourceIDs);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		}
		return NONE;
	}
	
	@Action("view")
	public String view() {
		this.dataSourcePojo = idataSourceService.findDataSourceById(dataSourcePojo.getId());
		dbTypes = dictionaryService.queryByParent(541570363L);
		dbEncodings = dictionaryService.queryByParent(101505931269L);
		return "view";
	}
	
	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if(dataSourcePojo != null) {
			if(dataSourcePojo.getDsName()!=null && !dataSourcePojo.getDsName().equals("")){
				queryCriteria.addQueryCondition("tbl_dsName", "%"+dataSourcePojo.getDsName()+"%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		
		//列表按更新时间降序排序
		queryCriteria.setOrderField(Constants.JDBC_DEFAULT_SORT_FIELD);
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);
		
		this.pageResult = idataSourceService.queryDataSource(queryCriteria);
		
		return "list";
	}
	
	@Action("add")
	public String add() {
		dataSourcePojo = new DataSourcePojo();
		dbTypes = dictionaryService.queryByParent(541570363L);
		dbEncodings = dictionaryService.queryByParent(101505931269L);
		return "add";
	}
	
	@Action("isDefault")
	public String isDefault(){
		compexDomainService.doDefault("sys_datasources", "tbl_dsStatus", dataSourcePojo.getId());
		printJSON("success", "设置默认成功", true);
		return NONE;
	}
	
	public DataSourcePojo getDataSourcePojo() {
		return dataSourcePojo;
	}

	public void setDataSourcePojo(DataSourcePojo dataSourcePojo) {
		this.dataSourcePojo = dataSourcePojo;
	}
	
	public Long[] getDataSourceIDs() {
		return dataSourceIDs;
	}

	public void setDataSourceIDs(Long[] dataSourceIDs) {
		this.dataSourceIDs = dataSourceIDs;
	}

	public List<Dictionary> getDbTypes() {
		return dbTypes;
	}

	public void setDbTypes(List<Dictionary> dbTypes) {
		this.dbTypes = dbTypes;
	}

	public List<Dictionary> getDbEncodings() {
		return dbEncodings;
	}

	public void setDbEncodings(List<Dictionary> dbEncodings) {
		this.dbEncodings = dbEncodings;
	}

}