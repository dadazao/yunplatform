package com.cloudstong.platform.resource.tabulation.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.core.util.EncryptUtil;
import com.cloudstong.platform.resource.button.model.Button;
import com.cloudstong.platform.resource.button.service.ButtonService;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.resource.listcontrol.model.ListControl;
import com.cloudstong.platform.resource.listcontrol.service.ListControlService;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.service.ColumnService;
import com.cloudstong.platform.resource.metadata.service.TableService;
import com.cloudstong.platform.resource.operationmanage.model.OperationManage;
import com.cloudstong.platform.resource.operationmanage.service.OperationManageService;
import com.cloudstong.platform.resource.querycontrol.model.AdvanceQueryControl;
import com.cloudstong.platform.resource.querycontrol.model.QueryControl;
import com.cloudstong.platform.resource.querycontrol.service.QueryControlService;
import com.cloudstong.platform.resource.seqcode.service.SeqcodeService;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.model.TabulationColumn;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.resource.tabulation.model.TabulationQuery;
import com.cloudstong.platform.resource.tabulation.service.TabulationButtonService;
import com.cloudstong.platform.resource.tabulation.service.TabulationService;
import com.cloudstong.platform.resource.template.service.TemplateService;
import com.cloudstong.platform.resource.useinfo.model.UseInfo;
import com.cloudstong.platform.resource.useinfo.service.UseInfoService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表Action
 */
public class TabulationAction extends CompexDomainAction {
	
	private static final long serialVersionUID = -1059607693138559139L;

	/**
	 * 列表ID
	 */
	private Long tabulationId;

	/**
	 * 列表字段ID
	 */
	private Long tabulationColumnId;

	/**
	 * 列表筛选条件ID
	 */
	private Long tabulationQueryId;

	/**
	 * 列表操作按钮ID
	 */
	private Long tabulationOptId;

	/**
	 * 数据表ID
	 */
	private Long tableId;

	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 表单Id
	 */
	private Long formManageId;

	/**
	 * 数据表集合
	 */
	private List<Table> tables;
	/**
	 * 字段集合
	 */
	private List<Column> columns;
	/**
	 * 列表组件集合
	 */
	private List<ListControl> listControls;
	/**
	 * 查询组件集合
	 */
	private List<QueryControl> queryControls;
	/**
	 * 高级查询组件集合
	 */
	private List<AdvanceQueryControl> advanceQueryControls;
	/**
	 * 模板集合
	 */
	private List templateLists;
	/**
	 * 表单集合
	 */
	private List<Form> forms;
	/**
	 * 列表字段
	 */
	private TabulationColumn tabulationColumn;
	/**
	 * 列表筛选条件
	 */
	private TabulationQuery tabulationQuery;
	/**
	 * 列表操作按钮
	 */
	private TabulationOpt tabulationOpt;

	/**
	 * 操作列表的服务接口,<code>tabulationService</code> 对象是TabulationService接口的一个实例
	 */
	@Resource
	private TabulationService tabulationService;
	
	/**
	 * 操作表属性的服务接口,<code>tableService</code> 对象是TableService接口的一个实例
	 */
	@Resource
	private TableService tableService;

	/**
	 * 操作字段的服务接口,<code>columnService</code> 对象是ColumnService接口的一个实例
	 */
	@Resource
	private ColumnService columnService;

	/**
	 * 操作模板的服务接口,<code>templateService</code> 对象是TemplateService接口的一个实例
	 */
	@Resource
	private TemplateService templateService;

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
	 * 操作按钮的服务接口,<code>buttonService</code> 对象是ButtonService接口的一个实例
	 */
	@Resource
	private ButtonService buttonService;

	/**
	 * 操作列表组件的服务接口,<code>listControlService</code> 对象是ListControlService接口的一个实例
	 */
	@Resource
	private ListControlService listControlService;

	/**
	 * 操作查询组件的服务接口,<code>queryControlService</code> 对象是QueryControlService接口的一个实例
	 */
	@Resource
	private QueryControlService queryControlService;

	/**
	 * 操作列表按钮的服务接口,<code>tabulationButtonService</code> 对象是TabulationButtonService接口的一个实例
	 */
	@Resource
	private TabulationButtonService tabulationButtonService;

	/**
	 * 操作列的服务接口,<code>OperationManageService</code> 对象是OperationManageService接口的一个实例
	 */
	@Resource
	private OperationManageService operationManageService;
	
	/**
	 * 操作使用信息的服务接口,<code>useInfoService</code> 对象是UseInfoService接口的一个实例
	 */
	@Resource
	private UseInfoService useInfoService;

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public List<AdvanceQueryControl> getAdvanceQueryControls() {
		return advanceQueryControls;
	}

	public void setAdvanceQueryControls(List<AdvanceQueryControl> advanceQueryControls) {
		this.advanceQueryControls = advanceQueryControls;
	}

	public Long getFormManageId() {
		return formManageId;
	}

	public void setFormManageId(Long formManageId) {
		this.formManageId = formManageId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List<ListControl> getListControls() {
		return listControls;
	}

	public void setListControls(List<ListControl> listControls) {
		this.listControls = listControls;
	}

	public List<QueryControl> getQueryControls() {
		return queryControls;
	}

	public void setQueryControls(List<QueryControl> queryControls) {
		this.queryControls = queryControls;
	}

	public Long getTabulationQueryId() {
		return tabulationQueryId;
	}

	public Long getTabulationOptId() {
		return tabulationOptId;
	}

	public List getTemplateLists() {
		return templateLists;
	}

	public void setTemplateLists(List templateLists) {
		this.templateLists = templateLists;
	}

	public List<Form> getForms() {
		return forms;
	}

	public void setForms(List<Form> forms) {
		this.forms = forms;
	}

	public Long getTabulationColumnId() {
		return tabulationColumnId;
	}

	public void setTabulationColumnId(Long tabulationColumnId) {
		this.tabulationColumnId = tabulationColumnId;
	}

	public TabulationOpt getTabulationOpt() {
		return tabulationOpt;
	}

	public void setTabulationOpt(TabulationOpt tabulationOpt) {
		this.tabulationOpt = tabulationOpt;
	}

	/* 
	 * 显示列表
	 */
	public String list() {
		super.list();
		this.formManageId = tabulation.getFormId();
		return "list";
	}

	/**
	 * Description:取出列表集合，作为json对象返回到页面
	 * @return NONE
	 * @throws IOException
	 */
	public String getTabulations() throws IOException {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		List tabulations = tabulationService.queryTabulation(qc);
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, tabulations);
		return NONE;
	}

	/* 
	 * 打开列表新建页面
	 */
	public String add() {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			this.tabulation = new Tabulation();
			queryCriteria = new QueryCriteria();
			queryCriteria.setPageSize(-1);
			//取出所属表集合
			this.tables = tableService.queryTable(queryCriteria);
			//取出表单集合
			this.forms = formService.queryForm(queryCriteria);
			queryCriteria.addQueryCondition("tbl_type", "0");
			queryCriteria.addQueryCondition("tbl_templatetype", "2");
			//取出列表模板集合
			this.templateLists = templateService.queryTemplate(queryCriteria);
			//取出列表组件集合
			this.listControls = listControlService.findAllListControl();
			//取出查询组件集合
			this.queryControls = queryControlService.findAllQueryControl();
			//取出高级查询组件集合
			this.advanceQueryControls = queryControlService.findAllAdvanceQueryControl();
			List<DomainVO> domainVOs = new ArrayList<DomainVO>();
			//根据表单ID取出表单按钮集合
			Form tform = formService.findFormByIdAndDomainVO(formManageId, domainVOs, user);
			this.formButtons = tform.getFormButtons();
		} catch (IOException e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("add:" + e.getMessage());
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("add:" + e.getMessage());
			}
		}
		return "add";
	}

	/* 
	 * 保存列表信息
	 */
	public String save() {
		try {
			
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			Long tabulationId = null;
			// 判断列表名称是否重复
			List list = compexDomainService.findReplyResult("sys_liebiao", "tbl_liebiaomingcheng",
					tabulation.getTabulationName(), tabulation.getId());
			if (list.size() > 0) {
				printJSON("300", "列表名称 不允许重复，请修改后重新提交！", false);
				return NONE;
			}
			// 判断关联的表单是否已经被其他列表引用
			if (tabulation.getFormId() != null && !tabulation.getFormId().equals("")) {
				List _tmplist = tabulationService.useFormCount(tabulation.getFormId(), tabulation.getId());
				if (_tmplist.size() > 0) {
					printJSON("301", "选择的表单已经被其他列表引用，请重新选择！", false);
					return NONE;
				}
			}

			if (tabulation.getId() != null && !tabulation.getId().equals("")) {
				Tabulation _temTabu = tabulationService.findTabulationById(tabulation.getId());
				
				//判断是否添加了操作按钮，如添加了则不可以修改关联的列表组件
				if(!tabulation.getListControlId().equals(_temTabu.getListControlId())){
					ListControl _listControl = listControlService.getListControlById(tabulation.getListControlId());
					ListControl _baselistControl = listControlService.getListControlById(_temTabu.getListControlId());
					if(!_listControl.getOperationId().equals(_baselistControl.getOperationId())){
						if(_listControl.getOperationId()!=null && !_listControl.getOperationId().equals("-1")){
							OperationManage _operationManage = operationManageService.findOperationManageById(_listControl.getOperationId());
							OperationManage _baseOperationManage = operationManageService.findOperationManageById(_baselistControl.getOperationId());
							if(_operationManage.getOptManagerCount()<_baseOperationManage.getOptManagerCount()){
								List _optList = tabulationService.findTabulationOpt(_temTabu.getId());
								if(_optList.size()>0){
									printJSON("failed", "请修改操作列的个数或删除操作按钮(新操作列的个数需大于等于原操作列)！", false,String.valueOf(tabulation.getId()),null);
									return NONE;
								}
							}
						}else{
							List _optList = tabulationService.findTabulationOpt(_temTabu.getId());
							if(_optList.size()>0){
								printJSON("failed", "请删除操作按钮！", false,String.valueOf(tabulation.getId()),null);
								return NONE;
							}
						}
					}
				}
				
				tabulation.setUpdateBy(user.getId());
				tabulation.setUpdateDate(new Date());
				this.tabulationService.doUpdateTabulation(tabulation);
				tabulationId = tabulation.getId();
				// 修改编码表名称
				seqcodeService.doUpdateSeqName(_temTabu.getCode(), tabulation.getTabulationName());
				// 更新列表的扩展码
				Catalog _temCatalog = catalogService.findCatalogByListId(tabulationId);
				if (_temCatalog != null) {
					seqcodeService.doUpdateSeqExtends(_temTabu.getCode(), _temCatalog.getCode());

					Long _temFormId = _temTabu.getFormId();
					if (_temFormId != null && !_temFormId.equals("") && !_temFormId.equals(-1L)) {
						Form _temForm = formService.findFormById(_temFormId);
						// 更新表单编码的上级码
						seqcodeService.doUpdateSeq(_temForm.getCode(), _temCatalog.getCode());
						// 更新表单编码的扩展码
						seqcodeService.doUpdateSeqExtends(_temForm.getCode(), _temCatalog.getCode());
					}
				}
			} else {
				tabulation.setCreateBy(user.getId());
				tabulation.setCreateDate(new Date());
				tabulation.setUpdateBy(user.getId());
				tabulation.setUpdateDate(new Date());
				//保存列表
				tabulationId = this.tabulationService.doSaveTabulation(tabulation);
				Tabulation t = tabulationService.findTabulationById(tabulationId);
				Form form = formService.findFormById(t.getFormId());
				t.setTabulationPath("/pages/resource/"+EncryptUtil.Md5(form.getMainTable()!=null?form.getMainTable():"")+"compexlist.action?listId=" + tabulationId);
				//更新列表路径
				this.tabulationService.doUpdateTabulationPath(t);

				Seqcode seqcode = new Seqcode();
				seqcode.setTblName(tabulation.getTabulationName());
				seqcode.setTblSystematom(Constants.SYSTEM_ATOM_TABN);
				//列表生成编码
				String bianma = seqcodeService.doSaveSeqcode(seqcode);
				List<Seqcode> _seqlist = seqcodeService.querySeqByValue(bianma);
				Seqcode _tmpseqcode = null;
				if(_seqlist.size()>0){
					_tmpseqcode = _seqlist.get(0);
					userService.addOrUpdateUserSeq(getRequest().getSession(), _tmpseqcode);
				}
				//更新列表中的编码
				compexDomainService.doUpdateCode("sys_liebiao", "tbl_bianma", tabulationId, bianma);
			}
			printJSON("success", false, String.valueOf(tabulationId));
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description: 保存列表详细信息
	 * @return
	 * @throws IOException
	 */
	public String saveDetails() throws IOException {
		this.tabulationService.doUpdateTabulationDetails(tabulation);
		printJSON("success", false);
		return NONE;
	}

	/* 
	 * 删除列表
	 */
	public String delete() throws IOException {
		try {
			if (selectedIDs != null) {
				this.tabulationService.doDeleteTabulations(selectedIDs);
			}
			printJSON("success");
		} catch (AppException e) {
			printJSON("300", e.getMessage(), false);
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("delete:" + e.getMessage());
			}
		} catch (Exception e) {
			printJSON("fail");
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("delete:" + e.getMessage());
			}
		}
		return NONE;
	}
	
	/* 
	 * 删除列表
	 */
	public String logicDelete() throws IOException {
		try {
			if (selectedIDs != null) {
				this.tabulationService.doLogicDeleteTabulations(selectedIDs);
			}
			printJSON("success");
		} catch (AppException e) {
			printJSON("300", e.getMessage(), false);
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("delete:" + e.getMessage());
			}
		} catch (Exception e) {
			printJSON("fail");
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("delete:" + e.getMessage());
			}
		}
		return NONE;
	}

	/* 
	 * 查看列表信息
	 */
	public String view() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		this.tabulation = tabulationService.findTabulationById(tabulationId);
		if (this.tabulation.getListControlId() != null) {//列表组件不为空时
			tabulation.setListControl(listControlService.getListControlById(this.tabulation
					.getListControlId()));
		}
		if (this.tabulation.getQueryControlId() != null) {//查询组件不为空时
			tabulation.setQueryControl(queryControlService.getQueryControlById(String.valueOf(this.tabulation
					.getQueryControlId())));
		}
		if (this.tabulation.getAdvanceQueryControlId() != null) {//高级查询组件不为空时
			if (this.tabulation.getAdvanceQueryControlId().equals("-1")) {
				AdvanceQueryControl aqc = new AdvanceQueryControl();
				aqc.setId(-1l);
				aqc.setQueryControlName("无");
				tabulation.setAdvanceQueryControl(aqc);
			} else {
				tabulation.setAdvanceQueryControl(queryControlService.getAdvanceQueryControlById(this.tabulation.getAdvanceQueryControlId()));
			}

		}

		queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		//取出所属表集合
		this.tables = tableService.queryTable(queryCriteria);
		//取出表单集合
		this.forms = formService.queryForm(queryCriteria);
		// this.templateLists=templateListService.queryTemplateList(queryCriteria);
		queryCriteria.addQueryCondition("tbl_type", "0");
		queryCriteria.addQueryCondition("tbl_templatetype", "2");
		//取出列表模板集合
		this.templateLists = templateService.queryTemplate(queryCriteria);
		//取出列表组件集合
		this.listControls = listControlService.findAllListControl();
		//取出查询组件集合
		this.queryControls = queryControlService.findAllQueryControl();
		//取出高级查询组件集合
		this.advanceQueryControls = queryControlService.findAllAdvanceQueryControl();
		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		//取出表单按钮
		Form tform = formService.findFormByIdAndDomainVO(formManageId, domainVOs, user);
		this.formButtons = tform.getFormButtons();
		return "view";
	}

	public String edit() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		this.tabulation = tabulationService.findTabulationById(tabulationId);
		queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		//取出所属表集合
		this.tables = tableService.queryTable(queryCriteria);
		//取出表单集合
		this.forms = formService.queryForm(queryCriteria);
		// this.templateLists=templateListService.queryTemplateList(queryCriteria);
		queryCriteria.addQueryCondition("tbl_type", "0");
		queryCriteria.addQueryCondition("tbl_templatetype", "2");
		//取出列表模板集合
		this.templateLists = templateService.queryTemplate(queryCriteria);
		//取出列表组件集合
		this.listControls = listControlService.findAllListControl();
		//取出查询组件集合
		this.queryControls = queryControlService.findAllQueryControl();
		//取出高级查询组件集合
		this.advanceQueryControls = queryControlService.findAllAdvanceQueryControl();
		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		//取出表单按钮
		Form tform = formService.findFormByIdAndDomainVO(formManageId, domainVOs, user);
		this.formButtons = tform.getFormButtons();
		return "edit";
	}

	/**
	 * Description:加载列表字段表单页面
	 * @return forward
	 * @throws Exception
	 */
	public String addColumn() throws Exception {
		this.tabulationColumn = new TabulationColumn();
		this.tabulation = tabulationService.findTabulationById(tabulationId);

		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		qc.addQueryCondition("tbl_tableId", this.tabulation.getTableId());
		this.columns = columnService.queryColumn(qc);

		return "addColumn";
	}

	/**
	 * Description:加载列表详细信息表单页面
	 * @return forward
	 * @throws Exception
	 */
	public String addDetails() throws Exception {
		this.tabulation = tabulationService.findTabulationById(tabulationId);
		this.listControls = listControlService.findAllListControl();
		return "addDetails";
	}

	/**
	 * Description:显示列表字段列表
	 * @return forward
	 */
	public String listColumn() {
		this.numPerPage = 10;
		queryCriteria = new QueryCriteria();
		queryCriteria.addQueryCondition("tbl_tabulation", tabulationId);
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1) * this.numPerPage + 1);
		queryCriteria.setPageSize(this.numPerPage);
		List tabulationColumns = tabulationService.queryTabulationColumn(queryCriteria);
		this.pageResult = new PageResult();
		this.pageResult.setContent(tabulationColumns);
		this.pageResult.setTotalCount(tabulationService.countTabulationColumn(queryCriteria));
		this.pageResult.setPageSize(this.numPerPage);
		this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
		this.pageResult.setCountPage((tabulationColumns.size() + this.numPerPage - 1) / this.numPerPage);
		return "listColumn";
	}

	/**
	 * Description:保存列表字段
	 * @return NONE
	 * @throws IOException
	 */
	public String saveColumn() throws IOException {
		this.tabulation = tabulationService.findTabulationById(tabulationColumn.getTableId());
		Table table = tableService.findTableById(tabulation.getTableId());
		tabulationColumn.setBelongTable(table.getTableName());
		tabulationColumn.setTableId(table.getId());
		tabulationColumn.setListId(tabulationId);
		try {
			if (tabulationColumn.getId() != null && !tabulationColumn.getId().equals("")) {
				this.tabulationService.doUpdateTabulationColumn(tabulationColumn);
			} else {
				this.tabulationService.doSaveTabulationColumn(tabulationColumn);
			}
			printJSON("success", false);
		} catch (Exception e) {
			printJSON("fail", false);
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("saveColumn:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:删除列表字段
	 * @return NONE
	 * @throws IOException
	 */
	public String deleteColumn() throws IOException {
		try {
			if (selectedIDs != null) {
				this.tabulationService.doDeleteTabulationColumns(selectedIDs);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("deleteColumn:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:编辑列表字段
	 * @return NONE
	 * @throws Exception
	 */
	public String editColumn() throws Exception {
		this.tabulation = tabulationService.findTabulationById(tabulationId);
		this.tabulationColumn = tabulationService.findTabulationColumnById(tabulationColumnId);

		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		qc.addQueryCondition("tbl_tableId", this.tabulation.getTableId());
		this.columns = columnService.queryColumn(qc);
		return "editColumn";
	}

	/**
	 * Description:查询字段信息，为列表字段表单下拉框提供json对象数据
	 * @return NONE
	 * @throws IOException
	 */
	public String showColumn() throws IOException {
		QueryCriteria qc = new QueryCriteria();
		qc.addQueryCondition("tbl_tableId", tableId);
		this.columns = columnService.queryColumn(qc);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"tabulationColumn.columnId\" style=\"width:135px\">");
		for (Column column : columns) {
			sb.append("<option value=\"" + column.getId() + "\">" + column.getColumnZhName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:查询字段信息，为列表筛选条件下拉框提供json对象数据
	 * @return NONE
	 * @throws IOException
	 */
	public String showQueryColumn() throws IOException {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		qc.addQueryCondition("tbl_tableId", tableId);
		this.columns = columnService.queryColumn(qc);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"tabulationQuery.columnId\" style=\"width:135px\">");
		for (Column column : columns) {
			sb.append("<option value=\"" + column.getId() + "\">" + column.getColumnZhName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:获得编码
	 * @return NONE
	 * @throws IOException
	 */
	public String getCode() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(code);
		return NONE;
	}

	/**
	 * Description:加载列表筛选条件表单
	 * @return forward
	 * @throws Exception
	 */
	public String addQuery() throws Exception {
		String op =  getRequest().getParameter("op");
		getRequest().setAttribute("op", op);
		
		this.tabulationQuery = new TabulationQuery();
		this.tabulation = tabulationService.findTabulationById(tabulationId);
		Form tabuform = null;
		if (this.tabulation.getFormId() != null) {
			tabuform = formService.findFormById(this.tabulation.getFormId());
		}
		QueryCriteria qc = new QueryCriteria();
		if (tabuform != null) {
			qc.addQueryCondition("tbl_tableName", tabuform.getTableName());
		}
		qc.setPageSize(-1);
		this.tables = tableService.queryTable(qc);
		if (tables != null && tables.size() > 0) {
			QueryCriteria colqc = new QueryCriteria();
			colqc.addQueryCondition("tbl_tableId", tables.get(0).getId());
			colqc.setPageSize(-1);
			this.columns = columnService.queryColumn(colqc);
		}
		return "addQuery";
	}

	/**
	 * Description:显示列表筛选条件列表
	 * @return forward
	 */
	public String listQuery() {
		String op =  getRequest().getParameter("op");
		getRequest().setAttribute("op", op);
		
		this.numPerPage = 10;
		queryCriteria = new QueryCriteria();
		queryCriteria.addQueryCondition("tbl_tabulation", tabulationId);
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1) * this.numPerPage + 1);
		queryCriteria.setPageSize(this.numPerPage);
		List tabulationQuerys = tabulationService.queryTabulationQuery(queryCriteria);
		this.pageResult = new PageResult();
		this.pageResult.setContent(tabulationQuerys);
		this.pageResult.setTotalCount(tabulationService.countTabulationQuery(queryCriteria));
		this.pageResult.setPageSize(this.numPerPage);
		this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
		this.pageResult.setCountPage((tabulationQuerys.size() + this.numPerPage - 1) / this.numPerPage);
		return "listQuery";
	}

	/**
	 * Description:保存列表筛选条件
	 * @return NONE
	 * @throws IOException
	 */
	public String saveQuery() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON("userFail", false);
			return NONE;
		}

		tabulationQuery.setTabulationId(tabulationId);
		try {
			if (tabulationQuery.getId() != null && !tabulationQuery.getId().equals("")) {
				this.tabulationService.doUpdateTabulationQuery(tabulationQuery);
			} else {
				this.tabulationService.doSaveTabulationQuery(tabulationQuery);
			}
			// 更新主记录的修改人和修改时间
			compexDomainService.doUpdateMainInfo("sys_liebiao", tabulationId, user);
			printJSON("success", false);
		} catch (Exception e) {
			printJSON("fail", false);
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("saveQuery:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:删除列表筛选条件
	 * @return NONE
	 * @throws IOException
	 */
	public String deleteQuery() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON("userFail", false);
			return NONE;
		}

		try {
			if (selectedSubIDs != null) {
				this.tabulationService.doDeleteTabulationQuerys(selectedSubIDs);
				// 更新主记录的修改人和修改时间
				compexDomainService.doUpdateMainInfo("sys_liebiao", tabulationId, user);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("deleteQuery:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:编辑列表筛选条件
	 * @return forward
	 * @throws Exception
	 */
	public String editQuery() throws Exception {
		this.tabulation = tabulationService.findTabulationById(tabulationId);
		this.tabulationQuery = tabulationService.findTabulationQueryById(tabulationQueryId);

		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		this.tables = tableService.queryTable(qc);
		qc.addQueryCondition("tbl_tableId", this.tabulationQuery.getTableId());
		this.columns = columnService.queryColumn(qc);
		return "editQuery";
	}

	/**
	 * Description:加载列表操作按钮表单
	 * @return forward
	 * @throws Exception
	 */
	public String addOpt() throws Exception {
		String op =  getRequest().getParameter("op");
		getRequest().setAttribute("op", op);
		
		this.tabulationOpt = new TabulationOpt();
		this.tabulation = tabulationService.findTabulationById(tabulationId);

		QueryCriteria qc = new QueryCriteria();
		qc.addQueryCondition("tbl_buttonType", 4);
		qc.setPageSize(-1);

		List<Button> buttons = new ArrayList<Button>();
		buttons = buttonService.queryButton(qc);
		getRequest().setAttribute("buttons", buttons);

		return "addOpt";
	}

	/**
	 * Description:显示列表操作按钮列表
	 * @return forward
	 * @throws IOException 
	 */
	public String listOpt() throws IOException {
		try {
			String op =  getRequest().getParameter("op");
			getRequest().setAttribute("op", op);
			
			Tabulation _tabulation = this.tabulationService.findTabulationById(tabulationId);
			ListControl _listControl = listControlService.getListControlById(_tabulation.getListControlId());
			OperationManage _operationManage = operationManageService.findOperationManageById(_listControl.getOperationId());
			getRequest().setAttribute("operationManage", _operationManage);
			
			this.numPerPage = 10;
			queryCriteria = new QueryCriteria();
			queryCriteria.addQueryCondition("tbl_tabulation", tabulationId);
			if (this.pageNum == null || "".equals(this.pageNum)) {
				this.pageNum = 1;
			}
			queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1) * this.numPerPage + 1);
			queryCriteria.setPageSize(this.numPerPage);
			List tabulationOpts = tabulationService.queryTabulationOpt(queryCriteria);
			this.pageResult = new PageResult();
			this.pageResult.setContent(tabulationOpts);
			this.pageResult.setTotalCount(tabulationService.countTabulationOpt(queryCriteria));
			this.pageResult.setPageSize(this.numPerPage);
			this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
			this.pageResult.setCountPage((tabulationOpts.size() + this.numPerPage - 1) / this.numPerPage);
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail", false);
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("listOpt:" + e.getMessage());
			}
		}
		return "listOpt";
	}

	/**
	 * Description:保存列表操作按钮
	 * @return NONE
	 * @throws IOException
	 */
	public String saveOpt() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		tabulationOpt.setTabulationId(tabulationId);
		try {
			List list = this.tabulationButtonService.findReplyOptResult(tabulationOpt);
			if (list.size() > 0) {
				HttpServletResponse response = getResponse();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.write("failed");
				out.flush();
				out.close();
				return NONE;
			}
			Long tabulationOptId=null;
			if (tabulationOpt.getId() != null && !tabulationOpt.getId().equals("")) {
				tabulationOptId = tabulationOpt.getId();
				TabulationOpt tb = this.tabulationButtonService.findTabulationOptById(tabulationOpt.getId());
				if (!tb.getButtonId().equals(tabulationOpt.getButtonId())) {
					String bianma = tb.getCode();
					seqcodeService.doUpdateSeqName(bianma, tabulationOpt.getShowName());
					
					// 上级码
					Tabulation _tabulation = tabulationService.findTabulationById(tabulationId);
					seqcodeService.doUpdateSeq(bianma, _tabulation.getCode());
					// 扩展码
					seqcodeService.doUpdateSeqExtends(bianma, _tabulation.getCode());

					useInfoService.doDeleteUseInfo(tabulationOpt.getId(), 4);
					saveUseInfo(tabulationOpt, tabulationId, user);
				}
				this.tabulationService.doUpdateTabulationOpt(tabulationOpt);
			} else {
				tabulationOptId = this.tabulationService.doSaveTabulationOpt(tabulationOpt);

				// 生成编码
				Seqcode seqcode = new Seqcode();

				//Button _button = buttonService.findByID(tabulationOpt.getButtonId());
				seqcode.setTblSystematom(Constants.SYSTEM_ATOM_OPRT_BUTN);
				seqcode.setTblName(tabulationOpt.getShowName());

				String bianma = seqcodeService.doSaveSeqcode(seqcode);
				List<Seqcode> _seqlist = seqcodeService.querySeqByValue(bianma);
				Seqcode _tmpseqcode = null;
				if(_seqlist.size()>0){
					_tmpseqcode = _seqlist.get(0);
					userService.addOrUpdateUserSeq(getRequest().getSession(), _tmpseqcode);
				}
				compexDomainService.doUpdateCode("sys_oprtbutton", "tbl_bianma", tabulationOptId,
						bianma);

				// 上级码
				Tabulation _tabulation = this.tabulationService.findTabulationById(tabulationId);
				seqcodeService.doUpdateSeq(bianma, _tabulation.getCode());
				// 扩展码
				seqcodeService.doUpdateSeqExtends(bianma, _tabulation.getCode());

				tabulationOpt.setId(Long.valueOf(tabulationOptId));
				saveUseInfo(tabulationOpt, tabulationId, user);
			}
			//若列表按钮显示顺序相同，则大于此顺序的都自动加1
			tabulationService.doUpdateOptOrder(tabulationOpt,tabulationOptId);

			// 更新主记录的修改人和修改时间
			compexDomainService.doUpdateMainInfo("sys_liebiao", tabulationId, user);
			printJSON("success", false);
		} catch (Exception e) {
			printJSON("fail", false);
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("saveOpt:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:保存列表操作按钮的使用信息
	 * @param tabulationOpt 列表操作按钮
	 * @param tabulationId 列表ID
	 * @param user 用户信息
	 * @throws Exception
	 */
	public void saveUseInfo(TabulationOpt tabulationOpt, Long tabulationId, SysUser user) throws Exception {
		Tabulation tabulation = tabulationService.findTabulationById(tabulationId);
		UseInfo useInfo = new UseInfo();
		useInfo.setRelationId(String.valueOf(tabulationOpt.getId()));
		useInfo.setBizId(tabulation.getId());
		useInfo.setCompId(tabulationOpt.getButtonId());
		useInfo.setBizName(tabulation.getTabulationName());
		Button button = buttonService.findByID(tabulationOpt.getButtonId());
		useInfo.setCompName(button.getButtonName() + "按钮");
		useInfo.setCode(button.getButtonCode());
		useInfo.setType("列表操作按钮");
		useInfo.setTypeId(4);
		useInfo.setCreateBy(user.getId());
		useInfo.setCreateDate(new Date());
		useInfoService.doSaveUseInfo(useInfo);
	}

	/**
	 * Description:删除列表操作按钮
	 * @return NONE
	 * @throws IOException
	 */
	public String deleteOpt() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		try {
			if (selectedSubIDs != null) {
				this.tabulationService.doDeleteTabulationOpts(selectedSubIDs);
				// 更新主记录的修改人和修改时间
				compexDomainService.doUpdateMainInfo("sys_liebiao", tabulationId, user);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("deleteOpt:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:编辑列表操作按钮
	 * @return forward
	 * @throws Exception
	 */
	public String editOpt() throws Exception {
		String op =  getRequest().getParameter("op");
		getRequest().setAttribute("op", op);
		String marking = getRequest().getParameter("marking");
		getRequest().setAttribute("marking", marking);
		this.tabulation = tabulationService.findTabulationById(tabulationId);
		this.tabulationOpt = tabulationService.findTabulationOptById(tabulationOptId);
		this.tabulationOpt.setButton(buttonService.findByID(Long.valueOf(this.tabulationOpt.getButtonId())));
		QueryCriteria qc = new QueryCriteria();
		qc.addQueryCondition("tbl_buttonType", 4);
		qc.setPageSize(-1);
		List<Button> buttons = new ArrayList<Button>();
		buttons = buttonService.queryButton(qc);
		getRequest().setAttribute("buttons", buttons);
		return "editOpt";
	}
	
	public String findOptCount() throws Exception{
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		Tabulation _tabulation = tabulationService.findTabulationById(tabulationId);
		ListControl _listControl = listControlService.getListControlById(_tabulation.getListControlId());
		OperationManage operationManage = operationManageService.findOperationManageById(_listControl.getOperationId());
		PrintWriter out = response.getWriter();
		out.write(String.valueOf(operationManage.getOptManagerCount()));
		out.flush();
		out.close();
		return NONE;
	}

	public String showFormHelp() {
		return "formhelp";
	}
	
	public TabulationColumn getTabulationColumn() {
		return tabulationColumn;
	}

	public Long getTabulationId() {
		return tabulationId;
	}

	public void setTabulationId(Long tabulationId) {
		this.tabulationId = tabulationId;
	}

	public void setTabulationQueryId(Long tabulationQueryId) {
		this.tabulationQueryId = tabulationQueryId;
	}

	public void setTabulationOptId(Long tabulationOptId) {
		this.tabulationOptId = tabulationOptId;
	}

	public void setTabulationColumn(TabulationColumn tabulationColumn) {
		this.tabulationColumn = tabulationColumn;
	}

	public TabulationQuery getTabulationQuery() {
		return tabulationQuery;
	}

	public void setTabulationQuery(TabulationQuery tabulationQuery) {
		this.tabulationQuery = tabulationQuery;
	}

}
