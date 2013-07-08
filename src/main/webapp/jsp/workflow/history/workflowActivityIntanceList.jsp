<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "流程历史活动实例");
%>
<%@include file="/jsp/common/header.jsp"%>
<body>
	<table width="98%" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>ID</th>
				<th>ActivityId</th>
				<th>ActivityName</th>
				<th>ActivityType</th>
				<th>ProcessDefinitionId</th>
				<th>ProcessInstanceId</th>
				<th>TaskId</th>
				<th>Assignee</th>
				<th>StartTime</th>
				<th>EndTime</th>
				<th>DurationInMillis</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="#request.page.result" var="historyInstance" status="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${historyInstance.id}</td>
					<td>${historyInstance.activityId }</td>
					<td>${historyInstance.activityName}</td>
					<td>${historyInstance.activityType}</td>
					<td>${historyInstance.processDefinitionId}</td>
					<td>${historyInstance.processInstanceId}</td>
					<td>${historyInstance.taskId}</td>
					<td>${historyInstance.assignee}</td>
					<td><s:date name="#historyInstance.startTime" /></td>
					<td><s:date name="#historyInstance.endTime" /></td>
					<td>${historyInstance.durationInMillis}</td>
				</tr>
			</s:iterator>	
		</tbody>
	</table>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
</body>
</html>