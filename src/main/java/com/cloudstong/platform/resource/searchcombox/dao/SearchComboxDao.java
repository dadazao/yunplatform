package com.cloudstong.platform.resource.searchcombox.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.searchcombox.model.SearchCombox;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:搜索下拉框操作数据库接口
 */
public interface SearchComboxDao {

	/**
	 * Description:根据搜索下拉框ID查找搜索下拉框
	 * @param ID 搜索下拉框ID
	 * @return 搜索下拉框
	 * @throws Exception
	 */
	@Cacheable(value="resourceCache",key="'SearchCombox_findByID:'+#id")
	public SearchCombox findByID(Long id);

	/**
	 * Description:查询搜索下拉框集合
	 * @param sql 查询语句
	 * @param array 参数值
	 * @param currentIndex 当前页
	 * @param pageSize 每页记录数
	 * @return 搜索下拉框集合
	 * @throws Exception
	 */
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String sql, Object[] array, int currentIndex, int pageSize);

}
