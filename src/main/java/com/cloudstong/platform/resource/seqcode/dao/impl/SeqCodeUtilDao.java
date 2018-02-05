package com.cloudstong.platform.resource.seqcode.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.DateUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.form.model.Tab;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.system.model.Privilege;

/**
 * @author sam
 * Created on 2012-11-27
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:编码初始化类,包括目录,表单,列表,按钮,字段等类型
 */
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class SeqCodeUtilDao{
	
	private JdbcTemplateExtend jdbcTemplate;
	/*
	 * 为目录建立基本码
	 */
	public String systemCode = "SYSM-00001";
	private String cataBase = "FUNC-";
	private String menuBase = "MENU-";
	private String formBase = "FORM-";
	private String listBase = "TABN-";
	private String columnBase = "COLN-";
	private String formButtonBase = "FORM_BUTN-";
	private String itemBase = "ITEM-";
	private String itemButtonBase = "ITEM_BUTN-";
	private String tabnButtonBase = "TABN_BUTN-";
	
	public SeqCodeUtilDao(JdbcTemplateExtend jte){
		jdbcTemplate = jte;
	}
	
	/**
	 * Description:所有操作前,执行清理编码表
	 * 
	 * Steps:
	 * 
	 */
	public void clean_seqcode(){
		String sql = "delete from sys_bianma";
		jdbcTemplate.update(sql);
	}
	
	public void catalog() throws Exception{
		//获取所有目录表数据
		List<Catalog> _lstCatalog = jdbcTemplate.query("select * from sys_catalog", new CatalogRowMapper());
		String _cataBase = "";
		//循环目录集合,对每一个目录进行更新基本码;更新后在编码表中插入编码
		for (Catalog catalog : _lstCatalog) {
			Thread.sleep(1l);
			_cataBase = cataBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
			try {
				jdbcTemplate.update("update sys_catalog set tbl_bianma = ? where id=?", new Object[] {_cataBase, catalog.getId()});
				jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_systematom,tbl_usestatus,tbl_tablename) values(?,?,?,?,?,?,?,?)", new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()), catalog.getName(), _cataBase, Constants.SYSTEM_ATOM_FUNC, "0","sys_catalog"});
			} catch (Exception e) {}
		}
		//编码后重新查询目录表,此时目录的编码字段已经为正确数据
		_lstCatalog = jdbcTemplate.query("select * from sys_catalog", new CatalogRowMapper());
		//循环目录集合,找到每一个目录的上级目录的基本码,更新编码表中当前目录所属基本码的上级码
		for (Catalog catalog : _lstCatalog) {
			for (Catalog innerCatalog : _lstCatalog) {
				if(catalog.getParentId().equals(innerCatalog.getId())){
					try {
						jdbcTemplate.update("update sys_bianma set tbl_parentId = ?,comm_updateDate = ? where tbl_value = ?", new Object[]{innerCatalog.getCode(), new Date(System.currentTimeMillis()),catalog.getCode()});
					} catch (Exception e) {}
					break;
				}
			}
		}
		//更新上级编码后重新查询目录表,循环更新扩展码
		/*_lstCatalog = jdbcTemplate.query("select * from sys_catalog", new CatalogRowMapper());
		for (Catalog catalog : _lstCatalog) {
			for (Catalog innerCatalog : _lstCatalog) {
				if(catalog.getParentId().equals(innerCatalog.getId())){
					try {
						updateSeqExpand(catalog.getCode(), innerCatalog.getCode());
					} catch (Exception e) {}
					break;
				}
			}
		}*/
	}
	
	public void list() throws Exception{
		//获取所有目录表数据
		List<Catalog> _lstCatalog = jdbcTemplate.query("select * from sys_catalog", new CatalogRowMapper());
		//获取所有列表表数据
		List<Tabulation> _lstTabulation = jdbcTemplate.query("select * from sys_liebiao", new TabulationRowMapper());
		String _listBase = "";
		//循环列表集合,对每一个列表进行更新基本码;更新后在编码表中插入编码
		for (Tabulation tabulation : _lstTabulation) {
			Thread.sleep(1l);
			_listBase = listBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
			try {
				jdbcTemplate.update("update sys_liebiao set tbl_bianma = ? where id=?", new Object[] {_listBase, tabulation.getId()});
				jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_systematom,tbl_usestatus,tbl_tablename) values(?,?,?,?,?,?,?,?)", new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), tabulation.getTabulationName(), _listBase, Constants.SYSTEM_ATOM_TABN, "0","sys_liebiao"});
			} catch (Exception e) {}
		}
		//编码后重新查询列表表,此时列表的编码字段已经为正确数据
		_lstTabulation = jdbcTemplate.query("select * from sys_liebiao", new TabulationRowMapper());
		for (Tabulation tabulation : _lstTabulation) {
			for (Catalog catalog : _lstCatalog) {
				if(tabulation.getId().equals(catalog.getTabulationId())){
					try {
						jdbcTemplate.update("update sys_bianma set tbl_parentId = ?,comm_updateDate = ? where tbl_value=?", new Object[]{catalog.getCode(),new Date(System.currentTimeMillis()),tabulation.getCode()});
					} catch (Exception e) {}
					break;
				}
			}
		}
		/*_lstTabulation = jdbcTemplate.query("select * from sys_liebiao", new TabulationRowMapper());
		for (Tabulation tabulation : _lstTabulation) {
			for (Catalog catalog : _lstCatalog) {
				if(tabulation.getId().equals(catalog.getTabulationId())){
					try {
						updateSeqExpand(tabulation.getCode(), catalog.getCode());
					} catch (Exception e) {}
					break;
				}
			}
		}*/
	}
	
	public void form() throws Exception{
		//获取所有目录表数据
		List<Catalog> _lstCatalog = jdbcTemplate.query("select * from sys_catalog", new CatalogRowMapper());
		//获取所有列表表数据
		List<Tabulation> _lstTabulation = jdbcTemplate.query("select * from sys_liebiao", new TabulationRowMapper());
		//获取所有表单表数据
		List<Form> _lstFrom = jdbcTemplate.query("select * from sys_biaodan", new FormRowMapper());
		String _formBase = "";
		//循环列表集合,对每一个列表进行更新基本码;更新后在编码表中插入编码
		for (Form form : _lstFrom) {
			Thread.sleep(1l);
			_formBase = formBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
			try {
				jdbcTemplate.update("update sys_biaodan set tbl_bianma = ? where id = ?",new Object[] {_formBase, form.getId()});
				jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_systematom,tbl_usestatus,tbl_tablename) values(?,?,?,?,?,?,?,?)", new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), form.getFormName(), _formBase, Constants.SYSTEM_ATOM_FORM, "0", "sys_biaodan"});
			} catch (Exception e) {}
		}
		//编码后重新查询表单表,此时表单的编码字段已经为正确数据
		_lstFrom = jdbcTemplate.query("select * from sys_biaodan", new FormRowMapper());
		for (Form form : _lstFrom) {
			for (Tabulation tabulation : _lstTabulation) {
				try {
					if(form.getId().equals(tabulation.getFormId())){
						for (Catalog catalog : _lstCatalog) {
							if(tabulation.getId().equals(catalog.getTabulationId())){
								jdbcTemplate.update("update sys_bianma set tbl_parentId = ?,comm_updateDate = ? where tbl_value=?", new Object[]{catalog.getCode(),new Date(System.currentTimeMillis()),form.getCode()});
								break;
							}
						}
						break;
					}
				} catch (Exception e) {}
			}
		}
		
		/*_lstFrom = jdbcTemplate.query("select * from sys_biaodan", new FormRowMapper());
		for (Form form : _lstFrom) {
			for (Tabulation tabulation : _lstTabulation) {
				try {
					if(form.getId().equals(tabulation.getFormId())){
						for (Catalog catalog : _lstCatalog) {
							if(tabulation.getId().equals(catalog.getTabulationId())){
								updateSeqExpand(form.getCode(), catalog.getCode());
								break;
							}
						}
						break;
					}
				} catch (Exception e) {}
			}
		}*/
	}
	
	public void listButton() throws Exception{
		//获取所有列表表数据
		List<Tabulation> _lstTabulation = jdbcTemplate.query("select * from sys_liebiao", new TabulationRowMapper());
		//获取所有列表按钮表数据
		List<TabulationButton> _lstTabulationButton = jdbcTemplate.query("select * from sys_liebiaobutton", new TabulationButtonRowMapper());
		
		String _listBaseButton = "";
		//循环列表按钮集合,对每一个列表按钮进行更新基本码;更新后在编码表中插入编码
		for (TabulationButton tabulationButton : _lstTabulationButton) {
			Thread.sleep(1l);
			_listBaseButton = tabnButtonBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
			try {
				jdbcTemplate.update("update sys_liebiaobutton set tbl_bianma = ? where id = ?", new Object[] {_listBaseButton, tabulationButton.getId()});
				jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_systematom,tbl_usestatus,tbl_tablename) values(?,?,?,?,?,?,?,?)", new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), tabulationButton.getShowName(), _listBaseButton, Constants.SYSTEM_ATOM_TABN_BUTN, "0", "sys_liebiaobutton"});
			} catch (Exception e) {}
			
		}
		//编码后重新查询列表按钮表,此时列表按钮的编码字段已经为正确数据
		_lstTabulationButton = jdbcTemplate.query("select * from sys_liebiaobutton", new TabulationButtonRowMapper());
		for (TabulationButton tabulationButton : _lstTabulationButton) {
			for (Tabulation tabulation : _lstTabulation) {
				if(tabulationButton.getListId() != null && tabulation.getId() != null && tabulationButton.getListId().equals(tabulation.getId())){
					try {
						jdbcTemplate.update("update sys_bianma set tbl_parentId = ?,comm_updateDate = ? where tbl_value=?", new Object[]{tabulation.getCode(),new Date(System.currentTimeMillis()),tabulationButton.getCode()});
					} catch (Exception e) {}
					break;
				}
			}
		}
		/*_lstTabulationButton = jdbcTemplate.query("select * from sys_liebiaobutton", new TabulationButtonRowMapper());
		for (TabulationButton tabulationButton : _lstTabulationButton) {
			for (Tabulation tabulation : _lstTabulation) {
				if(tabulationButton.getListId() != null && tabulation.getId() != null && tabulationButton.getListId().equals(tabulation.getId())){
					try {
						updateSeqExpand(tabulationButton.getCode(), tabulation.getCode());
					} catch (Exception e) {}
					break;
				}
			}
		}*/
	}
	
	public void column() throws Exception{
		//获取所有表单表数据
		List<Form> _lstForm = jdbcTemplate.query("select * from sys_biaodan", new FormRowMapper());
		//获取所有列表表数据
		List<FormColumn> _lstFormColumn = jdbcTemplate.query("select * from sys_biaodansheji", new FormDesignRowMapper());
		
		String _columnBaseButton_1 = "";
		String _columnBaseButton_2 = "";
		
		for (FormColumn formDesign : _lstFormColumn) {
			if(null != formDesign.getPartitionId() && !formDesign.getPartitionId().equals("-1")){
				String sPartitionType = checkPartitionType(formDesign);
				if(sPartitionType.equals("1")){
					continue;
				}
			}
			
			Thread.sleep(1l);
			_columnBaseButton_1 = columnBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
			Thread.sleep(1l);
			_columnBaseButton_2 = columnBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
			try {
				jdbcTemplate.update("update sys_biaodansheji set tbl_bianma = ? where id = ?",  new Object[] {_columnBaseButton_1+","+_columnBaseButton_2, formDesign.getId()});
				Column column=(Column)jdbcTemplate.queryForObject("select * from sys_columns where id=?", new Object[] {formDesign.getColumnId()}, new ColumnRowMapper());
				jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_systematom,tbl_usestatus,tbl_type,tbl_object,tbl_tablename) values(?,?,?,?,?,?,?,?,?,?)",
						new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), column.getColumnZhName(), _columnBaseButton_1, Constants.SYSTEM_ATOM_COLN, "0", "1", formDesign.getTabId()+"_"+column.getId(), "sys_biaodansheji"});
				jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_systematom,tbl_usestatus,tbl_type,tbl_object,tbl_tablename) values(?,?,?,?,?,?,?,?,?,?)", 
						new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), column.getColumnZhName(), _columnBaseButton_2, Constants.SYSTEM_ATOM_COLN, "0", "2", formDesign.getTabId()+"_"+column.getId(), "sys_biaodansheji"});
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		_lstFormColumn = jdbcTemplate.query("select * from sys_biaodansheji", new FormDesignRowMapper());
		for (FormColumn formColumn : _lstFormColumn) {
			for (Form form : _lstForm) {
				try {
					if(formColumn.getFormId().equals(form.getId())){
						jdbcTemplate.update("update sys_bianma set tbl_parentId = ?,comm_updateDate = ? where tbl_value=?", new Object[]{form.getCode(),new Date(System.currentTimeMillis()),formColumn.getCode().split(",")[0]});
						jdbcTemplate.update("update sys_bianma set tbl_parentId = ?,comm_updateDate = ? where tbl_value=?", new Object[]{form.getCode(),new Date(System.currentTimeMillis()),formColumn.getCode().split(",")[1]});
						break;
					}
				} catch (Exception e) {}
			}
		}
		/*_lstFormColumn = jdbcTemplate.query("select * from sys_biaodansheji", new FormDesignRowMapper());
		for (FormColumn formColumn : _lstFormColumn) {
			for (Form form : _lstForm) {
				try {
					if(formColumn.getFormId().equals(form.getId())){
						updateSeqExpand(formColumn.getCode(), form.getCode());
						break;
					}
				} catch (Exception e) {}
			}
		}*/
	}

	public String checkPartitionType(FormColumn formDesign) {
		String sPartitionType = (String)jdbcTemplate.queryForObject("select tbl_partitiontype from sys_partition where id=?", new Object[] {formDesign.getPartitionId()}, String.class);
		return sPartitionType;
	}
	
	public void item() throws Exception{
		//获取所有表单表数据
		List<Form> _lstForm = jdbcTemplate.query("select * from sys_biaodan", new FormRowMapper());
		//获取所有选项卡表数据
		List<Tab> _lstFormTab = jdbcTemplate.query("select * from sys_biaodantab", new TabRowMapper());
		
		String _item = "";
		for (Tab tab : _lstFormTab) {
			Thread.sleep(1l);
			_item = itemBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
			jdbcTemplate.update("update sys_biaodantab set tbl_bianma = ? where id = ?",  new Object[] {_item, tab.getId()});
			jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_systematom,tbl_usestatus,tbl_type,tbl_object,tbl_tablename) values(?,?,?,?,?,?,?,?,?,?)", 
					new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), tab.getTabName(), _item, Constants.SYSTEM_ATOM_ITEM, "0", "2", "0", "sys_biaodantab"});
		}
		_lstFormTab = jdbcTemplate.query("select * from sys_biaodantab", new TabRowMapper());
		for (Form form : _lstForm) {
			for (Tab tab : _lstFormTab) {
				try {
					if(form.getId().equals(tab.getFormId())){
						jdbcTemplate.update("update sys_bianma set tbl_parentId = ?,comm_updateDate = ?,tbl_object=? where tbl_value=?", new Object[]{form.getCode(),new Date(System.currentTimeMillis()),tab.getId(),tab.getCode()});
					}
				} catch (Exception e) {}
			}
		}
	}
	
	public void formAndItemButton() throws Exception{
		//获取所有表单表数据
		List<Form> _lstForm = jdbcTemplate.query("select * from sys_biaodan", new FormRowMapper());
		//获取所有选项卡表数据
		List<Tab> _lstFormTab = jdbcTemplate.query("select * from sys_biaodantab", new TabRowMapper());
		//获取所有表单按钮表数据
		List<FormButton> _lstFormButton = jdbcTemplate.query("select * from sys_biaodanbutton", new FormButtonRowMapper());
		
		String _formBaseButton = "";
		//循环表单按钮集合,对每一个表单按钮进行更新基本码;更新后在编码表中插入编码
		for (FormButton formButton : _lstFormButton) {
			Thread.sleep(1l);
			if(null != formButton.getTabId() && !formButton.getTabId().equals("-1")){
				_formBaseButton = itemButtonBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
			}else{
				_formBaseButton = formButtonBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
			}
			try {
				jdbcTemplate.update("update sys_biaodanbutton set tbl_bianma = ? where id = ?",  new Object[] {_formBaseButton, formButton.getId()});
				if(null != formButton.getTabId() && !formButton.getTabId().equals("-1")){
					jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_systematom,tbl_usestatus,tbl_object,tbl_tablename) values(?,?,?,?,?,?,?,?,?)", 
							new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), formButton.getShowName(), _formBaseButton, Constants.SYSTEM_ATOM_ITEM_BUTN, "0", formButton.getId(), "sys_biaodanbutton"});
				}else{
					jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_systematom,tbl_usestatus,tbl_object,tbl_tablename) values(?,?,?,?,?,?,?,?,?)", 
							new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), formButton.getShowName(), _formBaseButton, Constants.SYSTEM_ATOM_FORM_BUTN, "0", formButton.getId(), "sys_biaodanbutton"});
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//编码后重新查询表单按钮表,此时表单按钮的编码字段已经为正确数据
		_lstFormButton = jdbcTemplate.query("select * from sys_biaodanbutton", new FormButtonRowMapper());
		for (FormButton formButton : _lstFormButton) {
			if(null != formButton.getTabId() && !formButton.getTabId().equals("-1")){
				for (Tab tab : _lstFormTab) {
					try {
						if(null != tab.getId() && null != formButton.getFormId() && formButton.getTabId().equals(tab.getId())){
							jdbcTemplate.update("update sys_bianma set tbl_parentId = ?,comm_updateDate = ? where tbl_value=?", new Object[]{tab.getCode(),new Date(System.currentTimeMillis()),formButton.getCode()});
						}
					} catch (Exception e) {}
				}
			}else{
				for (Form form : _lstForm) {
					try {
						if(null != form.getId() && null != formButton.getFormId() && formButton.getFormId().equals(form.getId())){
							jdbcTemplate.update("update sys_bianma set tbl_parentId = ?,comm_updateDate = ? where tbl_value=?", new Object[]{form.getCode(),new Date(System.currentTimeMillis()),formButton.getCode()});
						}
					} catch (Exception e) {}
				}
			}
			
		}
		/*_lstFormButton = jdbcTemplate.query("select * from sys_biaodanbutton", new FormButtonRowMapper());
		for (FormButton formButton : _lstFormButton) {
			for (Form form : _lstForm) {
				try {
					if(formButton.getFormId().equals(form.getId())){
						updateSeqExpand(formButton.getCode(), form.getCode());
						break;
					}
				} catch (Exception e) {}
			}
		}*/
	}
	
	public void oprtButton()throws Exception{
		//获取所有列表表数据
		List<Tabulation> _lstTabulation = jdbcTemplate.query("select * from sys_liebiao", new TabulationRowMapper());
		//获取所有列表操作区按钮表数据
		List<TabulationOpt> _lstTabulationOptButton = jdbcTemplate.query("select * from sys_oprtbutton", new TabulationOptRowMapper());
		
		String _listBaseButton = "";
		//循环列表操作区按钮集合,对每一个列表按钮进行更新基本码;更新后在编码表中插入编码
		for (TabulationOpt tabulationOpt : _lstTabulationOptButton) {
			Thread.sleep(1l);
			_listBaseButton = tabnButtonBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
			try {
				jdbcTemplate.update("update sys_oprtbutton set tbl_bianma = ? where id = ?", new Object[] {_listBaseButton, tabulationOpt.getId()});
				jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_systematom,tbl_usestatus,tbl_tablename) values(?,?,?,?,?,?,?,?)",
						new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), tabulationOpt.getShowName(), _listBaseButton, Constants.SYSTEM_ATOM_OPRT_BUTN, "0", "sys_oprtbutton"});
			} catch (Exception e) {}
			
		}
		//编码后重新查询列表操作区按钮表,此时列表按钮的编码字段已经为正确数据
		_lstTabulationOptButton = jdbcTemplate.query("select * from sys_oprtbutton", new TabulationOptRowMapper());
		for (TabulationOpt tabulationOpt : _lstTabulationOptButton) {
			for (Tabulation tabulation : _lstTabulation) {
				if(tabulationOpt.getTabulationId() != null && tabulation.getId() != null && tabulationOpt.getTabulationId().equals(tabulation.getId())){
					try {
						jdbcTemplate.update("update sys_bianma set tbl_parentId = ?,comm_updateDate = ? where tbl_value=?", new Object[]{tabulation.getCode(),new Date(System.currentTimeMillis()),tabulationOpt.getCode()});
					} catch (Exception e) {}
					break;
				}
			}
		}
		/*_lstTabulationOptButton = jdbcTemplate.query("select * from sys_oprtbutton", new TabulationOptRowMapper());
		for (TabulationOpt tabulationOpt : _lstTabulationOptButton) {
			for (Tabulation tabulation : _lstTabulation) {
				if(tabulationOpt.getTabulationId() != null && tabulation.getId() != null && tabulationOpt.getTabulationId().equals(tabulation.getId())){
					try {
						updateSeqExpand(tabulationOpt.getCode(), tabulation.getCode());
					} catch (Exception e) {}
					break;
				}
			}
		}*/
	}
	
	public void updateSeqExpand(String psSeqValue, String psParentId){
		String _sParentId = psParentId;
		String _sSeqExpandCode = psParentId;
		for (int i=0; i<10; i++) {
			if(_sParentId == null || _sParentId.equals(systemCode)){
				break;
			}else{
				try {
					List<Seqcode> _lstSeqcode = jdbcTemplate.query(
							"select * from sys_bianma where tbl_value=?", new Object[] { _sParentId }, new BeanPropertyRowMapper(Seqcode.class));
					_sParentId = _lstSeqcode.get(0).getTblParentid();
					_sSeqExpandCode = _lstSeqcode.get(0).getTblValue().split("-"+systemCode)[0]+"_"+_sSeqExpandCode;
				} catch (Exception e) {
					break;
				}
			}
		}
		String sql = "update sys_bianma set tbl_expand=? where tbl_value=?";
		Object[] _objParams = new Object[] { psSeqValue.split("-"+systemCode)[0]+"_"+_sSeqExpandCode, psSeqValue};
		jdbcTemplate.update(sql, _objParams);
	}
	
	public void updatePrivilageSeqcode(){
		List<Privilege> _lstPrivilege = jdbcTemplate.query("select * from sys_privilege", new BeanPropertyRowMapper(Privilege.class));
		List<Seqcode> _lstSeqcode = jdbcTemplate.query("select * from sys_bianma", new BeanPropertyRowMapper(Seqcode.class));
		Map<String, String> _mapSeqcode = new HashMap<String, String>();
		for (Seqcode seqcode : _lstSeqcode) {
			_mapSeqcode.put(seqcode.getTblValue(), String.valueOf(seqcode.getId()));
		}
		for (Privilege privilege : _lstPrivilege) {
			try {
				if(privilege.getTblTablename().equalsIgnoreCase("sys_biaodansheji")){
					if(privilege.getTblReferenceid().split("_")[1].equals("1")){
						String seqcode = jdbcTemplate.queryForObject("select tbl_bianma from "+privilege.getTblTablename()+" where id=?", new Object[]{privilege.getTblReferenceid().split("_")[0]}, String.class);
						jdbcTemplate.update("update sys_privilege set tbl_seq = ? where tbl_referenceId=? and tbl_tablename=?", new Object[]{_mapSeqcode.get(seqcode.split(",")[0]), privilege.getTblReferenceid(), privilege.getTblTablename()});
					}else{
						String seqcode = jdbcTemplate.queryForObject("select tbl_bianma from "+privilege.getTblTablename()+" where id=?", new Object[]{privilege.getTblReferenceid().split("_")[0]}, String.class);
						jdbcTemplate.update("update sys_privilege set tbl_seq = ? where tbl_referenceId=? and tbl_tablename=?", new Object[]{_mapSeqcode.get(seqcode.split(",")[1]), privilege.getTblReferenceid(), privilege.getTblTablename()});
					}
				}else{
					String seqcode = jdbcTemplate.queryForObject("select tbl_bianma from "+privilege.getTblTablename()+" where id=?", new Object[]{privilege.getTblReferenceid()}, String.class);
					jdbcTemplate.update("update sys_privilege set tbl_seq = ? where tbl_referenceId=? and tbl_tablename=?", new Object[]{_mapSeqcode.get(seqcode), privilege.getTblReferenceid(), privilege.getTblTablename()});
				}
			} catch (Exception e) {}
		}
	}
	//知识复用--->开始
	public void catalog(String catalogId) throws Exception{
		Catalog _catalog = (Catalog)jdbcTemplate.queryForObject("select * from sys_catalog where id=?", new Object[] {catalogId}, new CatalogRowMapper());
		Catalog _catalogParent = (Catalog)jdbcTemplate.queryForObject("select * from sys_catalog where id=?", new Object[] {_catalog.getParentId()}, new CatalogRowMapper());
		Thread.sleep(1l);
		String _cataBase = cataBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
		String _catalogParentCode = "";
		if(_catalogParent != null){
			_catalogParentCode = _catalogParent.getCode();
		}
		try {
			jdbcTemplate.update("update sys_catalog set tbl_bianma = ? where id=?", new Object[] {_cataBase, _catalog.getId()});
			jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_parentId,tbl_systematom,tbl_usestatus) values(?,?,?,?,?,?,?,?)", new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()), _catalog.getName(), _cataBase, _catalogParentCode, "17", "0"});
		} catch (Exception e) {}
	
		//updateSeqExpand(_cataBase, _catalogParent.getCode());
		list(_catalog);
	}
	
	public void list(Catalog pCatalog) throws Exception{
		try {
			if(pCatalog != null){
				Tabulation _tabulation = (Tabulation)jdbcTemplate.queryForObject("select * from sys_liebiao where id=?", new Object[] {pCatalog.getTabulationId()}, new TabulationRowMapper());
				Thread.sleep(1l);
				String _listBase = listBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
				try {
					jdbcTemplate.update("update sys_liebiao set tbl_bianma = ? where id=?", new Object[] {_listBase, pCatalog.getTabulationId()});
					jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_parentId,tbl_systematom,tbl_usestatus) values(?,?,?,?,?,?,?,?)", 
							new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), _tabulation.getTabulationName(), _listBase, pCatalog.getCode(), Constants.SYSTEM_ATOM_TABN, "0"});
				} catch (Exception e) {}
				//updateSeqExpand(_listBase, pCatalog.getCode());
				form(_tabulation, pCatalog);
				listButton(_tabulation);
				oprtButton(_tabulation);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void form(Tabulation pTabulation, Catalog pCatalog) throws Exception{
		try {
			if(pTabulation != null){
				Form _form = (Form)jdbcTemplate.queryForObject("select * from sys_biaodan where id=?", new Object[] {pTabulation.getFormId()}, new FormRowMapper());
				Thread.sleep(1l);
				String _formBase = formBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
				try {
					jdbcTemplate.update("update sys_biaodan set tbl_bianma = ? where id = ?",new Object[] {_formBase, _form.getId()});
					jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_parentId,tbl_systematom,tbl_usestatus) values(?,?,?,?,?,?,?,?)",
							new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), _form.getFormName(), _formBase,pCatalog.getCode(), Constants.SYSTEM_ATOM_FORM, "0"});
				} catch (Exception e) {}
				//updateSeqExpand(_formBase, pCatalog.getCode());
				item(_form);
				formButton(_form);
				column(_form);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void listButton(Tabulation pTabulation) throws Exception{
		try {
			//获取所有列表按钮表数据
			List<TabulationButton> _lstTabulationButton = jdbcTemplate.query("select * from sys_liebiaobutton where tbl_tabulation=?", new Object[] {pTabulation.getId()}, new TabulationButtonRowMapper());
			
			String _listBaseButton = "";
			String _buttonName = "";
			//循环列表按钮集合,对每一个列表按钮进行更新基本码;更新后在编码表中插入编码
			for (TabulationButton tabulationButton : _lstTabulationButton) {
				Thread.sleep(1l);
				_listBaseButton = tabnButtonBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
				try {
					jdbcTemplate.update("update sys_liebiaobutton set tbl_bianma = ? where id = ?", new Object[] {_listBaseButton, tabulationButton.getId()});
					_buttonName=jdbcTemplate.queryForObject("select tbl_buttonName from sys_button where id=?", new Object[] {tabulationButton.getButtonId()}, String.class);
					jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_parentId,tbl_systematom,tbl_usestatus) values(?,?,?,?,?,?,?,?)",
							new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), _buttonName, _listBaseButton, pTabulation.getCode(), Constants.SYSTEM_ATOM_TABN_BUTN, "0"});
				} catch (Exception e) {}
				
			}
			
			/*_lstTabulationButton = jdbcTemplate.query("select * from sys_liebiaobutton where tbl_tabulation=?", new Object[] {pTabulation.getId()}, new TabulationButtonRowMapper());
			for (TabulationButton tabulationButton : _lstTabulationButton) {
				updateSeqExpand(tabulationButton.getCode(), pTabulation.getCode());
			}*/
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void column(Form pForm) throws Exception{
		try {
			if(pForm != null){
				List<FormColumn> _lstFormColumn = jdbcTemplate.query("select * from sys_biaodansheji where tbl_form=?", new Object[] {pForm.getId()}, new FormDesignRowMapper());
				
				String _columnBaseButton_1 = "";
				String _columnBaseButton_2 = "";
				
				for (FormColumn formDesign : _lstFormColumn) {
					Thread.sleep(1l);
					_columnBaseButton_1 = columnBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
					Thread.sleep(1l);
					_columnBaseButton_2 = columnBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
					try {
						jdbcTemplate.update("update sys_biaodansheji set tbl_bianma = ? where id = ?",  new Object[] {_columnBaseButton_1+","+_columnBaseButton_2, formDesign.getId()});
						Column column=(Column)jdbcTemplate.queryForObject("select tbl_columnZhName from sys_columns where id=?", new Object[] {formDesign.getColumnId()}, new ColumnRowMapper());
						jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value, tbl_parentId, tbl_systematom,tbl_usestatus) values(?,?,?,?,?,?,?,?)", 
								new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), column.getColumnZhName(), _columnBaseButton_1, pForm.getCode(),  Constants.SYSTEM_ATOM_COLN, "0", "1", formDesign.getTabId()+"_"+column.getId()});
						jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value, tbl_parentId, tbl_systematom,tbl_usestatus) values(?,?,?,?,?,?,?,?)", 
								new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), column.getColumnZhName(), _columnBaseButton_2, pForm.getCode(),  Constants.SYSTEM_ATOM_COLN, "0", "2", formDesign.getTabId()+"_"+column.getId()});
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				/*_lstFormColumn = jdbcTemplate.query("select * from sys_biaodansheji where tbl_form=?", new Object[] {pForm.getId()},  new FormDesignRowMapper());
				for (FormColumn formColumn : _lstFormColumn) {
					updateSeqExpand(formColumn.getCode().split(",")[0], pForm.getCode());
					updateSeqExpand(formColumn.getCode().split(",")[1], pForm.getCode());
				}*/
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void item(Form form) throws Exception{
		//获取所有选项卡表数据
		List<Tab> _lstFormTab = jdbcTemplate.query("select * from sys_biaodantab where tbl_form=?", new Object[] {form.getId()}, new TabRowMapper());
		
		String _item = "";
		for (Tab tab : _lstFormTab) {
			Thread.sleep(1l);
			_item = itemBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
			jdbcTemplate.update("update sys_biaodantab set tbl_bianma = ? where id = ?",  new Object[] {_item, tab.getId()});
			jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_parentId,tbl_systematom,tbl_usestatus,tbl_type,tbl_object) values(?,?,?,?,?,?,?,?,?,?)", 
					new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), tab.getTabName(), _item, form.getCode(), Constants.SYSTEM_ATOM_ITEM, "0", "2", form.getId()});
		}
	}
	
	public void formButton(Form pForm) throws Exception{
		try {
			if(pForm != null){
				//获取所有表单按钮表数据
				List<FormButton> _lstFormButton = jdbcTemplate.query("select * from sys_biaodanbutton where tbl_formid=?", new Object[] {pForm.getId()}, new FormButtonRowMapper());
				
				String _formBaseButton = "";
				String _buttonName = "";
				//循环表单按钮集合,对每一个表单按钮进行更新基本码;更新后在编码表中插入编码
				for (FormButton formButton : _lstFormButton) {
					Thread.sleep(1l);
					if(null != formButton.getTabId() && formButton.getTabId().equals("-1")){
						_formBaseButton = itemButtonBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
					}else{
						_formBaseButton = formButtonBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
					}
					try {
						jdbcTemplate.update("update sys_biaodanbutton set tbl_bianma = ? where id = ?",  new Object[] {_formBaseButton, formButton.getId()});
						_buttonName=jdbcTemplate.queryForObject("select tbl_buttonName from sys_button where id=?", new Object[] {formButton.getButtonId()}, String.class);
						if(null != formButton.getTabId() && formButton.getTabId().equals("-1")){
							jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value, tbl_parentId, tbl_systematom,tbl_usestatus) values(?,?,?,?,?,?,?,?)", 
									new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), _buttonName, _formBaseButton, pForm.getCode(), Constants.SYSTEM_ATOM_ITEM_BUTN, "0"});
						}else{
							jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value, tbl_parentId, tbl_systematom,tbl_usestatus) values(?,?,?,?,?,?,?,?)", 
									new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), _buttonName, _formBaseButton, pForm.getCode(), Constants.SYSTEM_ATOM_FORM_BUTN, "0"});
						}
						
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			
			}
		} catch (Exception e) {}
	}
	
	public void oprtButton(Tabulation pTabulation)throws Exception{
		try {
			if(pTabulation != null){
				//获取所有列表操作区按钮表数据
				List<TabulationOpt> _lstTabulationOptButton = jdbcTemplate.query("select * from sys_oprtbutton where tbl_tabulation=?", new Object[] {pTabulation.getId()}, new TabulationOptRowMapper());
				
				String _listBaseButton = "";
				String _buttonName = "";
				//循环列表操作区按钮集合,对每一个列表按钮进行更新基本码;更新后在编码表中插入编码
				for (TabulationOpt tabulationOpt : _lstTabulationOptButton) {
					Thread.sleep(1l);
					_listBaseButton = tabnButtonBase+DateUtil.getNowTimeSecondMilli()+"-"+systemCode;
					try {
						jdbcTemplate.update("update sys_oprtbutton set tbl_bianma = ? where id = ?", new Object[] {_listBaseButton, tabulationOpt.getId()});
						_buttonName=jdbcTemplate.queryForObject("select tbl_buttonName from sys_button where id=?", new Object[] {tabulationOpt.getButtonId()}, String.class);
						jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_parentId,tbl_systematom,tbl_usestatus) values(?,?,?,?,?,?,?,?)",
								new Object[] {UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), _buttonName, _listBaseButton, pTabulation.getCode(), Constants.SYSTEM_ATOM_TABN_BUTN, "0"});
					} catch (Exception e) {}
					
				}
				/*_lstTabulationOptButton = jdbcTemplate.query("select * from sys_oprtbutton where tbl_tabulation=?", new Object[] {pTabulation.getId()}, new TabulationOptRowMapper());
				for (TabulationOpt tabulationOpt : _lstTabulationOptButton) {
					updateSeqExpand(tabulationOpt.getCode(), pTabulation.getCode());
				}*/
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	//知识复用--->结束
}

class FormDesignRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		FormColumn f = new FormColumn();
		f.setId(rs.getLong("id"));
		f.setColumnId(rs.getLong("tbl_ziduan"));
		f.setCode(rs.getString("tbl_bianma"));
		f.setFormId(rs.getLong("tbl_form"));
		f.setTabId(rs.getLong("tbl_tabid"));
		f.setPartitionId(rs.getLong("tbl_partitionid"));
		return f;
	}
}

class CatalogRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		Catalog u = new Catalog();
		u.setId(rs.getLong("id"));
		u.setCode(rs.getString("tbl_bianma"));
		u.setName(rs.getString("tbl_name"));
		u.setParentId(rs.getLong("tbl_parentId"));
		u.setTabulationId(rs.getLong("tbl_listid"));
		return u;
	}
}

class TabulationRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		Tabulation t = new Tabulation();
		t.setId(rs.getLong("id"));
		t.setTabulationName(rs.getString("tbl_liebiaomingcheng"));
		t.setCode(rs.getString("tbl_bianma"));
		t.setFormId(rs.getLong("tbl_formid"));
		return t;
	}
}

class FormRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		Form u = new Form();
		u.setId(rs.getLong("id"));
		u.setFormName(rs.getString("tbl_biaodanming"));
		u.setCode(rs.getString("tbl_bianma"));
		return u;
	}
}

class ColumnRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		Column c = new Column();
		c.setId(rs.getLong("id"));
		c.setColumnZhName(rs.getString("tbl_columnZhName"));
		return c;
	}
}

class TabulationButtonRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		TabulationButton t = new TabulationButton();
		t.setId(rs.getLong("id"));
		t.setButtonId(rs.getLong("tbl_buttonid"));
		t.setListId(rs.getLong("tbl_tabulation"));
		t.setCode(rs.getString("tbl_bianma"));
		t.setShowName(rs.getString("tbl_showname"));
		return t;
	}
}

class FormButtonRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		FormButton f = new FormButton();
		f.setId(rs.getLong("id"));
		f.setButtonId(rs.getLong("tbl_buttonid"));
		f.setFormId(rs.getLong("tbl_formid"));
		f.setTabId(rs.getLong("tbl_tabid"));
		f.setCode(rs.getString("tbl_bianma"));
		f.setShowName(rs.getString("tbl_showname"));
		return f;
	}
}

class TabulationOptRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		TabulationOpt t = new TabulationOpt();
		t.setId(rs.getLong("id"));
		t.setButtonId(rs.getLong("tbl_buttonid"));
		t.setTabulationId(rs.getLong("tbl_tabulation"));
		t.setCode(rs.getString("tbl_bianma"));
		t.setShowName(rs.getString("tbl_showname"));
		return t;
	}
}

class TabRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		Tab u = new Tab();
		u.setId(rs.getLong("id"));
		u.setTabName(rs.getString("tbl_mingcheng"));
		u.setFormId(rs.getLong("tbl_form"));
		u.setCode(rs.getString("tbl_bianma"));
		return u;
	}
}