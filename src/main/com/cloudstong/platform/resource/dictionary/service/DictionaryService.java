package com.cloudstong.platform.resource.dictionary.service;

import java.util.List;
import java.util.Map;

import com.cloudstong.platform.resource.dictionary.model.Dictionary;

/**
 * @author michael Created on 2012-11-21
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:代码服务接口
 */
public interface DictionaryService {

	/**
	 * Description:根据代码ID查找代码
	 * 
	 * @param id
	 *            代码ID
	 * @return 代码
	 */
	public Dictionary queryById(Long id);

	/**
	 * Description:根据代码级别查询代码
	 * 
	 * @param level
	 *            代码级别
	 * @return 代码集合
	 */
	public List<Dictionary> queryDictionarysByLevel(int level);

	/**
	 * Description:根据父代码ID查询代码
	 * 
	 * @param parentId
	 *            父代码ID
	 * @param user
	 *            用户信息
	 * @return 代码集合
	 */
	public List<Dictionary> queryByParent(Long parentId);
	
	/**
	 * Description:根据父代码编码查询子代码集合
	 * 
	 * @param parentId
	 *            父代码编码
	 * @param user
	 *            用户信息
	 * @return 代码集合
	 */
	public List<Dictionary> queryByParentCode(String dicCode);

	/**
	 * Description:根据父代码ID查询代码
	 * 
	 * @param parentId
	 *            父代码ID
	 * @return 代码集合
	 */
	public List<Dictionary> queryCaseCadeByParent(Long parentId);

	/**
	 * Description:更新代码的顺序
	 * 
	 * @param dictionary
	 *            代码
	 */
	public void doUpdateOrder(Dictionary dictionary);

	/**
	 * Description:查询某个parentId下的所有字典，返回Map形式,Key为字典值，Value为字典名
	 * 
	 * Steps:
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Map<String, String>> queryKeyValueMapByParentId(Long parentId);

	public void doUpdateCode(Dictionary dictionary);

}
