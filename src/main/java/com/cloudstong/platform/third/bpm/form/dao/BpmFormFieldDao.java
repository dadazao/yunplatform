package com.cloudstong.platform.third.bpm.form.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.form.model.BpmFormField;

@Repository
public class BpmFormFieldDao extends BaseMyBatisDaoImpl<BpmFormField, Long> {
	public List<BpmFormField> getByTableId(Long tableId) {
		return getBySqlKey("getByTableId", tableId);
	}

	public List<BpmFormField> getByTableIdContainHidden(Long tableId) {
		return getBySqlKey("getByTableIdContainHidden", tableId);
	}

	public List<BpmFormField> getAllByTableId(Long tableId) {
		return getBySqlKey("getAllByTableId", tableId);
	}

	public int markDeletedByTableId(Long tableId) {
		return update("markDeletedByTableId", tableId);
	}

	public List<BpmFormField> getFlowVarByFlowDefId(Long defId) {
		return getBySqlKey("getFlowVarByFlowDefId", defId);
	}

	public void delByTableId(Long tableId) {
		delBySqlKey("delByTableId", tableId);
	}

	public BpmFormField getFieldByTidFna(Long tableId, String fieldName) {
		Map map = new HashMap();
		map.put("tableId", tableId);
		map.put("fieldName", fieldName);
		return (BpmFormField) getUnique("getFieldByTidFna", map);
	}
}