package com.cloudstong.platform.resource.uploadbox.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.uploadbox.dao.UploadBoxDao;
import com.cloudstong.platform.resource.uploadbox.model.UploadBox;
import com.cloudstong.platform.resource.uploadbox.service.UploadBoxService;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:上传文件框服务接口实现类
 */
@Repository("uploadBoxService")
public class UploadBoxServiceImpl implements UploadBoxService {
	
	@Resource
	private UploadBoxDao uploadBoxDao;

	public UploadBoxDao getUploadBoxDao() {
		return uploadBoxDao;
	}

	public void setUploadBoxDao(UploadBoxDao uploadBoxDao) {
		this.uploadBoxDao = uploadBoxDao;
	}

	@Override
	public UploadBox findByID(Long id) throws Exception {
		return uploadBoxDao.findByID(id);
	}

	@Override
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception {
		StringBuffer where = new StringBuffer();
		List queryForPageList = null;
		List paramList = new ArrayList();
		Map<String, Object> paramMap = queryCriteria.getQueryCondition();
		for (Iterator it = paramMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			where.append(" AND  " + entry.getKey() + " = ?");
			paramList.add(entry.getValue());
		}
		// where.append(" ORDER BY comm_createDate");
		try {
			queryForPageList = uploadBoxDao.queryForPageList(where.toString(),
					paramList.toArray(), queryCriteria.getCurrentIndex(),
					queryCriteria.getPageSize());
		} catch (Exception e) {
			throw e;
		}
		return queryForPageList;
	}
	
}
