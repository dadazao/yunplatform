/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.resource.metadata.model.DataSources;
import com.cloudstong.platform.system.dao.DataSourceDao;
import com.cloudstong.platform.system.model.DataSourcePojo;

/**
 * @author liuqi
 * Created on 2014-10-9 15:14:38
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
@Repository("idataSourceDao")
public class DataSourceDaoImpl extends BaseJdbcDaoImpl<DataSourcePojo, Long> implements DataSourceDao  {

	@Override
	public String getTable() {
		return "sys_datasources";
	}
	
	@Override
	public String doChangeDataBase(String psId) {
		String sqlDsEnable = "select * from sys_datasources where tbl_dsstatus=?";

		Object[] params = new Object[] { "1" };
		
		List<DataSources> _lstResult = this.jdbcTemplate.query(sqlDsEnable,
				params, new BeanPropertyRowMapper(DataSources.class));

		if (true) {//_lstResult != null && _lstResult.size() > 0
			DataSources oldDataSource = _lstResult.get(0);
			if (psId.equals(String.valueOf(oldDataSource.getId()))) {
				return "{\"status\":\"warn\",\"message\":\"没有改变数据源,不需要启动!\"}";
			}
			DataSources dataSource = findDataSourceById(psId);
			try {
				if ("mysql".equals(dataSource.getTblDstype())) {
				} else if ("oracle".equalsIgnoreCase(dataSource.getTblDstype())) {
					OracleInnerDaoImpl oracle = new OracleInnerDaoImpl(jdbcTemplate,
							dataSource);
					oracle.deleteOralceTables(dataSource.getTblDsuser().toUpperCase());
					oracle.createOrcle(dataSource.getTblDsuser().toUpperCase());
					//oracle.changeJdbcProp(dataSource);
				} else if ("shentong".equalsIgnoreCase(dataSource.getTblDstype())) {
					ShentongInnerDaoImpl shentong = new ShentongInnerDaoImpl(
							jdbcTemplate, dataSource);
					shentong.deleteShentongTables();
					shentong.createShentong();
					shentong.changeJdbcProp(dataSource);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			return "{\"status\":\"info\",\"message\":\"数据源改动成功,你已经切换到"
					+ dataSource.getTblDstype() + "数据库!\"}";
		} else {
			return "{\"status\":\"error\",\"message\":\"数据源异常,请检查数据库中是否存在默认数据源.\"}";
		}
	}

	private DataSources findDataSourceById(String plId) {
		String sql = "SELECT * FROM sys_DATASOURCES WHERE ID=?";
		DataSources dataSource = new DataSources();
		Object[] params = new Object[] { plId };
		List<DataSources> _lstResult = this.jdbcTemplate.query(sql, params,
				new BeanPropertyRowMapper(DataSources.class));

		if (_lstResult != null) {
			dataSource = _lstResult.get(0);
		}
		return dataSource;

	}

}
