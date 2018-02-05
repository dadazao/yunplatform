package com.cloudstong.platform.resource.metadata.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.metadata.dao.ColumnDao;
import com.cloudstong.platform.resource.metadata.dao.TableDao;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.ConfigColumn;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.service.ColumnService;

/**
 * @author Allan Created on 2012-11-20
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:字段管理服务实现
 */
@Repository("columnService")
public class ColumnServiceImpl implements ColumnService {

	@Resource
	private ColumnDao columnDao;

	@Resource
	private TableDao tableDao;

	public TableDao getTableDao() {
		return tableDao;
	}

	public void setTableDao(TableDao tableDao) {
		this.tableDao = tableDao;
	}

	public ColumnDao getColumnDao() {
		return columnDao;
	}

	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	@Override
	public void doSaveColumn(Column column) {
		Table table = tableDao.selectById(column.getTableId());
		column.setBelongTable(table.getTableName());
		columnDao.insert(column, true);
	}

	@Override
	public void doDeleteColumn(Long id) {
		columnDao.delete(id);
	}

	@Override
	public void doUpdateColumn(Column column) {
		columnDao.update(column);
	}

	@Override
	public List<Column> queryColumn(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select a.*,b.tbl_tableZhName as tbl_tableZhName,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_columns a left join sys_tables b on a.tbl_tableId=b.id left join sys_user u on a.comm_createBy = u.id   left join sys_user u2 on a.comm_updateBy = u2.id    where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			if (entry.getKey().equalsIgnoreCase("tbl_tableZhName")) {
				sql.append(" and tbl_tableZhName like ? ");
				param.add("%" + ((String)entry.getValue()).trim() + "%");
				continue;
			}
			if (entry.getKey().equalsIgnoreCase("tbl_tableId")) {
				sql.append(" and a." + entry.getKey() + " =? ");
				param.add(entry.getValue());
				continue;
			}
			if (entry.getKey().equalsIgnoreCase("tbl_belongTable")) {
				sql.append(" and a." + entry.getKey() + " =? ");
				param.add(entry.getValue());
			} else if (entry.getValue() instanceof String) {
				sql.append(" and a." + entry.getKey() + " like ? ");
				param.add("%" + ((String)entry.getValue()).trim() + "%");
			} else {
				sql.append(" and a." + entry.getKey() + " =? ");
				param.add(entry.getValue());
			}

		}
		sql.append(" order by a.comm_updateDate desc");
		return columnDao.select(sql.toString(), param.toArray(), qc.getCurrentIndex(), qc.getPageSize());

	}

	@Override
	public List<Column> queryColumnAuto(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select a.*,b.tbl_tableZhName as tbl_tableZhName,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_columns a left join sys_tables b on a.tbl_tableId=b.id left join sys_user u on a.comm_createBy = u.id   left join sys_user u2 on a.comm_updateBy = u2.id    where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			if (entry.getKey().equalsIgnoreCase("tbl_tableZhName")) {
				sql.append(" and tbl_tableZhName like ? ");
				param.add("%" + ((String)entry.getValue()).trim() + "%");
				continue;
			} else {
				sql.append(" and a." + entry.getKey() + " =? ");
				param.add(entry.getValue());
			}

		}
		sql.append(" order by a.comm_createDate asc");
		return columnDao.select(sql.toString(), param.toArray(), qc.getCurrentIndex(), qc.getPageSize());

	}

	@Override
	public List<Column> queryColumn(int startRow, int rowsCount) {
		String sql = "select * from sys_columns";
		return columnDao.select(sql, new Object[] {}, startRow, rowsCount);
	}

	@Override
	public Column findColumnById(Long id) {
		return columnDao.selectById(id);
	}

	@Override
	public int count(QueryCriteria qc) {
		return columnDao.count(qc);
	}

	@Override
	public String findValueByColumn(String tableName, String columnName, String whereColumn) {
		return columnDao.findValueByColumn(tableName, columnName, whereColumn);
	}

	@Override
	public void doUpdateColumn(Column column, Map<String, String> dyncMap) {
		Table table = tableDao.selectById(column.getTableId());
		column.setBelongTable(table.getTableName());
		columnDao.update(column, dyncMap);
	}

	@Override
	public void doSaveColumn(Column column, Map<String, String> dyncMap) {
		Table table = tableDao.selectById(column.getTableId());
		column.setBelongTable(table.getTableName());
		columnDao.insert(column, true, dyncMap);
	}

	@Override
	public void doDeleteColumns(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			columnDao.delete(id);
		}

	}

	@Override
	public boolean isColumnZhNameDouble(Long tableId, String columnZhName, Long columnId) {
		List columns = columnDao.queryColumnByField(tableId, columnZhName, "tbl_columnZhName");
		if (columns != null && columns.size() > 1) {
			return true;
		} else if (columns != null && columns.size() == 1 && columnId == null) {
			return true;
		} else if (columns != null && columns.size() == 1 && columnId != null && !((Column) columns.get(0)).getId().equals(columnId)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isColumnNameDouble(Long tableId, String columnName, Long columnId) {
		List columns = columnDao.queryColumnByField(tableId, columnName, "tbl_columnName");
		if (columns != null && columns.size() > 1) {
			return true;
		} else if (columns != null && columns.size() == 1 && columnId == null) {
			return true;
		} else if (columns != null && columns.size() == 1 && columnId != null && !((Column) columns.get(0)).getId().equals(columnId)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void doUpdateStauts(Long id, int i) {
		columnDao.updateStatus(id, i);
	}

	@Override
	public void doLogicDeleteColumn(Long id) {
		columnDao.logicDelete(id);
	}

	@Override
	public void doLogicDeleteColumns(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			columnDao.logicDelete(id);
		}
	}

	/*
	 * @see
	 * com.cloudstongplatform.resource.metadata.service.ColumnService#doConfigColumns
	 * (java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doConfigColumns(String configStr) {
		Assert.notNull(configStr);
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, List<LinkedHashMap<String, Object>>> map = mapper.readValue(configStr, Map.class);
			List<LinkedHashMap<String, Object>> list = map.get("elements");
			Collection<Object> column = list.get(0).values();
			Object[] objs = column.toArray();
			LinkedHashMap<String, Object> cmap = (LinkedHashMap<String, Object>) objs[1];
			String tableName = cmap.get("belongTable").toString();
			columnDao.clearCfgColumnByTableName(tableName);
			columnDao.saveCfgColumns(list);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ConfigColumn> queryCfgColumnByTableName(String tableName) {
		Assert.notNull(tableName);
		return columnDao.selectCfgColumnByTableName(tableName);
	}

	@Override
	public List<ConfigColumn> queryCftColumn(String tableName) {
		Assert.notNull(tableName);
		return columnDao.selectCfgColumn(tableName);
	}

	@Override
	public int countColumn(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select count(*) from sys_columns a where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and a." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by a.id asc");
		return columnDao.count(sql.toString(), param.toArray());
	}

}
