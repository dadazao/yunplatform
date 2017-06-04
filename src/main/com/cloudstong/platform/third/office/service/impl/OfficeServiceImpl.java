package com.cloudstong.platform.third.office.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.third.office.dao.OfficeDao;
import com.cloudstong.platform.third.office.model.Office;
import com.cloudstong.platform.third.office.service.OfficeService;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:字处理组件接口的实现
 */
@Repository("officeService")
public class OfficeServiceImpl implements OfficeService {

	@Resource
	private OfficeDao officeDao;

	@Override
	public List<Office> findAllOffice() throws Exception {
		return officeDao.getAllOffice();
	}	
}
