package com.cloudstong.platform.resource.form.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.core.util.EncryptUtil;
import com.cloudstong.platform.resource.autocomplete.service.AutoCompleteService;
import com.cloudstong.platform.resource.button.model.Button;
import com.cloudstong.platform.resource.button.service.ButtonService;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;
import com.cloudstong.platform.resource.buttongroup.service.ButtonGroupService;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.checkbox.service.CheckBoxMgtService;
import com.cloudstong.platform.resource.checkbox.vo.CheckBoxMgtVO;
import com.cloudstong.platform.resource.codecasecade.model.CodeCaseCade;
import com.cloudstong.platform.resource.codecasecade.service.CodeCaseCadeService;
import com.cloudstong.platform.resource.combox.model.Combox;
import com.cloudstong.platform.resource.combox.service.ComboxService;
import com.cloudstong.platform.resource.date.model.DateControl;
import com.cloudstong.platform.resource.date.service.DateService;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.editor.model.TextEditor;
import com.cloudstong.platform.resource.editor.service.TextEditorService;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.resource.form.model.Tab;
import com.cloudstong.platform.resource.form.service.FormButtonService;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.resource.form.service.TabService;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.service.ColumnService;
import com.cloudstong.platform.resource.metadata.service.TableService;
import com.cloudstong.platform.resource.passbox.service.PassboxService;
import com.cloudstong.platform.resource.radio.service.RadioMgtService;
import com.cloudstong.platform.resource.radio.vo.RadioMgtVO;
import com.cloudstong.platform.resource.searchcombox.model.SearchCombox;
import com.cloudstong.platform.resource.searchcombox.service.SearchComboxService;
import com.cloudstong.platform.resource.seqcode.service.SeqcodeService;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.resource.tabulation.service.TabulationButtonService;
import com.cloudstong.platform.resource.tabulation.service.TabulationService;
import com.cloudstong.platform.resource.template.model.Partition;
import com.cloudstong.platform.resource.template.model.Template;
import com.cloudstong.platform.resource.template.service.PartitionService;
import com.cloudstong.platform.resource.template.service.TemplateService;
import com.cloudstong.platform.resource.textarea.model.Textarea;
import com.cloudstong.platform.resource.textarea.service.TextareaService;
import com.cloudstong.platform.resource.textbox.service.TextBoxService;
import com.cloudstong.platform.resource.tree.model.MgrTree;
import com.cloudstong.platform.resource.tree.service.MgrTreeService;
import com.cloudstong.platform.resource.uploadbox.service.UploadBoxService;
import com.cloudstong.platform.resource.uploadify.model.Uploadify;
import com.cloudstong.platform.resource.uploadify.service.UploadifyService;
import com.cloudstong.platform.resource.useinfo.model.Component;
import com.cloudstong.platform.resource.useinfo.model.UseInfo;
import com.cloudstong.platform.resource.useinfo.service.UseInfoService;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.IPrivilegeService;
import com.cloudstong.platform.third.office.model.Office;
import com.cloudstong.platform.third.office.service.OfficeService;

/**
 * @author michael Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- ------- ----------------------------------------------------
 * 
 *         Description:表单Action
 */
@SuppressWarnings("serial")
@ParentPackage("default")
@Namespace("/pages/resource/form")
@Results(value = { 
		@Result(name="add", location = "/WEB-INF/view/resource/form/formEdit.jsp"),
		@Result(name="edit", location = "/WEB-INF/view/resource/form/formEdit.jsp"),
		@Result(name="formhelp", location = "/WEB-INF/view/resource/form/formHelp.jsp"),
		@Result(name="list", location = "/WEB-INF/view/resource/form/compexFormList.jsp"),
		@Result(name="input", location = "/WEB-INF/view/resource/form/formEdit.jsp"),
		@Result(name="view", location = "/WEB-INF/view/resource/form/formView.jsp"),
		@Result(name="search", location = "/WEB-INF/view/resource/form/formSearch.jsp"),
		@Result(name="addColumn", location = "/WEB-INF/view/resource/form/formColumnEdit.jsp"),
		@Result(name="editColumn", location = "/WEB-INF/view/resource/form/formColumnEdit.jsp"),
		@Result(name="listColumn", location = "/WEB-INF/view/resource/form/formColumnList.jsp"),
		@Result(name="simpleListColumn", location = "/WEB-INF/view/resource/form/formColumnSimpleList.jsp"),
		@Result(name="loadoperation", location = "/WEB-INF/view/resource/form/formOperation.jsp"),
		@Result(name="loadnolist", location = "/WEB-INF/view/resource/form/formNoList.jsp"),
		@Result(name="loadhaslist", location = "/WEB-INF/view/resource/form/formHasList.jsp"),
		@Result(name="detailtextbox", location = "/WEB-INF/view/resource/form/sub/detailsTextBox.jsp"),
		@Result(name="detailPassbox", location = "/WEB-INF/view/resource/form/sub/detailsPassbox.jsp"),
		@Result(name="detailsComBox", location = "/WEB-INF/view/resource/form/sub/detailsComBox.jsp"),
		@Result(name="detailsTextArea", location = "/WEB-INF/view/resource/form/sub/detailsTextArea.jsp"),
		@Result(name="detailsRadio", location = "/WEB-INF/view/resource/form/sub/detailsRadio.jsp"),
		@Result(name="detailsCheckBox", location = "/WEB-INF/view/resource/form/sub/detailsCheckBox.jsp"),
		@Result(name="detailsUploadFile", location = "/WEB-INF/view/resource/form/sub/detailsUploadFile.jsp"),
		@Result(name="detailsDateControl", location = "/WEB-INF/view/resource/form/sub/detailsDateControl.jsp"),
		@Result(name="detailsTree", location = "/WEB-INF/view/resource/form/sub/detailsTree.jsp"),
		@Result(name="detailsSearchComBox", location = "/WEB-INF/view/resource/form/sub/detailsSearchComBox.jsp"),
		@Result(name="detailsCustom", location = "/WEB-INF/view/resource/form/sub/detailsCustom.jsp"),
		@Result(name="detailsTextEditor", location = "/WEB-INF/view/resource/form/sub/detailsTextEditor.jsp"),
		@Result(name="detailAutoComplete", location = "/WEB-INF/view/resource/form/sub/detailsAutoComplete.jsp"),
		@Result(name="office", location = "/WEB-INF/view/resource/form/sub/detailsOffice.jsp"),
		@Result(name="codecasecade", location = "/WEB-INF/view/resource/form/sub/detailsCodeCaseCade.jsp"),
		@Result(name="uploadify", location = "/WEB-INF/view/resource/form/sub/detailsUploadify.jsp")
})
public class FormAction extends CompexDomainAction {

	/**
	 * 表单字段
	 */
	private FormColumn formColumn;
	/**
	 * 表单ID
	 */
	private Long formId;
	/**
	 * 选项卡ID
	 */
	private Long tabId = -1l;
	/**
	 * 分区ID
	 */
	private Long partitionId = -1l;
	/**
	 * 表单ID
	 */
	private Long id;
	/**
	 * 所属表Id
	 */
	private Long tableId;

	/**
	 * 关联字段
	 */
	private Long relColumnId;

	/**
	 * 分区
	 */
	private Partition partition;
	/**
	 * 所属表集合
	 */
	private List<Table> tables;
	/**
	 * 字段集合
	 */
	private List<Column> columns;
	/**
	 * 文本框，下拉框，搜索下拉框,文本域，密码框，上传文件框的父类集合
	 */
	private List<Component> components;
	/**
	 * 录入类型代码集合
	 */
	private List<Dictionary> inputTypeList;
	/**
	 * 校验代码集合
	 */
	private List<Dictionary> validates;
	/**
	 * 选项卡集合
	 */
	private List<Tab> tabs;
	/**
	 * 日期组件集合
	 */
	private List<DateControl> dates;
	/**
	 * 单选框集合
	 */
	private List<RadioMgtVO> radios;
	/**
	 * 复选框集合
	 */
	private List<CheckBoxMgtVO> checkBoxes;
	/**
	 * 树模板集合
	 */
	private List<MgrTree> mgrTrees;
	/**
	 * 文本编辑器集合
	 */
	private List<TextEditor> textEditors;
	/**
	 * 字处理组件集合
	 */
	private List<Office> offices;
	/**
	 * 代码级联组件集合
	 */
	private List<CodeCaseCade> codeCaseCades;
	/**
	 * 多文件上传组件集合
	 */
	private List<Uploadify> uploadifys;
	/**
	 * 录入类型
	 */
	private Integer currentInputType;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 表单Id
	 */
	private Long formManageId;
	/**
	 * 父节点ID
	 */
	private Long parentNodeID;
	/**
	 * 下拉框ID
	 */
	private String comBoxId;
	/**
	 * 日期组件ID
	 */
	private String dateId;
	/**
	 * 单选框ID
	 */
	private String radioId;
	/**
	 * 复选框ID
	 */
	private String checkBoxId;
	/**
	 * 搜索下拉框ID
	 */
	private String searchComBoxId;
	/**
	 * 树ID
	 */
	private String treeId;
	/**
	 * 文本编辑器ID
	 */
	private String editorId;

	/**
	 * 文本域ID
	 */
	private String textFieldId;
	/**
	 * 所属表
	 */
	private String table;
	/**
	 * 字段
	 */
	private String column;

	/**
	 * 选项卡ID
	 */
	private Long formColumnTabId;
	/**
	 * 分区ID
	 */
	private Long formColumnPartitionId;
	/**
	 * 临时分区ID
	 */
	private Long partitionTempId;

	/**
	 * 操作表单的服务接口,<code>formService</code> 对象是FormService接口的一个实例
	 */
	@Resource
	private FormService formService;
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
	 * 操作代码的服务接口,<code>dictionaryService</code> 对象是DictionaryService接口的一个实例
	 */
	@Resource
	private DictionaryService dictionaryService;
	/**
	 * 操作文本框的服务接口,<code>textBoxService</code> 对象是TextBoxService接口的一个实例
	 */
	@Resource
	private TextBoxService textBoxService;
	/**
	 * 操作自动补齐文本框的服务接口,<code>autoCompleteService</code> 对象是AutoCompleteService接口的一个实例
	 */
	@Resource
	private AutoCompleteService autoCompleteService;
	/**
	 * 操作下拉框的服务接口,<code>comboxService</code> 对象是ComboxService接口的一个实例
	 */
	@Resource
	private ComboxService comboxService;
	/**
	 * 操作搜索下拉框的服务接口,<code>searchComboxService</code> 对象是SearchComboxService接口的一个实例
	 */
	@Resource
	private SearchComboxService searchComboxService;
	/**
	 * 操作文本域的服务接口,<code>textareaService</code> 对象是TextareaService接口的一个实例
	 */
	@Resource
	private TextareaService textareaService;
	/**
	 * 操作密码框的服务接口,<code>passboxService</code> 对象是PassboxService接口的一个实例
	 */
	@Resource
	private PassboxService passboxService;
	/**
	 * 操作上传文件框的服务接口,<code>uploadBoxService</code> 对象是UploadBoxService接口的一个实例
	 */
	@Resource
	private UploadBoxService uploadBoxService;
	/**
	 * 操作选项卡的服务接口,<code>tabService</code> 对象是TabService接口的一个实例
	 */
	@Resource
	private TabService tabService;
	/**
	 * 操作日期组件的服务接口,<code>dateService</code> 对象是DateService接口的一个实例
	 */
	@Resource
	private DateService dateService;
	/**
	 * 操作单选框的服务接口,<code>radioMgtService</code> 对象是RadioMgtService接口的一个实例
	 */
	@Resource
	private RadioMgtService radioMgtService;
	/**
	 * 操作复选框的服务接口,<code>checkBoxMgtService</code> 对象是CheckBoxMgtService接口的一个实例
	 */
	@Resource
	private CheckBoxMgtService checkBoxMgtService;
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
	 * 操作编码的服务接口,<code>seqcodeService</code> 对象是SeqcodeService接口的一个实例
	 */
	@Resource
	private SeqcodeService seqcodeService;
	/**
	 * 操作列表的服务接口,<code>tabulationService</code> 对象是TabulationService接口的一个实例
	 */
	@Resource
	private TabulationService tabulationService;
	/**
	 * 操作列表按钮的服务接口,<code>tabulationButtonService</code> 对象是TabulationButtonService接口的一个实例
	 */
	@Resource
	private TabulationButtonService tabulationButtonService;
	/**
	 * 操作树模板的服务接口,<code>mgrTreeService</code> 对象是MgrTreeService接口的一个实例
	 */
	@Resource
	private MgrTreeService mgrTreeService;
	/**
	 * 操作文本编辑器的服务接口,<code>textEditorService</code> 对象是TextEditorService接口的一个实例
	 */
	@Resource
	private TextEditorService textEditorService;
	/**
	 * 操作字处理组件的服务接口,<code>officeService</code> 对象是OfficeService接口的一个实例
	 */
	@Resource
	private OfficeService officeService;
	/**
	 * 操作代码级联组件的服务接口,<code>codeCaseCadeService</code> 对象是CodeCaseCadeService接口的一个实例
	 */
	@Resource
	private CodeCaseCadeService codeCaseCadeService;
	/**
	 * 操作多文件上传组件的服务接口,<code>uploadifyService</code> 对象是UploadifyService接口的一个实例
	 */
	@Resource
	private UploadifyService uploadifyService;
	/**
	 * 操作用户使用信息的服务接口,<code>useInfoService</code> 对象是UseInfoService接口的一个实例
	 */
	@Resource
	private UseInfoService useInfoService;
	/**
	 * 操作表单按钮的服务接口,<code>formButtonService</code> 对象是FormButtonService接口的一个实例
	 */
	@Resource
	private FormButtonService formButtonService;
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

	@Resource
	private IPrivilegeService privilegeService;

	@Action("list")
	public String list() {// 显示表单列表
		super.list();
		this.formManageId = tabulation.getFormId();
		return "list";
	}

	/**
	 * Description:显示表单字段列表
	 * @return forward
	 */
	@Action("listColumn")
	public String listColumn() {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		String op = getRequest().getParameter("op");
		getRequest().setAttribute("op", op);

		this.form = formService.findFormById(formId);
		this.partition = partitionService.findPartitionById(partitionId);
		queryCriteria = new QueryCriteria();
		queryCriteria.addQueryCondition("tbl_form", formId);
		queryCriteria.addQueryCondition("tbl_tabid", tabId);
		queryCriteria.addQueryCondition("tbl_partitionid", partitionId);
		queryCriteria.addQueryCondition("tbl_tableid", tableId);

		// 列表和表单分成两个页面
		String forward = "listColumn";
		if (this.partition != null && this.partition.getPartitionType() != null && this.partition.getPartitionType() == 1) {
			forward = "simpleListColumn";
		}

		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1) * this.numPerPage + 1);
		this.numPerPage = 20;
		queryCriteria.setPageSize(this.numPerPage);
		try {
			List formColumns = formService.queryFormColumn(queryCriteria);
			this.pageResult = new PageResult();
			this.pageResult.setContent(formColumns);
			this.pageResult.setTotalCount(formService.countFormColumn(queryCriteria));
			this.pageResult.setPageSize(this.numPerPage);
			this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
			this.pageResult.setCountPage((formColumns.size() + this.numPerPage - 1) / this.numPerPage);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("listColumn:" + e.getMessage());
			}
		}
		inputTypeList = dictionaryService.queryByParent(10117610301L);

		QueryCriteria qc = new QueryCriteria();
		qc.addQueryCondition("tbl_tableId", tableId);
		qc.setPageSize(-1);
		this.columns = columnService.queryColumn(qc);
		return forward;
	}

	@Action("search")
	public String search() {
		return "search";
	}

	@Action("view")
	public String view() throws Exception {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			this.form = formService.findFormById(id);
			List<DomainVO> domainVOs = new ArrayList<DomainVO>();
			Form tform = formService.findFormByIdAndDomainVO(formManageId, domainVOs, user);
			this.formButtons = tform.getFormButtons();
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("view:" + e.getMessage());
			}
		}
		return "view";
	}

	@Action("add")
	public String add() {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			this.form = new Form();
			QueryCriteria qc = new QueryCriteria();
			qc.setPageSize(-1);
			this.tables = tableService.queryTable(qc);
			List<DomainVO> domainVOs = new ArrayList<DomainVO>();
			Form tform = formService.findFormByIdAndDomainVO(formManageId, domainVOs, user);
			this.formButtons = tform.getFormButtons();
		} catch (IOException e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("add" + e.getMessage());
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("add:" + e.getMessage());
			}
		}
		return "add";
	}

	/**
	 * Description:加载表单字段表单页面
	 * @return NONE
	 * @throws Exception
	 */
	@Action("addColumn")
	public String addColumn() throws Exception {
		HttpServletRequest request = getRequest();
		SysUser user = (SysUser) request.getSession().getAttribute("user");

		String op = getRequest().getParameter("op");
		getRequest().setAttribute("op", op);

		this.form = formService.findFormById(formId);
		this.formColumn = new FormColumn();

		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		// 查询主表以及所有的从表
		if (formId != null && !formId.equals("")) {
			this.tables = tableService.findTables(String.valueOf(this.form.getTableId()));
		} else {
			this.tables = tableService.queryTable(qc);
		}
		if (tables != null && tables.size() > 0) {
			for (int i = 0; i < tables.size(); i++) {
				if (formId != null && !formId.equals("")) {
					if (tables.get(i).getTableName().equals(this.form.getTableName())) {
						qc.addQueryCondition("tbl_tableId", ((Table) tables.get(i)).getId());
						this.formColumn.setTableId(((Table) tables.get(i)).getId());
						break;
					}
				}
			}
			this.columns = columnService.queryColumn(qc);
		}

		inputTypeList = dictionaryService.queryByParent(10117610301L);
		validates = dictionaryService.queryByParent(492175832L);

		QueryCriteria tabQc = new QueryCriteria();
		tabQc.addQueryCondition("tbl_form", formId);
		tabQc.setPageSize(-1);
		try {
			tabs = tabService.queryTab(tabQc);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("addColumn:" + e.getMessage());
			}
		}

		return "addColumn";
	}

	@Action("edit")
	public String edit() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		String op = getRequest().getParameter("op");
		getRequest().setAttribute("op", op);

		this.form = formService.findFormById(id);
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		this.tables = tableService.queryTable(qc);
		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		Form tform = formService.findFormByIdAndDomainVO(formManageId, domainVOs, user);
		this.formButtons = tform.getFormButtons();
		return "edit";
	}

	/**
	 * Description:编辑表单字段
	 * @return forward
	 * @throws Exception
	 */
	@Action("editColumn")
	public String editColumn() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		this.formColumn = formService.findFormColumnById(id);
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		this.tables = tableService.queryTable(qc);
		if (tables != null && tables.size() > 0) {
			qc.addQueryCondition("tbl_tableId", this.formColumn.getTableId());
			this.columns = columnService.queryColumn(qc);
		}

		inputTypeList = dictionaryService.queryByParent(10117610301L);
		validates = dictionaryService.queryByParent(492175832L);

		QueryCriteria tabQc = new QueryCriteria();
		tabQc.addQueryCondition("tbl_form", formId);
		tabQc.setPageSize(-1);
		tabs = tabService.queryTab(tabQc);

		return "editColumn";
	}

	/**
	 * Description:显示所属表字段
	 * @return NONE
	 * @throws IOException
	 */
	@Action("showColumn")
	public String showColumn() throws IOException {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		qc.addQueryCondition("tbl_tableId", id);
		this.columns = columnService.queryColumn(qc);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"formColumn.columnId\" style=\"width:135px\">");
		for (Column column : columns) {
			sb.append("<option value=\"" + column.getId() + "\">" + column.getColumnZhName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	@Action("save")
	public String save() {// 保存表单信息
		try {

			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			Long formId = null;
			// 判断表单名称是否重复
			List list = compexDomainService.findReplyResult("sys_biaodan", "tbl_biaodanming", form.getFormName(), form.getId());
			if (list.size() > 0) {
				printJSON("301", "表单名称不允许重复，请修改后重新提交！", false);
				return NONE;
			}
			if (form.getId() != null && !form.getId().equals("")) {
				Form _form = this.formService.findFormById(form.getId());
				if (!_form.getTableId().equals(form.getTableId())) {
					List _list = this.formService.findFormColumnByFormId(form.getId());
					if (_list.size() > 0) {
						printJSON("failed", "该表单已经添加过字段，若需修改主表，请先删除表单字段！", false, String.valueOf(form.getId()), null);
						return NONE;
					}

				}
				form.setUpdateBy(user.getId());
				form.setUpdateDate(new Date());
				if (form.getTableId() != null) {
					Table table = tableService.findTableById(form.getTableId());
					form.setTableName(table.getTableName());
				}
				this.formService.doUpdateForm(form);
				formId = form.getId();
				// 修改编码表名称
				Form _temForm = formService.findFormById(formId);
				seqcodeService.doUpdateSeqName(_temForm.getCode(), form.getFormName());
			} else {
				form.setCreateBy(user.getId());
				form.setCreateDate(new Date());
				form.setUpdateBy(user.getId());
				form.setUpdateDate(new Date());
				if (form.getTableId() != null) {
					Table table = tableService.findTableById(form.getTableId());
					form.setTableName(table.getTableName());
				}
				formId = this.formService.doSaveForm(form);
				Seqcode seqcode = new Seqcode();
				seqcode.setTblName(form.getFormName());
				seqcode.setTblSystematom(Constants.SYSTEM_ATOM_FORM);
				String bianma = seqcodeService.doSaveSeqcode(seqcode);
				List<Seqcode> _seqlist = seqcodeService.querySeqByValue(bianma);
				Seqcode _tmpseqcode = null;
				if (_seqlist.size() > 0) {
					_tmpseqcode = _seqlist.get(0);
					userService.addOrUpdateUserSeq(getRequest().getSession(), _tmpseqcode);
				}
				compexDomainService.doUpdateCode("sys_biaodan", "tbl_bianma", formId, bianma);
				
				//判断是否需要创建关联的列表和模块
				String createListAndCatalog = getRequest().getParameter("createListAndCatalog");
				if(createListAndCatalog.equals("1")){
					//添加列表
					Tabulation _tabulation = new Tabulation();
					_tabulation.setCreateBy(user.getId());
					_tabulation.setCreateDate(new Date());
					_tabulation.setSystemTeam("sys");
					_tabulation.setTabulationName(form.getFormName().replace("表单", "列表"));
					_tabulation.setListControlId(1L);
					_tabulation.setQueryControlId(1L);
					_tabulation.setFormId(formId);
					Long _tabulationId = tabulationService.doSaveTabulation(_tabulation);
					Tabulation t = tabulationService.findTabulationById(_tabulationId);
					t.setTabulationPath("/pages/resource/"+EncryptUtil.Md5(form.getTableName())+"compexlist.action?listId=" + _tabulationId);
					//更新列表路径
					this.tabulationService.doUpdateTabulationPath(t);
					//添加列表按钮和操作按钮
					addTabulationButton(1338178130578L,"0",_tabulationId,formId,1,"新建","XJ","在列表主界面中点击本按钮即弹出新建界面");
					addTabulationButton(1338430888400L,"0",_tabulationId,formId,2,"删除","PLSC","主列表界面删除按钮");
					addTabulationButton(1338446858775L,"0",_tabulationId,formId,3,"帮助","BZ","点击本按钮则弹出新页面，显示所在模块的使用帮助信息");
					addTabulationOpt("",1,1340071401633L,_tabulationId,"维护","WH","列表维护按钮");

					//添加模块
					Catalog _catalog = new Catalog();
					_catalog.setSystemTeam("sys");
					_catalog.setParentId(290L);
					_catalog.setName(form.getFormName().replace("表单", "模块"));
					_catalog.setTabulationId(_tabulationId);
					_catalog.setPath(t.getTabulationPath());
					_catalog.setCreateBy(user.getId());
					_catalog.setCreateDate(new Date());
					catalogService.doSaveCatalog(_catalog);
				}
			}
			printJSON("success", false, String.valueOf(formId));
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:添加列表操作按钮
	 * 
	 * Steps:
	 * 
	 */
	private void addTabulationOpt(String _name,Integer _order,Long _buttonId,Long _tabulationId,String _showName,String _funcName,String _fcomment) {
		TabulationOpt _tabulationOpt = new TabulationOpt();
		_tabulationOpt.setName(_name);
		_tabulationOpt.setOrder(_order);
		_tabulationOpt.setButtonId(_buttonId);
		_tabulationOpt.setTabulationId(_tabulationId);
		_tabulationOpt.setShowName(_showName);
		_tabulationOpt.setFuncName(_funcName);
		_tabulationOpt.setFcomment(_fcomment);
		tabulationService.doSaveTabulationOpt(_tabulationOpt);
	}

	/**
	 * Description:添加列表按钮
	 * 
	 * Steps:
	 * 
	 */
	private void addTabulationButton(Long _buttonId, String _buttonType, Long _tabulationId, Long _formId, Integer _showOrder, String _showName, String _funcName, String _fcomment) {
		TabulationButton _tabulationButton = new TabulationButton();
		_tabulationButton.setButtonId(_buttonId);
		_tabulationButton.setButtonType(_buttonType);
		_tabulationButton.setListId(_tabulationId);
		_tabulationButton.setFormId(String.valueOf(_formId));
		_tabulationButton.setShowOrder(_showOrder);
		_tabulationButton.setShowName(_showName);
		_tabulationButton.setFuncName(_funcName);
		_tabulationButton.setFcomment(_fcomment);
		tabulationButtonService.doSaveTabulationButton(_tabulationButton);
	}

	/**
	 * Description:保存表单字段信息
	 * @return NONE
	 * @throws IOException
	 */
	@Action("saveColumn")
	public String saveColumn() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		Table table = tableService.findTableById(formColumn.getTableId());
		formColumn.setBelongTable(table.getTableName());
		formColumn.setFormId(formId);
		try {
			if (formColumn.getId() != null && !formColumn.getId().equals("")) {
				FormColumn fc = formService.findFormColumnById(formColumn.getId());
				if (formColumn.getValidate() == null) {
					formColumn.setValidate(fc.getValidate());
				}
				if (formColumn.getDefaultValue() == null) {
					formColumn.setDefaultValue(fc.getDefaultValue());
				}
				if (formColumn.getCompexId() == null) {
					formColumn.setCompexId(fc.getCompexId());
				}
				if (formColumn.getSelectDataType() == null) {
					formColumn.setSelectDataType(fc.getSelectDataType());
				}
				if (formColumn.getCodeParentId() == null) {
					formColumn.setCodeParentId(fc.getCodeParentId());
				}
				if (formColumn.getCodeParentName() == null) {
					formColumn.setCodeParentName(fc.getCodeParentName());
				}
				if (formColumn.getRelationTable() == null) {
					formColumn.setRelationTable(fc.getRelationTable());
				}
				if (formColumn.getRelationColumn() == null) {
					formColumn.setRelationColumn(fc.getRelationColumn());
				}
				if (formColumn.getHasNull() == null) {
					formColumn.setHasNull(fc.getHasNull());
				}
				if (formColumn.getNullName() == null) {
					formColumn.setNullName(fc.getNullName());
				}
				if (fc.getCompexId() != null) {
					if (fc.getCompexId().equals(formColumn.getCompexId()) && fc.getInputType() == formColumn.getInputType()) {

					} else {
						useInfoService.doDeleteUseInfo(formColumn.getId(), 1);
						saveUseInfo(formColumn, formId, user);
					}
				}
				this.formService.doUpdateFormColumn(formColumn);

			} else {
				if (formColumn.getCompexId() == null || formColumn.getCompexId().equals("")) {
					Long compid = this.formService.findDefaultCompex(formColumn.getInputType());
					formColumn.setCompexId(compid);
				}
				Long formColumnId = this.formService.doSaveFormColumn(formColumn);
				formColumn.setId(formColumnId);
				saveUseInfo(formColumn, formId, user);
			}
			printJSON("success", false);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("saveColumn:" + e.getMessage());
			}
			printJSON("fail", false);
		}
		return NONE;
	}

	/**
	 * Description:保存表单字段详细设置
	 * @return NONE
	 * @throws IOException
	 */
	@Action("saveColumnDetails")
	public String saveColumnDetails() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		try {
			if (formColumn.getId() != null && !formColumn.getId().equals("")) {
				FormColumn fc = formService.findFormColumnById(formColumn.getId());

				if (formColumn.getIsQuery() != null) {
					fc.setIsQuery(formColumn.getIsQuery());
				}
				if (formColumn.getIsShowIndex() != null) {
					fc.setIsShowIndex(formColumn.getIsShowIndex());
				}
				if (formColumn.getValidate() != null) {
					fc.setValidate(formColumn.getValidate());
				}
				if (formColumn.getDefaultValue() != null) {
					fc.setDefaultValue(formColumn.getDefaultValue());
				}
				if (formColumn.getSelectDataType() != null) {
					fc.setSelectDataType(formColumn.getSelectDataType());
				}
				if (formColumn.getCodeParentId() != null) {
					fc.setCodeParentId(formColumn.getCodeParentId());
				}
				if (formColumn.getCodeParentName() != null) {
					fc.setCodeParentName(formColumn.getCodeParentName());
				}
				if (formColumn.getRelationTable() != null) {
					fc.setRelationTable(formColumn.getRelationTable());
				}
				if (formColumn.getRelationColumn() != null) {
					fc.setRelationColumn(formColumn.getRelationColumn());
				}
				if (formColumn.getHasNull() != null) {
					fc.setHasNull(formColumn.getHasNull());
				}
				if (formColumn.getNullName() != null) {
					fc.setNullName(formColumn.getNullName());
				}
				if (formColumn.getCanReply() != null) {
					fc.setCanReply(formColumn.getCanReply());
				}
				if (formColumn.getCanSelectItem() != null) {
					fc.setCanSelectItem(formColumn.getCanSelectItem());
				}
				if (formColumn.getHasDefaultItem() != null) {
					fc.setHasDefaultItem(formColumn.getHasDefaultItem());
				}
				if (formColumn.getDefaultSelectItem() != null) {
					fc.setDefaultSelectItem(formColumn.getDefaultSelectItem());
				}
				if (formColumn.getCodeShowOrder() != null) {
					fc.setCodeShowOrder(formColumn.getCodeShowOrder());
				}
				if (formColumn.getRecordColumn() != null) {
					fc.setRecordColumn(formColumn.getRecordColumn());
				}
				if (formColumn.getIsLinkView() != null) {
					fc.setIsLinkView(formColumn.getIsLinkView());
				}
				if(formColumn.getSupportOrder() != null){
					fc.setSupportOrder(formColumn.getSupportOrder());
				}
				if (formColumn.getOfficeMode() != null) {
					fc.setOfficeMode(formColumn.getOfficeMode());
				}
				if (formColumn.getCompexId() != null) {
					if (fc.getCompexId() != null) {
						if (fc.getCompexId().equals(formColumn.getCompexId()) && fc.getInputType() == currentInputType) {

						} else {
							fc.setInputType(currentInputType);
							fc.setCompexId(formColumn.getCompexId());
							useInfoService.doDeleteUseInfo(fc.getId(), 1);
							saveUseInfo(fc, formId, user);
						}
					} else {
						fc.setCompexId(formColumn.getCompexId());
						fc.setInputType(currentInputType);
						saveUseInfo(fc, formId, user);
					}
				}
				if (formColumn.getCompexId() != null) {
					fc.setCompexId(formColumn.getCompexId());
				}
				this.formService.doUpdateFormColumn(fc);
			}
			// 更新主记录的修改人和修改时间
			compexDomainService.doUpdateMainInfo("sys_biaodan", formId, user);
			printJSON("success", false);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("saveColumnDetails:" + e.getMessage());
			}
			printJSON("fail", false);
		}
		return NONE;
	}

	/**
	 * Description:保存表单字段的使用信息
	 * @param formColumn
	 *            表单字段
	 * @param formId
	 *            表单ID
	 * @param user
	 *            用户信息
	 */
	public void saveUseInfo(FormColumn formColumn, Long formId, SysUser user) {
		if (formColumn.getCompexId() != null) {
			Form form = formService.findFormById(formId);
			List list = formService.findCompById(formColumn.getCompexId(), formColumn.getInputType());

			UseInfo useInfo = new UseInfo();
			useInfo.setRelationId(String.valueOf(formColumn.getId()));
			useInfo.setBizId(form.getId());
			useInfo.setCompId(formColumn.getCompexId());
			useInfo.setBizName(form.getFormName());
			if (list.size() > 0) {
				String compname = (((Map) list.get(0)).get("compname")) == null ? "" : (((Map) list.get(0)).get("compname")).toString();
				String bianma = (((Map) list.get(0)).get("bianma")) == null ? "" : (((Map) list.get(0)).get("bianma")).toString();
				useInfo.setCompName(compname);
				useInfo.setCode(bianma);
			}
			useInfo.setType("表单元素");
			useInfo.setTypeId(1);
			useInfo.setCreateBy(user.getId());
			useInfo.setCreateDate(new Date());
			useInfoService.doSaveUseInfo(useInfo);
		}
	}

	/**
	 * Description:在列表中保存表单字段信息
	 * @return NONE
	 * @throws IOException
	 */
	@Action("saveColumnAtList")
	public String saveColumnAtList() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		// 判断字段是否添加过
		if (formColumn.getId() == null || formColumn.getId().equals("")) {
			formColumn.setTabId(tabId);
		}

		List list = formService.findReplyColumn(formColumn);

		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		if (list.size() > 0) {
			objectMapper.writeValue(printWriter, "true");
			return NONE;
		}
		Long _formColumnId = null;
		FormColumn fc = new FormColumn();
		if (formColumn.getId() != null && !formColumn.getId().equals("")) {
			_formColumnId = formColumn.getId();
			fc = formService.findFormColumnById(formColumn.getId());
			fc.setColumnId(formColumn.getColumnId());
			fc.setFormOrder(formColumn.getFormOrder());
			fc.setIsShowInList(formColumn.getIsShowInList());
			fc.setListOrder(formColumn.getListOrder());
			fc.setColWidth(formColumn.getColWidth());
			fc.setIsEdit(formColumn.getIsEdit());
			fc.setIsView(formColumn.getIsView());
			fc.setRequired(formColumn.getRequired());
			fc.setIsDefaultQuery(formColumn.getIsDefaultQuery());
			if (fc.getInputType() != formColumn.getInputType() || fc.getCompexId() == null || fc.getCompexId().equals("")) {
				Long compid = this.formService.findDefaultCompex(formColumn.getInputType());
				fc.setCompexId(compid);
				
				//删除组件，构件的使用信息，重新添加
				useInfoService.doDeleteUseInfo(fc.getId(), 1);
				saveUseInfo(fc, formId, user);
			}
			if (fc.getInputType() != formColumn.getInputType()) {
				String systematom = acquireSystematom(String.valueOf(formColumn.getInputType()));
				if (!systematom.equals("-1")) {
					/*
					 * seqcodeService.doDeleteSeq(fc.getCode()); Column _column = columnService.findColumnById(fc.getColumnId());
					 */

					/*
					 * //生成编码 Seqcode _seqcode = new Seqcode(); _seqcode.setCreateBy(user.getId()); _seqcode.setCreateDate(new
					 * Date(System.currentTimeMillis())); _seqcode.setUpdateBy(user.getId()); _seqcode.setUpdateDate(new
					 * Date(System.currentTimeMillis())); _seqcode.setTblName(_column.getColumnZhName());
					 * 
					 * _seqcode.setTblSystematom(systematom); String bianma = seqcodeService.doSaveSeqcode(_seqcode);
					 * compexDomainService.doUpdateCode("sys_biaodansheji", "tbl_bianma", String.valueOf(formColumn.getId()), bianma);
					 */

					/*
					 * //扩展码 Form _form = formService.findFormById(formId); seqcodeService.doUpdateSeq(bianma, _form.getCode());
					 */
				}
			}

			fc.setInputType(formColumn.getInputType());
			this.formService.doUpdateFormColumn(fc);

		} else {
			formColumn.setTabId(tabId);
			formColumn.setPartitionId(partitionId);
			formColumn.setTableId(tableId);
			formColumn.setRelColumnId(relColumnId);
			Table table = tableService.findTableById(tableId);
			formColumn.setBelongTable(table.getTableName());
			Long compid = this.formService.findDefaultCompex(formColumn.getInputType());
			formColumn.setCompexId(compid);
			_formColumnId = this.formService.doSaveFormColumn(formColumn);

			//添加组件，构件的使用信息
			formColumn.setId(_formColumnId);
			saveUseInfo(formColumn, formId, user);
			
			Column _column = columnService.findColumnById(formColumn.getColumnId());
			if (null != formColumn.getPartitionId() && !formColumn.getPartitionId().equals("-1")) {
				String sPartitionType = "";
				sPartitionType = seqcodeService.checkPartitionType(formColumn);
				if (!sPartitionType.equals("1")) {
					columnSeqCode(_formColumnId, _column);
				}
			} else {
				columnSeqCode(_formColumnId, _column);
			}
		}
		// 绑定选项卡的表
		Tab _temptab = tabService.findTabById(tabId);
		_temptab.setTableId(tableId);
		tabService.doUpdateTabTable(_temptab);

		// 更新主记录的修改人和修改时间
		compexDomainService.doUpdateMainInfo("sys_biaodan", formId, user);

		objectMapper.writeValue(printWriter, _formColumnId);
		return NONE;
	}

	private void columnSeqCode(Long _formColumnId, Column _column) {
		// 生成编码
		Seqcode _seqcode = new Seqcode();

		_seqcode.setTblName(_column.getColumnZhName());
		_seqcode.setTblSystematom(Constants.SYSTEM_ATOM_COLN);
		_seqcode.setTblObject(tabId + "_" + _column.getId());
		_seqcode.setTblType("1");
		String bianma_1 = seqcodeService.doSaveSeqcode(_seqcode);
		List<Seqcode> _seqlist = seqcodeService.querySeqByValue(bianma_1);
		Seqcode _tmpseqcode = null;
		if (_seqlist.size() > 0) {
			_tmpseqcode = _seqlist.get(0);
			userService.addOrUpdateUserSeq(getRequest().getSession(), _tmpseqcode);
		}
		_seqcode.setTblType("2");
		String bianma_2 = seqcodeService.doSaveSeqcode(_seqcode);
		List<Seqcode> _seqlist2 = seqcodeService.querySeqByValue(bianma_2);
		Seqcode _tmpseqcode2 = null;
		if (_seqlist2.size() > 0) {
			_tmpseqcode2 = _seqlist2.get(0);
			userService.addOrUpdateUserSeq(getRequest().getSession(), _tmpseqcode2);
		}
		String bianma = bianma_1 + "," + bianma_2;

		compexDomainService.doUpdateCode("sys_biaodansheji", "tbl_bianma", _formColumnId, bianma);

		// 保存上级码和扩展码
		Tab _tab = tabService.findTabById(tabId);
		seqcodeService.doUpdateSeq(bianma_1, _tab.getCode());
		seqcodeService.doUpdateSeq(bianma_2, _tab.getCode());
		seqcodeService.doUpdateSeqExtends(bianma_1, _tab.getCode());
		seqcodeService.doUpdateSeqExtends(bianma_2, _tab.getCode());
	}

	/**
	 * Description:自动生成表单字段
	 * @return NONE
	 * @throws IOException
	 */
	@Action("autoAddFormColumn")
	public String autoAddFormColumn() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		// 自动生成时绑定选项卡的表
		Tab _temptab = tabService.findTabById(tabId);
		_temptab.setTableId(tableId);
		tabService.doUpdateTabTable(_temptab);

		QueryCriteria qc = new QueryCriteria();
		qc.addQueryCondition("tbl_tableId", tableId);
		qc.setPageSize(-1);
		List<Column> tcols = columnService.queryColumnAuto(qc);
		List<Column> tempcols = new ArrayList<Column>();
		for (Column col : tcols) {
			if (col.getColumnName().contains("comm_")) {
				tempcols.add(col);
			}
		}
		tcols.removeAll(tempcols);
		tcols.addAll(tempcols);
		// 是否在列表中显示，若分区为列表区，则默认为是
		int isShowInList = 0;
		Partition _partition = partitionService.findPartitionById(partitionId);
		if (_partition.getPartitionType() != null && _partition.getPartitionType() == 1) {
			isShowInList = 1;
		}
		for (int i = 0; i < tcols.size(); i++) {
			Column column = tcols.get(i);
			if (column.getColumnName().equalsIgnoreCase("tbl_dstatus") || (column.getColumnName().toLowerCase().contains("comm_"))) {
				continue;
			}
			FormColumn formColumn = new FormColumn();
			formColumn.setFormId(formId);
			formColumn.setTabId(tabId);
			formColumn.setPartitionId(partitionId);
			formColumn.setTableId(tableId);
			formColumn.setRelColumnId(relColumnId);
			Table table = tableService.findTableById(tableId);
			formColumn.setBelongTable(table.getTableName());
			formColumn.setColumnId(column.getId());
			formColumn.setColumnName(column.getColumnName());
			formColumn.setFormOrder(i + 1);
			formColumn.setIsShowInList(isShowInList);
			formColumn.setListOrder(i + 1);
			formColumn.setColWidth(0);
			formColumn.setIsEdit(1);
			formColumn.setIsView(1);
			formColumn.setRequired(0);
			formColumn.setIsDefaultQuery(0);
			formColumn.setInputType(0);
			formColumn.setIsShowIndex(0);
			Long compid = this.formService.findDefaultCompex(formColumn.getInputType());
			formColumn.setCompexId(compid);
			Long _formColumnId = formService.doSaveFormColumn(formColumn);
			//添加组件，构件的使用信息
			formColumn.setId(_formColumnId);
			saveUseInfo(formColumn, formId, user);
			if (null != formColumn.getPartitionId() && !formColumn.getPartitionId().equals(-1L)) {
				String sPartitionType = "";
				sPartitionType = seqcodeService.checkPartitionType(formColumn);
				if (!sPartitionType.equals("1")) {
					columnSeqCode(_formColumnId, column);
				}
			} else {
				columnSeqCode(_formColumnId, column);
			}
		}
		return NONE;
	}

	@Action("del")
	public String delete() throws IOException {// 删除表单
		try {
			if (selectedIDs != null) {
				this.formService.doDeleteForms(selectedIDs);
			} else {
				this.formService.doDeleteForm(id);
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

	@Action("logicDelete")
	public String logicDelete() throws IOException {// 删除表单
		try {
			if (selectedIDs != null) {
				this.formService.doLogicDeleteForms(selectedIDs);
			} else {
				this.formService.doLogicDeleteForm(id);
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

	/**
	 * Description:删除表单字段
	 * @return NONE
	 * @throws IOException
	 */
	@Action("deleteColumn")
	public String deleteColumn() throws IOException {
		try {
			if (selectedSubIDs != null) {
				this.formService.doDeleteFormColumns(selectedSubIDs);
			} else {
				this.formService.doDeleteFormColumn(id);
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

	@Action("isCanDelete")
	public String isCanDelete() throws Exception {
		String canDelete = "true";
		if (selectedSubIDs != null) {
			for (int i = 0; i < selectedSubIDs.length; i++) {
				FormColumn _formColumn = formService.findFormColumnById(selectedSubIDs[i]);
				boolean b = privilegeService.hasDelPrivilege(_formColumn.getCode());
				if (b) {
					canDelete = "false";
					break;
				}
			}
		}
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(canDelete);

		return NONE;
	}

	/**
	 * Description:显示下拉框
	 * @return NONE
	 * @throws Exception
	 */
	@Action("showComBox")
	public String showComBox() throws Exception {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		components = comboxService.queryForPageList(qc);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"formColumn.comBoxId\" style=\"width:175px\">");
		for (Component component : components) {
			Combox combox = (Combox) component;
			sb.append("<option value=\"" + combox.getId() + "\" ");
			if (combox.getId().equals(comBoxId)) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(" >" + combox.getComboxName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示日期组件
	 * @return NONE
	 * @throws Exception
	 */
	@Action("showDate")
	public String showDate() throws Exception {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		dates = dateService.queryForPageList(qc);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"formColumn.dateId\" style=\"width:175px\">");
		for (com.cloudstong.platform.resource.date.model.DateControl date : dates) {
			sb.append("<option value=\"" + date.getId() + "\" ");
			if (date.getId().equals(dateId)) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(" >" + date.getDateName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示单选框
	 * @return NONE
	 * @throws Exception
	 */
	@Action("showRadio")
	public String showRadio() throws Exception {
		radios = radioMgtService.queryAllRadioMgtVOs();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"formColumn.radioId\" style=\"width:175px\">");
		for (RadioMgtVO radio : radios) {
			sb.append("<option value=\"" + radio.getId() + "\" ");
			if (radio.getId().equals(radioId)) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(" >" + radio.getTbl_compname() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示复选框
	 * @return NONE
	 * @throws Exception
	 */
	@Action("showCheckBox")
	public String showCheckBox() throws Exception {
		checkBoxes = checkBoxMgtService.queryAllCheckBoxMgtVOs();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"formColumn.checkBoxId\" style=\"width:175px\">");
		for (CheckBoxMgtVO checkbox : checkBoxes) {
			sb.append("<option value=\"" + checkbox.getId() + "\" ");
			if (checkbox.getId().equals(checkBoxId)) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(" >" + checkbox.getTbl_compname() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示搜索下拉框
	 * @return NONE
	 * @throws Exception
	 */
	@Action("showSearchComBox")
	public String showSearchComBox() throws Exception {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		components = searchComboxService.queryForPageList(qc);

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"formColumn.searchComBoxId\" style=\"width:175px\">");
		for (Component component : components) {
			SearchCombox searchCombox = (SearchCombox) component;
			sb.append("<option value=\"" + searchCombox.getId() + "\" ");
			if (searchCombox.getId().equals(searchComBoxId)) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(" >" + searchCombox.getSearchComboxName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示所有可选项
	 * @return NONE
	 * @throws Exception
	 */
	@Action("showCanSelectBox")
	public String showCanSelectBox() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		List<Dictionary> diclist = dictionaryService.queryByParent(parentNodeID);

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(
				"<select id=\"canSelectBox\" name=\"formColumn.canSelectItem\" multiple=\"true\" style=\"width: 175px;height: 120px;\">");
		for (Dictionary dictionary : diclist) {
			sb.append("<option selected='selected' value=\"" + dictionary.getId() + "\" ");
			sb.append(" >" + dictionary.getName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示默认选项
	 * @return NONE
	 * @throws Exception
	 */
	@Action("showDefaultSelectItem")
	public String showDefaultSelectItem() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		List<Dictionary> diclist = dictionaryService.queryByParent(parentNodeID);

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select id=\"defaultSelectBox\" name=\"formColumn.defaultSelectItem\" style=\"width:175px\">");
		for (Dictionary dictionary : diclist) {
			sb.append("<option value=\"" + dictionary.getId() + "\" ");
			if (getRequest().getParameter("defaultSelectItem") != null) {
				if (getRequest().getParameter("defaultSelectItem").equals(dictionary.getId().toString())) {
					sb.append(" selected=\"selected\" ");
				}
			}

			sb.append(" >" + dictionary.getName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/*
	 * 显示树
	 */
	@Action("showTree")
	public String showTree() throws Exception {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		mgrTrees = mgrTreeService.queryMgrTree(qc);

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"formColumn.treeId\" style=\"width:175px\">");
		for (MgrTree mgrTree : mgrTrees) {
			sb.append("<option value=\"" + mgrTree.getId() + "\" ");
			if (mgrTree.getId().equals(treeId)) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(" >" + mgrTree.getTreename() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示文本编辑器
	 * @return NONE
	 * @throws Exception
	 */
	@Action("showEditor")
	public String showEditor() throws Exception {
		textEditors = textEditorService.findAllTextEditor();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"formColumn.editorId\" style=\"width:175px\">");
		for (TextEditor textEditor : textEditors) {
			sb.append("<option value=\"" + textEditor.getId() + "\" ");
			if (textEditor.getId().equals(editorId)) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(" >" + textEditor.getTextEditorName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示文本域
	 * @return
	 * @throws Exception
	 */
	@Action("showTextField")
	public String showTextField() throws Exception {
		QueryCriteria qc = new QueryCriteria();
		components = textBoxService.queryForPageList(qc);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"formColumn.textFieldId\" style=\"width:175px\">");
		for (Component component : components) {
			Textarea textarea = (Textarea) component;
			sb.append("<option value=\"" + textarea.getId() + "\" ");
			if (textarea.getId().equals(textFieldId)) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(" >" + textarea.getTextareaName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示关系表
	 * 
	 * Steps:
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action("showRelationTable")
	public String showRelationTable() throws IOException {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		this.tables = tableService.queryTable(qc);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(
				"<select id=\"relationTableSelectId\" name=\"formColumn.relationTable\" style=\"width:175px\" onchange=\"showRelationColumn();\">");
		for (Table tbl : tables) {
			sb.append("<option value=\"" + tbl.getTableName() + "\" ");
			if (tbl.getTableName().equalsIgnoreCase(table)) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(" >" + tbl.getTableZhName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示关系字段
	 * @return NONE
	 * @throws IOException
	 */
	@Action("showRelationColumn")
	public String showRelationColumn() throws IOException {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		this.tables = tableService.queryTable(qc);
		if (tables != null && tables.size() > 0) {
			if (table != null && !"".equalsIgnoreCase(table)) {
				qc.addQueryCondition("tbl_belongTable", table);
			} else {
				qc.addQueryCondition("tbl_belongTable", tables.get(0).getTableName());
			}
			this.columns = columnService.queryColumn(qc);
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			StringBuffer sb = new StringBuffer("<select name=\"formColumn.relationColumn\" style=\"width:175px\">");
			for (Column col : columns) {
				sb.append("<option value=\"" + col.getColumnName() + "\" ");
				if (col.getColumnName().equals(column)) {
					sb.append(" selected=\"selected\" ");
				}
				sb.append(" >" + col.getColumnZhName() + "</option>");
			}
			sb.append("</select>");
			out.write(sb.toString());
		}

		return NONE;
	}

	@Action("setDefaultQuery")
	public String setDefaultQuery() {
		FormColumn fColumn = formService.findFormColumnById(id);
		Form f = formService.findFormById(formId);
		List list = f.getFormColumnExtends();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				FormColumnExtend fcExtend = (FormColumnExtend) list.get(i);
				FormColumn fcolumn = fcExtend.getFormColumn();
				fcolumn.setFormId(formId);
				if (fcolumn.getId().equals(fColumn.getId())) {
					fcolumn.setIsDefaultQuery(1);
					this.formService.doUpdateFormColumn(fcolumn);
				} else {
					fcolumn.setIsDefaultQuery(0);
					this.formService.doUpdateFormColumn(fcolumn);
				}
			}
		}
		return NONE;
	}

	/**
	 * Description:显示表单字段tab页
	 * @return NONE
	 * @throws IOException
	 */
	@Action("showFormColumnTab")
	public String showFormColumnTab() throws IOException {
		QueryCriteria qc = new QueryCriteria();
		qc.addQueryCondition("tbl_form", formId);
		qc.setPageSize(-1);
		List<Tab> tabs = tabService.queryTab(qc);

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(
				"<select id=\"formcolumntabid\" name=\"formColumn.tabId\" style=\"width:175px\" onchange=\"showFormColumnPartition(this.value)\">");
		for (Tab tab : tabs) {
			String selected = "";
			if (formColumnTabId != null) {
				if (tab.getId().equals(formColumnTabId)) {
					selected = "selected";
				}
			}
			sb.append("<option value=\"" + tab.getId() + "\" " + selected + ">" + tab.getTabName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示表单字段分区
	 * @return NONE
	 * @throws IOException
	 */
	@Action("showFormColumnPartition")
	public String showFormColumnPartition() throws IOException {
		Tab tab = tabService.findTabById(formColumnTabId);
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
				"<select id=\"formcolumnpartitionid\" name=\"formColumn.partitionId\" style=\"width:175px\" onchange=\"showFormColumnList()\">");
		for (Partition partition : partitions) {
			String selected = "";
			if (formColumnPartitionId != null) {
				if (partition.getId().equals(formColumnPartitionId)) {
					selected = "selected";
				}
			}
			sb.append("<option value=\"" + partition.getId() + "\" " + selected + ">" + partition.getPartitionName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示关联字段
	 * @return NONE
	 * @throws IOException
	 */
	@Action("showRelColumn")
	public String showRelColumn() throws IOException {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		if (tableId != null && !tableId.equals("")) {
			qc.addQueryCondition("tbl_tableId", tableId);
			this.columns = columnService.queryColumn(qc);
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			StringBuffer sb = new StringBuffer("<select id=\"relColumnId\" name=\"formColumn.relColumnId\" style=\"width:175px\">");
			sb.append("<option value=\"-1\"></option>");
			for (Column col : columns) {
				sb.append("<option value=\"" + col.getId() + "\" ");
				if (col.getColumnName().equals(column)) {
					sb.append(" selected=\"selected\" ");
				}
				sb.append(" >" + col.getColumnZhName() + "</option>");
			}
			sb.append("</select>");
			out.write(sb.toString());
		}
		return NONE;
	}

	/**
	 * Description:显示所属表
	 * @return
	 * @throws Exception
	 */
	@Action("showAllTable")
	public String showAllTable() throws Exception {
		List<FormColumn> list = formService.findFormColumnByTabId(tabId);
		Form _form = formService.findFormById(formId);
		QueryCriteria qc = new QueryCriteria();
		qc.addQueryCondition("tbl_systemteam", _form.getSystemTeam());
		qc.setPageSize(-1);
		// 查询所有的表，用于列表选项卡
		List<Table> allTables = tableService.queryTable(qc);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(
				"<select id=\"tableId\" name=\"formColumn.tableId\" style=\"width:175px\" onchange=\"showFormColumnList();\">");
		for (Table table : allTables) {
			sb.append("<option value=\"" + table.getId() + "\" ");
			if (list.size() > 0) {
				FormColumn fc = list.get(0);
				if (fc.getTableId() != null && fc.getTableId().equals(table.getId())) {
					sb.append("selected=\"selected\"");
				}
			}
			sb.append(" >" + table.getTableZhName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * Description:显示主表和所有和主表有关系的从表
	 * @return
	 */
	@Action("showRelTable")
	public String showRelTable() throws Exception {
		List<FormColumn> list = formService.findFormColumnByTabId(tabId);
		HttpServletRequest request = getRequest();
		SysUser user = (SysUser) request.getSession().getAttribute("user");

		String op = getRequest().getParameter("op");
		getRequest().setAttribute("op", op);

		this.form = formService.findFormById(formId);
		this.formColumn = new FormColumn();

		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		// 查询主表以及所有的从表
		if (formId != null && !formId.equals("")) {
			this.tables = tableService.findTablesByTeam(this.form.getTableId(), this.form.getSystemTeam());
		} else {
			this.tables = tableService.queryTable(qc);
		}

		List<Table> relTables = this.tables;
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(
				"<select id=\"tableId\" name=\"formColumn.tableId\" style=\"width:175px\" onchange=\"showFormColumnList();\">");
		for (Table table : relTables) {
			sb.append("<option value=\"" + table.getId() + "\" ");
			if (list.size() > 0) {
				FormColumn fc = list.get(0);
				if (fc.getTableId() != null && fc.getTableId().equals(table.getId())) {
					sb.append("selected=\"selected\"");
				}
			}
			sb.append(" >" + table.getTableZhName() + "</option>");
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

	@Action("checkTabType")
	public String checkTabType() {
		try {
			String tabType = "";
			Tab tab = tabService.findTabById(tabId);
			if (tab != null) {
				tabType = String.valueOf(tab.getTabType());
			}
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.write(tabType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/**
	 * Description:旧方法，用于加载表单字段表单页
	 * @return NONE
	 * @throws Exception
	 */
	@Action("loadOperation")
	public String loadOperation() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		this.form = formService.findFormById(formId);
		this.formColumn = new FormColumn();
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		this.tables = tableService.queryTable(qc);
		if (id == null) {
			if (tables != null && tables.size() > 0) {
				for (int i = 0; i < tables.size(); i++) {
					if (formId != null) {
						if (tables.get(i).getTableName().equals(this.form.getTableName())) {
							qc.addQueryCondition("tbl_tableId", ((Table) tables.get(i)).getId());
							this.formColumn.setTableId(((Table) tables.get(i)).getId());
							break;
						}
					}
				}
				this.columns = columnService.queryColumn(qc);
			}
		} else {
			this.formColumn = formService.findFormColumnById(id);
			if (tables != null && tables.size() > 0) {
				for (int i = 0; i < tables.size(); i++) {
					if (tables.get(i).getTableName().equals(this.formColumn.getBelongTable())) {
						qc.addQueryCondition("tbl_tableId", ((Table) tables.get(i)).getId());
						break;
					}
				}
				this.columns = columnService.queryColumn(qc);
			}
		}

		inputTypeList = dictionaryService.queryByParent(10117610301L);
		validates = dictionaryService.queryByParent(492175832L);

		QueryCriteria tabQc = new QueryCriteria();
		tabQc.addQueryCondition("tbl_form", formId);
		tabQc.setPageSize(-1);
		try {
			tabs = tabService.queryTab(tabQc);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 根据分区id查找分区类型，根据不同的分区类型转发至不同页面,如代码表中增加了分区类型，需在此添加代码
		String forward = "loadoperation";
		if (partitionTempId.equals("-1")) {
			forward = "loadoperation";
		} else {
			Partition partition = partitionService.findPartitionById(partitionTempId);
			if (partition.getPartitionType() == 0) {
				forward = "loadnolist";
			} else if (partition.getPartitionType() == 1) {
				forward = "loadhaslist";
			}

		}
		return forward;
	}

	/**
	 * Description:获得编码
	 * @return NONE
	 * @throws IOException
	 */
	@Action("getCode")
	public String getCode() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(code);
		return NONE;
	}

	/**
	 * Description:显示表单字段详细设置页面
	 * @return NONE
	 * @throws Exception
	 */
	@Action("showDetailPage")
	public String showDetailPage() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		String op = getRequest().getParameter("op");
		getRequest().setAttribute("op", op);

		this.formColumn = new FormColumn();
		if (this.id != null) {
			this.formColumn = formService.findFormColumnById(this.id);
		}
		String type = getRequest().getParameter("type");
		String forward = NONE;
		if (type != null && type.equals("0")) {
			validates = dictionaryService.queryByParent(492175832L);
			QueryCriteria textBoxQc = new QueryCriteria();
			textBoxQc.setPageSize(-1);
			components = textBoxService.queryForPageList(textBoxQc);
			forward = "detailtextbox";
		} else if (type != null && type.equals("1")) {
			QueryCriteria qc = new QueryCriteria();
			qc.setPageSize(-1);
			components = comboxService.queryForPageList(qc);
			forward = "detailsComBox";
		} else if (type != null && type.equals("2")) {
			QueryCriteria qc = new QueryCriteria();
			components = textareaService.queryForPageList(qc);
			forward = "detailsTextArea";
		} else if (type != null && type.equals("3")) {
			radios = radioMgtService.queryAllRadioMgtVOs();
			forward = "detailsRadio";
		} else if (type != null && type.equals("4")) {
			checkBoxes = checkBoxMgtService.queryAllCheckBoxMgtVOs();
			forward = "detailsCheckBox";
		} else if (type != null && type.equals("5")) {
			QueryCriteria textBoxQc = new QueryCriteria();
			textBoxQc.setPageSize(-1);
			components = uploadBoxService.queryForPageList(textBoxQc);
			forward = "detailsUploadFile";
		} else if (type != null && type.equals("6")) {
			QueryCriteria qc = new QueryCriteria();
			qc.setPageSize(-1);
			dates = dateService.queryForPageList(qc);
			forward = "detailsDateControl";
		} else if (type != null && type.equals("7")) {
			QueryCriteria qc = new QueryCriteria();
			qc.setPageSize(-1);
			mgrTrees = mgrTreeService.queryMgrTree(qc);
			forward = "detailsTree";
		} else if (type != null && type.equals("8")) {
			QueryCriteria qc = new QueryCriteria();
			qc.setPageSize(-1);
			components = searchComboxService.queryForPageList(qc);
			forward = "detailsSearchComBox";
		} else if (type != null && type.equals("9")) {
			forward = "detailsCustom";
		} else if (type != null && type.equals("10")) {
			textEditors = textEditorService.findAllTextEditor();
			forward = "detailsTextEditor";
		} else if (type != null && type.equals("11")) {
			QueryCriteria qc = new QueryCriteria();
			qc.setPageSize(-1);
			components = passboxService.queryForPageList(qc);
			forward = "detailPassbox";
		} else if (type != null && type.equals("12")) {
			offices = officeService.findAllOffice();
			forward = "office";
		} else if (type != null && type.equals("13")) {
			codeCaseCades = codeCaseCadeService.findAllCodeCaseCade();
			forward = "codecasecade";
		} else if (type != null && type.equals("14")) {
			Long tableId = this.formColumn.getTableId();
			QueryCriteria qc = new QueryCriteria();
			qc.addQueryCondition("tbl_tableId", tableId);
			qc.setPageSize(-1);
			List<Column> tcols = columnService.queryColumnAuto(qc);
			getRequest().setAttribute("recordColumns", tcols);
			uploadifys = uploadifyService.findAllUploadify();
			forward = "uploadify";
		}else if (type != null && type.equals("16")) {
			QueryCriteria qc = new QueryCriteria();
			qc.setPageSize(-1);
			components = autoCompleteService.queryForPageList(qc);
			forward = "detailAutoComplete";
		}
		getRequest().setAttribute("currentInputType", type);
		return forward;
	}

	/**
	 * Description:复制表单
	 * @return NONE
	 * @throws Exception
	 */
	@Action("copyForm")
	public String copyForm() throws Exception {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			if (selectedIDs != null) {
				Long _formId = selectedIDs[0];
				List<DomainVO> domainVOs = new ArrayList<DomainVO>();
				Form _form = formService.getFormByIdAndDomainVO(_formId, domainVOs, user);

				// 复制表单主记录
				String _newFormName = getRequest().getParameter("newFormName");
				_form.setCreateBy(user.getId());
				_form.setCreateDate(new Date());
				_form.setUpdateBy(user.getId());
				_form.setUpdateDate(new Date());
				_form.setFormName(_newFormName);
				// 插入一条新的表单
				Long _newFormId = this.formService.doSaveForm(_form);
				// 为新的表单生成编码
				Seqcode seqcode = new Seqcode();
				seqcode.setTblName(_form.getFormName());
				seqcode.setTblSystematom(Constants.SYSTEM_ATOM_FORM);
				String bianma = seqcodeService.doSaveSeqcode(seqcode);
				List<Seqcode> _seqlist = seqcodeService.querySeqByValue(bianma);
				Seqcode _tmpseqcode = null;
				if (_seqlist.size() > 0) {
					_tmpseqcode = _seqlist.get(0);
					userService.addOrUpdateUserSeq(getRequest().getSession(), _tmpseqcode);
				}
				// 更新新表单编码值
				compexDomainService.doUpdateCode("sys_biaodan", "tbl_bianma", _newFormId, bianma);

				// 获取要复制的表单的tab页列表
				List<Tab> _tabs = _form.getTabs();
				for (Tab tab : _tabs) {
					tab.setFormId(_newFormId);
					Long _tabId = tabService.doSaveTab(tab);
					// 为新的tab页生成编码
					Seqcode tabseqcode = new Seqcode();
					tabseqcode.setTblName(tab.getTabName());
					tabseqcode.setTblSystematom(Constants.SYSTEM_ATOM_ITEM);
					tabseqcode.setTblObject(String.valueOf(_tabId));
					String tabbianma = seqcodeService.doSaveSeqcode(seqcode);
					List<Seqcode> _tabseqlist = seqcodeService.querySeqByValue(tabbianma);
					Seqcode _tabseqcode = null;
					if (_tabseqlist.size() > 0) {
						_tabseqcode = _tabseqlist.get(0);
						userService.addOrUpdateUserSeq(getRequest().getSession(), _tabseqcode);
					}
					compexDomainService.doUpdateCode("sys_biaodantab", "tbl_bianma", _tabId, tabbianma);

					if (tab.getFormId() != null && !tab.getFormId().equals("") && !tab.getFormId().equals(-1L)) {
						// 上级码
						Form _tabform = formService.findFormById(tab.getFormId());
						seqcodeService.doUpdateSeq(bianma, _tabform.getCode());
						// 扩展码
						seqcodeService.doUpdateSeqExtends(bianma, _tabform.getCode());
					}

					// 获取选项卡里分区的集合
					List<Partition> _partitions = tab.getPartitions();
					if (_partitions != null && _partitions.size() > 0) {
						// 添加表单字段
						for (Partition partition : _partitions) {
							List<FormColumnExtend> _formColumns = partition.getFormColumnExtends();
							if (_formColumns != null) {
								for (FormColumnExtend formColumnExtend : _formColumns) {
									FormColumn _formColumn = formColumnExtend.getFormColumn();
									_formColumn.setFormId(_newFormId);
									_formColumn.setTabId(_tabId);
									_formColumn.setPartitionId(partition.getId());
									Long _formColumnId = formService.doSaveFormColumn(_formColumn);

									if (null != _formColumn.getPartitionId() && !_formColumn.getPartitionId().equals(-1L)) {
										String sPartitionType = "";
										sPartitionType = seqcodeService.checkPartitionType(_formColumn);
										if (!sPartitionType.equals("1")) {
											columnSeqCodeByCopy(_newFormId, _tabId, _formColumn, _formColumnId);
										}
									} else {
										columnSeqCodeByCopy(_newFormId, _tabId, _formColumn, _formColumnId);
									}
								}
							}

							// 添加按钮
							List<FormButton> _formButtons = partition.getFormButtons();
							for (FormButton formButton : _formButtons) {
								formButton.setFormId(_newFormId);
								if (formButton.getTabId().equals(tab.getId()) && formButton.getPartitionId().equals(partition.getId())) {
									formButton.setTabId(_tabId);
									formButton.setPartitionId(partition.getId());
									Long _formButtonId = formButtonService.doSaveFormButton(formButton);
									buttonOperation(formButton, _formButtonId, _newFormId, user);
								}
							}
						}
					} else {
						// 添加表单字段
						List<FormColumnExtend> _formColumns = tab.getFormColumnExtends();
						for (FormColumnExtend formColumnExtend : _formColumns) {
							FormColumn _formColumn = formColumnExtend.getFormColumn();
							_formColumn.setFormId(_newFormId);
							_formColumn.setTabId(_tabId);
							Long _formColumnId = formService.doSaveFormColumn(_formColumn);

							if (null != _formColumn.getPartitionId() && !_formColumn.getPartitionId().equals(-1L)) {
								String sPartitionType = "";
								sPartitionType = seqcodeService.checkPartitionType(_formColumn);
								if (!sPartitionType.equals("1")) {
									columnSeqCodeByCopy(_newFormId, _tabId, _formColumn, _formColumnId);
								}
							} else {
								columnSeqCodeByCopy(_newFormId, _tabId, _formColumn, _formColumnId);
							}
						}
						// 添加按钮
						List<FormButton> _formButtons = _form.getFormButtons();
						for (FormButton formButton : _formButtons) {
							formButton.setFormId(_newFormId);
							if (formButton.getTabId().equals(tab.getId())) {
								formButton.setTabId(_tabId);
								Long _formButtonId = formButtonService.doSaveFormButton(formButton);
								buttonOperation(formButton, _formButtonId, _newFormId, user);
							}
						}
					}
				}
				// 获取要复制的按钮列表(应用于整个表单的)
				List<FormButton> _formButtons = _form.getFormButtons();
				for (FormButton formButton : _formButtons) {
					formButton.setFormId(_newFormId);
					if (formButton.getTabId().equals(-1L)) {
						Long _formButtonId = formButtonService.doSaveFormButton(formButton);
						buttonOperation(formButton, _formButtonId, _newFormId, user);
					}
				}
			}
		} catch (Exception e) {
			printJSON("fail");
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("copyForm:" + e.getMessage());
			}
		}
		return NONE;
	}

	private void columnSeqCodeByCopy(Long _newFormId, Long _tabId, FormColumn _formColumn, Long _formColumnId) {
		// 生成编码
		Column _column = columnService.findColumnById(_formColumn.getColumnId());
		Seqcode _seqcode = new Seqcode();
		_seqcode.setTblName(_formColumn.getColumnZhName());
		_seqcode.setTblSystematom(Constants.SYSTEM_ATOM_COLN);
		_seqcode.setTblObject(_tabId + "_" + _column.getId());
		_seqcode.setTblType("1");
		String bianma_1 = seqcodeService.doSaveSeqcode(_seqcode);
		List<Seqcode> _copyformseqlist = seqcodeService.querySeqByValue(bianma_1);
		Seqcode _copyformseqcode = null;
		if (_copyformseqlist.size() > 0) {
			_copyformseqcode = _copyformseqlist.get(0);
			userService.addOrUpdateUserSeq(getRequest().getSession(), _copyformseqcode);
		}
		_seqcode.setTblType("2");
		String bianma_2 = seqcodeService.doSaveSeqcode(_seqcode);
		List<Seqcode> _copyformseqlist2 = seqcodeService.querySeqByValue(bianma_1);
		Seqcode _copyformseqcode2 = null;
		if (_copyformseqlist2.size() > 0) {
			_copyformseqcode2 = _copyformseqlist2.get(0);
			userService.addOrUpdateUserSeq(getRequest().getSession(), _copyformseqcode2);
		}
		String _bianma = bianma_1 + "," + bianma_2;
		compexDomainService.doUpdateCode("sys_biaodansheji", "tbl_bianma", _formColumnId, _bianma);

		// 保存上级码和扩展码
		Form _tmpform = formService.findFormById(_newFormId);
		seqcodeService.doUpdateSeq(bianma_1, _tmpform.getCode());
		seqcodeService.doUpdateSeq(bianma_2, _tmpform.getCode());
		seqcodeService.doUpdateSeqExtends(bianma_1, _tmpform.getCode());
		seqcodeService.doUpdateSeqExtends(bianma_2, _tmpform.getCode());
	}

	/**
	 * Description:表单按钮生成编码
	 * @param formButton
	 *            表单按钮
	 * @param _formButtonId
	 *            表单按钮ID
	 * @param _newFormId
	 *            表单ID
	 * @param user
	 *            用户信息
	 */
	public void buttonOperation(FormButton formButton, Long _formButtonId, Long _newFormId, SysUser user) {
		try {
			// 生成编码
			Seqcode _seqcode = new Seqcode();

			if (formButton.getButtonType().equals("0")) {
				// Button _button = buttonService.findByID(formButton.getButtonId());
				_seqcode.setTblSystematom(Constants.SYSTEM_ATOM_FORM_BUTN);
			}
			_seqcode.setTblName(formButton.getShowName());
			_seqcode.setTblObject(String.valueOf(_formButtonId));
			String _bianma = seqcodeService.doSaveSeqcode(_seqcode);
			List<Seqcode> _seqlist = seqcodeService.querySeqByValue(_bianma);
			Seqcode _tmpseqcode = null;
			if (_seqlist.size() > 0) {
				_tmpseqcode = _seqlist.get(0);
				userService.addOrUpdateUserSeq(getRequest().getSession(), _tmpseqcode);
			}
			compexDomainService.doUpdateCode("sys_biaodanbutton", "tbl_bianma", _formButtonId, _bianma);
			// 上级码
			Form _tform = formService.findFormById(_newFormId);
			seqcodeService.doUpdateSeq(_bianma, _tform.getCode());
			// 扩展码
			seqcodeService.doUpdateSeqExtends(_bianma, _tform.getCode());

			formButton.setId(_formButtonId);
			saveUseInfo(formButton, _newFormId, user);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("buttonOperation:" + e.getMessage());
			}
		}
	}

	/**
	 * Description:保存表单按钮使用信息
	 * @param formButton
	 *            表单字段
	 * @param formId
	 *            表单ID
	 * @param user
	 *            用户信息
	 * @throws Exception
	 */
	@Action("saveUseInfo")
	public void saveUseInfo(FormButton formButton, Long formId, SysUser user) throws Exception {
		Form form = formService.findFormById(formId);
		UseInfo useInfo = new UseInfo();
		useInfo.setRelationId(String.valueOf(formButton.getId()));
		useInfo.setBizId(form.getId());
		useInfo.setCompId(formButton.getButtonId());
		useInfo.setBizName(form.getFormName());
		if (formButton.getButtonType().equals("0")) {
			Button button = buttonService.findByID(formButton.getButtonId());
			useInfo.setCompName(button.getButtonName() + "按钮");
			useInfo.setCode(button.getButtonCode());
		} else if (formButton.getButtonType().equals("1")) {
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
	 * Description:判断表单名称是否已经存在
	 * @return NONE
	 * @throws IOException
	 */
	@Action("isHasFormName")
	public String isHasFormName() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String hasFormName = "false";
		String _newFormName = getRequest().getParameter("newFormName");
		List list = compexDomainService.findReplyResult("sys_biaodan", "tbl_biaodanming", _newFormName, null);
		if (list.size() > 0) {
			hasFormName = "true";
		}
		out.write(hasFormName);
		return NONE;
	}

	@Action("getFormColumnObj")
	public String getFormColumnObj() {
		String _formColumnId = getRequest().getParameter("formColumnId");
		// 通过表单字段ID查找表单字段信息
		try {
			FormColumn _formColumn = formService.findFormColumnById(Long.valueOf(_formColumnId));

			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter printWriter = response.getWriter();
			objectMapper.writeValue(printWriter, _formColumn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	@Action("showFormHelp")
	public String showFormHelp() {
		return "formhelp";
	}

	/**
	 * Description:根据录入类型匹配系统元素
	 * @param inputType
	 *            录入类型
	 * @return 系统元素ID
	 */
	public String acquireSystematom(String inputType) {
		String systematom = "-1";
		if (inputType.equals("0")) {
			systematom = "5";
		} else if (inputType.equals("1")) {
			systematom = "42";
		} else if (inputType.equals("2")) {
			systematom = "44";
		} else if (inputType.equals("3")) {
			systematom = "39";
		} else if (inputType.equals("4")) {
			systematom = "6";
		} else if (inputType.equals("5")) {
			systematom = "45";
		} else if (inputType.equals("6")) {
			systematom = "8";
		} else if (inputType.equals("7")) {
			systematom = "7";
		} else if (inputType.equals("8")) {
			systematom = "43";
		} else if (inputType.equals("10")) {
			systematom = "9";
		} else if (inputType.equals("12")) {
			systematom = "10";
		} else if (inputType.equals("13")) {
			systematom = "47";
		} else if (inputType.equals("14")) {
			systematom = "48";
		}
		return systematom;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public FormColumn getFormColumn() {
		return formColumn;
	}

	public void setFormColumn(FormColumn formColumn) {
		this.formColumn = formColumn;
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

	public List<Dictionary> getInputTypeList() {
		return inputTypeList;
	}

	public void setInputTypeList(List<Dictionary> inputTypeList) {
		this.inputTypeList = inputTypeList;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public List<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public List<Dictionary> getValidates() {
		return validates;
	}

	public void setValidates(List<Dictionary> validates) {
		this.validates = validates;
	}

	public List<com.cloudstong.platform.resource.date.model.DateControl> getDates() {
		return dates;
	}

	public void setDates(List<com.cloudstong.platform.resource.date.model.DateControl> dates) {
		this.dates = dates;
	}

	public void setPartitionId(Long partitionId) {
		this.partitionId = partitionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getParentNodeID() {
		return parentNodeID;
	}

	public void setParentNodeID(Long parentNodeID) {
		this.parentNodeID = parentNodeID;
	}

	public String getComBoxId() {
		return comBoxId;
	}

	public void setComBoxId(String comBoxId) {
		this.comBoxId = comBoxId;
	}

	public String getDateId() {
		return dateId;
	}

	public void setDateId(String dateId) {
		this.dateId = dateId;
	}

	public String getRadioId() {
		return radioId;
	}

	public void setRadioId(String radioId) {
		this.radioId = radioId;
	}

	public String getCheckBoxId() {
		return checkBoxId;
	}

	public void setCheckBoxId(String checkBoxId) {
		this.checkBoxId = checkBoxId;
	}

	public String getSearchComBoxId() {
		return searchComBoxId;
	}

	public void setSearchComBoxId(String searchComBoxId) {
		this.searchComBoxId = searchComBoxId;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getEditorId() {
		return editorId;
	}

	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}

	public String getTextFieldId() {
		return textFieldId;
	}

	public void setTextFieldId(String textFieldId) {
		this.textFieldId = textFieldId;
	}

	public Long getFormColumnTabId() {
		return formColumnTabId;
	}

	public void setFormColumnTabId(Long formColumnTabId) {
		this.formColumnTabId = formColumnTabId;
	}

	public Long getFormColumnPartitionId() {
		return formColumnPartitionId;
	}

	public void setFormColumnPartitionId(Long formColumnPartitionId) {
		this.formColumnPartitionId = formColumnPartitionId;
	}

	public Long getPartitionTempId() {
		return partitionTempId;
	}

	public void setPartitionTempId(Long partitionTempId) {
		this.partitionTempId = partitionTempId;
	}

	public Long getRelColumnId() {
		return relColumnId;
	}

	public void setRelColumnId(Long relColumnId) {
		this.relColumnId = relColumnId;
	}

	public Partition getPartition() {
		return partition;
	}

	public void setPartition(Partition partition) {
		this.partition = partition;
	}

	public List<RadioMgtVO> getRadios() {
		return radios;
	}

	public void setRadios(List<RadioMgtVO> radios) {
		this.radios = radios;
	}

	public List<CheckBoxMgtVO> getCheckBoxes() {
		return checkBoxes;
	}

	public void setCheckBoxes(List<CheckBoxMgtVO> checkBoxes) {
		this.checkBoxes = checkBoxes;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<MgrTree> getMgrTrees() {
		return mgrTrees;
	}

	public Long getFormManageId() {
		return formManageId;
	}

	public void setFormManageId(Long formManageId) {
		this.formManageId = formManageId;
	}

	public List<TextEditor> getTextEditors() {
		return textEditors;
	}

	public void setTextEditors(List<TextEditor> textEditors) {
		this.textEditors = textEditors;
	}

	public List<Office> getOffices() {
		return offices;
	}

	public void setOffices(List<Office> offices) {
		this.offices = offices;
	}

	public void setMgrTrees(List<MgrTree> mgrTrees) {
		this.mgrTrees = mgrTrees;
	}

	public List<CodeCaseCade> getCodeCaseCades() {
		return codeCaseCades;
	}

	public void setCodeCaseCades(List<CodeCaseCade> codeCaseCades) {
		this.codeCaseCades = codeCaseCades;
	}

	public Integer getCurrentInputType() {
		return currentInputType;
	}

	public void setCurrentInputType(Integer currentInputType) {
		this.currentInputType = currentInputType;
	}

	public List<Uploadify> getUploadifys() {
		return uploadifys;
	}

	public void setUploadifys(List<Uploadify> uploadifys) {
		this.uploadifys = uploadifys;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public Long getTabId() {
		return tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	public Long getPartitionId() {
		return partitionId;
	}
}
