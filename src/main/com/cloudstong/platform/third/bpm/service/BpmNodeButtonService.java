package com.cloudstong.platform.third.bpm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.dao.BpmDefinitionDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeButtonDao;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.model.BpmNodeButton;

@Service
public class BpmNodeButtonService {

	@Resource
	private BpmNodeButtonDao dao;

	@Resource
	private BpmDefinitionDao bpmDefinitionDao;

	@Resource
	private BpmService bpmService;

	public List<BpmNodeButton> getByDefNodeId(Long defId, String nodeId) {
		return dao.getByDefNodeId(defId, nodeId);
	}

	public List<BpmNodeButton> getByStartForm(Long defId) {
		List list = dao.getByStartForm(defId);
		return list;
	}

	public Map<String, List<BpmNodeButton>> getMapByStartForm(Long defId) {
		Map map = new HashMap();
		List<BpmNodeButton> list = dao.getByStartForm(defId);
		if (BeanUtils.isEmpty(list)) {
			return map;
		}
		for (BpmNodeButton bpmNodeButton : list) {
			if ((bpmNodeButton.getOperatortype().intValue() == 4) || (bpmNodeButton.getOperatortype().intValue() == 5))
				addItem(map, bpmNodeButton, "inform");
			else {
				addItem(map, bpmNodeButton, "button");
			}
		}
		return map;
	}

	public Map<String, List<BpmNodeButton>> getMapByDefNodeId(Long defId, String nodeId) {
		Map map = new HashMap();
		List<BpmNodeButton> list = dao.getByDefNodeId(defId, nodeId);
		if (BeanUtils.isEmpty(list)) {
			return map;
		}
		for (BpmNodeButton bpmNodeButton : list) {
			if ((bpmNodeButton.getOperatortype().intValue() == 12) || (bpmNodeButton.getOperatortype().intValue() == 13))
				addItem(map, bpmNodeButton, "inform");
			else {
				addItem(map, bpmNodeButton, "button");
			}
		}
		return map;
	}

	public Map<String, List<BpmNodeButton>> getMapByDefId(Long defId) {
		List<BpmNodeButton> list = dao.getByDefId(defId);
		Map map = new HashMap();
		for (BpmNodeButton bpmNodeButton : list) {
			if (bpmNodeButton.getIsstartform().intValue() == 1)
				addItem(map, bpmNodeButton, "start");
			else {
				addItem(map, bpmNodeButton, bpmNodeButton.getNodeid());
			}
		}
		return map;
	}

	private void addItem(Map<String, List<BpmNodeButton>> map, BpmNodeButton bpmNodeButton, String key) {
		if (map.containsKey(key)) {
			((List) map.get(key)).add(bpmNodeButton);
		} else {
			List list = new ArrayList();
			list.add(bpmNodeButton);
			map.put(key, list);
		}
	}

	public boolean isOperatorExist(BpmNodeButton bpmNodeButton) {
		Long defId = bpmNodeButton.getDefId();
		Integer operatortype = bpmNodeButton.getOperatortype();
		if (bpmNodeButton.getIsstartform().intValue() == 1) {
			return dao.isStartFormExist(defId, operatortype).intValue() > 0;
		}
		String nodeId = bpmNodeButton.getNodeid();
		return dao.isExistByNodeId(defId, nodeId, operatortype).intValue() > 0;
	}

	public boolean isOperatorExistForUpd(BpmNodeButton bpmNodeButton) {
		Long defId = bpmNodeButton.getDefId();
		Long id = bpmNodeButton.getId();
		Integer operatortype = bpmNodeButton.getOperatortype();
		if (bpmNodeButton.getIsstartform().intValue() == 1) {
			return dao.isStartFormExistForUpd(defId, operatortype, id).intValue() > 0;
		}
		String nodeId = bpmNodeButton.getNodeid();
		return dao.isExistByNodeIdForUpd(defId, nodeId, operatortype, id).intValue() > 0;
	}

	public void sort(String ids) {
		String[] aryId = ids.split(",");
		for (int i = 0; i < aryId.length; i++) {
			Long id = Long.valueOf(Long.parseLong(aryId[i]));
			dao.updSn(id, Long.valueOf(i + 1));
		}
	}

	private void initStartForm(String actDefId, Long defId) throws Exception {
		dao.save(new BpmNodeButton(actDefId, defId, "启动流程", Integer.valueOf(1)));
		dao.save(new BpmNodeButton(actDefId, defId, "保存表单", Integer.valueOf(6)));
		dao.save(new BpmNodeButton(actDefId, defId, "流程示意图", Integer.valueOf(2)));
		dao.save(new BpmNodeButton(actDefId, defId, "打印", Integer.valueOf(3)));
		dao.save(new BpmNodeButton(actDefId, defId, "短信", Integer.valueOf(4)));
		dao.save(new BpmNodeButton(actDefId, defId, "邮件", Integer.valueOf(5)));
	}

	private void initNodeId(String actDefId, Long defId, String nodeId, boolean isSignTask) throws Exception {
		int nodetype = isSignTask ? 1 : 0;
		dao.save(new BpmNodeButton(actDefId, defId, nodeId, "同意", Integer.valueOf(1), Integer.valueOf(nodetype)));
		dao.save(new BpmNodeButton(actDefId, defId, nodeId, "反对", Integer.valueOf(2), Integer.valueOf(nodetype)));
		if (isSignTask) {
			dao.save(new BpmNodeButton(actDefId, defId, nodeId, "弃权", Integer.valueOf(3), Integer.valueOf(nodetype)));
			dao.save(new BpmNodeButton(actDefId, defId, nodeId, "补签", Integer.valueOf(7), Integer.valueOf(nodetype)));
		}

		dao.save(new BpmNodeButton(actDefId, defId, nodeId, "驳回", Integer.valueOf(4), Integer.valueOf(nodetype)));
		dao.save(new BpmNodeButton(actDefId, defId, nodeId, "驳回到发起人", Integer.valueOf(5), Integer.valueOf(nodetype)));
		dao.save(new BpmNodeButton(actDefId, defId, nodeId, "转交代办", Integer.valueOf(6), Integer.valueOf(nodetype)));
		dao.save(new BpmNodeButton(actDefId, defId, nodeId, "保存表单", Integer.valueOf(8), Integer.valueOf(nodetype)));
		dao.save(new BpmNodeButton(actDefId, defId, nodeId, "流程示意图", Integer.valueOf(9), Integer.valueOf(nodetype)));
		dao.save(new BpmNodeButton(actDefId, defId, nodeId, "打印", Integer.valueOf(10), Integer.valueOf(nodetype)));
		dao.save(new BpmNodeButton(actDefId, defId, nodeId, "审批历史", Integer.valueOf(11), Integer.valueOf(nodetype)));
		dao.save(new BpmNodeButton(actDefId, defId, nodeId, "短信", Integer.valueOf(12), Integer.valueOf(nodetype)));
		dao.save(new BpmNodeButton(actDefId, defId, nodeId, "邮件", Integer.valueOf(13), Integer.valueOf(nodetype)));
	}

	public void init(Long defId, String nodeId) throws Exception {
		BpmDefinition bpmDefinition = (BpmDefinition) bpmDefinitionDao.getById(defId);
		String actDefId = bpmDefinition.getActDefId();
		int isStartForm = StringUtil.isEmpty(nodeId) ? 1 : 0;
		if (isStartForm == 1) {
			dao.delByStartForm(defId);
			initStartForm(actDefId, defId);
		} else {
			dao.delByNodeId(defId, nodeId);
			boolean isSignTask = bpmService.isSignTask(actDefId, nodeId);
			initNodeId(actDefId, defId, nodeId, isSignTask);
		}
	}

	public void initAll(Long defId) throws Exception {
		dao.delByDefId(defId);
		BpmDefinition bpmDefinition = (BpmDefinition) bpmDefinitionDao.getById(defId);
		String actDefId = bpmDefinition.getActDefId();

		initStartForm(actDefId, defId);

		Map taskMap = bpmService.getTaskType(actDefId);
		Set set = taskMap.entrySet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			boolean isSignTask = ((Integer) entry.getValue()).intValue() == 1;

			initNodeId(actDefId, defId, (String) entry.getKey(), isSignTask);
		}
	}

	public void delByDefId(Long defId) throws Exception {
		dao.delByDefId(defId);
	}

	public void delByDefNodeId(Long defId, String nodeId) {
		if (StringUtil.isEmpty(nodeId))
			dao.delByStartForm(defId);
		else
			dao.delByNodeId(defId, nodeId);
	}

	public List<BpmNodeButton> getByDefId(Long defId) {
		return dao.getByDefId(defId);
	}

	public void delByIds(Long[] lAryId) {
		for(Long id:lAryId) {
			dao.delById(id);
		}
	}

	public BpmNodeButton getById(Long id) {
		return (BpmNodeButton)dao.getById(id);
	}
}