package com.cloudstong.platform.system.service;

import com.cloudstong.platform.system.model.Theme;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:主题管理的服务
 */
public interface ThemeService {

	/**
	 * Description:更新主题
	 * 
	 * 
	 * @param id
	 */
	public void doUpdateTheme(Long id);

	/**
	 * Description:返回缺省主题
	 * 
	 * 
	 * @return
	 */
	public Theme doGetDefaultTheme();
	
	/**
	 * Description:删除主题
	 * 
	 * @param ids
	 */
	public void doDelete(Long[] ids);

}
