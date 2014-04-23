<%@ page language="java" pageEncoding="UTF-8" %><!-- 编辑推送 --><div class="modal fade" id="defaultPush" tabindex="-1" role="dialog"     aria-labelledby="myModalLabel" aria-hidden="true" id="defaultPushArea">    <div class="modal-dialog">        <div class="modal-content">            <div class="modal-header">                <button type="button" class="close" data-dismiss="modal"                        aria-hidden="true">&times;</button>                <h4 class="modal-title" id="myModalLabel">编辑推送信息</h4>            </div>            <div class="modal-body">                <!-- 头部TAB选择区域 -->                <div class="form-group">                    <ul class="nav nav-pills">                        <li id="push_text" class="active"><a id="push_text_a"                                                             href="#">文本</a></li>                        <li id="push_singleNews"><a id="push_singleNews_a" href="#">单图文</a>                        </li>                        <li id="push_newsList"><a id="push_newsList_a" href="#">多图文</a>                        </li>                    </ul>                </div>                <!-- 纯文本区域 -->                <form method="post" action="../messages/defText.do" id="defaultForm_text">                    <input type="hidden" name="accountID" id="text_accountID">                    <div class="form-group" id="push_text_area">                        <textarea id="text_content" name="push_text_content"                                  class="form-control" rows="3"></textarea>                    </div>                </form>                <form method="post" action="../messages/defNews.do" id="defaultForm_news" enctype="multipart/form-data">                    <!-- 单图文信息本区域 -->                    <div id="push_sinleNews_area">                        <input type="hidden" name="accountID" id="news_accountID">                        <div class="form-group">                            <label for="exampleInputEmail1">1.内容标题</label> <input type="text"                                                                                  name="title" id="title"                                                                                  class="form-control"                                                                                  placeholder="内容标题">                        </div>                        <div class="form-group">                            <label for="exampleInputEmail1">2.内容链接</label> <input type="text"                                                                                  name="url" id="url"                                                                                  class="form-control"                                                                                  placeholder="内容链接 例如 :http://www.o2oapp.com">                        </div>                        <div class="form-group">                            <label for="exampleInputEmail1">3.内容图片</label> <input type="file"                                                                                  name="imageUrl" id="imageUrl">                        </div>                    </div>                </form>                <!-- 多图文信息本区域 -->                <div id="push_newsList_area_loader">                    <div class="alert alert-danger">Coming soon...</div>                </div>            </div>            <div class="modal-footer">                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>                <button id="pushBtn" type="button" class="btn btn-primary">提交</button>            </div>        </div>        <!-- /.modal-content -->    </div>    <!-- /.modal-dialog --></div>