package com.cloudstong.platform.resource.button.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.button.dao.ButtonDao;
import com.cloudstong.platform.resource.button.model.Button;
import com.cloudstong.platform.resource.button.service.ButtonService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:按钮服务接口实现类
 */
@Repository("buttonService")
public class ButtonServiceImpl implements ButtonService {

	@Resource
	private ButtonDao buttonDao = null;

	public ButtonDao getButtonDao() {
		return buttonDao;
	}

	public void setButtonDao(ButtonDao buttonDao) {
		this.buttonDao = buttonDao;
	}

	@Override
	public void doSaveOrUpdate(Button button) throws Exception {
		String result = "-1";
		if (!button.getId().equals("")) {
			button.setUpdateBy(button.getUpdateBy());
			button.setUpdateDate(new java.sql.Timestamp(System
					.currentTimeMillis()));
			result = String.valueOf(buttonDao.update(button));
		} else {
			button.setStatus(new Long(0));
			button.setCreateBy(button.getCreateBy());
			button.setCreateDate(new java.sql.Timestamp(System
					.currentTimeMillis()));
			 buttonDao.insert(button);
		}
	}

	@Override
	public Button findByID(Long ID) throws Exception {
		return buttonDao.findByID(ID);
	}

	@Override
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception {
		StringBuffer where = new StringBuffer();
		List queryForPageList = null;
		List paramList = new ArrayList();
		Map<String, Object> paramMap = queryCriteria.getQueryCondition();
		for (Iterator it = paramMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			where.append(" AND  " + entry.getKey() + " = ?");
			paramList.add(entry.getValue());
		}
		where.append(" ORDER BY comm_createDate");
		try {
			queryForPageList = buttonDao.queryForPageList(where.toString(),
					paramList.toArray(), queryCriteria.getCurrentIndex(),
					queryCriteria.getPageSize());
		} catch (Exception e) {
			throw e;
		}
		return queryForPageList;
	}

	@Override
	public int getTotalCount(QueryCriteria queryCriteria) throws Exception {
		StringBuffer where = new StringBuffer();
		int rowCount = 0;
		List paramList = new ArrayList();
		Map<String, Object> paramMap = queryCriteria.getQueryCondition();
		for (Iterator it = paramMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			where.append(" AND  " + entry.getKey() + " = ?");
			paramList.add(entry.getValue());
		}
		try {
			rowCount = buttonDao.getTotalCount(where.toString(),
					paramList.toArray());
		} catch (Exception e) {
			throw e;
		}
		return rowCount;
	}

	@Override
	public void doDelete(Long id) throws Exception {
		try {
			int result = buttonDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List queryButton(QueryCriteria qc) throws Exception {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select * from sys_button where comm_status = 0 and tbl_buttonStatus = 0 and tbl_passed = 1 and 1=1 ");
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
		sql.append(" order by comm_createDate asc ");
		return buttonDao.select(sql.toString(), param.toArray(),
				qc.getCurrentIndex(), qc.getPageSize());
	}
}
