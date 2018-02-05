package com.cloudstong.platform.core.keygenerator.impl;

import org.activiti.engine.IdentityService;

import com.cloudstong.platform.core.keygenerator.IKeyGenerator;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;

public class IdentityGenerator implements IKeyGenerator {
	private String alias = "";

	public Object nextId() throws Exception {
//		IdentityService identityService = (IdentityService) AppUtil.getBean(IdentityService.class);
		return UniqueIdUtil.genId();
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}