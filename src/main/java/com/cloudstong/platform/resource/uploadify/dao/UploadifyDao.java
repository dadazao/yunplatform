package com.cloudstong.platform.resource.uploadify.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.uploadify.model.Uploadify;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:多文件上传组件操作数据库接口
 */
public interface UploadifyDao {
	/**
	 * Description:查询所有的多文件上传组件
	 * @return 多文件上传组件集合
	 */
	@Cacheable(value = "resourceCache", key = "findAllUploadify")
	List<Uploadify> findAllUploadify();

	/**
	 * Description:根据ID查找 多文件上传组件
	 * @param uploadifyId 多文件上传组件ID
	 * @return 多文件上传组件
	 */
	@Cacheable(value = "resourceCache", key = "'findUploadifyById:'+#uploadifyId")
	Uploadify findUploadifyById(Long uploadifyId);
}
