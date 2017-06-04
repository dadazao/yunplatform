package com.cloudstong.platform.resource.tabulation.dao.impl;

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
import com.cloudstong.platform.resource.tabulation.dao.TabulationButtonDao;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;

/**
 * @author michael
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表按钮操作数据库接口实现类
 */
@Repository("tabulationButtonDao")
public class TabulationButtonDaoImpl implements TabulationButtonDao {
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

	@Override
	public void updateTabulationButton(TabulationButton tabulationButton) {
		String sql = "update sys_liebiaobutton set tbl_buttonid=?,tbl_buttontype=?,tbl_tabulation=?,tbl_formid=?,tbl_showorder=?,tbl_showname=?,tbl_funcname=?,tbl_comment=? where id=?";
		Object[] params = new Object[] { tabulationButton.getButtonId(),
				tabulationButton.getButtonType(), tabulationButton.getListId(),
				tabulationButton.getFormId(),tabulationButton.getShowOrder(),
				tabulationButton.getShowName(),tabulationButton.getFuncName(),
				tabulationButton.getFcomment(),tabulationButton.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	public Long insertTabulationButton(TabulationButton tabulationButton) {
		Long id = UniqueIdUtil.genId();
		String sql = "insert into sys_liebiaobutton(id,tbl_buttonid,tbl_buttontype,tbl_tabulation,tbl_formid,tbl_showorder,tbl_showname,tbl_funcname,tbl_comment) values (?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[] { id,tabulationButton.getButtonId(),
				tabulationButton.getButtonType(), tabulationButton.getListId(),
				tabulationButton.getFormId(), tabulationButton.getShowOrder(),
				tabulationButton.getShowName(),tabulationButton.getFuncName(),
				tabulationButton.getFcomment()};
		jdbcTemplate.update(sql,params);
		return id;
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public List selectTabulationButton(String where, Object[] args,
			int startRow, int rowsCount) {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplate.query(where, args,
					new TabulationButtonRowMapper());
		} else {
			retList = this.jdbcTemplate.querySP(where, args, startRow,
					rowsCount, new TabulationButtonRowMapper());
		}
		return retList;
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public int countTabulationButton(String sql, Object[] array) {
		List counts = jdbcTemplate.query(sql, array, new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	class TabulationButtonRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			TabulationButton t = new TabulationButton();
			t.setId(rs.getLong("id"));
			t.setButtonId(rs.getLong("tbl_buttonid"));
			if (rs.getString("tbl_buttonname") == null
					|| rs.getString("tbl_buttonname").equals("")) {
				t.setButtonName(rs.getString("tbl_buttongroupname"));
				t.setComment(rs.getString("tbl_buttongroupcomment"));
			} else {
				t.setButtonName(rs.getString("tbl_buttonname"));
				t.setComment(rs.getString("tbl_buttoncomment"));
			}
			t.setButtonType(rs.getString("tbl_buttontype"));
			t.setListId(rs.getLong("tbl_tabulation"));
			t.setCode(rs.getString("tbl_bianma"));
			t.setFormId(rs.getString("tbl_formid"));
			t.setFormName(rs.getString("tbl_formname"));
			t.setShowOrder(rs.getInt("tbl_showorder"));
			t.setShowName(rs.getString("tbl_showname"));
			t.setFuncName(rs.getString("tbl_funcname"));
			t.setFcomment(rs.getString("tbl_comment"));
			t.setUrl(rs.getString("tbl_url"));
			
			return t;
		}
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			int ret = rs.getInt(1);
			return ret;
		}
	}

	@Override
	public TabulationButton selectTabulationButtonById(Long id) {
		TabulationButton t = null;
		try {
			String sql = "select l.*,b.tbl_url as tbl_url,b.tbl_buttonName from sys_liebiaobutton l left join sys_button b on l.tbl_buttonid=b.id where l.id=?";
			t = new TabulationButton();
			final Object[] params = new Object[] { id };
			queryTabulationButton(sql, params, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	private void queryTabulationButton(String sql, Object[] params,
			final TabulationButton t) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				t.setId(rs.getLong("id"));
				t.setButtonId(rs.getLong("tbl_buttonid"));
				t.setButtonType(rs.getString("tbl_buttontype"));
				t.setListId(rs.getLong("tbl_tabulation"));
				t.setFormId(rs.getString("tbl_formid"));
				t.setCode(rs.getString("tbl_bianma"));
				t.setShowOrder(rs.getInt("tbl_showorder"));
				t.setShowName(rs.getString("tbl_showname"));
				t.setFuncName(rs.getString("tbl_funcname"));
				t.setFcomment(rs.getString("tbl_comment"));
				t.setUrl(rs.getString("tbl_url"));
				t.setButtonName(rs.getString("tbl_buttonName"));
			}
		});
	}

	@Override
	public void deleteTabulationButton(Long id) {
		String sql = "delete from sys_liebiaobutton where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@Cacheable(value = "tabulationCache", key = "'selectByTabulationId:'+#tabulationId")
	public List<TabulationButton> selectByTabulationId(Long tabulationId) {
		String sql = "select g.tbl_comment as tbl_buttongroupcomment,t.tbl_comment as tbl_buttoncomment, l.*,t.tbl_buttonName as tbl_buttonname,t.tbl_url as tbl_url,g.tbl_buttonGroupName as tbl_buttongroupname,b.tbl_biaodanming as tbl_formname from sys_liebiaobutton l left join sys_button t " +
				"on l.tbl_buttonid = t.id left join sys_buttongroup g on l.tbl_buttonid = g.id left join sys_biaodan b on l.tbl_formid = b.id  where l.tbl_tabulation=? order by l.tbl_showorder asc";
		Object[] params = new Object[] { tabulationId };
		List<TabulationButton> tbs = jdbcTemplate.query(sql, params,
				new TabulationButtonRowMapper());
		for (int i = 0; tbs != null && i < tbs.size(); i++) {
			TabulationButton tb = tbs.get(i);
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
	public TabulationOpt selectTabulationOptById(Long id) {
		TabulationOpt t = null;
		try {
			String sql = "select o.*,b.tbl_url as tbl_url,b.tbl_buttonName from sys_oprtbutton o left join sys_button b on o.tbl_buttonid = b.id where o.id=?";
			t = new TabulationOpt();
			final Object[] params = new Object[] { id };
			queryTabulationOpt(sql, params, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	private void queryTabulationOpt(String sql, Object[] params,
			final TabulationOpt t) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				t.setId(rs.getLong("id"));
				t.setButtonId(rs.getLong("tbl_buttonid"));
				t.setTabulationId(rs.getLong("tbl_tabulation"));
				t.setCode(rs.getString("tbl_bianma"));
				t.setName(rs.getString("tbl_oprtname"));
				t.setOrder(rs.getInt("tbl_shunxu"));
				t.setShowName(rs.getString("tbl_showname"));
				t.setUrl(rs.getString("tbl_url"));
				t.setButtonName(rs.getString("tbl_buttonName"));
			}
		});
	}

	@Override
	public List findReplyResult(TabulationButton tabulationButton) {
		String sql="select * from sys_liebiaobutton where tbl_buttonid = ? and tbl_tabulation = ?";
		Object[]objs = new Object[]{tabulationButton.getButtonId(),tabulationButton.getListId()};
		if(tabulationButton.getId()!=null&&!tabulationButton.getId().equals("")){
			sql="select * from sys_liebiaobutton where tbl_buttonid = ? and id <> ? and tbl_tabulation = ?";
			objs=new Object[]{tabulationButton.getButtonId(),tabulationButton.getId(),tabulationButton.getListId()};
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
	public List findReplyOptResult(TabulationOpt tabulationOpt) {
		String sql="select * from sys_oprtbutton where tbl_buttonid = ? and tbl_tabulation = ?";
		Object[]objs = new Object[]{tabulationOpt.getButtonId(),tabulationOpt.getTabulationId()};
		if(tabulationOpt.getId()!=null&&!tabulationOpt.getId().equals("")){
			sql="select * from sys_oprtbutton where tbl_buttonid = ? and id <> ? and tbl_tabulation = ?";
			objs=new Object[]{tabulationOpt.getButtonId(),tabulationOpt.getId(),tabulationOpt.getTabulationId()};
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
	public void updateButtonOrder(TabulationButton tabulationButton,Long tabulationButtonId) {
		try {
			String sql="select * from sys_liebiaobutton where tbl_tabulation = ? and tbl_showorder = ? and id <> ?";
			Object [] objs = new Object[]{tabulationButton.getListId(),tabulationButton.getShowOrder(),tabulationButtonId};
			List list=jdbcTemplate.queryForList(sql,objs);
			if(list.size()>0){
				String usql = "update sys_liebiaobutton set tbl_showorder = tbl_showorder+1 where tbl_tabulation = ? and id <> ? and tbl_showorder >= ?";
				Object [] uobjs = new Object[]{tabulationButton.getListId(),tabulationButtonId,tabulationButton.getShowOrder()};
				jdbcTemplate.update(usql,uobjs);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateOptOrder(TabulationOpt tabulationOpt, Long tabulationOptId) {
		try {
			String sql="select * from sys_oprtbutton where tbl_tabulation = ? and tbl_shunxu = ? and id <> ?";
			Object [] objs = new Object[]{tabulationOpt.getTabulationId(),tabulationOpt.getOrder(),tabulationOptId};
			List list=jdbcTemplate.queryForList(sql,objs);
			if(list.size()>0){
				String usql = "update sys_oprtbutton set tbl_shunxu = tbl_shunxu+1 where tbl_tabulation = ? and id <> ? and tbl_shunxu >= ?";
				Object [] uobjs = new Object[]{tabulationOpt.getTabulationId(),tabulationOptId,tabulationOpt.getOrder()};
				jdbcTemplate.update(usql,uobjs);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTabulationButtonByTabulationId(Long id) {
		String sql = "delete from sys_liebiaobutton where tbl_tabulation=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}
}
