/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.desktop.dao.DesktopItemDao;
import com.cloudstong.platform.desktop.model.DesktopItem;
import com.cloudstong.platform.desktop.model.mapper.DesktopItemMapper;
import com.cloudstong.platform.desktop.service.DesktopItemService;

/**
 * @author Jason
 * 
 *         Created on 2014-9-27
 * 
 *         Description:
 * 
 */
@Service("desktopItemService")
public class DesktopItemServiceImpl implements DesktopItemService {

	@Resource
	private DesktopItemDao desktopItemDao;

	@Override
	@Cacheable(value="desktopCache")
	public PageResult queryDesktopItem(QueryCriteria queryCriteria) {
		return desktopItemDao.query(queryCriteria,new DesktopItemMapper());
	}

	@Override
	@Cacheable(value="desktopCache")
	public DesktopItem findDesktopItemById(Long id) {
		return desktopItemDao.selectById(id, new DesktopItemMapper());
	}

	@Override
	@CacheEvict(value="desktopCache",allEntries=true,beforeInvocation=true)
	public void doDeleteDesktopItems(Long[] ids) {
		for (Long id : ids) {
			desktopItemDao.delete(id);
		}
	}

	@Override
	@CacheEvict(value="desktopCache",allEntries=true,beforeInvocation=true)
	public void doSaveDesktopItem(DesktopItem desktopItem) {
		desktopItem.setId(UniqueIdUtil.genId());
		String sql = "insert into sys_desktopitem ( id,tbl_alias,tbl_methodurl,tbl_moduleurl,tbl_name,tbl_templatehtml,comm_createBy,comm_updateBy,comm_createDate,comm_updateDate) values(?,?,?,?,?,?,?,?,?,?)";
		desktopItemDao.insert(
				sql,
				new Object[] { desktopItem.getId(), desktopItem.getAlias(), desktopItem.getMethodUrl(), desktopItem.getModuleUrl(),
						desktopItem.getName(), desktopItem.getTemplateHtml(), desktopItem.getCreateBy(), desktopItem.getCreateBy(),
						desktopItem.getCreateDate(), desktopItem.getCreateDate() });
	}

	@Override
	@CacheEvict(value="desktopCache",allEntries=true,beforeInvocation=true)
	public void doUpdateDesktopItem(DesktopItem desktopItem) {
		String sql = "update sys_desktopitem set tbl_alias=?,tbl_methodurl=?,tbl_moduleurl=?,tbl_name=?,tbl_templatehtml=?,comm_updateBy=?,comm_updateDate = ? where id = ?";
		desktopItemDao.update(
				sql,
				new Object[] { desktopItem.getAlias(), desktopItem.getMethodUrl(), desktopItem.getModuleUrl(), desktopItem.getName(),
						desktopItem.getTemplateHtml(), desktopItem.getUpdateBy(), desktopItem.getUpdateDate(), desktopItem.getId() });
	}

	@Override
	@Cacheable(value="desktopCache")
	public List<DesktopItem> findAllDesktopItems() {
		return desktopItemDao.getAllByOrder("comm_updateDate", "DESC");
	}
}
