package com.cloudstong.platform.core.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.system.model.SysUser;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Allan Created on 2012-11-13
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:
 *         所有action的基类，定义基本属性，如分页信息、查询条件、操作、id等，定义了基本的方法，如获得request、
 *         response、和返回json字符串等操作。
 * 
 */
public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 3525445612504421307L;

	/**
	 * <code>log</code> 日志实例
	 */
	protected transient final Log log = LogFactory.getLog(getClass());
	
//	protected transient final Log log = LogFactory.getLog("business");

	/**
	 * <code>successMsg</code> 成功消息字符串
	 */
	private String successMsg;

	/**
	 * <code>queryCriteria</code> 查询条件
	 */
	protected QueryCriteria queryCriteria = new QueryCriteria();

	/**
	 * <code>pageResult</code> 返回结果.
	 */
	protected PageResult pageResult;

	/**
	 * <code>operationResult</code> 是否操作结果.
	 */
	protected boolean operationResult;

	/**
	 * <code>start</code> 查询起始位置.
	 */
	protected Integer start;

	/**
	 * <code>limit</code> 查询记录数.
	 */
	protected Integer limit;

	/**
	 * The <code>total</code> 总记录条数.
	 */
	protected Integer total = 0;

	/**
	 * <code>pageNum</code> 页数.
	 */
	protected Integer pageNum = 1;

	/**
	 * <code>numPerPage</code> 每页条数,默认20条.
	 */
	protected Integer numPerPage = 20;

	/**
	 * The <code>selectedIDs</code> 多选ID.
	 */
	protected Long[] selectedIDs;

	/**
	 * <code>selectedSubIDs</code> 子列表多选ID.
	 */
	protected Long[] selectedSubIDs;

	/**
	 * <code>op</code> 操作类型.
	 */
	protected String op;

	/**
	 * 查询类型
	 */
	protected String queryType;

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public Long[] getSelectedIDs() {
		return selectedIDs;
	}

	public void setSelectedIDs(Long[] selectedIDs) {
		this.selectedIDs = selectedIDs;
	}

	public Long[] getSelectedSubIDs() {
		return selectedSubIDs;
	}

	public void setSelectedSubIDs(Long[] selectedSubIDs) {
		this.selectedSubIDs = selectedSubIDs;
	}

	public PageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}

	public QueryCriteria getQueryCriteria() {
		return queryCriteria;
	}

	public void setQueryCriteria(QueryCriteria queryCriteria) {
		this.queryCriteria = queryCriteria;
	}

	public boolean isOperationResult() {
		return operationResult;
	}

	public void setOperationResult(boolean operationResult) {
		this.operationResult = operationResult;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	/**
	 * Convenience method to get the request
	 * 
	 * @return current request
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * Convenience method to get the response
	 * 
	 * @return current response
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected PrintWriter getWriter(String contentType, String characterEncoding) {
		try {
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding(characterEncoding);
			response.setContentType(contentType);
			return response.getWriter();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected PrintWriter getWriter(String contentType) {
		return getWriter(contentType, "UTF-8");
	}

	protected PrintWriter getWriter() {
		return getWriter("text/plain", "UTF-8");
	}

	/**
	 * Convenience method to get the session. This will create a session if one
	 * doesn't exist.
	 * 
	 * @return the session from the request (request.getSession()).
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected void printJSON(String statusCode) {
		String message = "";
		if ("success".equalsIgnoreCase(statusCode) || "200".equals(statusCode)) {
			statusCode = "200";
			message = "操作成功";
		} else if ("fail".equalsIgnoreCase(statusCode)) {
			statusCode = "300";
			message = "操作失败";
		} else {
			message = "操作失败";
		}
		printJSON(statusCode, message, false, null, null);
	}

	protected void printJSON(String statusCode, boolean isClose) {
		printJSON(statusCode, isClose, null);
	}

	protected void printJSON(String statusCode, boolean isClose, String domainId) {
		String message = "";
		if ("success".equalsIgnoreCase(statusCode) || "200".equals(statusCode)) {
			statusCode = "200";
			message = "操作成功";
		} else if ("fail".equalsIgnoreCase(statusCode)) {
			statusCode = "300";
			message = "操作失败";
		} else {
			message = "操作失败";
		}
		printJSON(statusCode, message, isClose, domainId, null);
	}

	protected void printJSON(String statusCode, boolean isClose, String domainId, String version) {
		String message = "";
		if ("success".equalsIgnoreCase(statusCode) || "200".equals(statusCode)) {
			statusCode = "200";
			message = "操作成功";
		} else if ("fail".equalsIgnoreCase(statusCode)) {
			statusCode = "300";
			message = "操作失败";
		} else {
			message = "操作失败";
		}
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("version", version);
		printJSON(statusCode, message, isClose, domainId, args);
	}

	protected void printJSON(String statusCode, String message) {
		printJSON(statusCode, message, false, null, null);
	}

	protected void printJSON(String statusCode, String message, boolean isClose) {
		printJSON(statusCode, message, isClose, null, null);
	}

	protected void printJSON(String statusCode, String message, boolean isClose, String domainId) {
		printJSON(statusCode, message, isClose, domainId, null);
	}

	protected void printJSON(String statusCode, String message, boolean isClose, String domainId, Map<String, Object> args) {
		if ("success".equalsIgnoreCase(statusCode)) {
			statusCode = "200";
		} else if ("fail".equalsIgnoreCase(statusCode)) {
			statusCode = "300";
		}

		if (args != null) {
			args.put("statusCode", statusCode != null ? statusCode : "");
			args.put("message", message != null ? message : "");
			args.put("callbackType", isClose ? "closeCurrent" : "");
			args.put("domainId", domainId != null ? domainId : "");
			args.put("navTabId", "main");
		} else {
			args = new HashMap<String, Object>();
			args.put("statusCode", statusCode != null ? statusCode : "");
			args.put("message", message != null ? message : "");
			args.put("callbackType", isClose ? "closeCurrent" : "");
			args.put("domainId", domainId != null ? domainId : "");
			args.put("navTabId", "main");
		}

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			objectMapper.writeValue(out, args);
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void printJSON(String statusCode, String message, boolean isClose, Map<String, Object> args) {
		if ("success".equalsIgnoreCase(statusCode)) {
			statusCode = "200";
		} else if ("fail".equalsIgnoreCase(statusCode)) {
			statusCode = "300";
		}

		if (args != null) {
			args.put("statusCode", statusCode != null ? statusCode : "");
			args.put("message", message != null ? message : "");
			args.put("callbackType", isClose ? "closeCurrent" : "");
			args.put("navTabId", "main");
		} else {
			args = new HashMap<String, Object>();
			args.put("statusCode", statusCode != null ? statusCode : "");
			args.put("message", message != null ? message : "");
			args.put("callbackType", isClose ? "closeCurrent" : "");
			args.put("navTabId", "main");
		}
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			objectMapper.writeValue(out, args);
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void printSuccess(EntityBase entity) {
		String clazzSimpleName = entity.getClass().getSimpleName();
		String objectName = clazzSimpleName.substring(0, 1).toLowerCase() + clazzSimpleName.substring(1);
		String versionName = objectName + "OptCounter";
		String idName = objectName + "Id";
		Map<String, Object> map = new HashMap<String, Object>(4);
		map.put(idName, entity.getId());
		map.put("domainId", entity.getId());
		map.put(versionName, entity.getOptCounter());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("domainObject", map);
		printJSON("success", "操作成功", false, result);
	}

	protected void printObject(Object obj) {
		PrintWriter writer = getWriter("text/plain", "UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(writer, obj);
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected SysUser getUser() {
		return (SysUser) getSession().getAttribute("user");
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

}
