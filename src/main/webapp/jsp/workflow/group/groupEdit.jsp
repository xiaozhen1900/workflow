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
<div id="addGroupDialog" style="display: none;" >
	<form id="groupForm" action="${ctx}/workflow/user_save.action" method="post" class="form-horizontal">
		<input type="hidden" name="model.revision" id="revision" value="0"/>
		<div class="control-group" id="idDiv">
			<label class="control-label" for="inputId"><font color="red">*</font>ID：</label>
			<div class="controls">
				<input type="text" name="model.id" id="id" onchange="checkValid(this.id);" 
				maxlength="64"></input>
			</div>
		</div>
		<div class="control-group" id="nameDiv">
			<label class="control-label" for="inputName"><font color="red">*</font>Name：</label>
			<div class="controls">
				<input type="text" name="model.name" id="name" onchange="checkValid(this.id);"
				maxlength="255"></input>	
			</div>
		</div>
		<div class="control-group" id="typeDiv">
			<label class="control-label" for="inputType"><font color="red">*</font>Type：</label>
			<div class="controls">
				<input type="text" name="model.type" id="type" onchange="checkValid(this.id);"
				maxlength="255"></input>	
			</div>
		</div>
	</form>
</div>
