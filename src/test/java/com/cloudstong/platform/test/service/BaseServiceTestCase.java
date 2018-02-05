/*
 * Created on Oct 10, 2007
 *
 */
package com.cloudstong.platform.test.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * All service test case must extends this case.
 * 
 * @author Cyclone
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-context.xml")
public class BaseServiceTestCase {

}
