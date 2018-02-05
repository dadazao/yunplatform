package com.cloudstong.platform.resource.checkbox.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.checkbox.dao.CheckBoxMgtDAO;
import com.cloudstong.platform.resource.checkbox.service.CheckBoxMgtService;
import com.cloudstong.platform.resource.checkbox.vo.CheckBoxMgtVO;
import com.cloudstong.platform.resource.radio.service.RadioMgtService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:复选框服务接口实现类
 */
@Repository("checkBoxMgtService")
public class CheckBoxMgtServiceImpl implements CheckBoxMgtService {

	@Resource
	private CheckBoxMgtDAO checkBoxMgtDAO;

	public CheckBoxMgtDAO getCheckBoxMgtDAO() {
		return checkBoxMgtDAO;
	}

	public void setCheckBoxMgtDAO(CheckBoxMgtDAO checkBoxMgtDAO) {
		this.checkBoxMgtDAO = checkBoxMgtDAO;
	}

	@Override
	public List<CheckBoxMgtVO> queryAllCheckBoxMgtVOs() throws Exception {
		return this.checkBoxMgtDAO.selectAllCheckBoxVOs();
	}

	@Override
	public List<CheckBoxMgtVO> queryCheckBoxMgtVOByID(Long id) throws Exception {
		return this.checkBoxMgtDAO.selectById(id);
	}

}
