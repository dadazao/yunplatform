/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.DataSourceDao;
import com.cloudstong.platform.system.model.DataSourcePojo;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.rowmap.DataSourceRowMapper;
import com.cloudstong.platform.system.service.DataSourceService;

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
@Repository("idataSourceService")
public class DataSourceServiceImpl implements DataSourceService {

	@Resource
	private DataSourceDao idataSourceDao;
	
	public String doChangeDataBase(String psId) {
		return idataSourceDao.doChangeDataBase(psId);
	}

	@Override
	public Long doSaveDataSource(DataSourcePojo dataSource, SysUser user) {
		Long id = -1L;
		Date now = new Date(System.currentTimeMillis());
		if(dataSource.getId()==null||dataSource.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_datasources (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_dsName,tbl_dsDriver,tbl_dsUrl,tbl_dsUser,tbl_dsPasswd,tbl_dsEncoding,tbl_dsType,tbl_comment,tbl_remark)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,dataSource.getDsName(),dataSource.getDsDriver(),dataSource.getDsUrl(),dataSource.getDsUser(),dataSource.getDsPasswd(),dataSource.getDsEncoding(),dataSource.getDsType(),dataSource.getComment(),dataSource.getRemark()};
			idataSourceDao.insert(sql, args);
			id = uuid;
		}else{
			id = dataSource.getId();
			String sql = "update sys_datasources set comm_updateBy=?,comm_updateDate=?,tbl_dsName=?,tbl_dsDriver=?,tbl_dsUrl=?,tbl_dsUser=?,tbl_dsPasswd=?,tbl_dsEncoding=?,tbl_dsType=?,tbl_comment=?,tbl_remark=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,dataSource.getDsName(),dataSource.getDsDriver(),dataSource.getDsUrl(),dataSource.getDsUser(),dataSource.getDsPasswd(),dataSource.getDsEncoding(),dataSource.getDsType(),dataSource.getComment(),dataSource.getRemark(),dataSource.getId()};
			idataSourceDao.update(sql, args);
		}
		return id; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataSourcePojo findDataSourceById(Long id) {
		return idataSourceDao.selectById(id, new DataSourceRowMapper());
	}

	@Override
	public void doDeleteDataSources(Long[] dataSourceIDs) {
		for (Long id : dataSourceIDs) {
			idataSourceDao.delete(id);
		}
	}

	@Override
	public PageResult queryDataSource(QueryCriteria queryCriteria) {
		return idataSourceDao.query(queryCriteria,new DataSourceRowMapper());
	}

}
