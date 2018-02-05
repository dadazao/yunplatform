package com.cloudstong.platform.resource.form.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.model.Code;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.core.util.DateUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.autocomplete.dao.AutoCompleteDao;
import com.cloudstong.platform.resource.autocomplete.model.AutoComplete;
import com.cloudstong.platform.resource.buttongroup.dao.ButtonAndGroupDao;
import com.cloudstong.platform.resource.buttongroup.dao.ButtonGroupDao;
import com.cloudstong.platform.resource.buttongroup.model.ButtonAndGroup;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;
import com.cloudstong.platform.resource.checkbox.dao.CheckBoxMgtDAO;
import com.cloudstong.platform.resource.checkbox.vo.CheckBoxMgtVO;
import com.cloudstong.platform.resource.codecasecade.dao.CodeCaseCadeDao;
import com.cloudstong.platform.resource.codecasecade.model.CodeCaseCade;
import com.cloudstong.platform.resource.combox.dao.ComboxDao;
import com.cloudstong.platform.resource.combox.model.Combox;
import com.cloudstong.platform.resource.date.dao.DateDao;
import com.cloudstong.platform.resource.date.model.DateControl;
import com.cloudstong.platform.resource.dictionary.dao.DictionaryDao;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.editor.dao.TextEditorDao;
import com.cloudstong.platform.resource.editor.model.TextEditor;
import com.cloudstong.platform.resource.form.dao.FormButtonDao;
import com.cloudstong.platform.resource.form.dao.FormDao;
import com.cloudstong.platform.resource.form.dao.TabDao;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.resource.form.model.Tab;
import com.cloudstong.platform.resource.passbox.dao.PassboxDao;
import com.cloudstong.platform.resource.passbox.model.Passbox;
import com.cloudstong.platform.resource.radio.dao.RadioMgtDAO;
import com.cloudstong.platform.resource.radio.vo.RadioMgtVO;
import com.cloudstong.platform.resource.searchcombox.dao.SearchComboxDao;
import com.cloudstong.platform.resource.searchcombox.model.SearchCombox;
import com.cloudstong.platform.resource.seqcode.dao.SeqcodeDao;
import com.cloudstong.platform.resource.tabulation.dao.TabulationDao;
import com.cloudstong.platform.resource.template.dao.PartitionDao;
import com.cloudstong.platform.resource.template.dao.TemplateDao;
import com.cloudstong.platform.resource.template.model.Partition;
import com.cloudstong.platform.resource.template.model.Template;
import com.cloudstong.platform.resource.textarea.dao.TextareaDao;
import com.cloudstong.platform.resource.textarea.model.Textarea;
import com.cloudstong.platform.resource.textbox.dao.TextBoxDao;
import com.cloudstong.platform.resource.textbox.model.TextBox;
import com.cloudstong.platform.resource.uploadbox.dao.UploadBoxDao;
import com.cloudstong.platform.resource.uploadbox.model.UploadBox;
import com.cloudstong.platform.resource.uploadify.dao.UploadifyDao;
import com.cloudstong.platform.resource.uploadify.model.Uploadify;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- ------- ----------------------------------------------------
 * 
 *         Description:表单操作数据库接口实现类
 */
@Repository("formDao")
public class FormDaoImpl implements FormDao {

	@javax.annotation.Resource
	private JdbcTemplateExtend jdbcTemplate;
	@Resource
	private TabDao tabDao;
	@Resource
	private TextBoxDao textBoxDao;
	@Resource
	private AutoCompleteDao autoCompleteDao;
	@Resource
	private PassboxDao passboxDao;
	@Resource
	private ComboxDao comboxDao;
	@Resource
	private SearchComboxDao searchComboxDao;
	@Resource
	private TextareaDao textareaDao;
	@Resource
	private PassboxDao passDao;
	@Resource
	private UploadBoxDao uploadBoxDao;
	@Resource
	private DateDao dateDao;

	@Resource
	private TextEditorDao textEditorDao;

	@Resource
	private CodeCaseCadeDao codeCaseCadeDao;

	@Resource
	private UploadifyDao uploadifyDao;

	@Resource
	private DictionaryDao dictionaryDao;

	@Resource
	private FormButtonDao formButtonDao;

	@Resource
	private ButtonGroupDao buttonGroupDao;

	@Resource
	private ButtonAndGroupDao buttonAndGroupDao;

	@Resource
	private TemplateDao templateDao;

	@Resource
	private PartitionDao partitionDao;

	@Resource
	private SeqcodeDao seqcodeDao;

	@Resource
	private TabulationDao tabulationDao;

	@Resource
	private RadioMgtDAO radioMgtDAO;

	@Resource
	private CheckBoxMgtDAO checkBoxMgtDAO;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	class FormRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Form u = new Form();
			u.setId(rs.getLong("id"));
			u.setFormName(rs.getString("tbl_biaodanming"));
			u.setCode(rs.getString("tbl_bianma"));
			u.setRight(rs.getString("tbl_quanxian"));
			u.setIsUploadFile(rs.getInt("tbl_shangchuanwenjian"));
			u.setCreateBy(rs.getLong("comm_createBy"));
			u.setCreateDate(rs.getTimestamp("comm_createDate"));
			u.setRemarks(rs.getString("tbl_remarks"));
			u.setDesc(rs.getString("tbl_beizhu"));
			u.setTableName(rs.getString("tbl_zhubiao"));
			u.setTableChName(rs.getString("tbl_tableChName"));
			u.setUserName(rs.getString("tbl_createname"));
			u.setWidth(rs.getInt("tbl_width"));
			u.setHeight(rs.getInt("tbl_height"));
			u.setJiaoben(rs.getString("tbl_jiaoben"));
			u.setXjTitle(rs.getString("tbl_xjtitle"));
			u.setWhTitle(rs.getString("tbl_whtitle"));
			u.setSystemTeam(rs.getString("tbl_systemteam"));
			return u;
		}
	}

	@Override
	@Cacheable(value = "formCache")
	public List select(String where, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplate.query(where, args, new FormRowMapper());
		} else {
			retList = this.jdbcTemplate.querySP(where, args, startRow, rowsCount, new FormRowMapper());
		}
		return retList;
	}

	@Override
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void update(Form u) {
		String sql = "update sys_biaodan set tbl_systemteam=?,tbl_xjtitle=?,tbl_whtitle=?,tbl_width=?,tbl_height=?,tbl_biaodanming=?,tbl_quanxian=?,tbl_shangchuanwenjian=?,tbl_remarks=?,tbl_beizhu=?,tbl_zhubiao=?,tbl_zhubiaoid=?,comm_updateBy=?,comm_updateDate=?,tbl_jiaoben=? where id=?";
		Object[] params = new Object[] { u.getSystemTeam(), u.getXjTitle(), u.getWhTitle(), u.getWidth(), u.getHeight(), u.getFormName(),
				u.getRight(), u.getIsUploadFile(), u.getRemarks(), u.getDesc(), u.getTableName(), u.getTableId(), u.getUpdateBy(), u.getUpdateDate(),
				u.getJiaoben(), u.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public Long insert(Form u) {
		Long id = UniqueIdUtil.genId();
		String sql = "insert into sys_biaodan(id,tbl_systemteam,tbl_xjtitle,tbl_whtitle,tbl_width,tbl_height,tbl_biaodanming,tbl_bianma,tbl_quanxian,tbl_shangchuanwenjian,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate,tbl_remarks,tbl_beizhu,tbl_zhubiao,tbl_zhubiaoid,tbl_jiaoben) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[] { id, u.getSystemTeam(), u.getXjTitle(), u.getWhTitle(), u.getWidth(), u.getHeight(), u.getFormName(),
				u.getCode(), u.getRight(), u.getIsUploadFile(), u.getCreateBy(), u.getCreateDate(), u.getUpdateBy(), u.getUpdateDate(),
				u.getRemarks(), u.getDesc(), u.getTableName(), u.getTableId(), u.getJiaoben() };
		jdbcTemplate.update(sql, params);
		return id;
	}

	@Override
	@Cacheable(value = "formCache", key = "'form_selectById:'+#id")
	public Form selectById(Long id) {
		String sql = "select b.*,t.tbl_tableZhName as tbl_tableChName,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_biaodan b left join sys_tables t on b.tbl_zhubiao = t.tbl_tableName left join sys_user u on b.comm_createBy = u.id left join sys_user u2 on b.comm_updateBy = u2.id where b.id=?";
		final Form u = new Form();
		final Object[] params = new Object[] { id };
		query(sql, params, u);

		// 选项卡
		List<Tab> tabs = tabDao.selectByFormId(id);
		u.setTabs(tabs);

		// 查询出表单元素
		String sqls = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,"
				+ "c.tbl_columnName as tbl_ziduanming,c.tbl_length as tbl_length,c.tbl_comment as tbl_colcomment,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a "
				+ "left join sys_tables b on a.tbl_tableid=b.id left join sys_columns c on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_form=? order by a.tbl_biaodanxianshishunxu asc";
		Object[] paramss = new Object[] { id };
		List<FormColumn> formColumns = jdbcTemplate.query(sqls, paramss, new FormColumnRowMapper());
		List<FormColumnExtend> formColumnExtends = new ArrayList<FormColumnExtend>();
		assembleFormColumn(formColumnExtends, formColumns);
		u.setFormColumnExtends(formColumnExtends);

		// 查询出列表元素
		String listSql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,c.tbl_columnName as tbl_ziduanming,"
				+ "c.tbl_length as tbl_length,c.tbl_comment as tbl_colcomment,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b on a.tbl_tableid=b.id "
				+ "left join sys_columns c on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_form=? and a.tbl_liebiaoxianshi=1 order by a.tbl_liebiaoxianshishunxu asc";
		Object[] listParams = new Object[] { id };
		List<FormColumn> listColumns = jdbcTemplate.query(listSql, listParams, new FormColumnRowMapper());
		List<FormColumnExtend> listColumnExtends = new ArrayList<FormColumnExtend>();
		assembleFormColumn(listColumnExtends, listColumns);
		u.setTabulationColumnExtends(listColumnExtends);

		return u;
	}

	/**
	 * 组装表单元素
	 * 
	 * @param formColumnExtends
	 * @param formColumns
	 */
	private void assembleFormColumn(List<FormColumnExtend> formColumnExtends, List<FormColumn> formColumns) {
		for (FormColumn formColumn : formColumns) {
			FormColumnExtend fce = new FormColumnExtend();
			fce.setFormColumn(formColumn);
			formColumnExtends.add(fce);
		}

	}

	private void query(String sql, Object[] params, final Form u) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				u.setId(rs.getLong("id"));
				u.setFormName(rs.getString("tbl_biaodanming"));
				u.setCode(rs.getString("tbl_bianma"));
				u.setRight(rs.getString("tbl_quanxian"));
				u.setIsUploadFile(rs.getInt("tbl_shangchuanwenjian"));
				u.setCreateBy(rs.getLong("comm_createBy"));
				u.setCreateDate(rs.getTimestamp("comm_createDate"));
				u.setUpdateBy(rs.getLong("comm_updateBy"));
				u.setUpdateDate(rs.getTimestamp("comm_updateDate"));
				u.setRemarks(rs.getString("tbl_remarks"));
				u.setDesc(rs.getString("tbl_beizhu"));
				u.setTableName(rs.getString("tbl_zhubiao"));
				u.setTableId(rs.getLong("tbl_zhubiaoid"));
				u.setTableChName(rs.getString("tbl_tableChName"));
				u.setUserName(rs.getString("tbl_createname"));
				u.setUpdateName(rs.getString("tbl_updatename"));
				u.setWidth(rs.getInt("tbl_width"));
				u.setHeight(rs.getInt("tbl_height"));
				u.setJiaoben(rs.getString("tbl_jiaoben"));
				u.setXjTitle(rs.getString("tbl_xjtitle"));
				u.setWhTitle(rs.getString("tbl_whtitle"));
				u.setSystemTeam(rs.getString("tbl_systemteam"));
				u.setMainTable(rs.getString("tbl_zhubiao"));
			}
		});
	}

	private void compexQuery(String sql, Object[] params, final Form u) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				u.setId(rs.getLong("id"));
				u.setFormName(rs.getString("tbl_biaodanming"));
				u.setCode(rs.getString("tbl_bianma"));
				u.setRight(rs.getString("tbl_quanxian"));
				u.setIsUploadFile(rs.getInt("tbl_shangchuanwenjian"));
				u.setCreateBy(rs.getLong("comm_createBy"));
				u.setCreateDate(rs.getTimestamp("comm_createDate"));
				u.setUpdateBy(rs.getLong("comm_updateBy"));
				u.setUpdateDate(rs.getTimestamp("comm_updateDate"));
				u.setRemarks(rs.getString("tbl_remarks"));
				u.setDesc(rs.getString("tbl_beizhu"));
				u.setTableName(rs.getString("tbl_zhubiao"));
				u.setTableId(rs.getLong("tbl_zhubiaoid"));
				u.setTableChName(rs.getString("tbl_tableChName"));
				u.setWidth(rs.getInt("tbl_width"));
				u.setHeight(rs.getInt("tbl_height"));
				u.setJiaoben(rs.getString("tbl_jiaoben"));
				u.setXjTitle(rs.getString("tbl_xjtitle"));
				u.setWhTitle(rs.getString("tbl_whtitle"));
				u.setSystemTeam(rs.getString("tbl_systemteam"));
			}
		});
	}

	@Override
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void delete(Long id) {
		// 删除表单
		String sql = "delete from sys_biaodan where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);

		// 删除表单按钮
		String selbtnsql = "select * from sys_biaodanbutton where tbl_formid=?";
		List<Map<String, Object>> btnList = jdbcTemplate.queryForList(selbtnsql, params);
		for (int i = 0; i < btnList.size(); i++) {
			// 删除编码
			String code = ((Map<String, Object>) btnList.get(i)).get("tbl_bianma").toString();
			seqcodeDao.deleteSeq(code);
		}

		String buttonSql = "delete from sys_biaodanbutton where tbl_formid=?";
		jdbcTemplate.update(buttonSql, params);

		// 删除选项卡
		String tabSql = "delete from sys_biaodantab where tbl_form=?";
		jdbcTemplate.update(tabSql, params);

		// 删除表单元素
		String selformcolsql = "select * from sys_biaodansheji where tbl_form=?";
		List<Map<String, Object>> formcolList = jdbcTemplate.queryForList(selformcolsql, params);
		for (int i = 0; i < formcolList.size(); i++) {
			// 删除编码
			try {
				String code = ((Map<String, Object>) formcolList.get(i)).get("tbl_bianma").toString();
				seqcodeDao.deleteSeq(code);
			} catch (Exception e) {
			}

		}
		String columnSql = "delete from sys_biaodansheji where tbl_form=?";
		jdbcTemplate.update(columnSql, params);
	}

	@Override
	@Cacheable(value = "formCache")
	public int count(String sql, Object[] array) {
		List counts = jdbcTemplate.query(sql, array, new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			return rs.getInt(1);
		}
	}

	@Override
	public Long selectMaxId() {
		String sql = "select MAX(id) from sys_biaodan";
		List counts = jdbcTemplate.query(sql, new MaxRowMapper());
		return (Long) (counts.get(0));
	}

	class MaxRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Long ret = rs.getLong(1);
			return ret;
		}
	}

	@Override
	@Cacheable(value = "formCache")
	public int countFormColumn(String sql, Object[] array) {
		List counts = null;
		try {
			counts = jdbcTemplate.query(sql, array, new CountRowMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return (Integer) (counts.get(0));
	}

	@Override
	@Cacheable(value = "formCache")
	public List selectFormColumn(String where, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplate.query(where, args, new FormColumnRowMapper());
		} else {
			retList = this.jdbcTemplate.querySP(where, args, startRow, rowsCount, new FormColumnRowMapper());
		}
		return retList;
	}

	class FormColumnRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			FormColumn u = new FormColumn();
			u.setId(rs.getLong("id"));
			u.setFormId(rs.getLong("tbl_form"));
			u.setCode(rs.getString("tbl_bianma"));
			u.setColumnId(rs.getLong("tbl_ziduan"));
			u.setTableId(rs.getLong("tbl_tableid"));
			u.setColumnZhName(rs.getString("tbl_ziduanmingzh"));
			u.setTableZhName(rs.getString("tbl_suoshubiaoming"));
			u.setFormOrder(rs.getString("tbl_biaodanxianshishunxu") == null ? null : rs.getInt("tbl_biaodanxianshishunxu"));
			u.setIsShowInList(rs.getInt("tbl_liebiaoxianshi"));
			u.setIsQuery(rs.getInt("tbl_chaxuntiaojian"));
			u.setIsDefaultQuery(rs.getInt("tbl_morenchaxun"));
			u.setIsUse(rs.getInt("tbl_qiyong"));
			u.setListOrder(rs.getString("tbl_liebiaoxianshishunxu") == null ? null : rs.getInt("tbl_liebiaoxianshishunxu"));
			u.setInputType(rs.getInt("tbl_luruleixing"));
			u.setTextBoxId(rs.getLong("tbl_wenbenkuang"));
			u.setTabName(rs.getString("tbl_tabmingcheng"));
			u.setTabId(rs.getLong("tbl_tabid"));
			u.setPartitionId(rs.getLong("tbl_partitionid"));
			u.setComBoxId(rs.getLong("tbl_xialakuang"));
			u.setSelectDataType(rs.getInt("tbl_shujuleixing"));
			u.setCodeParentId(rs.getLong("tbl_daima"));
			u.setRelationTable(rs.getString("tbl_guanxibiao"));
			u.setRelationColumn(rs.getString("tbl_guanxiziduan"));
			u.setDataType(rs.getString("tbl_datatype"));
			u.setLength(rs.getInt("tbl_length"));
			u.setTextFieldId(rs.getLong("tbl_wenbenyu"));
			u.setRequired(rs.getInt("tbl_required"));
			u.setValidate(rs.getString("tbl_validate"));
			u.setDateId(rs.getLong("tbl_dateid"));
			u.setRadioId(rs.getLong("tbl_radioid"));
			u.setCheckBoxId(rs.getLong("tbl_checkboxid"));
			u.setSearchComBoxId(rs.getLong("tbl_searchcomboxid"));
			u.setTreeId(rs.getLong("tbl_treeid"));
			u.setEditorId(rs.getLong("tbl_editorid"));
			u.setCompexId(rs.getLong("tbl_compexid"));
			u.setIsLine(rs.getInt("tbl_isline"));
			u.setIsEdit(rs.getInt("tbl_isedit"));
			u.setIsView(rs.getInt("tbl_isview"));
			u.setDefaultValue(rs.getString("tbl_defaultvalue"));
			u.setIsShowIndex(rs.getInt("tbl_showindex"));
			u.setHasNull(rs.getInt("tbl_hasnull"));
			u.setNullName(rs.getString("tbl_nullname"));
			u.setCanReply(rs.getInt("tbl_canreply"));
			u.setColWidth(rs.getInt("tbl_kuandu"));
			u.setColUnit(rs.getString("tbl_danwei"));
			u.setColComment(rs.getString("tbl_colcomment"));
			u.setCanSelectItem(rs.getString("tbl_canselectitem"));
			u.setCodeShowOrder(rs.getInt("tbl_codeshoworder"));
			u.setHasDefaultItem(rs.getInt("tbl_hasdefaultitem"));
			u.setDefaultSelectItem(rs.getString("tbl_defaultselectitem"));
			u.setRecordColumn(rs.getString("tbl_recordcolumn"));
			u.setIsLinkView(rs.getInt("tbl_islinkview"));
			u.setSupportOrder(rs.getInt("tbl_supportorder"));
			u.setRelColumnId(rs.getLong("tbl_relcolumnid"));
			u.setOfficeMode(rs.getInt("tbl_officemode"));

			String c = rs.getString("tbl_ziduanming");
			String t = rs.getString("tbl_suoshubiao");
			u.setColumnName(c);
			u.setBelongTable(t);
			u.setQueryStr(t + "_" + c);

			return u;
		}
	}

	class ColumnRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			FormColumn u = new FormColumn();
			u.setId(rs.getLong("id"));
			u.setColumnName(rs.getString("tbl_columnName"));
			u.setColumnZhName(rs.getString("tbl_columnZhName"));
			u.setBelongTable(rs.getString("tbl_belongTable"));
			return u;
		}
	}

	@Override
	@Cacheable(value = "formCache", key = "'selectFormColumnById:'+#id")
	public FormColumn selectFormColumnById(Long id) {
		String sql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,"
				+ "c.tbl_columnName as tbl_ziduanming,c.tbl_length as tbl_length,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b on a.tbl_tableid=b.id "
				+ "left join sys_columns c on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.id=?";
		final FormColumn u = new FormColumn();
		final Object[] params = new Object[] { id };
		queryFormColumn(sql, params, u);

		return u;
	}

	private void queryFormColumn(String sql, Object[] params, final FormColumn u) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				u.setId(rs.getLong("id"));
				u.setCode(rs.getString("tbl_bianma"));
				u.setFormId(rs.getLong("tbl_form"));
				u.setColumnId(rs.getLong("tbl_ziduan"));
				u.setTableId(rs.getLong("tbl_tableid"));
				u.setColumnZhName(rs.getString("tbl_ziduanmingzh"));
				u.setTableZhName(rs.getString("tbl_suoshubiaoming"));
				u.setFormOrder(rs.getInt("tbl_biaodanxianshishunxu"));
				u.setIsShowInList(rs.getInt("tbl_liebiaoxianshi"));
				u.setIsQuery(rs.getInt("tbl_chaxuntiaojian"));
				u.setIsDefaultQuery(rs.getInt("tbl_morenchaxun"));
				u.setIsUse(rs.getInt("tbl_qiyong"));
				u.setListOrder(rs.getInt("tbl_liebiaoxianshishunxu"));
				u.setInputType(rs.getInt("tbl_luruleixing"));
				u.setTextBoxId(rs.getLong("tbl_wenbenkuang"));
				u.setTabName(rs.getString("tbl_tabmingcheng"));
				u.setTabId(rs.getLong("tbl_tabid"));
				u.setPartitionId(rs.getLong("tbl_partitionid"));
				u.setComBoxId(rs.getLong("tbl_xialakuang"));
				u.setSelectDataType(rs.getInt("tbl_shujuleixing"));
				u.setRelationTable(rs.getString("tbl_guanxibiao"));
				u.setRelationColumn(rs.getString("tbl_guanxiziduan"));
				u.setDataType(rs.getString("tbl_datatype"));
				u.setTextFieldId(rs.getLong("tbl_wenbenyu"));
				u.setRequired(rs.getInt("tbl_required"));
				u.setValidate(rs.getString("tbl_validate"));
				u.setDateId(rs.getLong("tbl_dateid"));
				u.setRadioId(rs.getLong("tbl_radioid"));
				u.setCheckBoxId(rs.getLong("tbl_checkboxid"));
				u.setSearchComBoxId(rs.getLong("tbl_searchcomboxid"));
				u.setTreeId(rs.getLong("tbl_treeid"));
				u.setCompexId(rs.getLong("tbl_compexid"));
				u.setIsLine(rs.getInt("tbl_isline"));
				u.setIsEdit(rs.getInt("tbl_isedit"));
				u.setIsView(rs.getInt("tbl_isview"));
				u.setDefaultValue(rs.getString("tbl_defaultvalue"));
				u.setIsShowIndex(rs.getInt("tbl_showindex"));
				u.setHasNull(rs.getInt("tbl_hasnull"));
				u.setNullName(rs.getString("tbl_nullname"));
				u.setCanReply(rs.getInt("tbl_canreply"));
				u.setColWidth(rs.getInt("tbl_kuandu"));
				u.setColUnit(rs.getString("tbl_danwei"));
				u.setCanSelectItem(rs.getString("tbl_canselectitem"));
				u.setCodeShowOrder(rs.getInt("tbl_codeshoworder"));
				u.setHasDefaultItem(rs.getInt("tbl_hasdefaultitem"));
				u.setDefaultSelectItem(rs.getString("tbl_defaultselectitem"));
				u.setRecordColumn(rs.getString("tbl_recordcolumn"));
				u.setIsLinkView(rs.getInt("tbl_islinkview"));
				u.setSupportOrder(rs.getInt("tbl_supportorder"));
				u.setOfficeMode(rs.getInt("tbl_officemode"));

				String c = rs.getString("tbl_ziduanming");
				String t = rs.getString("tbl_suoshubiao");
				u.setColumnName(c);
				u.setBelongTable(t);
				u.setQueryStr(t + "_" + c);

				Long parentId = rs.getLong("tbl_daima");
				u.setCodeParentId(parentId);
				if (parentId != null) {
					Dictionary dic = dictionaryDao.selectById(parentId);
					u.setCodeParentName(dic.getName());
				}
			}
		});
	}

	@Override
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void updateFormColumn(FormColumn u) {
		String sql = "update sys_biaodansheji set tbl_supportorder = ?,tbl_officemode = ?,tbl_islinkview = ?,tbl_recordcolumn = ?,tbl_codeshoworder = ?,tbl_hasdefaultitem = ?,tbl_defaultselectitem = ?,tbl_canselectitem = ?,tbl_showindex = ?,tbl_isedit = ?,tbl_isview = ?,tbl_dateid = ?,tbl_radioid = ?,tbl_checkboxid = ?,tbl_searchcomboxid = ?,tbl_treeid = ?,tbl_editorid = ?,"
				+ "tbl_compexid = ?,tbl_required=?,tbl_wenbenyu=?,tbl_validate=?,tbl_xialakuang=?,tbl_shujuleixing=?,tbl_daima=?,tbl_guanxibiao=?,tbl_guanxiziduan=?,tbl_tabid=?,"
				+ "tbl_ziduan=?,tbl_wenbenkuang=?,tbl_tableid=?,tbl_suoshubiao=?,tbl_biaodanxianshishunxu=?,tbl_liebiaoxianshi=?,tbl_liebiaoxianshishunxu=?,tbl_chaxuntiaojian=?,"
				+ "tbl_morenchaxun=?,tbl_luruleixing=?,tbl_qiyong=?,tbl_form=?,tbl_partitionid=?,tbl_defaultvalue=?,tbl_hasnull=?,tbl_nullname=?,tbl_canreply=?,tbl_kuandu=?,tbl_danwei=? where id=?";
		Object[] params = new Object[] {u.getSupportOrder(), u.getOfficeMode(), u.getIsLinkView(), u.getRecordColumn(), u.getCodeShowOrder(), u.getHasDefaultItem(),
				u.getDefaultSelectItem(), u.getCanSelectItem(), u.getIsShowIndex(), u.getIsEdit(), u.getIsView(), u.getDateId(), u.getRadioId(),
				u.getCheckBoxId(), u.getSearchComBoxId(), u.getTreeId(), u.getEditorId(), u.getCompexId(), u.getRequired(), u.getTextFieldId(),
				u.getValidate(), u.getComBoxId(), u.getSelectDataType(), u.getCodeParentId(), u.getRelationTable(), u.getRelationColumn(),
				u.getTabId(), u.getColumnId(), u.getTextBoxId(), u.getTableId(), u.getBelongTable(), u.getFormOrder(), u.getIsShowInList(),
				u.getListOrder(), u.getIsQuery(), u.getIsDefaultQuery(), u.getInputType(), u.getIsUse(), u.getFormId(), u.getPartitionId(),
				u.getDefaultValue(), u.getHasNull(), u.getNullName(), u.getCanReply(), u.getColWidth(), u.getColUnit(), u.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value={"tabulationCache","formCache","domainCache"},allEntries=true,beforeInvocation=true)
	public Long insertFormColumn(FormColumn u) {
		Long id = -1L;
		try {
			id = UniqueIdUtil.genId();
			String sql = "insert into sys_biaodansheji(id,tbl_showindex,tbl_isedit,tbl_isview,tbl_compexid,tbl_required,tbl_validate,tbl_shujuleixing,tbl_daima,tbl_guanxibiao,tbl_guanxiziduan,tbl_tabid,"
					+ "tbl_ziduan,tbl_tableid,tbl_suoshubiao,tbl_biaodanxianshishunxu,tbl_liebiaoxianshi,tbl_liebiaoxianshishunxu,tbl_chaxuntiaojian,tbl_morenchaxun,tbl_luruleixing,tbl_qiyong,"
					+ "tbl_form,tbl_partitionid,tbl_defaultvalue,tbl_hasnull,tbl_nullname,tbl_canreply,tbl_kuandu,tbl_danwei,tbl_canselectitem,tbl_defaultselectitem,tbl_hasdefaultitem,tbl_codeshoworder,tbl_recordcolumn,tbl_islinkview,tbl_relcolumnid) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] params = new Object[] { id, u.getIsShowIndex(), u.getIsEdit(), u.getIsView(), u.getCompexId(), u.getRequired(), u.getValidate(),
					u.getSelectDataType(), u.getCodeParentId(), u.getRelationTable(), u.getRelationColumn(), u.getTabId(), u.getColumnId(),
					u.getTableId(), u.getBelongTable(), u.getFormOrder(), u.getIsShowInList(), u.getListOrder(), u.getIsQuery(),
					u.getIsDefaultQuery(), u.getInputType(), u.getIsUse(), u.getFormId(), u.getPartitionId(), u.getDefaultValue(), u.getHasNull(),
					u.getNullName(), u.getCanReply(), u.getColWidth(), u.getColUnit(), u.getCanSelectItem(), u.getDefaultSelectItem(),
					u.getHasDefaultItem(), u.getCodeShowOrder(), u.getRecordColumn(), u.getIsLinkView(), u.getRelColumnId() };
			jdbcTemplate.update(sql, params);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	@CacheEvict(value={"tabulationCache","formCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void deleteColumn(Long id) {
		String sql = "delete from sys_biaodansheji where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	public TabDao getTabDao() {
		return tabDao;
	}

	public void setTabDao(TabDao tabDao) {
		this.tabDao = tabDao;
	}

	@Override
	@Cacheable(value = "formCache", key = "'selectByTabId:'+#tabId")
	public List<FormColumn> selectByTabId(Long tabId) {
		String sql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,"
				+ "c.tbl_columnName as tbl_ziduanming,c.tbl_length as tbl_length,c.tbl_comment as tbl_colcomment,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b "
				+ "on a.tbl_tableid=b.id left join sys_columns c on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_tabid=? order by a.tbl_biaodanxianshishunxu asc";
		Object[] params = new Object[] { tabId };
		return jdbcTemplate.query(sql, params, new FormColumnRowMapper());
	}

	@Override
	@Cacheable(value = "formCache", key = "'selectEditByTabId:'+#tabId")
	public List<FormColumn> selectEditByTabId(String tabId) {
		String sql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,"
				+ "c.tbl_columnName as tbl_ziduanming,c.tbl_length as tbl_length,c.tbl_comment as tbl_colcomment,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b "
				+ "on a.tbl_tableid=b.id left join sys_columns c on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_tabid=? and a.tbl_isedit=1 order by a.tbl_biaodanxianshishunxu asc";
		Object[] params = new Object[] { tabId };
		return jdbcTemplate.query(sql, params, new FormColumnRowMapper());
	}

	@Cacheable(value = "formCache", key = "'selectListByTabId:'+#tabId")
	public List<FormColumn> selectListByTabId(String tabId) {
		String sql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,"
				+ "c.tbl_columnName as tbl_ziduanming,,c.tbl_length as tbl_length,c.tbl_comment as tbl_colcomment,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b"
				+ " on a.tbl_tableid=b.id left join sys_columns c on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_tabid=? order by a.tbl_liebiaoxianshishunxu asc";
		Object[] params = new Object[] { tabId };
		return jdbcTemplate.query(sql, params, new FormColumnRowMapper());
	}

	@Override
	@Cacheable(value = "formCache", key = "'selectByIdAndDomainVO:'+#id+'-'+#domains+#user.id")
	public Form selectByIdAndDomainVO(Long id, List<DomainVO> domains, SysUser user) throws Exception {
		String sql = "select b.*,t.tbl_tableZhName as tbl_tableChName,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_biaodan b left join sys_tables t "
				+ "on b.tbl_zhubiao = t.tbl_tableName left join sys_user u on b.comm_createBy = u.id   left join sys_user u2 on b.comm_updateBy = u2.id "
				+ "  where b.id=?";
		final Form u = new Form();
		final Object[] params = new Object[] { id };
		try {
			query(sql, params, u);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 表单按钮
		List<FormButton> formButtons = formButtonDao.selectByFormId(id);
		u.setFormButtons(formButtons);

		// 选项卡
		List<Tab> tabs = tabDao.selectListByFormId(id);
		for (int i = 0; tabs != null && i < tabs.size(); i++) {
			Tab tab = tabs.get(i);
			// 根据选项卡ID查询出该选项卡的表单元素
			List<FormColumn> formColumns = selectByTabId(tab.getId());

			List<FormColumnExtend> ceList = new ArrayList<FormColumnExtend>();
			assembleFormColumn(ceList, domains, formColumns, user);
			tab.setFormColumnExtends(ceList);

			// 根据选项卡buttonId查询出该选项卡的按钮组
			if (tab.getButtonId() != null && !tab.getButtonId().equals("0")) {
				ButtonGroup btGroup = buttonGroupDao.findByID(Long.valueOf(tab.getButtonId()));
				List<ButtonAndGroup> bags = buttonAndGroupDao.selectByButtonGroupId(String.valueOf(btGroup.getId()));
				btGroup.setButtonAndGroups(bags);
				tab.setButtonGroup(btGroup);
			}
		}
		u.setTabs(tabs);

		// 查询出表单元素的值
		String sqls = "select * from sys_columns where tbl_belongTable=? ";
		Object[] paramss = new Object[] { u.getTableName() };
		List<FormColumn> formColumns = jdbcTemplate.query(sqls, paramss, new ColumnRowMapper());
		List<FormColumnExtend> formColumnExtends = new ArrayList<FormColumnExtend>();
		assembleFormColumn(formColumnExtends, domains, formColumns, user);
		u.setFormColumnExtends(formColumnExtends);

		// 查询出列表元素的值
		String listSql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,"
				+ "c.tbl_columnName as tbl_ziduanming,c.tbl_length as tbl_length,c.tbl_comment as tbl_colcomment,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b"
				+ " on a.tbl_tableid=b.id left join sys_columns c on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_form=? order by a.tbl_liebiaoxianshishunxu asc";
		Object[] listParams = new Object[] { id };
		List<FormColumn> listColumns = jdbcTemplate.query(listSql, listParams, new FormColumnRowMapper());
		List<FormColumnExtend> listColumnExtends = new ArrayList<FormColumnExtend>();
		assembleFormColumn(listColumnExtends, domains, formColumns, user);
		u.setTabulationColumnExtends(listColumnExtends);

		return u;
	}

	/**
	 * 组装表单元素
	 * 
	 * @param ceList
	 * @param queryDomains
	 * @param formColumns
	 * @throws Exception
	 */
	private void assembleFormColumn(List<FormColumnExtend> ceList, List<DomainVO> queryDomains, List<FormColumn> formColumns, SysUser user)
			throws Exception {
		for (int m = 0; queryDomains != null && m < queryDomains.size(); m++) {
			Map retMap = new HashMap();
			Set keySet = new HashSet();
			// 查询出domain的列表，数据是Map的List
			String domainSql = "select * from sys_" + queryDomains.get(m).getTable() + " where id=?";
			Object[] args = new Object[] { queryDomains.get(m).getDomainId() };
			List retList = jdbcTemplate.query(domainSql, args, new ColumnMapRowMapper());

			if (retList != null && retList.size() > 0) {
				retMap = (Map) retList.get(0);
				keySet = retMap.keySet();
			}

			// 增加ID字段
			Long id = (Long)retMap.get("id");
			FormColumn ci = new FormColumn();
			ci.setColumnName("id");
			FormColumnExtend cei = new FormColumnExtend();
			cei.setFormColumn(ci);
			cei.setValue(id);
			ceList.add(cei);

			// 遍历表单元素
			for (int j = 0; formColumns != null && j < formColumns.size(); j++) {
				FormColumn c = (FormColumn) (formColumns.get(j));
				if (c.getBelongTable().equals(queryDomains.get(m).getTable())) {
					FormColumnExtend ce = new FormColumnExtend();
					ce.setFormColumn(c);
					if (keySet.contains(c.getColumnName())) {
						ce.setValue(retMap.get(c.getColumnName()));
					}
					// 文本框
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX) {
						TextBox tb = textBoxDao.findByID(c.getCompexId());
						ce.setComponent(tb);
					}
					//自动补齐文本框
					if(c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_AUTOCOMPLETE){
						AutoComplete autoComplete = autoCompleteDao.findByID(c.getCompexId());
						String columnValue = autoCompleteDao.findColumnValueList(c.getBelongTable(),c.getColumnName());
						autoComplete.setSource(columnValue);
						ce.setComponent(autoComplete);
					}
					// 密码框
					if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_PASSWORD) {
						Passbox passbox = passboxDao.findByID(c.getCompexId());
						ce.setComponent(passbox);
					}
					// 上传文件框
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FILE) {
						UploadBox uploadBox = uploadBoxDao.findByID(c.getCompexId());
						ce.setComponent(uploadBox);
					}
					// 下拉框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						Combox cb = comboxDao.findByID(c.getCompexId());
						ce.setComponent(cb);

						String order = "asc";
						if (c.getCodeShowOrder() != null) {
							if (c.getCodeShowOrder() == 0) {
								order = "asc";
							} else if (c.getCodeShowOrder() == 1) {
								order = "desc";
							}
						}

						List<Dictionary> dl = null;
						if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
						} else {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
						}

						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 搜索下拉框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
						ce.setComponent(scb);

						String order = "asc";
						if (c.getCodeShowOrder() != null) {
							if (c.getCodeShowOrder() == 0) {
								order = "asc";
							} else if (c.getCodeShowOrder() == 1) {
								order = "desc";
							}
						}

						List<Dictionary> dl = null;
						if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
						} else {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
						}

						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 单选框代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_RADIO
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						RadioMgtVO radioMgtVO = radioMgtDAO.selectById(c.getCompexId()).get(0);
						ce.setRadio(radioMgtVO);

						List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 多选框代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						CheckBoxMgtVO checkBoxMgtVO = checkBoxMgtDAO.selectById(c.getCompexId()).get(0);
						ce.setCheckBox(checkBoxMgtVO);

						List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 下拉框关系表
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
						Combox cb = comboxDao.findByID(c.getCompexId());
						ce.setComponent(cb);
						List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
						ce.setCodes(codes);
					}
					// 搜索下拉框 关系表
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
						SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
						ce.setComponent(scb);
						List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
						ce.setCodes(codes);
					}
					// 文本域
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FIELD) {
						Textarea textarea = textareaDao.findByID(c.getCompexId());
						ce.setComponent(textarea);
					}
					// 文本编辑器
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_EDITOR) {
						TextEditor textEditor = textEditorDao.findTextEditorById(c.getCompexId());
						ce.setTextEditor(textEditor);
					}
					// 日期组件
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_DATE) {
						DateControl dc = dateDao.findById(c.getCompexId());
						if (ce.getValue() != null) {
							if (ce.getFormColumn().getDataType().equals("timestamp")) {
								Timestamp ts = (Timestamp) (ce.getValue());
								Date date = new Date(ts.getTime());
								try {
									ce.setValue(DateUtil.getDateString(date, dc.getDateStyle()));
								} catch (Exception e) {
									ce.setValue(DateUtil.getDateString(date, "yyyy-MM-dd HH:mm:ss"));
								}
							} else if (ce.getFormColumn().getDataType().equals("date")) {
								ce.setValue(DateUtil.getDateString((Date) ce.getValue(), "yyyy-MM-dd"));
							}
						}
						ce.setDate(dc);

					}
					// 代码级联组件
					if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_CODECASECADE) {
						CodeCaseCade codeCaseCade = codeCaseCadeDao.findCodeCaseCadeById(c.getCompexId());
						ce.setCodeCaseCade(codeCaseCade);
					}
					// 多文件上传组件
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_UPLOADIFY) {
						Uploadify uploadify = uploadifyDao.findUploadifyById(c.getCompexId());
						ce.setUploadify(uploadify);
					}

					ceList.add(ce);
				}

			}
		}

		// 新建时
		if (queryDomains == null || queryDomains.size() == 0) {
			for (int j = 0; formColumns != null && j < formColumns.size(); j++) {
				FormColumn c = (FormColumn) (formColumns.get(j));
				FormColumnExtend ce = new FormColumnExtend();
				ce.setFormColumn(c);
				// 文本框
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX) {
					TextBox tb = textBoxDao.findByID(c.getCompexId());
					ce.setComponent(tb);

					if (c.getDefaultValue() != null && !c.getDefaultValue().equals("")) {
						if (c.getDefaultValue().equals("%username%")) {
							ce.setValue(user.getFullname());
						} else if (c.getDefaultValue().equals("%orgname%")) {
							List<Org> orgs = user.getOrgs();
							if (orgs.size() > 0) {
								ce.setValue(orgs.get(0).getTblName());
							}
						} else {
							ce.setValue(c.getDefaultValue());
						}

					}
				}
				//自动补齐文本框
				if(c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_AUTOCOMPLETE){
					AutoComplete autoComplete = autoCompleteDao.findByID(c.getCompexId());
					String columnValue = autoCompleteDao.findColumnValueList(c.getBelongTable(),c.getColumnName());
					autoComplete.setSource(columnValue);
					ce.setComponent(autoComplete);
				}
				// 密码框
				if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_PASSWORD) {
					Passbox passbox = passboxDao.findByID(c.getCompexId());
					ce.setComponent(passbox);
				}
				// 上传文件框
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FILE) {
					UploadBox uploadBox = uploadBoxDao.findByID(c.getCompexId());
					ce.setComponent(uploadBox);
				}
				// 下拉框 代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					Combox cb = comboxDao.findByID(c.getCompexId());
					ce.setComponent(cb);

					String order = "asc";
					if (c.getCodeShowOrder() != null) {
						if (c.getCodeShowOrder() == 0) {
							order = "asc";
						} else if (c.getCodeShowOrder() == 1) {
							order = "desc";
						}
					}

					List<Dictionary> dl = null;
					if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
					} else {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
					}

					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 下拉框 关系表
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
					Combox cb = comboxDao.findByID(c.getCompexId());
					ce.setComponent(cb);
					List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
					ce.setCodes(codes);
				}
				// 搜索下拉框 代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
					ce.setComponent(scb);

					String order = "asc";
					if (c.getCodeShowOrder() != null) {
						if (c.getCodeShowOrder() == 0) {
							order = "asc";
						} else if (c.getCodeShowOrder() == 1) {
							order = "desc";
						}
					}

					List<Dictionary> dl = null;
					if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
					} else {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
					}

					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 搜索下拉框 关系表
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
					SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
					ce.setComponent(scb);
					List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
					ce.setCodes(codes);
				}
				// 单选框代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_RADIO
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					RadioMgtVO radioMgtVO = radioMgtDAO.selectById(c.getCompexId()).get(0);
					ce.setRadio(radioMgtVO);

					List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 多选框 代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					CheckBoxMgtVO checkBoxMgtVO = checkBoxMgtDAO.selectById(c.getCompexId()).get(0);
					ce.setCheckBox(checkBoxMgtVO);

					List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 文本域
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FIELD) {
					Textarea textarea = textareaDao.findByID(c.getCompexId());
					ce.setComponent(textarea);
				}
				// 文本编辑器
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_EDITOR) {
					TextEditor textEditor = textEditorDao.findTextEditorById(c.getCompexId());
					ce.setTextEditor(textEditor);
				}
				// 日期组件
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_DATE) {
					DateControl dc = dateDao.findById(c.getCompexId());
					ce.setDate(dc);
				}
				// 代码级联组件
				if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_CODECASECADE) {
					CodeCaseCade codeCaseCade = codeCaseCadeDao.findCodeCaseCadeById(c.getCompexId());
					ce.setCodeCaseCade(codeCaseCade);
				}
				// 多文件上传组件
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_UPLOADIFY) {
					Uploadify uploadify = uploadifyDao.findUploadifyById(c.getCompexId());
					ce.setUploadify(uploadify);
				}
				ceList.add(ce);
			}
		}
	}

	/**
	 * 组装表单元素
	 * 
	 * @param ceList
	 * @param queryDomains
	 * @param formColumns
	 * @param model
	 *            子表id
	 */
	private void assembleFormColumn(List<FormColumnExtend> ceList, List<DomainVO> queryDomains, List<FormColumn> formColumns, String model, SysUser user) {
		try {
			for (int m = 0; queryDomains != null && m < queryDomains.size(); m++) {
				Map retMap = new HashMap();
				Set keySet = new HashSet();
				// 查询出domain的列表，数据是Map的List
				String domainSql = "select * from " + queryDomains.get(m).getTable() + " where id=?";
				Object[] args = new Object[] { queryDomains.get(m).getDomainId() };
				List retList = jdbcTemplate.query(domainSql, args, new ColumnMapRowMapper());

				if (retList != null && retList.size() > 0) {
					retMap = (Map) retList.get(0);
					keySet = retMap.keySet();
				}
				// 增加ID字段
				Long id = Long.valueOf(retMap.get("id").toString());
				FormColumn tc = new FormColumn();
				tc.setColumnName("id");
				tc.setBelongTable(model);
				FormColumnExtend tce = new FormColumnExtend();
				tce.setFormColumn(tc);
				tce.setValue(id);
				ceList.add(tce);

				// 遍历表单元素
				for (int j = 0; formColumns != null && j < formColumns.size(); j++) {
					FormColumn c = (FormColumn) (formColumns.get(j));
					if (c.getBelongTable().equals(queryDomains.get(m).getTable())) {
						FormColumnExtend ce = new FormColumnExtend();
						ce.setFormColumn(c);
						if (keySet.contains(c.getColumnName())) {
							ce.setValue(retMap.get(c.getColumnName()));
						}
						// 文本框
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX) {
							TextBox tb = textBoxDao.findByID(c.getCompexId());
							ce.setComponent(tb);
						}
						//自动补齐文本框
						if(c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_AUTOCOMPLETE){
							AutoComplete autoComplete = autoCompleteDao.findByID(c.getCompexId());
							String columnValue = autoCompleteDao.findColumnValueList(c.getBelongTable(),c.getColumnName());
							autoComplete.setSource(columnValue);
							ce.setComponent(autoComplete);
						}
						// 下拉框 代码
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
							Combox cb = comboxDao.findByID(c.getCompexId());
							ce.setComponent(cb);

							String order = "asc";
							if (c.getCodeShowOrder() != null) {
								if (c.getCodeShowOrder() == 0) {
									order = "asc";
								} else if (c.getCodeShowOrder() == 1) {
									order = "desc";
								}
							}

							List<Dictionary> dl = null;
							if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
								dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
							} else {
								dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
							}

							List<Code> codes = new ArrayList<Code>();
							for (Dictionary d : dl) {
								Code code = new Code(d.getId(), d.getName(), d.getValue());
								codes.add(code);
							}
							ce.setCodes(codes);
						}
						// 搜索下拉框 代码
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
							SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
							ce.setComponent(scb);

							String order = "asc";
							if (c.getCodeShowOrder() != null) {
								if (c.getCodeShowOrder() == 0) {
									order = "asc";
								} else if (c.getCodeShowOrder() == 1) {
									order = "desc";
								}
							}

							List<Dictionary> dl = null;
							if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
								dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
							} else {
								dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
							}

							List<Code> codes = new ArrayList<Code>();
							for (Dictionary d : dl) {
								Code code = new Code(d.getId(), d.getName(), d.getValue());
								codes.add(code);
							}
							ce.setCodes(codes);
						}
						// 单选框代码
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_RADIO
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
							/*
							 * TextBox tb = textBoxDao.findByID(c.getRadioId()); ce.setTextBox(tb);
							 */
							List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
							List<Code> codes = new ArrayList<Code>();
							for (Dictionary d : dl) {
								Code code = new Code(d.getId(), d.getName(), d.getValue());
								codes.add(code);
							}
							ce.setCodes(codes);
						}
						// 多选框代码
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
							/*
							 * TextBox tb = textBoxDao.findByID(c.getCheckBoxId()); ce.setTextBox(tb);
							 */
							List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
							List<Code> codes = new ArrayList<Code>();
							for (Dictionary d : dl) {
								Code code = new Code(d.getId(), d.getName(), d.getValue());
								codes.add(code);
							}
							ce.setCodes(codes);
						}
						// 下拉框关系表
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
							Combox cb = comboxDao.findByID(c.getCompexId());
							ce.setComponent(cb);
							List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
							ce.setCodes(codes);
						}
						// 搜索下拉框 关系表
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
							SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
							ce.setComponent(scb);
							List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
							ce.setCodes(codes);
						}
						// 文本域
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FIELD) {
							Textarea textarea = textareaDao.findByID(c.getCompexId());
							ce.setComponent(textarea);
						}
						// 文本编辑器
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_EDITOR) {
							TextEditor textEditor = textEditorDao.findTextEditorById(c.getCompexId());
							ce.setTextEditor(textEditor);
						}
						// 日期组件
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_DATE) {
							DateControl dc = dateDao.findById(c.getCompexId());
							if (ce.getValue() != null) {
								if (ce.getFormColumn().getDataType().equals("timestamp")) {
									Timestamp ts = (Timestamp) (ce.getValue());
									Date date = new Date(ts.getTime());
									try {
										ce.setValue(DateUtil.getDateString(date, dc.getDateStyle()));
									} catch (Exception e) {
										ce.setValue(DateUtil.getDateString(date, "yyyy-MM-dd HH:mm:ss"));
									}
								} else if (ce.getFormColumn().getDataType().equals("date")) {
									ce.setValue(DateUtil.getDateString((Date) ce.getValue(), "yyyy-MM-dd"));
								}
							}
							ce.setDate(dc);

						}
						// 代码级联组件
						if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_CODECASECADE) {
							CodeCaseCade codeCaseCade = codeCaseCadeDao.findCodeCaseCadeById(c.getCompexId());
							ce.setCodeCaseCade(codeCaseCade);
						}
						// 多文件上传组件
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_UPLOADIFY) {
							Uploadify uploadify = uploadifyDao.findUploadifyById(c.getCompexId());
							ce.setUploadify(uploadify);
						}

						ceList.add(ce);
					}

				}

			}

			// 新建时
			if (queryDomains == null || queryDomains.size() == 0) {
				for (int j = 0; formColumns != null && j < formColumns.size(); j++) {
					FormColumn c = (FormColumn) (formColumns.get(j));
					FormColumnExtend ce = new FormColumnExtend();
					ce.setFormColumn(c);
					// 文本框
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX) {
						TextBox tb = textBoxDao.findByID(c.getCompexId());
						ce.setComponent(tb);

						if (c.getDefaultValue() != null && !c.getDefaultValue().equals("")) {
							if (c.getDefaultValue().equals("%username%")) {
								ce.setValue(user.getFullname());
							} else if (c.getDefaultValue().equals("%orgname%")) {
								List<Org> orgs = user.getOrgs();
								if (orgs.size() > 0) {
									ce.setValue(orgs.get(0).getTblName());
								}
							} else {
								ce.setValue(c.getDefaultValue());
							}

						}
					}
					//自动补齐文本框
					if(c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_AUTOCOMPLETE){
						AutoComplete autoComplete = autoCompleteDao.findByID(c.getCompexId());
						String columnValue = autoCompleteDao.findColumnValueList(c.getBelongTable(),c.getColumnName());
						autoComplete.setSource(columnValue);
						ce.setComponent(autoComplete);
					}
					// 下拉框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						Combox cb = comboxDao.findByID(c.getCompexId());
						ce.setComponent(cb);

						String order = "asc";
						if (c.getCodeShowOrder() != null) {
							if (c.getCodeShowOrder() == 0) {
								order = "asc";
							} else if (c.getCodeShowOrder() == 1) {
								order = "desc";
							}
						}

						List<Dictionary> dl = null;
						if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
						} else {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
						}

						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 下拉框 关系表
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
						Combox cb = comboxDao.findByID(c.getCompexId());
						ce.setComponent(cb);
						List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
						ce.setCodes(codes);
					}
					// 搜索下拉框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
						ce.setComponent(scb);

						String order = "asc";
						if (c.getCodeShowOrder() != null) {
							if (c.getCodeShowOrder() == 0) {
								order = "asc";
							} else if (c.getCodeShowOrder() == 1) {
								order = "desc";
							}
						}

						List<Dictionary> dl = null;
						if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
						} else {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
						}

						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 搜索下拉框 关系表
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
						SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
						ce.setComponent(scb);
						List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
						ce.setCodes(codes);
					}
					// 单选框代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_RADIO
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						/*
						 * TextBox tb = textBoxDao.findByID(c.getRadioId()); ce.setTextBox(tb);
						 */
						List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 多选框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						/*
						 * TextBox tb = textBoxDao.findByID(c.getCheckBoxId()); ce.setTextBox(tb);
						 */
						List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 文本域
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FIELD) {
						Textarea textarea = textareaDao.findByID(c.getCompexId());
						ce.setComponent(textarea);
					}
					// 文本编辑器
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_EDITOR) {
						TextEditor textEditor = textEditorDao.findTextEditorById(c.getCompexId());
						ce.setTextEditor(textEditor);
					}
					// 日期组件
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_DATE) {
						DateControl dc = dateDao.findById(c.getCompexId());
						ce.setDate(dc);
					}
					// 代码级联组件
					if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_CODECASECADE) {
						CodeCaseCade codeCaseCade = codeCaseCadeDao.findCodeCaseCadeById(c.getCompexId());
						ce.setCodeCaseCade(codeCaseCade);
					}
					// 多文件上传组件
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_UPLOADIFY) {
						Uploadify uploadify = uploadifyDao.findUploadifyById(c.getCompexId());
						ce.setUploadify(uploadify);
					}
					ceList.add(ce);
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询出指定表的字段列表
	 * 
	 * @param relationTable
	 * @param relationColumn
	 * @return
	 */
	private List<Code> selectByRelation(String relationTable, String relationColumn) {
		// 判断表中有没有是否发布字段
		boolean hasCol = tabulationDao.tableHasColumn(relationTable, "tbl_passed");
		String where = "";
		if (hasCol) {
			where = " where tbl_passed = 1";
		}
		String sql = "select id," + relationColumn + " as tbl_relationColumn from " + relationTable + where + " order by comm_updateDate desc";
		List dl = this.jdbcTemplate.query(sql, new CodeRowMapper());
		return dl;
	}

	class CodeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Code d = new Code();
			d.setValue(rs.getString("id"));
			d.setText(rs.getString("tbl_relationColumn"));
			return d;
		}
	}

	public TextBoxDao getTextBoxDao() {
		return textBoxDao;
	}

	public void setTextBoxDao(TextBoxDao textBoxDao) {
		this.textBoxDao = textBoxDao;
	}

	public ComboxDao getComboxDao() {
		return comboxDao;
	}

	public void setComboxDao(ComboxDao comboxDao) {
		this.comboxDao = comboxDao;
	}

	public SearchComboxDao getSearchComboxDao() {
		return searchComboxDao;
	}

	public void setSearchComboxDao(SearchComboxDao searchComboxDao) {
		this.searchComboxDao = searchComboxDao;
	}

	public TextareaDao getTextareaDao() {
		return textareaDao;
	}

	public void setTextareaDao(TextareaDao textareaDao) {
		this.textareaDao = textareaDao;
	}

	public PassboxDao getPassDao() {
		return passDao;
	}

	public void setPassDao(PassboxDao passDao) {
		this.passDao = passDao;
	}

	public UploadBoxDao getUploadBoxDao() {
		return uploadBoxDao;
	}

	public void setUploadBoxDao(UploadBoxDao uploadBoxDao) {
		this.uploadBoxDao = uploadBoxDao;
	}

	public DateDao getDateDao() {
		return dateDao;
	}

	public void setDateDao(DateDao dateDao) {
		this.dateDao = dateDao;
	}

	public TextEditorDao getTextEditorDao() {
		return textEditorDao;
	}

	public void setTextEditorDao(TextEditorDao textEditorDao) {
		this.textEditorDao = textEditorDao;
	}

	public CodeCaseCadeDao getCodeCaseCadeDao() {
		return codeCaseCadeDao;
	}

	public void setCodeCaseCadeDao(CodeCaseCadeDao codeCaseCadeDao) {
		this.codeCaseCadeDao = codeCaseCadeDao;
	}

	public UploadifyDao getUploadifyDao() {
		return uploadifyDao;
	}

	public void setUploadifyDao(UploadifyDao uploadifyDao) {
		this.uploadifyDao = uploadifyDao;
	}

	public DictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}

	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

	public FormButtonDao getFormButtonDao() {
		return formButtonDao;
	}

	public void setFormButtonDao(FormButtonDao formButtonDao) {
		this.formButtonDao = formButtonDao;
	}

	public ButtonGroupDao getButtonGroupDao() {
		return buttonGroupDao;
	}

	public void setButtonGroupDao(ButtonGroupDao buttonGroupDao) {
		this.buttonGroupDao = buttonGroupDao;
	}

	public ButtonAndGroupDao getButtonAndGroupDao() {
		return buttonAndGroupDao;
	}

	public void setButtonAndGroupDao(ButtonAndGroupDao buttonAndGroupDao) {
		this.buttonAndGroupDao = buttonAndGroupDao;
	}

	public TemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public PartitionDao getPartitionDao() {
		return partitionDao;
	}

	public void setPartitionDao(PartitionDao partitionDao) {
		this.partitionDao = partitionDao;
	}

	public SeqcodeDao getSeqcodeDao() {
		return seqcodeDao;
	}

	public void setSeqcodeDao(SeqcodeDao seqcodeDao) {
		this.seqcodeDao = seqcodeDao;
	}

	public TabulationDao getTabulationDao() {
		return tabulationDao;
	}

	public void setTabulationDao(TabulationDao tabulationDao) {
		this.tabulationDao = tabulationDao;
	}

	@Override
	@Cacheable(value = "formCache", key = "'selectFormBySeqcode:'+#seqcode")
	public Form selectFormBySeqcode(String seqcode) {
		String sql = "select b.*,t.tbl_tableZhName as tbl_tableChName from sys_biaodan b left join sys_tables t on b.tbl_zhubiao = t.tbl_tableName where tbl_bianma=?";
		final Form u = new Form();
		final Object[] params = new Object[] { seqcode };
		query(sql, params, u);

		// 表单按钮
		List<FormButton> formButtons = formButtonDao.selectByFormId(u.getId());
		u.setFormButtons(formButtons);

		return u;
	}

	@Override
	public Form getByIdAndDomainVO(Long id, List<DomainVO> domains, SysUser user) {
		final Form u = new Form();
		try {
			String sql = "select b.*,t.tbl_tableZhName as tbl_tableChName from sys_biaodan b left join sys_tables t on b.tbl_zhubiao = t.tbl_tableName where b.id=?";

			final Object[] params = new Object[] { id };
			compexQuery(sql, params, u);

			// 表单按钮
			List<FormButton> formButtons = formButtonDao.selectByFormId(id, -1L);// tabid为-1则表示属于整个表单
			/*Map<String, Seqcode> curUserSeqcode = user.getCurUserSeqcode();*/
			/*Map<String, SysResource> curUserResource = user.getCurUserResource();
			for (FormButton formButton : formButtons) {
				if (null == curUserResource.get(formButton.getCode())) {
					formButton.setHasAuth("1");
				} else {
					formButton.setHasAuth("0");
				}
			}*/
			u.setFormButtons(formButtons);

			// 选项卡
			List<Tab> tabs = tabDao.selectListByFormId(id);
			/*for (Tab _tab : tabs) {
				if (null == curUserSeqcode.get(_tab.getCode())) {
					_tab.setHasAuth("1");
				} else {
					_tab.setHasAuth("0");
				}
			}*/
			
			List<Tab> newTabs = new ArrayList<Tab>();
			
			for (int i = 0; tabs != null && i < tabs.size(); i++) {
				Tab orgTab = tabs.get(i);
				Tab tab = orgTab.clone();
				Long templateId = tab.getTemplateId();
				Template template = templateDao.selectById(templateId);
				if (template != null) {
					template.setContent(template.getContent());
				}
				tab.setTemplate(template);
				if ("0".equals(template.getType())) {// 基础模版
					// 根据选项卡ID查询出该选项卡的表单元素
					List<FormColumn> formColumns = selectByTabId(tab.getId());

					/*for (FormColumn _formColumn : formColumns) {
						if (_formColumn.getCode() != null) {
							String[] code_arr = _formColumn.getCode().split(",");
							if (code_arr.length > 1) {
								boolean hasView = false;
								boolean hasEdit = false;
								if (null != curUserSeqcode.get(code_arr[0])) {
									hasView = true;
								}
								if (null != curUserSeqcode.get(code_arr[1])) {
									hasEdit = true;
								}
								if (hasView && hasEdit) {
									_formColumn.setHasAuth("3");
								} else if (hasView) {
									_formColumn.setHasAuth("1");
								} else if (hasEdit) {
									_formColumn.setHasAuth("2");
								}
							}
						}
					}*/
					String tableName = "";
					if (formColumns != null && formColumns.size() > 0) {
						tableName = formColumns.get(0).getBelongTable();
					}
					List<FormColumnExtend> ceList = new ArrayList<FormColumnExtend>();
					if (u.getTableName().equalsIgnoreCase(tableName)) {
						assembleFormColumn(ceList, tableName, domains, formColumns, user);
					} else {
						baseAssembleFormColumn(ceList, tableName, domains, formColumns, user);
					}

					tab.setFormColumnExtends(ceList);

					List<FormButton> buttons = formButtonDao.selectByTabId(tab.getId());
					tab.setFormButtons(buttons);
				} else if ("1".equals(template.getType())) {// 组合模版
					String tsql = "select p.*,t.tbl_templatechname as tbl_baseTemplateName,t.tbl_templatefilename as tbl_templateFileName from sys_partition p left join sys_template t "
							+ "on p.tbl_basetemplate = t.id where 1=1 and p.tbl_templateid = ?";

					Object[] param = new Object[] { templateId };
					List<Partition> partitions = partitionDao.select(tsql, param, 1, -1);
					for (int p = 0; p < partitions.size(); p++) {
						Partition partition = partitions.get(p);
						if (partition.getPartitionType() == 0) {// 0:操作区 1：列表区
							// 查找模板
							Template tpt = templateDao.selectById(partition.getBaseTemplate());
							partition.setTemplate(tpt);

							List<FormColumn> pfcolumns = selectByTabAndPid(tab.getId(), partition.getId());
							/*for (FormColumn _formColumn : pfcolumns) {
								if (_formColumn.getCode() != null) {
									String[] code_arr = _formColumn.getCode().split(",");
									if (code_arr.length > 1) {
										boolean hasView = false;
										boolean hasEdit = false;
										if (null != curUserSeqcode.get(code_arr[0])) {
											hasView = true;
										}
										if (null != curUserSeqcode.get(code_arr[1])) {
											hasEdit = true;
										}

										if (hasView && hasEdit) {
											_formColumn.setHasAuth("3");
										} else if (hasView) {
											_formColumn.setHasAuth("1");
										} else if (hasEdit) {
											_formColumn.setHasAuth("2");
										}
									}
								}
							}*/
							String tableName = "";
							if (pfcolumns != null && pfcolumns.size() > 0) {
								tableName = pfcolumns.get(0).getBelongTable();
							}
							List<FormColumnExtend> ceList = new ArrayList<FormColumnExtend>();
							assembleFormColumn(ceList, null, pfcolumns, user);

							// 根据选项卡buttonId查询出该选项卡的按钮组
							// 分区按钮
							List<FormButton> buttons = formButtonDao.selectByTabAndPid(tab.getId(), partition.getId());
							/*for (FormButton button : buttons) {
								if (null == curUserResource.get(button.getCode())) {
									button.setHasAuth("1");
								} else {
									button.setHasAuth("0");
								}
							}*/
							partition.setFormButtons(buttons);
							partition.setFormColumnExtends(ceList);
						} else if (partition.getPartitionType() == 1) {
							List<FormColumn> pfcolumns = selectByTabAndPid(tab.getId(), partition.getId());
							List<FormColumnExtend> ceList = new ArrayList<FormColumnExtend>();
							assembleFormColumn(ceList, null, pfcolumns, user);

							// 根据选项卡buttonId查询出该选项卡的按钮组
							// 分区按钮
							List<FormButton> buttons = formButtonDao.selectByTabAndPid(tab.getId(), partition.getId());
							/*for (FormButton button : buttons) {
								if (null == curUserResource.get(button.getCode())) {
									button.setHasAuth("1");
								} else {
									button.setHasAuth("0");
								}
							}*/
							partition.setFormButtons(buttons);
							partition.setFormColumnExtends(ceList);
						}
					}
					tab.setPartitions(partitions);
				}
				newTabs.add(tab);
			}
			u.setTabs(newTabs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return u;
	}

	private void assembleFormColumn(List<FormColumnExtend> ceList, String tableName, List<DomainVO> domains, List<FormColumn> formColumns, SysUser user)
			throws Exception {
		DomainVO domainVO = null;
		for (int i = 0; i < domains.size(); i++) {
			if (tableName.equals(domains.get(i).getTable())) {
				domainVO = domains.get(i);
				break;
			}
		}
		if (domainVO != null) {
			Map retMap = new HashMap();
			Set keySet = new HashSet();
			// 查询出domain的列表，数据是Map的List
			String domainSql = "select t.*,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from "
					+ domainVO.getTable()
					+ " t left join sys_user u on t.comm_createBy = u.id   left join sys_user u2 on t.comm_updateBy = u2.id where t.id=?";
			Object[] args = new Object[] { domainVO.getDomainId() };
			List retList = jdbcTemplate.query(domainSql, args, new ColumnMapRowMapper());

			if (retList != null && retList.size() > 0) {
				retMap = (Map) retList.get(0);
				keySet = retMap.keySet();
			}

			// 增加ID字段
			Long id = Long.valueOf(retMap.get("id").toString());
			FormColumn ci = new FormColumn();
			ci.setColumnName("id");
			ci.setBelongTable(domainVO.getTable());
			FormColumnExtend cei = new FormColumnExtend();
			cei.setFormColumn(ci);
			cei.setValue(id);
			ceList.add(cei);

			// 遍历表单元素
			for (int j = 0; formColumns != null && j < formColumns.size(); j++) {
				FormColumn c = (FormColumn) (formColumns.get(j));
				if (c.getBelongTable().equals(domainVO.getTable())) {
					FormColumnExtend ce = new FormColumnExtend();
					ce.setFormColumn(c);
					if (keySet.contains(c.getColumnName())) {
						ce.setValue(retMap.get(c.getColumnName()));
						if (c.getColumnName().equalsIgnoreCase("comm_createBy")) {
							ce.setValue(retMap.get("tbl_createname"));
						}
						if (c.getColumnName().equalsIgnoreCase("comm_updateBy")) {
							ce.setValue(retMap.get("tbl_updatename"));
						}
					}
					// 文本框
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX) {
						TextBox tb = textBoxDao.findByID(c.getCompexId());
						ce.setComponent(tb);
					}
					//自动补齐文本框
					if(c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_AUTOCOMPLETE){
						AutoComplete autoComplete = autoCompleteDao.findByID(c.getCompexId());
						String columnValue = autoCompleteDao.findColumnValueList(c.getBelongTable(),c.getColumnName());
						autoComplete.setSource(columnValue);
						ce.setComponent(autoComplete);
					}
					// 密码框
					if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_PASSWORD) {
						Passbox passbox = passboxDao.findByID(c.getCompexId());
						ce.setComponent(passbox);
					}
					// 上传文件框
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FILE) {
						UploadBox uploadBox = uploadBoxDao.findByID(c.getCompexId());
						ce.setComponent(uploadBox);
					}
					// 下拉框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						Combox cb = comboxDao.findByID(c.getCompexId());
						ce.setComponent(cb);

						String order = "asc";
						if (c.getCodeShowOrder() != null) {
							if (c.getCodeShowOrder() == 0) {
								order = "asc";
							} else if (c.getCodeShowOrder() == 1) {
								order = "desc";
							}
						}

						List<Dictionary> dl = null;
						if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
						} else {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
						}

						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 搜索下拉框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
						ce.setComponent(scb);

						String order = "asc";
						if (c.getCodeShowOrder() != null) {
							if (c.getCodeShowOrder() == 0) {
								order = "asc";
							} else if (c.getCodeShowOrder() == 1) {
								order = "desc";
							}
						}

						List<Dictionary> dl = null;
						if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
						} else {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
						}

						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 单选框代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_RADIO
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						RadioMgtVO radioMgtVO = radioMgtDAO.selectById(c.getCompexId()).get(0);
						ce.setRadio(radioMgtVO);

						List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 多选框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						CheckBoxMgtVO checkBoxMgtVO = checkBoxMgtDAO.selectById(c.getCompexId()).get(0);
						ce.setCheckBox(checkBoxMgtVO);

						List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 下拉框 关系表
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
						Combox cb = comboxDao.findByID(c.getCompexId());
						ce.setComponent(cb);
						List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
						ce.setCodes(codes);
					}
					// 搜索下拉框 关系表
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
						SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
						ce.setComponent(scb);
						List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
						ce.setCodes(codes);
					}
					// 文本域
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FIELD) {
						Textarea textarea = textareaDao.findByID(c.getCompexId());
						ce.setComponent(textarea);
					}
					// 文本编辑器
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_EDITOR) {
						TextEditor textEditor = textEditorDao.findTextEditorById(c.getCompexId());
						ce.setTextEditor(textEditor);
					}
					// 日期组件
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_DATE) {
						DateControl dc = dateDao.findById(c.getCompexId());
						if (ce.getValue() != null) {
							if (ce.getFormColumn().getDataType().equals("timestamp")) {
								Timestamp ts = (Timestamp) (ce.getValue());
								Date date = new Date(ts.getTime());
								try {
									ce.setValue(DateUtil.getDateString(date, dc.getDateStyle()));
								} catch (Exception e) {
									ce.setValue(DateUtil.getDateString(date, "yyyy-MM-dd HH:mm:ss"));
								}
							} else if (ce.getFormColumn().getDataType().equals("date")) {
								ce.setValue(DateUtil.getDateString((Date) ce.getValue(), "yyyy-MM-dd"));
							}
						}
						ce.setDate(dc);

					}
					// 代码级联组件
					if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_CODECASECADE) {
						CodeCaseCade codeCaseCade = codeCaseCadeDao.findCodeCaseCadeById(c.getCompexId());
						ce.setCodeCaseCade(codeCaseCade);
					}
					// 多文件上传组件
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_UPLOADIFY) {
						Uploadify uploadify = uploadifyDao.findUploadifyById(c.getCompexId());
						ce.setUploadify(uploadify);
					}

					ceList.add(ce);
				}

			}
		} else {
			for (int j = 0; formColumns != null && j < formColumns.size(); j++) {
				FormColumn c = (FormColumn) (formColumns.get(j));
				FormColumnExtend ce = new FormColumnExtend();
				ce.setFormColumn(c);
				// 文本框
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX) {
					TextBox tb = textBoxDao.findByID(c.getCompexId());
					ce.setComponent(tb);
					if (c.getDefaultValue() != null && !c.getDefaultValue().equals("")) {
						if (c.getDefaultValue().equals("%username%")) {
							ce.setValue(user.getId());
						} else if (c.getDefaultValue().equals("%orgname%")) {
							List<Org> orgs = user.getOrgs();
							if (orgs.size() > 0) {
								ce.setValue(orgs.get(0).getId());
							}
						} else {
							ce.setValue(c.getDefaultValue());
						}

					}
				}
				//自动补齐文本框
				if(c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_AUTOCOMPLETE){
					AutoComplete autoComplete = autoCompleteDao.findByID(c.getCompexId());
					String columnValue = autoCompleteDao.findColumnValueList(c.getBelongTable(),c.getColumnName());
					autoComplete.setSource(columnValue);
					ce.setComponent(autoComplete);
				}
				// 密码框
				if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_PASSWORD) {
					Passbox passbox = passboxDao.findByID(c.getCompexId());
					ce.setComponent(passbox);
				}
				// 上传文件框
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FILE) {
					UploadBox uploadBox = uploadBoxDao.findByID(c.getCompexId());
					ce.setComponent(uploadBox);
				}
				// 下拉框 代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					Combox cb = comboxDao.findByID(c.getCompexId());
					ce.setComponent(cb);

					String order = "asc";
					if (c.getCodeShowOrder() != null) {
						if (c.getCodeShowOrder() == 0) {
							order = "asc";
						} else if (c.getCodeShowOrder() == 1) {
							order = "desc";
						}
					}

					List<Dictionary> dl = null;
					if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
					} else {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
					}

					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 搜索下拉框 代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
					ce.setComponent(scb);

					String order = "asc";
					if (c.getCodeShowOrder() != null) {
						if (c.getCodeShowOrder() == 0) {
							order = "asc";
						} else if (c.getCodeShowOrder() == 1) {
							order = "desc";
						}
					}

					List<Dictionary> dl = null;
					if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
					} else {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
					}

					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 单选框代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_RADIO
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					RadioMgtVO radioMgtVO = radioMgtDAO.selectById(c.getCompexId()).get(0);
					ce.setRadio(radioMgtVO);

					List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 多选框 代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					CheckBoxMgtVO checkBoxMgtVO = checkBoxMgtDAO.selectById(c.getCompexId()).get(0);
					ce.setCheckBox(checkBoxMgtVO);

					List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 下拉框 关系表
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
					Combox cb = comboxDao.findByID(c.getCompexId());
					ce.setComponent(cb);
					List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
					ce.setCodes(codes);
				}
				// 搜索下拉框 关系表
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
					SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
					ce.setComponent(scb);
					List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
					ce.setCodes(codes);
				}
				// 文本域
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FIELD) {
					Textarea textarea = textareaDao.findByID(c.getCompexId());
					ce.setComponent(textarea);
				}
				// 文本编辑器
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_EDITOR) {
					TextEditor textEditor = textEditorDao.findTextEditorById(c.getCompexId());
					ce.setTextEditor(textEditor);
				}
				// 日期组件
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_DATE) {
					DateControl dc = dateDao.findById(c.getCompexId());
					ce.setDate(dc);
				}
				// 代码级联
				if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_CODECASECADE) {
					CodeCaseCade codeCaseCade = codeCaseCadeDao.findCodeCaseCadeById(c.getCompexId());
					ce.setCodeCaseCade(codeCaseCade);
				}
				// 多文件上传组件
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_UPLOADIFY) {
					Uploadify uploadify = uploadifyDao.findUploadifyById(c.getCompexId());
					ce.setUploadify(uploadify);
				}
				ceList.add(ce);
			}
		}
	}

	private void baseAssembleFormColumn(List<FormColumnExtend> ceList, String tableName, List<DomainVO> domains, List<FormColumn> formColumns,
			SysUser user) throws Exception {
		DomainVO domainVO = null;
		for (int i = 0; i < domains.size(); i++) {
			if (tableName.equals(domains.get(i).getTable())) {
				domainVO = domains.get(i);
				break;
			}
		}
		if (domainVO != null) {
			Map retMap = new HashMap();
			Set keySet = new HashSet();
			// 查询出domain的列表，数据是Map的List
			String domainSql = "select t.* from " + domainVO.getTable() + " t" + " where t.id=?";
			Object[] args = new Object[] { domainVO.getDomainId() };
			List retList = jdbcTemplate.query(domainSql, args, new ColumnMapRowMapper());

			if (retList != null && retList.size() > 0) {
				retMap = (Map) retList.get(0);
				keySet = retMap.keySet();
			}

			// 增加ID字段
			Long id = Long.valueOf(retMap.get("id").toString());
			FormColumn ci = new FormColumn();
			ci.setColumnName("id");
			ci.setBelongTable(domainVO.getTable());
			FormColumnExtend cei = new FormColumnExtend();
			cei.setFormColumn(ci);
			cei.setValue(id);
			ceList.add(cei);

			// 遍历表单元素
			for (int j = 0; formColumns != null && j < formColumns.size(); j++) {
				FormColumn c = (FormColumn) (formColumns.get(j));
				if (c.getBelongTable().equals(domainVO.getTable())) {
					FormColumnExtend ce = new FormColumnExtend();
					ce.setFormColumn(c);
					if (keySet.contains(c.getColumnName())) {
						ce.setValue(retMap.get(c.getColumnName()));
						if (c.getColumnName().equalsIgnoreCase("comm_createBy")) {
							ce.setValue(retMap.get("tbl_createname"));
						}
						if (c.getColumnName().equalsIgnoreCase("comm_updateBy")) {
							ce.setValue(retMap.get("tbl_updatename"));
						}
					}
					// 文本框
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX) {
						TextBox tb = textBoxDao.findByID(c.getCompexId());
						ce.setComponent(tb);
					}
					//自动补齐文本框
					if(c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_AUTOCOMPLETE){
						AutoComplete autoComplete = autoCompleteDao.findByID(c.getCompexId());
						String columnValue = autoCompleteDao.findColumnValueList(c.getBelongTable(),c.getColumnName());
						autoComplete.setSource(columnValue);
						ce.setComponent(autoComplete);
					}
					// 密码框
					if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_PASSWORD) {
						Passbox passbox = passboxDao.findByID(c.getCompexId());
						ce.setComponent(passbox);
					}
					// 上传文件框
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FILE) {
						UploadBox uploadBox = uploadBoxDao.findByID(c.getCompexId());
						ce.setComponent(uploadBox);
					}
					// 下拉框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						Combox cb = comboxDao.findByID(c.getCompexId());
						ce.setComponent(cb);

						String order = "asc";
						if (c.getCodeShowOrder() != null) {
							if (c.getCodeShowOrder() == 0) {
								order = "asc";
							} else if (c.getCodeShowOrder() == 1) {
								order = "desc";
							}
						}

						List<Dictionary> dl = null;
						if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
						} else {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
						}

						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 搜索下拉框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
						ce.setComponent(scb);

						String order = "asc";
						if (c.getCodeShowOrder() != null) {
							if (c.getCodeShowOrder() == 0) {
								order = "asc";
							} else if (c.getCodeShowOrder() == 1) {
								order = "desc";
							}
						}

						List<Dictionary> dl = null;
						if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
						} else {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
						}

						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 单选框代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_RADIO
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						RadioMgtVO radioMgtVO = radioMgtDAO.selectById(c.getCompexId()).get(0);
						ce.setRadio(radioMgtVO);

						List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 多选框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						CheckBoxMgtVO checkBoxMgtVO = checkBoxMgtDAO.selectById(c.getCompexId()).get(0);
						ce.setCheckBox(checkBoxMgtVO);

						List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 下拉框 关系表
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
						Combox cb = comboxDao.findByID(c.getCompexId());
						ce.setComponent(cb);
						List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
						ce.setCodes(codes);
					}
					// 搜索下拉框 关系表
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
						SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
						ce.setComponent(scb);
						List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
						ce.setCodes(codes);
					}
					// 文本域
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FIELD) {
						Textarea textarea = textareaDao.findByID(c.getCompexId());
						ce.setComponent(textarea);
					}
					// 文本编辑器
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_EDITOR) {
						TextEditor textEditor = textEditorDao.findTextEditorById(c.getCompexId());
						ce.setTextEditor(textEditor);
					}
					// 日期组件
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_DATE) {
						DateControl dc = dateDao.findById(c.getCompexId());
						if (ce.getValue() != null) {
							if (ce.getFormColumn().getDataType().equals("timestamp")) {
								Timestamp ts = (Timestamp) (ce.getValue());
								Date date = new Date(ts.getTime());
								try {
									ce.setValue(DateUtil.getDateString(date, dc.getDateStyle()));
								} catch (Exception e) {
									ce.setValue(DateUtil.getDateString(date, "yyyy-MM-dd HH:mm:ss"));
								}
							} else if (ce.getFormColumn().getDataType().equals("date")) {
								ce.setValue(DateUtil.getDateString((Date) ce.getValue(), "yyyy-MM-dd"));
							}
						}
						ce.setDate(dc);

					}
					// 代码级联
					if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_CODECASECADE) {
						CodeCaseCade codeCaseCade = codeCaseCadeDao.findCodeCaseCadeById(c.getCompexId());
						ce.setCodeCaseCade(codeCaseCade);
					}
					// 多文件上传组件
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_UPLOADIFY) {
						Uploadify uploadify = uploadifyDao.findUploadifyById(c.getCompexId());
						ce.setUploadify(uploadify);
					}

					ceList.add(ce);
				}

			}
		} else {
			for (int j = 0; formColumns != null && j < formColumns.size(); j++) {
				FormColumn c = (FormColumn) (formColumns.get(j));
				FormColumnExtend ce = new FormColumnExtend();
				ce.setFormColumn(c);
				// 文本框
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX) {
					TextBox tb = textBoxDao.findByID(c.getCompexId());
					ce.setComponent(tb);
					if (c.getDefaultValue() != null && !c.getDefaultValue().equals("")) {
						if (c.getDefaultValue().equals("%username%")) {
							ce.setValue(user.getFullname());
						} else if (c.getDefaultValue().equals("%orgname%")) {
							List<Org> orgs = user.getOrgs();
							if (orgs.size() > 0) {
								ce.setValue(orgs.get(0).getTblName());
							}
						} else {
							ce.setValue(c.getDefaultValue());
						}

					}
				}
				//自动补齐文本框
				if(c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_AUTOCOMPLETE){
					AutoComplete autoComplete = autoCompleteDao.findByID(c.getCompexId());
					String columnValue = autoCompleteDao.findColumnValueList(c.getBelongTable(),c.getColumnName());
					autoComplete.setSource(columnValue);
					ce.setComponent(autoComplete);
				}
				// 密码框
				if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_PASSWORD) {
					Passbox passbox = passboxDao.findByID(c.getCompexId());
					ce.setComponent(passbox);
				}
				// 上传文件框
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FILE) {
					UploadBox uploadBox = uploadBoxDao.findByID(c.getCompexId());
					ce.setComponent(uploadBox);
				}
				// 下拉框 代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					Combox cb = comboxDao.findByID(c.getCompexId());
					ce.setComponent(cb);

					String order = "asc";
					if (c.getCodeShowOrder() != null) {
						if (c.getCodeShowOrder() == 0) {
							order = "asc";
						} else if (c.getCodeShowOrder() == 1) {
							order = "desc";
						}
					}

					List<Dictionary> dl = null;
					if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
					} else {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
					}

					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 搜索下拉框 代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
					ce.setComponent(scb);

					String order = "asc";
					if (c.getCodeShowOrder() != null) {
						if (c.getCodeShowOrder() == 0) {
							order = "asc";
						} else if (c.getCodeShowOrder() == 1) {
							order = "desc";
						}
					}

					List<Dictionary> dl = null;
					if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
					} else {
						dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
					}

					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 单选框代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_RADIO
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					RadioMgtVO radioMgtVO = radioMgtDAO.selectById(c.getCompexId()).get(0);
					ce.setRadio(radioMgtVO);

					List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 多选框 代码
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
					CheckBoxMgtVO checkBoxMgtVO = checkBoxMgtDAO.selectById(c.getCompexId()).get(0);
					ce.setCheckBox(checkBoxMgtVO);

					List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
					List<Code> codes = new ArrayList<Code>();
					for (Dictionary d : dl) {
						Code code = new Code(d.getId(), d.getName(), d.getValue());
						codes.add(code);
					}
					ce.setCodes(codes);
				}
				// 下拉框 关系表
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
					Combox cb = comboxDao.findByID(c.getCompexId());
					ce.setComponent(cb);
					List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
					ce.setCodes(codes);
				}
				// 搜索下拉框 关系表
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
						&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
					SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
					ce.setComponent(scb);
					List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
					ce.setCodes(codes);
				}
				// 文本域
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_FIELD) {
					Textarea textarea = textareaDao.findByID(c.getCompexId());
					ce.setComponent(textarea);
				}
				// 文本编辑器
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_EDITOR) {
					TextEditor textEditor = textEditorDao.findTextEditorById(c.getCompexId());
					ce.setTextEditor(textEditor);
				}
				// 日期组件
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_DATE) {
					DateControl dc = dateDao.findById(c.getCompexId());
					ce.setDate(dc);
				}
				// 代码级联
				if (c.getCompexId() != null && c.getInputType() == Constants.Input_TYPE_TEXTBOX_CODECASECADE) {
					CodeCaseCade codeCaseCade = codeCaseCadeDao.findCodeCaseCadeById(c.getCompexId());
					ce.setCodeCaseCade(codeCaseCade);
				}
				// 多文件上传组件
				if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_UPLOADIFY) {
					Uploadify uploadify = uploadifyDao.findUploadifyById(c.getCompexId());
					ce.setUploadify(uploadify);
				}
				ceList.add(ce);
			}
		}
	}

	@Override
	@Cacheable(value = "formCache", key = "'selectEditByTabId:'+#tabId+#partitionId")
	public List<FormColumn> selectByTabAndPid(Long tabId, Long partitionId) {
		String sql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,c.tbl_columnName as tbl_ziduanming,"
				+ "c.tbl_length as tbl_length,c.tbl_comment as tbl_colcomment,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b on a.tbl_tableid=b.id left join sys_"
				+ "columns c on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_tabid=? and a.tbl_partitionid=? order by a.tbl_biaodanxianshishunxu asc";

		Object[] params = new Object[] { tabId, partitionId };
		return jdbcTemplate.query(sql, params, new FormColumnRowMapper());
	}

	public List<FormColumn> selectByPid(Long partitionId) {
		String sql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,c.tbl_columnName as tbl_ziduanming,"
				+ "c.tbl_length as tbl_length,c.tbl_comment as tbl_colcomment,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b on a.tbl_tableid=b.id left join sys_"
				+ "columns c on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_partitionid=? order by a.tbl_biaodanxianshishunxu asc";
		Object[] params = new Object[] { partitionId };
		return jdbcTemplate.query(sql, params, new FormColumnRowMapper());
	}

	@Override
	@Cacheable(value = "formCache", key = "'findDomainById:'+#model+#tabId+#partitionId+#subDomainId+#user.id")
	public Partition findDomainById(String model, Long tabId, Long partitionId, Long subDomainId, SysUser user) {
		List<FormColumn> pfcolumns = selectByTabAndPid(tabId, partitionId);
//		Map<String, Seqcode> curUserSeqcode = user.getCurUserSeqcode();
		for (FormColumn _formColumn : pfcolumns) {
			if (_formColumn.getCode() != null) {
				String[] code_arr = _formColumn.getCode().split(",");
				if (code_arr.length > 1) {
					boolean hasView = false;
					boolean hasEdit = false;
//					if (null != curUserSeqcode.get(code_arr[0])) {
//						hasView = true;
//					}
//					if (null != curUserSeqcode.get(code_arr[1])) {
//						hasEdit = true;
//					}
					if (hasView && hasEdit) {
						_formColumn.setHasAuth("3");
					} else if (hasView) {
						_formColumn.setHasAuth("1");
					} else if (hasEdit) {
						_formColumn.setHasAuth("2");
					}
				}
			}
		}

		List<FormColumnExtend> ceList = new ArrayList<FormColumnExtend>();
		List<DomainVO> queryDomains = new ArrayList<DomainVO>();
		DomainVO vo = new DomainVO(model, subDomainId);
		queryDomains.add(vo);
		assembleFormColumn(ceList, queryDomains, pfcolumns, model, user);
		// 根据partitionId查询出他所在的tab页
		Partition partition = partitionDao.selectById(partitionId);
		List<FormButton> buttons = formButtonDao.selectByTabAndPid(tabId, partition.getId());
		partition.setFormButtons(buttons);
		partition.setFormColumnExtends(ceList);

		// 查找模板
		Template tpt = templateDao.selectById(partition.getBaseTemplate());
		partition.setTemplate(tpt);
		return partition;
	}

	@Override
	@Cacheable(value = "formCache", key = "'getEditByIdAndDomainVO:'+#id+#domainVOs+#user.id")
	public Form getEditByIdAndDomainVO(Long id, List<DomainVO> domains, SysUser user) {
		final Form u = new Form();
		try {

			String sql = "select b.*,t.tbl_tableZhName as tbl_tableChName from sys_biaodan b left join sys_tables t on b.tbl_zhubiao = t.tbl_tableName where b.id=?";

			final Object[] params = new Object[] { id };
			compexQuery(sql, params, u);

			// 表单按钮
			List<FormButton> formButtons = formButtonDao.selectByFormId(id, -1L);// tabid为-1则表示属于整个表单
			u.setFormButtons(formButtons);

			// 选项卡
			List<Tab> tabs = tabDao.selectListByFormId(id);
			for (int i = 0; tabs != null && i < tabs.size(); i++) {
				Tab tab = tabs.get(i);
				Long templateId = tab.getTemplateId();
				Template template = templateDao.selectById(templateId);
				if (template != null) {
					template.setContent(template.getContent());
				}
				tab.setTemplate(template);
				if ("0".equals(template.getType())) {// 基础模版
					// 根据选项卡ID查询出该选项卡的表单元素
					List<FormColumn> formColumns = selectEditByTabId(String.valueOf(tab.getId()));
					String tableName = "";
					if (formColumns != null && formColumns.size() > 0) {
						tableName = formColumns.get(0).getBelongTable();
					}
					List<FormColumnExtend> ceList = new ArrayList<FormColumnExtend>();
					if (u.getTableName().equalsIgnoreCase(tableName)) {
						assembleFormColumn(ceList, tableName, domains, formColumns, user);
					} else {
						baseAssembleFormColumn(ceList, tableName, domains, formColumns, user);
					}

					tab.setFormColumnExtends(ceList);

					List<FormButton> buttons = formButtonDao.selectByTabId(tab.getId());
					tab.setFormButtons(buttons);
				} else if ("1".equals(template.getType())) {// 组合模版
					String tsql = "select p.*,t.tbl_templatechname as tbl_baseTemplateName,t.tbl_templatefilename as tbl_templateFileName from sys_partition p left join sys_template t "
							+ "on p.tbl_basetemplate = t.id where 1=1 and p.tbl_templateid = ?";
					Object[] param = new Object[] { templateId };
					List<Partition> partitions = partitionDao.select(tsql, param, 1, -1);
					for (int p = 0; p < partitions.size(); p++) {
						Partition partition = partitions.get(p);
						if (partition.getPartitionType() == 0) {// 0:操作区 1：列表区
							// 查找模板
							Template tpt = templateDao.selectById(partition.getBaseTemplate());
							partition.setTemplate(tpt);

							List<FormColumn> pfcolumns = selectByTabAndPid(tab.getId(), partition.getId());
							String tableName = "";
							if (pfcolumns != null && pfcolumns.size() > 0) {
								tableName = pfcolumns.get(0).getBelongTable();
							}
							List<FormColumnExtend> ceList = new ArrayList<FormColumnExtend>();
							assembleFormColumn(ceList, null, pfcolumns, user);

							// 根据选项卡buttonId查询出该选项卡的按钮组
							// 分区按钮
							List<FormButton> buttons = formButtonDao.selectByTabAndPid(tab.getId(), partition.getId());

							partition.setFormButtons(buttons);
							partition.setFormColumnExtends(ceList);
						}
					}
					tab.setPartitions(partitions);
				}

			}
			u.setTabs(tabs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return u;
	}

	@Override
	@Cacheable(value = "formCache", key = "'getCompById:'+#compexId+#inputType")
	public List getCompById(Long compexId, int inputType) {
		List list = null;
		String xsql = "select tbl_duiyingshujubiao from sys_xitongyuansu where tbl_luruleixing = " + inputType;
		// 从系统元素表取出table
		List xlist = jdbcTemplate.queryForList(xsql);
		if (xlist.size() > 0) {
			Object object = ((Map) xlist.get(0)).get("tbl_duiyingshujubiao");
			String table = "0";
			if (object != null) {
				table = object.toString();
				String tsql = "select tbl_tablename from sys_tables where id = '" + table + "'";
				List tlist = jdbcTemplate.queryForList(tsql);
				String tableName = ((Map) tlist.get(0)).get("tbl_tablename").toString();
				String compSql = "select tbl_compname,tbl_bianma from " + tableName + " where id = '" + compexId + "'";
				list = jdbcTemplate.queryForList(compSql);
			}
		}
		return list;
	}

	@Override
	public List findIsUse(Long compexId, String inputType) {
		String sql = "select * from sys_biaodansheji where tbl_luruleixing = ? and tbl_compexid = ? ";
		Object[] objs = new Object[] { inputType, compexId };
		List list = jdbcTemplate.queryForList(sql, objs);
		return list;
	}

	@Override
	public String findIsUseButtonGroup(Long buttonGroupId) {
		String res = "true";
		String fsql = "select * from sys_biaodanbutton where tbl_buttonid = ? and tbl_buttontype = 1";
		String tsql = "select * from sys_liebiaobutton where tbl_buttonid = ? and tbl_buttontype = 1";
		Object[] objs = new Object[] { buttonGroupId };
		List flist = jdbcTemplate.queryForList(fsql, objs);
		List tlist = jdbcTemplate.queryForList(tsql, objs);
		if (flist.size() > 0 || tlist.size() > 0) {
			res = "false";
		}
		return res;
	}

	@Override
	public String findIsUseButton(Long buttonId) {
		String res = "true";
		String fsql = "select * from sys_biaodanbutton where tbl_buttonid = ? and tbl_buttontype = 0";
		String tsql = "select * from sys_liebiaobutton where tbl_buttonid = ? and tbl_buttontype = 0";
		String bsql = "select * from sys_buttonandgroup where tbl_buttonID = ?";
		Object[] objs = new Object[] { buttonId };
		List flist = jdbcTemplate.queryForList(fsql, objs);
		List tlist = jdbcTemplate.queryForList(tsql, objs);
		List blist = jdbcTemplate.queryForList(bsql, objs);
		if (flist.size() > 0 || tlist.size() > 0 || blist.size() > 0) {
			res = "false";
		}
		return res;
	}

	@Override
	public String findIsUseQuery(Long queryId) {
		String res = "true";
		String lsql = "select * from sys_liebiao where tbl_querycontrolid = ?";
		Object[] objs = new Object[] { queryId };
		List llist = jdbcTemplate.queryForList(lsql, objs);
		if (llist.size() > 0) {
			res = "false";
		}
		return res;
	}

	@Override
	public String findIsUseList(Long listId) {
		String res = "true";
		String lsql = "select * from sys_liebiao where tbl_listcontrolid = ?";
		Object[] objs = new Object[] { listId };
		List llist = jdbcTemplate.queryForList(lsql, objs);
		if (llist.size() > 0) {
			res = "false";
		}
		return res;
	}

	@Override
	public String findIsUseListControl(Long controlId, String columnname) {
		String res = "true";
		try {
			String lsql = "select * from sys_liebiaozujian where " + columnname + " = ?";
			Object[] objs = new Object[] { controlId };
			List llist = jdbcTemplate.queryForList(lsql, objs);
			if (llist.size() > 0) {
				res = "false";
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	@Cacheable(value = "formCache", key = "'getDefaultCompex:'+#inputType")
	public Long getDefaultCompex(int inputType) {
		Long compid = -1L;
		List list = null;
		String xsql = "select tbl_duiyingshujubiao from sys_xitongyuansu where tbl_luruleixing = " + inputType;
		// 从系统元素表取出table
		List xlist = jdbcTemplate.queryForList(xsql);
		if (xlist.size() > 0) {
			Object object = ((Map) xlist.get(0)).get("tbl_duiyingshujubiao");
			Integer table = 0;
			if (object != null) {
				table = Integer.valueOf(object.toString());
				String tsql = "select tbl_tablename from sys_tables where id = " + table;
				List tlist = jdbcTemplate.queryForList(tsql);
				String tableName = ((Map) tlist.get(0)).get("tbl_tablename").toString();

				String compSql = "select id from " + tableName + " where tbl_isdefault = 1";
				list = jdbcTemplate.queryForList(compSql);
				if (list.size() > 0) {
					compid = (Long) ((Map<String, Object>) list.get(0)).get("id");
				}
			}
		}
		return compid;
	}

	@Override
	public int countColumnInForm(Long columnId) {
		String sql = "select count(*) as amount from sys_biaodansheji where tbl_ziduan=?";
		Object[] params = new Object[] { columnId };
		return (Integer) jdbcTemplate.queryForObject(sql, params, new CountRowMapper());
	}

	@Override
	@Cacheable(value = "formCache", key = "'findTabuByFormId:'+#formId")
	public List findTabuByFormId(Long formId) {
		String _sql = "select * from sys_liebiao where tbl_formid = ?";
		Object[] _params = new Object[] { formId };
		return jdbcTemplate.queryForList(_sql, _params);
	}

	@Override
	@Cacheable(value = "formCache", key = "'findSimpleFormById:'+#id")
	public Form findSimpleFormById(Long id) {
		Form form = new Form();
		String sql = "select b.*,t.tbl_tableZhName as tbl_tableChName,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_biaodan b left join sys_tables t on b.tbl_zhubiao = t.tbl_tableName left join sys_user u on b.comm_createBy = u.id   left join sys_user u2 on b.comm_updateBy = u2.id   where b.id=?";
		Object[] params = new Object[] { id };
		this.query(sql, params, form);
		return form;
	}

	@Override
	public List findReplyColumn(FormColumn formColumn) {
		String sql = "select * from sys_biaodansheji where tbl_ziduan = ? and tbl_form = ? and tbl_tabid = ? and tbl_partitionid = ?";
		Object[] objs = new Object[] { formColumn.getColumnId(), formColumn.getFormId(), formColumn.getTabId(), formColumn.getPartitionId() };
		if (formColumn.getId() != null && !formColumn.getId().equals("")) {
			sql = "select * from sys_biaodansheji where tbl_ziduan = ? and tbl_form = ? and tbl_tabid = ? and tbl_partitionid = ? and id <> ?";
			objs = new Object[] { formColumn.getColumnId(), formColumn.getFormId(), formColumn.getTabId(), formColumn.getPartitionId(),
					formColumn.getId() };
		}

		List list = null;
		try {
			list = jdbcTemplate.queryForList(sql, objs);
		} catch (Exception e) {
			list = new ArrayList();
		}

		return list;
	}

	@Override
	@Cacheable(value = "formCache", key = "'queryFormByTableid:'+#tableid")
	public List<Form> queryFormByTableid(Long tableid) {
		List<Form> lstForm = jdbcTemplate.query("select id from sys_biaodan where tbl_zhubiaoid=?", new Object[] { tableid },
				new FormByTidRowMapper());
		return lstForm;
	}

	class FormByTidRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Form u = new Form();
			u.setId(rs.getLong("id"));
			return u;
		}
	}

	@Override
	public String findIsUseAdvQuery(Long queryId) {
		String res = "true";
		String lsql = "select * from sys_liebiao where tbl_advancequerycontrolid = ?";
		Object[] objs = new Object[] { queryId };
		List llist = jdbcTemplate.queryForList(lsql, objs);
		if (llist.size() > 0) {
			res = "false";
		}
		return res;
	}

	@Override
	public List findFormColumnByFormId(Long id) {
		String sql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,"
				+ "c.tbl_columnName as tbl_ziduanming,c.tbl_length as tbl_length,c.tbl_comment as tbl_colcomment,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b "
				+ "on a.tbl_tableid=b.id left join sys_columns c on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_form=? order by a.tbl_biaodanxianshishunxu asc";
		Object[] params = new Object[] { id };
		return jdbcTemplate.query(sql, params, new FormColumnRowMapper());
	}

	@Override
	public void logicDelete(Long id) {
		// 删除表单
		String sql = "update sys_biaodan set tbl_dstatus = 1 where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);

		// 删除表单按钮
		String selbtnsql = "select * from sys_biaodanbutton where tbl_formid=?";
		List<Map<String, Object>> btnList = jdbcTemplate.queryForList(selbtnsql, params);
		for (int i = 0; i < btnList.size(); i++) {
			// 删除编码
			String code = ((Map<String, Object>) btnList.get(i)).get("tbl_bianma").toString();
			seqcodeDao.logicDeleteSeq(code);
		}

		String buttonSql = "update sys_biaodanbutton set tbl_dstatus = 1 where tbl_formid=?";
		jdbcTemplate.update(buttonSql, params);

		// 删除选项卡
		String tabSql = "update sys_biaodantab set tbl_dstatus = 1 where tbl_form=?";
		jdbcTemplate.update(tabSql, params);

		// 删除表单元素
		String selformcolsql = "select * from sys_biaodansheji where tbl_form=?";
		List<Map<String, Object>> formcolList = jdbcTemplate.queryForList(selformcolsql, params);
		for (int i = 0; i < formcolList.size(); i++) {
			// 删除编码
			try {
				String code = ((Map<String, Object>) formcolList.get(i)).get("tbl_bianma").toString();
				seqcodeDao.logicDeleteSeq(code);
			} catch (Exception e) {
			}

		}
		String columnSql = "update sys_biaodansheji set tbl_dstatus = 1 where tbl_form=?";
		jdbcTemplate.update(columnSql, params);
	}

}
