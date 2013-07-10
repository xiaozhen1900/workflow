<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "流程Model管理");
%>
<%@include file="/jsp/common/header.jsp"%>
 <script type="text/javascript">
    $(function() {
    	$('#create').button({
    		icons: {
    			primary: 'ui-icon-plus'
    		}
    	}).click(function() {
    		$('#createModelTemplate').dialog({
    			modal: true,
    			width: 500,
    			buttons: [{
    				text: '创建',
    				click: function() {
    					if (!$('#name').val()) {
    						alert('请填写名称！');
    						$('#name').focus();
    						return;
    					}
    					if (!$('#key').val()) {
    						alert('请填写key！');
    						$('#key').focus();
    						return;
    					}
    					$('#modelForm').submit();
    					$('#createModelTemplate').dialog("close");
    				}
    			}]
    		});
    	});
    });
    </script>
</head>
<body>
	<s:if test="%{not empty message}">
	<div class="ui-widget">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
				<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
				<strong>提示：</strong>${message}</p>
			</div>
		</div>
	</s:if>
	<div style="text-align: right"><button id="create">创建模型</button></div>
	<table width="98%" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>ID</th>
				<th>KEY</th>
				<th>Name</th>
				<th>Version</th>
				<th>创建时间</th>
				<th>最后更新时间</th>
				<%-- 
				<th>元数据</th>
				--%>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="#request.page.result" var="model" status="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${model.id }</td>
					<td>${model.key }</td>
					<td>${model.name}</td>
					<td>${model.version}</td>
					<td><s:date name="#model.createTime"/></td>
					<td><s:date name="#model.lastUpdateTime"/></td>
					<%-- <td>${model.metaInfo}</td>--%>
					<td>
						<a href="${ctx}/service/editor?id=${model.id}" target="_blank" style="color:blue;">编辑</a>
						<a href="${ctx}/workflow/model_deploy.action?modelId=${model.id}" style="color:blue;">部署</a>
						<a href="${ctx}/workflow/model_export.action?modelId=${model.id}" target="_blank" style="color:blue;">导出</a>
                        <a href="${ctx}/workflow/model_delete.action?modelId=${model.id}" style="color:blue;">删除</a>
					</td>
				</tr>
			</s:iterator>	
		</tbody>
	</table>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
	<div id="createModelTemplate" title="创建模型" class="template"
		style="display: none;">
		<form id="modelForm" action="${ctx}/workflow/model_create.action" target="_blank" method="post">
			<table>
				<tr>
					<td>名称：</td>
					<td><input id="name" name="name" type="text" /></td>
				</tr>
				<tr>
					<td>KEY：</td>
					<td><input id="key" name="key" type="text" /></td>
				</tr>
				<tr>
					<td>描述：</td>
					<td><textarea id="description" name="description"
							style="width: 300px; height: 50px;"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>