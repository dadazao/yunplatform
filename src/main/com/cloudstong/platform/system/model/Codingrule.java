/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cloudstong.platform.core.model.EntityBase;

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
@Entity
@Table(name="sys_codingrule")
public class Codingrule extends EntityBase{

	@Id
	private Long id;
	
	/**
	 * 编码对象
	 */
	@Column(name="tbl_bianmaduixiang")
	private Integer bianmaduixiang;
	
	/**
	 * 是否自动编码
	 */
	@Column(name="tbl_zidongbianma")
	private Integer zidongbianma = 0;
	
	/**
	 * 编码前缀
	 */
	@Column(name="tbl_bianmaqianzhui")
	private String bianmaqianzhui;
	
	/**
	 * 编码方式
	 */
	@Column(name="tbl_bianmafangshi")
	private Integer bianmafangshi;
	
	/**
	 * 可否修改
	 */
	@Column(name="tbl_kefouxiugai")
	private Integer kefouxiugai = 0;
	
	/**
	 * 备注
	 */
	@Column(name="tbl_beizhu")
	private String beizhu;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setBianmaduixiang(Integer bianmaduixiang){
		this.bianmaduixiang = bianmaduixiang;
	}
	
	public Integer getBianmaduixiang() {
		return bianmaduixiang;
	}
	
	public void setZidongbianma(Integer zidongbianma){
		this.zidongbianma = zidongbianma;
	}
	
	public Integer getZidongbianma() {
		return zidongbianma;
	}
	
	public void setBianmaqianzhui(String bianmaqianzhui){
		this.bianmaqianzhui = bianmaqianzhui;
	}
	
	public String getBianmaqianzhui() {
		return bianmaqianzhui;
	}
	
	public void setBianmafangshi(Integer bianmafangshi){
		this.bianmafangshi = bianmafangshi;
	}
	
	public Integer getBianmafangshi() {
		return bianmafangshi;
	}
	
	public void setKefouxiugai(Integer kefouxiugai){
		this.kefouxiugai = kefouxiugai;
	}
	
	public Integer getKefouxiugai() {
		return kefouxiugai;
	}
	
	public void setBeizhu(String beizhu){
		this.beizhu = beizhu;
	}
	
	public String getBeizhu() {
		return beizhu;
	}
	
}