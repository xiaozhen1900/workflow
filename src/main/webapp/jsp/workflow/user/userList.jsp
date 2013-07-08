<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "流程用户管理");
%>
<%@include file="/jsp/common/header.jsp"%>
<script type="text/javascript">
<!--
	$(function(){
		$('#addUser').button({
			icons: {
				primary: 'ui-icon-plus'
			}
		}).click(function(){
			var addUrl = ctx + "/workflow/user_save.action";
			$("#addUserDialog").dialog({
				title: "增加流程用户",
				modal: true,
    			width: 600,
    			buttons: [{
    				text: '提交',
    				click: submitForm
    			}],
    			open: function() {
					$("#addUserDialog #userForm").attr("action", addUrl);
					$("#addUserDialog #id").attr("disabled", false);
    				$("input:text").each(function(){
    					$(this).attr("value","");
    				});
    				$("#id").focus();
    			},
	   			close: function() {
	   				//window.location.reload();
	   				$("input:text").each(function(){
    					$(this).attr("value","");
    				});
				},
			});
		});
		$(".update").click(function(){
			var id = $(this).attr("id");
			var url = ctx + "/workflow/user_findById.action";
			$("#addUserDialog").dialog({
				title: "更新流程用户",
				modal: true,
				width: 600,
				open: function(){
					var updateUrl = ctx + "/workflow/user_update.action";
					var json = {model:{id:id}};	
					var method = "findById";
					jQuery.httpcom.communications(url,json,method,function(data){
						var model = data.results.model;
						$("#addUserDialog #id").attr("value", model.id);
						$("#addUserDialog #id").attr("disabled", true);
						$("#addUserDialog #revision").attr("value", model.revision);
						$("#addUserDialog #password").attr("value", model.password);
						$("#addUserDialog #firstName").attr("value", model.firstName);
						$("#addUserDialog #lastName").attr("value", model.lastName);
						$("#addUserDialog #email").attr("value", model.email);
						$("#addUserDialog #userForm").attr("action", updateUrl);
					});
				},
				close: function() {
					//window.location.reload();
					$("input:text").each(function(){
    					$(this).attr("value","");
    				});
				},
				buttons: [{
    				text: '提交',
    				click: submitForm
    			}]
			});
		}); 
		$(".delete").click(function(){
			var id = $(this).attr("id");
			var url = ctx + "/workflow/user_delete.action";
			var json = {model:{id:id}};
			var method = "delete";
			jQuery.httpcom.communications(url,json,method,function(data){
				window.location.reload();
			});
		});
		
		function submitForm() {
			if(chekValidAll()) {
				$("#addUserDialog #id").attr("disabled", false);
				$('#userForm').submit();
			}
		}
		
		function chekValidAll() {
			var result = true;
			$("input:text").each(function(){
				var id = $(this).attr("id");
				result = checkValid(id);
				if(!result){
					return result;
				};
			});
			return result;
		}
		
		function checkValid(objId) {
			if (!$('#' + objId).val()) {
				$('#' + objId).focus();
				$('#' + objId + "Div").addClass("control-group error");
				//$('#processdefinitionidError').css("display","block");
				return false;
			} else {
				$('#' + objId + "Div").removeClass("error");
				return true;
			}
		}
	});
//-->
</script>
<body>
	<div style="text-align: right;padding: 2px 1em 2px">
		<button id="addUser" style="color:blue;">增加</button>
	</div>
	<div id="content">
		<table width="98%" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>序号</th>
					<th>ID</th>
					<th>Password</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>Email</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#request.page.result" var="item" status="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${item.id}</td>
						<td>${item.password}</td>
						<td>${item.firstName}</td>
						<td>${item.lastName}</td>
						<td>${item.email}</td>
						<td>
							<a class="update" href="#" id="${item.id}" style="color:blue;">编辑</a>
	                        <a class="delete" href="#" id="${item.id}" style="color:blue;">删除</a>
						</td>
					</tr>
				</s:iterator>	
			</tbody>
		</table>
	</div>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
	<%@include file="/jsp/workflow/user/userEdit.jsp" %>
</body>
</html>