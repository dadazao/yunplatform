package com.cloudstong.platform.resource.dictionary.dao;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.dictionary.model.Dictionary;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:代码操作数据库接口
 */
public interface DictionaryDao {
	/**
	 * Description:根据代码ID查找代码
	 * @param id 代码ID
	 * @return 代码
	 */
	@Cacheable(value="resourceCache",key="'Dictionary_selectById:'+#id")
	public Dictionary selectById(Long id);
	
	public Dictionary selectByCode(String code);

	/**
	 * Description:根据父代码ID查询代码
	 * @param parentId 父代码ID
	 * @param order 排序规则
	 * @param user 用户信息
	 * @return 代码集合
	 */
	@Cacheable(value="resourceCache",key="'selectByParent:'+#parentId+#order")
	public List<Dictionary> selectByParent(Long parentId, String order);
	
	public List<Dictionary> selectByParentCode(String parentCode, String order);

	/**
	 * Description:根据父代码ID查询代码
	 * @param parentId 父代码ID
	 * @return 代码集合
	 */
	public List<Dictionary> selectCaseCadeByParent(Long parentId);

	/**
	 * Description:根据代码级别查询代码
	 * @param level 代码级别
	 * @return 代码集合
	 */
	public List<Dictionary> selectByLevel(int level);

	/**
	 * Description:更新代码的顺序
	 * @param dictionary 代码
	 */
	public void updateOrder(Dictionary dictionary);

	/**
	 * Description:根据父代码ID查询代码
	 * @param parentId 父代码ID
	 * @param canSelectItem 可选代码项
	 * @param order 排序规则
	 * @param user 用户信息
	 * @return 代码集合
	 */
	@Cacheable(value="resourceCache",key="'selectByParent:'+#parentId+#canSelectItem.hashCode()+#order")
	public List<Dictionary> selectByParent(Long parentId, String canSelectItem, String order);
	
	/**
	 * 
	 * Description:查询parentId下的所有字典，返回键值对列表，每个键值对中的key为字典值，value为字典名
	 * @param parentId 父代码ID
	 * @return
	 */
	@Cacheable(value="resourceCache",key="'queryKeyValueMapByParentId:'+#parentId")
	public List<Map<String, String>> queryKeyValueMapByParentId(Long parentId);

	public void doUpdateCode(Dictionary dictionary);
}
