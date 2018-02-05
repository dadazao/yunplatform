package com.cloudstong.platform.resource.imagepage.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.imagepage.dao.ImagePageDao;
import com.cloudstong.platform.resource.imagepage.model.ImagePage;
import com.cloudstong.platform.resource.imagepage.service.ImagePageService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:图片页面服务接口实现类
 */
@Repository("imagePageService")
public class ImagePageServiceImpl implements ImagePageService {

	@Resource
	private ImagePageDao imagePageDao;

	public ImagePageDao getImagePageDao() {
		return imagePageDao;
	}

	public void setImagePageDao(ImagePageDao imagePageDao) {
		this.imagePageDao = imagePageDao;
	}

	@Override
	public void doUpdateImagePage(ImagePage imagePage) {
		imagePageDao.doUpdateImagePage(imagePage);
	}

	@Override
	public ImagePage findImagePageById(Long id) {
		return imagePageDao.selectImagePageById(id);
	}

	@Override
	public List<ImagePage> queryImagePage(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select * from sys_imgpage where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			sql.append(" and " + entry.getKey() + " like ? ");
			param.add("%" + ((String)entry.getValue()).trim() + "%");
		}
		sql.append("order by id desc");
		return this.imagePageDao.select(sql.toString(), param.toArray(),
				qc.getCurrentIndex(), qc.getPageSize());
	}

}
