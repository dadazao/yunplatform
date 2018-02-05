package com.cloudstong.platform.test.service;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.service.ColumnService;

public class ColumnServiceTestCase extends BaseServiceTestCase {

	private ColumnService columnService;

	public ColumnService getColumnService() {
		return columnService;
	}

	public void setColumnService(ColumnService columnService) {
		this.columnService = columnService;
	}

	public void testInsert() {
		Column column = new Column();
		column.setColumnName("hello");
		columnService.doSaveColumn(column);
	}

	public void testUpdate() {
		Column column = new Column();
		column.setId(1l);
		column.setColumnZhName("你好吗");
		columnService.doUpdateColumn(column);
	}

	public void testFindColumnById() {
//		Column column = columnService.findColumnById("1");
//		Assert.assertEquals("hello", column.getColumnName());
	}

	public void testDelete() {
//		columnService.doDeleteColumn("3");
	}

	public void testQueryColumn() {
		List<Column> columns = columnService.queryColumn(0, 2);
		Assert.assertEquals(1, columns.size());
	}

	public void testQueryColumnByQueryCriteria() {
		QueryCriteria qc = new QueryCriteria();
		qc.setCurrentIndex(0);
		qc.setPageSize(2);
		Map map = qc.getQueryCondition();
		map.put("columnName", "hello");
		List<Column> columns = columnService.queryColumn(qc);
		Assert.assertEquals(1, columns.size());
	}

}
