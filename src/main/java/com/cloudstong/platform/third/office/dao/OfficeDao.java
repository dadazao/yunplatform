package com.cloudstong.platform.third.office.dao;

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
 * Description:字处理组件数据库接口
 */
public interface OfficeDao {
	/**
	 * Description:获得所有的字处理组件
	 * 
	 * Steps:
	 * 
	 * @return 字处理组件的集合
	 */
	public List<Office> getAllOffice();
}
