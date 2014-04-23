package com.melonlee.ewd.dao;

import com.melonlee.ewd.bean.MessageBean;
import com.melonlee.ewd.utils.DateFormatUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by Melon on 14-4-23.
 */

@Repository
public class MessageDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public int saveDefaultText(MessageBean message) {

        return jdbcTemplate
                .update("INSERT INTO T_Message (title,type,isDefault,createDate,accountID) VALUES(?,?,?,?,?)",
                        new Object[]{message.getTitle(),message.getType(),message.getIsDefault(),message.getCreateDate(),message.getAccountID()});
    }

    public int saveDefaultNews(MessageBean message) {
        return jdbcTemplate
                .update("INSERT INTO T_Message (title,type,isDefault,createDate,url,imageUrl,accountID) VALUES(?,?,?,?,?,?,?)",
                        new Object[]{message.getTitle(),message.getType(),message.getIsDefault(),message.getCreateDate(),message.getUrl(),message.getImageUrl(),message.getAccountID()});
    }
}
