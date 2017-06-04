package com.cloudstong.platform.third.bpm.form.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.form.model.BpmTableTemprights;

@Repository
public class BpmTableTemprightsDao extends BaseMyBatisDaoImpl<BpmTableTemprights, Long> {
	public void delByTemplateId(Long templateId) {
		delBySqlKey("delByTemplateId", templateId);
	}

	public void delByCategoryId(Long categoryId) {
		delBySqlKey("delByCategoryId", categoryId);
	}

	public List<BpmTableTemprights> getByTemplateId(Long templateId) {
		return getBySqlKey("getByTemplateId", templateId);
	}

	public List<BpmTableTemprights> getByCategoryId(Long categoryId) {
		return getBySqlKey("getByCategoryId", categoryId);
	}
}