package com.cloudstong.platform.resource.codecasecade.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.codecasecade.model.CodeCaseCade;
import com.cloudstong.platform.resource.editor.model.TextEditor;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:代码级联操作数据库接口
 */
public interface CodeCaseCadeDao {

	/**
	 * 查询所有的代码级联构件
	 * @return 代码级联构件集合
	 */
	@Cacheable(value = "resourceCache", key = "findAllCodeCaseCade")
	List<CodeCaseCade> findAllCodeCaseCade();

	
	/**
	 * 根据代码级联ID查找代码级联构件
	 * @param codeCaseCadeId 代码级联ID
	 * @return 代码级联构件
	 */
	@Cacheable(value = "resourceCache", key = "'findCodeCaseCadeById:'+#codeCaseCadeId")
	public CodeCaseCade findCodeCaseCadeById(Long codeCaseCadeId);
}
