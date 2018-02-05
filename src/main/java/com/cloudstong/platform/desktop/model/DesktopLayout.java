/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:桌面布局
 * 
 */
public class DesktopLayout extends EntityBase {

	private static final long serialVersionUID = -1403657513854435396L;

	private Long id;

	private String name;
	/**
	 * 列数
	 */
	private Integer columnNum = 0;
	/**
	 * 列宽，多个数值使用","分隔开
	 */
	private String columnWidths;
	/**
	 * 默认
	 */
	private boolean deflt = false;
	/**
	 * 备注
	 */
	private String description;
	/**
	 * 布局设置信息
	 */
	private String items; // 格式aa,bb,cc;dd,ee;ff,fd分号表示列的分隔

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * Description:根据没列的宽度按比例计算各列所占百分比，这样做的目的是为了防止用户输入的几个数字和大于100
	 * 
	 * 比如：如果有三列，用户输入200,200,300，那么系统布局的时候将会按照2:2:3的比例来布局，而不是200%，300%
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	public List<String> caculateWidthPercent() {
		String widths = getColumnWidths();
		if (widths == null) {
			return Collections.emptyList();
		}
		String[] widthArray = widths.split(",");
		double[] widthNums = new double[widthArray.length];
		List<String> resultList = new ArrayList<String>();
		double total = 0;
		// 根据多列的比值，确定百分比
		for (int i = 0, length = widthArray.length; i < length; i++) {
			widthNums[i] = Double.valueOf(widthArray[i]);
			total += widthNums[i];
		}

		if (total == 0) {
			return Collections.emptyList();
		}

		for (double w : widthNums) {
			int p = (int) Math.floor((w / total) * 100);
			resultList.add(p + "");
		}
		return resultList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}

	public String getColumnWidths() {
		return columnWidths;
	}

	public void setColumnWidths(String columnWidths) {
		this.columnWidths = columnWidths;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDeflt() {
		return deflt;
	}

	public void setDeflt(boolean deflt) {
		this.deflt = deflt;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

}
