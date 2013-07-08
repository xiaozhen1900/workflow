<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "流程实例管理");
%>
<%@include file="/jsp/common/header.jsp"%>
<script type="text/javascript">
<!--
	$(function(){
		$(".follow").button({
			icons:{
				primary: 'ui-icon-image'
			}
		});
		$(".deleteInstance").button({
			icons:{
				primary: 'ui-icon-closethick'
			}
		});
		$(".followTracing").button({
			icons:{
				primary: 'ui-icon-image'
			}
		}).click(function(){
			var pid = $(this).attr("pid");
			var url = "${ctx}/workflow/instance_loadResource.action?processInstanceId="+pid;
			$("#followTracingImageDialog").dialog({
				modal: true,
	            resizable: false,
	            dragable: false,
	            open: function() {
	                $('#followTracingImageDialog').dialog('option', 'title', '查看流程跟踪图');
	                $('#followTracingImageDialog').css('padding', '0.2em');
	                $('#followTracingImageDialog .ui-accordion-content').css('padding', '0.2em').height($('#followTracingImageDialog').height() - 75);
	                $('#followTracingImageDialog img').attr("src", url);
	            },
	            close: function() {
	                $('#followTracingImageDialog').close();
	            },
	            width: document.documentElement.clientWidth * 0.65,
	            height: document.documentElement.clientHeight * 0.65
			});
		});
	});
//-->
</script>
<body>
	<table width="98%" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>ID</th>
				<th>ProcessInstanceId</th>
				<th>BusinessKey</th>
				<th>ProcessDefinitionId</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="#request.page.result" var="instance" status="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${instance.id}</td>
					<td>${instance.processInstanceId}</td>
					<td>${instance.businessKey}</td>
					<td>${instance.processDefinitionId}</td>
					<td>
						<a href="#" class="followTracing" pid="${instance.processInstanceId}">流程跟踪图</a>
                        <a class="deleteInstance" href="${ctx}/workflow/instance_delete.action?processInstanceId=${instance.id}">删除</a>
					</td>
				</tr>
			</s:iterator>	
		</tbody>
	</table>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
	<div id="followTracingImageDialog" style="display: none;" class="ui-accordion-content">
		<img id="followTracingImage" src="" alt="流程跟踪图" />
	</div>
</body>
</html>