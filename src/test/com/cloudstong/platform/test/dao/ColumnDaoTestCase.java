package com.cloudstong.platform.test.dao;

import java.util.List;

import junit.framework.Assert;

import com.cloudstong.platform.resource.metadata.dao.ColumnDao;
import com.cloudstong.platform.resource.metadata.model.Column;

public class ColumnDaoTestCase extends BaseDaoTestCase {

	private ColumnDao columnDao;

	public ColumnDao getColumnDao() {
		return columnDao;
	}

	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	public void testInsert() {
		Column column = new Column();
		column.setColumnName("chello");
		column.setBelongTable("hello2");
		column.setDataType("varchar");
		column.setLength(200);
		columnDao.insert(column, true);
	}

	public void testUpdate() {
		/*Column column = columnDao.selectById("1");
		column.setColumnZhName("你好");
		column.setColumnName("chello4");
		column.setDataType("int");
		column.setLength(20);
		column.setBelongTable("hello2");
		columnDao.update(column);*/
	}

	public void testSelectById() {
		//Column column = columnDao.selectById("1");
		//Assert.assertEquals("hello", column.getColumnName());
	}

	public void testSelect() {
		String sql = "select * from columns";
		List columns = columnDao.select(sql, new Object[] {}, 0, 2);
		Assert.assertEquals(1, columns.size());
	}

	public void testDelete() {
		//columnDao.delete("2");
	}

}
