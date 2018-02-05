package com.cloudstong.platform.resource.useinfo.dao;

import java.util.List;

import com.cloudstong.platform.resource.useinfo.model.ChartComp;
import com.cloudstong.platform.resource.useinfo.model.UseInfo;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:使用信息操作数据库接口
 */
public interface UseInfoDao {

	/**
	 * Description:插入使用信息到数据库
	 * @param useInfo 使用信息
	 */
	void insert(UseInfo useInfo);

	/**
	 * Description:修改使用信息
	 * @param useInfo 使用信息
	 */
	void update(UseInfo useInfo);
	
	/**
	 * 统计组件的使用次数
	 * @param number 
	 * @return 组件的使用次数
	 */
	public List<ChartComp> stat(Integer number);

	/**
	 * Description:根据类型和关联信息ID删除使用信息
	 * @param relId 关联信息ID
	 * @param type 类型
	 */
	void deleteUseInfo(Long relId,int type);
}
