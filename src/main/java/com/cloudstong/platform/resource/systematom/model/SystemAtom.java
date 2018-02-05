package com.cloudstong.platform.resource.systematom.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:系统元素
 */
public class SystemAtom extends EntityBase {
	private static final long serialVersionUID = -5336440084686649814L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 系统元素名称
     */
    private String tblName;

    /**
     * 编码符号
     */
    private String tblBianmafuhao;

    /**
     * 对应数据表
     */
    private String tblDuiyingshujubiao;

    /**
     * 对应目录
     */
    private String tblDuiyingmulu;

    /**
     * 功能说明
     */
    private String tblGongnengshuoming;

    /**
     * 备注
     */
    private String tblBeizhu;

    /**
     * 类别
     */
    private String tblCategary;

    /**
     * 录入类型
     */
    private Long tblLuruleixing;

    /**
     * 父级元素
     */
    private String tblParentid;

    /**
     * 排序
     */
    private Long tblOrdernum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTblName() {
		return tblName;
	}

	public void setTblName(String tblName) {
		this.tblName = tblName;
	}

	public String getTblBianmafuhao() {
		return tblBianmafuhao;
	}

	public void setTblBianmafuhao(String tblBianmafuhao) {
		this.tblBianmafuhao = tblBianmafuhao;
	}

	public String getTblDuiyingshujubiao() {
		return tblDuiyingshujubiao;
	}

	public void setTblDuiyingshujubiao(String tblDuiyingshujubiao) {
		this.tblDuiyingshujubiao = tblDuiyingshujubiao;
	}

	public String getTblDuiyingmulu() {
		return tblDuiyingmulu;
	}

	public void setTblDuiyingmulu(String tblDuiyingmulu) {
		this.tblDuiyingmulu = tblDuiyingmulu;
	}

	public String getTblGongnengshuoming() {
		return tblGongnengshuoming;
	}

	public void setTblGongnengshuoming(String tblGongnengshuoming) {
		this.tblGongnengshuoming = tblGongnengshuoming;
	}

	public String getTblBeizhu() {
		return tblBeizhu;
	}

	public void setTblBeizhu(String tblBeizhu) {
		this.tblBeizhu = tblBeizhu;
	}

	public String getTblCategary() {
		return tblCategary;
	}

	public void setTblCategary(String tblCategary) {
		this.tblCategary = tblCategary;
	}

	public Long getTblLuruleixing() {
		return tblLuruleixing;
	}

	public void setTblLuruleixing(Long tblLuruleixing) {
		this.tblLuruleixing = tblLuruleixing;
	}

	public String getTblParentid() {
		return tblParentid;
	}

	public void setTblParentid(String tblParentid) {
		this.tblParentid = tblParentid;
	}

	public Long getTblOrdernum() {
		return tblOrdernum;
	}

	public void setTblOrdernum(Long tblOrdernum) {
		this.tblOrdernum = tblOrdernum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	} 

}
