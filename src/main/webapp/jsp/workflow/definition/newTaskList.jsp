<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("title", "发起新任务");
%>
<%@include file="/jsp/common/header.jsp"%>
<style>
<!--
.show-grid {
	margin-top: 10px;
	margin-bottom: 20px;
}

.show-grid [class*="span"] {
	background-color: #eee;
	text-align: center;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	min-height: 30px;
	line-height: 30px;
}

.show-grid:hover [class*="span"] {
	background: #ddd;
}

.show-grid .show-grid {
	margin-top: 0;
	margin-bottom: 0;
}

.show-grid .show-grid [class*="span"] {
	background-color: #ccc;
}
-->
</style>
<script type="text/javascript">
<!--
$(function() {
	$('#jsxmspbgb').click(function() {
		var defId = $(this).attr('defId');
		var url = "${ctx}/jsp/business/jsxmsp/jsxmspEdit.jsp?processDefinitionId="+defId;
		$("#startWorkflowDiv").dialog({
			 open: function() {
				$("#startWorkflowDiv iframe").attr("src", url);
			 },
			 width: document.documentElement.clientWidth * 0.65,
       	 	 height: document.documentElement.clientHeight * 0.65
		});
	});
});
//-->
</script>
<body>
	<c:if test="${not empty results.processDefinitionList}">
		<c:forEach begin="0" end="${fn:length(results.processDefinitionList)}"
			step="4" var="index">
			<div class="row-fluid show-grid">
				<c:if test="${(index + 1) lt fn:length(results.processDefinitionList)}">
				<div class="span3"
					title="${results.processDefinitionList[index].description}">
					<span class="img"></span> <span class="txt"> <strong><a
							href="#" id="${results.processDefinitionList[index].key}"
							defId="${results.processDefinitionList[index].id}"
							style="color: blue;">${results.processDefinitionList[index].name}</a></strong><br />
						<li>${results.processDefinitionList[index].description}</li>
					</span>
				</div>
				</c:if>
				<c:if test="${(index + 2) lt fn:length(results.processDefinitionList) && (index+2) % 2 == 0}">
					<div class="span3"
						title="${results.processDefinitionList[index + 1].description}">
						<span class="img"></span> <span class="txt"> <strong><a
								href="#" id="${results.processDefinitionList[index + 1].key}"
								defId="${results.processDefinitionList[index + 1].id}"
								style="color: blue;">${results.processDefinitionList[index +
									1].name}</a></strong><br />
							<li>${results.processDefinitionList[index + 1].description}</li>
						</span>
					</div>
				</c:if>
				<c:if test="${index eq 0}">
					<c:set var="modIndex" value="0"></c:set>
				</c:if>
				<c:if test="${index gt 0 }">
					<c:set var="modIndex" value="${index - index/4}"></c:set>
				</c:if>
				<c:if test="${(index + 3) le fn:length(results.processDefinitionList) && (modIndex % 3 == 0)}">
					<div class="span3"
						title="${results.processDefinitionList[index + 2].description}">
						<span class="img"></span> <span class="txt"> <strong><a
								href="#" id="${results.processDefinitionList[index + 2].key}"
								defId="${results.processDefinitionList[index + 2].id}"
								style="color: blue;">${results.processDefinitionList[index +
									2].name}</a></strong><br />
							<li>${results.processDefinitionList[index + 2].description}</li>
						</span>
					</div>
				</c:if>
				<c:if test="${(index + 4) le fn:length(results.processDefinitionList) && ((index+4) % 4 == 0)}">
					<div class="span3"
						title="${results.processDefinitionList[index + 3].description}">
						<span class="img"></span> <span class="txt"> <strong><a
								href="#" id="${results.processDefinitionList[index + 3].key}"
								defId="${results.processDefinitionList[index + 3].id}"
								style="color: blue;">${results.processDefinitionList[index +
									3].name}</a></strong><br />
							<li>${results.processDefinitionList[index + 3].description}</li>
						</span>
					</div>
				</c:if>
			</div>
		</c:forEach>
	</c:if>
	<div id="startWorkflowDiv" style="display:none;">
		<iframe id="startWorkflowContent" src="" style="width:95%;height:95%;"></iframe>
	</div>
</body>
</html>