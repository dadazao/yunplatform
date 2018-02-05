package com.cloudstong.platform.resource.enterinfo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.enterinfo.dao.EnterpriseInfoDao;
import com.cloudstong.platform.resource.enterinfo.model.EnterpriseInfo;
import com.cloudstong.platform.resource.enterinfo.service.EnterpriseInfoService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:企业信息服务接口实现类
 */
@Repository("enterpriseInfoService")
public class EnterpriseInfoServiceImpl implements EnterpriseInfoService {

	@Resource
	private EnterpriseInfoDao enterpriseInfoDao;
	
	public EnterpriseInfo findDefaultInfo() {
		return enterpriseInfoDao.findDefaultInfo();
	}

}
