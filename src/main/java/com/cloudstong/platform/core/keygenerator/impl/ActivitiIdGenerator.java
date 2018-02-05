package com.cloudstong.platform.core.keygenerator.impl;

import org.activiti.engine.impl.cfg.IdGenerator;

import com.cloudstong.platform.core.util.UniqueIdUtil;

public class ActivitiIdGenerator implements IdGenerator {
	public String getNextId() {
		return String.valueOf(UniqueIdUtil.genId());
	}
}