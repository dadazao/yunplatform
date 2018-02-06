package com.cloudstong.platform.resource.date.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.resource.date.service.DateService;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;

/**
 * @author liuqi
 *
 */
@ParentPackage("default")
@Namespace("/pages/resource/date")
@Results(value = { 
		@Result(name="list", location = "/WEB-INF/view/resource/date/compexDateList.jsp"),
		@Result(name="add", location = "/WEB-INF/view/resource/date/compexDateEdit.jsp"),
		@Result(name="edit", location = "/WEB-INF/view/resource/date/compexDateEdit.jsp"),
		@Result(name="view", location = "/WEB-INF/view/resource/date/compexDateView.jsp"),
		@Result(name="useinfoList", location = "/WEB-INF/view/resource/date/dateyyList.jsp")
})
public class DateAction extends CompexDomainAction {

	/**
	 * 日期组件ID
	 */
	private String dateId = "";

	@Resource
	private DateService dateService;
	
	@Action("list")
	public String list() {
		return super.list();
	}
	
	@Action("add")
	public String add() {
		return super.add();
	}
	
	@Action("edit")
	public String edit() {
		return super.add();
	}
	
	@Action("view")
	public String view() throws Exception {
		return super.view();
	}
	
	@Action("del")
	public String delete() throws Exception {
		return super.delete();
	}
	
	@Action("dataJson")
	public String dataJson() throws Exception {
		return super.dataJson();
	}
	
	@Action("viewJson")
	public String viewJson() throws Exception {
		return super.viewJson();
	}
	
	@Action("singleDelete") 
	public String singleDelete() throws Exception {
		return super.simpleDelete();
	}
	
	@Action("logicDelete") 
	public String logicDelete() throws IOException  {
		return super.logicDelete();
	}

	private String parseParams() {
		if (this.params == null || this.params.isEmpty()) {
			return null;
		}
		String temp = this.params;
		String ids = temp.substring(temp.indexOf(":") + 1, temp.length() - 1);
		return ids;
	}

	/**
	 * 录入类型包含日期组件的表单列表
	 * @return forward
	 * @throws Exception
	 */
	@Action("formlist")
	public String formlist() throws Exception {
		this.dateId = parseParams();

		this.numPerPage = 10;
		queryCriteria = new QueryCriteria();
		if (dateId != null && !dateId.equals("")) {
			queryCriteria.addQueryCondition("compexid", dateId);
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1)
				* this.numPerPage + 1);
		queryCriteria.setPageSize(this.numPerPage);
		List forms = null;
		try {
			forms = dateService.queryFormList(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.pageResult = new PageResult();
		this.pageResult.setContent(forms);
		this.pageResult.setTotalCount(dateService.countFormList(queryCriteria));
		this.pageResult.setPageSize(this.numPerPage);
		this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
		this.pageResult.setCountPage((forms.size() + this.numPerPage - 1)
				/ this.numPerPage);
		return "useinfoList";
	}

	/**
	 * 日期组件是否可以删除，若被应用则不能删除
	 * @return NONE
	 * @throws Exception
	 */
	@Action("canDelete")
	public String canDelete() throws Exception {
		String canDelete = "true";
		if (selectedVOs != null) {
			for (String vo : selectedVOs) {
				List<DomainVO> ds = getDomainVos(vo);
				QueryCriteria queryCriteria = new QueryCriteria();
				queryCriteria.setPageSize(-1);
				queryCriteria.addQueryCondition("tbl_compexid", ds.get(0)
						.getDomainId());
				List forms = dateService.queryFormList(queryCriteria);
				if (forms.size() > 0) {
					canDelete = "false";
					break;
				}
			}
		}
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(canDelete);
		return NONE;
	}

	public String getDateId() {
		return dateId;
	}

	public void setDateId(String dateId) {
		this.dateId = dateId;
	}

}
