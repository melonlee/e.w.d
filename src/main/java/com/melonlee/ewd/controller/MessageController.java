package com.melonlee.ewd.controller;

import com.melonlee.ewd.bean.MessageBean;
import com.melonlee.ewd.dao.MessageDao;
import com.melonlee.ewd.utils.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Melon on 14-4-23.
 */
@Controller
@RequestMapping("messages")
public class MessageController {

    @Resource
    private MessageDao messagesDao;


    /**
     * 提交默认推送文本信息
     *
     * @param model
     * @param push_text_content
     * @return
     */
    @RequestMapping(value = "defText", method = {RequestMethod.POST})
    public String defaultText(ModelMap model, @RequestParam(value = "msgID", required = false) int msgID, @RequestParam(value = "push_text_content", required = false) String push_text_content,
                              @RequestParam(value = "accountID", required = false) int accountID) {

        MessageBean message = new MessageBean();
        message.setTitle(push_text_content);
        message.setIsDefault(1);
        message.setType(1); //文本
        message.setCreateDate(DateFormatUtils.getNow());
        message.setAccountID(accountID);
        message.setId(msgID);

        int status =  0 ;

        if(msgID>0){
            status = messagesDao.updateDefaultText(message);
        }else{
            status =  messagesDao.saveDefaultText(message);
        }


        model.addAttribute("status", status);
        return "index";
    }

    /**
     *
     * 提交默认的富文本反馈内容
     * @param request
     * @param model
     * @param title
     * @param url
     * @return
     */
    @RequestMapping(value = "defNews", method = {RequestMethod.POST})
    public String defNews(HttpServletRequest request,  ModelMap model, @RequestParam(value = "msgID", required = false) int msgID, @RequestParam(value = "title", required = false) String title,
                          @RequestParam(value = "url", required = false) String url,@RequestParam(value = "accountID", required = false) int accountID) {

        String filePath = request.getSession().getServletContext().getRealPath("/") + "source/userdatas/";

        String imageUrl = "";

        try {

            if (request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile file = multipartRequest.getFiles("imageUrl").get(0);
                long size = file.getSize();
                byte[] data = new byte[(int) size];
                InputStream input = file.getInputStream();
                input.read(data);

                imageUrl = UUID.randomUUID().toString() + "."
                        + file.getOriginalFilename().split("\\.")[1];

                File outFile = new File(filePath + File.separator + imageUrl);
                if (!outFile.exists()) {
                    outFile.createNewFile();
                }
                FileOutputStream outStream = new FileOutputStream(outFile);

                outStream.write(data);
                outStream.close();
                input.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        MessageBean message = new MessageBean();
        message.setId(msgID);
        message.setTitle(title);
        message.setIsDefault(1);
        message.setType(2);
        message.setUrl(url);
        message.setImageUrl(imageUrl);
        message.setAccountID(accountID);
        message.setCreateDate(DateFormatUtils.getNow());


        int status = 0 ;

        if(msgID>0){
            status = messagesDao.updateDefaultNews(message);
        }else{
            status = messagesDao.saveDefaultNews(message);
        }

        model.addAttribute("status", status);
        return "index";
    }





}
