package com.cloudstong.platform.resource.imagepage.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.imagepage.model.ImagePage;

/**
 * @author michael
 * Created on 2012-11-15
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:图片页面服务接口
 */
public interface ImagePageService {
	/**
	 * Description:根据查询条件查询图片页面集合
	 * @param qc 查询条件
	 * @return 图片页面集合
	 */
	public List<ImagePage> queryImagePage(QueryCriteria qc);

	/**
	 * Description: 更新图片页面
	 * @param imagePage 图片页面
	 */
	public void doUpdateImagePage(ImagePage imagePage);

	/**
	 * Description:根据图片页面ID查找图片页面
	 * @param id 图片页面ID
	 * @return 图片页面
	 */
	public ImagePage findImagePageById(Long id);
}
