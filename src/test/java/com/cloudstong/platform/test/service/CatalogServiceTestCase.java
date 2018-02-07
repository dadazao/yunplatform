package com.cloudstong.platform.test.service;

import java.util.List;
import java.util.Map;

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
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml"})
public class CatalogServiceTestCase{

	@Resource
	private CatalogService catalogService;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public void updateCatalogPath() {
		String sql = "SELECT c.id,c.tbl_name,c.tbl_listid,c.tbl_path,b.tbl_zhubiao,b.tbl_zhubiaoid FROM `sys_catalog` c LEFT JOIN sys_liebiao l on c.tbl_listid=l.id LEFT JOIN sys_biaodan b on l.tbl_formid=b.id where b.tbl_zhubiao is not null and c.tbl_listid!=-1;";
		List<Map<String,Object>> os = jdbcTemplate.queryForList(sql);
		
		String updateSql = "update sys_catalog set tbl_path=? where id=?";
		for(Map<String,Object> o : os) {
			Long id = (Long)o.get("id");
			String path = (String)o.get("tbl_path");
			Long listId = (Long)o.get("tbl_listid");
			Long zhubiaoid = (Long)o.get("tbl_zhubiaoid");
			if(path.contains("compexlist.action")) {
				path = path.replace(listId.toString()+"compexlist.action", zhubiaoid.toString()+"compexlist.action");
				jdbcTemplate.update(updateSql, new Object[]{path,id});
				System.out.println(path);
			}
		}
	}
	
	public void updateResourcePath() {
		String sql = "SELECT * FROM sys_catalog";
		List<Map<String,Object>> os = jdbcTemplate.queryForList(sql);
		
		String updateSql = "update sys_resource set tbl_resourceurl=? where tbl_module=?";
		for(Map<String,Object> o : os) {
			Long id = (Long)o.get("id");
			String path = (String)o.get("tbl_path");
			jdbcTemplate.update(updateSql, new Object[]{path,id});
			System.out.println(path);
			
		}
	}


	@Test
	public void testUpdate() {
		Catalog catalog = new Catalog();
		catalog.setId(18l);
		catalog.setName("你好吗");
	}

	public void testFindCatalogById() {
		Catalog catalog = catalogService.findCatalogById(18l);
	}

	public void testDelete() {
		catalogService.doDeleteCatalog(18l);
	}

	public void testQueryCatalogByQueryCriteria() {
		QueryCriteria qc = new QueryCriteria();
		qc.setCurrentIndex(0);
		qc.setPageSize(2);
		Map map = qc.getQueryCondition();
		map.put("name", "目录树模板");
		List<Catalog> catalogs = catalogService.queryCatalog(qc);
	}

}
