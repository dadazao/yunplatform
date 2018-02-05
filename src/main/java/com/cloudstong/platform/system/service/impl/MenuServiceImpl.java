package com.cloudstong.platform.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.system.dao.MenuDao;
import com.cloudstong.platform.system.model.MenuItem;
import com.cloudstong.platform.system.service.MenuService;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:菜单管理实现
 */
@Repository("menuService")
public class MenuServiceImpl implements MenuService{

	@Resource
	private MenuDao menuDao;
	
	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public List<MenuItem> getDefaultMenu() {
		return menuDao.getDefaultMenu();
	}

	@Override
	public void doUpdateMenu(Long id) {
		menuDao.doUpdateMenu(id);
	}

}
