package com.cloudstong.platform.resource.imagepage.dao;

import java.util.List;

import com.cloudstong.platform.resource.imagepage.model.ImagePage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:图片页面操作数据库接口
 */
public interface ImagePageDao {
	/**
	 * Description:查询图片页面集合
	 * @param where sql语句
	 * @param args 参数值
	 * @param startRow 当前页
	 * @param rowsCount 每页记录数
	 * @return 图片页面集合
	 */
	public List select(String where, Object[] args, int startRow, int rowsCount);

	/**
	 * Description:修改图片页面信息
	 * @param imagePage 图片页面
	 */
	public void doUpdateImagePage(ImagePage imagePage);

	/**
	 * Description:根据图片页面ID查找图片页面
	 * @param id 图片页面ID
	 * @return 图片页面
	 */
	public ImagePage selectImagePageById(Long id);
}
