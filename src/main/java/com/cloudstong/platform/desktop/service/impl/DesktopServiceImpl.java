package com.cloudstong.platform.desktop.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.dialect.Dialect;
import com.cloudstong.platform.desktop.service.DesktopService;
import com.cloudstong.platform.email.model.InboxMessage;
import com.cloudstong.platform.message.model.ReceivedMessage;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.system.model.SysUser;

@Service("desktopService")
public class DesktopServiceImpl implements DesktopService {

	private final static int FETCH_SIZE = 10;// 默认查询10条记录

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	
	@Resource
	private Dialect dialect;

	@Override
	@Cacheable(value = "desktopCache")
	public List<SysOrg> loadOrgs() {
		String sql = "select o1.id,o1.tbl_name,o1.tbl_bumenzhineng,o2.tbl_name as parentName from sys_org o1 left join sys_org o2 on o1.tbl_parentId = o2.id where o1.id!=1 order by o1.id ASC";
		return jdbcTemplate.query(dialect.topN(sql,FETCH_SIZE), new RowMapper<SysOrg>() {
			@Override
			public SysOrg mapRow(ResultSet rs, int rowNum) throws SQLException {
				SysOrg o = new SysOrg();
				o.setId(rs.getLong("id"));
				o.setOrgName(rs.getString("tbl_name"));
				o.setOrgFunction(rs.getString("tbl_bumenzhineng"));
				o.setParentName(rs.getString("parentName"));
				return o;
			}
		});
	}

	@Override
	@Cacheable(value = "desktopCache")
	public List<List<String>> loadNews() {
		String sql = "select id ,tbl_biaoti from bus_xinxifabu where tbl_fenlei = '0' order by comm_updateDate DESC";
		return jdbcTemplate.query(dialect.topN(sql,FETCH_SIZE), new RowMapper<List<String>>() {

			@Override
			public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<String> r = new ArrayList<String>();
//				r.add(String.valueOf(rs.getLong("id")));
				r.add(rs.getString("tbl_biaoti"));
				return r;
			}
		});
	}

	@Override
	@Cacheable(value = "desktopCache")
	public List<List<String>> loadAnnouncements() {
		String sql = "select id ,tbl_biaoti from bus_xinxifabu where tbl_fenlei = '1' order by comm_updateDate DESC";
		return jdbcTemplate.query(dialect.topN(sql,FETCH_SIZE), new RowMapper<List<String>>() {

			@Override
			public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<String> r = new ArrayList<String>();
//				r.add(String.valueOf(rs.getLong("id")));
				r.add(rs.getString("tbl_biaoti"));
				return r;
			}
		});
	}

	@Override
	public List<InboxMessage> loadInbox(SysUser user) {

		String sql = "select id,tbl_subject,tbl_userid,tbl_useremail from sys_inbox where tbl_userid  = ? and tbl_useremail in ( select * from ( select  tbl_address from sys_mailaccount where tbl_userid = ?  order by tbl_deflt desc) r) order by tbl_date desc";
		return jdbcTemplate.query(dialect.topN(sql,FETCH_SIZE), new Object[] { user.getId(), user.getId() }, new RowMapper<InboxMessage>() {

			@Override
			public InboxMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
				InboxMessage im = new InboxMessage();
				im.setId(rs.getLong("id"));
				im.setSubject(rs.getString("tbl_subject"));
				return im;
			}
		});
	}

	@Override
	public List<ReceivedMessage> loadMessages(final SysUser user) {

		String sql = "select tbl_subject from sys_receivedmessage where tbl_receiver = ? order by tbl_date desc";
		return jdbcTemplate.query(dialect.topN(sql,FETCH_SIZE), new Object[] { user.getId() }, new RowMapper<ReceivedMessage>() {

			@Override
			public ReceivedMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReceivedMessage m = new ReceivedMessage();
				m.setSubject(rs.getString("tbl_subject"));
				return m;
			}
		});
	}
}
