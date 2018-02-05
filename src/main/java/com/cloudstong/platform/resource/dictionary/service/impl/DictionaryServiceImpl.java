package com.cloudstong.platform.resource.dictionary.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.dictionary.dao.DictionaryDao;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-21
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:代码服务接口实现类
 */
@Repository("DictionaryService")
public class DictionaryServiceImpl implements DictionaryService {

	@Resource
	private DictionaryDao dictionaryDao;

	public DictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}

	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

	@Override
	public Dictionary queryById(Long id) {
		return this.dictionaryDao.selectById(id);
	}

	@Override
	public List<Dictionary> queryByParent(Long parentId) {
		return this.dictionaryDao.selectByParent(parentId, "asc");
	}
	
	public List<Dictionary> queryByParentCode(String parentCode) {
		return this.dictionaryDao.selectByParentCode(parentCode, "asc");
	}

	@Override
	public List<Dictionary> queryCaseCadeByParent(Long parentId) {
		return this.dictionaryDao.selectCaseCadeByParent(parentId);
	}

	@Override
	public List<Dictionary> queryDictionarysByLevel(int level) {
		return dictionaryDao.selectByLevel(level);
	}

	@Override
	public void doUpdateOrder(Dictionary dictionary) {
		this.dictionaryDao.updateOrder(dictionary);
	}

	@Override
	public List<Map<String, String>> queryKeyValueMapByParentId(Long parentId) {
		return dictionaryDao.queryKeyValueMapByParentId(parentId);
	}

	@Override
	public void doUpdateCode(Dictionary dictionary) {
		dictionaryDao.doUpdateCode(dictionary);
	}
	
	

}
