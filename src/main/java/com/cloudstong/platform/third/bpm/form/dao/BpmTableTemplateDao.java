package com.cloudstong.platform.third.bpm.form.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.form.model.BpmTableTemplate;

@Repository
public class BpmTableTemplateDao extends BaseMyBatisDaoImpl<BpmTableTemplate, Long> {

	public List<BpmTableTemplate> getList(QueryCriteria filter) {
		return getBySqlKey("getList", filter);
	}

	public List<BpmTableTemplate> getByUserIdFilter(QueryCriteria queryFilter) {
		return getBySqlKey("getByUserIdFilter", queryFilter);
	}

	public List<BpmTableTemplate> getByFormKey(Long formKey) {
		return getBySqlKey("getByFormKey", formKey);
	}

	public void delByFormKey(Long formKey) {
		delBySqlKey("delByFormKey", formKey);
	}
}