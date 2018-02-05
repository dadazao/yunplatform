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
import com.cloudstong.platform.resource.buttongroup.dao.ButtonAndGroupDao;
import com.cloudstong.platform.resource.buttongroup.model.ButtonAndGroup;
import com.cloudstong.platform.resource.buttongroup.service.ButtonAndGroupService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:按钮和按钮组的中间表对象服务接口实现类
 */
@Repository("buttonAndGroupService")
public class ButtonAndGroupServiceImpl implements ButtonAndGroupService {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private ButtonAndGroupDao buttonAndGroupDao = null;

	public ButtonAndGroupDao getButtonAndGroupDao() {
		return buttonAndGroupDao;
	}

	public void setButtonAndGroupDao(ButtonAndGroupDao buttonAndGroupDao) {
		this.buttonAndGroupDao = buttonAndGroupDao;
	}

	@Override
	public Long doSaveAndUpdate(ButtonAndGroup buttonAndGroup) throws Exception {
		if (buttonAndGroup != null) {
			if (buttonAndGroup.getId() == null
					|| "".equals(buttonAndGroup.getId())) {
				buttonAndGroup.setStatus(new Long(0));
				return buttonAndGroupDao.insert(buttonAndGroup);
			} else {
				buttonAndGroupDao.update(buttonAndGroup);
				return buttonAndGroup.getId();
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
		queryCondition.append(" ORDER BY tbl_buttonDisplayOrder");
		queryForPageList = buttonAndGroupDao.queryForPageList(
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
			rowCount = buttonAndGroupDao.getTotalCount(
					queryCondition.toString(), queryConditionList.toArray());
		} catch (Exception e) {
			throw e;
		}
		return rowCount;
	}

	@Override
	public ButtonAndGroup findByID(String buttonAndGroupID) throws Exception {
		return buttonAndGroupDao.findByID(buttonAndGroupID);
	}

	@Override
	public String doDelete(Long id) throws Exception {
		int result = buttonAndGroupDao.delete(id);
		return (result > 0 ? id.toString() : "");
	}

	@Override
	public int countButton(Long buttonGroupId) {
		return buttonAndGroupDao.countButton(buttonGroupId);
	}

}
