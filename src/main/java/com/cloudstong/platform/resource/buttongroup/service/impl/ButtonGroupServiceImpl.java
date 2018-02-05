package com.cloudstong.platform.resource.buttongroup.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.buttongroup.dao.ButtonGroupDao;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;
import com.cloudstong.platform.resource.buttongroup.service.ButtonGroupService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 按钮组服务接口实现类
 */
@Repository("buttonGroupService")
public class ButtonGroupServiceImpl implements ButtonGroupService {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private ButtonGroupDao buttonGroupDao = null;

	public ButtonGroupDao getButtonGroupDao() {
		return buttonGroupDao;
	}

	public void setButtonGroupDao(ButtonGroupDao buttonGroupDao) {
		this.buttonGroupDao = buttonGroupDao;
	}

	@Override
	public Long doSaveAndUpdate(ButtonGroup buttonGroup) throws Exception {
		if (buttonGroup != null) {
			if (buttonGroup.getId() == null || "".equals(buttonGroup.getId())) {
				buttonGroup.setStatus(new Long(0));
				buttonGroup.setCreateBy("1");
				buttonGroup.setCreateDate(new java.sql.Timestamp(System
						.currentTimeMillis()));
				return buttonGroupDao.insert(buttonGroup);
			} else {
				buttonGroup.setUpdateBy("1");
				buttonGroup.setUpdateDate(new java.sql.Timestamp(System
						.currentTimeMillis()));
				buttonGroupDao.update(buttonGroup);
				return buttonGroup.getId();
			}
		}
		return null;
	}

	@Override
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception {
		StringBuffer queryCondition = new StringBuffer();
		List queryForPageList = null;
		List queryConditionList = new ArrayList();
		Map<String, Object> paramMap = queryCriteria.getQueryCondition();
		for (Iterator it = paramMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			queryCondition.append(" AND  " + entry.getKey() + " = ?");
			queryConditionList.add(entry.getValue());
		}
		queryCondition.append(" ORDER BY comm_createDate");
		queryForPageList = buttonGroupDao.queryForPageList(
				queryCondition.toString(), queryConditionList.toArray(),
				queryCriteria.getCurrentIndex(), queryCriteria.getPageSize());

		return queryForPageList;
	}

	@Override
	public int getTotalCount(QueryCriteria queryCriteria) throws Exception {
		StringBuffer queryCondition = new StringBuffer();
		int rowCount = 0;
		List queryConditionList = new ArrayList();
		Map<String, Object> paramMap = queryCriteria.getQueryCondition();
		for (Iterator it = paramMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			queryCondition.append(" AND  " + entry.getKey() + " = ?");
			queryConditionList.add(entry.getValue());
		}
		try {
			rowCount = buttonGroupDao.getTotalCount(queryCondition.toString(),
					queryConditionList.toArray());
		} catch (Exception e) {
			throw e;
		}
		return rowCount;
	}

	@Override
	public ButtonGroup findByID(Long buttonGroupID) throws Exception {
		return buttonGroupDao.findByID(buttonGroupID);
	}

	@Override
	public String doDelete(Long id) throws Exception {
		int result = buttonGroupDao.updateStatus(id);
		return (result > 0 ? id.toString() : "");
	}

	@Override
	public List queryButtonGroup(QueryCriteria qc) throws Exception {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select * from sys_buttongroup where comm_status = 0 and tbl_buttongroupenabled =0 and tbl_passed = 1 and 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			if (entry.getValue() instanceof String) {
				sql.append(" and " + entry.getKey() + " = ? ");
			} else {
				sql.append(" and " + entry.getKey() + " =? ");
			}
			param.add(entry.getValue());
		}
		sql.append(" order by id desc ");
		return buttonGroupDao.select(sql.toString(), param.toArray(),
				qc.getCurrentIndex(), qc.getPageSize());
	}

}
