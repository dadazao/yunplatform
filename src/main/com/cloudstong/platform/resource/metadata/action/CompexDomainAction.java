package com.cloudstong.platform.resource.metadata.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.model.Code;
import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.core.util.CacheUtil;
import com.cloudstong.platform.core.util.DateUtil;
import com.cloudstong.platform.core.util.EncryptUtil;
import com.cloudstong.platform.core.util.FormUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.attachment.service.AttachmentService;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.catalog.service.CatalogService;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.resource.form.model.Tab;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.resource.metadata.model.Relation;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.service.CompexDomainService;
import com.cloudstong.platform.resource.metadata.service.RelationService;
import com.cloudstong.platform.resource.metadata.service.TableService;
import com.cloudstong.platform.resource.personChiose.service.PersonChioseService;
import com.cloudstong.platform.resource.seqcode.service.SeqcodeService;
import com.cloudstong.platform.resource.systematom.model.SystemAtom;
import com.cloudstong.platform.resource.systematom.service.SystemAtomService;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.service.TabulationService;
import com.cloudstong.platform.resource.template.model.Partition;
import com.cloudstong.platform.resource.template.service.PartitionService;
import com.cloudstong.platform.resource.tree.model.CommonTree;
import com.cloudstong.platform.resource.tree.model.MgrTree;
import com.cloudstong.platform.resource.tree.service.MgrTreeService;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.Person;
import com.cloudstong.platform.system.model.SysPrivilege;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.IUserService;
import com.cloudstong.platform.system.service.SysPrivilegeService;

/**
 * @author michael Created on 2012-11-13
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:配置模块Action
 */
public class CompexDomainAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 主记录
	 */
	protected Domain dom;
	/**
	 * 列表id
	 */
	protected Long listId;
	/**
	 * 列表绑定的表单id
	 */
	protected Long formId;
	/**
	 * 主列表
	 */
	protected Tabulation tabulation;
	/**
	 * tab页ID
	 */
	protected Long tabId;
	/**
	 * 分区id
	 */
	protected Long partitionId;
	/**
	 * 表单
	 */
	protected Form form;
	/**
	 * 主列表对应的模板文件
	 */
	protected String template;
	/**
	 * 主列表的按钮
	 */
	protected List<TabulationButton> tabulationButtons;
	/**
	 * 表单的按钮
	 */
	protected List<FormButton> formButtons;
	/**
	 * 主列表选中项的集合
	 */
	protected String[] selectedVOs;
	/**
	 * 子列表选中项的集合
	 */
	protected String[] selectedSubVOs;
	/**
	 * 子列表表头
	 */
	protected List<FormColumnExtend> tabulationColumns;
	/**
	 * 子列表数据
	 */
	protected List<Domain> domains;
	/**
	 * 子列表分区DIV ID
	 */
	protected String partitionListDiv;

	/**
	 * 单列表DIV ID
	 */
	protected String listDiv;

	/**
	 * 主记录id
	 */
	protected Long domainId;
	/**
	 * 子记录
	 */
	protected Domain subDom;
	/**
	 * 子记录ID
	 */
	protected Long subDomainId;
	/**
	 * 模块名，一般为数据库表名称
	 */
	protected String model;
	/**
	 * 主表
	 */
	protected String mainTable;
	
	/**
	 * 主表简写
	 */
	protected String simpleModel;
	
	/**
	 * 默认字段的字段名
	 */
	protected String colName;
	/**
	 * 分区
	 */
	protected Partition partition;
	/**
	 * 表单分区DIV ID
	 */
	protected String partitionForm;
	/**
	 * 保存的当前记录的id
	 */
	protected Long currentSaveId;
	/**
	 * 查询类型
	 */
	protected String queryType;
	/**
	 * 模块所具有的规则
	 */
	protected String rule;

	/**
	 * 单个上传文件上传的文件
	 */
	protected File[] upload;
	/**
	 * 单个上传文件上传的文件类型
	 */
	protected String[] uploadContentType;
	/**
	 * 单个上传文件上传的文件名称
	 */
	protected String[] uploadFileName;

	/**
	 * 是否有默认的 查询条件
	 */
	protected boolean hasDefaultQuery = false;

	/**
	 * 表单参数
	 */
	protected String params = null;
	/**
	 * 查询条件为String的Map集合,支持模糊查询
	 */
	protected Map<String, String> dyncMapString = new HashMap<String, String>();
	/**
	 * 不作为查询条件，仅作为显示用
	 */
	protected Map<String, String> dyncMapTempString = new HashMap<String, String>();
	/**
	 * 查询条件为String的Map集合，支持精确查询
	 */
	protected Map<String, String> dyncMapStringPrecise = new HashMap<String, String>();
	/**
	 * 查询条件为String的Map集合，用于搜索下拉框
	 */
	protected Map<String, String> dyncMapStringCombobox = new HashMap<String, String>();
	/**
	 * 查询条件为Integer的Map集合，支持精确查询
	 */
	protected Map<String, Integer> dyncMapInteger = new HashMap<String, Integer>();
	/**
	 * 查询条件为Long的Map集合，支持精确查询
	 */
	protected Map<String, Long> dyncMapLong = new HashMap<String, Long>();
	/**
	 * 查询条件为Date的Map集合，Date以String格式传入
	 */
	protected Map<String, String> dyncMapDate = new HashMap<String, String>();

	/**
	 * 操作列表的服务接口,<code>tabulationService</code> 对象是TabulationService接口的一个实例
	 */
	@Resource
	protected TabulationService tabulationService;
	/**
	 * 操作表单的服务接口,<code>formService</code> 对象是FormService接口的一个实例
	 */
	@Resource
	protected FormService formService;
	/**
	 * 操作配置模块的服务接口,<code>compexDomainService</code>
	 * 对象是CompexDomainService接口的一个实例
	 */
	@Resource
	protected CompexDomainService compexDomainService;
	/**
	 * 操作分区的服务接口,<code>partitionService</code> 对象是PartitionService接口的一个实例
	 */
	@Resource
	protected PartitionService partitionService;
	/**
	 * 操作目录的服务接口,<code>catalogService</code> 对象是CatalogService接口的一个实例
	 */
	@Resource
	protected CatalogService catalogService;
	
	@Resource
	protected SysPrivilegeService sysPrivilegeService;
	/**
	 * 操作树模板的服务接口,<code>mgrTreeService</code> 对象是MgrTreeService接口的一个实例
	 */
	@Resource
	protected MgrTreeService mgrTreeService;
	/**
	 * 操作系统元素的服务接口,<code>systemAtomService</code> 对象是SystemAtomService接口的一个实例
	 */
	@Resource
	protected SystemAtomService systemAtomService;
	/**
	 * 操作代码的服务接口,<code>dictionaryService</code> 对象是DictionaryService接口的一个实例
	 */
	@Resource
	protected DictionaryService dictionaryService;
	/**
	 * 操作编码的服务接口,<code>seqcodeService</code> 对象是SeqcodeService接口的一个实例
	 */
	@Resource
	protected SeqcodeService seqcodeService;
	/**
	 * 操作附件的服务接口,<code>attachmentService</code> 对象是AttachmentService接口的一个实例
	 */
	@Resource
	protected AttachmentService attachmentService;
	/**
	 * 操作用户信息的服务接口,<code>userService</code> 对象是IUserService接口的一个实例
	 */
	@Resource
	protected IUserService userService;
	/**
	 * 操作表属性的服务接口,<code>tableService</code> 对象是TableService接口的一个实例
	 */
	@Resource
	protected TableService tableService;
	/**
	 * 人员选择构件服务接口,<code>personChioseService</code> 对象是PersonChioseService接口的一个实例
	 */
	@Resource
	protected PersonChioseService personChioseService;

	@Resource
	protected RelationService relationService;

	@Resource
	private Properties configproperties;

	/**
	 * 主列表，根据查询条件过滤数据，显示在列表中
	 * 
	 * @return forward
	 */
	public String list() {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		String orderField = getRequest().getParameter("orderField");
		String orderDirection = getRequest().getParameter("orderDirection");
		if (orderField != null && orderDirection != null) {
			queryCriteria.setOrderField(orderField);
			queryCriteria.setOrderDirection(orderDirection);
			getRequest().setAttribute("orderDirection", orderDirection);
		}
		// 通过while循环构造查询条件,dyncMapString,dyncMapStringPrecise,dyncMapLong都是不同的查询条件集合
		@SuppressWarnings("rawtypes")
		Iterator iterator = dyncMapString.entrySet().iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			if (entry.getValue() != null && !"".equals(entry.getValue()) && !"-1".equals(entry.getValue())) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), "%" + ((String) entry.getValue()).trim() + "%");
			}
		}
		@SuppressWarnings("rawtypes")
		Iterator iteratorp = dyncMapStringPrecise.entrySet().iterator();
		while (iteratorp.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iteratorp.next();
			if (dyncMapStringCombobox.containsKey(entry.getKey())) {
				String flag = dyncMapStringCombobox.get(entry.getKey());
				if ("false".equals(flag)) {
					queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), new Double(Math.E).toString());
				} else if ("true".equals(flag) && !"-1".equals(entry.getValue())) {
					queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), entry.getValue());
				}
			} else {
				if (entry.getValue() != null && !"".equals(entry.getValue()) && !"-1".equals(entry.getValue())) {
					queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), entry.getValue());
				}
			}
		}
		@SuppressWarnings("rawtypes")
		Iterator iteratorLong = dyncMapLong.entrySet().iterator();
		while (iteratorLong.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Long> entry = (Map.Entry<String, Long>) iteratorLong.next();
			if (entry.getValue() != null && !"".equals(entry.getValue()) && -1L != entry.getValue()) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), entry.getValue());
			}
		}

		@SuppressWarnings("rawtypes")
		Iterator iteratorInteger = dyncMapInteger.entrySet().iterator();
		while (iteratorInteger.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iteratorInteger.next();
			if (entry.getValue() != null && !"".equals(entry.getValue()) && -1 != entry.getValue()) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), entry.getValue());
			}
		}
		@SuppressWarnings("rawtypes")
		Iterator iteratord = dyncMapDate.entrySet().iterator();
		List<String> keyList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		while (iteratord.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iteratord.next();
			if (entry.getValue() != null && !"".equals(entry.getValue())) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), entry.getValue());
				keyList.add(entry.getKey());
				valueList.add(entry.getValue());
			}
		}

		// 判断规则
		Catalog catalog = catalogService.findCatalogByListId(listId);
		if (catalog != null) {
			rule = catalog.getRule();
			if (rule != null) {
				if (log.isDebugEnabled()) {
					log.debug("当前" + catalog.getName() + "的规则为：" + rule);
				}
				if (!user.isSuper() && rule.equals(Constants.RULE_NO_READ_WRITE)) {
					queryCriteria.addQueryCondition("comm_createBy", user.getId());
				}
			}
		}
		// 设置当前页
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1) * this.numPerPage + 1);
		// 设置每页记录数
		queryCriteria.setPageSize(this.numPerPage);
		try {
			// 通过列表ID，查询条件及客户信息查询出列表的信息
			tabulation = tabulationService.findTabulationByListId(Long.valueOf(listId), queryCriteria, user);

			if (tabulation != null) {
				// 判断是否有默认查询条件
				for (int i = 0; i < tabulation.getTabulationColumnExtends().size(); i++) {
					if (tabulation.getTabulationColumnExtends().get(i).getFormColumn().getIsDefaultQuery() == 1) {
						hasDefaultQuery = true;
						break;
					}
				}
				// 判断是否是高级查询
				List<FormColumnExtend> queryColumnExtends = new ArrayList<FormColumnExtend>();
				for (int i = 0; i < tabulation.getTabulationColumnExtends().size(); i++) {
					FormColumnExtend fce = tabulation.getTabulationColumnExtends().get(i);
					if (fce.getFormColumn().getIsQuery() == 1) {
						queryColumnExtends.add(fce);
					}
				}
				tabulation.setQueryColumnExtends(queryColumnExtends);
			}

			// 取出主列表对应的模板文件
			template = tabulation.getTemplateFileName();
			// 取出列表上所有的按钮
			tabulationButtons = tabulation.getTabulationButtons();
			// 取出列表关联的表单ID
			formId = tabulation.getFormId();
			// 根据表单ID查询出列表关联的表单
			form = formService.findFormById(formId);
			// 取出表单关联的主表
			model = form.getTableName();

			// 返回分页结果
			this.pageResult = new PageResult();
			this.pageResult.setContent(tabulation.getDomains());
			this.pageResult.setTotalCount(tabulation.getTotalCount());
			this.pageResult.setPageSize(this.numPerPage);
			this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
			this.pageResult.setCountPage((tabulation.getDomains().size() + this.numPerPage - 1) / this.numPerPage);

		} catch (Exception e) {
			e.printStackTrace();
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("list:" + e.getMessage());
			}
		}
		return "list";
	}

	/**
	 * 打开新建页面
	 * 
	 * @return forward
	 */
	public String add() {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			template = "compexDomainEdit.jsp";
			List<DomainVO> domainVOs = new ArrayList<DomainVO>();
			// 通过表单ID查找表单信息
			this.form = formService.getFormByIdAndDomainVO(formId, domainVOs, user);
			this.model = this.form.getTableName();
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("add:" + e.getMessage());
			}
		}
		return "add";
	}

	/**
	 * 预览表单显示效果
	 * 
	 * @return forward
	 * @throws Exception
	 */
	public String preview() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON(getText("user.login"), false);
			return NONE;
		}

		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		// 通过表单ID查找表单信息
		this.form = formService.getFormByIdAndDomainVO(formId, domainVOs, user);
		return "preview";
	}

	/**
	 * 编辑表单页面
	 * 
	 * @return forward
	 * @throws Exception
	 */
	public String edit() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON(getText("user.login"), false);
			return NONE;
		}

		template = "compexDomainEdit.jsp";
		// 通过表单ID查找表单信息
		this.form = formService.getFormByIdAndDomainVO(formId, getDomainVos(this.params), user);
		this.model = this.form.getTableName();
		
		return "edit";
	}

	/**
	 * 判断是否有编辑的权限,返回false或true
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String isEdit() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		String flag = "false";

		if (!user.isSuper() && (rule == null || rule.equals(Constants.RULE_READ_NO_WRITE))) {
			this.form = formService.getFormByIdAndDomainVO(formId, getDomainVos(this.params), user);
			try {
				List<FormColumnExtend> formColumnExtends = form.getTabs().get(0).getFormColumnExtends();
				for (FormColumnExtend formColumnExtend : formColumnExtends) {
					if (formColumnExtend.getFormColumn().getColumnName().equals("id")) {
						String _id = formColumnExtend.getValue().toString();
						List<Map> _lstResult = compexDomainService.queryTableData(formColumnExtend.getFormColumn().getBelongTable(), "id:" + _id);
						if (_lstResult != null && _lstResult.size() > 0) {
							for (int i = 0; i < _lstResult.size(); ++i) {
								Map mapEntries = (Map) _lstResult.get(i);
								Iterator itEntries = mapEntries.entrySet().iterator();
								while (itEntries.hasNext()) {
									Map.Entry entry = (Map.Entry) itEntries.next();
									String _sKey = "";
									String _sValue = "";
									if (entry.getKey() != null && entry.getValue() != null) {
										_sKey = entry.getKey().toString();
										_sValue = entry.getValue().toString();
										if (_sKey.equalsIgnoreCase("comm_createBy") && _sValue.equals(String.valueOf(user.getId()))) {
											flag = "true";
											break;
										}
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					e.printStackTrace();
					log.debug("isEdit:" + e.getMessage());
				}
				flag = "false";
			}
		} else {
			flag = "true";
		}

		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, flag);
		return NONE;
	}

	/**
	 * 新建和编辑时返回Json的表单元素数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String dataJson() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON(getText("user.login"), false);
			return NONE;
		}

		// 通过表单ID和表单参数查找表单信息
		this.form = formService.getFormByIdAndDomainVO(formId, getDomainVos(this.params), user);
		this.model = form.getTableName();
		
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, this.form);

		return NONE;
	}

	/**
	 * 维护子tab页列表中的数据
	 * 
	 * @return forward
	 * @throws Exception
	 */
	public String editsub() throws Exception {
		template = "compexDomainSubEdit.jsp";

		String op = getRequest().getParameter("op");
		getRequest().setAttribute("op", op);
		return "editsub";
	}

	/**
	 * 返回Json的分区数据，分区包含表单元素信息
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String dataJsonsub() throws Exception {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			if (user == null) {
				printJSON(getText("user.login"), false);
				return NONE;
			}

			// 查找分区信息
			partition = formService.findDomainById(model, tabId, partitionId, subDomainId, user);
			partition.setTabId(tabId);

			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter printWriter = response.getWriter();
			objectMapper.writeValue(printWriter, partition);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("dataJsonsub:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * 查看记录时的表单元素json数据
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String viewJson() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON(getText("user.login"), false);
			return NONE;
		}

		// 通过表单ID和表单参数查找表单信息
		this.form = formService.getFormByIdAndDomainVO(formId, getDomainVos(this.params), user);
		this.model = this.form.getTableName();
		
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, this.form);

		return NONE;
	}

	/**
	 * 解析字符串为DomainVO
	 * 
	 * @param params
	 *            格式为[表名_字段名:字段值;表名_字段名:字段值;]
	 * @return 表单参数
	 */
	public List<DomainVO> getDomainVos(String params) {// aaaa_id;
		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		if (params != null && !"".equals(params)) {
			String[] ps = params.split(";");
			for (String p : ps) {
				String[] vos = p.split(":");
				DomainVO vo = new DomainVO(vos[0].substring(0, vos[0].indexOf("-")), Long.valueOf(vos[1]));
				domainVOs.add(vo);
			}
		}
		return domainVOs;
	}

	/**
	 * 保存单条记录 step1:根据表单ID找到表单相关信息 step2：通过表单中的字段信息，取出相关的值 step3：保存表单的相关信息
	 * step4：需要添加编码的模块添加编码
	 * 
	 * @return NONE
	 */
	public String save() throws Exception {
		try {
			Long id = simpleSave();
			printJSON("success", false, id.toString());
		} catch (AppException e) {
			printJSON("301", e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isDebugEnabled()) {
				log.debug("save:" + e.getMessage());
			}
			printJSON("fail");
		}
		return NONE;
	}

	/**
	 * 保存单条记录 ,不输出任何信息，由子Action做输出操作 step1:根据表单ID找到表单相关信息
	 * step2：通过表单中的字段信息，取出相关的值 step3：保存表单的相关信息 step4：需要添加编码的模块添加编码
	 * 
	 * @return NONE
	 */
	public Long simpleSave() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		// 构造domain id
		List<DomainVO> vos = getDomainVos(this.params);
		// 创建表单对应的domain
		HttpServletRequest request = getRequest();
		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		Form form = formService.getEditFormByIdAndDomainVO(formId, domainVOs, user);

		if (vos.size() == 0) {
			if (domainId != null && !"".equals(domainId)) {
				DomainVO domainVo = new DomainVO();
				domainVo.setTable(form.getTableName());
				domainVo.setDomainId(domainId);
				vos.add(domainVo);
			}
		}

		List<Tab> tabs = form.getTabs();
		Tab currentTab = null;
		for (Tab tab : tabs) {
			if (tab.getId().equals(tabId)) {
				currentTab = tab;
			}
		}

		List<FormColumnExtend> fces = currentTab.getFormColumnExtends();
		List<Domain> domains = new ArrayList<Domain>();
		List<String> tables = new ArrayList<String>();

		String uploadDir = "";
		String ext = new Long(System.currentTimeMillis()).toString();
		// 表单中是否有单文件上传组件，有则做相关操作
		uploadDir = singleFileUpload(domains, fces, tables, vos, form, ext, uploadDir);
		// 从表单中取值，赋给domain
		int uploadNum = 0;
		for (FormColumnExtend fce : fces) {
			String res[] = request.getParameterValues(fce.getFormColumn().getBelongTable() + "-" + fce.getFormColumn().getColumnName());
			String result = request.getParameter(fce.getFormColumn().getBelongTable() + "-" + fce.getFormColumn().getColumnName());
			if (res != null && res.length > 1) {
				String r = "";
				for (int i = 0; i < res.length; i++) {
					r += res[i] + ",";
				}
				r = r.substring(0, r.length() - 1);
				result = r;
			}
			if ("".equals(result) || result == null) {
				if (fce.getFormColumn().getInputType() == Constants.INPUT_TYPE_TEXTBOX_FILE) {
					if (this.uploadFileName != null && this.uploadFileName.length > uploadNum) {
						String _sFileName = fce.getFormColumn().getColumnName() + "@" + ext
								+ uploadFileName[uploadNum].substring(uploadFileName[uploadNum].lastIndexOf("."));
						result = uploadDir + "/" + _sFileName;
					}
					uploadNum++;
				} else {
					if (fce.getFormColumn().getColumnName().equalsIgnoreCase("comm_updateBy")
							|| fce.getFormColumn().getColumnName().equalsIgnoreCase("comm_updateDate")
							|| fce.getFormColumn().getColumnName().equalsIgnoreCase("comm_createBy")
							|| fce.getFormColumn().getColumnName().equalsIgnoreCase("comm_createDate")) {
						continue;
					}
				}
			}

			for (Domain domain : domains) {
				if (fce.getFormColumn().getBelongTable().equals(domain.getTable().getTableName())) {
					if (result != null && !result.equals("")) {
						// 判断是否为密码框，若为密码框则进行加密
						if (fce.getFormColumn().getInputType() == Constants.Input_TYPE_TEXTBOX_PASSWORD) {
							result = EncryptUtil.shaHex(result);
						}
						if ("int".equals(fce.getFormColumn().getDataType())) {
							fce.setValue(Integer.valueOf(result));
						} else if ("bigint".equals(fce.getFormColumn().getDataType())) {
							fce.setValue(Long.valueOf(result));
						} else if ("date".equals(fce.getFormColumn().getDataType())) {
							fce.setValue(DateUtil.convertStringToDate(result));
						} else {
							fce.setValue(result);
						}
					}
					if (fce.getFormColumn().getInputType() == Constants.INPUT_TYPE_TEXTBOX_FILE && upload != null) {
						domain.getFormColumnExtends().add(fce);
					} else if (fce.getFormColumn().getInputType() != Constants.INPUT_TYPE_TEXTBOX_FILE) {
						domain.getFormColumnExtends().add(fce);

						// 判断是否允许重复
						if (fce.getFormColumn().getCanReply() == 0) {
							String value = request.getParameter(fce.getFormColumn().getBelongTable() + "-" + fce.getFormColumn().getColumnName());
							List list = compexDomainService.findReplyResult(fce.getFormColumn().getBelongTable(),
									fce.getFormColumn().getColumnName(), value, domain.getId());

							if (list.size() > 0 && !"".equals(value.trim())) {
								throw new AppException(fce.getFormColumn().getColumnZhName() + " 不允许重复，请修改后重新提交！");
							}
						}
					}
				}
			}
		}

		// 添加公有字段
		addPublicColumn(domains, form, user);

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// 判断是否是树类型的表，若是树类型的表，则同一父节点下的兄弟节点不允许同名
		String _tableName = tables.get(0);
		Long _domainId = domains.get(0).getId();
		if (_tableName != null && !_tableName.equals("")) {
			String type = tableService.findTableTypeByName(_tableName);
			if (type.equals("4")) {
				String _name = request.getParameter(_tableName + "-tbl_name");
				String _parentId = request.getParameter(_tableName + "-tbl_parentId");
				List list = compexDomainService.findTreeTypeReply(_tableName, _name, Long.valueOf(_parentId), _domainId);

				if (list.size() > 0) {
					throw new AppException("树类型兄弟节点不允许重复，请修改后重新提交！");
				}
			}
		}

		// 保存记录之前判断是否是目录管理模块，若是，则判断关联的列表是否已经被引用
		if (form.getTableName().equalsIgnoreCase("sys_catalog")) {
			String listid = request.getParameter("sys_catalog-tbl_listid");
			if (listid != null && !listid.equals("") && !listid.equals("undefined") && !listid.equals("-1")) {
				List list = catalogService.useListCount(listid, domains.get(0).getId());
				if (list.size() > 0) {
					throw new AppException("选择的列表已经被其他目录引用，请重新选择！");
				}
			}
			String treeid = request.getParameter("sys_catalog-tbl_treeid");
			List list = compexDomainService.queryTableData("sys_tree", "tbl_tabletype:1;id:" + treeid);
			if (list.size() > 0) {
				throw new AppException("你选择的为双数据表树，请重新选择！");
			}
		}

		// 清除脏数据
		try {
			for (String table : tables) {
				if (Constants.RESOURCE_TABLES.indexOf(table.toLowerCase()) != -1) {
					CacheUtil.clearAllSCache();
				} else if (Constants.THEME_TABLES.indexOf(table.toLowerCase()) != -1) {
					CacheUtil.clearSCache("themeCache");
					CacheUtil.clearSCache("domainCache");
				} else {
					CacheUtil.clearSCache("domainCache");
				}
			}
		} catch (Exception e) {
			log.info("cache cannot clear");
		}

		// 执行数据库保存或更新
		List<Long> ids = this.compexDomainService.doSaveOrUpdateDomain(domains, tables, vos, user);

		Long id = null;
		if (ids.size() > 0) {
			id = ids.get(0);
			currentSaveId = id;
			// 生成目录编码
			compexDomainService.doGeneratorCode(currentSaveId, domains, form, request);
		}
		return id;
	}

	/**
	 * 单文件上传
	 * 
	 * @param fces
	 *            表单字段扩展信息集合
	 * @param tables
	 *            表单的表名称集合
	 * @param vos
	 *            表单参数
	 * @throws Exception
	 */
	public String singleFileUpload(List<Domain> domains, List<FormColumnExtend> fces, List<String> tables, List<DomainVO> vos, Form form, String ext,
			String uploadDir) throws Exception {
		boolean isUpload = false;// 是否上传文件
		String columnName = "";
		for (FormColumnExtend fce : fces) {
			if (!tables.contains(fce.getFormColumn().getBelongTable())) {
				Domain domain = new Domain();
				domain.setTable(new Table(fce.getFormColumn().getBelongTable()));
				List<FormColumnExtend> list = new ArrayList<FormColumnExtend>();
				domain.setFormColumnExtends(list);
				for (int i = 0; vos != null && i < vos.size(); i++) {
					if (vos.get(i).getTable().equals(fce.getFormColumn().getBelongTable())) {
						domain.setId(vos.get(i).getDomainId());
					}
				}
				domains.add(domain);
				tables.add(fce.getFormColumn().getBelongTable());
			}

			if (fce.getFormColumn().getInputType() == Constants.INPUT_TYPE_TEXTBOX_FILE) {
				isUpload = true;
				columnName = fce.getFormColumn().getColumnName();
			}
		}

		// 上传文件
		if (isUpload && upload != null) {
			String uploadPath = form.getTableName() + "Path";
			if (configproperties.getProperty(uploadPath) == null) {
				uploadDir = configproperties.getProperty("uploadDir");
			} else {
				uploadDir = configproperties.getProperty(uploadPath);
			}
			for (int i = 0; i < upload.length; i++) {
				String _sFileName = columnName + "@" + ext + uploadFileName[i].substring(uploadFileName[i].lastIndexOf("."));
				File target = new File(getRequest().getRealPath(uploadDir), _sFileName);
				FileUtils.copyFile(upload[i], target);
			}
		}
		return uploadDir;
	}

	/**
	 * 添加公有字段(创建人，创建时间，修改人，修改时间)
	 */
	public void addPublicColumn(List<Domain> domains, Form form, SysUser user) {
		for (Domain domain : domains) {
			if (domain.getTable().getTableName().equals(form.getTableName())) {
				if (domain.getId() != null && !domain.getId().equals("")) {
					FormColumnExtend fceUpdateBy = new FormColumnExtend();
					FormColumn fcUpdateBy = new FormColumn();
					fcUpdateBy.setColumnName("comm_updateBy");
					fceUpdateBy.setFormColumn(fcUpdateBy);
					fceUpdateBy.setValue(user.getId());

					FormColumnExtend fceUpdateDate = new FormColumnExtend();
					FormColumn fcUpdateDate = new FormColumn();
					fcUpdateDate.setColumnName("comm_updateDate");
					fceUpdateDate.setFormColumn(fcUpdateDate);
					fceUpdateDate.setValue(new Date());
					domain.getFormColumnExtends().add(fceUpdateBy);
					domain.getFormColumnExtends().add(fceUpdateDate);
				} else {
					FormColumnExtend fceCreateBy = new FormColumnExtend();
					FormColumn fcCreateBy = new FormColumn();
					fcCreateBy.setColumnName("comm_createBy");
					fceCreateBy.setFormColumn(fcCreateBy);
					fceCreateBy.setValue(user.getId());

					FormColumnExtend fceCreateDate = new FormColumnExtend();
					FormColumn fcCreateDate = new FormColumn();
					fcCreateDate.setColumnName("comm_createDate");
					fceCreateDate.setFormColumn(fcCreateDate);
					fceCreateDate.setValue(new Date());
					domain.getFormColumnExtends().add(fceCreateBy);
					domain.getFormColumnExtends().add(fceCreateDate);

					FormColumnExtend fceUpdateBy = new FormColumnExtend();
					FormColumn fcUpdateBy = new FormColumn();
					fcUpdateBy.setColumnName("comm_updateBy");
					fceUpdateBy.setFormColumn(fcUpdateBy);
					fceUpdateBy.setValue(user.getId());

					FormColumnExtend fceUpdateDate = new FormColumnExtend();
					FormColumn fcUpdateDate = new FormColumn();
					fcUpdateDate.setColumnName("comm_updateDate");
					fceUpdateDate.setFormColumn(fcUpdateDate);
					fceUpdateDate.setValue(new Date());
					domain.getFormColumnExtends().add(fceUpdateBy);
					domain.getFormColumnExtends().add(fceUpdateDate);
				}
			}
		}
	}

	// /**
	// * 为目录添加基本码，表单，列表添加扩展码和上级码
	// */
	// public void generatorCode(List<Domain> domains,Form
	// form,HttpServletRequest request){
	// if (form.getTableName().equalsIgnoreCase("sys_catalog")) {
	// Domain domain = domains.get(0);
	// if (domain.getId() == null || domain.getId().equals("")) {
	// Seqcode seqcode = new Seqcode();
	// seqcode.setTblName(request.getParameter("sys_catalog-tbl_name"));
	// seqcode.setTblSystematom("17");
	//
	// String bianma = seqcodeService.doSaveSeqcode(seqcode);
	// compexDomainService.doUpdateCode("sys_catalog", "tbl_bianma",
	// String.valueOf(currentSaveId),
	// bianma);
	// // 保存上级码和扩展码
	// String _parentId = request.getParameter("sys_catalog-tbl_parentId");
	// Catalog _parentCata = catalogService.findCatalogById(_parentId);
	// seqcodeService.doUpdateSeq(bianma, _parentCata.getCode());
	// seqcodeService.doUpdateSeqExtends(bianma, _parentCata.getCode());
	//
	// String listid = request.getParameter("listid");
	// Catalog catalog = catalogService.findCatalogById(currentSaveId);
	// // 目录表中保存关联的列表id
	// catalogService.doUpdateCatalogList(currentSaveId, listid);
	// if (listid != null && !listid.equals("") && !listid.equals("undefined"))
	// {
	// // 给列表加上级码
	// Tabulation _tabulation = tabulationService.findTabulationById(listid);
	// seqcodeService.doUpdateSeq(_tabulation.getCode(), catalog.getCode());
	// seqcodeService.doUpdateSeqExtends(_tabulation.getCode(),
	// catalog.getCode());
	// // 给表单加上级码
	// if (_tabulation.getFormId() != null &&
	// !_tabulation.getFormId().equals(0L)) {
	// Form _form = formService.findFormById(_tabulation.getFormId());
	// seqcodeService.doUpdateSeq(_form.getCode(), catalog.getCode());
	// seqcodeService.doUpdateSeqExtends(_form.getCode(), catalog.getCode());
	// }
	// }
	// } else {
	// String listid = request.getParameter("listid");
	// Catalog catalog = catalogService.findCatalogById(domain.getId());
	// seqcodeService.doUpdateSeqName(catalog.getCode(), catalog.getName());//
	// 更新编码表中的名称
	//
	// // 保存上级码和扩展码
	// Catalog _parentCata =
	// catalogService.findCatalogById(catalog.getParentId());
	// seqcodeService.doUpdateSeq(catalog.getCode(), _parentCata.getCode());
	// seqcodeService.doUpdateSeqExtends(catalog.getCode(),
	// _parentCata.getCode());
	//
	// // 目录表中保存关联的列表id
	// catalogService.doUpdateCatalogList(currentSaveId, listid);
	// if (listid != null && !listid.equals("") && !listid.equals("undefined"))
	// {
	// // 给列表加上级码
	// Tabulation _tabulation = tabulationService.findTabulationById(listid);
	// seqcodeService.doUpdateSeq(_tabulation.getCode(), catalog.getCode());
	// seqcodeService.doUpdateSeqExtends(_tabulation.getCode(),
	// catalog.getCode());
	// // 给表单加上级码
	// if (_tabulation.getFormId() != null &&
	// !_tabulation.getFormId().equals(0L)) {
	// Form _form = formService.findFormById(_tabulation.getFormId());
	// seqcodeService.doUpdateSeq(_form.getCode(), catalog.getCode());
	// seqcodeService.doUpdateSeqExtends(_form.getCode(), catalog.getCode());
	// }
	// }
	// }
	// }
	// }

	/**
	 * 查看单条记录
	 * 
	 * @return forward
	 * @throws Exception
	 */
	public String view() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON(getText("user.login"), false);
			return NONE;
		}
		
		template = "compexDomainView.jsp";
		try {
			this.form = formService.getFormByIdAndDomainVO(formId, getDomainVos(this.params), user);
			this.model = this.form.getTableName();
		} catch (Exception e) {
			
			if (log.isDebugEnabled()) {
				log.debug("view:" + e.getMessage());
			}
		}
		String t = getDomainVos(this.params).toString();
		return "view";
	}

	/**
	 * 发布记录，主要用于各种构件和组件模块，添加后需发布才能生效
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String passed() throws Exception {
		List<DomainVO> vos = getDomainVos(this.params);
		String tableName = vos.get(0).getTable();
		Long recordId = vos.get(0).getDomainId();
		if (tableName != null && !"".equalsIgnoreCase(tableName) && recordId != null) {
			compexDomainService.doPassed(tableName, recordId);
		}
		return NONE;
	}

	/**
	 * 撤回记录，主要用于各种构件和组件模块
	 * 
	 * @return
	 * @throws Exception
	 */
	public String recall() throws Exception {
		List<DomainVO> vos = getDomainVos(this.params);
		String tableName = vos.get(0).getTable();
		Long recordId = vos.get(0).getDomainId();
		if (tableName != null && !"".equalsIgnoreCase(tableName) && recordId != null) {
			compexDomainService.doRecall(tableName, recordId);
		}
		return NONE;
	}

	/**
	 * 删除单条或者多条主列表中的记录
	 * 
	 * @return NONE
	 * @throws IOException
	 */
	public String delete() throws IOException {
		try {
			this.simpleDelete();
			printJSON("success");
		} catch (AppException e) {
			printJSON("300", e.getMessage(), false);
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
	 * 删除单条或者多条主列表中的记录,不会输出操作提示，由子Action做输出操作
	 * 
	 * @return NONE
	 * @throws IOException
	 */
	public String simpleDelete() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (selectedVOs != null) {
			boolean flag = false;
			if (!user.isSuper()) {
				for (String vo : selectedVOs) {
					if (flag) {
						break;
					}
					List<Map> _lstResult = compexDomainService.queryTableData(vo.split("-")[0], "id:" + vo.split(":")[1]);
					if (_lstResult != null && _lstResult.size() > 0) {
						for (int i = 0; i < _lstResult.size(); ++i) {
							Map mapEntries = (Map) _lstResult.get(i);
							Iterator itEntries = mapEntries.entrySet().iterator();
							while (itEntries.hasNext()) {
								Map.Entry entry = (Map.Entry) itEntries.next();
								String _sKey = "";
								String _sValue = "";
								if (entry.getKey() != null && entry.getValue() != null) {
									_sKey = entry.getKey().toString();
									_sValue = entry.getValue().toString();
									if (_sKey.equalsIgnoreCase("comm_createBy") && !_sValue.equals(String.valueOf(user.getId()))) {
										flag = true;
										break;
									}
								}
							}
						}
					}
				}
			}
			if (flag) {
				throw new AppException("你删除的数据中存在其他用户建立的数据,请过滤后在删除!");
			} else {
				for (String vo : selectedVOs) {
					List<DomainVO> ds = getDomainVos(vo);
					// 如果删除目录，则删除编码表中对应的编码
					if (ds.get(0).getTable().equalsIgnoreCase("sys_catalog")) {
						Long catalogId = ds.get(0).getDomainId();
						Catalog catalog = catalogService.findCatalogById(Long.valueOf(catalogId));
						seqcodeService.doDeleteSeq(catalog.getCode());
						List<SysPrivilege> privilegeIds = sysPrivilegeService.findAllPrivilegeByModule(catalogId);
						if(privilegeIds!=null && privilegeIds.size()>0) {
							List<Long> pids = new ArrayList<Long>();
							for(SysPrivilege sp:privilegeIds) {
								pids.add(sp.getId());
							}
							Long[] ids = new Long[pids.size()];
							pids.toArray(ids); 
							sysPrivilegeService.doDeletePrivileges(ids);
						}
						
					}
					this.compexDomainService.doDeleteDomains(ds, formId, user);
				}

				// 清除脏数据
				try {
					for (String vo : selectedVOs) {
						String table = vo.split("-")[0];
						if (Constants.RESOURCE_TABLES.indexOf(table.toLowerCase()) != -1) {
							CacheUtil.clearAllSCache();
						} else if (Constants.THEME_TABLES.indexOf(table.toLowerCase()) != -1) {
							CacheUtil.clearSCache("themeCache");
							CacheUtil.clearSCache("domainCache");
						} else {
							CacheUtil.clearSCache("domainCache");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.info("cache cannot clear");
				}
			}
		}
		return NONE;
	}

	/**
	 * 删除单条或者多条主列表中的记录
	 * 
	 * @return NONE
	 * @throws IOException
	 */
	public String logicDelete() throws IOException {
		try {
			this.simpleLogicDelete();
			printJSON("success");
		} catch (AppException e) {
			printJSON("300", e.getMessage(), false);
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
	 * 删除单条或者多条主列表中的记录,不会输出操作提示，由子Action做输出操作
	 * 
	 * @return NONE
	 * @throws IOException
	 */
	public String simpleLogicDelete() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (selectedVOs != null) {
			boolean flag = false;
			if (!user.isSuper()) {
				for (String vo : selectedVOs) {
					if (flag) {
						break;
					}
					List<Map> _lstResult = compexDomainService.queryTableData(vo.split("-")[0], "id:" + vo.split(":")[1]);
					if (_lstResult != null && _lstResult.size() > 0) {
						for (int i = 0; i < _lstResult.size(); ++i) {
							Map mapEntries = (Map) _lstResult.get(i);
							Iterator itEntries = mapEntries.entrySet().iterator();
							while (itEntries.hasNext()) {
								Map.Entry entry = (Map.Entry) itEntries.next();
								String _sKey = "";
								String _sValue = "";
								if (entry.getKey() != null && entry.getValue() != null) {
									_sKey = entry.getKey().toString();
									_sValue = entry.getValue().toString();
									if (_sKey.equalsIgnoreCase("comm_createBy") && !_sValue.equals(user.getId())) {
										flag = true;
										break;
									}
								}
							}
						}
					}
				}
			}
			if (flag) {
				throw new AppException("你删除的数据中存在其他用户建立的数据,请过滤后在删除!");
			} else {
				for (String vo : selectedVOs) {
					List<DomainVO> ds = getDomainVos(vo);
					// 如果删除目录，则删除编码表中对应的编码
					if (ds.get(0).getTable().equalsIgnoreCase("sys_catalog")) {
						Long catalogId = ds.get(0).getDomainId();
						Catalog catalog = catalogService.findCatalogById(Long.valueOf(catalogId));
						seqcodeService.doLogicDeleteSeq(catalog.getCode());
					}
					this.compexDomainService.doLogicDeleteDomains(ds, formId, user);
				}
			}
		}
		return NONE;
	}

	/**
	 * 子列表，根据查询条件过滤数据，显示在子列表中
	 * 
	 * @return forward
	 */
	public String listsub() {
		try {
			// 获取登录用户信息
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			// 根据选项卡和分区获得子列表的字段
			this.tabulationColumns = compexDomainService.findSubListColumn(tabId, partitionId);
			// 根据分区ID查找分区
			Partition partition = partitionService.findPartitionById(partitionId);
			// 取出分区对应的模板文件
			this.template = partition.getTemplateFileName();

			String op = getRequest().getParameter("op");
			getRequest().setAttribute("op", op);

			String model = null;
			if (this.tabulationColumns != null && this.tabulationColumns.size() > 0) {
				model = this.tabulationColumns.get(0).getFormColumn().getBelongTable();
			}

			queryCriteria = new QueryCriteria();
			if (this.pageNum == null || "".equals(this.pageNum)) {
				this.pageNum = 1;
			}
			// 设置当前页
			queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1) * this.numPerPage + 1);
			// 设置每页记录数
			queryCriteria.setPageSize(this.numPerPage);

			DomainVO vo = new DomainVO();
			List<DomainVO> vos = getDomainVos(this.params);
			if (vos != null && vos.size() > 0) {
				vo = vos.get(0);
			}
			// 查找子列表的数据
			List<Domain> domains = compexDomainService.queryDomain(queryCriteria, tabId, partitionId, vo, user);

			this.pageResult = new PageResult();
			this.pageResult.setContent(domains);
			this.pageResult.setTotalCount(compexDomainService.countDomain(model, vo));
			this.pageResult.setPageSize(this.numPerPage);
			this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
			this.pageResult.setCountPage((domains.size() + this.numPerPage - 1) / this.numPerPage);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("listsub:" + e.getMessage());
			}
		}
		return "listsub";
	}

	/**
	 * 添加或者保存子记录
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String savesub() throws Exception {
		try {
			Long _domainId = this.simpleSavesub();
			printJSON("success", false, _domainId.toString());
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("savesub:" + e.getMessage());
			}
			printJSON("fail");
		}
		return NONE;
	}

	/**
	 * 添加或者保存子记录,不会输出是否操作成功的提示，可自己在子Action中做相关处理
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public Long simpleSavesub() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		// 构造domain id
		List<DomainVO> vos = getDomainVos(this.params);
		// 创建表单对应的domain
		HttpServletRequest request = getRequest();
		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		Form form = formService.getFormByIdAndDomainVO(formId, domainVOs, user);
		List<Tab> tabs = form.getTabs();
		Tab currentTab = null;
		for (Tab tab : tabs) {
			if (tab.getId().equals(tabId)) {
				currentTab = tab;
			}
		}

		List<Partition> partitions = currentTab.getPartitions();
		Partition p = null;
		for (Partition part : partitions) {
			if (part.getId().equals(partitionId)) {
				p = part;
				break;
			}
		}
		List<FormColumnExtend> fces = p.getFormColumnExtends();
		Domain domain = new Domain();
		for (FormColumnExtend fce : fces) {
			domain.setTable(new Table(fce.getFormColumn().getBelongTable()));
			List<FormColumnExtend> list = new ArrayList<FormColumnExtend>();
			domain.setFormColumnExtends(list);
		}

		String subid = request.getParameter(domain.getTable().getTableName() + "-id");
		if (subid != null && !"".equals(subid)) {
			domain.setId(Long.valueOf(subid));
		}

		// 从表单中取值，赋给domain
		for (FormColumnExtend fce : fces) {
			String result = request.getParameter(fce.getFormColumn().getBelongTable() + "-" + fce.getFormColumn().getColumnName());
			if ("".equals(result) || result == null) {
				continue;
			}
			if (fce.getFormColumn().getBelongTable().equals(domain.getTable().getTableName())) {
				if ("int".equals(fce.getFormColumn().getDataType())) {
					fce.setValue(result);
				} else if ("bigint".equals(fce.getFormColumn().getDataType())) {
					fce.setValue(Long.valueOf(result));
				} else if ("date".equals(fce.getFormColumn().getDataType())) {
					fce.setValue(DateUtil.convertStringToDate(result));
				} else {
					fce.setValue(result);
				}
				domain.getFormColumnExtends().add(fce);
			}
		}
		if (domain.getId() != null && !domain.getId().equals("")) {
			this.compexDomainService.doUpdateSub(domain, user);
		} else {
			this.compexDomainService.doSaveSub(domain, domainId, form.getTableName(), user);
		}
		// 更新主记录的修改人和修改时间
		compexDomainService.doUpdateMainInfo(form.getTableName(), domainId, user);
		return domainId;
	}

	/**
	 * 删除单条或者多条子列表中的记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deletesub() throws Exception {
		try {
			this.simpleDeletesub();
			printJSON("success");
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("deletesub:" + e.getMessage());
			}
			printJSON("fail");
		}
		return NONE;
	}

	/**
	 * 删除单条或者多条子列表中的记录,不会输出操作成功提示，可自己在子Action中做相关处理
	 * 
	 * @return
	 * @throws Exception
	 */
	public String simpleDeletesub() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (selectedSubVOs != null) {
			for (String vo : selectedSubVOs) {
				List<DomainVO> ds = getDomainVos(vo);
				this.compexDomainService.doDeleteDomains(ds, mainTable, formId, user);
			}
		}
		return NONE;
	}

	public String singleList() {
		try {
			// 获取登录用户信息
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			// 根据选项卡获得列表的字段
			this.tabulationColumns = compexDomainService.findSubListColumn(tabId, -1L);

			String model = null;
			Long relColumnId = null;
			if (this.tabulationColumns != null && this.tabulationColumns.size() > 0) {
				FormColumn _fc = this.tabulationColumns.get(0).getFormColumn();
				model = _fc.getBelongTable();
				relColumnId = _fc.getRelColumnId();
			}

			queryCriteria = new QueryCriteria();
			if (this.pageNum == null || "".equals(this.pageNum)) {
				this.pageNum = 1;
			}

			// 设置当前页
			queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1) * this.numPerPage + 1);
			// 设置每页记录数
			queryCriteria.setPageSize(this.numPerPage);

			DomainVO vo = new DomainVO();
			List<DomainVO> vos = getDomainVos(this.params);
			if (vos != null && vos.size() > 0) {
				vo = vos.get(0);
			}
			// 查找列表的数据
			List<Domain> domains = compexDomainService.querySingleDomain(queryCriteria, tabId, vo, user);

			this.pageResult = new PageResult();
			this.pageResult.setContent(domains);
			this.pageResult.setTotalCount(compexDomainService.singleCountDomain(model, vo, relColumnId));
			this.pageResult.setPageSize(this.numPerPage);
			this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
			this.pageResult.setCountPage((domains.size() + this.numPerPage - 1) / this.numPerPage);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("singleList:" + e.getMessage());
			}
		}
		return "singleList";
	}

	/**
	 * 取得表单字段配置的树，转化成CommonTree返回
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String showTree() throws Exception {
		String mgrTreeId = getRequest().getParameter("mgrTreeId");
		MgrTree mgrTree = mgrTreeService.findMgrTreeById(Long.valueOf(mgrTreeId));
		String id = getRequest().getParameter("treeId");

		CommonTree commonTree = mgrTreeService.findCommonTreeByMgrTree(mgrTree, Long.valueOf(id));
		commonTree.setTableType(mgrTree.getTableType());

		if (mgrTree.getTableType().equals("1")) {
			if (mgrTree.getType() == 4L) {
				List<Person> _persons = userService.selectPersonNameByIds(id);
				String pName = "";
				for (int p = 0; p < _persons.size(); p++) {
					Person _person = _persons.get(p);
					pName += _person.getTblXingming() + ";";
				}
				commonTree.setName(pName);
			} else if (mgrTree.getType() == 5L) {
				List<SysUser> _users = userService.selectUserNameByIds(id);
				String pName = "";
				for (int p = 0; p < _users.size(); p++) {
					SysUser _user = _users.get(p);
					pName += _user.getFullname() + ";";
				}
				commonTree.setName(pName);
			}
		}
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, commonTree);

		return NONE;
	}

	public String getTreeTableType() throws Exception {
		String mgrTreeId = getRequest().getParameter("mgrTreeId");
		MgrTree mgrTree = mgrTreeService.findMgrTreeById(Long.valueOf(mgrTreeId));

		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, mgrTree);

		return NONE;
	}

	/**
	 * 根据用户ID，显示用户名称，返回当前用户
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String showUserName() throws Exception {
		SysUser user = (SysUser) userService.getUserResource("id", getRequest().getParameter("userid"), 0);
		// SysUser curruser = userService.getUser(user.getTblUsername(),
		// user.getTblPassword(), user.isSuper());

		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, user);

		return NONE;
	}

	/**
	 * 根据机构ID，显示机构名称，返回用户所属机构
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String showOrgName() throws Exception {
		Org org = userService.getOrgById(getRequest().getParameter("orgid"));

		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, org);

		return NONE;
	}

	/**
	 * 显示列表的帮助信息，主要介绍此列表有哪些按钮，显示字段等信息
	 * 
	 * @return forward
	 */
	public String showListHelp() {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		// 根据列表id取出列表信息
		QueryCriteria queryCriteria = new QueryCriteria();

		Tabulation tabulation = tabulationService.findTabulationByListId(Long.valueOf(listId), queryCriteria, user);

		// 根据列表id取出目录信息
		queryCriteria.setPageSize(-1);
		List catalogList = catalogService.queryCatalog(queryCriteria);
		Catalog catalog = new Catalog();
		for (int i = 0; i < catalogList.size(); i++) {
			Catalog cl = (Catalog) catalogList.get(i);
			if (cl.getPath() != null) {
				String catalogIdStr = "";
				int index = cl.getPath().indexOf("&");
				int listidIndex = cl.getPath().indexOf("listId");
				if (index > 0 && listidIndex > 0) {
					catalogIdStr = cl.getPath().substring(cl.getPath().indexOf("listId=") + 7, cl.getPath().indexOf("&"));
				} else if (index < 0 && listidIndex > 0) {
					catalogIdStr = cl.getPath().substring(cl.getPath().indexOf("listId=") + 7);
				}
				if (catalogIdStr.equals(listId.toString())) {
					catalog = cl;
				}
			}
		}

		// 根据目录id取出系统元素信息
		SystemAtom systemAtom = new SystemAtom();
		if (catalog.getId() != null && !catalog.getId().equals("")) {
			systemAtom = systemAtomService.getSystemAtomByCatalogId(String.valueOf(catalog.getId()));
		}

		// 列表显示几项信息
		int showcount = 0;

		for (int j = 0; j < tabulation.getTabulationColumnExtends().size(); j++) {
			if (tabulation.getTabulationColumnExtends().get(j).getFormColumn().getIsShowInList() == 1) {
				showcount += 1;
			}
		}
		int totalcount = showcount;
		if (tabulation.getIsSelect().equals(String.valueOf(1))) {
			totalcount += 1;
		}
		if (tabulation.getIsNumber().equals(String.valueOf(1))) {
			totalcount += 1;
		}
		if (tabulation.getIsModify().equals(String.valueOf(1))) {
			totalcount += 1;
		}

		getRequest().setAttribute("tabulation", tabulation);
		getRequest().setAttribute("catalog", catalog);
		getRequest().setAttribute("systemAtom", systemAtom);
		getRequest().setAttribute("showcount", showcount);
		getRequest().setAttribute("totalcount", totalcount);

		return "listhelp";
	}

	/**
	 * 显示表单的帮助信息，主要介绍此表单有哪些按钮，字段等信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public String showFormHelp() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON("userFail", false);
			return NONE;
		}

		List<DomainVO> formhelpDomains = new ArrayList<DomainVO>();
		Form helpForm = formService.getFormByIdAndDomainVO(formId, formhelpDomains, user);

		Catalog catalog = catalogService.findCatalogByFormId(helpForm.getId());
		getRequest().setAttribute("form", helpForm);
		getRequest().setAttribute("catalog", catalog);
		return "formhelp";
	}

	/**
	 * 公用数据库查询方法,以数据库字段名为key,返回一个json字符串
	 * 
	 * @return NONE
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String tableData() throws IOException {
		// mainTable
		List<Map> _lstResult = compexDomainService.queryTableData(mainTable, params);
		StringBuilder _sbdJson = new StringBuilder();
		_sbdJson.append("[");
		StringBuilder _sbdChar = new StringBuilder();
		if (_lstResult != null && _lstResult.size() > 0) {

			for (int i = 0; i < _lstResult.size(); ++i) {
				_sbdChar.append("{");
				Map mapEntries = (Map) _lstResult.get(i);
				Iterator itEntries = mapEntries.entrySet().iterator();
				while (itEntries.hasNext()) {
					Map.Entry entry = (Map.Entry) itEntries.next();
					String _sKey = "";
					String _sValue = "";
					if (entry.getKey() != null && entry.getValue() != null) {
						_sKey = entry.getKey().toString();
						_sValue = entry.getValue().toString();
						_sbdChar.append("\"" + _sKey + "\":");
						_sbdChar.append("\"" + _sValue + "\",");
					}
				}
				if (_sbdChar.length() > 1)
					_sbdChar.deleteCharAt(_sbdChar.length() - 1);
				_sbdChar.append("},");
			}
		}
		if (_sbdChar.length() > 1)
			_sbdChar.deleteCharAt(_sbdChar.length() - 1);
		_sbdJson.append(_sbdChar);
		_sbdJson.append("]");
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(_sbdJson.toString());
		out.flush();
		out.close();
		return NONE;
	}

	/**
	 * 设置默认，用于各种组件,构件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String enabled() throws Exception {
		colName = "tbl_enabled";
		return isDefault();
	}

	/**
	 * 设置默认，用于各种组件,构件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String isDefault() throws Exception {
		Long _sId = null;
		if (params != null) {
			if (params.indexOf(":") != -1) {
				_sId = Long.valueOf(params.split(":")[1].toString());
			} else {
				_sId = Long.valueOf(params);
			}

		}
		compexDomainService.doDefault(mainTable, colName, _sId);
		if (Constants.RESOURCE_TABLES.indexOf(mainTable.toLowerCase()) != -1) {
			CacheUtil.clearAllSCache();
		} else if (Constants.THEME_TABLES.indexOf(mainTable.toLowerCase()) != -1) {
			CacheUtil.clearSCache("themeCache");
			CacheUtil.clearSCache("domainCache");
		} else {
			CacheUtil.clearSCache("domainCache");
		}

		printJSON("success", "设置默认成功", true);
		return NONE;
	}

	/**
	 * 判断组件，构件是否被引用，如被引用则不能够删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public String isCanDelete() throws Exception {
		String canDelete = "true";
		if (model.equalsIgnoreCase("sys_textbox") || model.equalsIgnoreCase("sys_textarea") || model.equalsIgnoreCase("sys_combox")
				|| model.equalsIgnoreCase("sys_searchcombox") || model.equalsIgnoreCase("sys_uploadfilebox")
				|| model.equalsIgnoreCase("sys_passwordbox") || model.equalsIgnoreCase("sys_radiomgt") || model.equalsIgnoreCase("sys_checkboxmgt")
				|| model.equalsIgnoreCase("sys_tree") || model.equalsIgnoreCase("sys_riqizujian") || model.equalsIgnoreCase("sys_texteditor")
				|| model.equals("sys_uploadify") || model.equals("sys_codecasecade")) {
			String inputType = FormUtil.getInputTypeByModel(model);
			if (selectedVOs != null) {
				for (String vo : selectedVOs) {
					List<DomainVO> ds = getDomainVos(vo);
					List counts = formService.findIsUse(ds.get(0).getDomainId(), inputType);
					if (counts.size() > 0) {
						canDelete = "false";
						break;
					}
				}
			}
		} else if (model.equalsIgnoreCase("sys_button")) {
			if (selectedVOs != null) {
				for (String vo : selectedVOs) {
					List<DomainVO> ds = getDomainVos(vo);
					String res = formService.findIsUseButton(ds.get(0).getDomainId());
					if (res.equals("false")) {
						canDelete = res;
						break;
					}
				}
			}
		} else if (model.equalsIgnoreCase("sys_buttongroup")) {
			if (selectedVOs != null) {
				for (String vo : selectedVOs) {
					List<DomainVO> ds = getDomainVos(vo);
					String res = formService.findIsUseButtonGroup(ds.get(0).getDomainId());
					if (res.equals("false")) {
						canDelete = res;
						break;
					}
				}
			}
		} else if (model.equalsIgnoreCase("sys_chaxunzujian")) {
			if (selectedVOs != null) {
				for (String vo : selectedVOs) {
					List<DomainVO> ds = getDomainVos(vo);
					String res = formService.findIsUseQuery(ds.get(0).getDomainId());
					if (res.equals("false")) {
						canDelete = res;
						break;
					}
				}
			}
		} else if (model.equalsIgnoreCase("sys_gaojichaxun")) {
			if (selectedVOs != null) {
				for (String vo : selectedVOs) {
					List<DomainVO> ds = getDomainVos(vo);
					String res = formService.findIsUseAdvQuery(ds.get(0).getDomainId());
					if (res.equals("false")) {
						canDelete = res;
						break;
					}
				}
			}
		} else if (model.equalsIgnoreCase("sys_liebiaozujian")) {
			if (selectedVOs != null) {
				for (String vo : selectedVOs) {
					List<DomainVO> ds = getDomainVos(vo);
					String res = formService.findIsUseList(ds.get(0).getDomainId());
					if (res.equals("false")) {
						canDelete = res;
						break;
					}
				}
			}
		} else if (model.equalsIgnoreCase("sys_liebiaoselect") || model.equalsIgnoreCase("sys_liebiaoorder")
				|| model.equalsIgnoreCase("sys_liebiaooperation") || model.equalsIgnoreCase("sys_liebiaopagination")) {
			String columnname = "";
			if (model.equalsIgnoreCase("sys_liebiaoselect")) {
				columnname = "tbl_selectid";
			} else if (model.equalsIgnoreCase("sys_liebiaoorder")) {
				columnname = "tbl_orderid";
			} else if (model.equalsIgnoreCase("sys_liebiaooperation")) {
				columnname = "tbl_operationid";
			} else if (model.equalsIgnoreCase("sys_liebiaopagination")) {
				columnname = "tbl_paginationid";
			}
			if (selectedVOs != null) {
				for (String vo : selectedVOs) {
					List<DomainVO> ds = getDomainVos(vo);
					String res = formService.findIsUseListControl(ds.get(0).getDomainId(), columnname);
					if (res.equals("false")) {
						canDelete = res;
						break;
					}
				}
			}
		}
		// 包含默认数据时不能删除
		if (selectedVOs != null) {
			// 判断该模块是否包含默认字段
			boolean hb = compexDomainService.hasDefaultCol(model);
			String _column = "tbl_isdefault";
			if (hb || model.equals("sys_menu")) {
				if (model.equals("sys_menu")) {
					_column = "tbl_shifoumoren";
				}
				for (String vo : selectedVOs) {
					List<DomainVO> ds = getDomainVos(vo);
					boolean ib = compexDomainService.isDefaultData(model, _column, ds.get(0).getDomainId());
					if (ib) {
						canDelete = "false.isdefault";
						break;
					}
				}
			}
		} else {
			if (model.equals("sys_logo")) {
				for (Long id : selectedIDs) {
					boolean ib = compexDomainService.isDefaultData(model, "tbl_isdefault", id);
					if (ib) {
						canDelete = "false.isdefault";
						break;
					}
				}
			}
		}
		
		//系统默认创建的不能删除
		if (selectedVOs != null) {
			for (String vo : selectedVOs) {
				List<DomainVO> ds = getDomainVos(vo);
				Long createby = (Long)compexDomainService.queryColumnValue(ds.get(0).getTable(), "comm_createBy", ds.get(0).getDomainId());
				if (1L==createby) {
					canDelete = "false.system";
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
	 * 判断组件，构件是否被引用，如被引用则不能够撤回
	 * 
	 * @return
	 * @throws Exception
	 */
	public String isCanRecall() throws Exception {
		List<DomainVO> ds = getDomainVos(this.params);
		this.model = ds.get(0).getTable();
		String canRecall = "true";
		if (model.equalsIgnoreCase("sys_textbox") || model.equalsIgnoreCase("sys_textarea") || model.equalsIgnoreCase("sys_combox")
				|| model.equalsIgnoreCase("sys_searchcombox") || model.equalsIgnoreCase("sys_uploadfilebox")
				|| model.equalsIgnoreCase("sys_passwordbox") || model.equalsIgnoreCase("sys_radiomgt") || model.equalsIgnoreCase("sys_checkboxmgt")
				|| model.equalsIgnoreCase("sys_tree") || model.equalsIgnoreCase("sys_riqizujian") || model.equalsIgnoreCase("sys_texteditor")
				|| model.equals("sys_uploadify") || model.equals("sys_codecasecade")) {
			String inputType = FormUtil.getInputTypeByModel(model);
			List counts = formService.findIsUse(ds.get(0).getDomainId(), inputType);
			if (counts.size() > 0) {
				canRecall = "false";
			}
		} else if (model.equalsIgnoreCase("sys_button")) {
			String res = formService.findIsUseButton(ds.get(0).getDomainId());
			if (res.equals("false")) {
				canRecall = res;
			}
		} else if (model.equalsIgnoreCase("sys_buttongroup")) {
			String res = formService.findIsUseButtonGroup(ds.get(0).getDomainId());
			if (res.equals("false")) {
				canRecall = res;
			}
		} else if (model.equalsIgnoreCase("sys_chaxunzujian")) {
			String res = formService.findIsUseQuery(ds.get(0).getDomainId());
			if (res.equals("false")) {
				canRecall = res;
			}
		} else if (model.equalsIgnoreCase("sys_gaojichaxun")) {
			String res = formService.findIsUseAdvQuery(ds.get(0).getDomainId());
			if (res.equals("false")) {
				canRecall = res;
			}
		} else if (model.equalsIgnoreCase("sys_liebiaozujian")) {
			String res = formService.findIsUseList(ds.get(0).getDomainId());
			if (res.equals("false")) {
				canRecall = res;
			}
		} else if (model.equalsIgnoreCase("sys_liebiaoselect") || model.equalsIgnoreCase("sys_liebiaoorder")
				|| model.equalsIgnoreCase("sys_liebiaooperation") || model.equalsIgnoreCase("sys_liebiaopagination")) {
			String columnname = "";
			if (model.equalsIgnoreCase("sys_liebiaoselect")) {
				columnname = "tbl_selectid";
			} else if (model.equalsIgnoreCase("sys_liebiaoorder")) {
				columnname = "tbl_orderid";
			} else if (model.equalsIgnoreCase("sys_liebiaooperation")) {
				columnname = "tbl_operationid";
			} else if (model.equalsIgnoreCase("sys_liebiaopagination")) {
				columnname = "tbl_paginationid";
			}
			String res = formService.findIsUseListControl(ds.get(0).getDomainId(), columnname);
			if (res.equals("false")) {
				canRecall = res;
			}
		}

		// 包含默认数据时不能删除
		// 判断该模块是否包含默认字段
		boolean hb = compexDomainService.hasDefaultCol(model);
		String _column = "tbl_isdefault";
		if (hb || model.equals("sys_menu")) {
			if (model.equals("sys_menu")) {
				_column = "tbl_shifoumoren";
			}
			boolean ib = compexDomainService.isDefaultData(model, _column, ds.get(0).getDomainId());
			if (ib) {
				canRecall = "false.isdefault";
			}
		}
		if (model.equals("sys_logo")) {
			for (Long id : selectedIDs) {
				boolean ib = compexDomainService.isDefaultData(model, "tbl_isdefault", id);
				if (ib) {
					canRecall = "false.isdefault";
					break;
				}
			}
		}

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(canRecall);

		return NONE;
	}

	/**
	 * 显示代码级联控件，返回json格式的代码集合
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String showCaseCade() throws Exception {
		String topCode = getRequest().getParameter("topCode");
		String showProgression = getRequest().getParameter("showProgression");
		List<Dictionary> list = dictionaryService.queryCaseCadeByParent(Long.valueOf(topCode));
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, list);
		return NONE;
	}

	/**
	 * 显示代码级联的显示名称
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String showCaseCadeName() throws Exception {
		String caseCadeId = getRequest().getParameter("caseCadeId");
		Dictionary _dictionary = dictionaryService.queryById(Long.valueOf(caseCadeId));
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, _dictionary);
		return NONE;
	}

	/**
	 * 根据代码级联的级数获得代码的集合
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String getParentCodeByProg() throws Exception {
		String progression = getRequest().getParameter("progression");
		String value = getRequest().getParameter("value");
		Dictionary dictionary = dictionaryService.queryById(Long.valueOf(value));
		Long parentId = dictionary.getBelong();
		if (progression != null && Integer.valueOf(progression) > 0) {
			Dictionary dic = new Dictionary();
			for (int i = 0; i < Integer.valueOf(progression); i++) {
				dic = dictionaryService.queryById(parentId);
				parentId = dic.getBelong();
			}
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter printWriter = response.getWriter();
			objectMapper.writeValue(printWriter, dic);
		}
		return NONE;
	}

	/**
	 * 
	 * Description: 导出某个目录中对应的所有数据： 关联关系：目录-->列表-->表单
	 * 实际上导出的数据字段为：在表单中配置的字段为isView的（即用户点击查看按钮能看到的所有数据）。 注意：
	 * 1.一般认为：如果一个字段是可以被查看的，那么它一定是可以被编辑的。（取决于配置表单用户的配置）
	 * 如果违背此原则，则导出的数据表在进行导入的时候，可能会缺失某些字段。 2.该导出功能并非导出全部数据，而是列表帅选后的数据。 Steps:
	 * 1.根据formId查找到与该目录关联的form对象。 2.获取form对象关联的表，并查询数据库，获取所有的字段值。
	 * 3.根据listId获取该list关联的数据表中的数据，如果该字段在form中可以被查看，导出该字段。 4.文件导出后，调用下载方法
	 * <code>downLoadFile</code>，将文件下载至客户端。
	 * 
	 * @Author Jason 2012-12-12
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String exportExcel() throws Exception {
		// 通过while循环构造查询条件,dyncMapString,dyncMapStringPrecise,dyncMapLong都是不同的查询条件集合
		@SuppressWarnings("rawtypes")
		Iterator iterator = dyncMapString.entrySet().iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			if (entry.getValue() != null && !"".equals(entry.getValue()) && !"-1".equals(entry.getValue())) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."),
						"%" + java.net.URLDecoder.decode(String.valueOf(entry.getValue()), "UTF-8") + "%");
			}
		}
		@SuppressWarnings("rawtypes")
		Iterator iteratorp = dyncMapStringPrecise.entrySet().iterator();
		while (iteratorp.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iteratorp.next();
			if (dyncMapStringCombobox.containsKey(entry.getKey())) {
				String flag = dyncMapStringCombobox.get(entry.getKey());
				if ("false".equals(flag)) {
					queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), new Double(Math.E).toString());
				} else if ("true".equals(flag) && !"-1".equals(entry.getValue())) {
					queryCriteria.addQueryCondition(entry.getKey().replace("-", "."),
							java.net.URLDecoder.decode(String.valueOf(entry.getValue()), "UTF-8"));
				}
			} else {
				if (entry.getValue() != null && !"".equals(entry.getValue()) && !"-1".equals(entry.getValue())) {
					queryCriteria.addQueryCondition(entry.getKey().replace("-", "."),
							java.net.URLDecoder.decode(String.valueOf(entry.getValue()), "UTF-8"));
				}
			}
		}
		@SuppressWarnings("rawtypes")
		Iterator iteratorLong = dyncMapLong.entrySet().iterator();
		while (iteratorLong.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Long> entry = (Map.Entry<String, Long>) iteratorLong.next();
			if (entry.getValue() != null && !"".equals(entry.getValue()) && -1L != entry.getValue()) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."),
						java.net.URLDecoder.decode(String.valueOf(entry.getValue()), "UTF-8"));
			}
		}

		@SuppressWarnings("rawtypes")
		Iterator iteratorInteger = dyncMapInteger.entrySet().iterator();
		while (iteratorInteger.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iteratorInteger.next();
			if (entry.getValue() != null && !"".equals(entry.getValue()) && -1 != entry.getValue()) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."),
						java.net.URLDecoder.decode(String.valueOf(entry.getValue()), "UTF-8"));
			}
		}
		@SuppressWarnings("rawtypes")
		Iterator iteratord = dyncMapDate.entrySet().iterator();
		List<String> keyList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		while (iteratord.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iteratord.next();
			if (entry.getValue() != null && !"".equals(entry.getValue())) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."),
						java.net.URLDecoder.decode(String.valueOf(entry.getValue()), "UTF-8"));
				keyList.add(entry.getKey());
				valueList.add(entry.getValue());
			}
		}

		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON("userFail", false);
			return NONE;
		}
		try {
			/**
			 * 查询form对象
			 */
			form = this.formService.findFormById(formId);
			/**
			 * 查询包含有所有列的tabulation对象,只查询少量数据
			 */
			queryCriteria.setCurrentIndex(0);
			queryCriteria.setPageSize(2);
			tabulation = this.tabulationService.selectNoPageAllColumnsTabulationByListId(listId, queryCriteria, user);

			final List<FormColumnExtend> _formColumnExtendList = form.getTabulationColumnExtends();
			// 过滤出主表和一对一关系的表字段
			List<String> tblNameList = new ArrayList<String>();
			tblNameList.add(form.getTableName());
			QueryCriteria _qc = new QueryCriteria();
			_qc.addQueryCondition("r.tbl_mainid", form.getTableId());
			_qc.setCurrentPage(0);
			_qc.setPageSize(-1);
			List<Relation> _relationList = relationService.queryRelation(_qc);
			for (Relation relation : _relationList) {
				if (relation.getRelationType() == 0) {
					tblNameList.add(relation.getMainTableName());
					tblNameList.add(relation.getSubTableName());
				}
			}
			List<FormColumnExtend> formColumnExtendList = new ArrayList<FormColumnExtend>();
			for (FormColumnExtend formColumnExtend : _formColumnExtendList) {
				if (tblNameList.contains(formColumnExtend.getFormColumn().getBelongTable())) {
					formColumnExtendList.add(formColumnExtend);
				}
			}

			String fileName = tabulation.getTabulationName() + "@" + new Date().getTime();// Attention:没有扩展名
			String extName = ".xls";// 扩展名
			if (tabulation.getTabulationName() == null || tabulation.getTabulationName().trim().equals("")) {
				fileName = "workbook_" + new Date().getTime();
			}

			if (tabulation != null) {
				int totalCount = tabulation.getTotalCount();// 获取全部记录数量
				if (totalCount > 0) {
					Workbook wb = new HSSFWorkbook();
					List<Domain> _domainList = tabulation.getDomains();
					String sheetName = _domainList.get(0).getTable().getTableZhName();

					Sheet sheet = wb.createSheet(sheetName);
					/**
					 * 根据formColumnExtends中的字段，找出“查看”的字段，生成表格的标题行（字段中文名）和字段行（
					 * 字段英文名）
					 */
					Row firstRow = sheet.createRow(0);// 字段行
					Row secondRow = sheet.createRow(1);// 标题行
					/**
					 * 定义标题行的样式
					 */
					Font font = wb.createFont();
					font.setBoldweight(Font.BOLDWEIGHT_BOLD);
					// font.setFontHeight((short)250);
					CellStyle cstyle = wb.createCellStyle();
					cstyle.setAlignment(CellStyle.ALIGN_CENTER);// 居中
					cstyle.setFont(font);

					/**
					 * 遍历formColumnExtendList，将isView为1的字段添加到前两行中
					 */
					int _index = 0;
					for (FormColumnExtend _fce : formColumnExtendList) {
						FormColumn _fc = _fce.getFormColumn();
						if (_fc.getIsShowInList() == 1) {
							Cell _fcell = firstRow.createCell(_index);
							_fcell.setCellType(Cell.CELL_TYPE_STRING);
							_fcell.setCellValue(String.valueOf(_fc.getColumnId()));

							Cell _scell = secondRow.createCell(_index);
							_scell.setCellType(Cell.CELL_TYPE_STRING);
							_scell.setCellValue(_fc.getColumnZhName());

							_scell.setCellStyle(cstyle);

							_index++;
						}
					}
					firstRow.setZeroHeight(true);// 隐藏第一行

					/**
					 * 遍历所有记录，生成数据行
					 */
					int _rowindex = sheet.getFirstRowNum() + 2;

					int _clstart = firstRow.getFirstCellNum();
					int _cllast = firstRow.getLastCellNum();

					// 分页获取数据并添加到Excel中
					queryCriteria.setPageSize(-1);
					queryCriteria.setCurrentIndex(0);
					Tabulation tabulation = this.tabulationService.selectNoPageAllColumnsTabulationByListId(listId, queryCriteria, user);
					List<Domain> domainList = tabulation.getDomains();
					for (Domain _domain : domainList) {
						Row row = sheet.createRow(_rowindex);
						List<FormColumnExtend> fcelist = _domain.getTabulationColumnExtends();

						for (int j = _clstart; j < _cllast; j++) {
							Cell cell = row.createCell(j);
							cell.setCellType(Cell.CELL_TYPE_STRING);

							for (FormColumnExtend fce : fcelist) {
								try {
									FormColumn fc = fce.getFormColumn();
									if (firstRow.getCell(j).getStringCellValue().equals(String.valueOf(fc.getColumnId()))) {
										/**
										 * 根据录入类型，更改显示的字段值
										 */
										int type = fc.getInputType();
										if (type == Constants.INPUT_TYPE_TEXTBOX_COMBOX || // 下拉框
												type == Constants.INPUT_TYPE_TEXTBOX_RADIO || // 单选框
												type == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX) {// 搜索下拉框
											for (Code code : fce.getCodes()) {
												if (code.getValue().equals(String.valueOf(fce.getValue()))) {
													cell.setCellValue(code.getText());
												}
											}
										} else if (type == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX) {// 复选框
											String values[] = String.valueOf(fce.getValue()).split(",");
											String text = "";
											for (String value : values) {
												for (Code code : fce.getCodes()) {
													if (code.getValue().equals(value)) {
														text += code.getText() + ";";
													}
												}
											}
											cell.setCellValue(text);
										} else if (type == Constants.INPUT_TYPE_TEXTBOX_TREE) {// 树组件
											MgrTree mgrTree = mgrTreeService.findMgrTreeById(fce.getFormColumn().getCompexId());
											CommonTree commonTree = mgrTreeService.findCommonTreeByMgrTree(mgrTree, (Long) fce.getValue());

											cell.setCellValue(commonTree.getName());
										} else if (type == Constants.INPUT_TYPE_TEXTBOX_PERSONCHOISE) {// 部门人员选择组件
											String personids = String.valueOf(fce.getValue());
											String personNames = "";
											List<Person> _lstPerson = personChioseService.getPersons();
											for (Person person : _lstPerson) {
												for (String _sPerson : personids.split(";")) {
													if (person.getId().equals(_sPerson)) {
														personNames += person.getTblXingming() + ";";
													}
												}
											}
											cell.setCellValue(personNames);
										} else if (type == Constants.INPUT_TYPE_TEXTBOX || type == Constants.INPUT_TYPE_TEXTBOX_EDITOR) {// 文本框、文本编辑器
											cell.setCellValue(String.valueOf(fce.getValue()).equals("null") ? "" : String.valueOf(fce.getValue()));
										} else {
											cell.setCellValue(String.valueOf(fce.getValue()).equals("null") ? "" : String.valueOf(fce.getValue()));
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
									if (log.isErrorEnabled())
										log.error(e.getMessage(), e);
									continue; // 当写入数据过程出错后，忽略本次循环，继续下一次循环
								}
							}
						}
						_rowindex++;
					}
					/**
					 * 统一设置每一列的样式为“根据内容调整”
					 */
					int minClmNum = sheet.getFirstRowNum();
					int maxClmNum = sheet.getLastRowNum();
					for (int cin = minClmNum + 1; cin < maxClmNum; cin++) {
						sheet.autoSizeColumn(cin);// 设置列宽为自动(貌似不是很好用 = =!)
					}
					/**
					 * 通过读取配置文件，获取临时文件存放路径
					 */
					// Properties sp = (Properties)
					// getSession().getAttribute("global");
					// String tempuUploadDir = sp.getProperty("uploadDir");
					/**
					 * 生成临时文件
					 */
					// FileOutputStream fileout = new
					// FileOutputStream(tempuUploadDir+"/"+fileName + extName);
					// wb.write(fileout);
					// fileout.close();
					// File tmpfile = new File(tempuUploadDir+"/"+fileName +
					// extName);

					HttpServletResponse response = getResponse();
					response.setCharacterEncoding("UTF-8");

					response.reset();

					String filename = "";
					fileName += extName;
					if (getRequest().getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
						filename = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
					} else if (getRequest().getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
						filename = URLEncoder.encode(fileName.trim(), "UTF-8");
					} else {
						filename = fileName;
					}

					response.addHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
					// response.addHeader("Content-Length",
					// String.valueOf(file.length()));
					OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
					response.setContentType("application/octet-stream");
					wb.write(toClient);
					// toClient.write(buffer);
					toClient.flush();
					toClient.close();

					/**
					 * 将生成的文件下载到客户端
					 */
					// this.downLoadFile(tmpfile);
					// tmpfile.delete();// 删掉临时文件
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("300", "数据导出过程出错", false);
		}
		return NONE;
	}

	/**
	 * Description: 从Excel导入数据 Steps: 1.获取上传的Excel，进行格式判断，如果格式错误则停止执行并返回错误提示
	 * 2.将文件上传至临时目录 3.读取文件内容，并插入到数据库中
	 * 
	 * @return
	 * @throws Exception
	 */
	public String importExcel() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON("userFail", false);
			return NONE;
		}
		/**
		 * 判断文件上传类型
		 */
		if (upload.length > 0) {
			String extendName = uploadFileName[0].substring(uploadFileName[0].lastIndexOf("."));// 获取第一个文件的扩展名

			if (extendName.equals(".xls") || extendName.equals(".xlsx")) {// 仅支持excel

				String tempuUploadDir = "";// 临时文件上传路径
				String ext = new Long(System.currentTimeMillis()).toString();// 添加到文件名后面的时间戳
				File tmpFile = null;

				/**
				 * 通过读取配置文件，获取临时文件上传路径
				 */
				tempuUploadDir = configproperties.getProperty("uploadDir");

				/**
				 * 根据提交过来的文件，将文件复制到临时路径
				 */
				String _sFileName = uploadFileName[0].substring(0, uploadFileName[0].lastIndexOf(".")) + ext + extendName;
				tmpFile = new File(getRequest().getRealPath(tempuUploadDir), _sFileName);
				FileUtils.copyFile(upload[0], tmpFile);
				/**
				 * 如果文件上传成功，则读取该文件
				 */
				if (tmpFile.exists() && tmpFile.canRead()) {
					/**
					 * 获取form，从而获取其中表单字段列表
					 */
					form = this.formService.findFormById(formId);

					Table _mainTable = new Table(form.getMainTable());// 主表

					final List<FormColumnExtend> formColumnExtendList = form.getFormColumnExtends();

					/**
					 * 获取form中录入类型是代码的所有目录下代码(用于将Excel表中的代码名转化为相应的代码值)
					 */
					for (FormColumnExtend _fce : form.getFormColumnExtends()) {
						try {
							FormColumn _fc = _fce.getFormColumn();
							int type = _fc.getInputType();
							if (type == Constants.INPUT_TYPE_TEXTBOX_COMBOX || type == Constants.INPUT_TYPE_TEXTBOX_RADIO
									|| type == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX || type == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX) {

								if (_fc.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) { // 代码值
									List<Dictionary> dl = dictionaryService.queryByParent(_fc.getCodeParentId());
									List<Code> codes = new ArrayList<Code>();
									for (Dictionary d : dl) {
										Code code = new Code(d.getId(), d.getName(), d.getValue());
										codes.add(code);
									}
									_fce.setCodes(codes);
								} else if (_fc.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) { // 关系表
									if (log.isErrorEnabled())
										log.error("Excel导入：关系表类型没做处理");
									// 关系表需要特殊处理 to be done
								}
							} else if (type == Constants.INPUT_TYPE_TEXTBOX_TREE) {// 树组件
								if (log.isErrorEnabled())
									log.error("Excel导入：树组件类型没做处理");
								// to do
								// value =
								// String.valueOf(_map.get(_showmap.get(j)));
							} else if (type == Constants.INPUT_TYPE_TEXTBOX_PERSONCHOISE) {// 部门人员选择组件
								if (log.isErrorEnabled())
									log.error("Excel导入：部门人员选择组件类型没做处理");
								// to do
								// value =
								// String.valueOf(_map.get(_showmap.get(j)));
							}
						} catch (Exception e) {
							e.printStackTrace();
							if (log.isErrorEnabled())
								log.error(e.getMessage(), e);
							continue;
						}
					}

					/**
					 * 读取刚才上传的Excel文件
					 */
					InputStream in = new FileInputStream(tmpFile);
					Workbook wb = WorkbookFactory.create(in);
					in.close();
					/**
					 * 读取第一个sheet
					 */
					Sheet sheet = wb.getSheetAt(0);
					/**
					 * 获取sheet中的第一行行号和最后一行行号
					 */
					int firstRowNum = sheet.getFirstRowNum();
					int lastRowNum = sheet.getLastRowNum();

					final List<Domain> domainList = new ArrayList<Domain>(lastRowNum * 3 / 2 + 1);

					/**
					 * 最后一列，获取列数
					 */
					int lastColumnNum = sheet.getRow(firstRowNum).getLastCellNum();
					/**
					 * 读取第一行：第一行的数据为字段在sys_Columns表中的id值
					 */
					Row titleRow = sheet.getRow(firstRowNum);
					/**
					 * 接下来从第三行开始遍历（Excel表格结构：第一行为字段对应id，第二行为字段中文名，从第三行开始为真正数据字段）
					 */
					for (int i = firstRowNum + 2; i <= lastRowNum; i++) {
						/**
						 * 新建一个list！！
						 */
						List<FormColumnExtend> tmpList = new ArrayList<FormColumnExtend>(lastColumnNum * 3 / 2 + 1);

						try {
							Row _row = sheet.getRow(i);
							for (Cell _cell : _row) {
								try {
									for (FormColumnExtend fce : formColumnExtendList) {
										/**
										 * 如果标题行中该列的字段id存在于formColumnExtendList中
										 * ， 则读取该列的值，并赋值给formColumnExtend
										 */
										FormColumn fc = fce.getFormColumn();

										if (fc.getColumnId().equals(Long.valueOf(titleRow.getCell(_cell.getColumnIndex()).getStringCellValue()))) {
											/**
											 * 新建一个formColumn
											 */
											FormColumnExtend tmpFce = new FormColumnExtend();
											tmpFce.setFormColumn(fc);

											String value = String.valueOf(_cell.getStringCellValue());// 获取数据表中的值

											/**
											 * 根据字段的录入类型以及数据类型，将表中的值进行转换.
											 */
											int inputtype = fc.getInputType();

											if (inputtype == Constants.INPUT_TYPE_TEXTBOX_COMBOX || inputtype == Constants.INPUT_TYPE_TEXTBOX_RADIO
													|| inputtype == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX) {

												if (fc.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) { // 代码值
													for (Code code : fce.getCodes()) {
														if (value.equals(code.getText())) {
															value = code.getValue();
															break;
														}
													}
												} else if (fc.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) { // 关系表
													// 关系表需要特殊处理 to be done
												}
											} else if (inputtype == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX) {
												String _value = value;
												for (Code code : fce.getCodes()) {
													if (value.equals(code.getText())) {
														_value += code.getValue() + ",";
													}
												}
												value = _value;
											} else if (inputtype == Constants.INPUT_TYPE_TEXTBOX_TREE) {// 树组件
												if (log.isErrorEnabled())
													log.error("Excel导入：树组件类型没做处理");
											} else if (inputtype == Constants.INPUT_TYPE_TEXTBOX_PERSONCHOISE) {// 部门人员选择组件
												if (log.isErrorEnabled())
													log.error("Excel导入：部门人员选择组件类型没做处理");
											}

											String dataType = fc.getDataType();// 字段数据类型
											if (dataType.equals("varchar")) {
												tmpFce.setValue(value);
											} else if (dataType.equals("timestamp")) {
												SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
												tmpFce.setValue(format.parse(value));
											} else if (dataType.equals("date")) {
												SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
												tmpFce.setValue(format.parse(value));
											} else if (dataType.equals("int")) {
												if (value.trim().length() == 0) {
													value = "0";
												}
												tmpFce.setValue(Integer.valueOf(value));
											} else {
												if (log.isErrorEnabled())
													log.error("Excel导入：未处理数据类型" + fc.getDataType());
											}
											tmpList.add(tmpFce);
										}
									}
								} catch (Exception e) {// 如果某一个单元格出现异常,则并向上抛出异常。
									e.printStackTrace();
									throw e;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							continue;// 如果有异常，则忽略这一行，执行下一行
						}
						/**
						 * 再次遍历tmpList,将其中的createBy,updateBy,createDate,
						 * updateDate修改
						 */
						for (FormColumnExtend _fce : tmpList) {
							String formColumnName = _fce.getFormColumn().getColumnName();
							if ("comm_createBy".equals(formColumnName) || "comm_updateBy".equals(formColumnName)) {
								_fce.setValue(user.getId());
							} else if ("comm_updateDate".equals(formColumnName) || "comm_createDate".equals(formColumnName)) {
								_fce.setValue(new Date());
							}
						}
						/**
						 * 每一行遍历完之后，如果tmpList不为空(数据表中数据符合规范)，
						 * 将tmpList封装到一个Domain中
						 */
						if (!tmpList.isEmpty()) {
							Domain domain = new Domain();
							domain.setId(null);// id必须为null或者""才能执行insert语句，否则将执行update语句
							domain.setTable(_mainTable);
							domain.setFormColumnExtends(tmpList);
							/**
							 * 将domain加入到domainList中
							 */
							domainList.add(domain);
						}
					}
					/**
					 * 表格遍历结束后，将生成的domainList插入到数据库中
					 */
					if (!domainList.isEmpty()) {// 如果有记录，则执行插入操作
						int count = compexDomainService.doSaveDomainList(domainList, user);
						/**
						 * 删除临时表
						 */
						tmpFile.delete();
						/**
						 * 输出成功提示
						 */
						printJSON("200", "数据表记录总数：" + lastRowNum + "; 成功导入记录数：" + count + ";", true);
					} else {
						/**
						 * 删除临时表
						 */
						tmpFile.delete();
						printJSON("301", "Excel表中没有与该模块表单配置相对应的字段", true);
					}
				}
			} else {
				printJSON("301", "上传文件类型错误，请选择.xls或.xlsx格式文件", true);
			}
		} else {
			printJSON("301", "请选择一个文件！", true);
		}
		return NONE;
	}

	/**
	 * 
	 * Description: 下载文件的方法 Steps:
	 * 
	 * @param file
	 * @throws Exception
	 */
	@Deprecated
	private void downLoadFile(final File file) throws Exception {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");

		try {
			byte[] buffer;
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			response.reset();

			String filename = "";
			if (getRequest().getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				filename = new String(file.getName().getBytes("UTF-8"), "ISO8859-1");
			} else if (getRequest().getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				filename = URLEncoder.encode(file.getName().trim(), "UTF-8");
			} else {
				filename = file.getName();
			}

			response.addHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
			response.addHeader("Content-Length", String.valueOf(file.length()));
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new AppException("文件下载失败");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new AppException("文件下载失败");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AppException("文件下载失败");
		}
	}

	public Map<String, String> getDyncMapTempString() {
		return dyncMapTempString;
	}

	public void setDyncMapTempString(Map<String, String> dyncMapTempString) {
		this.dyncMapTempString = dyncMapTempString;
	}

	public Map<String, String> getDyncMapString() {
		return dyncMapString;
	}

	public Map<String, String> getDyncMapStringPrecise() {
		return dyncMapStringPrecise;
	}

	public void setDyncMapStringPrecise(Map<String, String> dyncMapStringPrecise) {
		this.dyncMapStringPrecise = dyncMapStringPrecise;
	}

	public void setDyncMapString(Map<String, String> dyncMapString) {
		this.dyncMapString = dyncMapString;
	}

	public Map<String, Integer> getDyncMapInteger() {
		return dyncMapInteger;
	}

	public void setDyncMapInteger(Map<String, Integer> dyncMapInteger) {
		this.dyncMapInteger = dyncMapInteger;
	}

	public Map<String, String> getDyncMapStringCombobox() {
		return dyncMapStringCombobox;
	}

	public void setDyncMapStringCombobox(Map<String, String> dyncMapStringCombobox) {
		this.dyncMapStringCombobox = dyncMapStringCombobox;
	}

	public Map<String, Long> getDyncMapLong() {
		return dyncMapLong;
	}

	public void setDyncMapLong(Map<String, Long> dyncMapLong) {
		this.dyncMapLong = dyncMapLong;
	}

	public Map<String, String> getDyncMapDate() {
		return dyncMapDate;
	}

	public void setDyncMapDate(Map<String, String> dyncMapDate) {
		this.dyncMapDate = dyncMapDate;
	}

	public List<TabulationButton> getTabulationButtons() {
		return tabulationButtons;
	}

	public void setTabulationButtons(List<TabulationButton> tabulationButtons) {
		this.tabulationButtons = tabulationButtons;
	}

	public Tabulation getTabulation() {
		return tabulation;
	}

	public void setTabulation(Tabulation tabulation) {
		this.tabulation = tabulation;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String[] getSelectedVOs() {
		return selectedVOs;
	}

	public void setSelectedVOs(String[] selectedVOs) {
		this.selectedVOs = selectedVOs;
	}

	public String[] getSelectedSubVOs() {
		return selectedSubVOs;
	}

	public void setSelectedSubVOs(String[] selectedSubVOs) {
		this.selectedSubVOs = selectedSubVOs;
	}

	public Domain getDom() {
		return dom;
	}

	public void setDom(Domain dom) {
		this.dom = dom;
	}

	public List<FormColumnExtend> getTabulationColumns() {
		return tabulationColumns;
	}

	public void setTabulationColumns(List<FormColumnExtend> tabulationColumns) {
		this.tabulationColumns = tabulationColumns;
	}

	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}

	public String getPartitionListDiv() {
		return partitionListDiv;
	}

	public void setPartitionListDiv(String partitionListDiv) {
		this.partitionListDiv = partitionListDiv;
	}

	public String getListDiv() {
		return listDiv;
	}

	public void setListDiv(String listDiv) {
		this.listDiv = listDiv;
	}

	public Domain getSubDom() {
		return subDom;
	}

	public void setSubDom(Domain subDom) {
		this.subDom = subDom;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Partition getPartition() {
		return partition;
	}

	public void setPartition(Partition partition) {
		this.partition = partition;
	}

	public String getPartitionForm() {
		return partitionForm;
	}

	public void setPartitionForm(String partitionForm) {
		this.partitionForm = partitionForm;
	}

	public String getMainTable() {
		return mainTable;
	}

	public void setMainTable(String mainTable) {
		this.mainTable = mainTable;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public boolean isHasDefaultQuery() {
		return hasDefaultQuery;
	}

	public void setHasDefaultQuery(boolean hasDefaultQuery) {
		this.hasDefaultQuery = hasDefaultQuery;
	}

	public List<FormButton> getFormButtons() {
		return formButtons;
	}

	public void setFormButtons(List<FormButton> formButtons) {
		this.formButtons = formButtons;
	}

	public File[] getUpload() {
		return upload;
	}

	public void setUpload(File[] upload) {
		this.upload = upload;
	}

	public String[] getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String[] getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
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

	public void setPartitionId(Long partitionId) {
		this.partitionId = partitionId;
	}

	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	public Long getSubDomainId() {
		return subDomainId;
	}

	public void setSubDomainId(Long subDomainId) {
		this.subDomainId = subDomainId;
	}

	public Long getCurrentSaveId() {
		return currentSaveId;
	}

	public void setCurrentSaveId(Long currentSaveId) {
		this.currentSaveId = currentSaveId;
	}

	public String getSimpleModel() {
		if(model != null) {
			return EncryptUtil.Md5(model);
		}else{
			return null;
		}
	}

	public void setSimpleModel(String simpleModel) {
		this.simpleModel = simpleModel;
	}


}
