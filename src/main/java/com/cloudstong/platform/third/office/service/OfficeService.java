package com.cloudstong.platform.third.office.service;

import java.util.List;

import com.cloudstong.platform.third.office.model.Office;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:字处理组件的接口
 */
public interface OfficeService {
	
	/**
	 * Description:获得所有的字处理组件
	 * 
	 * Steps:
	 * 
	 * @return 字处理组件的集合
	 */
	public List<Office> findAllOffice() throws Exception;
}
