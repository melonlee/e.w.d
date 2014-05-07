<%@ page language="java" pageEncoding="UTF-8" %><!DOCTYPE html><html lang="zh-cn"><head>    <%@include file="static.jsp" %></head><body><%@include file="header.jsp" %><div class="container">    <div class="starter-template">        <div>            <!-- Default panel contents -->            <div class="panel-heading">                <button class="btn btn-success" data-toggle="modal"                        data-target="#myModal" style="text-align: right"                        onClick="addAccount()">添加公众账号                </button>            </div>            <hr/>            <div class="row" id="datas">                <div id="loader" class="alert alert-danger">暂无数据...</div>            </div>        </div>    </div></div><!-- Modal  delete --><div class="modal fade" id="delete" tabindex="-1" role="dialog"     aria-labelledby="myModalLabel" aria-hidden="true">    <div class="modal-dialog">        <div class="modal-content">            <div class="modal-header">                <button type="button" class="close" data-dismiss="modal"                        aria-hidden="true">&times;</button>                <h4 class="modal-title" id="myModalLabel">删除提示</h4>            </div>            <div class="modal-body">                <h4>您确定删除该项目信息？</h4>            </div>            <div class="modal-footer">                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>                <button type="button" class="btn btn-primary"                        onClick="deleteItem()" data-dismiss="modal">确定                </button>            </div>        </div>        <!-- /.modal-content -->    </div>    <!-- /.modal-dialog --></div><!-- Modal --><div class="modal fade" id="myModal" tabindex="-1" role="dialog"     aria-labelledby="myModalLabel" aria-hidden="true">    <div class="modal-dialog">        <div class="modal-content">            <div class="modal-header">                <button type="button" class="close" data-dismiss="modal"                        aria-hidden="true">&times;</button>                <h4 class="modal-title" id="myModalLabel">编辑公众账号</h4>            </div>            <form method="post" action="../account/modify.do">                <div class="modal-body">                    <input type="hidden" id="id" name="id" value="0"/>                    <div class="form-group">                        <label for="exampleInputEmail1">1.公众账号名称</label> <input                            type="text" name="title" id="title" class="form-control"                            placeholder="公众账号名称">                    </div>                    <div class="form-group">                        <label for="exampleInputPassword1">2.公众账号token</label> <input                            type="text" name="token" id="token" class="form-control"                            placeholder="设定公众账号token，纯数字英文组合">                    </div>                    <div class="form-group" id="devUrlDiv">                        <label for="exampleInputPassword1">3.开发者模式地址</label> <input                            type="text" name="devUrl" id="devUrl" class="form-control"                            placeholder="http://ewd.melonlee.com/account/token/{token}.do">                    </div>                </div>                <div class="modal-footer">                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>                    <button type="submit" class="btn btn-primary">提交</button>                </div>            </form>        </div>        <!-- /.modal-content -->    </div>    <!-- /.modal-dialog --></div><!-- 引入设置推送的DIV模块 --><%@include file="pushSetting.jsp" %><!-- /.modal --><!-- /.container --><!-- Bootstrap core JavaScript================================================== --><!-- Placed at the end of the document so the pages load faster --><script src="../source/js/jquery.min.js"></script><script src="../source/js/bootstrap.min.js"></script><script type="text/javascript" src="../source/js/account.js"></script></body></html>