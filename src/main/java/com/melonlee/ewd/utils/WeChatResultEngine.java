package com.melonlee.ewd.utils;import java.io.IOException;import java.io.InputStream;import java.io.UnsupportedEncodingException;import java.util.ArrayList;import javax.servlet.http.HttpServletRequest;import com.melonlee.ewd.bean.MessageBean;import com.melonlee.ewd.bean.WeChatResponseBean;import org.dom4j.Document;import org.dom4j.DocumentException;import org.dom4j.DocumentHelper;import org.dom4j.Element;public class WeChatResultEngine {	// 获取返回的数据结构	public static WeChatResponseBean getResponse(HttpServletRequest request) {		WeChatResponseBean bean = null;		InputStream is = null;		try {			is = request.getInputStream();		} catch (IOException e) {		}		// 取HTTP请求流长度		int size = request.getContentLength();		// 用于缓存每次读取的数据		byte[] buffer = new byte[size];		// 用于存放结果的数组		byte[] xmldataByte = new byte[size];		int count = 0;		int rbyte = 0;		// 循环读取		while (count < size) {			// 每次实际读取长度存于rbyte中			try {				rbyte = is.read(buffer);			} catch (IOException e) {				return bean;			}			for (int i = 0; i < rbyte; i++) {				xmldataByte[count + i] = buffer[i];			}			count += rbyte;		}		try {			is.close();		} catch (IOException e) {			return bean;		}		String requestStr = null;		try {			requestStr = new String(xmldataByte, "UTF-8");		} catch (UnsupportedEncodingException e) {			return bean;		}		Document doc = null;		try {			doc = DocumentHelper.parseText(requestStr);		} catch (DocumentException e) {			return bean;		}		Element rootElt = doc.getRootElement();		bean = new WeChatResponseBean();		bean.setContent(rootElt.elementText("Content"));		bean.setToUserName(rootElt.elementText("ToUserName"));		bean.setFromUserName(rootElt.elementText("FromUserName"));		return bean;	}	// 生成纯文本信息进行返回	public static String getText(String messageText,			WeChatResponseBean weChatResponse) {		String responseStr = "<xml>";		responseStr += "<ToUserName><![CDATA["				+ weChatResponse.getFromUserName() + "]]></ToUserName>";		responseStr += "<FromUserName><![CDATA["				+ weChatResponse.getToUserName() + "]]></FromUserName>";		responseStr += "<CreateTime>" + System.currentTimeMillis()				+ "</CreateTime>";		responseStr += "<MsgType><![CDATA[text]]></MsgType>";		responseStr += "<Content>" + messageText + "</Content>";		responseStr += "<FuncFlag>0</FuncFlag>";		responseStr += "</xml>";		return responseStr;	}	// 返回单图文信息	public static String getSingleNews(MessageBean messageBean,			WeChatResponseBean weChatResponse) {		String responseStr = "<xml>";		responseStr += "<ToUserName><![CDATA["				+ weChatResponse.getFromUserName() + "]]></ToUserName>";		responseStr += "<FromUserName><![CDATA["				+ weChatResponse.getToUserName() + "]]></FromUserName>";		responseStr += "<CreateTime>" + System.currentTimeMillis()				+ "</CreateTime>";		responseStr += "<MsgType><![CDATA[news]]></MsgType>";		responseStr += "<Content><![CDATA[]]></Content>";		responseStr += "<ArticleCount>1</ArticleCount>";		responseStr += "<Articles>";		responseStr += "<item>";		responseStr += "<Title><![CDATA[" + messageBean.getTitle()				+ "]]></Title>";		responseStr += "<Discription><![CDATA[DESC]]></Discription>";		responseStr += "<PicUrl><![CDATA[" + messageBean.getImageUrl()				+ "]]></PicUrl>";		responseStr += "<Url><![CDATA[" + messageBean.getUrl() + "]]></Url>";		responseStr += "</item>";		responseStr += "</Articles>";		responseStr += "<FuncFlag>1</FuncFlag>";		responseStr += "</xml>";		return responseStr;	}	// 返回多图文信息	public static String getNewsList(ArrayList<MessageBean> news,			WeChatResponseBean weChatResponse) {		String responseStr = "<xml>";		responseStr += "<ToUserName><![CDATA["				+ weChatResponse.getFromUserName() + "]]></ToUserName>";		responseStr += "<FromUserName><![CDATA["				+ weChatResponse.getToUserName() + "]]></FromUserName>";		responseStr += "<CreateTime>" + System.currentTimeMillis()				+ "</CreateTime>";		responseStr += "<MsgType><![CDATA[news]]></MsgType>";		responseStr += "<Content><![CDATA[]]></Content>";		responseStr += "<ArticleCount>" + news.size() + "</ArticleCount>";		responseStr += "<Articles>";		for (MessageBean message : news) {			responseStr += "<item>";			responseStr += "<Title><![CDATA[" + message.getTitle()					+ "]]></Title>";			responseStr += "<Discription><![CDATA[" + message.getDescription()					+ "]]></Discription>";			responseStr += "<PicUrl><![CDATA[" + message.getImageUrl()					+ "]]></PicUrl>";			responseStr += "<Url><![CDATA[" + message.getUrl() + "]]></Url>";			responseStr += "</item>";		}		responseStr += "</Articles>";		responseStr += "<FuncFlag>1</FuncFlag>";		responseStr += "</xml>";		return responseStr;	}}