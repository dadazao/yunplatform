/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.desktop.dao.DesktopLayoutDao;
import com.cloudstong.platform.desktop.model.DesktopLayout;
import com.cloudstong.platform.desktop.model.mapper.DesktopLayoutMapper;
import com.cloudstong.platform.desktop.service.DesktopLayoutService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:
 * 
 */
@Service("desktopLayoutService")
public class DesktopLayoutServiceImpl implements DesktopLayoutService {
	@Resource
	private DesktopLayoutDao desktopLayoutDao;

	@Override
	public PageResult queryDesktopLayout(QueryCriteria queryCriteria) {
		return desktopLayoutDao.query(queryCriteria, new DesktopLayoutMapper());
	}

	@Override
	public DesktopLayout findDesktopLayoutById(Long id) {
		return desktopLayoutDao.selectById(id, new DesktopLayoutMapper());
	}

	@Override
	public void doSaveDesktopLayout(DesktopLayout desktopLayout) {
		desktopLayout.setId(UniqueIdUtil.genId());
		String sql = "insert into sys_desktoplayout ( id,tbl_columnnum,tbl_columnwidths,tbl_deflt,tbl_description,tbl_name,comm_createDate,comm_updateDate,comm_createBy,comm_updateBy) values(?,?,?,?,?,?,?,?,?,?)";
		Date date = new Date();
		desktopLayoutDao.update(sql, new Object[] { desktopLayout.getId(), desktopLayout.getColumnNum(), desktopLayout.getColumnWidths(),
				desktopLayout.isDeflt(), desktopLayout.getDescription(), desktopLayout.getName(), date, date, desktopLayout.getCreateBy(),
				desktopLayout.getCreateBy() });
	}

	@Override
	public void doSaveOrUpdateDesktopLayout(DesktopLayout desktopLayout) {
		if (desktopLayout.getId() != null) {
			doUpdateDesktopLayout(desktopLayout);
		} else {
			doSaveDesktopLayout(desktopLayout);
		}
	}

	@Override
	public void doDeleteDesktopLayouts(Long[] ids) {
		for (Long id : ids) {
			desktopLayoutDao.delete(id);
		}
	}

	@Override
	public void doDeleteDesktopLayout(Long id) {
		desktopLayoutDao.delete(id);
	}

	@Override
	public void doUpdateDesktopLayout(DesktopLayout desktopLayout) {
		String sql = "update sys_desktoplayout set tbl_columnnum = ?,tbl_columnwidths=?,tbl_deflt=?,tbl_description=?,tbl_name=?,comm_updateDate=?,comm_updateBy=? where id=?";
		Date date = new Date();
		desktopLayoutDao.update(sql, new Object[] { desktopLayout.getColumnNum(), desktopLayout.getColumnWidths(), desktopLayout.isDeflt(),
				desktopLayout.getDescription(), desktopLayout.getName(), date, desktopLayout.getUpdateBy(), desktopLayout.getId() });
	}

	@Override
	public void doSetDefaultLayout(Long id) {
		String sql = "update sys_desktoplayout set tbl_deflt = ? ";
		desktopLayoutDao.update(sql, new Object[] { false });
		String sqlDef = "update sys_desktoplayout set tbl_deflt = ? ,comm_updateDate = ? where id = ?";
		desktopLayoutDao.update(sqlDef, new Object[] { true, new Date(), id });
	}

	@Override
	public List<DesktopLayout> findAllDesktopLayouts() {
		return desktopLayoutDao.getAllOrderBy("comm_updateDate", "DESC");
	}

	@Override
	public void doSaveDesktopDesign(SysUser user, Long id, String columns) {
		String sql = "insert into sys_desktopdesign ( id,tbl_columnnum,tbl_columnwidths,tbl_layoutitemids,tbl_userid) values(?,?,?,?,?)";
		DesktopLayout layout = findDesktopLayoutById(id);
		desktopLayoutDao.update(sql, new Object[] { UniqueIdUtil.genId(), layout.getColumnNum(), layout.getColumnWidths(), columns, user.getId() });
	}

	@Override
	public void doSaveOrUpdateLayoutDesign(Long id, String items) {
		String sql = "update sys_desktoplayout set tbl_items = ? where id= ?";
		desktopLayoutDao.update(sql, new Object[] { items, id });
	}
}
