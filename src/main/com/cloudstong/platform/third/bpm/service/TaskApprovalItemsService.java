package com.cloudstong.platform.third.bpm.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.TaskApprovalItemsDao;
import com.cloudstong.platform.third.bpm.model.TaskApprovalItems;

@Service
public class TaskApprovalItemsService {

	@Resource
	private TaskApprovalItemsDao dao;

	public TaskApprovalItems getFlowApproval(String actDefId, int isGlobal) {
		return dao.getFlowApproval(actDefId, isGlobal);
	}

	public TaskApprovalItems getTaskApproval(String actDefId, String nodeId, int isGlobal) {
		return dao.getTaskApproval(actDefId, nodeId, isGlobal);
	}

	public void delFlowApproval(String actDefId, int isGlobal) {
		dao.delFlowApproval(actDefId, isGlobal);
	}

	public void delTaskApproval(String actDefId, String nodeId, int isGlobal) {
		dao.delTaskApproval(actDefId, nodeId, isGlobal);
	}

	public void addTaskApproval(String exp, String isGlobal, String actDefId, Long setId, String nodeId) throws Exception {
		TaskApprovalItems taItem = null;
		taItem = new TaskApprovalItems();
		taItem.setItemId(Long.valueOf(UniqueIdUtil.genId()));
		taItem.setActDefId(actDefId);
		if (!isGlobal.equals("1")) {
			taItem.setSetId(setId);
			taItem.setNodeId(nodeId);
			taItem.setIsGlobal(TaskApprovalItems.notGlobal);
		} else {
			taItem.setIsGlobal(TaskApprovalItems.global);
		}
		taItem.setExpItems(exp);
		dao.save(taItem);
	}

	public List<String> getApprovalByActDefId(String actDefId, String nodeId) {
		List taskAppItemsList = new ArrayList();
		List taskAppItems = dao.getApprovalByActDefId(actDefId, nodeId);
		if (BeanUtils.isNotEmpty(taskAppItems)) {
			int j = 0;
			int i = 0;
			for (Iterator localIterator = taskAppItems.iterator(); localIterator.hasNext() && i < j;) {
				TaskApprovalItems taskAppItem = (TaskApprovalItems) localIterator.next();
				String[] itemArr = taskAppItem.getExpItems().split("\r\n");
				String[] arrayOfString1;
				j = (arrayOfString1 = itemArr).length;
				String item = arrayOfString1[i];
				taskAppItemsList.add(item);

				i++;
			}

		}

		return taskAppItemsList;
	}

	public List<TaskApprovalItems> getByActDefId(String actDefId) {
		return dao.getByActDefId(actDefId);
	}
}