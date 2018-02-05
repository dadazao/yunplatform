package com.cloudstong.platform.resource.template.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.template.model.SysTemplate;
import com.cloudstong.platform.resource.template.model.Template;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:模板管理接口,包含表单模板,组合模板以及模板库的一些操作
 */
public interface TemplateService {
	/**
	 * Description:利用Id查找表单模板
	 * 
	 * Steps:
	 * 
	 * @param id 
	 * 				表单模板ID
	 * @return
	 */
	public SysTemplate queryTemplateById(Long id);
	/**
	 * Description:保存或修改表单模板
	 * 
	 * Steps:
	 * 
	 * @param SysTemplate 
	 * 				表单模板对象
	 * @return
	 */
	public Long doSaveOrUpdateTemplate(SysTemplate sysTemplate);

	/**
	 * Description:根据条件查询模板
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria 
	 * 				查询条件
	 * @return
	 */
	public List queryTemplate(QueryCriteria queryCriteria);

	/**
	 * Description:根据模板ID查询模板
	 * 
	 * Steps:
	 * 
	 * @param id 
	 * 				模板ID
	 * @return
	 */
	public Template findTemplateById(Long id);

	/**
	 * Description:从表单模板中复制模板到模板库
	 * 
	 * Steps:
	 * 
	 * @param template 表单模板对象
	 */
	public void doSaveLibrary(Template template);
	
	/**
	 * Description:从模板库获得所有模板
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	public List<Template> selectLibary();
}
