package com.cloudstong.platform.resource.codecasecade.service;

import java.util.List;

import com.cloudstong.platform.resource.codecasecade.model.CodeCaseCade;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:代码级联服务接口
 */
public interface CodeCaseCadeService {
	/**
	 * 查询所有的代码级联构件
	 * @return 代码级联构件集合
	 * @throws Exception
	 */
	public List<CodeCaseCade> findAllCodeCaseCade() throws Exception;
}
