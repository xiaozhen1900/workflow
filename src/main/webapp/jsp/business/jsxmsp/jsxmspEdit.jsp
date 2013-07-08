<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "建设项目审批管理收文");
%>
<%@include file="/jsp/common/header.jsp"%>
<script type="text/javascript">
<!--
//时间控件初始化
$.datepicker.setDefaults( $.datepicker.regional[ "zh-CN" ] );
$("#cjsj").datepicker({
	showOn: "button",
	buttonImage: "http://localhost:8080/wsnframework/wsnJsFrameWork/corelib/images/calendar.gif",
	buttonImageOnly: true
});	

function startWorkflow() {
	$("#jsxmspEditForm").submit();
}
//-->
</script>
<body>
	<div class='popContent' style='width: 100%; height: 100%'>
		<form id="jsxmspEditForm"
			action="${ctx}/business/jsxmsp_startWorkflow.action" method="post">
			<input type="hidden" name="processDefinitionId" value="${param.processDefinitionId}" />
			<table border="1" cellpadding="0" cellspacing="0" width="95%"
				id="editTable">
				<tbody>
					<tr style="display: none;">
						<td width="20%"></td>
						<td width="30%"></td>
						<td width="20%"></td>
						<td width="30%"></td>
					</tr>
					<tr>
						<td>项目名称</td>
						<td colspan="3"><input type="text" id="xmmc" name="model.xmmc"
								maxlength="200" required="true" class="inputTxt"></td>
					</tr>
					<tr>
						<td>建设单位</td>
						<td colspan="3"><input type="text" id="jsdw" name="model.jsdw"
								maxlength="200" required="true" class="inputTxt"/></td>
					</tr>
					<tr>
						<td>建设地点</td>
						<td colspan="3"><input type="text" id="jsdd" name="model.jsdd"
								maxlength="200" required="true" class="inputTxt"/></td>
					</tr>
					<%-- 
					<tr>
						<td>创建人</td>
						<td><input type="text" id="cjr" name="model.cjr" maxlength="50"
								required="true" class="inputTxt"/></td>
						<td>创建时间</td>
						<td><input type="text" id="cjsj" name="model.cjsj" required="true"
								class="inputTxt"/></td>
					</tr>
					--%>
				</tbody>
			</table>
			<input type="button" name="button" value="发起流程"
				onclick="startWorkflow();" class='actionBtn' />
		</form>
	</div>
</body>
</html>