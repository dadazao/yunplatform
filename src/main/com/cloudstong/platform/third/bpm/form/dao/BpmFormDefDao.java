package com.cloudstong.platform.third.bpm.form.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.form.model.BpmFormDef;

@Repository
public class BpmFormDefDao extends BaseMyBatisDaoImpl<BpmFormDef, Long> {

	public Integer getCountByFormKey(Long formKey) {
		return (Integer) getOne("getCountByFormKey", formKey);
	}

	public BpmFormDef getDefaultVersionByFormKey(Long formKey) {
		return (BpmFormDef) getOne("getDefaultVersionByFormKey", formKey);
	}

	public BpmFormDef getDefaultPublishedByFormKey(Long formKey) {
		return (BpmFormDef) getOne("getDefaultPublishedByFormKey", formKey);
	}

	public boolean isTableHasFormDef(Long tableId) {
		Integer result = (Integer) getOne("isTableHasFormDef", tableId);
		return result.intValue() > 0;
	}

	public int getFormDefAmount(Long tableId) {
		Integer result = (Integer) getOne("getFormDefAmount", tableId);
		return result.intValue();
	}

	public List<BpmFormDef> getByFormKey(Long formKey) {
		return getBySqlKey("getByFormKey", formKey);
	}

	public List<BpmFormDef> getPublished(QueryCriteria queryFilter) {
		return getBySqlKey("getPublished", queryFilter);
	}

	public int getFlowUsed(Long formKey) {
		Integer rtn = (Integer) getOne("getFlowUsed", formKey);
		return rtn.intValue();
	}

	public void delByFormKey(Long formKey) {
		delBySqlKey("delByFormKey", formKey);
	}

	public void setDefaultVersion(Long formKey, Long formDefId) {
		update("updNotDefaultByFormKey", formKey);
		update("updDefaultByFormId", formDefId);
	}

	public Integer getMaxVersionByFormKey(Long formKey) {
		Integer rtn = (Integer) getOne("getMaxVersionByFormKey", formKey);
		return rtn;
	}

	public List<BpmFormDef> getByFormKeyIsDefault(Long formKey, Short isDefault) {
		Map map = new HashMap();
		map.put("formKey", formKey);
		map.put("isDefault", isDefault);
		return getBySqlKey("getByFormKeyIsDefault", map);
	}
}