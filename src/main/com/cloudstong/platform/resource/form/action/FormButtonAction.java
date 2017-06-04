package com.cloudstong.platform.resource.form.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.button.model.Button;
import com.cloudstong.platform.resource.button.service.ButtonService;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;
import com.cloudstong.platform.resource.buttongroup.service.ButtonGroupService;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.form.model.Tab;
import com.cloudstong.platform.resource.form.service.FormButtonService;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.resource.form.service.TabService;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.metadata.service.CompexDomainService;
import com.cloudstong.platform.resource.seqcode.service.SeqcodeService;
import com.cloudstong.platform.resource.template.model.Partition;
import com.cloudstong.platform.resource.template.model.Template;
import com.cloudstong.platform.resource.template.service.PartitionService;
import com.cloudstong.platform.resource.template.service.TemplateService;
import com.cloudstong.platform.resource.useinfo.model.UseInfo;
import com.cloudstong.platform.resource.useinfo.service.UseInfoService;
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
 * Description:表单按钮Action
 */
public class FormButtonAction extends BaseAction {
	/**
	 * 操作表单按钮的服务接口,<code>formButtonService</code> 对象是FormButtonService接口的一个实例
	 */
	@Resource
	private FormButtonService formButtonService;

	/**
	 * 操作表单的服务接口,<code>formService</code> 对象是FormService接口的一个实例
	 */
	@Resource
	private FormService formService;

	/**
	 * 操作按钮的服务接口,<code>buttonService</code> 对象是ButtonService接口的一个实例
	 */
	@Resource
	private ButtonService buttonService;

	/**
	 * 操作按钮组的服务接口,<code>buttonGroupService</code> 对象是ButtonGroupService接口的一个实例
	 */
	@Resource
	private ButtonGroupService buttonGroupService;

	/**
	 * 操作模板的服务接口,<code>templateService</code> 对象是TemplateService接口的一个实例
	 */
	@Resource
	private TemplateService templateService;

	/**
	 * 操作分区的服务接口,<code>partitionService</code> 对象是PartitionService接口的一个实例
	 */
	@Resource
	private PartitionService partitionService;

	/**
	 * 操作选项卡的服务接口,<code>tabService</code> 对象是TabService接口的一个实例
	 */
	@Resource
	private TabService tabService;
	
	/**
	 * 操作使用信息的服务接口,<code>useInfoService</code> 对象是UseInfoService接口的一个实例
	 */
	@Resource
	private UseInfoService useInfoService;
	
	/**
	 * 操作编码的服务接口,<code>seqcodeService</code> 对象是SeqcodeService接口的一个实例
	 */
	@Resource
	private SeqcodeService seqcodeService;
	
	/**
	 * 操作配置模块的服务接口,<code>compexDomainService</code> 对象是CompexDomainService接口的一个实例
	 */
	@Resource
	protected CompexDomainService compexDomainService;
	
	@Resource
	protected IUserService userService;

	/**
	 * 表单
	 */
	private Form form;

	/**
	 * 表单按钮
	 */
	private FormButton formButton;

	/**
	 * 表单ID
	 */
	private Long formId;

	/**
	 * 按钮ID
	 */
	private Long buttonId;

	/**
	 * 按钮类型 0 ：按钮 ，1：按钮组
	 */
	private String buttonType;

	/**
	 * 按钮集合
	 */
	private List<Button> buttons;

	/**
	 * 按钮组集合
	 */
	private List<ButtonGroup> buttonGroups;

	/**
	 * 选项卡ID
	 */
	private Long buttonTabId;

	/**
	 * 分区ID
	 */
	private Long buttonPartitionId;


	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public Long getButtonId() {
		return buttonId;
	}

	public void setButtonId(Long buttonId) {
		this.buttonId = buttonId;
	}

	public Long getButtonTabId() {
		return buttonTabId;
	}

	public void setButtonTabId(Long buttonTabId) {
		this.buttonTabId = buttonTabId;
	}

	public Long getButtonPartitionId() {
		return buttonPartitionId;
	}

	public void setButtonPartitionId(Long buttonPartitionId) {
		this.buttonPartitionId = buttonPartitionId;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public FormButton getFormButton() {
		return formButton;
	}

	public void setFormButton(FormButton formButton) {
		this.formButton = formButton;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

	public List<ButtonGroup> getButtonGroups() {
		return buttonGroups;
	}

	public void setButtonGroups(List<ButtonGroup> buttonGroups) {
		this.buttonGroups = buttonGroups;
	}

	/**
	 * Description:加载表单按钮表单页面
	 * @return forward
	 */
	public String addButton() {
		try {
			String op =  getRequest().getParameter("op");
			getRequest().setAttribute("op", op);
			
			queryCriteria = new QueryCriteria();
			queryCriteria.setPageSize(-1);
			this.buttons = buttonService.queryButton(queryCriteria);
			queryCriteria.addQueryCondition("tbl_type", 1);
			this.buttonGroups = buttonGroupService.queryButtonGroup(queryCriteria);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("addButton:" + e.getMessage());
			}
		}
		return "addButton";
	}

	/**
	 * Description:显示表单按钮列表
	 * @return forward
	 */
	public String listButton() {
		String op =  getRequest().getParameter("op");
		getRequest().setAttribute("op", op);
		
		this.numPerPage = 10;
		queryCriteria = new QueryCriteria();
		queryCriteria.addQueryCondition("tbl_formId", formId);
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1)
				* this.numPerPage + 1);
		queryCriteria.setPageSize(this.numPerPage);
		List formButtons = formButtonService.queryFormButton(queryCriteria);
		this.pageResult = new PageResult();
		this.pageResult.setContent(formButtons);
		this.pageResult.setTotalCount(formButtonService
				.countFormButton(queryCriteria));
		this.pageResult.setPageSize(this.numPerPage);
		this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
		this.pageResult.setCountPage((formButtons.size() + this.numPerPage - 1)
				/ this.numPerPage);
		return "listButton";
	}

	/**
	 * Description:保存表单按钮
	 * @return NONE
	 * @throws IOException
	 */
	public String save() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		formButton.setFormId(formId);
		try {
			List list = this.formButtonService.findReplyResult(formButton);
			if(list.size()>0){
				HttpServletResponse response = getResponse();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.write("failed");
				out.flush();
				out.close();
				return NONE;
			}
			
			if (formButton.getId() != null&&!formButton.getId().equals("")) {
				FormButton fb=this.formButtonService.findFormButtonById(formButton.getId());
				if(fb.getButtonType().equals(formButton.getButtonType())&&fb.getButtonId()==formButton.getButtonId()){
					
				}else{
					String bianma = fb.getCode();
					seqcodeService.doUpdateSeqName(bianma, formButton.getShowName());
					if(formButton.getTabId()==null||formButton.getTabId().equals("")||formButton.getTabId().equals("-1")){
						//上级码
						Form _form = formService.findFormById(formId);
						seqcodeService.doUpdateSeq(bianma, _form.getCode());
						//扩展码
						seqcodeService.doUpdateSeqExtends(bianma, _form.getCode());
					}else{
						//上级码
						Tab _tab = tabService.findTabById(formButton.getTabId());
						seqcodeService.doUpdateSeq(bianma, _tab.getCode());
						//扩展码
						seqcodeService.doUpdateSeqExtends(bianma, _tab.getCode());
					}
					
					
					useInfoService.doDeleteUseInfo(formButton.getId(),2);
					saveUseInfo(formButton,formId,user);
				}
				this.formButtonService.doUpdateFormButton(formButton);
				
				
			} else {
				Long formButtonId = this.formButtonService.doSaveFormButton(formButton);
				
				//生成编码
				Seqcode seqcode = new Seqcode();
				
				if(formButton.getButtonType().equals("0")){
					//Button _button = buttonService.findByID(formButton.getButtonId());
					seqcode.setTblSystematom(Constants.SYSTEM_ATOM_FORM_BUTN);
				}
				seqcode.setTblName(formButton.getShowName());
				seqcode.setTblObject(formButtonId.toString());
				String bianma = seqcodeService.doSaveSeqcode(seqcode);
				List<Seqcode> _seqlist = seqcodeService.querySeqByValue(bianma);
				Seqcode _tmpseqcode = null;
				if(_seqlist.size()>0){
					_tmpseqcode = _seqlist.get(0);
					userService.addOrUpdateUserSeq(getRequest().getSession(), _tmpseqcode);
				}
				compexDomainService.doUpdateCode("sys_biaodanbutton", "tbl_bianma", formButtonId, bianma);
				
				if(formButton.getTabId()==null||formButton.getTabId().equals("")||formButton.getTabId().equals("-1")){
					//上级码
					Form _form = formService.findFormById(formId);
					seqcodeService.doUpdateSeq(bianma, _form.getCode());
					//扩展码
					seqcodeService.doUpdateSeqExtends(bianma, _form.getCode());
				}else{
					//上级码
					Tab _tab = tabService.findTabById(formButton.getTabId());
					seqcodeService.doUpdateSeq(bianma, _tab.getCode());
					//扩展码
					seqcodeService.doUpdateSeqExtends(bianma, _tab.getCode());
				}
				
				formButton.setId(formButtonId);
				saveUseInfo(formButton,formId,user);
			}
			//若表单按钮显示顺序相同，则大于此顺序的都自动加1
			this.formButtonService.doUpdateOrder(formButton);
			
			//更新主记录的修改人和修改时间
			compexDomainService.doUpdateMainInfo("sys_biaodan",formId,user);
			printJSON("success", false);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
			printJSON("fail", false);
		}
		return NONE;
	}
	
	/**
	 * Description:保存表单按钮的使用信息
	 * @param formButton 表单按钮
	 * @param formId 表单ID
	 * @param user 用户信息
	 * @throws Exception
	 */
	public void saveUseInfo(FormButton formButton,Long formId,SysUser user) throws Exception{
		Form form=formService.findFormById(formId);
		UseInfo useInfo=new UseInfo();
		useInfo.setRelationId(String.valueOf(formButton.getId()));
		useInfo.setBizId(form.getId());
		useInfo.setCompId(formButton.getButtonId());
		useInfo.setBizName(form.getFormName());
		if(formButton.getButtonType().equals("0")){
			Button button = buttonService.findByID(formButton.getButtonId());
			useInfo.setCompName(button.getButtonName() + "按钮");
			useInfo.setCode(button.getButtonCode());
		}else if(formButton.getButtonType().equals("1")){
			ButtonGroup buttonGroup = buttonGroupService.findByID(formButton.getButtonId());
			useInfo.setCompName(buttonGroup.getButtonGroupName());
			useInfo.setCode("");
		}
		useInfo.setType("表单按钮");
		useInfo.setTypeId(2);
		useInfo.setCreateBy(user.getId());
		useInfo.setCreateDate(new Date());
		useInfoService.doSaveUseInfo(useInfo);
	}

	/**
	 * Description:编辑表单按钮
	 * @return forward
	 * @throws Exception
	 */
	public String edit() throws Exception {
		String op =  getRequest().getParameter("op");
		String marking = getRequest().getParameter("marking");
		getRequest().setAttribute("op", op);
		getRequest().setAttribute("marking", marking);
		
		this.form = formService.findFormById(formId);
		this.formButton = formButtonService.findFormButtonById(buttonId);

		queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		this.buttons = buttonService.queryButton(queryCriteria);
		queryCriteria.addQueryCondition("tbl_type", 1);
		this.buttonGroups = buttonGroupService.queryButtonGroup(queryCriteria);
		return "editButton";
	}

	/**
	 * Description:删除表单按钮
	 * @return NONE
	 * @throws IOException
	 */
	public String delete() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		try {
			if (selectedSubIDs != null) {
				this.formButtonService.doDeleteFormButtons(selectedSubIDs);
				//更新主记录的修改人和修改时间
				compexDomainService.doUpdateMainInfo("sys_biaodan",formId,user);
			}
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
	 * Description:显示表单按钮
	 * @return NONE
	 * @throws Exception
	 */
	public String showButton() throws Exception {
		queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(
				"<select id=\"buttonID\" name=\"formButton.buttonId\" style=\"width:186px\" onchange=\"showButtonComment();\">");
		if (buttonType.equals("1")) {
			queryCriteria.addQueryCondition("tbl_type", 1);
			;
			this.buttonGroups = buttonGroupService
					.queryButtonGroup(queryCriteria);
			for (ButtonGroup buttonGroup : buttonGroups) {
				sb.append("<option title=\"" +buttonGroup.getComment()+"\" value=\"" + buttonGroup.getId() + "\">"
						+ buttonGroup.getButtonGroupName() + "</option>");
			}
		} else {
			this.buttons = buttonService.queryButton(queryCriteria);
			for (Button button : buttons) {
				sb.append("<option title=\"" +button.getComment()+"\" value=\"" + button.getId() + "\">"
						+ button.getButtonName() + "</option>");
			}
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示表单选项卡
	 * @return NONE
	 * @throws IOException
	 */
	public String showButtonTab() throws IOException {
		QueryCriteria qc = new QueryCriteria();
		qc.addQueryCondition("tbl_form", formId);
		qc.addQueryCondition("tbl_type", 1);
		qc.setPageSize(-1);
		List<Tab> tabs = tabService.queryTab(qc);

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(
				"<select id=\"formButtonTabId\" name=\"formButton.tabId\" style=\"width:186px\" onchange=\"showButtonPartition(this.value)\">");
		sb.append("<option value='-1' >整个表单</option>");
		for (Tab tab : tabs) {
			String selected = "";
			if (buttonTabId != null) {
				if (tab.getId().equals(buttonTabId)) {
					selected = "selected";
				}
			}
			sb.append("<option value=\"" + tab.getId() + "\" " + selected + ">"
					+ tab.getTabName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示分区
	 * @return NONE
	 * @throws IOException
	 */
	public String showPartition() throws IOException {
		Tab tab = tabService.findTabById(buttonTabId);
		Long templateId = tab.getTemplateId();
		Template template = templateService.findTemplateById(templateId);

		QueryCriteria qc = new QueryCriteria();
		qc.addQueryCondition("tbl_templateid", template.getId());
		qc.setPageSize(-1);
		List<Partition> partitions = new ArrayList<Partition>();
		if ("1".equals(template.getType())) {
			partitions = partitionService.queryPartition(qc);
		}

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(
				"<select id=\"formButtonPartitionId\" name=\"formButton.partitionId\" style=\"width:186px\">");
		for (Partition partition : partitions) {
			String selected = "";
			if (buttonPartitionId != null) {
				if (partition.getId().equals(buttonPartitionId)) {
					selected = "selected";
				}
			}
			sb.append("<option value=\"" + partition.getId() + "\" " + selected
					+ ">" + partition.getPartitionName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}
}
