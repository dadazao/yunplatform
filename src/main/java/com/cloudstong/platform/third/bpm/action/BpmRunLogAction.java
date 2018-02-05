package com.cloudstong.platform.third.bpm.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.third.bpm.model.BpmRunLog;
import com.cloudstong.platform.third.bpm.service.BpmRunLogService;

@ParentPackage("default")
@Namespace("/pages/third/bpm/bpmRunLog")
@Results(value = { @Result(name = "list", location = "/WEB-INF/view/third/bpm/bpmRunLogList.jsp"),
		@Result(name = "view", location = "/WEB-INF/view/third/bpm/bpmRunLogView.jsp") 
})
@SuppressWarnings("serial")
public class BpmRunLogAction extends BaseAction {

	private List<BpmRunLog> bpmRunLogList;

	private Long runLogId;

	private BpmRunLog bpmRunLog;

	@Resource
	private BpmRunLogService bpmRunLogService;

	@Action("list")
	public String list() throws Exception {
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);

		Long runId = Long.valueOf(RequestUtil.getLong(getRequest(), "runId"));
		if (runId.longValue() > 0L) {
			queryCriteria.addQueryCondition("runid", runId);
		}

		pageResult = bpmRunLogService.query(queryCriteria);

		return "list";
	}

	@Action("mylist")
	public String mylist() throws Exception {
		Long userId = AppContext.getCurrentUserId();
		queryCriteria.addQueryCondition("userid", userId);

		pageResult = bpmRunLogService.query(queryCriteria);

		return "mylist";
	}

	@Action("del")
	public String del() throws Exception {
		try {
			if (selectedIDs != null) {
				bpmRunLogService.delByIds(selectedIDs);
			}
			printJSON("success");
		} catch (Exception ex) {
			ex.printStackTrace();
			printJSON("fail");
		}

		return NONE;
	}

	@Action("view")
	public String view() throws Exception {
		bpmRunLog = (BpmRunLog) bpmRunLogService.getById(runLogId);
		return "view";
	}

	public List<BpmRunLog> getBpmRunLogList() {
		return bpmRunLogList;
	}

	public void setBpmRunLogList(List<BpmRunLog> bpmRunLogList) {
		this.bpmRunLogList = bpmRunLogList;
	}

	public Long getRunLogId() {
		return runLogId;
	}

	public void setRunLogId(Long runLogId) {
		this.runLogId = runLogId;
	}

	public BpmRunLog getBpmRunLog() {
		return bpmRunLog;
	}

	public void setBpmRunLog(BpmRunLog bpmRunLog) {
		this.bpmRunLog = bpmRunLog;
	}

}