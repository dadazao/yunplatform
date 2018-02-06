/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.system.model.SysLog;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysLogService;

/**
 * @author michael
 * Created on 2012-12-6
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:系统日志Action
 * 
 */

@ParentPackage("default")
@Namespace("/pages/system/syslog")
@Results(value = { 
		@Result(name="list", location = "/WEB-INF/view/system/log/compexLogList.jsp"),
		@Result(name="view", location = "/WEB-INF/view/system/log/compexLogView.jsp"),
		@Result(name="loglist", location = "/WEB-INF/view/system/log/logList.jsp")
})
public class SysLogAction extends CompexDomainAction {

	private static final long serialVersionUID = -3685313333678447912L;
	
	private SysLog sysLog;
	
	private String year;
	
	private String month;
	
	/**
	 * 操作系统日志的服务接口,<code>sysLogService</code> 对象是SysLogService接口的一个实例
	 */
	@Resource
	private SysLogService sysLogService;
	
	@Action("list")
	public String list() {
		return super.list();
	}
	
	@Action("view")
	public String view() throws Exception {
		return super.view();
	}
	
	@Action("savalog")
	public String savelog(){
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		String catalogId = getRequest().getParameter("catalogId");
		
		SysLog sysLog=new SysLog();
		sysLog.setOperator(user.getId());
		sysLog.setIp(user.getIp());
		sysLog.setOperationModule(catalogId);
		sysLog.setOperationContent("3");
		sysLog.setOperationTime(new Date(System.currentTimeMillis()));
		sysLogService.doSaveLog(sysLog);
		return NONE;
	}
	
	@Action("loglist")
	public String querylog(){
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1) * this.numPerPage + 1);
		queryCriteria.setPageSize(this.numPerPage);
		if(sysLog!=null){
			if(sysLog.getOperatorName()!=null && !sysLog.getOperatorName().equals("")){
				queryCriteria.addQueryCondition("tbl_username", "%"+sysLog.getOperatorName()+"%");
			}
			if(sysLog.getIp()!=null && !sysLog.getIp().equals("")){
				queryCriteria.addQueryCondition("tbl_loginip", "%"+sysLog.getIp()+"%");
			}
			if(sysLog.getOperationContent()!=null && !sysLog.getOperationContent().equals("")){
				queryCriteria.addQueryCondition("tbl_operation", "%"+sysLog.getOperationContent()+"%");
			}
		}
		String logTableName = initLogTableName(year,month);
		//判断表是否存在
		List<SysLog> logList = new ArrayList<SysLog>();
		int count = 0;
		Boolean b = sysLogService.checkTableExists(logTableName);
		if(b){
			logList = sysLogService.queryLog(queryCriteria,logTableName);
			count = sysLogService.countLog(queryCriteria,logTableName);
		}
		this.pageResult = new PageResult();
		this.pageResult.setContent(logList);
		this.pageResult.setTotalCount(count);
		this.pageResult.setPageSize(this.numPerPage);
		this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
		this.pageResult.setCountPage((logList.size() + this.numPerPage - 1) / this.numPerPage);
		return "loglist";
	}

	private String initLogTableName(String _year, String _month) {
		Date now = new Date(System.currentTimeMillis());
		String nowStr = new SimpleDateFormat("yyyyMM").format(now);
		String _tableName = "sys_log";
		if(_year!=null && !_year.equals("")){
			_tableName+=_year;
		}else{
			_tableName+=nowStr.substring(0,4);
		}
		if(_month!=null && !_month.equals("")){
			_tableName+=_month;
		}else{
			_tableName+=nowStr.substring(4);
		}
		return _tableName;
	}

	public SysLog getSysLog() {
		return sysLog;
	}

	public void setSysLog(SysLog sysLog) {
		this.sysLog = sysLog;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
}
