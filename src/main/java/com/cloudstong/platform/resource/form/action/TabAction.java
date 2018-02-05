package com.cloudstong.platform.resource.form.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;
import com.cloudstong.platform.resource.buttongroup.service.ButtonGroupService;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.Tab;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.resource.form.service.TabService;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.metadata.service.CompexDomainService;
import com.cloudstong.platform.resource.seqcode.service.SeqcodeService;
import com.cloudstong.platform.resource.template.model.Template;
import com.cloudstong.platform.resource.template.service.TemplateService;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.IUserService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 选项卡Action
 */

@SuppressWarnings("serial")
@ParentPackage("default")
@Namespace("/pages/resource/tab")
@Results(value = { 
		@Result(name="add", location = "/WEB-INF/view/resource/form/tabEdit.jsp"),
		@Result(name="edit", location = "/WEB-INF/view/resource/form/tabEdit.jsp"),
		@Result(name="list", location = "/WEB-INF/view/resource/form/tabList.jsp"),
		@Result(name="input", location = "/WEB-INF/view/resource/form/tabEdit.jsp"),
		@Result(name="view", location = "/WEB-INF/view/resource/form/tabView.jsp")
})
public class TabAction extends BaseAction {

	/**
	 * 选项卡
	 */
	private Tab tab;
	/**
	 * 选项卡ID
	 */
	private Long id;
	/**
	 * 表单ID
	 */
	private Long formId;
	/**
	 * 模板集合
	 */
	private List templates;
	/**
	 * 选项卡集合
	 */
	private List<Tab> tabs;
	/**
	 * 按钮组集合
	 */
	private List<ButtonGroup> buttonGroups;
	/**
	 * 字体集合
	 */
	private List<Dictionary> fontStyles;
	/**
	 * 颜色集合
	 */
	private List<Dictionary> fontColors;
	/**
	 * 类型（表单模板或组合模板）
	 */
	private Integer type;
	/**
	 * 模板类型
	 */
	private Integer templateType;

	/**
	 * 选项卡模板类型
	 */
	private Integer tabtemplateType;
	/**
	 * 选项卡模板
	 */
	private String tabtemplate;
	

	/**
	 * 操作选项卡的服务接口,<code>tabService</code> 对象是TabService接口的一个实例
	 */
	@Resource
	private TabService tabService;
	/**
	 * 操作按钮组的服务接口,<code>buttonGroupService</code> 对象是ButtonGroupService接口的一个实例
	 */
	@Resource
	private ButtonGroupService buttonGroupService;
	/**
	 * 操作代码的服务接口,<code>dictionaryService</code> 对象是DictionaryService接口的一个实例
	 */
	@Resource
	private DictionaryService dictionaryService;
	/**
	 * 操作模板的服务接口,<code>templateService</code> 对象是TemplateService接口的一个实例
	 */
	@Resource
	private TemplateService templateService;
	/**
	 *  操作配置模块的服务接口,<code>compexDomainService</code> 对象是CompexDomainService接口的一个实例
	 */
	@Resource
	private CompexDomainService compexDomainService;

	/**
	 * 操作表单的服务接口,<code>formService</code> 对象是FormService接口的一个实例
	 */
	@Resource
	private FormService formService;
	
	/**
	 * 操作编码的服务接口,<code>seqCodeService</code> 对象是SeqcodeService接口的一个实例
	 */
	@Resource
	private SeqcodeService seqcodeService;
	
	@Resource
	protected IUserService userService;
	
	/**
	 * Description: 显示选项卡列表
	 * @return forward
	 */
	@Action("list")
	public String list() {
		String op =  getRequest().getParameter("op");
		getRequest().setAttribute("op", op);
		
		this.numPerPage = 10;
		queryCriteria = new QueryCriteria();
		queryCriteria.addQueryCondition("tbl_form", formId);
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1)
				* this.numPerPage + 1);
		queryCriteria.setPageSize(this.numPerPage);
		List tables = tabService.queryTab(queryCriteria);

		this.pageResult = new PageResult();
		this.pageResult.setContent(tables);
		this.pageResult.setTotalCount(tabService.countTab(queryCriteria));
		this.pageResult.setPageSize(this.numPerPage);
		this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
		this.pageResult.setCountPage((tables.size() + this.numPerPage - 1)
				/ this.numPerPage);

		return "list";
	}

	@Action("search")
	public String search() {
		return "search";
	}

	/**
	 * Description:显示选项卡表单查看页面
	 * @return forward
	 */
	@Action("view")
	public String view() {
		this.tab = tabService.findTabById(id);
		return "view";
	}

	/**
	 * Description:打开选项卡新建页面
	 * @return forward
	 * @throws Exception
	 */
	@Action("add")
	public String add() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		String op =  getRequest().getParameter("op");
		getRequest().setAttribute("op", op);
		
		this.tab = new Tab();
		queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		queryCriteria.addQueryCondition("tbl_type", 2);
		this.buttonGroups = buttonGroupService.queryButtonGroup(queryCriteria);
		this.fontStyles = dictionaryService.queryByParent(1597634417L);
		this.fontColors = dictionaryService.queryByParent(101676872749L);
		return "add";
	}

	/**
	 * Description:显示选项卡表单编辑页面
	 * @return forward
	 * @throws Exception
	 */
	@Action("edit")
	public String edit() throws Exception {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			String op =  getRequest().getParameter("op");
			getRequest().setAttribute("op", op);
			
			this.tab = tabService.findTabById(id);

			queryCriteria = new QueryCriteria();
			queryCriteria.setPageSize(-1);
			queryCriteria.addQueryCondition("tbl_type", 2);
			this.buttonGroups = buttonGroupService.queryButtonGroup(queryCriteria);
			this.fontStyles = dictionaryService.queryByParent(1597634417L);
			this.fontColors = dictionaryService.queryByParent(101676872749L);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("edit:" + e.getMessage());
			}
		}
		return "edit";
	}

	/**
	 * Description:显示选项卡下拉框
	 * @return NONE
	 * @throws IOException
	 */
	@Action("show")
	public String show() throws IOException {
		QueryCriteria qc = new QueryCriteria();
		qc.addQueryCondition("tbl_form", formId);
		qc.setPageSize(-1);
		this.tabs = tabService.queryTab(qc);

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(
				"<select name=\"formColumn.tabId\" style=\"width:135px\">");
		for (Tab tab : tabs) {
			sb.append("<option value=\"" + tab.getId() + "\">"
					+ tab.getTabName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:保存选项卡信息
	 * @return NONE
	 * @throws IOException
	 */
	@Action("save")
	public String save() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON("userFail", false);
			return NONE;
		}
		
		tab.setFormId(formId);
		Long tabId = -1L;
		try {
			List list=tabService.findReplyResult("sys_biaodantab","tbl_mingcheng",tab.getTabName(),tab.getId(),formId);
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			if(list.size()>0){
				out.write("failed");
				out.flush();
				out.close();
				return NONE;
			}
			if (tab.getId() != null && !tab.getId().equals("")) {
				Tab _tab = this.tabService.findTabById(tab.getId());
				if(tab.getTemplateId()!=null){
					if(!tab.getTemplateId().equals(_tab.getTemplateId())){
						if(_tab.getType() == 1){
							List _list = formService.findFormColumnByTabId(_tab.getId());
							if(_list.size()>0){
								out.write("hasColumn");
								out.flush();
								out.close();
								return NONE;
							}
						}
					}
				}
				this.tabService.doUpdateTab(tab);
				tabId = tab.getId();
				
				// 修改编码表名称
				seqcodeService.doUpdateSeqName(_tab.getCode(),_tab.getTabName());
			} else {
				tabId = this.tabService.doSaveTab(tab);
				
				Seqcode seqcode = new Seqcode();
				seqcode.setTblName(tab.getTabName());
				seqcode.setTblSystematom(Constants.SYSTEM_ATOM_ITEM);
				seqcode.setTblObject(String.valueOf(tabId));
				String bianma = seqcodeService.doSaveSeqcode(seqcode);
				List<Seqcode> _seqlist = seqcodeService.querySeqByValue(bianma);
				Seqcode _tmpseqcode = null;
				if(_seqlist.size()>0){
					_tmpseqcode = _seqlist.get(0);
					userService.addOrUpdateUserSeq(getRequest().getSession(), _tmpseqcode);
				}
				compexDomainService.doUpdateCode("sys_biaodantab",
						"tbl_bianma", tabId, bianma);
				
				if(tab.getFormId()!=null && !tab.getFormId().equals("") && !tab.getFormId().equals("-1")){
					//上级码
					Form _form = formService.findFormById(tab.getFormId());
					seqcodeService.doUpdateSeq(bianma, _form.getCode());
					//扩展码
					seqcodeService.doUpdateSeqExtends(bianma, _form.getCode());
				}
			}
			
			//若tab页显示顺序相同，则大于此顺序的都自动加1
			this.tabService.doUpdateOrder(tab,tabId);

			//更新主记录的修改人和修改时间
			compexDomainService.doUpdateMainInfo("sys_biaodan",formId,user);
			printJSON("success", false, tabId.toString());
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
			e.printStackTrace();
			printJSON("fail", false);
		}
		return NONE;
	}

	/**
	 * Description:删除选项卡信息
	 * @return NONE
	 * @throws IOException
	 */
	@Action("del")
	public String delete() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON("userFail", false);
			return NONE;
		}
		try {
			if (selectedSubIDs != null) {
				this.tabService.doDeleteTabs(selectedSubIDs);
			} else {
				this.tabService.doDeleteTab(id);
			}
			//更新主记录的修改人和修改时间
			compexDomainService.doUpdateMainInfo("sys_biaodan",formId,user);
			printJSON("success");
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("delete:" + e.getMessage());
			}
			printJSON("fail");
		}
		return NONE;
	}

	/**
	 * Description:显示模板类型
	 * @return NONE
	 * @throws IOException
	 */
	@Action("showTemplateType")
	public String showTemplateType() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		List<Dictionary> dictionaryList = new ArrayList<Dictionary>();
		if (type == 0) {
			dictionaryList = dictionaryService.queryByParent(1014150558L);
		} else if (type == 1) {
			dictionaryList = dictionaryService.queryByParent(47948533L);
		}

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(
				"<select id=\"tabtemplateType\" name=\"tab.templateType\" style=\"width:100px\" onchange=\"showTemplate("
						+ type + ",this.value)\">");
		for (Dictionary dictionary : dictionaryList) {
			String selected = "";
			if (tabtemplateType != null) {
				if (dictionary.getValue().equals(tabtemplateType.toString())) {
					selected = "selected";
				}
			}

			sb.append("<option value=\"" + dictionary.getValue() + "\" "
					+ selected + ">" + dictionary.getName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示模板
	 * @return NONE
	 * @throws IOException
	 */
	@Action("showTemplate")
	public String showTemplate() throws IOException {
		QueryCriteria qc = new QueryCriteria();
		qc.addQueryCondition("tbl_type", type);
		qc.addQueryCondition("tbl_templatetype", templateType);
		qc.setPageSize(-1);
		List<Template> templates = templateService.queryTemplate(qc);

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(
				"<select id=\"tabtemplateId\" name=\"tab.templateId\" style=\"width:150px\">");
		for (Template template : templates) {
			String selected = "";
			if (tabtemplate != null && !"".equals(tabtemplate)) {
				if (template.getId().equals(Long.valueOf(tabtemplate))) {
					selected = "selected";
				}
			}
			sb.append("<option value=\"" + template.getId() + "\" " + selected
					+ ">" + template.getTemplateChName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public Tab getTab() {
		return tab;
	}

	public void setTab(Tab tab) {
		this.tab = tab;
	}

	public List getTemplates() {
		return templates;
	}

	public void setTemplates(List templates) {
		this.templates = templates;
	}

	public List<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}

	public List<ButtonGroup> getButtonGroups() {
		return buttonGroups;
	}

	public void setButtonGroups(List<ButtonGroup> buttonGroups) {
		this.buttonGroups = buttonGroups;
	}

	public List<Dictionary> getFontStyles() {
		return fontStyles;
	}

	public void setFontStyles(List<Dictionary> fontStyles) {
		this.fontStyles = fontStyles;
	}

	public List<Dictionary> getFontColors() {
		return fontColors;
	}

	public void setFontColors(List<Dictionary> fontColors) {
		this.fontColors = fontColors;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public Integer getTabtemplateType() {
		return tabtemplateType;
	}

	public void setTabtemplateType(Integer tabtemplateType) {
		this.tabtemplateType = tabtemplateType;
	}

	public String getTabtemplate() {
		return tabtemplate;
	}

	public void setTabtemplate(String tabtemplate) {
		this.tabtemplate = tabtemplate;
	}

}
