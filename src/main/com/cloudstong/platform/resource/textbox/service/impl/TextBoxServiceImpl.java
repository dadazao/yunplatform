package com.cloudstong.platform.resource.textbox.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.textbox.dao.TextBoxDao;
import com.cloudstong.platform.resource.textbox.model.TextBox;
import com.cloudstong.platform.resource.textbox.service.TextBoxService;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本框服务接口实现类
 */
@Repository("textBoxService")
public class TextBoxServiceImpl implements TextBoxService {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private TextBoxDao textBoxDao = null;

	public TextBoxDao getTextBoxDao() {
		return textBoxDao;
	}

	public void setTextBoxDao(TextBoxDao textBoxDao) {
		this.textBoxDao = textBoxDao;
	}

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	@Override
	public void doSaveOrUpdate(TextBox textBox) throws Exception {
		if (textBox.getId() != null || !"".equals(textBox.getId())) {
			textBox.setUpdateBy(textBox.getUpdateBy());
			textBox.setUpdateDate(new java.sql.Timestamp(System
					.currentTimeMillis()));
			textBoxDao.update(textBox);
		} else {
			textBox.setStatus(0);
			textBox.setCreateBy(textBox.getCreateBy());
			textBox.setCreateDate(new java.sql.Timestamp(System
					.currentTimeMillis()));
			textBoxDao.insert(textBox);
		}
	}

	@Override
	public TextBox findByID(Long id) throws Exception {
		return textBoxDao.findByID(id);
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
		// where.append(" ORDER BY comm_createDate");
		try {
			queryForPageList = textBoxDao.queryForPageList(where.toString(),
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
			rowCount = textBoxDao.getTotalCount(where.toString(),
					paramList.toArray());
		} catch (Exception e) {
			throw e;
		}
		return rowCount;
	}

	@Override
	public void doDelete(Long id) throws Exception {
		textBoxDao.delete(id);
	}

}
