<%@ page import="com.melonlee.ewd.bean.KeyWordsBean" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <%@include file="static.jsp" %>
</head>
<body>

<%@include file="header.jsp" %>

<%
    ArrayList<KeyWordsBean> list = (ArrayList<KeyWordsBean>) request.getAttribute("list");
%>
<div class="container">
    <div class="starter-template">
        <div>
            <!-- Default panel contents -->
            <div class="panel-heading">
                <button class="btn btn-success" data-toggle="modal"
                        data-target="#modify" style="text-align: right"
                        onClick='getItem()'>添加关键词
                </button>
            </div>
            <hr/>
            <div class="row" id="datas">
                <%--<div id="loader" class="alert alert-danger">暂无数据...</div>--%>
                <table class="table table-hover">

                    <thead>
                    <tr>
                        <th>#</th>
                        <th>关键词</th>
                        <th>创建时间</th>
                        <th>所属账号</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for (KeyWordsBean bean : list) {
                    %>
                    <tr>
                        <td><%=bean.getId()%>
                        </td>
                        <td><%=bean.getTitle()%>
                        </td>
                        <td><%=bean.getCreateDate()%>
                        </td>
                        <td>@<%=bean.getAccountName()%>
                        </td>
                        <td><a data-toggle='modal' data-target='#modify' role='button' class='btn btn-warning'
                               onClick='getItem()'>修改</a> &nbsp;<a data-toggle='modal' data-target='#delete'
                                                                   role='button'
                                                                   class='btn btn-danger'
                                                                   onclick="cacheItem(<%=bean.getId()%>)">删除</a>
                        </td>
                    </tr>

                    <%

                        }
                    %>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- Modal  delete -->
<div class="modal fade" id="delete" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">删除提示</h4>
            </div>
            <div class="modal-body">
                <h4>您确定删除该项目信息？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary"
                        onClick="deleteItem()" data-dismiss="modal">确定
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>


<!-- Modal -->
<div class="modal fade" id="modify" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">编辑关键词信息</h4>
            </div>
            <form method="post" action="../keys/modify.do"  id="keyForm" enctype="multipart/form-data" >
                <div class="modal-body">

                    <input type="hidden" id="id" name="id" value="0"/>
                    <input type="hidden" id="msgID" name="msgID" value="0"/>

                    <div class="form-group">
                        <label for="exampleInputEmail1">1.关键词</label> <input
                            type="text" name="title" id="title" class="form-control"
                            placeholder="关键词">
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="exampleInputPassword1">2.所属公众账号</label>
                                <select class="form-control" id="key_account" name="accountID">

                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="exampleInputPassword1">3.返回信息类型</label>
                                <select id="key_msgType" name="msgType" class="form-control">
                                    <option value="1">纯文本</option>
                                    <option value="2">单图文</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group" id="push_text_area">
                        <label for="exampleInputPassword1">4.文字内容</label>
                        <textarea id="key_title" name="msgTitle"
                                  class="form-control" rows="3"></textarea>
                    </div>
                    <div class="form-group" id="key_url_div">
                        <label for="exampleInputEmail1">5.内容链接</label> <input type="text"
                                                                              name="msgUrl" id="key_url"
                                                                              class="form-control"
                                                                              placeholder="内容链接 例如 :http://www.o2oapp.com">
                    </div>

                    <div class="form-group" id="key_thumb_div">
                        <label for="exampleInputEmail1">6.内容图片</label> <input type="file"
                                                                              name="msgImageUrl" id="key_imageUrl">
                        <img src="" id="msg_thumb" >
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">提交</button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>


<!-- /.modal -->
<!-- /.container -->
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="../source/js/jquery.min.js"></script>
<script src="../source/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../source/js/key.js"></script>
</body>
</html>
