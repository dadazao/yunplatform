package com.cloudstong.platform.third.bpm.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.third.bpm.model.BpmUserCondition;
import com.cloudstong.platform.third.bpm.service.BpmNodeUserService;
import com.cloudstong.platform.third.bpm.service.BpmUserConditionService;

import net.sf.json.JSONObject;

@ParentPackage("default")
@Namespace("/pages/third/bpm/bpmUserCondition")
@SuppressWarnings("serial")
public class BpmUserConditionAction extends BaseAction {

	@Resource
	private BpmUserConditionService bpmUserConditionService;

	@Resource
	private BpmNodeUserService bpmNodeUserService;
	
	private List bpmUserConditionList;
	
	private BpmUserCondition bpmUserCondition;

	@Action("save")
	public String save() throws Exception {
		HttpServletRequest request = getRequest();
		Long setId = Long.valueOf(RequestUtil.getLong(request, "setId"));
		Long conditionId = Long.valueOf(RequestUtil.getLong(request, "conditionId"));
		String actDefId = RequestUtil.getString(request, "actDefId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String conditionShow = RequestUtil.getString(request, "conditionShow");
		String condition = RequestUtil.getString(request, "condition");
		String conditionName = RequestUtil.getString(request, "conditionName");
		Long tableId = Long.valueOf(RequestUtil.getLong(request, "variableids"));
		Long sn = Long.valueOf(RequestUtil.getLong(request, "sn"));
		condition = condition.replace("''", "'");

		try {
			if ((conditionId == null) || (conditionId.longValue() == 0L)) {
				BpmUserCondition bpmUserCondition = new BpmUserCondition();
				bpmUserCondition.setSetId(setId);
				bpmUserCondition.setNodeid(nodeId);
				bpmUserCondition.setActdefid(actDefId);
				bpmUserCondition.setConditionShow(conditionShow);
				bpmUserCondition.setCondition(condition);
				bpmUserCondition.setSn(sn);
				bpmUserCondition.setConditionname(conditionName);
				bpmUserCondition.setId(Long.valueOf(UniqueIdUtil.genId()));
				bpmUserConditionService.add(bpmUserCondition);
			} else {
				BpmUserCondition bpmUserCondition = (BpmUserCondition) bpmUserConditionService.getById(conditionId);
				bpmUserCondition.setSetId(setId);
				bpmUserCondition.setNodeid(nodeId);
				bpmUserCondition.setActdefid(actDefId);
				bpmUserCondition.setConditionShow(conditionShow);
				bpmUserCondition.setCondition(condition);
				bpmUserCondition.setConditionname(conditionName);
				bpmUserConditionService.update(bpmUserCondition);
			}
			printJSON("success");
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail");
		}

		return NONE;
	}

	@Action("updateSn")
	public String updateSn() throws Exception {
		HttpServletRequest request = getRequest();
		Long currentId = Long.valueOf(RequestUtil.getLong(request, "currentId"));
		Long currentSn = Long.valueOf(RequestUtil.getLong(request, "currentSn"));
		Long otherId = Long.valueOf(RequestUtil.getLong(request, "otherId"));
		Long otherSn = Long.valueOf(RequestUtil.getLong(request, "otherSn"));

		String resultMsg = null;
		try {
			BpmUserCondition bpmUserCondition = (BpmUserCondition) bpmUserConditionService.getById(currentId);
			BpmUserCondition otherCondition = (BpmUserCondition) bpmUserConditionService.getById(otherId);
			if ((bpmUserCondition == null) || (otherCondition == null))
				throw new Exception("获取不到对象");
			if (currentSn == otherSn) {
				otherSn = Long.valueOf(otherSn.longValue() + 1L);
			}
			bpmUserCondition.setSn(currentSn);
			bpmUserConditionService.update(bpmUserCondition);

			otherCondition.setSn(otherSn);
			bpmUserConditionService.update(otherCondition);
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
		}
		return NONE;
	}

	protected BpmUserCondition getFormObject(HttpServletRequest request) throws Exception {
//		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { "yyyy-MM-dd" }));

		String json = RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);

		BpmUserCondition bpmUserCondition = (BpmUserCondition) JSONObject.toBean(obj, BpmUserCondition.class);

		return bpmUserCondition;
	}

	@Action("list")
	public String list() throws Exception {
		bpmUserConditionList = this.bpmUserConditionService.getAll();
		return "list";
	}

	@Action("del")
	public void del() throws Exception {
		String preUrl = RequestUtil.getPrePage(getRequest());
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(getRequest(), "id");
			for (int i = 0; i < lAryId.length; i++) {
				this.bpmNodeUserService.delByConditionId(lAryId[i]);
			}
			this.bpmUserConditionService.delByIds(lAryId);
			printJSON("success");
		} catch (Exception ex) {
			ex.printStackTrace();
			printJSON("fail");
		}
	}

	@Action("edit")
	public String edit() throws Exception {
		Long id = Long.valueOf(RequestUtil.getLong(getRequest(), "id"));
		String returnUrl = RequestUtil.getPrePage(getRequest());
		bpmUserCondition = (BpmUserCondition) this.bpmUserConditionService.getById(id);

		return "edit";
	}

	@Action( "get" )
	public String get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		bpmUserCondition = (BpmUserCondition) this.bpmUserConditionService.getById(Long.valueOf(id));
		return "get";
	}

	@RequestMapping({ "delByAjax" })
	@ResponseBody
	@Action("delByAjax")
	public void delByAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.bpmUserConditionService.delByIds(lAryId);
			printJSON("success");
		} catch (Exception ex) {
			printJSON("fail");
		}
	}

	@Action("updateGroup")
	public void updateGroup() throws Exception {
		String ids = RequestUtil.getString(getRequest(), "conditionIds");
		String groupNos = RequestUtil.getString(getRequest(), "groupNos");
		String[] aryConditionId = ids.split(",");
		String[] arygroupNo = groupNos.split(",");
		try {
			for (int i = 0; i < aryConditionId.length; i++) {
				String idStr = aryConditionId[i];
				Long id = Long.valueOf(Long.parseLong(idStr));
				Integer groupNo = Integer.valueOf(Integer.parseInt(arygroupNo[i]));

				BpmUserCondition bpmUserCondition = (BpmUserCondition) this.bpmUserConditionService.getById(id);
				bpmUserCondition.setGroupNo(groupNo);
				this.bpmUserConditionService.update(bpmUserCondition);
			}
			printJSON("success");
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail");
		}
	}

	public List getBpmUserConditionList() {
		return bpmUserConditionList;
	}

	public void setBpmUserConditionList(List bpmUserConditionList) {
		this.bpmUserConditionList = bpmUserConditionList;
	}

	public BpmUserCondition getBpmUserCondition() {
		return bpmUserCondition;
	}

	public void setBpmUserCondition(BpmUserCondition bpmUserCondition) {
		this.bpmUserCondition = bpmUserCondition;
	}

}