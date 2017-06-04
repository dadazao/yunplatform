package com.cloudstong.platform.resource.tabulation.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.button.model.Button;
import com.cloudstong.platform.resource.button.service.ButtonService;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;
import com.cloudstong.platform.resource.buttongroup.service.ButtonGroupService;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.metadata.service.CompexDomainService;
import com.cloudstong.platform.resource.seqcode.service.SeqcodeService;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.service.TabulationButtonService;
import com.cloudstong.platform.resource.tabulation.service.TabulationService;
import com.cloudstong.platform.resource.useinfo.model.UseInfo;
import com.cloudstong.platform.resource.useinfo.service.UseInfoService;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.IUserService;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表按钮Action
 */
public class TabulationButtonAction extends BaseAction {
	
	private static final long serialVersionUID = 7070545034229748052L;

	/**
	 * 操作列表按钮的服务接口,<code>tabulationButtonService</code> 对象是TabulationButtonService接口的一个实例
	 */
	@Resource
	private TabulationButtonService tabulationButtonService;

	/**
	 * 操作列表的服务接口,<code>tabulationService</code> 对象是TabulationService接口的一个实例
	 */
	@Resource
	private TabulationService tabulationService;

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
	 * 操作使用信息的服务接口,<code>useInfoService</code> 对象是UseInfoService接口的一个实例
	 */
	@Resource
	private UseInfoService useInfoService;

	/**
	 * 操作表单的服务接口,<code>formService</code> 对象是FormService接口的一个实例
	 */
	@Resource
	private FormService formService;
	
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
	 * 列表
	 */
	private Tabulation tabulation;

	/**
	 * 列表按钮
	 */
	private TabulationButton tabulationButton;

	/**
	 * 列表ID
	 */
	private Long tabulationId;

	/**
	 * 按钮ID
	 */
	private Long buttonId;

	/**
	 * 按钮类型  0 ：按钮 ，1：按钮组
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
	 * 表单集合
	 */
	private List forms;

	public Long getButtonId() {
		return buttonId;
	}

	public void setButtonId(Long buttonId) {
		this.buttonId = buttonId;
	}

	public Long getTabulationId() {
		return tabulationId;
	}

	public void setTabulationId(Long tabulationId) {
		this.tabulationId = tabulationId;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public List getButtons() {
		return buttons;
	}

	public void setButtons(List buttons) {
		this.buttons = buttons;
	}

	public List getForms() {
		return forms;
	}

	public void setForms(List forms) {
		this.forms = forms;
	}

	public List getButtonGroups() {
		return buttonGroups;
	}

	public void setButtonGroups(List buttonGroups) {
		this.buttonGroups = buttonGroups;
	}

	public Tabulation getTabulation() {
		return tabulation;
	}

	public void setTabulation(Tabulation tabulation) {
		this.tabulation = tabulation;
	}

	public TabulationButton getTabulationButton() {
		return tabulationButton;
	}

	public void setTabulationButton(TabulationButton tabulationButton) {
		this.tabulationButton = tabulationButton;
	}

	/**
	 * Description:加载列表按钮表单页面
	 * @return forward
	 */
	public String addButton() {
		try {
			String op =  getRequest().getParameter("op");
			getRequest().setAttribute("op", op);
			
			queryCriteria = new QueryCriteria();
			queryCriteria.setPageSize(-1);
			this.buttons = buttonService.queryButton(queryCriteria);
			this.forms = formService.queryForm(queryCriteria);
			queryCriteria.addQueryCondition("tbl_type", 2);
			this.buttonGroups = buttonGroupService
					.queryButtonGroup(queryCriteria);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("addButton:" + e.getMessage());
			}
		}
		return "addButton";
	}

	/**
	 * Description:显示列表按钮列表
	 * @return forward
	 */
	public String listButton() {
		String op =  getRequest().getParameter("op");
		getRequest().setAttribute("op", op);
		
		this.numPerPage = 10;
		queryCriteria = new QueryCriteria();
		queryCriteria.addQueryCondition("tbl_tabulation", tabulationId);
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1)
				* this.numPerPage + 1);
		queryCriteria.setPageSize(this.numPerPage);
		List tabulationButtons = tabulationButtonService
				.queryTabulationButton(queryCriteria);
		this.pageResult = new PageResult();
		this.pageResult.setContent(tabulationButtons);
		this.pageResult.setTotalCount(tabulationButtonService
				.countTabulationButton(queryCriteria));
		this.pageResult.setPageSize(this.numPerPage);
		this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
		this.pageResult.setCountPage((tabulationButtons.size()
				+ this.numPerPage - 1)
				/ this.numPerPage);
		return "listButton";
	}

	/**
	 * Description:保存列表按钮
	 * @return NONE
	 * @throws IOException
	 */
	public String save() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		tabulationButton.setListId(tabulationId);
		try {
			List list = this.tabulationButtonService.findReplyResult(tabulationButton);
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
			Long tabulationButtonId=null;
			if (tabulationButton.getId() != null&&!tabulationButton.getId().equals("")) {
				tabulationButtonId=tabulationButton.getId();
				TabulationButton tb=this.tabulationButtonService.findTabulationButtonById(tabulationButton.getId());
				if(tb.getButtonType().equals(tabulationButton.getButtonType())&&tb.getButtonId()==tabulationButton.getButtonId()){
					
				}else{
					String bianma = tb.getCode();
					seqcodeService.doUpdateSeqName(bianma, tabulationButton.getShowName());
					
					//上级码
					Tabulation _tabulation = tabulationService.findTabulationById(tabulationId);
					seqcodeService.doUpdateSeq(bianma, _tabulation.getCode());
					//扩展码
					seqcodeService.doUpdateSeqExtends(bianma, _tabulation.getCode());
					
					useInfoService.doDeleteUseInfo(tabulationButton.getId(),3);
					saveUseInfo(tabulationButton,tabulationId,user);
				}
				this.tabulationButtonService.doUpdateTabulationButton(tabulationButton);
			} else {
				tabulationButtonId = this.tabulationButtonService.doSaveTabulationButton(tabulationButton);
				
				//生成编码
				Seqcode seqcode = new Seqcode();
				
				if(tabulationButton.getButtonType().equals("0")){
					//Button _button = buttonService.findByID(tabulationButton.getButtonId());
					seqcode.setTblSystematom(Constants.SYSTEM_ATOM_TABN_BUTN);
				}
				seqcode.setTblName(tabulationButton.getShowName());
				
				String bianma = seqcodeService.doSaveSeqcode(seqcode);
				List<Seqcode> _seqlist = seqcodeService.querySeqByValue(bianma);
				Seqcode _tmpseqcode = null;
				if(_seqlist.size()>0){
					_tmpseqcode = _seqlist.get(0);
					userService.addOrUpdateUserSeq(getRequest().getSession(), _tmpseqcode);
				}
				compexDomainService.doUpdateCode("sys_liebiaobutton", "tbl_bianma", tabulationButtonId, bianma);
				
				//上级码
				Tabulation _tabulation = tabulationService.findTabulationById(tabulationId);
				seqcodeService.doUpdateSeq(bianma, _tabulation.getCode());
				//扩展码
				seqcodeService.doUpdateSeqExtends(bianma, _tabulation.getCode());
				
				tabulationButton.setId(Long.valueOf(tabulationButtonId));
				saveUseInfo(tabulationButton,tabulationId,user);
			}
			//若列表按钮显示顺序相同，则大于此顺序的都自动加1
			tabulationService.doUpdateButtonOrder(tabulationButton,tabulationButtonId);
			
			//更新主记录的修改人和修改时间
			compexDomainService.doUpdateMainInfo("sys_liebiao",tabulationId,user);
			printJSON("success", false);
		} catch (Exception e) {
			printJSON("fail", false);
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:保存列表按钮使用信息
	 * @param tabulationButton 列表按钮
	 * @param tabulationId 列表ID
	 * @param user 用户信息
	 * @throws Exception
	 */
	public void saveUseInfo(TabulationButton tabulationButton,Long tabulationId,SysUser user) throws Exception{
		Tabulation tabulation=tabulationService.findTabulationById(tabulationId);
		UseInfo useInfo=new UseInfo();
		useInfo.setRelationId(String.valueOf(tabulationButton.getId()));
		useInfo.setBizId(tabulation.getId());
		useInfo.setCompId(tabulationButton.getButtonId());
		useInfo.setBizName(tabulation.getTabulationName());
		if(tabulationButton.getButtonType().equals("0")){
			Button button = buttonService.findByID(Long.valueOf(tabulationButton.getButtonId()));
			useInfo.setCompName(button.getButtonName() + "按钮");
			useInfo.setCode(button.getButtonCode());
		}else if(tabulationButton.getButtonType().equals("1")){
			ButtonGroup buttonGroup = buttonGroupService.findByID(Long.valueOf(tabulationButton.getButtonId()));
			useInfo.setCompName(buttonGroup.getButtonGroupName());
			useInfo.setCode("");
		}
		useInfo.setType("列表按钮");
		useInfo.setTypeId(3);
		useInfo.setCreateBy(user.getId());
		useInfo.setCreateDate(new Date());
		useInfoService.doSaveUseInfo(useInfo);
	}

	/**
	 * Description:编辑列表按钮
	 * @return forward
	 * @throws Exception
	 */
	public String edit() throws Exception {
		String op =  getRequest().getParameter("op");
		String marking = getRequest().getParameter("marking");
		getRequest().setAttribute("op", op);
		getRequest().setAttribute("marking", marking);
		
		this.tabulation = tabulationService.findTabulationById(tabulationId);
		this.tabulationButton = tabulationButtonService
				.findTabulationButtonById(buttonId);

		queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		this.buttons = buttonService.queryButton(queryCriteria);
		this.forms = formService.queryForm(queryCriteria);
		queryCriteria.addQueryCondition("tbl_type", 2);
		this.buttonGroups = buttonGroupService.queryButtonGroup(queryCriteria);
		return "editButton";
	}

	/**
	 * Description:删除列表按钮
	 * @return NONE
	 * @throws IOException
	 */
	public String delete() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		try {
			if (selectedSubIDs != null) {
				this.tabulationButtonService.doDeleteTabulationButtons(selectedSubIDs);
				//更新主记录的修改人和修改时间
				compexDomainService.doUpdateMainInfo("sys_liebiao",tabulationId,user);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("delete:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:查询按钮和按钮组，返回json对象，为下拉框提供数据来源
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
				"<select id=\"buttonID\" name=\"tabulationButton.buttonId\" style=\"width:135px\" onchange=\"showButtonComment();\">");
		if (buttonType.equals("1")) {
			queryCriteria.addQueryCondition("tbl_type", 2);
			this.buttonGroups = buttonGroupService.queryButtonGroup(queryCriteria);
			for (ButtonGroup buttonGroup : buttonGroups) {
				sb.append("<option title=\""+buttonGroup.getComment()+"\" value=\"" + buttonGroup.getId() + "\">"
						+ buttonGroup.getButtonGroupName() + "</option>");
			}
		} else {
			queryCriteria.addQueryCondition("tbl_buttonType", 1);
			this.buttons = buttonService.queryButton(queryCriteria);
			for (Button button : buttons) {
				sb.append("<option title=\""+button.getComment()+"\" value=\"" + button.getId() + "\">"
						+ button.getButtonName() + "</option>");
			}
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}
}
