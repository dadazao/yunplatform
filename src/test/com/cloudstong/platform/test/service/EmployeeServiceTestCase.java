package com.cloudstong.platform.test.service;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.cloudstong.business.employee.model.Detail;
import com.cloudstong.business.employee.model.Employee;
import com.cloudstong.business.employee.service.DetailService;
import com.cloudstong.business.employee.service.EmployeeService;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:applicationContext-cache.xml", "classpath:applicationContext-dao.xml",
		"classpath:applicationContext-resources.xml", "classpath:applicationContext-service.xml" })
public class EmployeeServiceTestCase {
	@Resource
	private EmployeeService employeeService;
	@Resource
	private DetailService detailService;

	private static String id = "402881f840805661014080b121820005";

	@Test
	public void testInsert() {
//		Employee employee = new Employee();
//		employee.setName("张三");
//
//		Detail d = new Detail();
//		d.setCardnum("12045672");
//		d.setLocation("Beijing Olympic Park");
//		
//		d.setEmployee(employee);
		
//		employee.setDetail(d);
		
//		employeeService.doSaveEmployee(employee);
//		detailService.doSaveDetail(d);
//		id = String.valueOf(employee.getId());
//		System.out.println(id);
	}

	@Test
	public void testUpdate() {
		//Employee employee = employeeService.findEmployeeById("402881f840805661014080b121820005");
		//employee.setName("李四");
		//employeeService.doUpdateEmployee(employee);
	}

	@Test
	public void testFindEmployeeById() {
		//Employee employee = employeeService.findEmployeeById(id);
		//Employee employee2 = employeeService.findEmployeeById(id);
		//Employee employee3 = employeeService.findEmployeeById("402881f640bf50010140bf5005060000");
		//Detail d = employee3.getDetail();
		//System.out.println(d.getId());

		//Assert.assertEquals("李四", employee.getName());
		//Assert.assertEquals("李四", employee2.getName());
	}

	@Test
	public void testQueryEmployeeByQueryCriteria() {
//		QueryCriteria qc = new QueryCriteria();
//		qc.setCurrentIndex(0);
//		qc.setPageSize(2);
//		PageResult rp = employeeService.queryEmployee(qc);
	}

	@Test
	public void testDelete() {
		//employeeService.doDeleteEmployee("402881f640bf50010140bf5005060000");
	}

}
