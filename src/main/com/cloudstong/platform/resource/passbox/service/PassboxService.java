package com.cloudstong.platform.resource.passbox.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.passbox.model.Passbox;


/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:密码框服务接口
 */
public interface PassboxService {
	/**
	 * Description:通过密码框ID查找密码框
	 * @param ID 密码框ID
	 * @return 密码框
	 * @throws Exception
	 */
	public Passbox findByID(Long id) throws Exception;
	/**
	 * Description:根据查询条件查找密码框集合
	 * @param queryCriteria 查询条件
	 * @return 密码框集合
	 * @throws Exception
	 */
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception;
}
