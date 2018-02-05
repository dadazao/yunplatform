package com.cloudstong.platform.resource.template.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.util.CacheUtil;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.resource.template.model.SysTemplate;
import com.cloudstong.platform.resource.template.model.Template;
import com.cloudstong.platform.resource.template.service.TemplateService;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.IUserService;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:主要负责表单模板的一些操作
 */
/**
 * @author liutao
 * Created on 2012-12-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
@SuppressWarnings("serial")
public class TemplateAction extends CompexDomainAction {
	/**
	 * 表单模板管理的服务,<code>templateService</code>对象是TemplateService接口的一个实例.
	 */
	@Resource
	private TemplateService templateService;
	/**
	 * 用户管理的服务,<code>userService</code>对象是IUserService接口的一个实例.
	 */
	@Resource
	private IUserService userService;
	/**
	 * 表单模板的ID, <code>templateId</code> String类的一个实例.
	 */
	private String templateId;
	/**
	 * 表单模板, <code>sysTemplate</code> SysTemplate类的一个实例.
	 */
	private SysTemplate sysTemplate;
	
	/**
	 * 通用模板的模板集合
	 */
	private List<Template> templateLibaryList;
	
	private List<Dictionary> templateTypeList;

	/**
	 * Description:返回表单模板列表所有数据
	 * 
	 * Steps:
	 * 
	 * @return 页面标识
	 */
	public String listBase() {
		super.list();
		return "baselist";
	}

	/**
	 * Description:返回添加表单列表页面
	 * 
	 * Steps:
	 * 
	 * @return 页面标识
	 */
	public String addBase() {
		try {
			super.add();
		} catch (Exception e) {
			e.printStackTrace();
		}
		templateLibaryList = templateService.selectLibary();
		return "baseadd";
	}
	
	/**
	 * Description:返回添加表单列表页面
	 * 
	 * Steps:
	 * 
	 * @return 页面标识
	 */
	public String addBaseByForm() {
		try {
			super.add();
		} catch (Exception e) {
			e.printStackTrace();
		}
		templateLibaryList = templateService.selectLibary();
		String tabop = getRequest().getParameter("tabop");
		getRequest().setAttribute("tabop", tabop);
		return "baseaddbyform";
	}

	/**
	 * Description:保存表单列表
	 * 
	 * Steps:
	 * 
	 * @return none
	 * @throws Exception
	 */
	public String saveBase() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		sysTemplate.setCreateBy(user.getId());
		sysTemplate.setUpdateBy(user.getId());
		try {
			sysTemplate.setTblContent(templateContentHtml(sysTemplate));
			Long id = templateService.doSaveOrUpdateTemplate(sysTemplate);
			
			CacheUtil.clearSCache("domainCache");
			
			printJSON("success", false, id.toString());
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
			
		}
		return NONE;
	}

	/**
	 * Description:将模板由设计模式转换为浏览模式
	 * 
	 * Steps:
	 * 
	 * @param sysTemplate 需要转换的模板对象
	 * @return
	 */
	public String templateContentHtml(SysTemplate sysTemplate){
		StringBuilder sbdContent = new StringBuilder();
		String tblContent = sysTemplate.getTblContent();
		String tmp = "";
		boolean flag = false;
		for (int i = 0; i < tblContent.length(); i++) {
			if(String.valueOf(tblContent.charAt(i)).equals("】")){
				if(tmp.split(",")[0].equals("数据字段")){
					sbdContent.append("<div id='compexDomainTabEdit_label_t");
					sbdContent.append(tmp.split(",")[1]);
					sbdContent.append("'></div>");
				}
				if(tmp.split(",")[0].equals("数据值")){
					sbdContent.append("<div id='compexDomainTabEdit_value_t");
					sbdContent.append(tmp.split(",")[1]);
					sbdContent.append("'></div>");
				}
				tmp = "";
				flag = false;
			}else if(flag){
				tmp += String.valueOf(tblContent.charAt(i));
			}else if(String.valueOf(tblContent.charAt(i)).equals("【")){
				flag = true;
			}else{
				sbdContent.append(tblContent.charAt(i));
			}
		}
		return sbdContent.toString();
	}
	
	/**
	 * Description:将模板由浏览模式转换为设计模式
	 * 
	 * Steps:
	 * 
	 * @param sysTemplate 需要转换的模板对象
	 * @return
	 */
	public String templateContentChar(SysTemplate sysTemplate){
		String content = sysTemplate.getTblContent();
		String[] strs = content.split("<div id=\"compexDomainTabEdit_label_t");
		for (int i = 0; i < strs.length; i++) {
			content = content.replace("<div id=\"compexDomainTabEdit_label_t"+(i+1)+"\"></div>", "【数据字段,"+(i+1)+"】");
			content = content.replace("<div id='compexDomainTabEdit_label_t"+(i+1)+"'></div>", "【数据字段,"+(i+1)+"】");
			content = content.replace("<div id=\"compexDomainTabEdit_value_t"+(i+1)+"\"></div>", "【数据值,"+(i+1)+"】");
			content = content.replace("<div id='compexDomainTabEdit_value_t"+(i+1)+"'></div>", "【数据值,"+(i+1)+"】");
		}
		strs = content.split("<div id='compexDomainTabEdit_label_t");
		for (int i = 0; i < strs.length; i++) {
			content = content.replace("<div id=\"compexDomainTabEdit_label_t"+(i+1)+"\"></div>", "【数据字段,"+(i+1)+"】");
			content = content.replace("<div id='compexDomainTabEdit_label_t"+(i+1)+"'></div>", "【数据字段,"+(i+1)+"】");
			content = content.replace("<div id=\"compexDomainTabEdit_value_t"+(i+1)+"\"></div>", "【数据值,"+(i+1)+"】");
			content = content.replace("<div id='compexDomainTabEdit_value_t"+(i+1)+"'></div>", "【数据值,"+(i+1)+"】");
		}
		return content;
	}
	
	
	// 老版本的保存方法
	//public String saveBase() throws Exception {
	//	super.save();
	//	return NONE;
	//}
	
	/**
	 * Description:返回表单模板详细页面
	 * 
	 * Steps:
	 * 
	 * @return 页面标识
	 * @throws Exception
	 */
	public String viewBase() throws Exception {
		super.view();
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		sysTemplate = templateService.queryTemplateById(Long.valueOf(params.split(":")[1].split(";")[0]));
		if(sysTemplate.getCreateBy() == null || "-1".equals(sysTemplate.getCreateBy())){
			sysTemplate.setCreateUser("");
		}else{
			user = (SysUser)userService.getUserResource("id", String.valueOf(sysTemplate.getCreateBy()), 0);
			sysTemplate.setCreateUser(user.getFullname());
		}
		
		if(sysTemplate.getUpdateBy() == null || "-1".equals(sysTemplate.getUpdateBy()) ){
			sysTemplate.setUpdateUser("");
		}else{
			user = (SysUser)userService.getUserResource("id", String.valueOf(sysTemplate.getUpdateBy()), 0);
			sysTemplate.setUpdateUser(user.getFullname());
		}
		sysTemplate.setTblContent(templateContentChar(sysTemplate));
		templateLibaryList = templateService.selectLibary();
		
		templateTypeList = dictionaryService.queryByParent(1014150558L);
		return "baseview";
	}

	/**
	 * Description:返回表单模板编辑页面
	 * 
	 * Steps:
	 * 
	 * @return 页面标识
	 * @throws Exception
	 */
	public String editBase() throws Exception {
		super.edit();
		sysTemplate = templateService.queryTemplateById(Long.valueOf(params.split(":")[1].split(";")[0]));
		sysTemplate.setTblContent(templateContentChar(sysTemplate));
		templateLibaryList = templateService.selectLibary();
		return "baseedit";
	}

	/**
	 * Description:将表单模板的数据复制到模板库
	 * 
	 * Steps:
	 * 
	 * @return none
	 * @throws IOException
	 */
	public String copyBase() throws IOException {
		String _sId = "";
		if (params != null) {
			_sId = params.split(":")[1].toString();
		}
		Template template1 = new Template();
		template1 = templateService.findTemplateById(Long.valueOf(_sId));
		templateService.doSaveLibrary(template1);
		printJSON("success");
		return NONE;
	}
	
	/**
	 * Description:删除表单模板
	 * 
	 * Steps:
	 * 
	 * @return
	 * @throws IOException
	 */
	public String deleteBase() throws IOException {
			return super.delete();
	}
	
	/**
	 * Description:删除表单模板
	 * 
	 * Steps:
	 * 
	 * @return
	 * @throws IOException
	 */
	public String logicDeleteBase() throws IOException {
			return super.logicDelete();
	}

	/**
	 * Description:获得通用模板所选择的模板库中数据
	 * 
	 * Steps:
	 * 1.得到所有模板库数据
	 * 2.根据所选择的通用模板筛选模板库的返回数据
	 * 
	 * @return none
	 * @throws Exception
	 */
	public String content() throws Exception{
		Template template1 = new Template();
		List<Template> _lstResult = templateService.selectLibary();
		for (Template template : _lstResult) {
			if(templateId.equals(template.getId())){
				template1 = template;
				break;
			}
		}
		if(template1.getContent() != null){
			SysTemplate sysTemplate = new SysTemplate();
			sysTemplate.setTblContent(template1.getContent());
			template1.setContent(templateContentChar(sysTemplate));
		}else{
			template1.setContent("");
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, template1);
		return NONE;
	}
	
	/**
	 * Description:返回组合模板列表数据
	 * 
	 * Steps:
	 * 
	 * @return 页面标识
	 * @throws Exception
	 */
	public String listComb() {
		super.list();
		return "comblist";
	}

	/**
	 * Description:返回组合模板编辑页面
	 * 
	 * Steps:
	 * 
	 * @return 页面标识
	 * @throws Exception
	 */
	public String addComb() throws Exception {
		super.add();
		return "combadd";
	}

	/**
	 * Description:保存组合模板
	 * 
	 * Steps:
	 * 
	 * @return none
	 * @throws Exception
	 */
	public String saveComb() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		sysTemplate.setCreateBy(user.getId());
		sysTemplate.setUpdateBy(user.getId());
		sysTemplate.setTblContent(combContentHtml(sysTemplate));
		Long id = templateService.doSaveOrUpdateTemplate(sysTemplate);
		CacheUtil.clearSCache("domainCache");
		
		printJSON("success", false, id.toString());
		return NONE;
	}

	/**
	 * Description:返回组合模板查看页面
	 * 
	 * Steps:
	 * 
	 * @return 页面标识
	 * @throws Exception
	 */
	public String viewComb() throws Exception {
		super.view();
		return "combview";
	}

	/**
	 * Description:将组合模板由设计模式转换为浏览模式
	 * 
	 * Steps:
	 * 
	 * @param sysTemplate 需要转换的模板对象
	 * @return
	 */
	public String combContentHtml(SysTemplate sysTemplate){
		String content = sysTemplate.getTblContent();
		String[] formLength = content.split("表单");
		String[] listLength = content.split("列表");
		for (int i=0; i<formLength.length; i++) {
			content = content.replace("[表单"+(i+1)+"]", "<div id='partitionForm"+(i+1)+"'>&nbsp;</div>");
		}
		for (int i=0; i<listLength.length; i++) {
			content = content.replace("[列表"+(i+1)+"]", "<div id='partitionList"+(i+1)+"'>&nbsp;</div>");
		}
		return content;
	}
	
	/**
	 * Description:将模板由浏览模式转换为设计模式
	 * 
	 * Steps:
	 * 
	 * @param sysTemplate 需要转换的模板对象
	 * @return
	 */
	public String combContentChar(SysTemplate sysTemplate){
		String content = sysTemplate.getTblContent();
		for (int i = 0; i < 30; i++) {
			content = content.replace("<div id=\"partitionForm"+i+"\">&nbsp;</div>", "[表单"+i+"]");
			content = content.replace("<div id='partitionForm"+i+"'>&nbsp;</div>", "[表单"+i+"]");
			content = content.replace("<div id=\"partitionList"+i+"\">&nbsp;</div>", "[列表"+i+"]");
			content = content.replace("<div id='partitionList"+i+"'>&nbsp;</div>", "[列表"+i+"]");
		}
		return content;
	}
	
	
	/**
	 * Description:返回表单模板编辑页面
	 * 
	 * Steps:
	 * 
	 * @return 页面标识
	 * @throws Exception
	 */
	public String editComb() throws Exception {
		sysTemplate = templateService.queryTemplateById(Long.valueOf(params.split(":")[1].split(";")[0]));
		sysTemplate.setTblContent(combContentChar(sysTemplate));
		return "combedit";
	}

	/**
	 * Description:删除组合模板
	 * 
	 * Steps:
	 * 
	 * @return 页面标识
	 * @throws Exception
	 */
	public String deleteComb() throws IOException {
		return super.delete();
	}
	
	/**
	 * Description:删除组合模板
	 * 
	 * Steps:
	 * 
	 * @return 页面标识
	 * @throws Exception
	 */
	public String logicDeleteComb() throws IOException {
		return super.logicDelete();
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public SysTemplate getSysTemplate() {
		return sysTemplate;
	}

	public void setSysTemplate(SysTemplate sysTemplate) {
		this.sysTemplate = sysTemplate;
	}

	public List<Template> getTemplateLibaryList() {
		return templateLibaryList;
	}

	public void setTemplateLibaryList(List<Template> templateLibaryList) {
		this.templateLibaryList = templateLibaryList;
	}

	public List<Dictionary> getTemplateTypeList() {
		return templateTypeList;
	}

	public void setTemplateTypeList(List<Dictionary> templateTypeList) {
		this.templateTypeList = templateTypeList;
	}
	
}
