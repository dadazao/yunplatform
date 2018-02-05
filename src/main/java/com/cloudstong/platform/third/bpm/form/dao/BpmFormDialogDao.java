package com.cloudstong.platform.third.bpm.form.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.form.model.BpmFormDialog;

@Repository
public class BpmFormDialogDao extends BaseMyBatisDaoImpl<BpmFormDialog, Long> {

	public BpmFormDialog getByAlias(String alias) {
		return (BpmFormDialog) getUnique("getByAlias", alias);
	}

	public Integer isExistAlias(String alias) {
		return (Integer) getOne("isExistAlias", alias);
	}

	public Integer isExistAliasForUpd(Long id, String alias) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("alias", alias);
		return (Integer) getOne("isExistAliasForUpd", map);
	}
}