package com.cloudstong.platform.resource.codecasecade.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.codecasecade.dao.CodeCaseCadeDao;
import com.cloudstong.platform.resource.codecasecade.model.CodeCaseCade;
import com.cloudstong.platform.resource.codecasecade.service.CodeCaseCadeService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:代码级联服务接口实现类
 */
@Repository("codeCaseCadeService")
public class CodeCaseCadeServiceImpl implements CodeCaseCadeService {

	@Resource
	private CodeCaseCadeDao codeCaseCadeDao;
	
	public CodeCaseCadeDao getCodeCaseCadeDao() {
		return codeCaseCadeDao;
	}

	public void setCodeCaseCadeDao(CodeCaseCadeDao codeCaseCadeDao) {
		this.codeCaseCadeDao = codeCaseCadeDao;
	}

	@Override
	public List<CodeCaseCade> findAllCodeCaseCade() throws Exception {
		return codeCaseCadeDao.findAllCodeCaseCade();
	}
}
