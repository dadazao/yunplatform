/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.attachment.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cloudstong.platform.resource.attachment.model.Attachment;

/**
 * @author liuqi
 * 
 *         Created on 2014-10-8
 * 
 *         Description:
 * 
 */
public class AttachmentRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Attachment attachment = new Attachment();
		attachment.setId(rs.getLong("id"));
		attachment.setFileName(rs.getString("tbl_filename"));
		attachment.setFilePath(rs.getString("tbl_filepath"));
		attachment.setFileExt(rs.getString("tbl_fileext"));
		attachment.setFileSize(rs.getString("tbl_filesize"));
		attachment.setModel(rs.getString("tbl_model"));
		attachment.setColumn(rs.getString("tbl_column"));
		attachment.setRecordColumnValue(rs.getString("tbl_columnvalue"));
		attachment.setRecordColumnName(rs.getString("tbl_columnname"));
		attachment.setRecordId(rs.getString("tbl_recordid"));
		attachment.setCreateBy(rs.getLong("comm_createBy"));
		attachment.setCreateDate(rs.getTimestamp("comm_createDate"));
		attachment.setCatalogId(rs.getString("tbl_catalogid"));
		try {
			if(rs.getString("tbl_yonghuxingming")!=null){
				attachment.setCreateUser(rs.getString("tbl_yonghuxingming"));
			}
		} catch (Exception e) {
			
		}
		return attachment;
	}

}
