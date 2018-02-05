package com.cloudstong.platform.resource.enterinfo.dao;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.enterinfo.model.EnterpriseInfo;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:企业信息操作数据库接口
 */
public interface EnterpriseInfoDao {

	/**
	 * 查找平台默认的企业信息
	 * @return 企业信息
	 */
	@Cacheable(value="themeCache")
	EnterpriseInfo findDefaultInfo();

}
