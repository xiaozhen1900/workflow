<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	function checkValid(objId) {
		if (!$('#' + objId).val()) {
			$('#' + objId).focus();
			$('#' + objId + "Div").addClass("control-group error");
			//$('#processdefinitionidError').css("display","block");
			return;
		} else {
			$('#' + objId + "Div").removeClass("error");
		}
	}
</script>
<div id="addProcdefGroupDialog" style="display: none;" >
	<form id="procdefGroupForm" action="${ctx}/workflow/procdefGroup_save.action" method="post" class="form-horizontal">
		<input type="hidden" name="model.id" id="id" value="1"></input>	
			<div class="control-group" id="processdefinitionidDiv">
				<label class="control-label" for="inputProcessdefinitionid"><font color="red">*</font>流程定义ID：</label>
				<div class="controls">
					<input type="text" name="model.processdefinitionid" id="processdefinitionid" onchange="checkValid(this.id);"
					maxlength="64"></input>
				</div>
			</div>
			<div class="control-group" id="groupidDiv">
				<label class="control-label" for="inputGroupId"><font color="red">*</font>用户组ID：</label>
				<div class="controls">
					<input type="text" name="model.groupid" id="groupid" onchange="checkValid(this.id);"
					maxlength="64"></input>
				</div>
			</div>
	</form>
</div>
