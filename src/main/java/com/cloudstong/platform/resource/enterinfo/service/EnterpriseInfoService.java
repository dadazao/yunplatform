package com.cloudstong.platform.resource.enterinfo.service;

import com.cloudstong.platform.resource.enterinfo.model.EnterpriseInfo;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:企业信息服务接口
 */
public interface EnterpriseInfoService {

	/**
	 * 查找平台默认的企业信息
	 * @return 企业信息
	 */
	EnterpriseInfo findDefaultInfo();

}
