/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.desktop.dao.DesktopDesignDao;
import com.cloudstong.platform.desktop.model.DesktopDesign;
import com.cloudstong.platform.desktop.model.mapper.DesktopDesignMapper;
import com.cloudstong.platform.desktop.service.DesktopDesignService;

/**
 * @author Jason
 * 
 *         Created on 2014-9-29
 * 
 *         Description:
 * 
 */
@Service("desktopDesignService")
public class DesktopDesignServiceImpl implements DesktopDesignService {

	@Resource
	private DesktopDesignDao desktopDesignDao;

	@Override
	public void doSaveOrUpdate(DesktopDesign desktopDesign) {
		if (desktopDesign.getId() == null) {
			this.doSave(desktopDesign);
		} else {
			this.doUpdate(desktopDesign);
		}
	}

	@Override
	public DesktopDesign findDefault() {
		String sql = "select id,tbl_columnnum,tbl_columnwidths,tbl_items as tbl_layoutitemids,comm_createDate,comm_updateDate,comm_createBy,comm_updateBy from sys_desktoplayout where tbl_deflt = ?";
		List<DesktopDesign> list = desktopDesignDao.select(sql, new Object[] { true }, new RowMapper<DesktopDesign>() {
			@Override
			public DesktopDesign mapRow(ResultSet rs, int rowNum) throws SQLException {
				DesktopDesign desktopDesign = new DesktopDesign();
				desktopDesign.setId(rs.getLong("id"));
				desktopDesign.setColumnNum(rs.getInt("tbl_columnnum"));
				desktopDesign.setColumnWidths(rs.getString("tbl_columnwidths"));
				desktopDesign.setLayoutItemIds(rs.getString("tbl_layoutitemids"));
				// desktopDesign.setUserid(rs.getLong("tbl_userid"));

				desktopDesign.setCreateBy(rs.getLong("comm_createBy"));
				desktopDesign.setUpdateBy(rs.getLong("comm_updateBy"));
				desktopDesign.setCreateDate(rs.getTimestamp("comm_createDate"));
				desktopDesign.setUpdateDate(rs.getTimestamp("comm_updateDate"));

				return desktopDesign;
			}
		});
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public DesktopDesign findByUserId(Long userId) {
		String sql = "select * from sys_desktopdesign where tbl_userid = ?";
		List<DesktopDesign> list = desktopDesignDao.select(sql, new Object[] { userId }, new DesktopDesignMapper());
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void doSave(DesktopDesign desktopDesign) {
		desktopDesign.setId(UniqueIdUtil.genId());
		String sql = "insert into sys_desktopdesign ( id,tbl_columnnum,tbl_columnwidths,tbl_layoutitemids,tbl_userid) values(?,?,?,?,?)";
		desktopDesignDao.insert(sql, new Object[] { desktopDesign.getId(), desktopDesign.getColumnNum(), desktopDesign.getColumnWidths(),
				desktopDesign.getLayoutItemIds(), desktopDesign.getUserid() });
	}

	@Override
	public void doUpdate(DesktopDesign desktopDesign) {
		String sql = "update sys_desktopdesign set tbl_columnnum = ?,tbl_columnwidths=?,tbl_layoutitemids=?,tbl_userid=? where id = ?";
		desktopDesignDao.update(sql, new Object[] { desktopDesign.getColumnNum(), desktopDesign.getColumnWidths(), desktopDesign.getLayoutItemIds(),
				desktopDesign.getUserid(), desktopDesign.getId() });
	}

	@Override
	public DesktopDesign findById(Long id) {
		return desktopDesignDao.selectById(id, new DesktopDesignMapper());
	}

	@Override
	public DesktopDesign loadDesktop(Long id) {
		DesktopDesign desktop = findByUserId(id);
		if (desktop == null) {
			desktop = findDefault();
		}
		return desktop;
	}

}
