package com.cloudstong.platform.system.model;

import java.sql.Date;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author sam
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:人员表的映射对象
 */
@SuppressWarnings("serial")
public class Person extends EntityBase {
	/**
	 * 人员id
	 */
	private Long id;
	
	/**
	 * 姓名 
	 */
	private String tblXingming;
	
	/**
	 * 性别
	 */
	private int tblXingbie;
	
	/**
	 * 政治面貌
	 */
	private String tblZhengzhimianmao;
	
	/**
	 * 学历 
	 */
	private String tblXueli;
	
	/**
	 * 手机号码 
	 */
	private String tblShoujihaoma;
	
	/**
	 * Email
	 */
	private String tblEmail;
	
	/**
	 * 联系地址
	 */
	private String tblLianxidizhi;
	
	/**
	 * 所属部门 
	 */
	private String tblSuoshubumen;
	
	/**
	 * 职务 
	 */
	private String tblZhiwu;
	
	/**
	 * 员工编号
	 */
	private String tblYuangongbianhao;
	
	/**
	 * 出生年月 
	 */
	private Date tblChushengnianyue;
	
	/**
	 * 证件类型 
	 */
	private String tblZhengjianleixing;
	
	/**
	 * 证件号码 
	 */
	private String tblZhengjianhaoma;
	
	/**
	 * 民族 
	 */
	private String tblMinzu;
	
	/**
	 * 籍贯 
	 */
	private String tblJiguan;
	
	/**
	 * 专业 
	 */
	private String tblZhuanye;
	
	/**
	 * 联系电话 
	 */
	private String tblLianxidianhua;
	
	/**
	 * 个人简介
	 */
	private String tblGerenjianjie;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTblXingming() {
		return tblXingming;
	}

	public void setTblXingming(String tblXingming) {
		this.tblXingming = tblXingming;
	}

	public int getTblXingbie() {
		return tblXingbie;
	}

	public void setTblXingbie(int tblXingbie) {
		this.tblXingbie = tblXingbie;
	}

	public String getTblZhengzhimianmao() {
		return tblZhengzhimianmao;
	}

	public void setTblZhengzhimianmao(String tblZhengzhimianmao) {
		this.tblZhengzhimianmao = tblZhengzhimianmao;
	}

	public String getTblXueli() {
		return tblXueli;
	}

	public void setTblXueli(String tblXueli) {
		this.tblXueli = tblXueli;
	}

	public String getTblShoujihaoma() {
		return tblShoujihaoma;
	}

	public void setTblShoujihaoma(String tblShoujihaoma) {
		this.tblShoujihaoma = tblShoujihaoma;
	}

	public String getTblEmail() {
		return tblEmail;
	}

	public void setTblEmail(String tblEmail) {
		this.tblEmail = tblEmail;
	}

	public String getTblLianxidizhi() {
		return tblLianxidizhi;
	}

	public void setTblLianxidizhi(String tblLianxidizhi) {
		this.tblLianxidizhi = tblLianxidizhi;
	}

	public String getTblSuoshubumen() {
		return tblSuoshubumen;
	}

	public void setTblSuoshubumen(String tblSuoshubumen) {
		this.tblSuoshubumen = tblSuoshubumen;
	}

	public String getTblZhiwu() {
		return tblZhiwu;
	}

	public void setTblZhiwu(String tblZhiwu) {
		this.tblZhiwu = tblZhiwu;
	}

	public String getTblYuangongbianhao() {
		return tblYuangongbianhao;
	}

	public void setTblYuangongbianhao(String tblYuangongbianhao) {
		this.tblYuangongbianhao = tblYuangongbianhao;
	}

	public Date getTblChushengnianyue() {
		return tblChushengnianyue;
	}

	public void setTblChushengnianyue(Date tblChushengnianyue) {
		this.tblChushengnianyue = tblChushengnianyue;
	}

	public String getTblZhengjianleixing() {
		return tblZhengjianleixing;
	}

	public void setTblZhengjianleixing(String tblZhengjianleixing) {
		this.tblZhengjianleixing = tblZhengjianleixing;
	}

	public String getTblZhengjianhaoma() {
		return tblZhengjianhaoma;
	}

	public void setTblZhengjianhaoma(String tblZhengjianhaoma) {
		this.tblZhengjianhaoma = tblZhengjianhaoma;
	}

	public String getTblMinzu() {
		return tblMinzu;
	}

	public void setTblMinzu(String tblMinzu) {
		this.tblMinzu = tblMinzu;
	}

	public String getTblJiguan() {
		return tblJiguan;
	}

	public void setTblJiguan(String tblJiguan) {
		this.tblJiguan = tblJiguan;
	}

	public String getTblZhuanye() {
		return tblZhuanye;
	}

	public void setTblZhuanye(String tblZhuanye) {
		this.tblZhuanye = tblZhuanye;
	}

	public String getTblLianxidianhua() {
		return tblLianxidianhua;
	}

	public void setTblLianxidianhua(String tblLianxidianhua) {
		this.tblLianxidianhua = tblLianxidianhua;
	}

	public String getTblGerenjianjie() {
		return tblGerenjianjie;
	}

	public void setTblGerenjianjie(String tblGerenjianjie) {
		this.tblGerenjianjie = tblGerenjianjie;
	}

	
}
