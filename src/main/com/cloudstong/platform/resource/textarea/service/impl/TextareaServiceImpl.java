package com.cloudstong.platform.resource.textarea.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.textarea.dao.TextareaDao;
import com.cloudstong.platform.resource.textarea.model.Textarea;
import com.cloudstong.platform.resource.textarea.service.TextareaService;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本域服务接口实现类
 */
@Repository("textareaService")
public class TextareaServiceImpl implements TextareaService {
	
	@Resource
	private TextareaDao textareaDao;

	public TextareaDao getTextareaDao() {
		return textareaDao;
	}

	public void setTextareaDao(TextareaDao textareaDao) {
		this.textareaDao = textareaDao;
	}

	@Override
	public Textarea findByID(Long id) throws Exception {
		return textareaDao.findByID(id);
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
			queryForPageList = textareaDao.queryForPageList(where.toString(),
					paramList.toArray(), queryCriteria.getCurrentIndex(),
					queryCriteria.getPageSize());
		} catch (Exception e) {
			throw e;
		}
		return queryForPageList;
	}
	
	
}
