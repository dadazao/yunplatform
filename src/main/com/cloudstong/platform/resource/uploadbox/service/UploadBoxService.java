package com.cloudstong.platform.resource.uploadbox.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.uploadbox.model.UploadBox;


/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:上传文件框服务接口
 */
public interface UploadBoxService {
	/**
	 * Description:根据上传文件框ID查找上传文件框
	 * @param ID 上传文件框ID
	 * @return 上传文件框
	 * @throws Exception
	 */
	public UploadBox findByID(Long id) throws Exception;
	
	/**
	 * Description:根据查询条件查询上传文件框
	 * @param queryCriteria 查询条件
	 * @return 上传文件框集合
	 * @throws Exception
	 */
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception;
}
