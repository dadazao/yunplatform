/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.system.dao.SubSystemDao;
import com.cloudstong.platform.system.model.SubSystem;
import com.cloudstong.platform.system.service.SubSystemService;

/**
 * @author Allan
 * 
 *         Created on 2014-10-15
 * 
 *         Description:
 * 
 */
@Repository("subSystemService")
public class SubSystemServiceImpl implements SubSystemService {

	@Resource
	private SubSystemDao subSystemDao;
	
	@Override
	public List<SubSystem> getLocalSystem() {
		List<SubSystem> subSystems = new ArrayList<SubSystem>();
		
		SubSystem subSysteam = new SubSystem();
		subSysteam.setId(0L);
		subSysteam.setSysName("云快速开发与管理平台");
		subSysteam.setAlias("YUNPLATFORM");
		subSystems.add(subSysteam);
		
		return subSystems;
	}

	@Override
	public SubSystem getById(Long id) {
		return subSystemDao.selectById(id, new RowMapper<SubSystem>(){
			@Override
			public SubSystem mapRow(ResultSet rs, int rowNum) throws SQLException {
				SubSystem subSystem = new SubSystem();
				subSystem.setId(rs.getLong("id"));
				subSystem.setAlias(rs.getString("tbl_alias"));
				subSystem.setDefaultUrl(rs.getString("tbl_url"));
				subSystem.setHomePage(rs.getString("tbl_homepage"));
				subSystem.setRemark(rs.getString("tbl_remark"));
				return subSystem;
			}
		});
	}

}
