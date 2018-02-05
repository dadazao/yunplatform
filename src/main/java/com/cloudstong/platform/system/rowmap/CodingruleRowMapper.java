/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cloudstong.platform.system.model.Codingrule;

/**
 * @author Allan
 * 
 *         Created on 2014-10-14
 * 
 *         Description:
 * 
 */
@SuppressWarnings("rawtypes")
public class CodingruleRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Codingrule codingrule = new Codingrule();
		codingrule.setId(rs.getLong("id"));
		codingrule.setBianmaduixiang(rs.getInt("tbl_bianmaduixiang"));
		codingrule.setZidongbianma(rs.getInt("tbl_zidongbianma"));
		codingrule.setBianmaqianzhui(rs.getString("tbl_bianmaqianzhui"));
		codingrule.setBianmafangshi(rs.getInt("tbl_bianmafangshi"));
		codingrule.setKefouxiugai(rs.getInt("tbl_kefouxiugai"));
		codingrule.setBeizhu(rs.getString("tbl_beizhu"));
		return codingrule;
	}

}
