package com.cloudstong.platform.third.bpm.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.Pinyin4jUtil;
import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.TimeUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.third.bpm.graph.ShapeMeta;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.model.BpmNodeButton;
import com.cloudstong.platform.third.bpm.model.BpmNodeRule;
import com.cloudstong.platform.third.bpm.model.BpmNodeScript;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.model.BpmNodeUserUplow;
import com.cloudstong.platform.third.bpm.model.BpmType;
import com.cloudstong.platform.third.bpm.model.BpmUserCondition;
import com.cloudstong.platform.third.bpm.model.FlowNode;
import com.cloudstong.platform.third.bpm.model.NodeCache;
import com.cloudstong.platform.third.bpm.model.NodeUserMap;
import com.cloudstong.platform.third.bpm.model.TaskApprovalItems;
import com.cloudstong.platform.third.bpm.model.TaskReminder;
import com.cloudstong.platform.third.bpm.service.BpmDefinitionService;
import com.cloudstong.platform.third.bpm.service.BpmFormRunService;
import com.cloudstong.platform.third.bpm.service.BpmNodeButtonService;
import com.cloudstong.platform.third.bpm.service.BpmNodeRuleService;
import com.cloudstong.platform.third.bpm.service.BpmNodeScriptService;
import com.cloudstong.platform.third.bpm.service.BpmNodeSetService;
import com.cloudstong.platform.third.bpm.service.BpmNodeUserCalculationSelector;
import com.cloudstong.platform.third.bpm.service.BpmNodeUserService;
import com.cloudstong.platform.third.bpm.service.BpmNodeUserUplowService;
import com.cloudstong.platform.third.bpm.service.BpmService;
import com.cloudstong.platform.third.bpm.service.BpmTypeService;
import com.cloudstong.platform.third.bpm.service.BpmUserConditionService;
import com.cloudstong.platform.third.bpm.service.ProcessRunService;
import com.cloudstong.platform.third.bpm.service.TaskApprovalItemsService;
import com.cloudstong.platform.third.bpm.service.TaskReminderService;
import com.cloudstong.platform.third.bpm.util.BpmUtil;

@ParentPackage("default")
@Namespace("/pages/third/bpm/bpmDefinition")
@Results(value = { @Result(name = "list", location = "/pages/third/bpm/bpmDefinitionList.jsp"),
		@Result(name = "design", location = "/pages/third/bpm/bpmDefinitionDesign.jsp"),
		@Result(name = "detail", location = "/pages/third/bpm/bpmDefinitionDetail.jsp"),
		@Result(name = "nodeSet", location = "/pages/third/bpm/bpmDefinitionNodeSet.jsp"),
		@Result(name = "nodeSummary", location = "/pages/third/bpm/bpmDefinitionNodeSummary.jsp"),
		@Result(name = "userSet", location = "/pages/third/bpm/bpmDefinitionUserSet.jsp"),
		@Result(name = "conditionEdit", location = "/pages/third/bpm/bpmDefinitionConditionEdit.jsp"),
		@Result(name = "instance", location = "/pages/third/bpm/bpmDefinitionInstance.jsp"),
		@Result(name = "versions", location = "/pages/third/bpm/bpmDefinitionVersions.jsp"),
		@Result(name = "otherParam", location = "/pages/third/bpm/bpmDefinitionOtherParam.jsp"),
		@Result(name = "informType", location = "/pages/third/bpm/bpmDefinitionInformType.jsp"),
		@Result(name = "flowImg", location = "/pages/third/bpm/bpmDefinitionFlowImg.jsp"),
		@Result(name = "taskNodes", location = "/pages/third/bpm/bpmDefinitionTaskNodes.jsp") })
@SuppressWarnings("serial")
public class BpmDefinitionAction extends BaseAction {

	private BpmDefinition bpmDefinition;

	private List<BpmDefinition> bpmDefinitionList;

	@Resource
	private BpmService bpmService;

	@Resource
	private BpmDefinitionService bpmDefinitionService;

	@Resource
	private BpmTypeService bpmTypeService;

	private String xmlRecord;

	private Long definitionId;

	private String defKey;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private BpmNodeUserService bpmNodeUserService;

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@Resource
	private BpmNodeUserUplowService bpmNodeUserUplowService;

	@Resource
	private BpmNodeRuleService bpmNodeRuleService;

	@Resource
	private BpmNodeScriptService bpmNodeScriptService;

	@Resource
	private BpmNodeButtonService bpmNodeButtonService;

	@Resource
	private TaskReminderService taskReminderService;

	@Resource
	private TaskApprovalItemsService taskApprovalItemsService;

	@Resource
	private BpmUserConditionService bpmUserConditionService;

	@Resource
	private BpmNodeUserCalculationSelector bpmNodeUserCalculationSelector;

	@Resource
	private BpmFormRunService bpmFormRunService;

	@Action("list")
	public String list() throws Exception {
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		if (bpmDefinition != null) {
			if (bpmDefinition.getSubject() != null && !"".equals(bpmDefinition.getSubject())) {
				queryCriteria.addQueryCondition("subject", "%" + bpmDefinition.getSubject() + "%");
			}
			if (bpmDefinition.getDefKey() != null && !"".equals(bpmDefinition.getSubject())) {
				queryCriteria.addQueryCondition("defKey", bpmDefinition.getDefKey());
			}
			if (bpmDefinition.getVersionNo() != null && bpmDefinition.getVersionNo() != -1) {
				queryCriteria.addQueryCondition("versionNo", bpmDefinition.getVersionNo());
			}
			if (bpmDefinition.getStatus() != null && bpmDefinition.getStatus() != -1) {
				queryCriteria.addQueryCondition("status", bpmDefinition.getStatus());
			}
			if (bpmDefinition.getDisableStatus() != null && bpmDefinition.getDisableStatus() != -1) {
				queryCriteria.addQueryCondition("disableStatus", bpmDefinition.getDisableStatus());
			}
		}
		pageResult = bpmDefinitionService.queryBpmDefinition(queryCriteria);
		return "list";
	}

	@Action("versions")
	public String versions() throws Exception {
		bpmDefinition = (BpmDefinition) bpmDefinitionService.getById(definitionId);
		bpmDefinitionList = bpmDefinitionService.getAllHistoryVersions(definitionId);

		return "versions";
	}

	@Action("del")
	public String del() throws Exception {
		String isOnlyVersion = getRequest().getParameter("isOnlyVersion");
		boolean onlyVersion = "true".equals(isOnlyVersion) ? true : false;
		try {
			if (selectedIDs != null) {
				for (Long defId : selectedIDs) {
					bpmDefinitionService.delDefbyDeployId(defId, onlyVersion);
				}
			} else if (definitionId != null) {
				bpmDefinitionService.delDefbyDeployId(definitionId, onlyVersion);
			}
			printJSON("success", "删除流程定义成功");
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail", "删除流程定义失败:<br><br><div class='exceptionMessage'>" + e.getMessage() + "</div>");
		}

		return NONE;
	}

	@Action("detail")
	public String detail() throws Exception {
		bpmDefinition = (BpmDefinition) bpmDefinitionService.getById(definitionId);
		return "detail";
	}

	@Action("nodeSet")
	public String nodeSet() throws Exception {
		bpmDefinition = (BpmDefinition) bpmDefinitionService.getById(definitionId);
		HttpServletRequest request = getRequest();
		if (bpmDefinition.getActDeployId() != null) {
			String defXml = bpmService.getDefXmlByDeployId(bpmDefinition.getActDeployId().toString());
			request.setAttribute("defXml", defXml);
			ShapeMeta shapeMeta = BpmUtil.transGraph(defXml);
			request.setAttribute("shapeMeta", shapeMeta);
		}
		return "nodeSet";
	}

	@Action("userSet")
	public String userSet() throws Exception {
		HttpServletRequest request = getRequest();
		String nodeId = RequestUtil.getString(request, "nodeTag");

		BpmDefinition bpmDefinition = (BpmDefinition) bpmDefinitionService.getById(definitionId);
		List<BpmNodeSet> setList = bpmNodeSetService.getByDefId(definitionId);
		List nodeUserMapList = new ArrayList();
		List bpmUserConditionList;
		if (!StringUtil.isEmpty(nodeId)) {
			BpmNodeSet nodeSet = bpmNodeSetService.getByDefIdNodeId(definitionId, nodeId);
			bpmUserConditionList = bpmUserConditionService.getBySetId(nodeSet.getSetId());
			NodeUserMap nodeUserMap = new NodeUserMap();
			nodeUserMap.setSetId(nodeSet.getSetId());
			nodeUserMap.setNodeId(nodeSet.getNodeId());
			nodeUserMap.setNodeName(nodeSet.getNodeName());
			nodeUserMap.setBpmUserConditionList(bpmUserConditionList);
			nodeUserMapList.add(nodeUserMap);
			request.setAttribute("nodeTag", nodeId);
		} else {
			for (BpmNodeSet nodeSet : setList) {
				bpmUserConditionList = bpmUserConditionService.getBySetId(nodeSet.getSetId());
				NodeUserMap nodeUserMap = new NodeUserMap();
				nodeUserMap.setSetId(nodeSet.getSetId());
				nodeUserMap.setNodeId(nodeSet.getNodeId());
				nodeUserMap.setNodeName(nodeSet.getNodeName());
				nodeUserMap.setBpmUserConditionList(bpmUserConditionList);
				nodeUserMapList.add(nodeUserMap);
			}
		}
		request.setAttribute("nodeUserMapList", nodeUserMapList);
		request.setAttribute("nodeSetList", setList);
		request.setAttribute("bpmDefinition", bpmDefinition);
		return "userSet";
	}

	@Action("saveInformType")
	public void saveInformType() throws Exception {
		HttpServletRequest request = getRequest();
		String actDefId = request.getParameter("actDefId");
		String nodeId = request.getParameter("nodeId");
		String[] informTypes = request.getParameterValues("informType");
		BpmNodeSet bpmNodeSet = this.bpmNodeSetService.getBpmNodeSetByActDefIdNodeId(actDefId, nodeId);
		String it = "";
		if (informTypes != null && informTypes.length > 0) {
			for (String i : informTypes) {
				it += i + ",";
			}
		}
		bpmNodeSet.setInformType(it);
		try {
			this.bpmNodeSetService.update(bpmNodeSet);
			printJSON("success", "设置成功");
		} catch (Exception e) {
			printJSON("fail", "设置失败");
		}
	}

	@Action("design")
	public String design() throws Exception {
		HttpServletRequest request = getRequest();

		if (definitionId > 0L) {
			bpmDefinition = (BpmDefinition) bpmDefinitionService.getById(definitionId);
		} else {
			request.setAttribute("subject", "未命名");
		}

		Long uId = AppContext.getCurrentUserId();
		xmlRecord = getAllBpmType();
		request.setAttribute("xmlRecord", xmlRecord);
		request.setAttribute("uId", uId);

		return "design";
	}

	@Action("flexDefSave")
	public String flexDefSave() throws Exception {

		if (bpmDefinition.getDefId() == null || bpmDefinition.getTaskNameRule() == null) {
			bpmDefinition.setTaskNameRule("{流程标题:title}-{发起人:startUser}-{发起时间:startTime}");
		}

		if (StringUtils.isNotEmpty(bpmDefinition.getDefXml())) {
			bpmDefinition.setDefXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + bpmDefinition.getDefXml());
		}

		Boolean isDeploy = Boolean.valueOf(Boolean.parseBoolean(getRequest().getParameter("deploy")));
		String actFlowDefXml = "";
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			actFlowDefXml = BpmUtil.transform(bpmDefinition.getDefKey(), bpmDefinition.getSubject(),
					bpmDefinition.getDefXml());
			bpmDefinitionService.doSaveOrUpdate(bpmDefinition, isDeploy.booleanValue(), actFlowDefXml);
			response.getWriter().print("success");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.getWriter().print(
					"流程定义保存或发布失败:<br><br><div class='exceptionMessage'>" + ex.getMessage() + "</div>");
		}
		return NONE;
	}

	@Action("flowImg")
	public String flowImg() throws Exception {
		HttpServletRequest request = getRequest();
		
		String actDefId = request.getParameter("actDefId");

		String defXml = this.bpmService.getDefXmlByProcessDefinitionId(actDefId);
		ShapeMeta shapeMeta = BpmUtil.transGraph(defXml);


		request.setAttribute("shapeMeta", shapeMeta);
		request.setAttribute("actDefId", actDefId);

		return "flowImg";
	}

	@Action("deploy")
	public String deploy() throws Exception {
		bpmDefinition = (BpmDefinition) bpmDefinitionService.getById(definitionId);

		try {
			String actFlowDefXml = BpmUtil.transform(bpmDefinition.getDefKey(), bpmDefinition.getSubject(),
					bpmDefinition.getDefXml());
			bpmDefinitionService.doSaveOrUpdate(bpmDefinition, true, actFlowDefXml);
			printJSON("success", true, "流程发布成功！");
		} catch (Exception ex) {
			ex.printStackTrace();
			printJSON("fail", "流程保发布失败！失败原因:" + ex.getMessage());
		}
		return NONE;
	}

	@Action("flexGet")
	public String flexGet() throws Exception {
		BpmDefinition bpmDefinition = null;
		if (definitionId > 0L) {
			bpmDefinition = (BpmDefinition) bpmDefinitionService.getById(definitionId);
		} else {
			bpmDefinition = new BpmDefinition();
		}
		StringBuffer msg = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Result>");
		msg.append("<defId>" + bpmDefinition.getDefId() + "</defId>");
		msg.append("<defXml>" + bpmDefinition.getDefXml() + "</defXml>");
		if (bpmDefinition.getTypeId() != null) {
			BpmType proType = (BpmType) bpmTypeService.getById(bpmDefinition.getTypeId());
			msg.append("<typeName>" + proType.getName() + "</typeName>");
			msg.append("<typeId>" + proType.getId() + "</typeId>");
		}
		msg.append("<subject>" + bpmDefinition.getSubject() + "</subject>");
		msg.append("<defKey>" + bpmDefinition.getDefKey() + "</defKey>");
		msg.append("<descp>" + bpmDefinition.getDescp() + "</descp>");
		msg.append("<versionNo>" + bpmDefinition.getVersionNo() + "</versionNo>");
		msg.append("</Result>");

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(msg.toString());
		return NONE;
	}

	@Action("taskNodes")
	public String taskNodes() throws Exception {
		String actDefId = RequestUtil.getString(getRequest(), "actDefId");
		String nodeId = RequestUtil.getString(getRequest(), "nodeId");
		Map taskDefNodeMap = this.bpmService.getTaskNodes(actDefId, nodeId);
		getRequest().setAttribute("taskNodeMap", taskDefNodeMap);
		return "taskNodes";
	}

	@Action("saveUser")
	public String saveUser() throws Exception {
		HttpServletRequest request = getRequest();
		Long conditionId = Long.valueOf(RequestUtil.getLong(request, "conditionId"));
		String[] assignTypes = request.getParameterValues("assignType");
		String[] assignUseType = request.getParameterValues("assignUseType");
		String[] nodeIds = request.getParameterValues("nodeId");
		String[] cmpIdss = request.getParameterValues("cmpIds");
		String[] cmpNamess = request.getParameterValues("cmpNames");
		String[] nodeUserIds = request.getParameterValues("nodeUserId");
		String[] compTypes = request.getParameterValues("compType");

		BpmDefinition bpmDefintion = (BpmDefinition) bpmDefinitionService.getById(definitionId);
		try {
			if ((assignTypes != null) && (assignTypes.length > 0)) {
				for (int i = 0; i < assignTypes.length; i++) {
					BpmNodeUser bnUser = null;
					if (StringUtil.isNotEmpty(nodeUserIds[i])) {
						long nodeUserId = new Long(nodeUserIds[i]).longValue();

						bnUser = (BpmNodeUser) bpmNodeUserService.getById(Long.valueOf(nodeUserId));
						bnUser.setCmpIds(cmpIdss[i]);
						bnUser.setCmpNames(cmpNamess[i]);
						bnUser.setCompType(new Short(compTypes[i]));
						bnUser.setSn(Integer.valueOf(i));
						bnUser.setConditionId(conditionId);
						bpmNodeUserService.update(bnUser);

						if ((bnUser.getAssignType().shortValue() == 6) && (cmpIdss[i] != null)
								&& (cmpIdss[i].length() > 0)) {
							JSONArray ja = JSONArray.fromObject(cmpIdss[i]);
							List uplowList = (List) JSONArray.toCollection(ja, BpmNodeUserUplow.class);
							bpmNodeUserUplowService.upd(nodeUserId, uplowList);
						}
					} else {
						long nodeUserId = UniqueIdUtil.genId();

						bnUser = new BpmNodeUser();
						bnUser.setCmpIds(cmpIdss[i]);
						bnUser.setCmpNames(cmpNamess[i]);
						BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(bpmDefintion.getActDefId(),
								nodeIds[i]);
						if (bpmNodeSet != null) {
							bnUser.setSetId(bpmNodeSet.getSetId());
						}
						if (assignUseType.length > i)
							bnUser.setAssignUseType(new Short(assignUseType[i]));
						else {
							bnUser.setAssignUseType(Short.valueOf((short) 0));
						}
						bnUser.setActDefId(bpmDefintion.getActDefId());
						bnUser.setAssignType(new Short(assignTypes[i]));
						bnUser.setCompType(new Short(compTypes[i]));
						bnUser.setNodeId(nodeIds[i]);
						bnUser.setNodeUserId(Long.valueOf(nodeUserId));
						bnUser.setSn(Integer.valueOf(i));
						bnUser.setConditionId(conditionId);
						bpmNodeUserService.add(bnUser);

						if ((bnUser.getAssignType().shortValue() == 6) && (cmpIdss[i] != null)
								&& (cmpIdss[i].length() > 0)) {
							JSONArray ja = JSONArray.fromObject(cmpIdss[i]);
							List uplowList = (List) JSONArray.toCollection(ja, BpmNodeUserUplow.class);
							bpmNodeUserUplowService.upd(nodeUserId, uplowList);
						}
					}
				}
			}
			printJSON("success", true);
		} catch (Exception e) {
			printJSON("fail");
		}

		return NONE;
	}

	@Action("instance")
	public String instance() throws Exception {
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		queryCriteria.addQueryCondition("defId", definitionId);
		queryCriteria.addQueryCondition("exceptStatus", Integer.valueOf(4));
		queryCriteria.addQueryCondition("exceptDefStatus", Integer.valueOf(3));
		pageResult = processRunService.getAll(queryCriteria);
		this.bpmDefinition = (BpmDefinition) bpmDefinitionService.getById(definitionId);

		return "instance";
	}

	@Action("otherParam")
	public String otherParam() throws Exception {
		HttpServletRequest request = getRequest();
		long defId = RequestUtil.getLong(request, "definitionId");
		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService.getById(Long.valueOf(defId));

		boolean isStartMultipleNode = NodeCache.isMultipleFirstNode(bpmDefinition.getActDefId());
		List list = this.bpmNodeSetService.getByDefId(Long.valueOf(defId));

		request.setAttribute("bpmDefinition", bpmDefinition);
		request.setAttribute("defId", bpmDefinition.getDefId());
		request.setAttribute("nodeSetList", list);
		request.setAttribute("isStartMultipleNode", Boolean.valueOf(isStartMultipleNode));

		return "otherParam";
	}

	@Action("informType")
	public String informType() throws Exception {
		HttpServletRequest request = getRequest();
		String actDefId = request.getParameter("actDefId");
		String nodeId = request.getParameter("nodeId");
		BpmNodeSet bpmNodeSet = this.bpmNodeSetService.getBpmNodeSetByActDefIdNodeId(actDefId, nodeId);
		request.setAttribute("bpmNodeSet", bpmNodeSet);
		return "informType";
	}

	@Action("saveParam")
	public void saveParam() throws Exception {
		HttpServletRequest request = getRequest();
		BpmDefinition bpmDefinition = new BpmDefinition();

		long defId = RequestUtil.getLong(request, "defId");
		bpmDefinition.setDefId(Long.valueOf(defId));

		String taskNameRule = RequestUtil.getString(request, "taskNameRule");
		bpmDefinition.setTaskNameRule(taskNameRule);

		short toFirstNode = (short) RequestUtil.getInt(request, "toFirstNode");
		bpmDefinition.setToFirstNode(Short.valueOf(toFirstNode));

		short showFirstAssignee = (short) RequestUtil.getInt(request, "showFirstAssignee", 0);
		bpmDefinition.setShowFirstAssignee(Short.valueOf(showFirstAssignee));

		String formDetailUrl = RequestUtil.getString(request, "formDetailUrl", "");
		bpmDefinition.setFormDetailUrl(formDetailUrl);

		short needStartForm = (short) RequestUtil.getInt(request, "needStartForm");
		bpmDefinition.setNeedStartForm(needStartForm);

		Integer directstart = Integer.valueOf(RequestUtil.getInt(request, "directstart", 0));
		bpmDefinition.setDirectstart(directstart);

		// int submitConfirm = RequestUtil.getInt(request, "submitConfirm", 0);
		// bpmDefinition.setSubmitConfirm(Integer.valueOf(submitConfirm));
		//
		// int allowDivert = RequestUtil.getInt(request, "allowDivert", 0);
		// bpmDefinition.setAllowDivert(Integer.valueOf(allowDivert));
		//
		// int allowFinishedDivert = RequestUtil.getInt(request,
		// "allowFinishedDivert", 0);
		// bpmDefinition.setAllowFinishedDivert(Integer.valueOf(allowFinishedDivert));
		//
		// String informStart = RequestUtil.getString(request, "informStart");
		// bpmDefinition.setInformStart(informStart);
		//
		// String informType = RequestUtil.getString(request, "informType");
		// bpmDefinition.setInformType(informType);
		//
		// Integer allowFinishedCc = Integer.valueOf(RequestUtil.getInt(request,
		// "allowFinishedCc"));
		// bpmDefinition.setAllowFinishedCc(allowFinishedCc);
		//
		// short isPrintForm = (short) RequestUtil.getInt(request,
		// "isPrintForm", 0);
		// bpmDefinition.setIsPrintForm(Short.valueOf(isPrintForm));
		//
		// Long attachment = Long.valueOf(RequestUtil.getLong(request,
		// "attachment"));
		// bpmDefinition.setAttachment(attachment);
		//
		short status = (short) RequestUtil.getInt(request, "status", 1);
		bpmDefinition.setStatus(Short.valueOf(status));
		//
		// short sameExecutorJump = (short) RequestUtil.getInt(request,
		// "sameExecutorJump", 0);
		// bpmDefinition.setSameExecutorJump(Short.valueOf(sameExecutorJump));
		//
		// short isUseOutForm = (short) RequestUtil.getInt(request,
		// "isUseOutForm", 0);
		// bpmDefinition.setIsUseOutForm(Short.valueOf(isUseOutForm));
		//
		// Integer allowRefer = Integer.valueOf(RequestUtil.getInt(request,
		// "allowRefer"));
		// bpmDefinition.setAllowRefer(allowRefer);
		//
		// Integer instanceAmount = Integer.valueOf(RequestUtil.getInt(request,
		// "instanceAmount"));
		// bpmDefinition.setInstanceAmount(instanceAmount);
		//

		//
		// String ccMessageType = RequestUtil.getString(request,
		// "ccMessageType");
		// bpmDefinition.setCcMessageType(ccMessageType);
		//
		// String testStatusTag = RequestUtil.getString(request,
		// "testStatusTag");
		// bpmDefinition.setTestStatusTag(testStatusTag);
		try {
			this.bpmDefinitionService.doSaveParam(bpmDefinition);
			printJSON("success");
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail");
		}
	}

	@Action("getCanDirectStart")
	public String getCanDirectStart() throws Exception {
		Long defId = Long.valueOf(RequestUtil.getLong(getRequest(), "defId"));
		boolean rtn = this.bpmFormRunService.getCanDirectStart(defId);
		if (rtn) {
			printJSON("200");
		} else {
			printJSON("300");
		}
		return NONE;
	}

	@Action("getFlowKey")
	public String getFlowKey() throws Exception {
		String subject = RequestUtil.getString(getRequest(), "subject");
		if (StringUtil.isEmpty(subject))
			return null;
		List<String> pinyins = Pinyin4jUtil.getPinyin(subject);
		String pinyin = pinyins.get(0).trim();
		String key = pinyin;
		int count = 0;
		do {
			key = pinyin + (count == 0 ? "" : Integer.valueOf(count));
			count++;
		} while (bpmDefinitionService.isActDefKeyExists(key));

		PrintWriter out = getResponse().getWriter();
		out.write(key);

		return NONE;
	}

	@Action("conditionEdit")
	public String conditionEdit() throws Exception {
		HttpServletRequest request = getRequest();
		Long conditionId = Long.valueOf(RequestUtil.getLong(request, "conditionId"));
		String nodeId = RequestUtil.getString(request, "nodeTag");
		this.bpmDefinition = (BpmDefinition) bpmDefinitionService.getById(definitionId);
		List setList = bpmNodeSetService.getByDefId(definitionId);
		Long currentSn = Long.valueOf(TimeUtil.getCurrentTimeMillis());
		Long formId = Long.valueOf(0L);

		NodeUserMap nodeUserMap = new NodeUserMap();
		if (!StringUtil.isEmpty(nodeId)) {
			BpmNodeSet nodeSet = bpmNodeSetService.getByDefIdNodeId(definitionId, nodeId);
			List userList = bpmNodeUserService.getBySetIdAndConditionId(nodeSet.getSetId(), conditionId);
			nodeUserMap = new NodeUserMap(nodeSet.getSetId(), nodeSet.getNodeId(), nodeSet.getNodeName(), userList);

			// formId = bpmFormDefService.getCurrentTableId(nodeSet);
			request.setAttribute("setId", nodeSet.getSetId());
			request.setAttribute("actDefId", nodeSet.getActDefId());
		}

		if ((conditionId != null) && (conditionId.longValue() != 0L)) {
			BpmUserCondition bpmUserCondition = (BpmUserCondition) bpmUserConditionService.getById(conditionId);
			if (bpmUserCondition != null) {
				// Long tableId = bpmUserCondition.getTableId();

				// if ((tableId == null) || (tableId.equals(Long.valueOf(0L))))
				// {
				// bpmUserCondition.setFormIdentity(formId);
				// }

				// if ((tableId != null) && (!formId.equals(tableId))) {
				// bpmUserCondition.setTableId(formId);
				// bpmUserCondition.setCondition("");
				// addMessage(new ResultMessage(0, "表单字段规则与现表单字段不一致,请修改表单條件"),
				// request);
				// }
			}
			request.setAttribute("bpmUserCondition", bpmUserCondition);
			request.setAttribute("nodeUserMap", nodeUserMap);
		} else {
			BpmUserCondition bpmUserCondition = new BpmUserCondition();
			bpmUserCondition.setSn(currentSn);
			// bpmUserCondition.setTableId(formId);
			request.setAttribute("bpmUserCondition", bpmUserCondition);
		}

		// List flowVars =
		// bpmFormFieldService.getFlowVarByFlowDefId(definitionId);

		request.setAttribute("nodeSetList", setList);
		// request.setAttribute("flowVars", flowVars);
		request.setAttribute("nodeTag", nodeId);
		request.setAttribute("userSetTypes", bpmNodeUserCalculationSelector.getNodeUserSetType());
		return "conditionEdit";
	}

	@Action("nodeSummary")
	public String nodeSummary() throws Exception {
		BpmDefinition bpmDefinition = (BpmDefinition) bpmDefinitionService.getById(definitionId);
		List<BpmNodeSet> nodeSetList = bpmNodeSetService.getByDefId(definitionId);
		BpmNodeSet startNodeSet = bpmNodeSetService.getBySetType(definitionId, BpmNodeSet.SetType_StartForm);
		BpmNodeSet globalNodeSet = bpmNodeSetService.getBySetType(definitionId, BpmNodeSet.SetType_GloabalForm);
		for (BpmNodeSet nodeSet : nodeSetList) {
			if (nodeSet.getNodeId() == null) {
				nodeSetList.remove(nodeSet);
				break;
			}
		}

		String actDefId = bpmDefinition.getActDefId();
		FlowNode startFlowNode = NodeCache.getStartNode(actDefId);
		List endFlowNodeList = NodeCache.getEndNode(actDefId);

		Map startScriptMap = new HashMap();
		Map endScriptMap = new HashMap();
		Map preScriptMap = new HashMap();
		Map afterScriptMap = new HashMap();

		Map assignScriptMap = new HashMap();
		Map nodeRulesMap = new HashMap();

		Map bpmFormMap = new HashMap();
		Map buttonMap = new HashMap();
		Map nodeButtonMap = new HashMap();
		Map taskReminderMap = new HashMap();
		Map mobileSetMap = new HashMap();
		Map nodeUserMap = new HashMap();
		Map formMap = new HashMap();
		Map taskApprovalItemsMap = new HashMap();
		Map globalApprovalMap = new HashMap();

		getNodeUserMap(nodeSetList, actDefId, nodeUserMap);

		geTaskApprovalItemsMap(nodeSetList, actDefId, taskApprovalItemsMap, globalApprovalMap);

		getNodeScriptMap(nodeSetList, actDefId, startScriptMap, endScriptMap, preScriptMap, afterScriptMap,
				assignScriptMap);

		getNodeRulesMap(nodeSetList, actDefId, nodeRulesMap);

		getNodeButtonMap(nodeSetList, definitionId, buttonMap, nodeButtonMap);

		getTaskReminderMap(nodeSetList, actDefId, taskReminderMap);

		getNodeSetMap(nodeSetList, bpmFormMap, mobileSetMap);

		if (checkForm(globalNodeSet).booleanValue()) {
			formMap.put("global", Boolean.valueOf(true));
		}
		if (checkForm(startNodeSet).booleanValue())
			formMap.put("start", Boolean.valueOf(true));

		HttpServletRequest request = getRequest();
		request.setAttribute("bpmDefinition", bpmDefinition);
		request.setAttribute("deployId", bpmDefinition.getActDeployId());
		request.setAttribute("defId", definitionId);
		request.setAttribute("actDefId", actDefId);
		request.setAttribute("nodeSetList", nodeSetList);
		request.setAttribute("startScriptMap", startScriptMap);
		request.setAttribute("endScriptMap", endScriptMap);
		request.setAttribute("preScriptMap", preScriptMap);
		request.setAttribute("afterScriptMap", afterScriptMap);
		request.setAttribute("assignScriptMap", assignScriptMap);
		request.setAttribute("nodeRulesMap", nodeRulesMap);
		request.setAttribute("nodeUserMap", nodeUserMap);
		request.setAttribute("bpmFormMap", bpmFormMap);
		request.setAttribute("formMap", formMap);
		request.setAttribute("buttonMap", buttonMap);
		request.setAttribute("nodeButtonMap", nodeButtonMap);
		request.setAttribute("taskReminderMap", taskReminderMap);
		request.setAttribute("taskApprovalItemsMap", taskApprovalItemsMap);
		request.setAttribute("globalApprovalMap", globalApprovalMap);
		request.setAttribute("mobileSetMap", mobileSetMap);
		request.setAttribute("startFlowNode", startFlowNode);
		request.setAttribute("endFlowNodeList", endFlowNodeList);
		return "nodeSummary";
	}

	private void getNodeUserMap(List<BpmNodeSet> nodeSetList, String actDefId, Map<Long, Boolean> nodeUserMap) {
		List bpmUserConditionList = bpmUserConditionService.getByActDefId(actDefId);

		for (Iterator localIterator1 = nodeSetList.iterator(); localIterator1.hasNext();) {
			BpmNodeSet nodeSet = (BpmNodeSet) localIterator1.next();
			for (Iterator localIterator2 = bpmUserConditionList.iterator(); localIterator2.hasNext();) {
				BpmUserCondition bpmUserCondition = (BpmUserCondition) localIterator2.next();
				if (nodeSet.getNodeId().equals(bpmUserCondition.getNodeid()))
					nodeUserMap.put(nodeSet.getSetId(), Boolean.valueOf(true));
			}
		}
	}

	private void geTaskApprovalItemsMap(List<BpmNodeSet> nodeSetList, String actDefId,
			Map<Long, Boolean> taskApprovalItemsMap, Map<String, Boolean> globalApprovalMap) {
		List<TaskApprovalItems> taskApprovalItemsList = taskApprovalItemsService.getByActDefId(actDefId);
		for (TaskApprovalItems taskApprovalItems : taskApprovalItemsList)
			if (taskApprovalItems.getIsGlobal().shortValue() == TaskApprovalItems.global.shortValue())
				globalApprovalMap.put("global", Boolean.valueOf(true));
		for (Iterator localIterator = nodeSetList.iterator(); localIterator.hasNext();) {
			BpmNodeSet nodeSet = (BpmNodeSet) localIterator.next();
			for (Iterator localIterator2 = taskApprovalItemsList.iterator(); localIterator2.hasNext();) {
				TaskApprovalItems taskApprovalItems = (TaskApprovalItems) localIterator2.next();
				if ((BeanUtils.isNotEmpty(taskApprovalItems.getNodeId()))
						&& (nodeSet.getNodeId().equals(taskApprovalItems.getNodeId())))
					taskApprovalItemsMap.put(nodeSet.getSetId(), Boolean.valueOf(true));
			}
		}
	}

	private void getNodeSetMap(List<BpmNodeSet> nodeSetList, Map<Long, Boolean> bpmFormMap,
			Map<Long, Boolean> mobileSetMap) {
		for (BpmNodeSet nodeSet : nodeSetList) {
			if (nodeSet.getIsAllowMobile().shortValue() == 1)
				mobileSetMap.put(nodeSet.getSetId(), Boolean.valueOf(true));
			if (checkForm(nodeSet).booleanValue())
				bpmFormMap.put(nodeSet.getSetId(), Boolean.valueOf(true));
		}
	}

	private Boolean checkForm(BpmNodeSet bpmNodeSet) {
		if (BeanUtils.isEmpty(bpmNodeSet))
			return Boolean.valueOf(false);
		if (bpmNodeSet.getFormType().shortValue() == BpmNodeSet.FORM_TYPE_ONLINE.shortValue()) {
			if (bpmNodeSet.getFormKey().longValue() > 0L)
				return Boolean.valueOf(true);
		} else if (bpmNodeSet.getFormType().shortValue() == BpmNodeSet.FORM_TYPE_URL.shortValue()) {
			if (StringUtil.isNotEmpty(bpmNodeSet.getFormUrl()))
				return Boolean.valueOf(true);
		}
		return Boolean.valueOf(false);
	}

	private void getNodeScriptMap(List<BpmNodeSet> nodeSetList, String actDefId, Map<String, Boolean> startScriptMap,
			Map<String, Boolean> endScriptMap, Map<Long, Boolean> preScriptMap, Map<Long, Boolean> afterScriptMap,
			Map<Long, Boolean> assignScriptMap) {
		List<BpmNodeScript> bpmNodeScriptList = bpmNodeScriptService.getByNodeScriptId(null, actDefId);

		for (BpmNodeScript bpmNodeScript : bpmNodeScriptList)
			if (StringUtil.isNotEmpty(bpmNodeScript.getNodeId()))
				if (bpmNodeScript.getScriptType().intValue() == BpmNodeScript.SCRIPT_TYPE_1.intValue())
					startScriptMap.put(bpmNodeScript.getNodeId(), Boolean.valueOf(true));
				else if (bpmNodeScript.getScriptType().intValue() == BpmNodeScript.SCRIPT_TYPE_2.intValue())
					endScriptMap.put(bpmNodeScript.getNodeId(), Boolean.valueOf(true));

		for (Iterator localIterator = nodeSetList.iterator(); localIterator.hasNext();) {
			BpmNodeSet nodeSet = (BpmNodeSet) localIterator.next();
			for (Iterator localIterator2 = bpmNodeScriptList.iterator(); localIterator2.hasNext();) {
				BpmNodeScript bpmNodeScript = (BpmNodeScript) localIterator2.next();
				if (StringUtil.isNotEmpty(bpmNodeScript.getNodeId())) {
					if (nodeSet.getNodeId().equals(bpmNodeScript.getNodeId()))
						if (bpmNodeScript.getScriptType().intValue() == BpmNodeScript.SCRIPT_TYPE_1.intValue()) {
							preScriptMap.put(nodeSet.getSetId(), Boolean.valueOf(true));
						} else if (bpmNodeScript.getScriptType().intValue() == BpmNodeScript.SCRIPT_TYPE_2.intValue()) {
							afterScriptMap.put(nodeSet.getSetId(), Boolean.valueOf(true));
						} else if (bpmNodeScript.getScriptType().intValue() == BpmNodeScript.SCRIPT_TYPE_4.intValue())
							assignScriptMap.put(nodeSet.getSetId(), Boolean.valueOf(true));
				}
			}
		}
	}

	private void getNodeRulesMap(List<BpmNodeSet> nodeSetList, String actDefId, Map<Long, Boolean> nodeRulesMap) {
		List nodeRuleList = bpmNodeRuleService.getByActDefId(actDefId);
		Iterator localIterator2;
		for (Iterator localIterator1 = nodeSetList.iterator(); localIterator1.hasNext();) {
			BpmNodeSet nodeSet = (BpmNodeSet) localIterator1.next();
			for (localIterator2 = nodeRuleList.iterator(); localIterator2.hasNext();) {
				BpmNodeRule bpmNodeRule = (BpmNodeRule) localIterator2.next();
				if (nodeSet.getNodeId().equals(bpmNodeRule.getNodeId()))
					nodeRulesMap.put(nodeSet.getSetId(), Boolean.valueOf(true));
			}
		}
	}

	private void getNodeButtonMap(List<BpmNodeSet> nodeSetList, Long defId, Map<String, Boolean> buttonMap,
			Map<Long, Boolean> nodeButtonMap) {
		List<BpmNodeButton> nodeButtonList = bpmNodeButtonService.getByDefId(defId);
		for (BpmNodeButton bpmNodeButton : nodeButtonList)
			buttonMap.put(bpmNodeButton.getNodeid(), Boolean.valueOf(true));
		Iterator localIterator2;
		for (Iterator localIterator = nodeSetList.iterator(); localIterator.hasNext();) {
			BpmNodeSet nodeSet = (BpmNodeSet) localIterator.next();
			for (localIterator2 = nodeButtonList.iterator(); localIterator2.hasNext();) {
				BpmNodeButton bpmNodeButton = (BpmNodeButton) localIterator2.next();
				if (nodeSet.getNodeId().equals(bpmNodeButton.getNodeid()))
					nodeButtonMap.put(nodeSet.getSetId(), Boolean.valueOf(true));
			}
		}
	}

	private void getTaskReminderMap(List<BpmNodeSet> nodeSetList, String actDefId, Map<Long, Boolean> taskReminderMap) {
		List taskReminderList = taskReminderService.getByActDefId(actDefId);

		for (Iterator localIterator1 = nodeSetList.iterator(); localIterator1.hasNext();) {
			BpmNodeSet nodeSet = (BpmNodeSet) localIterator1.next();
			for (Iterator localIterator2 = taskReminderList.iterator(); localIterator2.hasNext();) {
				TaskReminder taskReminder = (TaskReminder) localIterator2.next();
				if (nodeSet.getNodeId().equals(taskReminder.getNodeId()))
					taskReminderMap.put(nodeSet.getSetId(), Boolean.valueOf(true));
			}
		}
	}

	private String getAllBpmType() {
		List<BpmType> bpmTypes = bpmTypeService.findAll();
		StringBuffer sb = new StringBuffer("<folder id='0' label='全部'>");
		if (BeanUtils.isNotEmpty(bpmTypes)) {
			for (BpmType type : bpmTypes) {
				if (type.getId() != 1L) {
					sb.append("<folder id='" + type.getId() + "' label='" + type.getName() + "'>");
					sb.append("</folder>");
				}
			}
		}
		sb.append("</folder>");
		return sb.toString();
	}

	public String getXmlRecord() {
		return xmlRecord;
	}

	public void setXmlRecord(String xmlRecord) {
		this.xmlRecord = xmlRecord;
	}

	public BpmDefinition getBpmDefinition() {
		return bpmDefinition;
	}

	public void setBpmDefinition(BpmDefinition bpmDefinition) {
		this.bpmDefinition = bpmDefinition;
	}

	public Long getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(Long definitionId) {
		this.definitionId = definitionId;
	}

	public List<BpmDefinition> getBpmDefinitionList() {
		return bpmDefinitionList;
	}

	public void setBpmDefinitionList(List<BpmDefinition> bpmDefinitionList) {
		this.bpmDefinitionList = bpmDefinitionList;
	}

	public String getDefKey() {
		return defKey;
	}

	public void setDefKey(String defKey) {
		this.defKey = defKey;
	}

	// @RequestMapping({ "bpmnXml" })
	// public ModelAndView bpmnXml(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// long id = RequestUtil.getLong(request, "defId");
	// BpmDefinition po = (BpmDefinition)
	// bpmDefinitionService.getById(Long.valueOf(id));
	// if (po.getActDeployId() != null) {
	// String bpmnXml =
	// bpmService.getDefXmlByDeployId(po.getActDeployId().toString());
	//
	// if (bpmnXml.startsWith("<?xml version=\"1.0\" encoding=\"utf-8\"?>")) {
	// bpmnXml = bpmnXml.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>",
	// "");
	// }
	// if (bpmnXml.startsWith("<?xml version=\"1.0\" encoding=\"GBK\"?>")) {
	// bpmnXml = bpmnXml.replace("<?xml version=\"1.0\" encoding=\"GBK\"?>",
	// "");
	// }
	// bpmnXml = bpmnXml.trim();
	//
	// request.setAttribute("bpmnXml", bpmnXml);
	// }
	// return getAutoView();
	// }
	//
	// @RequestMapping({ "designXml" })
	// public ModelAndView designXml(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// long id = RequestUtil.getLong(request, "defId");
	// BpmDefinition po = (BpmDefinition)
	// bpmDefinitionService.getById(Long.valueOf(id));
	// String defXml = po.getDefXml();
	// if
	// (defXml.trim().startsWith("<?xml version=\"1.0\" encoding=\"utf-8\"?>"))
	// {
	// defXml = defXml.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>",
	// "");
	// }
	// request.setAttribute("designXml", defXml);
	// return getAutoView();
	// }

}