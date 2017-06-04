/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.EncryptUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.CodingruleDao;
import com.cloudstong.platform.system.model.Codingrule;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.rowmap.CodingruleRowMapper;
import com.cloudstong.platform.system.service.CodingruleService;
import com.cloudstong.platform.system.service.impl.SysOrgServiceImpl.SysOrgRowMapper;

/**
 * @author Allan
 * Created on 2014-9-29 14:58:55
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
@Repository("codingruleService")
public class CodingruleServiceImpl implements CodingruleService {

	@Resource
	private CodingruleDao codingruleDao;

	@Override
	public List<Codingrule> getAllCodingrule() {
		return codingruleDao.getAll(new CodingruleRowMapper());
	}

	@Override
	public Long doSaveCodingrule(Codingrule codingrule,SysUser user) {
		Long id = -1L;
		Date now = new Date(System.currentTimeMillis());
		if(codingrule.getId()==null||codingrule.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_codingrule (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_bianmaduixiang,tbl_zidongbianma,tbl_bianmaqianzhui,tbl_bianmafangshi,tbl_kefouxiugai,tbl_beizhu)" +
					"values(?,?,?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,codingrule.getBianmaduixiang(),codingrule.getZidongbianma(),codingrule.getBianmaqianzhui(),codingrule.getBianmafangshi(),codingrule.getKefouxiugai(),codingrule.getBeizhu()};
			codingruleDao.insert(sql, args);
			id = uuid;
		}else{
			id = codingrule.getId();
			String sql = "update sys_codingrule set comm_updateBy=?,comm_updateDate=?,tbl_bianmaduixiang=?,tbl_zidongbianma=?,tbl_bianmaqianzhui=?,tbl_bianmafangshi=?,tbl_kefouxiugai=?,tbl_beizhu=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,codingrule.getBianmaduixiang(),codingrule.getZidongbianma(),codingrule.getBianmaqianzhui(),codingrule.getBianmafangshi(),codingrule.getKefouxiugai(),codingrule.getBeizhu(),codingrule.getId()};
			codingruleDao.update(sql, args);
		}
		return id; 
	}

	@Override
	public void doDeleteCodingrule(Long codingruleId) {
		codingruleDao.delete(codingruleId);
	}

	@Override
	public Codingrule findCodingruleById(Long codingruleId) {
		return (Codingrule)codingruleDao.selectById(codingruleId, new CodingruleRowMapper());
	}

	@Override
	public PageResult queryCodingrule(QueryCriteria queryCriteria){
		return codingruleDao.query(queryCriteria, new CodingruleRowMapper());
	}

	@Override
	public void doDeleteCodingrules(Long[] codingruleIds) {
		for (Long id : codingruleIds) {
			doDeleteCodingrule(id);
		}
	}
	
}
