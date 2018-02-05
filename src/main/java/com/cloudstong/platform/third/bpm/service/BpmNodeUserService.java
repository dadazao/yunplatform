package com.cloudstong.platform.third.bpm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.activiti.engine.RuntimeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cloudstong.platform.core.engine.GroovyScriptEngine;
import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.dao.BpmNodeSetDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeUserDao;
import com.cloudstong.platform.third.bpm.dao.BpmUserConditionDao;
import com.cloudstong.platform.third.bpm.dao.ProcessRunDao;
import com.cloudstong.platform.third.bpm.model.BpmNodeSet;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.model.BpmUserCondition;
import com.cloudstong.platform.third.bpm.model.ProcessRun;

@Service
public class BpmNodeUserService {

	protected final Log log = LogFactory.getLog(getClass());

	@Resource
	private BpmNodeUserDao dao;

	@Resource
	private ProcessRunDao processRunDao;

	@Resource
	private BpmNodeSetDao bpmNodeSetDao;

	@Resource
	private GroovyScriptEngine groovyScriptEngine;

	@Resource
	private BpmUserConditionDao bpmUserConditionDao;

	@Resource
	private RuntimeService runtimeService;

//	@Resource
//	private BpmFormDefService bpmFormDefService;

	@Resource
	private BpmNodeUserCalculationSelector bpmNodeUserCalculationSelector;

	public List<BpmNodeUser> getBySetId(Long setId) {
		return dao.getBySetId(setId);
	}

	public Map<String, List<TaskExecutor>> getExeUserIdsByInstance(String actInstId, String nodeId, String preTaskUser, Map<String, Object> vars) {
		String startUserId = null;
		String actDefId = "";
		ProcessRun processRun = processRunDao.getByActInstanceId(actInstId);
		if (processRun != null) {
			startUserId = processRun.getCreatorId().toString();
			actDefId = processRun.getActDefId();
		}
		return getExeUserIds(actDefId, actInstId, nodeId, startUserId, preTaskUser, vars);
	}

	private Boolean executeOperator(List<Map<String, Object>> operatorList, Boolean returnVal) {
		for (Integer k = Integer.valueOf(0); k.intValue() < operatorList.size(); k = Integer.valueOf(k.intValue() + 1)) {
			Map resultMap = (Map) operatorList.get(k.intValue());
			String operator = (String) resultMap.get("operator");
			if ("0".equals(operator))
				returnVal = Boolean.valueOf((returnVal.booleanValue()) && (((Boolean) resultMap.get("result")).booleanValue()));
			else if ("2".equals(operator))
				returnVal = Boolean.valueOf((returnVal.booleanValue()) && (!((Boolean) resultMap.get("result")).booleanValue()));
			else {
				returnVal = Boolean.valueOf((returnVal.booleanValue()) || (((Boolean) resultMap.get("result")).booleanValue()));
			}
		}
		return returnVal;
	}

	private Boolean conditionCheck(BpmUserCondition currentCondition, Long formId, Map<String, Object> vars) {
		Boolean isPassCondition = Boolean.valueOf(true);
		Map formVars = new HashMap();
		String conditionFormId = currentCondition.getFormIdentity();

		if ((conditionFormId != null) && (!conditionFormId.equals(Long.valueOf(0L))) && (conditionFormId.equals(formId))) {
			JSONArray jsonArry = JSONArray.parseArray(currentCondition.getCondition());
			List operatorList = new ArrayList();
			for (Integer k = Integer.valueOf(0); k.intValue() < jsonArry.size(); k = Integer.valueOf(k.intValue() + 1)) {
				if ((vars.get("executionId") != null) && (formVars.isEmpty())) {
					try {
						formVars = runtimeService.getVariables((String) vars.get("executionId"));
					} catch (Exception e) {
						throw new RuntimeException("第一个节点不存在表单变量，无法校验表单规则。");
					}
				}
				JSONObject Jsonobject = JSONObject.fromObject(jsonArry.get(k.intValue()));

				String valName = (String) Jsonobject.get("key");
				String varValue = (String) Jsonobject.get("conditionValue");
				String compare = (String) Jsonobject.get("compare");
				String operator = (String) Jsonobject.get("operator");
				String formVal = (String) formVars.get(valName);
				if (formVal == null) {
					formVal = "";
				}
				String script = "";
				if (compare.contains("()"))
					script = valName + compare.replace("()", "") + "('" + varValue + "')";
				else {
					script = valName + compare + "'" + varValue + "'";
				}

				Map scriptvars = new HashMap();
				scriptvars.put(valName, formVal);
				Boolean result = Boolean.valueOf(groovyScriptEngine.executeBoolean(script, scriptvars));
				Map resultMap = new HashMap();
				resultMap.put("operator", operator);
				resultMap.put("result", result);
				operatorList.add(resultMap);
			}

			isPassCondition = executeOperator(operatorList, isPassCondition);
		}

		return isPassCondition;
	}

	public Map<String, List<TaskExecutor>> getExeUserIds(String actDefId, String nodeId, String startUserId, Map<String, Object> vars) {
		return getExeUserIds(actDefId, null, nodeId, startUserId, "", vars);
	}

	public Map<String, List<TaskExecutor>> getExeUserIds(String actDefId, String actInstId, String nodeId, String startUserId, String preTaskUser,
			Map<String, Object> vars) {
		log.debug("startUserId:" + startUserId + ",preTaskUser:" + preTaskUser);
		Map map = new HashMap();

		BpmNodeSet bpmNodeSet = bpmNodeSetDao.getByActDefIdNodeId(actDefId, nodeId);

		if (bpmNodeSet == null) {
			return map;
		}
		Long formId = Long.valueOf(0L);

//		formId = bpmFormDefService.getCurrentTableId(bpmNodeSet);

		List bpmUserConditionList = bpmUserConditionDao.getBySetId(bpmNodeSet.getSetId());
		if (!BeanUtils.isNotEmpty(bpmUserConditionList)) {
			return map;
		}
		Boolean isExistUser = Boolean.valueOf(false);
		for (Integer i = Integer.valueOf(0); i.intValue() < bpmUserConditionList.size(); i = Integer.valueOf(i.intValue() + 1)) {
			Boolean isPass = Boolean.valueOf(true);

			if (isExistUser.booleanValue())
				break;
			BpmUserCondition currentCondition = (BpmUserCondition) bpmUserConditionList.get(i.intValue());

			isPass = conditionCheck(currentCondition, formId, vars);
			if (isPass.booleanValue()) {
				Map nodeUserMap = getMapBySetId(bpmNodeSet.getSetId(), currentCondition.getId());

				List partUser = (List) nodeUserMap.get("PARTICIPATION");

				List notifyUser = (List) nodeUserMap.get("NOTIFY");
				if (BeanUtils.isNotEmpty(partUser)) {
					List partUsers = getUsers(partUser, actDefId, actInstId, nodeId, startUserId, preTaskUser);
					map.put("PARTICIPATION", partUsers);
					if (partUsers != null) {
						isExistUser = Boolean.valueOf(true);
					}
				}
				if (BeanUtils.isNotEmpty(notifyUser)) {
					List notifyUsers = getUsers(notifyUser, actDefId, actInstId, nodeId, startUserId, preTaskUser);
					map.put("NOTIFY", notifyUsers);
					if (notifyUsers != null) {
						isExistUser = Boolean.valueOf(true);
					}
				}
			}
		}

		return map;
	}

	public List<TaskExecutor> getUsers(String userType, String actDefId, String actInstId, String nodeId, String startUserId, String preTaskUser) {
		BpmNodeSet bpmNodeSet = bpmNodeSetDao.getByActDefIdNodeId(actDefId, nodeId);
		if (bpmNodeSet == null)
			return new ArrayList();
		Map nodeUserMap = getMapBySetId(bpmNodeSet.getSetId());

		List users = (List) nodeUserMap.get(userType);
		if (BeanUtils.isEmpty(users))
			return new ArrayList();
		List userList = getUsers(users, actDefId, actInstId, nodeId, startUserId, preTaskUser);
		return userList;
	}

	private Set<TaskExecutor> getByBpmNodeUser(BpmNodeUser bpmNodeUser, boolean extractUser, String startUserIdString, String preTaskUserIdString,
			String actInstId) {
		Long startUserIdLong = null;
		Long preTaskUserIdLong = null;
		if (!StringUtil.isEmpty(startUserIdString)) {
			startUserIdLong = Long.valueOf(Long.parseLong(startUserIdString));
		}
		if (!StringUtil.isEmpty(preTaskUserIdString)) {
			preTaskUserIdLong = Long.valueOf(Long.parseLong(preTaskUserIdString));
		}

		IBpmNodeUserCalculation.CalcVars vars = new IBpmNodeUserCalculation.CalcVars(startUserIdLong, preTaskUserIdLong, actInstId, extractUser);
		IBpmNodeUserCalculation calculation = bpmNodeUserCalculationSelector.getByKey(bpmNodeUser.getAssignType());
		return calculation.getExecutor(bpmNodeUser, vars);
	}

	private List<TaskExecutor> getUsers(List<BpmNodeUser> nodeUsers, String actDefId, String actInstId, String nodeId, String startUserId,
			String preTaskUser) {
		List list = new ArrayList();

		if (nodeUsers.size() == 1) {
			BpmNodeUser bpmNodeUser = (BpmNodeUser) nodeUsers.get(0);
			Set uIdSet = getByBpmNodeUser(bpmNodeUser, false, startUserId, preTaskUser, actInstId);
			list.addAll(uIdSet);
			return list;
		}
		Set userIdSet = new HashSet();
		for (BpmNodeUser bpmNodeUser : nodeUsers) {
			Set uIdSet = getByBpmNodeUser(bpmNodeUser, true, startUserId, preTaskUser, actInstId);
			if (userIdSet.size() == 0)
				userIdSet = uIdSet;
			else {
				userIdSet = computeUserSet(bpmNodeUser.getCompType().shortValue(), userIdSet, uIdSet);
			}
		}
		list.addAll(userIdSet);

		return list;
	}

	private Map<String, List<BpmNodeUser>> getMapBySetId(Long setId) {
		Map map = new HashMap();
		List<BpmNodeUser> userList = dao.getBySetId(setId);
		for (BpmNodeUser bpmNodeUser : userList) {
			if (bpmNodeUser.getAssignUseType().shortValue() == 0)
				addMap(map, "PARTICIPATION", bpmNodeUser);
			else {
				addMap(map, "NOTIFY", bpmNodeUser);
			}
		}
		return map;
	}

	private Map<String, List<BpmNodeUser>> getMapBySetId(Long setId, Long conditionId) {
		Map map = new HashMap();
		List<BpmNodeUser> userList = dao.getBySetIdAndConditionId(setId, conditionId);
		for (BpmNodeUser bpmNodeUser : userList) {
			if (bpmNodeUser.getAssignUseType().shortValue() == 0)
				addMap(map, "PARTICIPATION", bpmNodeUser);
			else {
				addMap(map, "NOTIFY", bpmNodeUser);
			}
		}
		return map;
	}

	private void addMap(Map<String, List<BpmNodeUser>> map, String type, BpmNodeUser bpmNodeUser) {
		if (map.containsKey(type)) {
			((List) map.get(type)).add(bpmNodeUser);
		} else {
			List list = new ArrayList();
			list.add(bpmNodeUser);
			map.put(type, list);
		}
	}

	private Set<TaskExecutor> computeUserSet(short computeType, Set<TaskExecutor> userIdSet, Set<TaskExecutor> newUserSet) {
		if (newUserSet == null)
			return userIdSet;
		Iterator uIt;
		if (1 == computeType) {
			Set orLastSet = new HashSet();
			uIt = userIdSet.iterator();
			while (uIt.hasNext()) {
				TaskExecutor key = (TaskExecutor) uIt.next();
				if (newUserSet.contains(key)) {
					orLastSet.add(key);
				}
			}
			return orLastSet;
		}
		if (computeType == 0)
			userIdSet.addAll(newUserSet);
		else {
			for (TaskExecutor newUserId : newUserSet) {
				userIdSet.remove(newUserId);
			}
		}
		return userIdSet;
	}

	public List<BpmNodeUser> getBySetIdAndConditionId(Long setId, Long conditionId) {
		return dao.getBySetIdAndConditionId(setId, conditionId);
	}

	public void delByConditionId(Long conditionId) {
		dao.delByConditionId(conditionId);
	}

	public List<BpmNodeUser> selectNull() {
		return dao.selectNull();
	}

	public Set<TaskExecutor> getNodeUser(BpmNodeUser bpmNodeUser, Long startUserId, Long preTaskUserId, String actInstId, Boolean extractUser) {
		IBpmNodeUserCalculation.CalcVars vars = new IBpmNodeUserCalculation.CalcVars(startUserId, preTaskUserId, actInstId,
				extractUser.booleanValue());
		IBpmNodeUserCalculation calculation = bpmNodeUserCalculationSelector.getByKey(bpmNodeUser.getAssignType());
		return calculation.getExecutor(bpmNodeUser, vars);
	}

	public void update(BpmNodeUser bnUser) {
		dao.update(bnUser);
	}


	public void add(BpmNodeUser bnUser) {
		dao.save(bnUser);
	}

	public BpmNodeUser getById(Long id) {
		return (BpmNodeUser)dao.getById(id);
	}
}