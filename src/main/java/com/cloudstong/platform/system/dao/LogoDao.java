package com.cloudstong.platform.system.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.system.model.Logo;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:操作Logo的数据库接口
 */
public interface LogoDao {
	/**
	 * Description:根据Logo ID 查找Logo
	 * @param id Logo ID
	 * @return Logo
	 */
	Logo findLogoById(Long id);

	/**
	 * Description:根据主题ID查找Logo
	 * @param themeId 主题ID
	 * @return Logo
	 */
	List<Logo> findLogByThemeId(Long themeId);

	/**
	 * Description:保存Logo
	 * @param logo
	 */
	@CacheEvict(value = "themeCache", allEntries = true, beforeInvocation = true)
	void insert(Logo logo);

	/**
	 * Description:获取默认的Logo的名称
	 * @return Logo名称
	 */
	String getLogoName();

	/**
	 * Description:删除Logo
	 * @param ids Logo ID的数组
	 */
	@CacheEvict(value = "themeCache", allEntries = true, beforeInvocation = true)
	void delete(Long[] ids);

	/**
	 * Description:根据Logo ID修改默认的Logo
	 * @param id Logo ID
	 */
	@CacheEvict(value = "themeCache", allEntries = true, beforeInvocation = true)
	void update(Long id);

	/**
	 * Description:获取默认的Logo
	 * @return Logo
	 */
	@Cacheable(value="themeCache")
	Logo findDefaultLogo();
}
