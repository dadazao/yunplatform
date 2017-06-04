package com.cloudstong.platform.resource.radio.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.radio.dao.RadioMgtDAO;
import com.cloudstong.platform.resource.radio.service.RadioMgtService;
import com.cloudstong.platform.resource.radio.vo.RadioMgtVO;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:单选框服务接口实现类
 */
@Repository("radioMgtService")
public class RadioMgtServiceImpl implements RadioMgtService {

	@Resource
	private RadioMgtDAO radioMgtDAO;

	public RadioMgtDAO getRadioMgtDAO() {
		return radioMgtDAO;
	}

	public void setRadioMgtDAO(RadioMgtDAO radioMgtDAO) {
		this.radioMgtDAO = radioMgtDAO;
	}

	@Override
	public List<RadioMgtVO> queryAllRadioMgtVOs() throws Exception {
		return this.radioMgtDAO.selectAll();
	}

	@Override
	public List<RadioMgtVO> queryRadioMgtVOById(Long id) throws Exception {
		return this.radioMgtDAO.selectById(id);
	}

}
