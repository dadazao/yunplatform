/*
 * Created on Oct 10, 2007
 *
 */
package com.cloudstong.platform.test.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.cloudstong.platform.test.dao.BaseDaoTestCase;

/**
 * All service test case must extends this case.
 * 
 * @author Cyclone
 * 
 */
public class BaseServiceTestCase extends BaseDaoTestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations
	 * ()
	 */
	protected String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		return new String[] { 
				"classpath:applicationContext-dao.xml",
				"classpath:applicationContext-resources.xml",
				"classpath:applicationContext-resources.xml",
				"applicationContext-activiti.xml" };
	}
}
