<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "流程待办任务管理");
%>
<%@include file="/jsp/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/wsnJsFrameWork/plug/workflow/workflow.js">
<!--

//-->
</script>
<script type="text/javascript">
<!--
	$(function() {
		$(".handle").button({
	        icons: {
	            primary: 'ui-icon-info'
	        }
	    }).click(function() {
			var taskId = $(this).attr("taskId");
			var taskDefinitionkey = $(this).attr("tdkey");
			var json = {};
			var task = {};
			task.taskId = taskId; 
			if(taskDefinitionkey == "consignee") {
				task.isAccept = true;
			}else {
				task.isAccept = false;
			}
			json.task = task;
			var url = "${ctx}/workflow/task_complete.action";
			var method = "complete";
			jQuery.httpcom.communications(url,json,method,function(data){
				window.location.reload();
			});
		});
		$(".claim").button({
			icons: {
	            primary: 'ui-button'
	        }
	    }).click(function(){
			var taskId = $(this).attr("taskId");
			var url = "${ctx}/workflow/task_claim.action";
			var json = {};
			var task = {};
			task.taskId = taskId;
			json.task = task;
			var method = "claim";
			jQuery.httpcom.communications(url,json,method,function(){
				window.location.reload();
			});
		});
		
		$(".followTracing").button({
			icons: {
	            primary: 'ui-icon-image'
	        }
	    }).click(graphTrace);
	});
//-->
</script>
<body>
	<div class="ui-widget" style="display: none;">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
			<strong>提示：</strong><span id="message"></span></p>
		</div>
	</div>
	<div class="content">
	<table width="98%" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>ID</th>
				<th>Name</th>
				<th>创建时间</th>
				<th>流程定义名称</th>
				<th>流程定义版本</th>
				<th>流程实例ID</th>
				<th>任务状态</th>
				<th>当前处理人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="#request.page.result" var="task" status="stats">
				<tr>
					<td>${stats.index + 1}</td>
					<td>${task.id}</td>
					<td>${task.name }</td>
					<td>${task.createTime}</td>
					<td>${task.pdname}</td>
					<td>${task.pdversion}</td>
					<td>${task.pid}</td>
					<td>${task.status}</td>
					<td>${task.assignee}</td>
					<td>
						<s:if test="#task.status == 'toClaim'">
							<a class="claim" href="#" taskId="${task.id}" style="color:blue;">签收</a>
						</s:if>
						<s:if test="#task.status == 'todo'">
							<a class="handle" href="#"  taskId="${task.id}" tdkey="${task.taskDefinitionKey}" style="color:blue;">办理</a>
						</s:if>
						<a class="followTracing" href="#"  pid="${task.pid}" proDefId="${task.proDefId}" style="color:blue;">流程跟踪图</a>
					</td>
				</tr>
			</s:iterator>	
		</tbody>
	</table>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
	</div>
</body>
</html>