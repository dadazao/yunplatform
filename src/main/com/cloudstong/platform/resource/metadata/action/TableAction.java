package com.cloudstong.platform.resource.metadata.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.ColumnExtend;
import com.cloudstong.platform.resource.metadata.model.ConfigColumn;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.service.ColumnService;
import com.cloudstong.platform.resource.metadata.service.RelationService;
import com.cloudstong.platform.resource.metadata.service.TableService;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.service.TabulationService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Allan Created on 2012-11-20
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:数据表管理Action
 */
public class TableAction extends CompexDomainAction {

	/**
	 * <code>tableService</code> 数据表服务实例.
	 */
	@Resource
	private TableService tableService;
	/**
	 * 字段服务实例
	 */
	@Resource
	private ColumnService columnService;
	/**
	 * 代码服务实例
	 */
	@Resource
	private DictionaryService dictionaryService;
	/**
	 * 列表服务实例
	 */
	@Resource
	private TabulationService tabulationService;
	/**
	 * 关系服务实例
	 */
	@Resource
	private RelationService relationService;
	/**
	 * 表单服务实例
	 */
	@Resource
	private FormService formService;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 数据表
	 */
	private Table table;
	/**
	 * 表名称
	 */
	private String tableName;
	/**
	 * 表中文名称
	 */
	private String tableZhName;
	/**
	 * 字段扩展list
	 */
	private List<ColumnExtend> columnExtends;
	/**
	 * 表扩展map
	 */
	private Map<String, String> dyncMap = new HashMap<String, String>();
	/**
	 * 表类型代码list
	 */
	private List<Dictionary> tableTypeList;
	/**
	 * 表关系代码list
	 */
	private List<Dictionary> tableRelationList;
	/**
	 * 表schemm代码list
	 */
	private List<Dictionary> tableSchemaList;
	/**
	 * 分组
	 */
	private List<Dictionary> groupList;
	/**
	 * 编码
	 */
	private String seqcode;

	private String version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String search() {
		return "search";
	}

	public String view() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		id = Long.valueOf(params.substring(params.indexOf(":") + 1, params.indexOf(";")));
		this.table = tableService.findTableById(id);
		queryColumnValues(this.table);

		tableTypeList = dictionaryService.queryByParent(10295064139L);
		tableRelationList = dictionaryService.queryByParent(10525952264L);
		tableSchemaList = dictionaryService.queryByParent(101452612797L);
		groupList = dictionaryService.queryByParent(10913513383L);

		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		Form tform = formService.findFormByIdAndDomainVO(formId, domainVOs, user);
		this.formButtons = tform.getFormButtons();

		return "view";
	}

	public String add() {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			this.table = new Table();
			queryColumnValues(table);

			tableTypeList = dictionaryService.queryByParent(10295064139L);
			tableRelationList = dictionaryService.queryByParent(10525952264L);
			tableSchemaList = dictionaryService.queryByParent(101452612797L);
			groupList = dictionaryService.queryByParent(10913513383L);

			List<DomainVO> domainVOs = new ArrayList<DomainVO>();
			Form tform = formService.findFormByIdAndDomainVO(formId, domainVOs, user);
			this.formButtons = tform.getFormButtons();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "add";
	}

	public String edit() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		id = Long.valueOf(params.substring(params.indexOf(":") + 1, params.indexOf(";")));
		this.table = tableService.findTableById(id);
		queryColumnValues(this.table);

		tableTypeList = dictionaryService.queryByParent(10295064139L);
		tableRelationList = dictionaryService.queryByParent(10525952264L);
		tableSchemaList = dictionaryService.queryByParent(101452612797L);
		groupList = dictionaryService.queryByParent(10913513383L);

		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		Form tform = formService.findFormByIdAndDomainVO(formId, domainVOs, user);
		this.formButtons = tform.getFormButtons();

		return "edit";
	}

	private void queryColumnValues(Table table) {
		queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		queryCriteria.addQueryCondition("tbl_belongTable", "sys_tables");
		List columns = columnService.queryColumn(queryCriteria);
		columnExtends = new ArrayList();
		for (int i = 0; columns != null && i < columns.size(); i++) {
			Column column = (Column) (columns.get(i));
			String value = tableService.findValueByColumn(table.getTableName(), column.getColumnName());
			ColumnExtend ce = new ColumnExtend(column, value);
			columnExtends.add(ce);
		}
	}

	/**
	 * @author Jason Description: 数据表字段配置功能使用
	 * @return
	 */
	public String columnCfg() {
		String tableid = getRequest().getParameter("tableId");
		if (tableid != null) {
			table = tableService.findTableById(Long.valueOf(tableid));
		}
		return "columncfg";
	}

	/**
	 * @author Jason Description: 数据表字段配置功能使用
	 * @return
	 */
	public void listColumns() throws IOException {
		List<ConfigColumn> list = null;
		String tableName = getRequest().getParameter("tableName");
		if (tableName != null) {
			list = columnService.queryCfgColumnByTableName(tableName);
			if (list.isEmpty()) {
				list = columnService.queryCftColumn(tableName);
			}
		} else {
			list = Collections.emptyList();
		}
		printObject(list);
	}

	/**
	 * @author Jason Description: 数据表字段配置功能使用
	 * @return
	 */
	public String saveColumnConfig() throws IOException {
		String configStr = getRequest().getParameter("cfgcolumns");
		if (configStr == null) {
			printJSON("failure");
		}
		String tableName = getRequest().getParameter("tableName");
		if (tableName != null) {
			if (this.tableService.isTableZhNameDouble(tableName)) {
				printJSON("300", "表名称重复", false);
				return NONE;
			}
			compexDomainService.doUpdateTableData("sys_tables", "tbl_tableZhName", tableName, Long.valueOf(getRequest().getParameter("tableId")));
		}
		try{
			columnService.doConfigColumns(configStr);
		}catch(Exception e) {
			e.printStackTrace();
		}
		printJSON("success");
		return NONE;
	}

	public String save() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		try {
			if (table.getId() != null && !"".equals(table.getId())) {
				table.setUpdateDate(new Date());
				table.setUpdateBy(user.getId());
				if ("new".equals(op)) {
					table.setTableName(table.getGroup() + "_" + table.getTableName());
				}
				queryCriteria = new QueryCriteria();
				queryCriteria.addQueryCondition("tbl_tableZhName", table.getTableZhName());
				List<Table> tables = this.tableService.queryTable(queryCriteria);
				if (tables != null && (tables.size() > 1 || (tables.size() == 1 && !tables.get(0).getId().equals(table.getId())))) {
					throw new AppException("表名称重复!");
				}
				queryCriteria = new QueryCriteria();
				queryCriteria.addQueryCondition("tbl_tableName", table.getTableName());
				List<Table> tabs = this.tableService.queryTable(queryCriteria);
				if (tabs != null && (tabs.size() > 1 || (tabs.size() == 1 && !tabs.get(0).getId().equals(table.getId())))) {
					throw new AppException("拼音名称重复!");
				}
				this.tableService.doUpdateTable(table);
				printJSON("success",false, table.getId().toString());
			} else {
				if (this.tableService.isTableZhNameDouble(table.getTableZhName())) {
					throw new AppException("表名称重复!");
				}
				if (this.tableService.isTableNameDouble(table.getGroup() + "_" + table.getTableName())) {
					throw new AppException("拼音名称重复!");
				}
				table.setCreateDate(new Date());
				table.setCreateBy(user.getId());
				table.setUpdateDate(new Date());
				table.setUpdateBy(user.getId());
				table.setTableName(table.getGroup() + "_" + table.getTableName());
				Long id = this.tableService.doSaveTable(table);
				printJSON("success", false, id.toString());
			}

		} catch (AppException e) {
			printJSON("300", e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail");
		}
		return NONE;
	}

	public String delete() throws IOException {
		try {
			if (selectedVOs != null) {
				selectedIDs = new Long[selectedVOs.length];
				for (int i = 0; i < selectedVOs.length; i++) {
					String vo = selectedVOs[i];
					List<DomainVO> ds = getDomainVos(vo);
					Long id = Long.valueOf(vo.substring(vo.indexOf(":") + 1, vo.indexOf(";")));

					QueryCriteria qc = new QueryCriteria();
					qc.addQueryCondition("tbl_mainid='" + id + "' or tbl_subid", id);
					List relations = relationService.queryRelation(qc);
					if (relations != null && relations.size() > 0) {
						throw new AppException("数据表仍有表关系没有删除！");
					}

					QueryCriteria qct = new QueryCriteria();
					qct.addQueryCondition("tbl_tableId", id);
					List columns = columnService.queryColumn(qct);
					Table table = tableService.findTableById(id);
					if (table.getHasCommonColumn() == 1 && columns.size() > 6) {
						throw new AppException("字段表仍有该表的非公有字段未删除！");
					} else if (table.getHasCommonColumn() == 0 && columns.size() > 0) {
						throw new AppException("字段表仍有该表的引用字段未删除！");
					}

					selectedIDs[i] = id;

				}
				this.tableService.doDeleteTables(selectedIDs);
			}
			printJSON("success");
		} catch (AppException e) {
			printJSON("300", e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail");
		}
		return NONE;
	}

	public String logicDelete() throws IOException {
		try {
			if (selectedVOs != null) {
				selectedIDs = new Long[selectedVOs.length];
				for (int i = 0; i < selectedVOs.length; i++) {
					String vo = selectedVOs[i];
					List<DomainVO> ds = getDomainVos(vo);
					Long id = Long.valueOf(vo.substring(vo.indexOf(":") + 1, vo.indexOf(";")));

					QueryCriteria qc = new QueryCriteria();
					qc.addQueryCondition("tbl_mainid='" + id + "' or tbl_subid", id);
					List relations = relationService.queryRelation(qc);
					if (relations != null && relations.size() > 0) {
						throw new AppException("数据表仍有表关系没有删除！");
					}

					QueryCriteria qct = new QueryCriteria();
					qct.addQueryCondition("tbl_tableId", id);
					List columns = columnService.queryColumn(qct);
					Table table = tableService.findTableById(id);
					if (table.getHasCommonColumn() == 1 && columns.size() > 4) {
						throw new AppException("字段表仍有该表的非公有字段未删除！");
					} else if (table.getHasCommonColumn() == 0 && columns.size() > 0) {
						throw new AppException("字段表仍有该表的引用字段未删除！");
					}

					selectedIDs[i] = id;

				}
				this.tableService.dologicDeleteTables(selectedIDs);
			}
			printJSON("success");
		} catch (AppException e) {
			printJSON("300", e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail");
		}
		return NONE;
	}

	public String pinyin() throws IOException {
		String pinyin = "";// Pinyin4jUtil.makeStringByStringSet(Pinyin4jUtil.getPinyin(tableZhName));
		String[] str = pinyin.split(",");
		StringBuffer sb = new StringBuffer(
				"<select id=\"tableNameSelect\" size=\"3\" name=\"table.tableName\" style=\"width:186px\" onchange=\"showTableInput();\">");
		boolean flag = true;
		for (int i = 0; i < str.length; i++) {
			if ("".equals(str[i])) {
				flag = false;
				continue;
			}
			if (i == 0) {
				sb.append("<option selected=selected value=\"" + str[i] + "\">" + str[i] + "</option>");
			} else {
				sb.append("<option value=\"" + str[i] + "\">" + str[i] + "</option>");
			}
		}
		if (flag) {
			sb.append("<option value=\"-1\">自定义</option>");
		} else {
			sb.append("<option selected='selected' value=\"-1\">自定义</option>");
		}
		sb.append("</select>");
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
		return NONE;
	}

	public String publish() throws IOException {
		this.table = tableService.findTableById(id);
		return NONE;
	}

	public String lock() throws Exception {
		try {
			SysUser user = (SysUser) getSession().getAttribute("user");

			if (!user.isSuper()) {
				printJSON("300", "无权限操作！", false);
			} else {
				tableService.doUpdateStauts(id, 1);
				printJSON("success");
			}
		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
			printJSON("fail");
		}
		return NONE;
	}

	public String unLock() throws Exception {
		try {
			SysUser user = (SysUser) getSession().getAttribute("user");

			if (!user.isSuper()) {
				printJSON("300", "无权限操作！", false);
			} else {
				tableService.doUpdateStauts(id, 0);
				printJSON("success");
			}
		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
			printJSON("fail");
		}
		return NONE;
	}

	public String change() throws Exception {
		SysUser user = (SysUser) getSession().getAttribute("user");
		try {
			Table table = tableService.findTableById(id);
			tableService.doChangeVersion(String.valueOf(id));
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
		}
		return NONE;
	}

	public String history() {
		List historys = tableService.queryHistory(String.valueOf(id));
		this.pageResult = new PageResult();
		this.pageResult.setContent(historys);
		this.pageResult.setTotalCount(historys.size());

		return "history";
	}

	public String metaHistory() {
		try {
			List historys = tableService.queryMetaHistory(String.valueOf(id), version);
			this.pageResult = new PageResult();
			this.pageResult.setContent(historys);
			this.pageResult.setTotalCount(historys.size());
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
		}

		return "meta";
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public TableService getTableService() {
		return tableService;
	}

	public void setTableService(TableService tableService) {
		this.tableService = tableService;
	}

	public List<ColumnExtend> getColumnExtends() {
		return columnExtends;
	}

	public void setColumnExtends(List<ColumnExtend> columnExtends) {
		this.columnExtends = columnExtends;
	}

	public ColumnService getColumnService() {
		return columnService;
	}

	public void setColumnService(ColumnService columnService) {
		this.columnService = columnService;
	}

	public Map<String, String> getDyncMap() {
		return dyncMap;
	}

	public void setDyncMap(Map<String, String> dyncMap) {
		this.dyncMap = dyncMap;
	}

	public String getTableZhName() {
		return tableZhName;
	}

	public void setTableZhName(String tableZhName) {
		this.tableZhName = tableZhName;
	}

	public List<Dictionary> getTableTypeList() {
		return tableTypeList;
	}

	public void setTableTypeList(List<Dictionary> tableTypeList) {
		this.tableTypeList = tableTypeList;
	}

	public List<Dictionary> getTableRelationList() {
		return tableRelationList;
	}

	public void setTableRelationList(List<Dictionary> tableRelationList) {
		this.tableRelationList = tableRelationList;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public List<Dictionary> getTableSchemaList() {
		return tableSchemaList;
	}

	public void setTableSchemaList(List<Dictionary> tableSchemaList) {
		this.tableSchemaList = tableSchemaList;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public TabulationService getTabulationService() {
		return tabulationService;
	}

	public void setTabulationService(TabulationService tabulationService) {
		this.tabulationService = tabulationService;
	}

	public FormService getFormService() {
		return formService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	public String getSeqcode() {
		return seqcode;
	}

	public void setSeqcode(String seqcode) {
		this.seqcode = seqcode;
	}

	public List<TabulationButton> getTabulationButtons() {
		return tabulationButtons;
	}

	public void setTabulationButtons(List<TabulationButton> tabulationButtons) {
		this.tabulationButtons = tabulationButtons;
	}

	public List<FormButton> getFormButtons() {
		return formButtons;
	}

	public void setFormButtons(List<FormButton> formButtons) {
		this.formButtons = formButtons;
	}

	public List<Dictionary> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Dictionary> groupList) {
		this.groupList = groupList;
	}

	public RelationService getRelationService() {
		return relationService;
	}

	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
