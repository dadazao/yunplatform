package com.cloudstong.platform.resource.textarea.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.textarea.model.Textarea;


/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本域服务接口
 */
public interface TextareaService {
	/**
	 * Description:根据文本域ID查找文本域
	 * @param ID 文本域ID
	 * @return 文本域
	 * @throws Exception
	 */
	public Textarea findByID(Long id) throws Exception;
	
	/**
	 * Description:根据查询条件查询文本域
	 * @param queryCriteria 查询条件
	 * @return 文本域集合
	 * @throws Exception
	 */
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception;
}
