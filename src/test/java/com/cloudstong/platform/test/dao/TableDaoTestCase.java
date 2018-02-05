package com.cloudstong.platform.test.dao;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import com.cloudstong.platform.resource.metadata.dao.TableDao;
import com.cloudstong.platform.resource.metadata.model.Table;

public class TableDaoTestCase extends BaseDaoTestCase {

	private TableDao tableDao;

	public TableDao getTableDao() {
		return tableDao;
	}

	public void setTableDao(TableDao tableDao) {
		this.tableDao = tableDao;
	}

	public void testInsert() {
		Table table = new Table();
		table.setTableName("hellohello");
		tableDao.insert(table);
	}

	public void testUpdate() {
		/*Table table = tableDao.selectById("1");
		table.setTableName("hello2");
		table.setTableZhName("你好");
		tableDao.update(table);*/
	}

	public void testSelectById() {
		/*Table table = tableDao.selectById("1");
		Assert.assertEquals("hello", table.getTableName());*/
	}

	public void testSelect() {
		String sql = "select * from tables";
		List tables = tableDao.select(sql, new Object[] {}, 0, 2);
		Assert.assertEquals(1, tables.size());
	}

	public void testDelete() {
		//tableDao.delete("2");
	}

}
