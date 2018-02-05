package com.cloudstong.platform.system.dao;

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
 * Description:菜单数据操作Dao
 */
public interface MenuDao {

	/**
	 * Description:获得默认菜单
	 * 
	 * @return
	 */
	public List<MenuItem> getDefaultMenu() ;

	/**
	 * Description:更新菜单
	 * 
	 * @param id
	 */
	public void doUpdateMenu(Long id);

}
