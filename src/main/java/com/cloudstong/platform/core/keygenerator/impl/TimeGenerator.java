package com.cloudstong.platform.core.keygenerator.impl;

import com.cloudstong.platform.core.keygenerator.IKeyGenerator;
import com.cloudstong.platform.core.util.UniqueIdUtil;

public class TimeGenerator implements IKeyGenerator {
	public Object nextId() throws Exception {
		return Long.valueOf(UniqueIdUtil.genId());
	}

	public void setAlias(String alias) {
	}
}