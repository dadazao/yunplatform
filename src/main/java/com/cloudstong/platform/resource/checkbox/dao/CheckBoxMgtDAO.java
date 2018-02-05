package com.cloudstong.platform.resource.checkbox.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.checkbox.vo.CheckBoxMgtVO;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 复选框操作数据库接口
 */
public interface CheckBoxMgtDAO {

	/**
	 * 查找所有已经发布的复选框
	 * @return 复选框集合
	 * @throws Exception
	 */
	@Cacheable(value = "resourceCache", key = "'CheckBoxMgt_selectAllCheckBoxVOs")
	public List<CheckBoxMgtVO> selectAllCheckBoxVOs() throws Exception;

	/**
	 * 根据复选框ID查找复选框
	 * @param id 复选框ID
	 * @return 复选框
	 * @throws Exception
	 */
	@Cacheable(value = "resourceCache", key = "'CheckBoxMgt_selectById:'+#id")
	public List<CheckBoxMgtVO> selectById(Long id) throws Exception;

}
