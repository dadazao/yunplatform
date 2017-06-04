package com.cloudstong.platform.system.service;

import java.util.List;

import com.cloudstong.platform.system.model.MenuItem;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:菜单管理服务
 */
public interface MenuService {

	/**
	 * Description:获得缺省菜单
	 * 
	 * @return
	 */
	public List<MenuItem> getDefaultMenu();

	/**
	 * Description:更新菜单
	 * 
	 * @param id
	 */
	public void doUpdateMenu(Long id);

}
