package com.cloudstong.platform.resource.uploadify.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.uploadify.dao.UploadifyDao;
import com.cloudstong.platform.resource.uploadify.model.Uploadify;
import com.cloudstong.platform.resource.uploadify.service.UploadifyService;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:多文件上传组件服务接口实现类
 */
@Repository("uploadifyService")
public class UploadifyServiceImpl implements UploadifyService {

	@Resource
	private UploadifyDao uploadifyDao;
	
	public UploadifyDao getUploadifyDao() {
		return uploadifyDao;
	}

	public void setUploadifyDao(UploadifyDao uploadifyDao) {
		this.uploadifyDao = uploadifyDao;
	}

	@Override
	public List<Uploadify> findAllUploadify() throws Exception {
		return uploadifyDao.findAllUploadify();
	}

	@Override
	public Uploadify findUploadifyById(Long uploadifyId) throws Exception {
		return uploadifyDao.findUploadifyById(uploadifyId);
	}

}
