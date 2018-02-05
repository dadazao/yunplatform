package com.cloudstong.platform.system.dao;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.system.model.Theme;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:主题数据操作Dao
 */
public interface ThemeDao {
	/**
	 * Description:更新
	 * 
	 * 
	 * @param id
	 */
	@CacheEvict(value = "themeCache", allEntries = true, beforeInvocation = true)
	public void update(Long id);

	/**
	 * Description:查找默认主题
	 * 
	 * 
	 * @return
	 */
	@Cacheable(value="themeCache")
	public Theme getDefaultTheme();
	
	/**
	 * Description:删除主题
	 * 
	 * 
	 * @param ids
	 */
	@CacheEvict(value = "themeCache", allEntries = true, beforeInvocation = true)
	public void delete(Long[] ids);
}
