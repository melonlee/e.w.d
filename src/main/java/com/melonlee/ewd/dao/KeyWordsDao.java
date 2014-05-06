package com.melonlee.ewd.dao;

import com.melonlee.ewd.bean.KeyWordsBean;
import com.melonlee.ewd.bean.MessageBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Melon on 14-5-5.
 */
@Repository
public class KeyWordsDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public ArrayList<KeyWordsBean> listAll() {


        final ArrayList<KeyWordsBean> list = new ArrayList<KeyWordsBean>();


        jdbcTemplate.query(
                "SELECT T_KeyWords.*,T_Account.title FROM T_KeyWords  LEFT JOIN T_Account ON T_KeyWords.accountID = T_Account.id order  by T_KeyWords.createDate desc",
                new RowCallbackHandler() {
                    @Override
                    public void processRow(ResultSet result)
                            throws SQLException {
                        KeyWordsBean bean = new KeyWordsBean();
                        bean.setId(result.getInt("T_KeyWords.id"));
                        bean.setCreateDate(result.getString("T_KeyWords.createDate"));
                        bean.setTitle(result.getString("T_KeyWords.title"));
                        bean.setAccountName(result.getString("T_Account.title"));
                        list.add(bean);
                    }
                });

        return list;
    }

    public int deleteByID(int id) {
        return jdbcTemplate.update("DELETE FROM T_KeyWords WHERE id = ?",
                new Object[]{id});
    }

    public Integer saveKeyWords(final KeyWordsBean keyWordsBean) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                String sql = "insert into T_KeyWords  (title,accountID,createDate) values (?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, keyWordsBean.getTitle());
                ps.setInt(2, keyWordsBean.getAccountID());
                ps.setString(3, keyWordsBean.getCreateDate());
                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void saveMessageWithKey(MessageBean message) {
        jdbcTemplate
                .update("INSERT INTO T_Message (title,type,isDefault,createDate,url,imageUrl,accountID,keyID) VALUES(?,?,?,?,?,?,?,?)",
                        new Object[]{message.getTitle(), message.getType(), message.getIsDefault(), message.getCreateDate(), message.getUrl(), message.getImageUrl(), message.getAccountID(),message.getKeyID()});

    }
}
