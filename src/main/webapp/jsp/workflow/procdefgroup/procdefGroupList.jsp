<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "流程启动用户组管理");
%>
<%@include file="/jsp/common/header.jsp"%>
<script type="text/javascript">
<!--
	$(function(){
		$('#addProcdefGroup').button({
			icons: {
				primary: 'ui-icon-plus'
			}
		}).click(function(){
			var addUrl = ctx + "/workflow/procdefGroup_save.action";
			$("#addProcdefGroupDialog").dialog({
				title: "增加流程启动用户组",
				modal: true,
    			width: 600,
    			buttons: [{
    				text: '提交',
    				click: submitForm
    			}],
    			open: function() {
    				$("#addProcdefGroupDialog #userFrom").attr("action", addUrl);
    				$("#addProcdefGroupDialog #id").attr("value", 1);
    				$("input:text").each(function(){
    					$(this).attr("value","");
    				});
    			},
	   			close: function() {
	   				//window.location.reload();
				},
			});
		});
		$(".update").click(function(){
			var id = $(this).attr("id");
			var url = ctx + "/workflow/procdefGroup_findById.action";
			$("#addProcdefGroupDialog").dialog({
				title: "更新流程启动用户组",
				modal: true,
				width: 600,
				open: function(){
					var updateUrl = ctx + "/workflow/procdefGroup_update.action";
					var json = {model:{id:id}};					
					var method = "findById";
					jQuery.httpcom.communications(url,json,method,function(data){
						var model = data.results.model;
						$("#addProcdefGroupDialog #id").attr("value", model.id);
						$("#addProcdefGroupDialog #processdefinitionid").attr("value", model.processdefinitionid);
						$("#addProcdefGroupDialog #groupid").attr("value", model.groupid);
						$("#addProcdefGroupDialog #procdefGroupForm").attr("action", updateUrl);
					});
				},
				close: function() {
					//window.location.reload();
				},
				buttons: [{
    				text: '提交',
    				click: submitForm
    			}]
			});
			
		}); 
		$(".delete").click(function(){
			var id = $(this).attr("id");
			var url = ctx + "/workflow/procdefGroup_delete.action";
			var json = {model:{id:id}};
			var method = "delete";
			jQuery.httpcom.communications(url,json,method,function(data){
				window.location.reload();
			});
		});
		
		function submitForm() {
            if(chekValidAll()) {
				$('#procdefGroupForm').submit();
            };
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
		<button id="addProcdefGroup" style="color:blue;">增加</button>
	</div>
	<div id="content">
		<table width="98%" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>序号</th>
					<th>ID</th>
					<th>流程名</th>
					<th>用户组</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#request.page.result" var="item" status="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${item.id}</td>
						<td>${item.processDefinitionName}</td>
						<td>${item.groupName}</td>
						<td>
							<a class="update" href="#" id="${item.id}" style="color:blue;">更新</a>
	                        <a class="delete" href="#" id="${item.id}" style="color:blue;">删除</a>
						</td>
					</tr>
				</s:iterator>	
			</tbody>
		</table>
	</div>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
	<%@include file="/jsp/workflow/procdefgroup/procdefGroupEdit.jsp" %>
</body>
</html>