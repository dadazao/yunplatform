package com.cloudstong.platform.resource.metadata.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.core.util.DateUtil;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.ColumnExtend;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.service.ColumnService;
import com.cloudstong.platform.resource.metadata.service.TableService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Allan Created on 2012-11-20
 * 
 *         Revision History: Date Reviser Description ---- ------- ----------------------------------------------------
 * 
 *         Description:字段管理Action
 */
public class ColumnAction extends CompexDomainAction {

	/**
	 * <code>columnService</code> 字段服务实例.
	 */
	@Resource
	private ColumnService columnService;
	/**
	 * <code>tableService</code> 数据表服务实例.
	 */
	@Resource
	private TableService tableService;
	/**
	 * <code>dictionaryService</code> 代码服务实例.
	 */
	@Resource
	private DictionaryService dictionaryService;

	/**
	 * <code>column</code> 字段.
	 */
	private Column column;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 数据表ID
	 */
	private Long tableId;
	/**
	 * 数据表
	 */
	private List tables;
	/**
	 * 字段中文名
	 */
	private String columnZhName;
	/**
	 * 字段名
	 */
	private String columnName;
	/**
	 * 编码
	 */
	private String seqcode;
	/**
	 * 字段扩展list
	 */
	private List<ColumnExtend> columnExtends;
	/**
	 * 字段扩展map
	 */
	private Map<String, String> dyncMap = new HashMap<String, String>();
	/**
	 * 数据类型代码list
	 */
	private List<Dictionary> dataTypeList;
	/**
	 * 显示类型代码list
	 */
	private List<Dictionary> showTypeList;
	/**
	 * 显示规则代码list
	 */
	private List<Dictionary> showRuleList;

	/**
	 * 
	 * Description:查询
	 * 
	 * @return
	 */
	public String search() {
		queryCriteria = new QueryCriteria();
		queryCriteria.setCurrentIndex(0);
		queryCriteria.setPageSize(-1);
		tables = tableService.queryTable(queryCriteria);
		return "search";
	}

	/**
	 * 增加字段
	 */
	public String add() {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			this.column = new Column();
			queryCriteria = new QueryCriteria();
			queryCriteria.setCurrentIndex(0);
			queryCriteria.setPageSize(-1);
			tables = tableService.queryTable(queryCriteria);

			queryColumnValues(this.column);

			dataTypeList = dictionaryService.queryByParent(10372524119L);
			showRuleList = dictionaryService.queryByParent(10339540171L);
			showTypeList = dictionaryService.queryByParent(1440112160L);

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

	/**
	 * 编辑字段
	 */
	public String edit() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		id = Long.valueOf(params.substring(params.indexOf(":") + 1, params.indexOf(";")));
		this.column = columnService.findColumnById(id);
		queryCriteria = new QueryCriteria();
		queryCriteria.setCurrentIndex(0);
		queryCriteria.setPageSize(-1);
		tables = tableService.queryTable(queryCriteria);

		queryColumnValues(this.column);

		dataTypeList = dictionaryService.queryByParent(10372524119L);
		showRuleList = dictionaryService.queryByParent(10339540171L);
		showTypeList = dictionaryService.queryByParent(1440112160L);

		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		Form tform = formService.findFormByIdAndDomainVO(formId, domainVOs, user);
		this.formButtons = tform.getFormButtons();

		return "edit";
	}

	/**
	 * 保存字段
	 */
	public String save() {
		try {

			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			if (columnName != null && !"".equals(columnName)) {
				column.setColumnName(columnName);
			}
			try {
				if (column.getDefaultValue() != null && !"".equals(column.getDefaultValue())) {
					if ("int".equals(column.getDataType())) {
						Integer.valueOf(column.getDefaultValue());
					}
					if ("bigint".equals(column.getDataType())) {
						Long.valueOf(column.getDefaultValue());
					}
					if ("date".equals(column.getDataType())) {
						Date date = DateUtil.formatDate(column.getDefaultValue(), DateUtil.DATEFORMAT_DAY);
						if (date == null) {
							throw new AppException("数据库默认值类型不符!");
						}
					}
					if ("timestamp".equals(column.getDataType())) {
						Date date = DateUtil.formatDate(column.getDefaultValue(), DateUtil.LEG_TIMESTAMP_FORMATE);
						if (date == null) {
							throw new AppException("数据库默认值类型不符!");
						}
					}
				}

				if (column.getId() != null && !"".equals(column.getId())) {
					if (this.columnService.isColumnZhNameDouble(column.getTableId(), column.getColumnZhName(), column.getId())) {
						throw new AppException("字段名称重复!");
					}
					if (this.columnService.isColumnNameDouble(column.getTableId(), column.getColumnName(), column.getId())) {
						throw new AppException("拼音名称重复!");
					}
					column.setUpdateDate(new Date());
					column.setUpdateBy(user.getId());
					this.columnService.doUpdateColumn(column, dyncMap);
				} else {
					if (this.columnService.isColumnZhNameDouble(column.getTableId(), column.getColumnZhName(), column.getId())) {
						throw new AppException("字段名称重复!");
					}
					if (this.columnService.isColumnNameDouble(column.getTableId(), Constants.COLUMN_NAME_PREFIX + column.getColumnName(),
							column.getId())) {
						throw new AppException("拼音名称重复!");
					}
					column.setCreateDate(new Date());
					column.setCreateBy(user.getId());
					column.setUpdateDate(new Date());
					column.setUpdateBy(user.getId());
					column.setNewFlag(1);
					column.setColumnName(Constants.COLUMN_NAME_PREFIX + column.getColumnName());
					this.columnService.doSaveColumn(column, dyncMap);
				}
				printJSON("success");
			} catch (NumberFormatException e) {
				printJSON("300", "数据库默认值类型不符！", false);
			} catch (AppException e) {
				printJSON("300", e.getMessage(), false);
			} catch (Exception e) {
				e.printStackTrace();
				printJSON("fail");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/**
	 * 删除字段
	 */
	public String delete() throws IOException {
		try {
			if (selectedVOs != null) {
				selectedIDs = new Long[selectedVOs.length];
				for (int i = 0; i < selectedVOs.length; i++) {
					String vo = selectedVOs[i];
					List<DomainVO> ds = getDomainVos(vo);
					Long id = Long.valueOf(vo.substring(vo.indexOf(":") + 1, vo.indexOf(";")));
					int count = formService.countColumnInForm(id);
					if (count > 0) {
						throw new AppException("表单设计中仍引用该字段！");
					}
					selectedIDs[i] = id;
				}
				this.columnService.doDeleteColumns(selectedIDs);
			} else {
				this.columnService.doDeleteColumn(id);
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
	
	/**
	 * 删除数据表字段
	 */
	public String deleteColumn() throws IOException {
		try {
			if (selectedSubIDs != null) {
				for (int i = 0; i < selectedSubIDs.length; i++) {
					Long id = selectedSubIDs[i];
					int count = formService.countColumnInForm(id);
					if (count > 0) {
						throw new AppException("表单设计中仍引用该字段！");
					}
				}
				this.columnService.doDeleteColumns(selectedSubIDs);
			} else {
				this.columnService.doDeleteColumn(id);
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

	/**
	 * 删除字段
	 */
	public String logicDelete() throws IOException {
		try {
			if (selectedVOs != null) {
				selectedIDs = new Long[selectedVOs.length];
				for (int i = 0; i < selectedVOs.length; i++) {
					String vo = selectedVOs[i];
					List<DomainVO> ds = getDomainVos(vo);
					Long id = Long.valueOf(vo.substring(vo.indexOf(":") + 1, vo.indexOf(";")));
					int count = formService.countColumnInForm(id);
					if (count > 0) {
						throw new AppException("表单设计中仍引用该字段！");
					}
					selectedIDs[i] = id;
				}
				this.columnService.doLogicDeleteColumns(selectedIDs);
			} else {
				this.columnService.doLogicDeleteColumn(id);
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

	/**
	 * 编辑字段
	 */
	public String view() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		id = Long.valueOf(params.substring(params.indexOf(":") + 1, params.indexOf(";")));
		this.column = columnService.findColumnById(id);
		queryColumnValues(this.column);

		dataTypeList = dictionaryService.queryByParent(10372524119L);
		showRuleList = dictionaryService.queryByParent(10339540171L);
		showTypeList = dictionaryService.queryByParent(1440112160L);

		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		Form tform = formService.findFormByIdAndDomainVO(formId, domainVOs, user);
		this.formButtons = tform.getFormButtons();

		return "view";
	}

	/**
	 * Description:返回数据表json串
	 * 
	 * @return
	 * @throws IOException
	 */
	public String subTable() throws IOException {
		Table table = tableService.findTableById(id);
		String name = table.getTableName();
		if (name.length() > 3) {
			name = name.substring(0, 3);
		}
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(name);
		return NONE;
	}

	/**
	 * Description:发布字段
	 * 
	 * @return
	 * @throws IOException
	 */
	public String publish() throws IOException {
		this.column = columnService.findColumnById(id);
		if (column.getIsPublish() == 0) {// 发布
			column.setIsPublish(1);
		} else {// 回收
			column.setIsPublish(0);
		}
		String ret = "success";
		try {
			columnService.doUpdateColumn(column);
		} catch (Exception e) {
			e.printStackTrace();
			ret = "fail";
		}
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(ret);
		return NONE;
	}

	/**
	 * 
	 * Description:加锁字段
	 * 
	 * @return
	 * @throws Exception
	 */
	public String lock() throws Exception {
		try {
			SysUser user = (SysUser) getSession().getAttribute("user");

			if (!user.isSuper()) {
				printJSON("300", "无权限操作！", false);
			} else {
				columnService.doUpdateStauts(id, 1);
				printJSON("success");
			}
		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
			printJSON("fail");
		}
		return NONE;
	}

	/**
	 * 
	 * Description:解锁字段
	 * 
	 * @return
	 * @throws Exception
	 */
	public String unLock() throws Exception {
		try {
			SysUser user = (SysUser) getSession().getAttribute("user");

			if (!user.isSuper()) {
				printJSON("300", "无权限操作！", false);
			} else {
				columnService.doUpdateStauts(id, 0);
				printJSON("success");
			}
		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
			printJSON("fail");
		}
		return NONE;
	}

	/**
	 * Description:查询字段值
	 * 
	 * @param c
	 */
	private void queryColumnValues(Column c) {
		queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		queryCriteria.addQueryCondition("tbl_belongTable", "sys_columns");
		List columns = columnService.queryColumn(queryCriteria);
		columnExtends = new ArrayList();
		for (int i = 0; columns != null && i < columns.size(); i++) {
			Column column = (Column) (columns.get(i));
			String value = columnService.findValueByColumn(c.getBelongTable(), column.getColumnName(), c.getColumnName());
			ColumnExtend ce = new ColumnExtend(column, value);
			columnExtends.add(ce);
		}
	}
	
	public String addColumn() throws Exception {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			this.column = new Column();
			queryCriteria = new QueryCriteria();
			queryCriteria.setCurrentIndex(0);
			queryCriteria.setPageSize(-1);
			tables = tableService.queryTable(queryCriteria);

			queryColumnValues(this.column);

			dataTypeList = dictionaryService.queryByParent(10372524119L);
			showRuleList = dictionaryService.queryByParent(10339540171L);
			showTypeList = dictionaryService.queryByParent(1440112160L);

			List<DomainVO> domainVOs = new ArrayList<DomainVO>();
			Form tform = formService.findFormByIdAndDomainVO(formId, domainVOs, user);
			this.formButtons = tform.getFormButtons();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addColumn";
	}
	
	public String editColumn() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		this.column = columnService.findColumnById(id);
		queryCriteria = new QueryCriteria();
		queryCriteria.setCurrentIndex(0);
		queryCriteria.setPageSize(-1);
		tables = tableService.queryTable(queryCriteria);

		queryColumnValues(this.column);

		dataTypeList = dictionaryService.queryByParent(10372524119L);
		showRuleList = dictionaryService.queryByParent(10339540171L);
		showTypeList = dictionaryService.queryByParent(1440112160L);

		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		Form tform = formService.findFormByIdAndDomainVO(formId, domainVOs, user);
		this.formButtons = tform.getFormButtons();

		return "addColumn";
	}
	
	public String columnList() {
		this.numPerPage = 10;
		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.addQueryCondition("tbl_tableId", tableId);
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1)
				* this.numPerPage + 1);
		queryCriteria.setPageSize(this.numPerPage);
		List<Column> _columnList = columnService.queryColumn(queryCriteria);

		this.pageResult = new PageResult();
		this.pageResult.setContent(_columnList);
		this.pageResult.setTotalCount(columnService.countColumn(queryCriteria));
		this.pageResult.setPageSize(this.numPerPage);
		this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
		this.pageResult.setCountPage((_columnList.size() + this.numPerPage - 1)
				/ this.numPerPage);
		return "columnList";
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column Column) {
		this.column = Column;
	}

	public ColumnService getColumnService() {
		return columnService;
	}

	public void setColumnService(ColumnService ColumnService) {
		this.columnService = ColumnService;
	}

	public TableService getTableService() {
		return tableService;
	}

	public void setTableService(TableService tableService) {
		this.tableService = tableService;
	}

	public List getTables() {
		return tables;
	}

	public void setTables(List tables) {
		this.tables = tables;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ColumnExtend> getColumnExtends() {
		return columnExtends;
	}

	public void setColumnExtends(List<ColumnExtend> columnExtends) {
		this.columnExtends = columnExtends;
	}

	public Map<String, String> getDyncMap() {
		return dyncMap;
	}

	public void setDyncMap(Map<String, String> dyncMap) {
		this.dyncMap = dyncMap;
	}

	public String getColumnZhName() {
		return columnZhName;
	}

	public void setColumnZhName(String columnZhName) {
		this.columnZhName = columnZhName;
	}

	public List<Dictionary> getDataTypeList() {
		return dataTypeList;
	}

	public void setDataTypeList(List<Dictionary> dataTypeList) {
		this.dataTypeList = dataTypeList;
	}

	public List<Dictionary> getShowTypeList() {
		return showTypeList;
	}

	public void setShowTypeList(List<Dictionary> showTypeList) {
		this.showTypeList = showTypeList;
	}

	public List<Dictionary> getShowRuleList() {
		return showRuleList;
	}

	public void setShowRuleList(List<Dictionary> showRuleList) {
		this.showRuleList = showRuleList;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public String getSeqcode() {
		return seqcode;
	}

	public void setSeqcode(String seqcode) {
		this.seqcode = seqcode;
	}

	/**
	 * @return Returns the value of tableId field with the type Long.
	 */
	public Long getTableId() {
		return tableId;
	}

	/**
	 * @param tableId. Set the value of tableId field with the type Long by the tableId parameter.
	 */
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

}
