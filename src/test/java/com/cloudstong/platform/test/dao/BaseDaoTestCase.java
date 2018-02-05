package com.cloudstong.platform.test.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({"classpath:applicationContext-resources.xml","classpath:applicationContext-dao.xml"})
public abstract class BaseDaoTestCase {
	protected final Log log = LogFactory.getLog(getClass());
	
}
