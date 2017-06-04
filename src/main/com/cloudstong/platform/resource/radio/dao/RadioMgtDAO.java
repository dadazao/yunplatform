package com.cloudstong.platform.resource.radio.dao;

import java.util.List;

import com.cloudstong.platform.resource.radio.vo.RadioMgtVO;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:单选框操作数据库接口
 */
public interface RadioMgtDAO {

	/**
	 * 查询所有的单选框
	 * @return 单选框集合
	 * @throws Exception
	 */
	public List<RadioMgtVO> selectAll() throws Exception;

	/**
	 * 根据单选框ID查找单选框
	 * @param id 单选框ID
	 * @return 单选框
	 * @throws Exception
	 */
	public List<RadioMgtVO> selectById(Long id) throws Exception;

}
