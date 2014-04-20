package com.melonlee.ewd.controller;import com.melonlee.ewd.bean.AccountBean;import com.melonlee.ewd.bean.MessageBean;import com.melonlee.ewd.bean.WeChatResponseBean;import com.melonlee.ewd.dao.AccountDao;import com.melonlee.ewd.utils.ResultKit;import com.melonlee.ewd.utils.StaticParam;import com.melonlee.ewd.utils.WeChatResultEngine;import org.dom4j.DocumentException;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.*;import javax.annotation.Resource;import javax.servlet.http.HttpServletRequest;import java.io.IOException;import java.util.ArrayList;@Controller@RequestMapping("account")public class AccountController {    @Resource    private AccountDao accountDao;    /*     * 获取全部公众账号信息     */    @ResponseBody    @RequestMapping(value = "list", method = {RequestMethod.GET})    public String listAll(HttpServletRequest request) throws IOException,            DocumentException {        ArrayList<AccountBean> accounts = accountDao.getAll();        return ResultKit.addList(accounts);    }    /*     * 编辑公众账号     *     * id=0 ->add     *     * id>0 ->update     */    @RequestMapping(value = "modify", method = {RequestMethod.POST})    public String modify(@RequestParam(value = "id", required = false) int id,                         @RequestParam(value = "title", required = false) String title,                         @RequestParam(value = "token", required = false) String token,                         HttpServletRequest request) throws IOException, DocumentException {        AccountBean account = new AccountBean();        account.setTitle(title);        account.setToken(token);        account.setDevUrl(StaticParam.WECHAT_HOST + token + ".do");        if (id > 0) {            // update            account.setId(id);            accountDao.update(account);        } else {            // save            accountDao.save(account);        }        return "index";    }    @ResponseBody    @RequestMapping(value = "delete", method = {RequestMethod.GET})    public String delete(@RequestParam(value = "id", required = false) int id,                         HttpServletRequest request) throws IOException, DocumentException {        int status = accountDao.deleteByID(id);        return status > 0 ? ResultKit.success() : ResultKit.fail();    }    /**     * 对微信进行验证     *     * @param token     * @param request     * @return     */    @ResponseBody    @RequestMapping(value = "token/{token}", method = {RequestMethod.GET,            RequestMethod.POST})    public String get(@PathVariable("token") String token,                      HttpServletRequest request) {        String responseStr = null;        // 从数据库中获取验证状态        int validateStatus = accountDao.validateToken(token);        // 如果是第一次验证 返回echoststr        if (validateStatus > 0) {            responseStr = request.getParameter(StaticParam.WECHAT_ECHOSTR);        } else {            WeChatResponseBean weChatResponse = WeChatResultEngine                    .getResponse(request);            //验证成功后获取关键字集合 然后进行配置设定            if ("text".equals(weChatResponse.getContent())) {                responseStr = WeChatResultEngine.getText("你好", weChatResponse);            } else if ("news".equals(weChatResponse.getContent())) {                MessageBean msg = new MessageBean();                msg.setTitle("托尔斯泰");                msg.setImageUrl("http://tp4.sinaimg.cn/2002536511/180/5684892209/1");                msg.setUrl("http://www.melonlee.com");                responseStr = WeChatResultEngine.getSingleNews(msg,                        weChatResponse);            } else if ("ok".equals(weChatResponse.getContent())) {                ArrayList<MessageBean> list = new ArrayList<MessageBean>();                for (int i = 0; i < 4; i++) {                    MessageBean msg = new MessageBean();                    msg.setTitle("托尔斯泰");                    msg.setImageUrl("http://tp4.sinaimg.cn/2002536511/180/5684892209/1");                    msg.setUrl("http://www.melonlee.com");                    list.add(msg);                }                responseStr = WeChatResultEngine.getNewsList(                        list, weChatResponse);            } else {                //默认推送                responseStr = WeChatResultEngine.getText("默认推送", weChatResponse);            }        }        // 如果是已经验证过 进行回复设定        return responseStr;    }}