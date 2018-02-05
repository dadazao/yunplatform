package com.cloudstong.platform.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.system.dao.ThemeDao;
import com.cloudstong.platform.system.model.Theme;
import com.cloudstong.platform.system.service.ThemeService;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:主题管理的实现
 */
@Repository("themeService")
public class ThemeServiceImpl implements ThemeService {

	@Resource
	private ThemeDao themeDao;

	public ThemeDao getThemeDao() {
		return themeDao;
	}

	public void setThemeDao(ThemeDao themeDao) {
		this.themeDao = themeDao;
	}

	@Override
	public void doUpdateTheme(Long id) {
		themeDao.update(id);
	}

	@Override
	public Theme doGetDefaultTheme() {
		return themeDao.getDefaultTheme();
	}

	@Override
	public void doDelete(Long[] ids) {
		themeDao.delete(ids);
	}

}
