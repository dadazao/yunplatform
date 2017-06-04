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
 *         Created on 2014-9-29
 * 
 *         Description:
 * 
 */
public class DesktopDesign extends EntityBase {

	private static final long serialVersionUID = 4435015199575281640L;

	private Long id;

	private Integer columnNum;

	private String columnWidths;

	private Long userid;

	private String layoutItemIds;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
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

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getLayoutItemIds() {
		return layoutItemIds;
	}

	public void setLayoutItemIds(String layoutItemIds) {
		this.layoutItemIds = layoutItemIds;
	}

	/**
	 * 获取布局参数
	 */
	public List<List<Long>> layout() {
		String layouts = getLayoutItemIds();
		if (layouts == null || layouts.length() == 0) {
			return Collections.emptyList();
		}
		String[] columns = layouts.split(";");
		List<List<Long>> columnList = new ArrayList<List<Long>>(columns.length);
		for (String column : columns) {
			String[] items = column.split(",");
			List<Long> itemList = new ArrayList<Long>(items.length);
			for (String item : items) {
				if (item.trim().length() != 0) {
					itemList.add(Long.valueOf(item));
				}
			}
			columnList.add(itemList);
		}
		return columnList;
	}
}
