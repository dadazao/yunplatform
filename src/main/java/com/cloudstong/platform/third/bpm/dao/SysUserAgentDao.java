package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.SysUserAgent;

@Repository
public class SysUserAgentDao extends BaseMyBatisDaoImpl<SysUserAgent, Long> {
	public Class getEntityClass() {
		return SysUserAgent.class;
	}

	public List<SysUserAgent> getByTouserId(Long userId) {
		Map params = new HashMap();
		params.put("touserid", userId);
		return getBySqlKey("getByTouserId", params);
	}
}