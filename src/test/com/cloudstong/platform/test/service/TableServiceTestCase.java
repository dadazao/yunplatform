package com.cloudstong.platform.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.catalog.service.CatalogService;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { 
		"classpath:applicationContext-test.xml"
})
public class TableServiceTestCase {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private CatalogService catalogService;
	
	@Test
	public void testInsert() {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		List<Catalog> catalogs = catalogService.queryCatalog(qc);
		
	}

}
