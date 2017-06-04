package com.cloudstong.platform.resource.textarea.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.textarea.model.Textarea;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本域操作数据库接口
 */
public interface TextareaDao {

	/**
	 * Description:根据文本域ID查找文本域
	 * @param ID 文本域ID
	 * @return 文本域
	 */
	@Cacheable(value="resourceCache",key="'Textarea_findByID:'+#id")
	public Textarea findByID(Long id);

	/**
	 * Description:查询文本域
	 * @param sql sql语句
	 * @param array 参数值
	 * @param currentIndex 当前页
	 * @param pageSize 每页记录数
	 * @return 文本域集合
	 */
	@Cacheable(value="resourceCache")
	public List queryForPageList(String sql, Object[] array, int currentIndex, int pageSize);

}
