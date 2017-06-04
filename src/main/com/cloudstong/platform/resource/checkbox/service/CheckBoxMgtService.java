package com.cloudstong.platform.resource.checkbox.service;

import java.util.List;

import com.cloudstong.platform.resource.checkbox.vo.CheckBoxMgtVO;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 复选框服务接口
 */
public interface CheckBoxMgtService {

	/**
	 * 查找所有已经发布的复选框
	 * @return 复选框集合
	 * @throws Exception
	 */
	public List<CheckBoxMgtVO> queryAllCheckBoxMgtVOs() throws Exception;

	/**
	 * 根据复选框ID查找复选框
	 * @param id 复选框ID
	 * @return 复选框
	 * @throws Exception
	 */
	public List<CheckBoxMgtVO> queryCheckBoxMgtVOByID(Long id) throws Exception;

}
