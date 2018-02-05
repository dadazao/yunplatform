package com.cloudstong.platform.resource.useinfo.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

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
 * Description:使用信息服务接口
 */
public interface UseInfoService {
	/**
	 * Description:保存使用信息
	 * @param useInfo 使用信息
	 */
	@CacheEvict(value = "resourceCache", allEntries = true, beforeInvocation = true)
	public void doSaveUseInfo(UseInfo useInfo);
	
	/**
	 * Description:更新使用信息
	 * @param useInfo 使用信息
	 */
	@CacheEvict(value = "resourceCache", allEntries = true, beforeInvocation = true)
	public void doUpdateUseInfo(UseInfo useInfo);
	
	/**
	 * 统计组件的使用次数
	 * @param number 
	 * @return 组件的使用次数
	 */
	@Cacheable(value = "resourceCache")
	public List<ChartComp> stat(Integer number);

	/**
	 * Description:根据类型和关联信息ID删除使用信息
	 * @param relId 关联信息ID
	 * @param type 类型
	 */
	@CacheEvict(value = "resourceCache", allEntries = true, beforeInvocation = true)
	public void doDeleteUseInfo(Long relId,int type);

}
