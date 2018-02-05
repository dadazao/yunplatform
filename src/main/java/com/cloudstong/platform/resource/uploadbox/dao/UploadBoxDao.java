package com.cloudstong.platform.resource.uploadbox.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.uploadbox.model.UploadBox;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:上传文件框操作数据库接口
 */
public interface UploadBoxDao {

	/**
	 * Description:根据上传文件框ID查找上传文件框
	 * @param ID 上传文件框ID
	 * @return 上传文件框
	 */
	@Cacheable(value = "resourceCache", key = "'UploadBox_findByID:'+#ID")
	public UploadBox findByID(Long id);

	/**
	 * Description:查询上传文件框
	 * @param sql sql语句
	 * @param array 参数值
	 * @param currentIndex 当前页
	 * @param pageSize 每页记录数
	 * @return 上传文件框集合
	 */
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String where, Object[] array, int currentIndex, int pageSize);

}
