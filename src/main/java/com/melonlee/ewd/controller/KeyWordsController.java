package com.melonlee.ewd.controller;

import com.melonlee.ewd.bean.KeyWordsBean;
import com.melonlee.ewd.bean.MessageBean;
import com.melonlee.ewd.dao.KeyWordsDao;
import com.melonlee.ewd.utils.DateFormatUtils;
import com.melonlee.ewd.utils.ResultKit;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Melon on 14-5-5.
 */

@Controller
@RequestMapping("keys")
public class KeyWordsController {

    @Resource
    private KeyWordsDao keyWordsDao;

    //获取全部关键词
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(ModelMap model) {

        ArrayList<KeyWordsBean> list = keyWordsDao.listAll();

        model.addAttribute("list", list);

        return "keyWords";
    }


    @ResponseBody
    @RequestMapping(value = "delete", method = {RequestMethod.GET})
    public String delete(@RequestParam(value = "id", required = false) int id,
                         HttpServletRequest request) throws IOException, DocumentException {

        int status = keyWordsDao.deleteByID(id);

        return status > 0 ? ResultKit.success() : ResultKit.fail();
    }

    /**
     * 提交一个关键词内容
     *
     * 首先保存一个Message,成功后保存KeyWords
     *
     * @param request
     * @param model
     * @param id
     * @param title
     * @param msgID
     * @param msgTitle
     * @param msgUrl
     * @param msgType
     * @param accountID
     * @return
     */
    @RequestMapping(value = "modify", method = {RequestMethod.POST})
    public String modify(HttpServletRequest request, ModelMap model, @RequestParam(value = "id", required = false) int id, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "msgID", required = false) int msgID, @RequestParam(value = "msgTitle", required = false) String msgTitle,
                          @RequestParam(value = "msgUrl", required = false) String msgUrl,@RequestParam(value = "msgType", required = false) int msgType, @RequestParam(value = "accountID", required = false) int accountID) {


        //save keywords retuen keyID then save messageBean
        KeyWordsBean keyWordsBean = new KeyWordsBean();
        keyWordsBean.setTitle(title);
        keyWordsBean.setCreateDate(DateFormatUtils.getNow());
        keyWordsBean.setAccountID(accountID);
        keyWordsBean.setId(id);

        Integer keyID = keyWordsDao.saveKeyWords(keyWordsBean);
        if(null!=keyID){

            String filePath = request.getSession().getServletContext().getRealPath("/") + "source/userdatas/";

            String imageUrl = "";

            try {

                if (request instanceof MultipartHttpServletRequest) {
                    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                    MultipartFile file = multipartRequest.getFiles("msgImageUrl").get(0);
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


            MessageBean msg = new MessageBean();
            msg.setTitle(msgTitle);
            msg.setId(msgID);
            msg.setCreateDate(DateFormatUtils.getNow());
            msg.setImageUrl(imageUrl);
            msg.setUrl(msgUrl);
            msg.setAccountID(accountID);
            msg.setIsDefault(0);
            msg.setType(msgType);
            msg.setKeyID(keyID);
            keyWordsDao.saveMessageWithKey(msg);
        }

        return  list(model);
    }

}
