package com.cloudstong.platform.resource.form.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.button.dao.ButtonDao;
import com.cloudstong.platform.resource.button.model.Button;
import com.cloudstong.platform.resource.buttongroup.dao.ButtonAndGroupDao;
import com.cloudstong.platform.resource.buttongroup.dao.ButtonGroupDao;
import com.cloudstong.platform.resource.buttongroup.model.ButtonAndGroup;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;
import com.cloudstong.platform.resource.form.dao.FormButtonDao;
import com.cloudstong.platform.resource.form.model.FormButton;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:表单按钮操作数据库接口实现类
 */
@Repository("formButtonDao")
public class FormButtonDaoImpl implements FormButtonDao {
	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	@Resource
	private ButtonDao buttonDao;
	@Resource
	private ButtonGroupDao buttonGroupDao;
	@Resource
	private ButtonAndGroupDao buttonAndGroupDao;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public ButtonDao getButtonDao() {
		return buttonDao;
	}

	public void setButtonDao(ButtonDao buttonDao) {
		this.buttonDao = buttonDao;
	}

	public ButtonGroupDao getButtonGroupDao() {
		return buttonGroupDao;
	}

	public void setButtonGroupDao(ButtonGroupDao buttonGroupDao) {
		this.buttonGroupDao = buttonGroupDao;
	}

	public ButtonAndGroupDao getButtonAndGroupDao() {
		return buttonAndGroupDao;
	}

	public void setButtonAndGroupDao(ButtonAndGroupDao buttonAndGroupDao) {
		this.buttonAndGroupDao = buttonAndGroupDao;
	}

	@Override
	public void updateFormButton(FormButton formButton) {
		String sql = "update sys_biaodanbutton set tbl_buttonid=?,tbl_buttontype=?,tbl_formid=?,tbl_tabid=?,tbl_partitionid=?,tbl_showorder=?,tbl_showname=?,tbl_funcname=?,tbl_comment=? where id=?";
		Object[] params = new Object[] { formButton.getButtonId(),
				formButton.getButtonType(), formButton.getFormId(),
				formButton.getTabId(), formButton.getPartitionId(),
				formButton.getShowOrder(), formButton.getShowName(),
				formButton.getFuncName(), formButton.getFcomment(), formButton.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	public Long insertFormButton(FormButton formButton) {
		Long id = UniqueIdUtil.genId();
		String sql = "insert into sys_biaodanbutton(id,tbl_buttonid,tbl_buttontype,tbl_formid,tbl_tabid,tbl_partitionid,tbl_showorder,tbl_showname,tbl_funcname,tbl_comment) values (?,?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[] {id,formButton.getButtonId(),
				formButton.getButtonType(), formButton.getFormId(),
				formButton.getTabId(), formButton.getPartitionId(),
				formButton.getShowOrder(), formButton.getShowName(),
				formButton.getFuncName(), formButton.getFcomment()};
		jdbcTemplate.update(sql,params);
		return id;
	}

	@Override
	@Cacheable(value = "formCache")
	public List selectFormButton(String where, Object[] args, int currentIndex,
			int pageSize) {
		List retList = new ArrayList();
		if (pageSize == -1) {// 查询出所有记录
			retList = this.jdbcTemplate.query(where, args,
					new FormButtonRowMapper());
		} else {
			retList = this.jdbcTemplate.querySP(where, args, currentIndex,
					pageSize, new FormButtonRowMapper());
		}
		return retList;
	}

	@Override
	@Cacheable(value = "formCache")
	public int countFormButton(String sql, Object[] array) {
		List counts = jdbcTemplate.query(sql, array, new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	class FormButtonRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			FormButton f = new FormButton();
			f.setId(rs.getLong("id"));
			f.setButtonId(rs.getLong("tbl_buttonid"));
			if (rs.getString("tbl_buttonname") == null
					|| rs.getString("tbl_buttonname").equals("")) {
				f.setButtonName(rs.getString("tbl_buttongroupname"));
				f.setComment(rs.getString("tbl_buttongroupcomment"));
			} else {
				f.setButtonName(rs.getString("tbl_buttonname"));
				f.setComment(rs.getString("tbl_buttoncomment"));
			}
			f.setButtonType(rs.getString("tbl_buttontype"));
			f.setFormId(rs.getLong("tbl_formid"));
			f.setTabId(rs.getLong("tbl_tabid"));
			f.setTabName(rs.getString("tbl_tabname"));
			f.setCode(rs.getString("tbl_bianma"));
			f.setPartitionId(rs.getLong("tbl_partitionid"));
			f.setShowOrder(rs.getInt("tbl_showorder"));
			f.setShowName(rs.getString("tbl_showname"));
			f.setFuncName(rs.getString("tbl_funcname"));
			f.setFcomment(rs.getString("tbl_comment"));
			f.setUrl(rs.getString("tbl_url"));
			return f;
		}
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			int ret = rs.getInt(1);
			return ret;
		}
	}

	@Override
	@Cacheable(value = "formCache")
	public FormButton selectFormButtonById(Long id) {
		FormButton t = null;
		try {
			String sql = "select l.*,b.tbl_url as tbl_url,b.tbl_buttonName from sys_biaodanbutton l left join sys_button b on l.tbl_buttonid=b.id where l.id=?";
			t = new FormButton();
			final Object[] params = new Object[] { id };
			queryFormButton(sql, params, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	private void queryFormButton(String sql, Object[] params,
			final FormButton f) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				f.setId(rs.getLong("id"));
				f.setButtonId(rs.getLong("tbl_buttonid"));
				f.setButtonType(rs.getString("tbl_buttontype"));
				f.setFormId(rs.getLong("tbl_formid"));
				f.setTabId(rs.getLong("tbl_tabid"));
				f.setPartitionId(rs.getLong("tbl_partitionid"));
				f.setCode(rs.getString("tbl_bianma"));
				f.setShowOrder(rs.getInt("tbl_showorder"));
				f.setShowName(rs.getString("tbl_showname"));
				f.setFuncName(rs.getString("tbl_funcname"));
				f.setFcomment(rs.getString("tbl_comment"));
				f.setUrl(rs.getString("tbl_url"));
				f.setButtonName(rs.getString("tbl_buttonName"));
			}
		});
	}

	@Override
	public void deleteFormButton(Long id) {
		String sql = "delete from sys_biaodanbutton where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@Cacheable(value = "formCache")
	public List<FormButton> selectByFormId(Long formId) {
		String sql = "select l.*,g.tbl_comment as tbl_buttongroupcomment,t.tbl_comment as tbl_buttoncomment,t.tbl_buttonName as tbl_buttonname,t.tbl_url as tbl_url,g.tbl_buttonGroupName as tbl_buttongroupname,b.tbl_biaodanming as tbl_formname,tb.tbl_mingcheng as tbl_tabname from sys_biaodanbutton l left join sys_button t on l.tbl_buttonid = t.id left join sys_buttongroup g on l.tbl_buttonid = g.id left join sys_biaodan b on l.tbl_formid = b.id left join sys_biaodantab tb on tb.id = l.tbl_tabid where l.tbl_formid=? order by l.tbl_showorder asc";
		Object[] params = new Object[] { formId };
		List<FormButton> tbs = jdbcTemplate.query(sql, params,
				new FormButtonRowMapper());
		for (int i = 0; tbs != null && i < tbs.size(); i++) {
			FormButton tb = tbs.get(i);
			if (Constants.TYPE_BUTTON.equals(tb.getButtonType())) {
				Button button = buttonDao.findByID(tb.getButtonId());
				tb.setButton(button);
			} else {
				ButtonGroup buttonGroup = buttonGroupDao.findByID(tb.getButtonId());
				List<ButtonAndGroup> bags = buttonAndGroupDao
						.selectByButtonGroupId(String.valueOf(buttonGroup.getId()));
				buttonGroup.setButtonAndGroups(bags);
				tb.setButtonGroup(buttonGroup);
			}
		}
		return tbs;
	}

	@Override
	@Cacheable(value = "formCache")
	public List<FormButton> selectByTabId(Long tabId) {
		String sql = "select l.*,g.tbl_comment as tbl_buttongroupcomment,t.tbl_comment as tbl_buttoncomment,t.tbl_buttonName as tbl_buttonname,t.tbl_url as tbl_url,g.tbl_buttonGroupName as tbl_buttongroupname,b.tbl_biaodanming as tbl_formname,tb.tbl_mingcheng as tbl_tabname from sys_biaodanbutton l left join sys_button t on l.tbl_buttonid = t.id left join sys_buttongroup g on l.tbl_buttonid = g.id left join sys_biaodan b on l.tbl_formid = b.id left join sys_biaodantab tb on tb.id = l.tbl_tabid where l.tbl_tabid=? ";
		Object[] params = new Object[] { tabId };
		List<FormButton> tbs = jdbcTemplate.query(sql, params,
				new FormButtonRowMapper());
		for (int i = 0; tbs != null && i < tbs.size(); i++) {
			FormButton tb = tbs.get(i);
			if (Constants.TYPE_BUTTON.equals(tb.getButtonType())) {
				Button button = buttonDao.findByID(tb.getButtonId());
				tb.setButton(button);
			} else {
				ButtonGroup buttonGroup = buttonGroupDao.findByID(tb.getButtonId());
				List<ButtonAndGroup> bags = buttonAndGroupDao
						.selectByButtonGroupId(String.valueOf(buttonGroup.getId()));
				buttonGroup.setButtonAndGroups(bags);
				tb.setButtonGroup(buttonGroup);
			}
		}
		return tbs;
	}

	@Override
	@Cacheable(value = "formCache")
	public List<FormButton> selectByTabAndPid(Long tabId, Long partitionId) {
		String sql = "select l.*,g.tbl_comment as tbl_buttongroupcomment,t.tbl_comment as tbl_buttoncomment,t.tbl_buttonName as tbl_buttonname,t.tbl_url as tbl_url,g.tbl_buttonGroupName as tbl_buttongroupname,b.tbl_biaodanming as tbl_formname,tb.tbl_mingcheng as tbl_tabname from sys_biaodanbutton l left join sys_button t on l.tbl_buttonid = t.id left join sys_buttongroup g on l.tbl_buttonid = g.id left join sys_biaodan b on l.tbl_formid = b.id left join sys_biaodantab tb on tb.id = l.tbl_tabid  where l.tbl_tabid=? and l.tbl_partitionid=? order by l.tbl_showorder asc";
		Object[] params = new Object[] { tabId, partitionId };
		List<FormButton> tbs = jdbcTemplate.query(sql, params,
				new FormButtonRowMapper());
		for (int i = 0; tbs != null && i < tbs.size(); i++) {
			FormButton tb = tbs.get(i);
			if (Constants.TYPE_BUTTON.equals(tb.getButtonType())) {
				Button button = buttonDao.findByID(Long.valueOf(tb.getButtonId()));
				tb.setButton(button);
			} else {
				ButtonGroup buttonGroup = buttonGroupDao.findByID(Long.valueOf(tb.getButtonId()));
				List<ButtonAndGroup> bags = buttonAndGroupDao
						.selectByButtonGroupId(String.valueOf(buttonGroup.getId()));
				buttonGroup.setButtonAndGroups(bags);
				tb.setButtonGroup(buttonGroup);
			}
		}
		return tbs;
	}

	@Override
	@Cacheable(value = "formCache")
	public List<FormButton> selectByPid(Long partitionId) {
		String sql = "select l.*,g.tbl_comment as tbl_buttongroupcomment,t.tbl_comment as tbl_buttoncomment,t.tbl_buttonName as tbl_buttonname,t.tbl_url as tbl_url,g.tbl_buttonGroupName as tbl_buttongroupname,b.tbl_biaodanming as tbl_formname,tb.tbl_mingcheng as tbl_tabname from sys_biaodanbutton l left join sys_button t on l.tbl_buttonid = t.id left join sys_buttongroup g on l.tbl_buttonid = g.id left join sys_biaodan b on l.tbl_formid = b.id left join sys_biaodantab tb on tb.id = l.tbl_tabid  where l.tbl_partitionid=? ";
		Object[] params = new Object[] { partitionId };
		List<FormButton> tbs = jdbcTemplate.query(sql, params, new FormButtonRowMapper());
		for (int i = 0; tbs != null && i < tbs.size(); i++) {
			FormButton tb = tbs.get(i);
			if (Constants.TYPE_BUTTON.equals(tb.getButtonType())) {
				Button button = buttonDao.findByID(Long.valueOf(tb.getButtonId()));
				tb.setButton(button);
			} else {
				ButtonGroup buttonGroup = buttonGroupDao.findByID(Long.valueOf(tb
						.getButtonId()));
				List<ButtonAndGroup> bags = buttonAndGroupDao
						.selectByButtonGroupId(String.valueOf(buttonGroup.getId()));
				buttonGroup.setButtonAndGroups(bags);
				tb.setButtonGroup(buttonGroup);
			}
		}
		return tbs;
	}

	@Override
	@Cacheable(value = "formCache")
	public List<FormButton> selectByFormId(Long formId, Long tabId) {
		String sql = "select l.*,g.tbl_comment as tbl_buttongroupcomment,t.tbl_comment as tbl_buttoncomment,t.tbl_buttonName as tbl_buttonname,t.tbl_url as tbl_url,g.tbl_buttonGroupName as tbl_buttongroupname,b.tbl_biaodanming as tbl_formname,tb.tbl_mingcheng as tbl_tabname from sys_biaodanbutton l left join sys_button t on l.tbl_buttonid = t.id left join sys_buttongroup g on l.tbl_buttonid = g.id left join sys_biaodan b on l.tbl_formid = b.id left join sys_biaodantab tb on tb.id = l.tbl_tabid where l.tbl_formid=? and l.tbl_tabid=? order by l.tbl_showorder asc";
		Object[] params = new Object[] { formId, tabId };
		List<FormButton> tbs = jdbcTemplate.query(sql, params,
				new FormButtonRowMapper());
		for (int i = 0; tbs != null && i < tbs.size(); i++) {
			FormButton tb = tbs.get(i);
			if (Constants.TYPE_BUTTON.equals(tb.getButtonType())) {
				Button button = buttonDao.findByID(Long.valueOf(tb.getButtonId()));
				tb.setButton(button);
			} else {
				ButtonGroup buttonGroup = buttonGroupDao.findByID(Long.valueOf(tb.getButtonId()));
				List<ButtonAndGroup> bags = buttonAndGroupDao.selectByButtonGroupId(String.valueOf(buttonGroup.getId()));
				buttonGroup.setButtonAndGroups(bags);
				tb.setButtonGroup(buttonGroup);
			}
		}
		return tbs;
	}

	@Override
	public List findReplyResult(FormButton formButton) {
		String sql="select * from sys_biaodanbutton where tbl_buttonid = ? and tbl_formid = ?";
		Object[]objs = new Object[]{formButton.getButtonId(),formButton.getFormId()};
		if(formButton.getId()!=null&&!formButton.getId().equals("")){
			sql="select * from sys_biaodanbutton where tbl_buttonid = ? and id <> ? and tbl_formid = ?";
			objs=new Object[]{formButton.getButtonId(),formButton.getId(),formButton.getFormId()};
		}
		
		List list = null;
		try {
			list=jdbcTemplate.queryForList(sql,objs);
		} catch (Exception e) {
			list = new ArrayList();
		}
		
		return list;
	}

	@Override
	public void updateOrder(FormButton formButton) {
		try {
			String sql="select * from sys_biaodanbutton where tbl_formid = ? and tbl_tabid = ? and tbl_partitionid = ? and tbl_showorder = ? and id <> ?";
			if(formButton.getPartitionId()==null){
				sql="select * from sys_biaodanbutton where tbl_formid = ? and tbl_tabid = ? and tbl_partitionid is ? and tbl_showorder = ? and id <> ?";
			}
			Object [] objs = new Object[]{formButton.getFormId(),formButton.getTabId(),formButton.getPartitionId(),formButton.getShowOrder(),formButton.getId()};
			List list=jdbcTemplate.queryForList(sql,objs);
			if(list.size()>0){
				String usql = "update sys_biaodanbutton set tbl_showorder = tbl_showorder+1 where tbl_formid = ? and tbl_tabid = ? and tbl_partitionid = ? and id <> ? and tbl_showorder >= ?";
				if(formButton.getPartitionId()==null){
					usql = "update sys_biaodanbutton set tbl_showorder = tbl_showorder+1 where tbl_formid = ? and tbl_tabid = ? and tbl_partitionid is ? and id <> ? and tbl_showorder >= ?";
				}
				Object [] uobjs = new Object[]{formButton.getFormId(),formButton.getTabId(),formButton.getPartitionId(),formButton.getId(),formButton.getShowOrder()};
				jdbcTemplate.update(usql,uobjs);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
}
