<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "已办结任务");
%>
<%@include file="/jsp/common/header.jsp"%>
<body>
	<table width="98%" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>ID</th>
				<th>ProcessDefinitionId</th>
				<th>ProcessInstanceId</th>
				<th>Name</th>
				<th>Description</th>
				<th>DeleteReason</th>
				<th>Owner</th>
				<th>Assignee</th>
				<th>StartTime</th>
				<th>EndTime</th>
				<th>WorkTimeInMillis</th>
				<th>ClaimTime</th>
				<th>TaskDefinitionKey</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="#request.page.result" var="task" status="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${task.id}</td>
					<td>${task.processDefinitionId}</td>
					<td>${task.processInstanceId}</td>
					<td>${task.name}</td>
					<td>${task.description}</td>
					<td>${task.deleteReason}</td>
					<td>${task.owner}</td>
					<td>${task.assignee}</td>
					<td><s:date name="#task.startTime" /></td>
					<td><s:date name="#task.endTime" /></td>
					<td>${task.workTimeInMillis}</td>
					<td><s:date name="#task.claimTime" /></td>
					<td>${task.taskDefinitionKey}</td>
				</tr>
			</s:iterator>	
		</tbody>
	</table>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
</body>
</html>