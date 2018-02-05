package com.cloudstong.platform.resource.uploadify.service;

import java.util.List;

import com.cloudstong.platform.resource.uploadify.model.Uploadify;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:多文件上传组件服务接口
 */
public interface UploadifyService {
	/**
	 * Description:查询所有的多文件上传组件
	 * @return 多文件上传组件集合
	 * @throws Exception
	 */
	public List<Uploadify> findAllUploadify() throws Exception;
	
	/**
	 * Description:根据ID查找 多文件上传组件
	 * @param uploadifyId 多文件上传组件ID
	 * @return 多文件上传组件
	 * @throws Exception
	 */
	public Uploadify findUploadifyById(Long uploadifyId) throws Exception;
}
