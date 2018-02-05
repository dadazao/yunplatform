package com.cloudstong.platform.resource.combox.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.combox.model.Combox;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:下拉框操作数据库接口
 */
public interface ComboxDao {

	/**
	 * 根据下拉框ID查找 下拉框
	 * @param ID 下拉框ID
	 * @return 下拉框
	 */
	@Cacheable(value="resourceCache",key="'Combox_findByID:'+#ID")
	public Combox findByID(Long id);

	/**
	 * 查询下拉框
	 * @param string sql语句
	 * @param array 参数值
	 * @param currentIndex 当前页
	 * @param pageSize 每页记录数
	 * @return
	 */
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String string, Object[] array, int currentIndex, int pageSize);

}
