<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "流程JOB管理");
%>
<%@include file="/jsp/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/wsnJsFrameWork/plug/workflow/workflow.js">
<!--

//-->
</script>
<script type="text/javascript">
<!--
//-->
</script>
<body>
	<table width="98%" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>ID</th>
				<th>流程实例ID</th>
				<th>流程定义ID</th>
				<th>Retries</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="#request.page.result" var="job" status="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${job.id}</td>
					<td>${job.processInstanceId}</td>
					<td>${job.processDefinitionId}</td>
					<td>${job.retries}</td>
					<td>
						<a href="#" style="color:blue;">操作</a>
					</td>
				</tr>
			</s:iterator>	
		</tbody>
	</table>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
</body>
</html>