package com.cloudstong.platform.third.bpm.form.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.form.model.BpmFormTemplate;

@Repository
public class BpmFormTemplateDao extends BaseMyBatisDaoImpl<BpmFormTemplate, Long> {
	public List<BpmFormTemplate> getAll(Map params) {
		return getBySqlKey("getAll", params);
	}

	public void add(BpmFormTemplate bpmFormTemplate) {
		getBySqlKey("add", bpmFormTemplate);
	}

	public void delSystem() {
		delBySqlKey("delSystem", null);
	}

	public BpmFormTemplate getByTemplateAlias(String alias) {
		return (BpmFormTemplate) getUnique("getByTemplateAlias", alias);
	}

	public Integer getHasData() {
		return (Integer) getOne("getHasData", null);
	}
}