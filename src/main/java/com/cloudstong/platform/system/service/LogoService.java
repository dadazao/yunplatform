package com.cloudstong.platform.system.service;

import java.util.List;

import com.cloudstong.platform.system.model.Logo;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:LOGO服务接口
 */
public interface LogoService {
	/**
	 * Description:遍历图片，放入list集合
	 * @return Logo集合
	 */
	public List findLogo();

	/**
	 * Description:根据Logo ID 查找Logo
	 * @param id Logo ID
	 * @return Logo
	 */
	public Logo findLogoById(Long id);

	/**
	 * Description:根据Logo ID修改默认的Logo
	 * @param id Logo ID
	 */
	public void doUpdateLogo(Long id);

	/**
	 * Description:新增一个Logo
	 * @param logo
	 */
	public void doInsertLogo(Logo logo);

	/**
	 * Description:获取默认的Logo的名称
	 * @return Logo名称
	 */
	public String getLogoName();

	/**
	 * Description:删除Logo
	 * @param ids Logo ID的数组
	 */
	public void doDelete(Long[] ids);

	/**
	 * Description:获取默认的Logo
	 * @return Logo
	 */
	public Logo findDefaultLogo();
}
