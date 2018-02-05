package com.cloudstong.platform.third.bpm.form.dao;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.form.model.BpmFormRule;

@Repository
public class BpmFormRuleDao extends BaseMyBatisDaoImpl<BpmFormRule, Long> {
	public Class getEntityClass() {
		return BpmFormRule.class;
	}
}