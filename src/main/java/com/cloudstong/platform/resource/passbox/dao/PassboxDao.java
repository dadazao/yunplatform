package com.cloudstong.platform.resource.passbox.dao;

import java.util.List;

import com.cloudstong.platform.resource.passbox.model.Passbox;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:操作密码框数据库接口
 */
public interface PassboxDao {

	/**
	 * Description:根据密码框ID查找密码框
	 * @param ID 密码框ID
	 * @return 密码框ID
	 */
	public Passbox findByID(Long id);

	/**
	 * Description:查找密码框集合
	 * @param sql sql语句
	 * @param array 参数值
	 * @param currentIndex 当前页
	 * @param pageSize 每页记录数
	 * @return
	 */
	public List queryForPageList(String sql, Object[] array, int currentIndex, int pageSize);

}
