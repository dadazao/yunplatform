package com.cloudstong.platform.core.keygenerator.impl;

import com.cloudstong.platform.core.keygenerator.IKeyGenerator;
import com.cloudstong.platform.core.util.UniqueIdUtil;

public class GuidGenerator implements IKeyGenerator {
	public Object nextId() throws Exception {
		return UniqueIdUtil.getGuid();
	}

	public void setAlias(String alias) {
	}
}