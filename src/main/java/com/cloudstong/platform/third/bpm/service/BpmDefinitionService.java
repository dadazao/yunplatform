package com.cloudstong.platform.third.bpm.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.bpmn20.entity.CallActivity;
import com.cloudstong.platform.core.bpmn20.entity.FlowElement;
import com.cloudstong.platform.core.bpmn20.entity.Process;
import com.cloudstong.platform.core.bpmn20.entity.UserTask;
import com.cloudstong.platform.core.bpmn20.entity.ht.BPMN20HtExtConst;
import com.cloudstong.platform.core.bpmn20.util.BPMN20Util;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.Dom4jUtil;
import com.cloudstong.platform.core.util.MsgUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.core.util.XmlBeanUtil;
import com.cloudstong.platform.core.xml.BpmDefinitionXml;
import com.cloudstong.platform.core.xml.BpmDefinitionXmlList;
import com.cloudstong.platform.third.bpm.dao.BpmAgentDao;
import com.cloudstong.platform.third.bpm.dao.BpmDao;
import com.cloudstong.platform.third.bpm.dao.BpmDefRightsDao;
import com.cloudstong.platform.third.bpm.dao.BpmDefVarDao;
import com.cloudstong.platform.third.bpm.dao.BpmDefinitionDao;
import com.cloudstong.platform.third.bpm.dao.BpmFormRunDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeButtonDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeMessageDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodePrivilegeDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeRuleDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeScriptDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeSetDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeSignDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeUserDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeUserUplowDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeWebServiceDao;
import com.cloudstong.platform.third.bpm.dao.BpmProStatusDao;
import com.cloudstong.platform.third.bpm.dao.BpmTaskCommentDao;
import com.cloudstong.platform.third.bpm.dao.BpmUserConditionDao;
import com.cloudstong.platform.third.bpm.dao.ExecutionDao;
import com.cloudstong.platform.third.bpm.dao.ExecutionStackDao;
import com.cloudstong.platform.third.bpm.dao.GlobalTypeDao;
import com.cloudstong.platform.third.bpm.dao.MessageDao;
import com.cloudstong.platform.third.bpm.dao.ProcessRunDao;
import com.cloudstong.platform.third.bpm.dao.ReminderStateDao;
import com.cloudstong.platform.third.bpm.dao.TaskApprovalItemsDao;
import com.cloudstong.platform.third.bpm.dao.TaskDao;
import com.cloudstong.platform.third.bpm.dao.TaskOpinionDao;
import com.cloudstong.platform.third.bpm.dao.TaskReminderDao;
import com.cloudstong.platform.third.bpm.dao.TaskSignDataDao;
import com.cloudstong.platform.third.bpm.form.dao.BpmFormDefDao;
import com.cloudstong.platform.third.bpm.model.BpmDefRights;
import com.cloudstong.platform.third.bpm.model.BpmDefVar;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.model.BpmNodeButton;
import com.cloudstong.platform.third.bpm.model.BpmNodeMessage;
import com.cloudstong.platform.third.bpm.model.BpmNodeRule;
import com.cloudstong.platform.third.bpm.model.BpmNodeScript;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;
import com.cloudstong.platform.third.bpm.model.BpmNodeSign;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.model.BpmNodeUserUplow;
import com.cloudstong.platform.third.bpm.model.BpmUserCondition;
import com.cloudstong.platform.third.bpm.model.GlobalType;
import com.cloudstong.platform.third.bpm.model.NodeCache;
import com.cloudstong.platform.third.bpm.model.TaskApprovalItems;
import com.cloudstong.platform.third.bpm.model.TaskReminder;
import com.cloudstong.platform.third.bpm.util.BpmUtil;

@Service
public class BpmDefinitionService {

	@Resource
	private BpmDefinitionDao dao;

	@Resource
	private BpmNodeSetDao bpmNodeSetDao;

	@Resource
	private BpmDefVarDao bpmDefVarDao;

	@Resource
	private BpmService bpmService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private BpmNodeSignDao bpmNodeSignDao;

	@Resource
	private BpmNodeRuleDao bpmNodeRuleDao;

	@Resource
	private TaskSignDataDao taskSignDataDao;

	@Resource
	private BpmNodeMessageDao bpmNodeMessageDao;

	@Resource
	private MessageDao messageDao;

	@Resource
	private BpmDefVarDao bpmDefVarsDao;

	@Resource
	private ExecutionStackDao executionStackDao;

	@Resource
	private BpmNodeUserDao bpmNodeUserDao;

	@Resource
	private BpmTaskCommentDao bpmTaskCommentDao;

	@Resource
	private BpmNodeScriptDao bpmNodeScriptDao;

	@Resource
	private BpmAgentDao bpmAgentDao;

	@Resource
	private BpmDefRightsDao bpmDefRightDao;

	@Resource
	private BpmNodeButtonDao bpmNodeButtonDao;

	@Resource
	private TaskApprovalItemsDao taskApprovalItemsDao;

	@Resource
	private TaskReminderDao taskReminderDao;

	@Resource
	private BpmDefRightsDao bpmDefRightsDao;

	@Resource
	private BpmNodeUserUplowDao bpmNodeUserUplowDao;

	@Resource
	private BpmUserConditionDao bpmUserConditionDao;

	@Resource
	private GlobalTypeDao globalTypeDao;

	@Resource
	private BpmDao bpmDao;

//	@Resource
//	private BpmFormDefService bpmFormDefService;

	@Resource
	private BpmFormDefDao bpmFormDefDao;

//	@Resource
//	private BpmFormTableService bpmFormTableService;

	@Resource
	private ProcessRunDao processRunDao;

	@Resource
	private BpmFormRunDao bpmFormRunDao;

	@Resource
	private BpmNodePrivilegeDao bpmNodePrivilegeDao;

	@Resource
	private BpmNodeWebServiceDao bpmNodeWebServiceDao;

	@Resource
	private BpmProStatusDao bpmProStatusDao;

	@Resource
	private TaskForkService taskForkService;

	@Resource
	private TaskOpinionDao taskOpinionDao;

	@Resource
	private ReminderStateDao reminderStateDao;

	@Resource
	private BpmAgentService bpmAgentService;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private TaskDao taskDao;

	@Resource
	private ExecutionDao executionDao;

	public void deploy(BpmDefinition bpmDefinition, String actFlowDefXml) throws Exception {
		Deployment deployment = bpmService.deploy(bpmDefinition.getSubject(), actFlowDefXml);
		ProcessDefinitionEntity ent = bpmService.getProcessDefinitionByDeployId(deployment.getId());
		bpmDefinition.setActDeployId(new Long(deployment.getId()));
		bpmDefinition.setActDefId(ent.getId());
		bpmDefinition.setActDefKey(ent.getKey());
		bpmDefinition.setStatus(BpmDefinition.STATUS_DEPLOYED);
		dao.update(bpmDefinition);

		saveOrUpdateNodeSet(actFlowDefXml, bpmDefinition, false);
	}

	public void doSaveOrUpdate(BpmDefinition bpmDefinition, boolean isDeploy, String actFlowDefXml) throws Exception {
		Long oldDefId = bpmDefinition.getDefId();

		Long newDefId = bpmDefinition.getDefId();

		boolean isUpdate = false;

		if ((bpmDefinition.getDefId() == null) || (bpmDefinition.getDefId().longValue() == 0L)) {
			if (isDeploy) {
				Deployment deployment = bpmService.deploy(bpmDefinition.getSubject(), actFlowDefXml);
				ProcessDefinitionEntity ent = bpmService.getProcessDefinitionByDeployId(deployment.getId());
				bpmDefinition.setActDeployId(new Long(deployment.getId()));
				bpmDefinition.setActDefId(ent.getId());
				bpmDefinition.setActDefKey(ent.getKey());
			}
			bpmDefinition.setVersionNo(Integer.valueOf(1));

			bpmDefinition.setDefId(Long.valueOf(UniqueIdUtil.genId()));

			bpmDefinition.setIsMain(BpmDefinition.MAIN);
			bpmDefinition.setCreatetime(new Date());
			bpmDefinition.setUpdatetime(new Date());
			Short status = isDeploy ? BpmDefinition.STATUS_DEPLOYED : BpmDefinition.STATUS_NOTDEPLOYED;
			bpmDefinition.setStatus(status);
			dao.save(bpmDefinition);

			if (isDeploy) {
				saveOrUpdateNodeSet(actFlowDefXml, bpmDefinition, true);
			}

		} else if (isDeploy) {
			newDefId = Long.valueOf(UniqueIdUtil.genId());
			dao.updateSubVersions(newDefId, bpmDefinition.getDefKey());

			Deployment deployment = bpmService.deploy(bpmDefinition.getSubject(), actFlowDefXml);
			ProcessDefinitionEntity ent = bpmService.getProcessDefinitionByDeployId(deployment.getId());
			String actDefId = ent.getId();

			BpmDefinition newBpmDefinition = (BpmDefinition) bpmDefinition.clone();

			newBpmDefinition.setVersionNo(Integer.valueOf(ent.getVersion()));
			newBpmDefinition.setActDeployId(new Long(deployment.getId()));
			newBpmDefinition.setActDefId(actDefId);
			newBpmDefinition.setActDefKey(ent.getKey());

			newBpmDefinition.setDefId(newDefId);
			newBpmDefinition.setParentDefId(newDefId);
			newBpmDefinition.setCreatetime(new Date());
			newBpmDefinition.setUpdatetime(new Date());
			newBpmDefinition.setStatus(BpmDefinition.STATUS_DEPLOYED);

			newBpmDefinition.setIsMain(BpmDefinition.MAIN);

			dao.save(newBpmDefinition);

			isUpdate = true;

			saveOrUpdateNodeSet(actFlowDefXml, newBpmDefinition, true);

			syncStartGlobal(oldDefId, newDefId, actDefId);
		} else {
			if (bpmDefinition.getActDeployId() != null) {
				bpmService.wirteDefXml(bpmDefinition.getActDeployId().toString(), actFlowDefXml);

				saveOrUpdateNodeSet(actFlowDefXml, bpmDefinition, false);

				String actDefId = bpmDefinition.getActDefId();

				NodeCache.clear(actDefId);
			}
				bpmDefinition.setIsMain(BpmDefinition.MAIN);
			dao.update(bpmDefinition);
		}

		if (isUpdate)
			saveOrUpdateBpmDefSeting(newDefId, oldDefId, actFlowDefXml, bpmDefinition.getDefKey());
	}

	private void saveOrUpdateNodeSet(String actFlowDefXml, BpmDefinition bpmDefinition, boolean isAdd) throws Exception {
		Long defId = bpmDefinition.getDefId();
		List processes = BPMN20Util.getProcess(actFlowDefXml);
		if (processes.size() == 0) {
			return;
		}

		Class[] classes = { UserTask.class, CallActivity.class };

		List<FlowElement> flowElements = BPMN20Util.getFlowElementByType((Process) processes.get(0), true, classes);
		if (isAdd) {
			for (FlowElement flowElement : flowElements)
				addNodeSet(bpmDefinition, flowElement);
		} else {
			Map nodeSetMap = bpmNodeSetDao.getMapByDefId(defId);

			delNodeSet(nodeSetMap, flowElements);

			updNodeSet(bpmDefinition, nodeSetMap, flowElements);
		}
	}

	private void updNodeSet(BpmDefinition bpmDefinition, Map<String, BpmNodeSet> oldSetMap, List<FlowElement> flowNodes) throws Exception {
		for (FlowElement flowElement : flowNodes)
			if (oldSetMap.containsKey(flowElement.getId())) {
				Integer nodeOrder = Integer.valueOf(0);
				List extensions = BPMN20Util.getFlowElementExtension(flowElement, BPMN20HtExtConst._Order_QNAME);

				if (BeanUtils.isNotEmpty(extensions)) {
					nodeOrder = (Integer) extensions.get(0);
				}
				BpmNodeSet bpmNodeSet = (BpmNodeSet) oldSetMap.get(flowElement.getId());
				bpmNodeSet.setNodeName(flowElement.getName());
				bpmNodeSet.setNodeOrder(nodeOrder);
				bpmNodeSetDao.update(bpmNodeSet);
			} else {
				addNodeSet(bpmDefinition, flowElement);
			}
	}

	private void delNodeSet(Map<String, BpmNodeSet> oldSetMap, List<FlowElement> flowNodes) {
		Iterator keys = oldSetMap.keySet().iterator();
		while (keys.hasNext()) {
			String nodeId = (String) keys.next();
			boolean inflag = false;
			for (FlowElement flowNode : flowNodes) {
				if (flowNode.getId().equals(nodeId)) {
					inflag = true;
					break;
				}
			}
			if (!inflag) {
				BpmNodeSet bpmNodeSet = (BpmNodeSet) oldSetMap.get(nodeId);
				bpmNodeSetDao.delById(bpmNodeSet.getSetId());
			}
		}
	}

	private void addNodeSet(BpmDefinition bpmDefinition, FlowElement flowNode) throws Exception {
		Long defId = bpmDefinition.getDefId();
		String actDefId = bpmDefinition.getActDefId();
		Integer nodeOrder = Integer.valueOf(0);
		List extensions = BPMN20Util.getFlowElementExtension(flowNode, BPMN20HtExtConst._Order_QNAME);

		if (BeanUtils.isNotEmpty(extensions)) {
			nodeOrder = (Integer) extensions.get(0);
		}

		BpmNodeSet bpmNodeSet = new BpmNodeSet();
		bpmNodeSet.setSetId(Long.valueOf(UniqueIdUtil.genId()));
		bpmNodeSet.setFormType(Short.valueOf((short) -1));
		bpmNodeSet.setActDefId(actDefId);
		bpmNodeSet.setDefId(defId);
		bpmNodeSet.setNodeId(flowNode.getId());
		bpmNodeSet.setNodeName(flowNode.getName());
		bpmNodeSet.setNodeOrder(nodeOrder);
		bpmNodeSetDao.save(bpmNodeSet);
	}

	private void saveOrUpdateBpmDefSeting(Long newDefId, Long oldDefId, String actFlowDefXml, String defKey) throws Exception {
		if ((oldDefId == null) || (oldDefId.longValue() <= 0L))
			return;

		BpmDefinition newDef = (BpmDefinition) dao.getById(newDefId);
		BpmDefinition oldDef = (BpmDefinition) dao.getById(oldDefId);

		String newActDefId = newDef.getActDefId();
		String oldActDefId = oldDef.getActDefId();
		if (oldActDefId == null)
			return;

		List<BpmDefVar> defVarList = bpmDefVarsDao.getByDefId(oldDefId);
		if (BeanUtils.isNotEmpty(defVarList)) {
			for (BpmDefVar o : defVarList) {
				BpmDefVar n = (BpmDefVar) o.clone();
				n.setVarId(Long.valueOf(UniqueIdUtil.genId()));
				n.setDefId(newDefId);
				bpmDefVarsDao.save(n);
			}

		}

		List nodeScripts = bpmNodeScriptDao.getByActDefId(oldActDefId);
		Map taskActivitysMap = BpmUtil.getTaskActivitys(actFlowDefXml);
		Map startActivitysMap = (Map) taskActivitysMap.get("开始节点");
		Map endActivitysMap = (Map) taskActivitysMap.get("结束节点");
		Map seActivitysMap = new HashMap();
		if (!BeanUtils.isEmpty(startActivitysMap)) {
			seActivitysMap.putAll(startActivitysMap);
		}
		if (!BeanUtils.isEmpty(endActivitysMap)) {
			seActivitysMap.putAll(endActivitysMap);
		}
		Iterator seNodeIds = seActivitysMap.keySet().iterator();
		for (; seNodeIds.hasNext();) {
			String nodeId = (String) seNodeIds.next();
			for (Iterator localIterator2 = nodeScripts.iterator(); localIterator2.hasNext();) {
				BpmNodeScript script = (BpmNodeScript) localIterator2.next();
				if (script.getNodeId().equals(nodeId)) {
					BpmNodeScript newScript = (BpmNodeScript) script.clone();
					newScript.setId(Long.valueOf(UniqueIdUtil.genId()));
					newScript.setActDefId(newActDefId);
					bpmNodeScriptDao.save(newScript);
				}
			}

		}

		TaskApprovalItems globalTApproval = taskApprovalItemsDao.getFlowApproval(oldActDefId, TaskApprovalItems.global.shortValue());
		if (BeanUtils.isNotEmpty(globalTApproval)) {
			globalTApproval.setActDefId(newActDefId);
			globalTApproval.setItemId(Long.valueOf(UniqueIdUtil.genId()));
			taskApprovalItemsDao.save(globalTApproval);
		}

		List<BpmNodeSet> newNodeSetList = bpmNodeSetDao.getByDefId(newDefId);
		Map oldNodeSetMap = bpmNodeSetDao.getMapByDefId(oldDefId);
		if ((BeanUtils.isEmpty(newNodeSetList)) || (BeanUtils.isEmpty(oldNodeSetMap)))
			return;
		for (BpmNodeSet bpmNodeSet : newNodeSetList) {
			String nodeId = bpmNodeSet.getNodeId();
			if (oldNodeSetMap.containsKey(nodeId)) {
				BpmNodeSet oldBpmNodeSet = (BpmNodeSet) oldNodeSetMap.get(nodeId);
				Long oldSetId = oldBpmNodeSet.getSetId();

				bpmNodeSet.setAfterHandler(oldBpmNodeSet.getAfterHandler());
				bpmNodeSet.setBeforeHandler(oldBpmNodeSet.getBeforeHandler());
				bpmNodeSet.setFormDefId(oldBpmNodeSet.getFormDefId());
				bpmNodeSet.setFormDefName(oldBpmNodeSet.getFormDefName());
				bpmNodeSet.setFormKey(oldBpmNodeSet.getFormKey());
				bpmNodeSet.setFormType(oldBpmNodeSet.getFormType());
				bpmNodeSet.setFormUrl(oldBpmNodeSet.getFormUrl());
				bpmNodeSet.setIsHideOption(oldBpmNodeSet.getIsHideOption());
				bpmNodeSet.setIsHidePath(oldBpmNodeSet.getIsHidePath());
				bpmNodeSet.setIsJumpForDef(oldBpmNodeSet.getIsJumpForDef());
				bpmNodeSet.setJoinTaskKey(oldBpmNodeSet.getJoinTaskKey());
				bpmNodeSet.setJoinTaskName(oldBpmNodeSet.getJoinTaskName());
				bpmNodeSet.setJumpType(oldBpmNodeSet.getJumpType());
				bpmNodeSet.setOldFormKey(oldBpmNodeSet.getOldFormKey());
				bpmNodeSetDao.update(bpmNodeSet);

				List<BpmNodeRule> nodeRuleList = bpmNodeRuleDao.getByDefIdNodeId(oldActDefId, nodeId);
				BpmNodeRule nR;
				if (BeanUtils.isNotEmpty(nodeRuleList)) {
					for (BpmNodeRule oR : nodeRuleList) {
						nR = (BpmNodeRule) oR.clone();
						nR.setRuleId(Long.valueOf(UniqueIdUtil.genId()));
						nR.setActDefId(newActDefId);
						bpmNodeRuleDao.save(nR);
					}

				}

				List<BpmNodeScript> nodeScriptList = bpmNodeScriptDao.getByBpmNodeScriptId(nodeId, oldActDefId);
				BpmNodeScript nS;
				if (BeanUtils.isNotEmpty(nodeScriptList)) {
					for (BpmNodeScript oS : nodeScriptList) {
						nS = (BpmNodeScript) oS.clone();
						nS.setId(Long.valueOf(UniqueIdUtil.genId()));
						nS.setActDefId(newActDefId);
						bpmNodeScriptDao.save(nS);
					}

				}

				List<BpmUserCondition> userConditionList = bpmUserConditionDao.getBySetId(oldSetId);
				BpmUserCondition nC;
				BpmNodeUser oU;
				BpmNodeUser nU;
				if (BeanUtils.isNotEmpty(userConditionList)) {
					for (BpmUserCondition oC : userConditionList) {
						nC = (BpmUserCondition) oC.clone();
						nC.setId(Long.valueOf(UniqueIdUtil.genId()));
						nC.setActdefid(newActDefId);
						nC.setSetId(bpmNodeSet.getSetId());
						bpmUserConditionDao.save(nC);

						List nodeUserList = bpmNodeUserDao.getBySetIdAndConditionId(oC.getSetId(), oC.getId());
						if (BeanUtils.isNotEmpty(nodeUserList)) {
							for (Iterator localIterator5 = nodeUserList.iterator(); localIterator5.hasNext();) {
								oU = (BpmNodeUser) localIterator5.next();
								nU = (BpmNodeUser) oU.clone();

								nU.setNodeUserId(Long.valueOf(UniqueIdUtil.genId()));
								nU.setActDefId(newActDefId);
								nU.setSetId(bpmNodeSet.getSetId());
								nU.setConditionId(nC.getId());
								bpmNodeUserDao.save(nU);
							}
						}
					}

				}

				List<BpmNodeMessage> nodeMessageList = bpmNodeMessageDao.getByActDefId(oldActDefId);
				if (BeanUtils.isNotEmpty(nodeMessageList)) {
					for (BpmNodeMessage oM : nodeMessageList) {
						BpmNodeMessage nM = (BpmNodeMessage) oM.clone();
						nM.setId(Long.valueOf(UniqueIdUtil.genId()));
						nM.setActDefId(newActDefId);
						bpmNodeMessageDao.save(nM);
					}

				}

				BpmNodeSign nodeSign = bpmNodeSignDao.getByDefIdAndNodeId(oldActDefId, nodeId);
				if (BeanUtils.isNotEmpty(nodeSign)) {
					BpmNodeSign newSign = (BpmNodeSign) nodeSign.clone();
					newSign.setSignId(Long.valueOf(UniqueIdUtil.genId()));
					newSign.setActDefId(newActDefId);
					bpmNodeSignDao.save(newSign);
				}

				List<BpmNodeButton> nodeButtonList = bpmNodeButtonDao.getByDefNodeId(oldDefId, nodeId);
				if (BeanUtils.isNotEmpty(nodeButtonList)) {
					for (BpmNodeButton oB : nodeButtonList) {
						BpmNodeButton nB = oB;
						nB.setId(Long.valueOf(UniqueIdUtil.genId()));
						nB.setActdefid(newActDefId);
						nB.setDefId(newDefId);
						bpmNodeButtonDao.save(nB);
					}
				}

				TaskApprovalItems approvalItems = taskApprovalItemsDao.getTaskApproval(oldActDefId, nodeId, TaskApprovalItems.notGlobal.shortValue());
				if (BeanUtils.isNotEmpty(approvalItems)) {
					approvalItems.setActDefId(newActDefId);
					approvalItems.setSetId(bpmNodeSet.getSetId());
					approvalItems.setItemId(Long.valueOf(UniqueIdUtil.genId()));
					taskApprovalItemsDao.save(approvalItems);
				}

				List<TaskReminder> taskReminders = taskReminderDao.getByActDefAndNodeId(oldActDefId, nodeId);
				for (TaskReminder taskReminder : taskReminders) {
					taskReminder.setActDefId(newActDefId);
					taskReminder.setTaskDueId(Long.valueOf(UniqueIdUtil.genId()));
					taskReminderDao.save(taskReminder);
				}
			}
		}
	}

	private void syncStartGlobal(Long oldDefId, Long newDefId, String newActDefId) throws Exception {
		BpmNodeSet nodeSet = bpmNodeSetDao.getByStartGlobal(oldDefId);
		if(nodeSet != null) {
			nodeSet.setSetId(Long.valueOf(UniqueIdUtil.genId()));
			nodeSet.setDefId(newDefId);
			nodeSet.setActDefId(newActDefId);
			bpmNodeSetDao.save(nodeSet);
		}
	}

	public List<BpmDefinition> getAllHistoryVersions(Long defId) {
		return dao.getByParentDefIdIsMain(defId, BpmDefinition.NOT_MAIN);
	}

	public BpmDefinition getByActDefId(String actDefId) {
		return dao.getByActDefId(actDefId);
	}

	public List<BpmDefinition> getByTypeId(Long typeId) {
		return dao.getByTypeId(typeId);
	}

	public List<BpmDefinition> getAllForAdmin(QueryCriteria queryFilter) {
		return dao.getAllForAdmin(queryFilter);
	}

	public int doSaveParam(BpmDefinition bpmDefinition) {
		return dao.saveParam(bpmDefinition);
	}

	public void delDefbyDeployId(Long flowDefId, boolean isOnlyVersion) {
		if (BeanUtils.isEmpty(flowDefId))
			return;

		BpmDefinition definition = (BpmDefinition) dao.getById(flowDefId);

		if (definition.getActDeployId() == null) {
			dao.delById(definition.getDefId());
			return;
		}

		if (isOnlyVersion) {
			delBpmDefinition(definition);
			return;
		}

		String actFlowKey = definition.getActDefKey();

		List<BpmDefinition> list = dao.getByActDefKey(actFlowKey);

		for (BpmDefinition bpmDefinition : list) {
			delBpmDefinition(bpmDefinition);
		}
	}

	private void delBpmDefinition(BpmDefinition bpmDefinition) {
		Long actDeployId = bpmDefinition.getActDeployId();
		Long defId = bpmDefinition.getDefId();
		String actDefId = bpmDefinition.getActDefId();
		if (StringUtil.isNotEmpty(actDefId)) {
			bpmFormRunDao.delByActDefId(actDefId);

			bpmNodeButtonDao.delByActDefId(actDefId);

			bpmNodeMessageDao.delByActDefId(actDefId);

			bpmNodePrivilegeDao.delByActDefId(actDefId);

			bpmNodeRuleDao.delByActDefId(actDefId);

			bpmNodeScriptDao.delByActDefId(actDefId);

			bpmNodeWebServiceDao.delByActDefId(actDefId);

			bpmProStatusDao.delByActDefId(actDefId);

			taskReminderDao.delByActDefId(actDefId);

			taskForkService.delByActDefId(actDefId);

			processRunService.delByActDefId(actDefId);

			taskOpinionDao.delByActDefId(actDefId);

			reminderStateDao.delByActDefId(actDefId);

			bpmUserConditionDao.delByActDefId(actDefId);

			bpmAgentService.delByActDefId(actDefId);

			executionStackDao.delByActDefId(actDefId);

			bpmTaskCommentDao.delByactDefId(actDefId);

			taskSignDataDao.delByIdActDefId(actDefId);
		}
		if ((actDeployId != null) && (actDeployId.longValue() > 0L)) {
			dao.delProcDefByActDeployId(actDeployId);
		}

		bpmDefRightDao.delByDefKey(bpmDefinition.getDefKey());

		bpmDefVarsDao.delByDefId(defId);

		bpmNodeSetDao.delByDefId(defId);

		dao.delById(defId);
	}

	public void importXml(InputStream inputStream) throws Exception {
		Document doc = Dom4jUtil.loadXml(inputStream);
		Element root = doc.getRootElement();

		checkXMLFormat(root);

		String xmlStr = root.asXML();
		BpmDefinitionXmlList bpmDefinitionXmlList = (BpmDefinitionXmlList) XmlBeanUtil.unmarshall(xmlStr, BpmDefinitionXmlList.class);
		List<BpmDefinitionXml> list = bpmDefinitionXmlList.getBpmDefinitionXmlList();

		for (BpmDefinitionXml bpmDefinitionXml : list) {
			importBpmDefinitionXml(bpmDefinitionXml);
			MsgUtil.addSplit();
		}
	}

	private void checkXMLFormat(Element root) throws Exception {
		String msg = "导入文件格式不对";
		if (!root.getName().equals("bpm"))
			throw new Exception(msg);
		List<Element> itemLists = root.elements();
		for (Element elm : itemLists)
			if (!elm.getName().equals("bpmDefinitions"))
				throw new Exception(msg);
	}

	private void importBpmDefinitionXml(BpmDefinitionXml bpmDefinitionXml) throws Exception {
//		List<BpmFormTableXml> bpmFormTableXmlList = bpmDefinitionXml.getBpmFormTableXmlList();
//		if (BeanUtils.isNotEmpty(bpmFormTableXmlList)) {
//			for (BpmFormTableXml bpmFormTableXml : bpmFormTableXmlList) {
//				bpmFormTableService.importBpmFormTable(bpmFormTableXml, new Long(0L));
//			}
//
//		}
//
//		List<BpmFormDefXml> bpmFormDefXmlList = bpmDefinitionXml.getBpmFormDefXmlList();
//		if (BeanUtils.isNotEmpty(bpmFormDefXmlList)) {
//			for (BpmFormDefXml bpmFormDefXml : bpmFormDefXmlList) {
//				bpmFormDefService.importBpmFormDef(bpmFormDefXml, BpmFormDef.IS_DEFAULT);
//			}
//		}
//		importBpmDefinition(bpmDefinitionXml, null);
	}

	private void importBpmDefinition(BpmDefinitionXml bpmDefinitionXml, Long parentDefId) throws Exception {
		BpmDefinition bpmDefinition = bpmDefinitionXml.getBpmDefinition();
		BpmDefinition definition = (BpmDefinition) dao.getById(bpmDefinition.getDefId());
		if (BeanUtils.isEmpty(definition)) {
			importDefinition(bpmDefinition, parentDefId);
		} else {
			MsgUtil.addMsg(2, "流程定义,ID:" + bpmDefinition.getDefId() + "已经存在,该记录终止导入！");
			return;
		}
		String actDefId = bpmDefinition.getActDefId();
		Long defId = bpmDefinition.getDefId();

		List<BpmDefinitionXml> bpmDefinitionXmlList = bpmDefinitionXml.getBpmDefinitionXmlList();
		if (BeanUtils.isNotEmpty(bpmDefinitionXmlList)) {
			for (BpmDefinitionXml definitionXml : bpmDefinitionXmlList) {
				importBpmDefinition(definitionXml, defId);
			}

		}

		List<BpmDefinitionXml> subBpmDefinitionXmlList = bpmDefinitionXml.getSubBpmDefinitionXmlList();

		if (BeanUtils.isNotEmpty(subBpmDefinitionXmlList)) {
			for (BpmDefinitionXml definitionXml : subBpmDefinitionXmlList) {
				importBpmDefinition(definitionXml, null);
			}

		}

		List bpmDefRightsList = bpmDefinitionXml.getBpmDefRightsList();
		if (BeanUtils.isNotEmpty(bpmDefRightsList)) {
			importBpmDefRights(bpmDefRightsList, defId);
		}

		List bpmNodeRuleList = bpmDefinitionXml.getBpmNodeRuleList();
		if (BeanUtils.isNotEmpty(bpmNodeRuleList)) {
			importBpmNodeRule(bpmNodeRuleList, actDefId);
		}

		List bpmNodeScriptList = bpmDefinitionXml.getBpmNodeScriptList();
		if (BeanUtils.isNotEmpty(bpmNodeScriptList)) {
			importBpmNodeScript(bpmNodeScriptList, actDefId);
		}

		List bpmDefVarList = bpmDefinitionXml.getBpmDefVarList();
		if (BeanUtils.isNotEmpty(bpmDefVarList)) {
			importBpmDefVar(bpmDefVarList, defId);
		}

		List bpmNodeSignList = bpmDefinitionXml.getBpmNodeSignList();
		if (BeanUtils.isNotEmpty(bpmNodeSignList)) {
			importBpmNodeSign(bpmNodeSignList, actDefId);
		}

		List bpmNodeMessageList = bpmDefinitionXml.getBpmNodeMessageList();
		importBpmNodeMessage(bpmNodeMessageList, actDefId);

		List taskApprovalItemsList = bpmDefinitionXml.getTaskApprovalItemsList();

		List bpmNodeUserList = bpmDefinitionXml.getBpmNodeUserList();

		List bpmNodeUserUplowList = bpmDefinitionXml.getBpmNodeUserUplowList();
		List bpmUserConditionList = bpmDefinitionXml.getBpmUserConditionList();
		if (BeanUtils.isNotEmpty(bpmUserConditionList)) {
			importBpmUserCondition(bpmUserConditionList, defId, actDefId);
		}
		List bpmNodeSetList = bpmDefinitionXml.getBpmNodeSetList();
		if (BeanUtils.isNotEmpty(bpmNodeSetList)) {
			importBpmNodeSet(bpmNodeSetList, bpmNodeUserList, bpmNodeUserUplowList, taskApprovalItemsList, defId, actDefId);
		}

		List bpmNodeButtonList = bpmDefinitionXml.getBpmNodeButtonList();
		if (BeanUtils.isNotEmpty(bpmNodeButtonList)) {
			importBpmNodeButton(bpmNodeButtonList, defId, actDefId);
		}

		List taskReminderList = bpmDefinitionXml.getTaskReminderList();
		if (BeanUtils.isNotEmpty(taskReminderList))
			importTaskReminder(taskReminderList, actDefId);
	}

	private void importDefinition(BpmDefinition bpmDefinition, Long parentDefId) throws Exception {
		String actFlowDefXml = BpmUtil.transform(bpmDefinition.getDefKey(), bpmDefinition.getSubject(), bpmDefinition.getDefXml());
		saveBpmDefinition(bpmDefinition, actFlowDefXml);
	}

	private void saveBpmDefinition(BpmDefinition bpmDefinition, String actFlowDefXml) throws Exception {
		Long id = bpmDefinition.getDefId();
		BpmDefinition definition = (BpmDefinition) dao.getById(id);

		if (BeanUtils.isEmpty(definition)) {
			Deployment deployment = bpmService.deploy(bpmDefinition.getSubject(), actFlowDefXml);
			ProcessDefinitionEntity ent = bpmService.getProcessDefinitionByDeployId(deployment.getId());

			bpmDefinition = setTypeId(bpmDefinition);
			bpmDefinition.setActDeployId(new Long(deployment.getId()));
			bpmDefinition.setActDefId(ent.getId());
			bpmDefinition.setActDefKey(ent.getKey());
			dao.save(bpmDefinition);
			MsgUtil.addMsg(1, "流程定义,ID:" + id + ",该记录成功导入！");
		} else {
			MsgUtil.addMsg(2, "流程定义,ID:" + id + "已经存在,该记录终止导入！");
		}
	}

	private BpmDefinition setTypeId(BpmDefinition bpmDefinition) throws Exception {
		if (BeanUtils.isEmpty(bpmDefinition.getTypeId()))
			return bpmDefinition;
		GlobalType globalType = (GlobalType) globalTypeDao.getById(bpmDefinition.getTypeId());
		if (BeanUtils.isEmpty(globalType))
			bpmDefinition.setTypeId(null);
		return bpmDefinition;
	}

	private void importTaskReminder(List<TaskReminder> taskReminderList, String actDefId) throws Exception {
		for (TaskReminder taskReminder : taskReminderList) {
			Long id = taskReminder.getTaskDueId();
			TaskReminder reminder = (TaskReminder) taskReminderDao.getById(id);
			if (BeanUtils.isEmpty(reminder)) {
				taskReminder.setActDefId(actDefId);
				taskReminderDao.save(taskReminder);
				MsgUtil.addMsg(1, "任务节点催办时间设置,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(2, "任务节点催办时间设置,ID:" + id + "已经存在,该记录终止导入！");
			}
		}
	}

	private void importBpmNodeButton(List<BpmNodeButton> bpmNodeButtonList, Long defId, String actDefId) throws Exception {
		for (BpmNodeButton bpmNodeButton : bpmNodeButtonList) {
			Long id = bpmNodeButton.getId();
			BpmNodeButton nodeButton = (BpmNodeButton) bpmNodeButtonDao.getById(id);
			if (BeanUtils.isEmpty(nodeButton)) {
				bpmNodeButton.setDefId(defId);
				bpmNodeButton.setActdefid(actDefId);
				bpmNodeButtonDao.save(bpmNodeButton);
				MsgUtil.addMsg(1, "流程操作按钮设置,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(2, "流程操作按钮设置,ID:" + id + "已经存在,该记录终止导入！");
			}
		}
	}

	private void importBpmNodeSet(List<BpmNodeSet> bpmNodeSetList, List<BpmNodeUser> bpmNodeUserList, List<BpmNodeUserUplow> bpmNodeUserUplowList,
			List<TaskApprovalItems> taskApprovalItemsList, Long defId, String actDefId) throws Exception {
		List approvalItemsList = new ArrayList();
		if (BeanUtils.isNotEmpty(taskApprovalItemsList)) {
			for (TaskApprovalItems taskApprovalItems : taskApprovalItemsList) {
				if (BeanUtils.isNotEmpty(taskApprovalItems.getSetId()))
					approvalItemsList.add(taskApprovalItems);
				else {
					importTaskApprovalItems(taskApprovalItems, actDefId, null);
				}
			}
		}
		for (Iterator bpmNodeSetIterator = bpmNodeSetList.iterator(); bpmNodeSetIterator.hasNext();) {
			BpmNodeSet bpmNodeSet = (BpmNodeSet) bpmNodeSetIterator.next();
			Long setId = bpmNodeSet.getSetId();

			Long nodeSetId = importBpmNodeSet(bpmNodeSet, defId, actDefId, setId);

			for (BpmNodeUser bpmNodeUser : bpmNodeUserList) {
				if (setId.longValue() == bpmNodeUser.getSetId().longValue()) {
					Long userId = bpmNodeUser.getNodeUserId();

					Long nodeUserId = importBpmNodeUser(bpmNodeUser, actDefId, nodeSetId);
					if (bpmNodeUserUplowList != null) {
						for (BpmNodeUserUplow bpmNodeUserUplow : bpmNodeUserUplowList) {
							if (userId.longValue() == bpmNodeUserUplow.getNodeUserId().longValue()) {
								importBpmNodeUserUplow(bpmNodeUserUplow, nodeUserId);
							}
						}
					}
				}
			}

			for (Iterator approvalItemsIterator = approvalItemsList.iterator(); approvalItemsIterator.hasNext();) {
				TaskApprovalItems taskApprovalItems = (TaskApprovalItems) approvalItemsIterator.next();
				if (setId.longValue() == taskApprovalItems.getSetId().longValue())
					importTaskApprovalItems(taskApprovalItems, actDefId, nodeSetId);
			}
		}
	}

	private void importTaskApprovalItems(TaskApprovalItems taskApprovalItems, String actDefId, Long setId) throws Exception {
		Long id = taskApprovalItems.getItemId();
		TaskApprovalItems approvalItems = (TaskApprovalItems) taskApprovalItemsDao.getById(id);
		if (BeanUtils.isEmpty(approvalItems)) {
			taskApprovalItems.setSetId(setId);
			taskApprovalItems.setActDefId(actDefId);
			taskApprovalItemsDao.save(taskApprovalItems);
			MsgUtil.addMsg(1, "常用语设置,ID:" + id + ",该记录成功导入！");
		} else {
			MsgUtil.addMsg(2, "常用语设置,ID:" + id + "已经存在,该记录终止导入！");
		}
	}

	private Long importBpmNodeSet(BpmNodeSet bpmNodeSet, Long defId, String actDefId, Long setId) throws Exception {
		Long id = bpmNodeSet.getSetId();
		BpmNodeSet nodeSet = (BpmNodeSet) bpmNodeSetDao.getById(id);
		if (BeanUtils.isEmpty(nodeSet)) {
			bpmNodeSet.setDefId(defId);
			bpmNodeSet.setActDefId(actDefId);
			bpmNodeSetDao.save(bpmNodeSet);
			MsgUtil.addMsg(1, "流程节点设置,ID:" + id + ",该记录成功导入！");
		} else {
			MsgUtil.addMsg(2, "流程节点设置,ID:" + id + "已经存在,该记录终止导入！");
		}
		return id;
	}

	private void importBpmUserCondition(List<BpmUserCondition> bpmUserConditionList, Long defId, String actDefId) throws Exception {
		for (BpmUserCondition bpmUserCondition : bpmUserConditionList) {
			Long id = bpmUserCondition.getId();
			BpmUserCondition userCondition = (BpmUserCondition) bpmUserConditionDao.getById(id);
			if (BeanUtils.isEmpty(userCondition)) {
				bpmUserCondition.setActdefid(actDefId);
				bpmUserConditionDao.save(bpmUserCondition);
				MsgUtil.addMsg(1, "流程节点设置,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(2, "流程节点设置,ID:" + id + "已经存在,该记录终止导入！");
			}
		}
	}

	private Long importBpmNodeUser(BpmNodeUser bpmNodeUser, String actDefId, Long setId) throws Exception {
		Long id = bpmNodeUser.getNodeUserId();
		BpmNodeUser nodeUser = (BpmNodeUser) bpmNodeUserDao.getById(id);
		if (BeanUtils.isEmpty(nodeUser)) {
			bpmNodeUser.setActDefId(actDefId);
			bpmNodeUser.setSetId(setId);
			bpmNodeUserDao.save(bpmNodeUser);
			MsgUtil.addMsg(1, "节点下的人员设置,ID:" + id + ",该记录成功导入！");
		} else {
			MsgUtil.addMsg(2, "节点下的人员设置,ID:" + id + "已经存在,该记录终止导入！");
		}
		return id;
	}

	private void importBpmNodeUserUplow(BpmNodeUserUplow bpmNodeUserUplow, Long nodeUserId) throws Exception {
		Long id = bpmNodeUserUplow.getId();
		BpmNodeUserUplow nodeUserUplow = (BpmNodeUserUplow) bpmNodeUserUplowDao.getById(id);
		if (BeanUtils.isEmpty(nodeUserUplow)) {
			bpmNodeUserUplow.setNodeUserId(nodeUserId);
			bpmNodeUserUplowDao.save(bpmNodeUserUplow);
			MsgUtil.addMsg(1, "用户节点的上下级设置,ID:" + id + ",该记录成功导入！");
		} else {
			MsgUtil.addMsg(2, "用户节点的上下级设置,ID:" + id + "已经存在,该记录终止导入！");
		}
	}

	private void importBpmNodeMessage(List<BpmNodeMessage> bpmNodeMessageList, String actDefId) throws Exception {
		for (BpmNodeMessage bpmNodeMessage : bpmNodeMessageList) {
			Long id = bpmNodeMessage.getId();
			BpmNodeMessage nodeMessage = (BpmNodeMessage) bpmNodeMessageDao.getById(id);
			if (BeanUtils.isEmpty(nodeMessage)) {
				bpmNodeMessage.setActDefId(actDefId);
				bpmNodeMessageDao.save(bpmNodeMessage);
				MsgUtil.addMsg(1, "流程消息设置,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(2, "流程消息设置,ID:" + id + "已经存在,该记录终止导入！");
			}
		}
	}

	private void importBpmNodeSign(List<BpmNodeSign> bpmNodeSignList, String actDefId) throws Exception {
		for (BpmNodeSign bpmNodeSign : bpmNodeSignList) {
			Long id = bpmNodeSign.getSignId();
			BpmNodeSign nodeSign = (BpmNodeSign) bpmNodeSignDao.getById(id);
			if (BeanUtils.isEmpty(nodeSign)) {
				bpmNodeSign.setActDefId(actDefId);
				bpmNodeSignDao.save(bpmNodeSign);
				MsgUtil.addMsg(1, "流程会签规则,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(2, "流程会签规则,ID:" + id + "已经存在,该记录终止导入！");
			}
		}
	}

	private void importBpmDefVar(List<BpmDefVar> bpmDefVarList, Long defId) throws Exception {
		for (BpmDefVar bpmDefVar : bpmDefVarList) {
			Long id = bpmDefVar.getVarId();
			BpmDefVar defVar = (BpmDefVar) bpmDefVarDao.getById(id);
			if (BeanUtils.isEmpty(defVar)) {
				bpmDefVar.setDefId(defId);
				bpmDefVarDao.save(bpmDefVar);
				MsgUtil.addMsg(1, "流程变量,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(2, "流程变量,ID:" + id + "已经存在,该记录终止导入！");
			}
		}
	}

	private void importBpmNodeScript(List<BpmNodeScript> bpmNodeScriptList, String actDefId) throws Exception {
		for (BpmNodeScript bpmNodeScript : bpmNodeScriptList) {
			Long id = bpmNodeScript.getId();
			BpmNodeScript nodeScript = (BpmNodeScript) bpmNodeScriptDao.getById(id);
			if (BeanUtils.isEmpty(nodeScript)) {
				bpmNodeScript.setActDefId(actDefId);
				bpmNodeScriptDao.save(bpmNodeScript);
				MsgUtil.addMsg(1, "流程事件脚本,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(2, "流程事件脚本,ID:" + id + "已经存在,该记录终止导入！");
			}
		}
	}

	private void importBpmNodeRule(List<BpmNodeRule> bpmNodeRuleList, String actDefId) throws Exception {
		for (BpmNodeRule bpmNodeRule : bpmNodeRuleList) {
			Long id = bpmNodeRule.getRuleId();
			BpmNodeRule nodeRule = (BpmNodeRule) bpmNodeRuleDao.getById(id);
			if (BeanUtils.isEmpty(nodeRule)) {
				bpmNodeRule.setActDefId(actDefId);
				bpmNodeRuleDao.save(bpmNodeRule);
				MsgUtil.addMsg(1, "流程跳转规则,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(2, "流程跳转规则,ID:" + id + "已经存在,该记录终止导入！");
			}
		}
	}

	private void importBpmDefRights(List<BpmDefRights> bpmDefRightsList, Long defId) throws Exception {
		for (BpmDefRights bpmDefRights : bpmDefRightsList) {
			Long id = bpmDefRights.getId();
			BpmDefRights defRights = (BpmDefRights) bpmDefRightsDao.getById(id);
			if (BeanUtils.isEmpty(defRights)) {
				bpmDefRights.setDefKey(String.valueOf(defId));
				bpmDefRightsDao.save(bpmDefRights);
				MsgUtil.addMsg(1, "表单权限,ID:" + id + ",该记录成功导入！");
			} else {
				MsgUtil.addMsg(2, "表单权限,ID:" + id + "已经存在,该记录终止导入！");
			}
		}
	}

	public String exportXml(Long[] bpmDefIds, Map<String, Boolean> map) throws Exception {
		BpmDefinitionXmlList bpmDefinitionXmlList = new BpmDefinitionXmlList();
		List list = new ArrayList();
		for (int i = 0; i < bpmDefIds.length; i++) {
			BpmDefinition definition = (BpmDefinition) dao.getById(bpmDefIds[i]);
			BpmDefinitionXml bpmDefinitionXml = exportBpmDefinition(definition, BpmDefinition.MAIN, map);

			list.add(bpmDefinitionXml);
		}
		bpmDefinitionXmlList.setBpmDefinitionXmlList(list);
		return XmlBeanUtil.marshall(bpmDefinitionXmlList, BpmDefinitionXmlList.class);
	}

	private BpmDefinitionXml exportBpmDefinition(BpmDefinition definition, Short isMain, Map<String, Boolean> map) throws Exception {
		BpmDefinitionXml bpmDefinitionXml = new BpmDefinitionXml();
		if (BeanUtils.isEmpty(definition))
			return bpmDefinitionXml;
		if (BeanUtils.isEmpty(map)) {
			return bpmDefinitionXml;
		}
		Long defId = definition.getDefId();
		String actDefId = definition.getActDefId();
		Long actDeployId = definition.getActDeployId();

		bpmDefinitionXml.setBpmDefinition(definition);

		if ((((Boolean) map.get("bpmDefinitionHistory")).booleanValue()) && (isMain.shortValue() == BpmDefinition.MAIN.shortValue())) {
			exportBpmDefinitionHistory(defId, bpmDefinitionXml, map);
		}

		if (((Boolean) map.get("subBpmDefinition")).booleanValue()) {
			exportSubBpmDefinition(actDeployId, bpmDefinitionXml, map);
		}

		if (((Boolean) map.get("bpmDefRights")).booleanValue()) {
			List bpmDefRightsList = bpmDefRightsDao.getByDefKey(String.valueOf(defId));
			bpmDefinitionXml.setBpmDefRightsList(bpmDefRightsList);
		}

		if (((Boolean) map.get("bpmNodeRule")).booleanValue()) {
			List bpmNodeRuleList = bpmNodeRuleDao.getByDefIdNodeId(actDefId, "");
			bpmDefinitionXml.setBpmNodeRuleList(bpmNodeRuleList);
		}

		if (((Boolean) map.get("bpmNodeScript")).booleanValue()) {
			List bpmNodeScriptList = bpmNodeScriptDao.getByBpmNodeScriptId("", actDefId);
			bpmDefinitionXml.setBpmNodeScriptList(bpmNodeScriptList);
		}

		if (((Boolean) map.get("bpmDefVar")).booleanValue()) {
			List bpmDefVarList = bpmDefVarDao.getByDefId(defId);
			bpmDefinitionXml.setBpmDefVarList(bpmDefVarList);
		}

		if (((Boolean) map.get("bpmNodeSign")).booleanValue()) {
			List bpmNodeSignList = bpmNodeSignDao.getByActDefId(actDefId);
			bpmDefinitionXml.setBpmNodeSignList(bpmNodeSignList);
		}

		if (((Boolean) map.get("bpmNodeMessage")).booleanValue()) {
			List bpmNodeMessageList = bpmNodeMessageDao.getByActDefId(actDefId);
			bpmDefinitionXml.setBpmNodeMessageList(bpmNodeMessageList);
		}

		if (((Boolean) map.get("bpmNodeSet")).booleanValue()) {
			List<BpmNodeSet> bpmNodeSetList = bpmNodeSetDao.getAllByDefId(defId);
			if (!((Boolean) map.get("bpmFormDef")).booleanValue()) {
				for (BpmNodeSet bpmNodeSet : bpmNodeSetList) {
					bpmNodeSet.setFormKey(Long.valueOf(0L));
					bpmNodeSet.setFormDefName("");
				}
			}
			bpmDefinitionXml.setBpmNodeSetList(bpmNodeSetList);

			if (((Boolean) map.get("bpmNodeUser")).booleanValue()) {
				List<BpmNodeUser> bpmNodeUserList = bpmNodeUserDao.getByActDefId(actDefId);
				bpmDefinitionXml.setBpmNodeUserList(bpmNodeUserList);

				for (BpmNodeUser bpmNodeUser : bpmNodeUserList) {
					Long nodeUserId = bpmNodeUser.getNodeUserId();
					List bpmNodeUserUplowList = bpmNodeUserUplowDao.getByNodeUserId(nodeUserId.longValue());
					if (BeanUtils.isNotEmpty(bpmNodeUserUplowList)) {
						bpmDefinitionXml.setBpmNodeUserUplowList(bpmNodeUserUplowList);
					}
				}
				List bpmUserConditionList = bpmUserConditionDao.getByActDefId(actDefId);
				if (BeanUtils.isNotEmpty(bpmUserConditionList)) {
					bpmDefinitionXml.setBpmUserConditionList(bpmUserConditionList);
				}
			}

			if ((((Boolean) map.get("bpmFormDef")).booleanValue()) && (BeanUtils.isNotEmpty(bpmNodeSetList))) {
				Set tableIdSet = exportBpmFormDef(bpmNodeSetList, bpmDefinitionXml);

				if ((((Boolean) map.get("bpmFormTable")).booleanValue()) && (BeanUtils.isNotEmpty(tableIdSet))) {
					exportBpmFormTable(tableIdSet, bpmDefinitionXml);
				}
			}
		}

		if (((Boolean) map.get("bpmNodeButton")).booleanValue()) {
			List bpmNodeButtonList = bpmNodeButtonDao.getByDefId(defId);
			bpmDefinitionXml.setBpmNodeButtonList(bpmNodeButtonList);
		}

		if (((Boolean) map.get("taskApprovalItems")).booleanValue()) {
			List taskApprovalItemsList = taskApprovalItemsDao.getByActDefId(actDefId);
			bpmDefinitionXml.setTaskApprovalItemsList(taskApprovalItemsList);
		}

		if (((Boolean) map.get("taskReminder")).booleanValue()) {
			List taskReminderList = taskReminderDao.getByActDefId(actDefId);
			bpmDefinitionXml.setTaskReminderList(taskReminderList);
		}

		return bpmDefinitionXml;
	}

	private void exportBpmFormTable(Set<Long> tableIdSet, BpmDefinitionXml bpmDefinitionXml) {
//		Map map = bpmFormTableService.getDefaultExportMap(null);
//		List bpmFormTableXmlList = new ArrayList();
//		for (Long tableId : tableIdSet) {
//			BpmFormTable formTable = (BpmFormTable) bpmFormTableService.getById(tableId);
//			BpmFormTableXml bpmFormTableXml = bpmFormTableService.exportTable(formTable, map);
//			bpmFormTableXmlList.add(bpmFormTableXml);
//		}
//		if (BeanUtils.isNotEmpty(bpmFormTableXmlList))
//			bpmDefinitionXml.setBpmFormTableXmlList(bpmFormTableXmlList);
	}

	private Set<Long> exportBpmFormDef(List<BpmNodeSet> bpmNodeSetList, BpmDefinitionXml bpmDefinitionXml) {
//		Map map = bpmFormDefService.getDefaultExportMap(null);
//
//		Set<Long> formKeySet = new HashSet();
//		for (BpmNodeSet bpmNodeSet : bpmNodeSetList) {
//			if ((BeanUtils.isNotEmpty(bpmNodeSet.getFormKey())) && (bpmNodeSet.getFormKey().longValue() > 0L)) {
//				formKeySet.add(bpmNodeSet.getFormKey());
//			}
//		}
		Set tableIdSet = new HashSet();
//
//		Object bpmFormDefXmlList = new ArrayList();
//		for (Long formKey : formKeySet) {
//			List bpmFormDefList = bpmFormDefDao.getByFormKeyIsDefault(formKey, BpmFormDef.IS_DEFAULT);
//			if (BeanUtils.isNotEmpty(bpmFormDefList)) {
//				BpmFormDef bpmFormDef = (BpmFormDef) bpmFormDefList.get(0);
//				BpmFormDefXml bpmFormDefXml = bpmFormDefService.exportBpmFormDef(bpmFormDef, BpmFormDef.IS_DEFAULT, map);
//				((List) bpmFormDefXmlList).add(bpmFormDefXml);
//
//				if ((BeanUtils.isNotEmpty(bpmFormDef)) && (BeanUtils.isNotEmpty(bpmFormDef.getTableId()))) {
//					tableIdSet.add(bpmFormDef.getTableId());
//				}
//			}
//		}
//		if (BeanUtils.isNotEmpty(bpmFormDefXmlList))
//			bpmDefinitionXml.setBpmFormDefXmlList((List) bpmFormDefXmlList);
		return tableIdSet;
	}

	private void exportSubBpmDefinition(Long actDeployId, BpmDefinitionXml bpmDefinitionXml, Map<String, Boolean> map) throws Exception {
		if (BeanUtils.isEmpty(actDeployId))
			return;
		List subBpmDefinitionXmlList = new ArrayList();
		String xml = bpmDao.getDefXmlByDeployId(actDeployId.toString());
		Set<String> keySet = NodeCache.parseXmlBySubKey(xml);
		for (String flowKey : keySet) {
			BpmDefinition bpmDefinition = dao.getByActDefKeyIsMain(flowKey);
			subBpmDefinitionXmlList.add(exportBpmDefinition(bpmDefinition, BpmDefinition.MAIN, map));
		}

		if (BeanUtils.isNotEmpty(subBpmDefinitionXmlList))
			bpmDefinitionXml.setSubBpmDefinitionXmlList(subBpmDefinitionXmlList);
	}

	private void exportBpmDefinitionHistory(Long defId, BpmDefinitionXml bpmDefinitionXml, Map<String, Boolean> map) throws Exception {
		List<BpmDefinition> bpmDefinitionList = getAllHistoryVersions(defId);
		if (BeanUtils.isEmpty(bpmDefinitionList))
			return;
		List bpmDefinitionXmlList = new ArrayList();
		for (BpmDefinition bpmDefinition : bpmDefinitionList) {
			BpmDefinitionXml definitionXml = exportBpmDefinition(bpmDefinition, BpmDefinition.NOT_MAIN, map);
			bpmDefinitionXmlList.add(definitionXml);
		}
		bpmDefinitionXml.setBpmDefinitionXmlList(bpmDefinitionXmlList);
	}

	public BpmDefinition getMainDefByActDefKey(String actDefKey) {
		return dao.getByActDefKeyIsMain(actDefKey);
	}

	public List<BpmDefinition> getByUserId(QueryCriteria queryFilter) {
		List list = dao.getByUserId(queryFilter);
		return list;
	}

	public List<BpmDefinition> getByUserIdFilter(QueryCriteria queryFilter) {
		return dao.getByUserIdFilter(queryFilter);
	}

	public boolean isActDefKeyExists(String key) {
		return dao.isActDefKeyExists(key);
	}

	public List<BpmDefinition> getAllPublished(String subject) {
		return dao.getAllPublished(subject);
	}

	public List<BpmDefinition> getPublishedByTypeId(String typeId) {
		return dao.getPublishedByTypeId(typeId);
	}

	public BpmDefinition getMainByDefKey(String defkey) {
		return dao.getMainByDefKey(defkey);
	}

	public int updateDisableStatus(Long defId, Short disableStatus) {
		return dao.updateDisableStatus(defId, disableStatus);
	}

	public List<BpmDefinition> getByUserId(Long userId, Map<String, Object> params, QueryCriteria pb) {
		return dao.getByUserId(userId, params, pb);
	}
	
	public PageResult queryBpmDefinition(QueryCriteria queryCriteria) {
		return dao.query(queryCriteria);
	}

	public BpmDefinition getById(Long definitionId) {
		return (BpmDefinition)dao.getById(definitionId);
	}
}