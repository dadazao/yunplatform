package com.cloudstong.platform.resource.searchcombox.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.searchcombox.model.SearchCombox;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:搜索下拉框服务接口
 */
public interface SearchComboxService {
	/**
	 * Description:根据搜索下拉框ID查找搜索下拉框
	 * @param ID 搜索下拉框ID
	 * @return 搜索下拉框
	 * @throws Exception
	 */
	public SearchCombox findByID(Long id) throws Exception;
	
	/**
	 * Description:查询搜索下拉框集合
	 * @param queryCriteria 查询条件
	 * @return 搜索下拉框集合
	 * @throws Exception
	 */
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception;
}
