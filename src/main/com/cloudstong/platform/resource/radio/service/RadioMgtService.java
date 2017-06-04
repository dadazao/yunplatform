package com.cloudstong.platform.resource.radio.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.radio.vo.RadioMgtVO;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:单选框服务接口
 */
public interface RadioMgtService {

	/**
	 * 查询所有的单选框
	 * @return 单选框集合
	 * @throws Exception
	 */
	public List<RadioMgtVO> queryAllRadioMgtVOs() throws Exception;

	/**
	 * 根据单选框ID查找单选框
	 * @param id 单选框ID
	 * @return 单选框
	 * @throws Exception
	 */
	public List<RadioMgtVO> queryRadioMgtVOById(Long id) throws Exception;

}
