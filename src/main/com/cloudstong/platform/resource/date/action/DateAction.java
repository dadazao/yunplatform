package com.cloudstong.platform.resource.date.action;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.resource.date.service.DateService;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;

/**
 * @author liuqi
 *
 */
public class DateAction extends CompexDomainAction {

	/**
	 * 日期组件ID
	 */
	private String dateId = "";

	@Resource
	private DateService dateService;

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
