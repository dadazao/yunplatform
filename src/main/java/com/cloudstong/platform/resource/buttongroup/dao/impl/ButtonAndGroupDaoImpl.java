package com.cloudstong.platform.resource.buttongroup.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.button.model.Button;
import com.cloudstong.platform.resource.buttongroup.dao.ButtonAndGroupDao;
import com.cloudstong.platform.resource.buttongroup.model.ButtonAndGroup;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 按钮和按钮组中间表对象操作数据库接口实现类
 */
@Repository("buttonAndGroupDao")
public class ButtonAndGroupDaoImpl implements ButtonAndGroupDao {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private JdbcTemplateExtend jdbcTemplateExtend;

	public JdbcTemplateExtend getJdbcTemplateExtend() {
		return jdbcTemplateExtend;
	}

	public void setJdbcTemplateExtend(JdbcTemplateExtend jdbcTemplateExtend) {
		this.jdbcTemplateExtend = jdbcTemplateExtend;
	}

	@Override
	public Long insert(ButtonAndGroup buttonAndGroup) throws Exception {
		Long id = UniqueIdUtil.genId();
		Object[] params = this.getInsertParams(buttonAndGroup,id);
		jdbcTemplateExtend.update(getInsertSQL(),params);
		return id;
	}

	private String getInsertSQL() {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into sys_buttonAndGroup(id,tbl_buttonGroupID,tbl_buttonID,tbl_buttonEnabledStatus,tbl_buttonDisplayStatus,tbl_buttonDisplayOrder,comm_status)");
		sql.append("values(?,?,?,?,?,?,?)");
		return sql.toString();
	}

	private Object[] getInsertParams(ButtonAndGroup buttonAndGroup,Long id) {
		return new Object[] {id,buttonAndGroup.getButtonGroupID(),
				buttonAndGroup.getButtonID(),
				buttonAndGroup.getButtonEnabledStatus(),
				buttonAndGroup.getButtonDisplayStatus(),
				buttonAndGroup.getButtonDisplayOrder(),
				buttonAndGroup.getStatus() };
	}

	@Override
	public int update(ButtonAndGroup buttonAndGroup) throws Exception {
		try {
			return jdbcTemplateExtend.update(this.getUpdateSQL(),
					this.getUpdateParams(buttonAndGroup));
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":" + e.getMessage());
		}
		return 0;
	}

	private String getUpdateSQL() {
		StringBuffer sql = new StringBuffer();
		sql.append("update sys_buttonAndGroup set tbl_buttonEnabledStatus = ?,tbl_buttonDisplayStatus = ?,tbl_buttonDisplayOrder = ? where ID = ?");

		return sql.toString();
	}

	private Object[] getUpdateParams(ButtonAndGroup buttonAndGroup) {
		return new Object[] { buttonAndGroup.getButtonEnabledStatus(),
				buttonAndGroup.getButtonDisplayStatus(),
				buttonAndGroup.getButtonDisplayOrder(), buttonAndGroup.getId() };
	}

	@Override
	public int updateStatus(String buttonAndGroupID) throws Exception {
		try {
			return jdbcTemplateExtend.update(
					"update sys_buttonandgroup set comm_status=1 where id=?",
					new Object[] { buttonAndGroupID });
		} catch (Exception e) {
			logger.error(this.getClass().getName() + ":" + e.getMessage(), e);
		}
		return 0;
	}
	
	public int delete(Long buttonAndGroupID)  {
		try {
			return jdbcTemplateExtend.update(
					"delete from sys_buttonandgroup where id=?",
					new Object[] { buttonAndGroupID });
		} catch (Exception e) {
			logger.error(this.getClass().getName() + ":" + e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public List queryForPageList(String where, Object[] args, int startRow,
			int rowsCount) throws Exception {
		List queryForPageList = new ArrayList();
		try {
			if (rowsCount == -1) {
				queryForPageList = jdbcTemplateExtend.query(where, args,
						new ButtonAndGroupRowMapper());
			} else {
				queryForPageList = jdbcTemplateExtend.querySP(
						this.getQueryForPageListSQL(where), args, startRow,
						rowsCount, new ButtonAndGroupRowMapper());
			}
		} catch (Exception e) {
		}
		return queryForPageList;
	}

	public String getQueryForPageListSQL(String where) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,tbl_buttongroupid,tbl_buttonid,tbl_buttonenabledstatus,tbl_buttondisplaystatus,tbl_buttondisplayorder,comm_status ");
		sql.append(" from sys_buttonandgroup where comm_status = 0 ").append(where);
		return sql.toString();
	}

	@Override
	public int getTotalCount(String where, Object[] args) throws Exception {
		try {
			StringBuffer buffer = new StringBuffer(
					"select count(*) as totalCount from sys_buttonAndGroup where comm_status = 0")
					.append(where);
			return (Integer)jdbcTemplateExtend.queryForObject(buffer.toString(), args,
					new TotalCountMapper());
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":getRowCount:"
					+ e.getMessage());
		}
		return 0;
	}

	@Override
	public ButtonAndGroup findByID(String buttonGroupID) throws Exception {
		try {
			return (ButtonAndGroup)jdbcTemplateExtend.queryForObject(this.getFindByIDSQL(),
					new Object[] { buttonGroupID },
					new ButtonAndGroupRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getFindByIDSQL() {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,tbl_buttongroupid,tbl_buttonid,tbl_buttonenabledstatus,tbl_buttondisplaystatus,tbl_buttondisplayorder,comm_status");
		sql.append(" from sys_buttonandgroup where id = ?");
		return sql.toString();
	}

	class ButtonAndGroupRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ButtonAndGroup buttonAndGroup = new ButtonAndGroup();
			buttonAndGroup.setId(rs.getLong("id"));
			buttonAndGroup.setButtonGroupID(rs.getLong("tbl_buttonGroupID"));
			buttonAndGroup.setButtonID(rs.getLong("tbl_buttonID"));
			if (buttonAndGroup.getButtonID() != null
					&& !buttonAndGroup.getButtonID().equals("")) {
				String buttonName = (String)jdbcTemplateExtend
						.queryForObject(
								"select b.tbl_buttonName as tbl_buttonName from sys_button b where b.id = ?",
								new Object[] { buttonAndGroup.getButtonID() },
								new ButtonNameMapper());
				buttonAndGroup.setButtonName(buttonName);
			}
			buttonAndGroup.setButtonEnabledStatus(rs
					.getLong("tbl_buttonEnabledStatus"));
			String buttonEnabledStatusName = rs.getString("tbl_buttonEnabledStatus");
			buttonAndGroup.setButtonEnabledStatusName(buttonEnabledStatusName);
			buttonAndGroup.setButtonDisplayOrder(rs
					.getLong("tbl_buttonDisplayOrder"));
			buttonAndGroup.setButtonDisplayStatus(rs
					.getLong("tbl_buttonDisplayStatus"));
			String buttonDisplayStatusName = rs.getString("tbl_buttonDisplayStatus");
			buttonAndGroup.setButtonDisplayStatusName(buttonDisplayStatusName);
			buttonAndGroup.setStatus(rs.getLong("comm_status"));

			return buttonAndGroup;
		}
	}

	class ButtonNameMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			return rs.getString("tbl_buttonName");
		}
	}

	class TotalCountMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			return rs.getInt("totalCount");
		}
	}

	public List<ButtonAndGroup> selectByButtonGroupId(String buttonGroupId) {
		String buttonSql = "select a.*,b.*,b.id as tbl_bid,b.comm_status as tbl_bstatus,a.comm_status as tbl_astatus,a.id as tbl_aid from sys_buttonandgroup a left join sys_button b on a.tbl_buttonid=b.id where a.tbl_buttongroupid=? order by a.tbl_buttondisplayorder asc";
		Object[] args = new Object[] { buttonGroupId };
		return this.jdbcTemplateExtend.query(buttonSql, args,
				new ButtonAndGroupExtendRowMapper());
	}

	class ButtonAndGroupExtendRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Button button = new Button();
			button.setId(rs.getLong("tbl_bid"));
			button.setButtonType(rs.getLong("tbl_buttonType"));
			button.setButtonBM(rs.getString("tbl_buttonBM"));
			button.setButtonName(rs.getString("tbl_buttonName"));
			button.setButtonWidth(rs.getLong("tbl_buttonWidth"));
			button.setButtonHeight(rs.getLong("tbl_buttonHeight"));
			button.setButtonNameFontStyle(rs.getString("tbl_buttonNameFontStyle"));
			button.setButtonNameFontSize(rs.getLong("tbl_buttonNameFontSize"));
			button.setButtonNameFontColor(rs.getString("tbl_buttonNameFontColor"));
			button.setButtonBackGroundColor(rs
					.getString("tbl_buttonBackGroundColor"));
			button.setButtonBackGroundImage(rs
					.getString("tbl_buttonBackGroundImage"));
			button.setButtonBorderSize(rs.getLong("tbl_buttonBorderSize"));
			button.setButtonBorderColor(rs.getString("tbl_buttonBorderColor"));
			button.setButtonStatus(rs.getLong("tbl_buttonStatus"));
			button.setButtonRemarks(rs.getString("tbl_buttonRemarks"));
			button.setComment(rs.getString("tbl_comment"));
			button.setStatus(rs.getLong("tbl_bstatus"));
			button.setCreateBy(rs.getString("comm_createBy"));
			button.setCreateDate(rs.getTimestamp("comm_createDate"));
			button.setUpdateBy(rs.getString("comm_updateBy"));
			button.setUpdateDate(rs.getTimestamp("comm_updateDate"));

			ButtonAndGroup buttonAndGroup = new ButtonAndGroup();
			buttonAndGroup.setId(rs.getLong("tbl_aid"));
			buttonAndGroup.setButtonGroupID(rs.getLong("tbl_buttonGroupID"));
			buttonAndGroup.setButtonID(rs.getLong("tbl_buttonID"));
			if (buttonAndGroup.getButtonID() != null
					&& !buttonAndGroup.getButtonID().equals("")) {
				String buttonName = (String)jdbcTemplateExtend
						.queryForObject(
								"select b.tbl_buttonName as tbl_buttonName from sys_button b where b.id = ?",
								new Object[] { buttonAndGroup.getButtonID() },
								new ButtonNameMapper());
				buttonAndGroup.setButtonName(buttonName);
			}
			buttonAndGroup.setButtonEnabledStatus(rs
					.getLong("tbl_buttonEnabledStatus"));
			String buttonEnabledStatusName = rs.getString("tbl_buttonEnabledStatus");
			buttonAndGroup.setButtonEnabledStatusName(buttonEnabledStatusName);
			buttonAndGroup.setButtonDisplayOrder(rs
					.getLong("tbl_buttonDisplayOrder"));
			buttonAndGroup.setButtonDisplayStatus(rs
					.getLong("tbl_buttonDisplayStatus"));
			String buttonDisplayStatusName = rs.getString("tbl_buttonDisplayStatus");
			buttonAndGroup.setButtonDisplayStatusName(buttonDisplayStatusName);
			buttonAndGroup.setStatus(rs.getLong("tbl_astatus"));
			buttonAndGroup.setButton(button);
			return buttonAndGroup;
		}
	}

	@Override
	public int countButton(Long buttonGroupId) {
		try {
			StringBuffer buffer = new StringBuffer("select count(*) as totalCount from sys_buttonAndGroup where tbl_buttonGroupID = ?");
			Object[] args = new Object[]{buttonGroupId};
			return (Integer)jdbcTemplateExtend.queryForObject(buffer.toString(), args,
					new TotalCountMapper());
		} catch (Exception e) {
			logger.info(this.getClass().getName() + ":getRowCount:"	+ e.getMessage());
		}
		return 0;
	}
}
