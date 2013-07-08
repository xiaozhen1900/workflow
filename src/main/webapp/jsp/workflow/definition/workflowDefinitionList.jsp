<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "流程定义管理");
%>
<%@include file="/jsp/common/header.jsp"%>
<script type="text/javascript">
<!--
$(function() {
	$('#redeploy').button({
		icons: {
			primary: 'ui-icon-refresh'
		}
	});
	$('#deploy').button({
		icons: {
			primary: 'ui-icon-document'
		}
	}).click(function() {
		$('#deployFieldset').toggle('normal');
	});
});
//-->
</script>
<body>
	<s:if test="%{not empty message}">
		<div class="ui-widget">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
				<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
				<strong>提示：</strong>${message}</p>
			</div>
		</div>
	</s:if>
	<div style="text-align: right;padding: 2px 1em 2px">
		<div id="message" class="alert alert-info" style="display:inline;"><b>提示：</b>点击xml或者png链接可以查看具体内容！</div>
		<a id='deploy' href='#' style="color: blue;">部署流程</a>
		<a id='redeploy' href='${ctx}/workflow/redeploy/all' style="display:none">重新部署流程</a>
	</div>
	
	<fieldset id="deployFieldset" style="display: none">
		<legend>部署新流程</legend>
		<div><b>支持文件格式：</b>zip、bar、bpmn</div>
		<form action="${ctx}/workflow/definition_deploy.action" method="post" enctype="multipart/form-data">
			<input type="file" name="deployFile" />
			<input type="submit" value="Submit" class="btn"/>
		</form>
	</fieldset>
	<table width="98%" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>定义ID</th>
				<th>部署ID</th>
				<th>名称</th>
				<th>KEY</th>
				<th>版本号</th>
				<th>XML</th>
				<th>图片</th>
				<th>是否挂起</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator var="processDefinition" value="#request.page.result" status="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${processDefinition.id}</td>
					<td>${processDefinition.deploymentId }</td>
					<td>${processDefinition.name }</td>
					<td>${processDefinition.key }</td>
					<td>${processDefinition.version }</td>
					<td><a target="_blank"
						href="${ctx}/workflow/definition_loadResource.action?processDefinitionId=${processDefinition.id}&resourceType=xml" style="color: blue;">${processDefinition.resourceName}</a></td>
					<td><a target="_blank"
						href="${ctx}/workflow/definition_loadResource.action?processDefinitionId=${processDefinition.id}&resourceType=image" style="color: blue;">${processDefinition.diagramResourceName}</a></td>
					<td>${processDefinition.suspended} |
						<s:if test="%{processDefinition.suspended}">
							<a href="${ctx}/workflow/definition_updateState.action?processDefinitionId=${processDefinition.id}&state=active" style="color: blue;">激活</a>
						</s:if>
						<s:if test="%{!processDefinition.suspended}">
							<a href="${ctx}/workflow/definition_updateState.action?processDefinitionId=${processDefinition.id}&state=suspended" style="color: blue;">挂起</a>
						</s:if>
					</td>
					<td>
                        <a href='${ctx}/workflow/definition_delete.action?deploymentId=${processDefinition.deploymentId}&processDefinitionId=${processDefinition.id}' style="color: blue;">删除</a>
                        <a href='${ctx}/workflow/definition_convert2Model.action?processDefinitionId=${processDefinition.id}' style="color: blue;">转换为Model</a>
                    </td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
</body>
</html>