package com.cloudstong.platform.resource.metadata.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.metadata.dao.ColumnDao;
import com.cloudstong.platform.resource.metadata.dao.TableDao;
import com.cloudstong.platform.resource.metadata.model.Meta;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.service.TableService;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:数据表服务实现
 */
@Repository("tableService")
public class TableServiceImpl implements TableService {

	@Resource
	private TableDao tableDao;
	
	@Resource
	private ColumnDao columnDao;

	public TableDao getTableDao() {
		return tableDao;
	}

	public void setTableDao(TableDao tableDao) {
		this.tableDao = tableDao;
	}

	@Override
	public Long doSaveTable(Table table) {
		return tableDao.insert(table);
	}

	@Override
	public void doDeleteTable(Long id) {
		tableDao.delete(id);
	}

	@Override
	public void doUpdateTable(Table table) {
		tableDao.update(table);
	}

	@Override
	public List<Table> queryTable(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select t.*,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_tables t left join sys_user u on t.comm_createBy = u.id   left join sys_user u2 on t.comm_updateBy = u2.id   where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			if(entry.getKey().equalsIgnoreCase("tbl_tableName")){
				sql.append(" and t." + entry.getKey() + " =? ");
				param.add(entry.getValue());
			}else if (entry.getValue() instanceof String) {
				sql.append(" and t." + entry.getKey() + " like ? ");
				param.add("%" + ((String)entry.getValue()).trim() + "%");
			} else {
				sql.append(" and t." + entry.getKey() + " =? ");
				param.add(entry.getValue());
			}
		}
		sql.append(" order by t.comm_updateDate desc ");
		return tableDao.select(sql.toString(), param.toArray(),
				qc.getCurrentIndex(), qc.getPageSize());
	}

	@Override
	public List<Table> queryTable(int startRow, int rowsCount) {
		String sql = "select * from sys_tables";
		return tableDao.select(sql, new Object[] {}, startRow, rowsCount);
	}

	@Override
	public Table findTableById(Long id) {
		return tableDao.selectById(id);
	}

	@Override
	public int countTable(QueryCriteria qc) {
		return tableDao.count(qc);
	}

	@Override
	public void initializeDataTable() {
		tableDao.initializeDataTable();
	}

	@Override
	public String findValueByColumn(String tableName, String columnName) {
		return tableDao.findValueByColumn(tableName, columnName);
	}

	@Override
	public void doDeleteTables(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			tableDao.delete(id);
		}
	}

	@Override
	public boolean isTableNameDouble(String tableZhName) {
		List tables = tableDao.queryTableByField(tableZhName,"tbl_tableName");
		if(tables != null && tables.size()>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isTableZhNameDouble(String tableZhName) {
		List tables = tableDao.queryTableByField(tableZhName,"tbl_tableZhName");
		if(tables != null && tables.size()>0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Table> findTables(String tableId) {
		return tableDao.findTables(tableId);
	}

	@Override
	public String findTableTypeByName(String _tableName) {
		return tableDao.findTableTypeByName(_tableName);
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateStauts(Long id, int i) {
		tableDao.updateStatus(id,i);
		
	}

	@Override
	public void dologicDeleteTables(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			tableDao.logicDelete(id);
		}
	}

	@Override
	public List<Table> findTablesByTeam(Long tableId, String systemTeam) {
		return tableDao.findTablesByTeam(tableId, systemTeam);
	}

	@Override
	public void doAutoCreateLogTable() {
		tableDao.autoCreateLogTable();
	}
	
	@Override
	public void doChangeVersion(String id) {
		Table table = tableDao.selectById(Long.valueOf(id));
		List<Map<String,Object>> columns = columnDao.findColumnsByTableId(id);
		for(int i=0; i<columns.size(); i++) {
			Map<String,Object> map = columns.get(i);
			Meta meta =  new Meta();
			meta.setVersion(table.getVersion());
			meta.setTableId(table.getId());
			meta.setBelongTable(table.getTableName());
			meta.setColumnId(String.valueOf(map.get("id")));
			meta.setColumnZhName((String)map.get("tbl_columnZhName"));
			meta.setColumnName((String)map.get("tbl_columnName"));
			meta.setDataType((String)map.get("tbl_dataType"));
			meta.setColumnCode((String)map.get("tbl_columnCode"));
			meta.setIsRequired((Integer)map.get("tbl_isRequired"));
			meta.setIsRepeat((Integer)map.get("tbl_isRepeat"));
			meta.setMin((Integer)map.get("tbl_min"));
			meta.setMax((Integer)map.get("tbl_Max"));
			meta.setMetaType((Integer)map.get("tbl_metaType"));
			meta.setDefaultValue((String)map.get("tbl_defaultValue"));
			tableDao.insertMeta(meta);
		}
		int version = Integer.valueOf(table.getVersion()) + 1;
		tableDao.updateVersion(new Integer(version).toString(),id);
	}

	@Override
	public List queryHistory(String id) {
		return tableDao.selectHistoryById(id);
	}

	@Override
	public List queryMetaHistory(String id, String version) {
		return tableDao.selectMetaHistoryById(id,version);
	}

}
