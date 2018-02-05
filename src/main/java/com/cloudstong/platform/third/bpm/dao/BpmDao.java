package com.cloudstong.platform.third.bpm.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

@Repository
public class BpmDao
{
  private Logger logger = LoggerFactory.getLogger(BpmDao.class);

  @Resource
  protected JdbcTemplate jdbcTemplate;

  public String getDefXmlByDeployId(String deployId)
  {
    String sql = "select a.* from ACT_GE_BYTEARRAY a where NAME_ LIKE '%bpmn20.xml' and DEPLOYMENT_ID_= ? ";

    final LobHandler lobHandler = new DefaultLobHandler();

    final ByteArrayOutputStream contentOs = new ByteArrayOutputStream();
    String defXml = null;
    try
    {
      jdbcTemplate.query(sql, new Object[] { deployId }, new AbstractLobStreamingResultSetExtractor()
      {
        public void streamData(ResultSet rs) throws SQLException, IOException
        {
          FileCopyUtils.copy(lobHandler.getBlobAsBinaryStream(rs, "BYTES_"), contentOs);
        }
      });
      defXml = new String(contentOs.toByteArray(), "UTF-8");
    }
    catch (Exception ex) {
      logger.debug(ex.getMessage());
    }
    return defXml;
  }

  public void wirteDefXml(final String deployId, String defXml)
  {
    try
    {
      LobHandler lobHandler = new DefaultLobHandler();
      final byte[] btyesXml = defXml.getBytes("UTF-8");
      String sql = "update ACT_GE_BYTEARRAY set BYTES_=? where NAME_ LIKE '%bpmn20.xml' and DEPLOYMENT_ID_= ? ";
      jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(
        lobHandler)
      {
        protected void setValues(PreparedStatement ps, LobCreator lobCreator)
          throws SQLException, DataAccessException
        {
          lobCreator.setBlobAsBytes(ps, 1, btyesXml);
          ps.setString(2, deployId);
        }
      });
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}