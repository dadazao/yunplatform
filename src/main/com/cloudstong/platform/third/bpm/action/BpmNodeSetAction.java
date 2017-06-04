package com.cloudstong.platform.third.bpm.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;
import com.cloudstong.platform.third.bpm.service.BpmDefinitionService;
import com.cloudstong.platform.third.bpm.service.BpmNodeSetService;
import com.cloudstong.platform.third.bpm.service.BpmService;
import com.cloudstong.platform.third.bpm.util.BpmUtil;

@ParentPackage("default")
@Namespace("/pages/third/bpm/bpmNodeSet")
@Results(value = { @Result(name = "list", location = "/pages/third/bpm/bpmNodeSetList.jsp"),
		@Result(name = "edit", location = "/pages/third/bpm/bpmNodeSetEdit.jsp") 
})
public class BpmNodeSetAction extends BaseAction {

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@Resource
	private BpmDefinitionService bpmDefinitionService;

	@Resource
	private BpmService bpmService;
	
	@Resource
	private FormService formService;

	@Action("list")
	public String list() throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(getRequest(), "definitionId"));
		String isNew = "yes";
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService.getById(defId);
		String deployId = bpmDefinition.getActDeployId().toString();
		List nodeList = new ArrayList();

		Map activityList = new HashMap();
		String defXml = this.bpmService.getDefXmlByDeployId(deployId);
		Map activityAllList = BpmUtil.getTranstoActivitys(defXml, nodeList);
		List<BpmNodeSet> list = this.bpmNodeSetService.getByDefId(defId);
		Map taskMap = (Map) activityAllList.get("任务节点");
		for (int i = 0; i < list.size(); i++) {
			String nodeId = ((BpmNodeSet) list.get(i)).getNodeId();
			Map tempMap = new HashMap();
			Set set = taskMap.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				if (nodeId!=null && !nodeId.equals(entry.getKey())) {
					tempMap.put(entry.getKey(), entry.getValue());
				}
			}
			activityList.put(((BpmNodeSet) list.get(i)).getNodeId(), tempMap);
		}

		BpmNodeSet globalForm = this.bpmNodeSetService.getBySetType(defId, BpmNodeSet.SetType_GloabalForm);
		BpmNodeSet bpmForm = this.bpmNodeSetService.getBySetType(defId, BpmNodeSet.SetType_BpmForm);

		if (("yes".equals(isNew)) && ((BeanUtils.isNotEmpty(globalForm)) || (BeanUtils.isNotEmpty(bpmForm)))) {
			isNew = "no";
		}
		
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		qc.addQueryCondition("tbl_systemteam", "bus");
		List<Form> formList = formService.queryForm(qc);

		HttpServletRequest request = getRequest();
		request.setAttribute("isNew", isNew);
		request.setAttribute("bpmNodeSetList", list);
		request.setAttribute("bpmDefinition", bpmDefinition);
		request.setAttribute("globalForm", globalForm);
		request.setAttribute("activityList", activityList);
		request.setAttribute("bpmForm", bpmForm);
		request.setAttribute("formList", formList);

		return "list";
	}

	@Action("del")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "setId");
		this.bpmNodeSetService.delByIds(lAryId);
		response.sendRedirect(preUrl);
	}

	@Action("edit")
	public String edit() throws Exception {
		Long setId = Long.valueOf(RequestUtil.getLong(getRequest(), "setId"));
		String returnUrl = RequestUtil.getPrePage(getRequest());
		BpmNodeSet bpmNodeSet = null;
		if (setId != null)
			bpmNodeSet = (BpmNodeSet) this.bpmNodeSetService.getById(setId);
		else {
			bpmNodeSet = new BpmNodeSet();
		}
		return "edit";
	}

	@Action("get")
	public String get() throws Exception {
		long id = RequestUtil.getLong(getRequest(), "setId");
		BpmNodeSet bpmNodeSet = (BpmNodeSet) this.bpmNodeSetService.getById(Long.valueOf(id));
		return "get";
	}

	@Action("save")
	public String save() throws Exception {
		HttpServletRequest request = getRequest();
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId"));
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService.getById(defId);
		
		String[] nodeIds = request.getParameterValues("nodeId");
		String[] nodeNames = request.getParameterValues("bpmNodeName");
		String[] formTypes = request.getParameterValues("formType");
		String[] aryFormKey = request.getParameterValues("formKey");
		String[] formUrls = request.getParameterValues("formUrl");
		String[] formDefNames = request.getParameterValues("formDefName");
		String[] aryBeforeHandler = request.getParameterValues("beforeHandler");
		String[] aryAfterHandler = request.getParameterValues("afterHandler");
		String[] aryDetailUrl = request.getParameterValues("detailUrl");
		Map nodeMap = this.bpmNodeSetService.getMapByDefId(defId);

		List nodeList = new ArrayList();
		for (int i = 0; i < nodeIds.length; i++) {
			String nodeId = nodeIds[i];
			BpmNodeSet nodeSet = new BpmNodeSet();
			if (nodeMap.containsKey(nodeId+nodeNames[i])) {
				nodeSet = (BpmNodeSet) nodeMap.get(nodeId+nodeNames[i]);

				if (nodeSet.getFormKey().longValue() > 0L) {
					nodeSet.setOldFormKey(nodeSet.getFormKey());
				}
			}

			nodeSet.setNodeId(nodeId);
			nodeSet.setActDefId(bpmDefinition.getActDefId());
			nodeSet.setNodeName(nodeNames[i]);

			short formType = Short.parseShort(formTypes[i]);

//			String beforeHandler = aryBeforeHandler[i];
//			String afterHandler = aryAfterHandler[i];

//			beforeHandler = getHandler(beforeHandler);
//			afterHandler = getHandler(afterHandler);

//			String detailUrl = aryDetailUrl[i];
//			String formUrl = formUrls[i];

			nodeSet.setFormType(Short.valueOf(formType));

			nodeSet.setFormUrl("");
			nodeSet.setDetailUrl("");

			Long formKey = Long.valueOf(Long.parseLong(aryFormKey[i]));

//			nodeSet.setBeforeHandler(beforeHandler);
//			nodeSet.setAfterHandler(afterHandler);

			nodeSet.setDefId(new Long(defId.longValue()));

			String[] jumpType = request.getParameterValues("jumpType_" + nodeId);
			if (jumpType != null)
				nodeSet.setJumpType(StringUtil.getArrayAsString(jumpType));
			else {
				nodeSet.setJumpType("");
			}
			String isHideOption = request.getParameter("isHideOption_" + nodeId);
			String isHidePath = request.getParameter("isHidePath_" + nodeId);
			if (StringUtil.isNotEmpty(isHideOption))
				nodeSet.setIsHideOption(BpmNodeSet.HIDE_OPTION);
			else {
				nodeSet.setIsHideOption(BpmNodeSet.NOT_HIDE_OPTION);
			}
			if (StringUtil.isNotEmpty(isHidePath))
				nodeSet.setIsHidePath(BpmNodeSet.HIDE_PATH);
			else {
				nodeSet.setIsHidePath(BpmNodeSet.NOT_HIDE_PATH);
			}
			nodeSet.setSetType(BpmNodeSet.SetType_TaskNode);
			nodeSet.setFormKey(formKey);
			nodeList.add(nodeSet);
		}
		List list = getGlobalBpm(request, bpmDefinition);
		nodeList.addAll(list);
		try{
			this.bpmNodeSetService.save(defId, nodeList);
			printJSON("success");
		}catch(Exception e){
			e.printStackTrace();
			printJSON("fail");
		}
		return NONE;
	}

	private List<BpmNodeSet> getGlobalBpm(HttpServletRequest request, BpmDefinition bpmDefinition) {
		List list = new ArrayList();

		Long defaultFormKey = Long.valueOf(RequestUtil.getLong(request, "defaultFormKey"));
		if (defaultFormKey != -1) {
			String defaultFormName = RequestUtil.getString(request, "defaultFormName");
			String setId = RequestUtil.getString(request, "defaultFormSetId");
			String beforeHandlerGlobal = RequestUtil.getString(request, "beforeHandlerGlobal");
			String afterHandlerGlobal = RequestUtil.getString(request, "afterHandlerGlobal");
			beforeHandlerGlobal = getHandler(beforeHandlerGlobal);
			afterHandlerGlobal = getHandler(afterHandlerGlobal);
			String formUrlGlobal = RequestUtil.getString(request, "formUrlGlobal");
			String detailUrlGlobal = RequestUtil.getString(request, "detailUrlGlobal");
			BpmNodeSet bpmNodeSet = new BpmNodeSet();
			bpmNodeSet.setDefId(bpmDefinition.getDefId());
			bpmNodeSet.setActDefId(bpmDefinition.getActDefId());
			bpmNodeSet.setFormKey(defaultFormKey);
			bpmNodeSet.setFormDefName(defaultFormName);
			bpmNodeSet.setFormUrl(formUrlGlobal);
			bpmNodeSet.setBeforeHandler(beforeHandlerGlobal);
			bpmNodeSet.setAfterHandler(afterHandlerGlobal);
			bpmNodeSet.setDetailUrl(detailUrlGlobal);
			bpmNodeSet.setSetType(BpmNodeSet.SetType_GloabalForm);
			if(!"".equals(setId)) {
				bpmNodeSet.setSetId(Long.valueOf(setId));
			}

			list.add(bpmNodeSet);

		}

		Long bpmFormKey = Long.valueOf(RequestUtil.getLong(request, "bpmFormKey"));
		if (bpmFormKey != -1) {
			String setId = RequestUtil.getString(request, "bpmFormSetId");
			String bpmFormName = RequestUtil.getString(request, "bpmFormName");
			String bpmFormUrl = RequestUtil.getString(request, "bpmFormUrl");

			BpmNodeSet bpmNodeSet = new BpmNodeSet();
			bpmNodeSet.setDefId(bpmDefinition.getDefId());
			bpmNodeSet.setActDefId(bpmDefinition.getActDefId());
			if (bpmFormKey.longValue() > 0L) {
				bpmNodeSet.setFormKey(bpmFormKey);
				bpmNodeSet.setFormDefName(bpmFormName);
			}
			bpmNodeSet.setFormUrl(bpmFormUrl);
			bpmNodeSet.setSetType(BpmNodeSet.SetType_BpmForm);
			if(!"".equals(setId)) {
				bpmNodeSet.setSetId(Long.valueOf(setId));
			}
			list.add(bpmNodeSet);
		}
		return list;
	}

	private String getHandler(String handler) {
		if ((StringUtil.isEmpty(handler)) || (handler.indexOf(".") == -1)) {
			handler = "";
		}
		return handler;
	}

	@Action("validHandler")
	public void validHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String handler = RequestUtil.getString(request, "handler");
		int rtn = BpmUtil.isHandlerValid(handler);
		String template = "{\"result\":\"%s\",\"msg\":\"%s\"}";
		String msg = "";
		switch (rtn) {
		case 0:
			msg = "输入有效";
			break;
		case -1:
			msg = "输入格式无效";
			break;
		case -2:
			msg = "没有service类";
			break;
		case -3:
			msg = "没有对应的方法";
			break;
		default:
			msg = "其他错误";
		}

		String str = String.format(template, new Object[] { Integer.valueOf(rtn), msg });
		response.getWriter().print(str);
	}
}