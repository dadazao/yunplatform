package com.cloudstong.platform.resource.combox.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.combox.model.Combox;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:下拉框服务接口
 */
public interface ComboxService {
	/**
	 * 根据下拉框ID查找 下拉框
	 * @param ID 下拉框ID
	 * @return 下拉框
	 * @throws Exception
	 */
	public Combox findByID(Long id) throws Exception;
	/**
	 * 根据查询条件查询下拉框
	 * @param queryCriteria 查询条件
	 * @return 下拉框集合
	 * @throws Exception
	 */
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception;
}
