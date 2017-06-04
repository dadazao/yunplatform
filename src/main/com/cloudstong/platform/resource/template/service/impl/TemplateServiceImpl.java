package com.cloudstong.platform.resource.template.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.template.dao.TemplateDao;
import com.cloudstong.platform.resource.template.model.SysTemplate;
import com.cloudstong.platform.resource.template.model.Template;
import com.cloudstong.platform.resource.template.service.TemplateService;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:模板管理接口实现
 */
@Repository("templateService")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TemplateServiceImpl implements TemplateService {
	@Resource
	private TemplateDao templateDao;

	public SysTemplate queryTemplateById(Long id){
		return templateDao.queryTemplateById(id);
	}
	
	public Long doSaveOrUpdateTemplate(SysTemplate sysTemplate){
		return templateDao.saveOrUpdateTemplate(sysTemplate);
	}
	
	public List queryTemplate(QueryCriteria queryCriteria) {
		Map<String, Object> map = queryCriteria.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select * from sys_template where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			if (entry.getValue() instanceof String) {
				sql.append(" and " + entry.getKey() + " like ? ");
				param.add("%" + ((String)entry.getValue()).trim() + "%");
			} else {
				sql.append(" and " + entry.getKey() + " =? ");
				param.add(entry.getValue());
			}
		}
		sql.append(" order by comm_updateDate desc ");
		return templateDao.select(sql.toString(), param.toArray(),
				queryCriteria.getCurrentIndex(), queryCriteria.getPageSize());
	}

	@Override
	public Template findTemplateById(Long id) {
		return templateDao.selectById(id);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doSaveLibrary(Template template) {
		templateDao.insertLibrary(template);
	}
	
	public List<Template> selectLibary(){
		return templateDao.selectLibary();
	}
}
