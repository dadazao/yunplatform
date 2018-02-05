package com.cloudstong.platform.resource.textbox.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.textbox.model.TextBox;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本框服务接口
 */
public interface TextBoxService {

	/**
	 * Description:保存或者修改文本框
	 * @param textBox 文本框
	 * @throws Exception
	 */
	public void doSaveOrUpdate(TextBox textBox) throws Exception;

	/**
	 * Description:根据文本框ID查找文本框
	 * @param ID 文本框ID
	 * @return 文本框
	 * @throws Exception
	 */
	public TextBox findByID(Long id) throws Exception;

	/**
	 * Description:根据文本框ID删除文本框
	 * @param ID 文本框
	 * @return 成功标识
	 * @throws Exception
	 */
	public void doDelete(Long id) throws Exception;

	/**
	 * Description:根据查询条件查询文本框
	 * @param queryCriteria 查询条件
	 * @return 文本框集合
	 * @throws Exception
	 */
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception;

	/**
	 * Description:根据查询条件统计文本框
	 * @param queryCriteria 查询条件
	 * @return 文本框
	 * @throws Exception
	 */
	public int getTotalCount(QueryCriteria queryCriteria) throws Exception;
}
