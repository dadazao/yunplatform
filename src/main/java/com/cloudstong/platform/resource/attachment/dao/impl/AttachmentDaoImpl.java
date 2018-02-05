package com.cloudstong.platform.resource.attachment.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.attachment.dao.AttachmentDao;
import com.cloudstong.platform.resource.attachment.model.Attachment;
import com.cloudstong.platform.resource.attachment.rowmap.AttachmentRowMapper;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:附件操作数据库接口实现类
 */
@Repository("attachmentDao")
public class AttachmentDaoImpl extends BaseJdbcDaoImpl<Attachment, Long> implements AttachmentDao {
	@Resource
	private JdbcTemplateExtend jdbcTemplateExtend;
	
	private JdbcTemplate jdbcTemplate;

	public AttachmentDaoImpl() {
		super();
	}
	
	@Override
	public String getTable() {
		return "sys_attachment";
	}
	
	public AttachmentDaoImpl(WebApplicationContext wc) {
		super();
		this.jdbcTemplate=(JdbcTemplate)wc.getBean("jdbcTemplate");
	}

	public JdbcTemplateExtend getJdbcTemplateExtend() {
		return jdbcTemplateExtend;
	}

	public void setJdbcTemplateExtend(JdbcTemplateExtend jdbcTemplateExtend) {
		this.jdbcTemplateExtend = jdbcTemplateExtend;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Long insertAttachment(Attachment attachment){
		Long id = UniqueIdUtil.genId();
		final String _sql = "insert into sys_attachment (id,tbl_filename,tbl_filepath,tbl_fileext,tbl_filesize,tbl_model,tbl_column,tbl_columnvalue,tbl_columnname,tbl_recordid,comm_createBy,comm_createDate,tbl_catalogid) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		final Object [] _params = new Object[]{id,attachment.getFileName(),attachment.getFilePath(),attachment.getFileExt(),attachment.getFileSize(),attachment.getModel(),attachment.getColumn(),attachment.getRecordColumnValue(),attachment.getRecordColumnName(),attachment.getRecordId(),attachment.getCreateBy(),attachment.getCreateDate(),attachment.getCatalogId()};
		jdbcTemplate.update(_sql,_params);
		return id;
	}

	@Override
	public void addRelByAttachment(String model, String columnname,
			String attachmentId, String recordid) {
		String _selsql="select "+columnname+" from "+model+" where id = ?";
		Object [] _params = new Object[]{recordid};
		List list=jdbcTemplate.queryForList(_selsql,_params);
		if(list.size()>0){
			Object value=((Map)list.get(0)).get(columnname);
			if(value==null||value.toString().equals("")){
				value=attachmentId;
			}else{
				value = value + ","+attachmentId;
			}
			String _sql = "update " + model + " set " + columnname + " = '"+value+"' where id = ?";
			jdbcTemplate.update(_sql, _params);
		}
	}

	@Override
	public List<Attachment> findAttachmentByIds(String fileIds) {
		String [] fileid=fileIds.split(",");
		StringBuffer _sql=new StringBuffer("select * from sys_attachment where id in(");
		for(int i=0;i<fileid.length;i++){
			if(i==0){
				_sql.append("?");
			}else{
				_sql.append(",?");
			}
		}
		_sql.append(")");
		return jdbcTemplateExtend.query(_sql.toString(),fileid, new AttachmentRowMapper());
	}
	
	@Override
	public void delete(String fileid) {
		String _sql = "delete from sys_attachment where id = ?";
		Object [] _params = new Object[]{fileid};
		jdbcTemplateExtend.update(_sql,_params);
	}

	@Override
	public String findTableName(String tableEnName) {
		String _sql = "select tbl_tableZhName from sys_tables where tbl_tableName = ?";
		Object [] _params = new Object[]{tableEnName};
		return jdbcTemplate.queryForObject(_sql, _params, String.class);
	}

	@Override
	public String findColumnName(String tableEnName,String columnEnName) {
		String _sql = "select tbl_columnZhName from sys_columns where tbl_columnName = ? and tbl_belongTable = ?";
		Object [] _params = new Object[]{columnEnName,tableEnName};
		return jdbcTemplate.queryForObject(_sql, _params, String.class);
	}

	@Override
	public String findRecordColumnValue(String model, String recordid,
			String recordColumn) {
		String recordColumnValue ="";
		String _csql = "select tbl_columnName from sys_columns where id = ?";
		Object [] _cparams = new Object[]{recordColumn};
		String columnName = jdbcTemplate.queryForObject(_csql, _cparams, String.class);
		if(columnName!=null&&!columnName.equals("")){
			String _sql="select "+columnName+" from "+model+" where id = ?";
			Object [] _params = new Object[]{recordid};
			recordColumnValue = jdbcTemplate.queryForObject(_sql, _params, String.class);
		}
		return recordColumnValue;
	}

	@Override
	public String findRecordColumnName(String recordColumn) {
		String _sql = "select tbl_columnName from sys_columns where id = ?";
		Object [] _params = new Object[]{recordColumn};
		String columnName = jdbcTemplate.queryForObject(_sql, _params, String.class);
		return columnName;
	}

	@Override
	public List<Attachment> findAttachmentsByRid(Long id) {
		String _sql = "select * from sys_attachment where tbl_recordid = ?";
		Object [] _params = new Object[]{id};
		return jdbcTemplateExtend.query(_sql,_params,new AttachmentRowMapper());
	}

	@Override
	public void updateAttachment(Attachment attachment, Domain domain, Long recordId) {
		String _ssql = "select "+attachment.getRecordColumnName()+" from "+domain.getTable().getTableName()+" where id = ?";
		Object [] _params = new Object[]{recordId};
		String value = jdbcTemplateExtend.queryForObject(_ssql, _params,String.class);
		if(value!=null && !value.equals("")){
			String _usql = "update sys_attachment set tbl_columnvalue = '"+value+"' where id = '"+attachment.getId()+"'";
			jdbcTemplateExtend.update(_usql);
		}
	}

	@Override
	public void updateRecordId(String uploadifyValue,Long recordId) {
		String [] attachmentIds = uploadifyValue.split(",");
		StringBuffer sql=new StringBuffer("update sys_attachment s set tbl_recordid = ? where id in(");
		List params=new ArrayList();
		params.add(recordId);
		for(int i=0;i<attachmentIds.length;i++){
			if(i==0){
				sql.append("?");
			}else{
				sql.append(",?");
			}
			params.add(attachmentIds[i]);
		}
		sql.append(")");
		jdbcTemplateExtend.update(sql.toString(), params.toArray());
	}

}
