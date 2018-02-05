package com.cloudstong.platform.resource.seqcode.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.DateUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.seqcode.dao.SeqcodeDao;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:编码管理的数据库实现
 */
@Repository("seqcodeDao")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SeqcodeDaoImpl implements SeqcodeDao {

	public static String SYSTEM_CODE = "SYSM-00001";
	private String cataBase = "FUNC-";
	private String menuBase = "MENU-";
	private String formBase = "FORM-";
	private String listBase = "TABN-";
	private String columnBase = "COLN-";
	private String formButtonBase = "FORM_BUTN-";
	private String itemBase = "ITEM-";
	private String itemButtonBase = "ITEM_BUTN-";
	private String tabnButtonBase = "TABN_BUTN-";
	
	List<Seqcode> LST_SEQCODE = new ArrayList<Seqcode>();
	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	/* 
	 * @see com.cloudstongplatform.metadata.dao.SeqcodeDao#querySeqById(java.lang.String)
	 */
	@Override
	public Seqcode querySeqById(Long id) {
		String sql = "select * from sys_bianma where id=?";
		Object[] obj = new Object[] { id };
		List<Seqcode> _lstResult = jdbcTemplate.query(sql, obj,
				new BeanPropertyRowMapper(Seqcode.class));
		if (_lstResult.size() > 0) {
			return _lstResult.get(0);
		} else {
			return new Seqcode();
		}
	}

	/* 
	 * @see com.cloudstongplatform.metadata.dao.SeqcodeDao#selectSeq()
	 */
	public List<Seqcode> selectSeq() {
		String sql = "select * from sys_bianma where tbl_usestatus='0'";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Seqcode.class));
	}

	/* 
	 * @see com.cloudstongplatform.metadata.dao.SeqcodeDao#querySeqByValue(java.lang.String)
	 */
	@Override
	public List<Seqcode> querySeqByValue(String psSeqValue) {
		LST_SEQCODE = new ArrayList<Seqcode>();
		
		String sql = "select * from sys_bianma where tbl_value=?";
		List<Seqcode> _lstSeqcode =  jdbcTemplate.query(sql, new Object[] { psSeqValue}, new BeanPropertyRowMapper(Seqcode.class));
		if(_lstSeqcode != null && _lstSeqcode.size()>0){
			LST_SEQCODE.add(_lstSeqcode.get(0));
			queryChildrenSeqcode(psSeqValue);
			
		}
		if(LST_SEQCODE == null){
			LST_SEQCODE = new ArrayList<Seqcode>();
		}
		
		return LST_SEQCODE;
	}
	
	/**
	 * Description:使用迭代,循环取得编码的上级基本码
	 * 
	 * Steps:
	 * 
	 * @param psParentId
	 * 				编码的上级码
	 */
	private void queryChildrenSeqcode(String psParentId){
		String sql = "select * from sys_bianma where tbl_parentId=?";
		List<Seqcode> _lstSeqcode =  jdbcTemplate.query(sql, new Object[] { psParentId}, new BeanPropertyRowMapper(Seqcode.class));
		if(_lstSeqcode !=null && _lstSeqcode.size()>0){
			for (Seqcode seqcode : _lstSeqcode) {
				if(!seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_FUNC)){
					if(!seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_FORM) && 
							!seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_TABN)){
						LST_SEQCODE.add(seqcode);
					}
					queryChildrenSeqcode(seqcode.getTblValue());
				}
				
			}
		}
	}
	
	/* 
	 * @see com.cloudstongplatform.metadata.dao.SeqcodeDao#saveSeq(com.cloudstongplatform.metadata.model.Seqcode)
	 */
	@Override
	public String saveSeq(Seqcode seqcode){
		String _sSeqCode = "";
		if(seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_FUNC)){
			_sSeqCode = cataBase;
			seqcode.setTblTablename("sys_catalog");
		}else if(seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_FORM)){
			_sSeqCode = formBase;
			seqcode.setTblTablename("sys_biaodan");
		}else if(seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_TABN)){
			_sSeqCode = listBase;
			seqcode.setTblTablename("sys_liebiao");
		}else if(seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_MENU)){
			_sSeqCode = menuBase;
			seqcode.setTblTablename("sys_menu");
		}else if(seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_FORM_BUTN)){
			_sSeqCode = formButtonBase;
			seqcode.setTblTablename("sys_biaodanbutton");
		}else if(seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_ITEM)){
			_sSeqCode = itemBase;
			seqcode.setTblTablename("sys_biaodantab");
		}else if(seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_COLN)){
			_sSeqCode = columnBase;
			seqcode.setTblTablename("sys_biaodansheji");
		}else if(seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_ITEM_BUTN)){
			_sSeqCode = itemButtonBase;
			seqcode.setTblTablename("sys_biaodanbutton");
		}else if(seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_TABN_BUTN)){
			_sSeqCode = tabnButtonBase;
			seqcode.setTblTablename("sys_liebiaobutton");
		}else if(seqcode.getTblSystematom().equals(Constants.SYSTEM_ATOM_OPRT_BUTN)){
			_sSeqCode = tabnButtonBase;
			seqcode.setTblTablename("sys_oprtbutton");
		}
		
		_sSeqCode = _sSeqCode+DateUtil.getNowTimeSecondMilli()+"-"+SYSTEM_CODE;
		jdbcTemplate.update("insert into sys_bianma(id,comm_createDate,comm_updateDate,tbl_name,tbl_value,tbl_systematom, tbl_usestatus, tbl_type, tbl_object, tbl_tablename) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { UniqueIdUtil.genId(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()),seqcode.getTblName(),_sSeqCode, seqcode.getTblSystematom(), "0", seqcode.getTblType(), seqcode.getTblObject(), seqcode.getTblTablename()});
		return _sSeqCode;
	}

	/* 
	 * @see com.cloudstongplatform.metadata.dao.SeqcodeDao#updateSeqStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateSeqStatus(String psId, String psUsestatus){
		String sql = "update sys_bianma set tbl_usestatus=? where id=?";
		Object[] _objParams = new Object[] { psUsestatus, psId};
		jdbcTemplate.update(sql, _objParams);
	}
	
	/* 
	 * @see com.cloudstongplatform.metadata.dao.SeqcodeDao#updateSeq(java.lang.String, java.lang.String)
	 */
	public void updateSeq(String psSeqValue, String psParentId){
		/*清理该目录下的所有列表和表单,为更新新目录做准备,*/
		List<Seqcode> _lstSeqcodeList = new ArrayList<Seqcode>();
		if(psSeqValue.contains("FORM")){
			_lstSeqcodeList = jdbcTemplate.query(
					"select * from sys_bianma where tbl_parentId=? and tbl_systematom=?", new Object[] { psParentId, "2"}, new BeanPropertyRowMapper(Seqcode.class));
			for (Seqcode seqcode : _lstSeqcodeList) {
				jdbcTemplate.update("update sys_bianma set tbl_parentId=?,comm_updateDate=? where tbl_value=?", new Object[] { "", new Date(System.currentTimeMillis()), seqcode.getTblValue()});
			}
		}else if( psSeqValue.contains("TABN")){
			_lstSeqcodeList = jdbcTemplate.query(
					"select * from sys_bianma where tbl_parentId=? and tbl_systematom=?", new Object[] { psParentId, "18"}, new BeanPropertyRowMapper(Seqcode.class));
			for (Seqcode seqcode : _lstSeqcodeList) {
				jdbcTemplate.update("update sys_bianma set tbl_parentId=?,comm_updateDate=? where tbl_value=?", new Object[] {"", new Date(System.currentTimeMillis()), seqcode.getTblValue()});
			}
		}
		/*清理结束*/
		
		String sql = "update sys_bianma set tbl_parentId=? where tbl_value=?";
		Object[] _objParams = new Object[] { psParentId, psSeqValue};
		jdbcTemplate.update(sql, _objParams);
	}
	
	/* 
	 * @see com.cloudstongplatform.metadata.dao.SeqcodeDao#updateSeqExtends(java.lang.String, java.lang.String)
	 */
	public void updateSeqExtends(String psSeqValue, String psParentId){
//		String _sParentId = psParentId;
//		String _sSeqExpandCode = psParentId;
//		for (int i=0; i<10; i++) {
//			if(_sParentId == null || _sParentId.equals(SYSTEM_CODE)){
//				break;
//			}else{
//				try {
//					List<Seqcode> _lstSeqcode = jdbcTemplate.query(
//							"select * from sys_bianma where tbl_value=?", new Object[] { _sParentId }, new BeanPropertyRowMapper(Seqcode.class));
//					_sParentId = _lstSeqcode.get(0).getTblParentid();
//					_sSeqExpandCode = _lstSeqcode.get(0).getTblValue().split("-"+SYSTEM_CODE)[0]+"_"+_sSeqExpandCode;
//				} catch (Exception e) {
//					break;
//				}
//			}
//		}
//		//更新该目录下的所有资源的扩展码
//		List<Seqcode> _lstSeqcodeList = jdbcTemplate.query(
//				"select * from sys_bianma where tbl_parentId=?", new Object[] { psParentId}, new BeanPropertyRowMapper(Seqcode.class));
//		String _sFormExtendCode = "";
//		for (Seqcode seqcode : _lstSeqcodeList) {
//			_sFormExtendCode = seqcode.getTblValue().split("-"+SYSTEM_CODE)[0]+"_"+_sSeqExpandCode;
//			jdbcTemplate.update("update sys_bianma set tbl_expand=?,tbl_parentId=?,comm_updateDate=? where tbl_value=?", new Object[] { _sFormExtendCode, psParentId, new Date(System.currentTimeMillis()), seqcode.getTblValue()});
//			List<Seqcode> _lstSeqcodeTmp = jdbcTemplate.query(
//					"select * from sys_bianma where tbl_parentId=?", new Object[] { seqcode.getTblValue()}, new BeanPropertyRowMapper(Seqcode.class));
//			for (Seqcode seqcode2 : _lstSeqcodeTmp) {
//				jdbcTemplate.update("update sys_bianma set tbl_expand=?,tbl_parentId=?,comm_updateDate=? where tbl_value=?", new Object[] { seqcode2.getTblValue().split("-"+SYSTEM_CODE)[0]+"_"+_sFormExtendCode, seqcode.getTblValue(), new Date(System.currentTimeMillis()), seqcode2.getTblValue()});
//			}
//		}
	}
	
	/* 
	 * @see com.cloudstongplatform.metadata.dao.SeqcodeDao#updateSeqName(java.lang.String, java.lang.String)
	 */
	public void updateSeqName(String psSeqValue, String psName){
		String sql = "update sys_bianma set tbl_name=?,comm_updateDate=? where tbl_value=?";
		Object[] _objParams = new Object[] { psName, new Date(System.currentTimeMillis()), psSeqValue};
		jdbcTemplate.update(sql, _objParams);
	}
	
	/* 
	 * @see com.cloudstongplatform.metadata.dao.SeqcodeDao#deleteSeq(java.lang.String)
	 */
	public void deleteSeq(String psSeqValue){
		String sql = "delete from sys_bianma where tbl_value=?";
		Object[] _objParams = new Object[] { psSeqValue};
		jdbcTemplate.update(sql, _objParams);
	}
	
	/* 
	 * @see com.cloudstongplatform.metadata.dao.SeqcodeDao#init()
	 */
	public void init(){ 
		SeqCodeUtilDao sud = new SeqCodeUtilDao(jdbcTemplate);
		try {
			sud.clean_seqcode();//清理编码编码
			sud.catalog();//初始化目录
			sud.list();//初始化列表
			sud.form();//初始化表单
			sud.listButton();//初始化列表按钮
			sud.item();
			sud.formAndItemButton();//初始化表单按钮
			sud.oprtButton();//初始化操作区按钮
			sud.column();
			sud.updatePrivilageSeqcode();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String knowledgeReuse(Object[] pObjs, String psType) throws Exception{
		SeqCodeUtilDao sud = new SeqCodeUtilDao(jdbcTemplate);
		if(psType.equals("1")){
			if(pObjs !=null && pObjs.length ==1 && pObjs[0] instanceof String){
				sud.catalog(pObjs[0].toString());
				return "1";
			}else{
				return "0";
			}
		}else if(psType.equals("2")){
			if(pObjs !=null && pObjs.length ==1 && pObjs[0] instanceof Catalog){
				sud.list((Catalog)pObjs[0]);
				return "2";
			}else{
				return "0";
			}
		}else if(psType.equals("3")){
			if(pObjs !=null && pObjs.length ==2 && pObjs[0] instanceof Tabulation && pObjs[1] instanceof Catalog){
				sud.form((Tabulation)pObjs[0], (Catalog)pObjs[1]);
				return "3";
			}else{
				return "0";
			}
		}
		return null;
	}

	@Override
	public void logicDeleteSeq(String code) {
		String sql = "update sys_bianma set tbl_dstatus = 1 where tbl_value = ?";
		Object[] _objParams = new Object[] { code};
		jdbcTemplate.update(sql, _objParams);
	}

	@Override
	public String checkPartitionType(FormColumn formColumn) {
		SeqCodeUtilDao sud = new SeqCodeUtilDao(jdbcTemplate);
		return sud.checkPartitionType(formColumn);
	}
}