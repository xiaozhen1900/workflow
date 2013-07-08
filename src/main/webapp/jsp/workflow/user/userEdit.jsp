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
	
	document.onkeydown = function () {
		if (event.keyCode === 13 && "function" === typeof onEnter) {
			onEnter();
		}
	}
</script>
<div id="addUserDialog" style="display: none;" >
	<form id="userForm" action="${ctx}/workflow/user_save.action" method="post" class="form-horizontal">
		<input type="hidden" name="model.revision" id="revision" value="0"/>
		<div class="control-group" id="idDiv">
			<label class="control-label" for="inputId"><font color="red">*</font>ID：</label>
			<div class="controls">
				<input type="text" name="model.id" id="id" onchange="checkValid(this.id);" 
				maxlength="64"></input>	
			</div>
		</div>
		<div class="control-group" id="passwordDiv">
			<label class="control-label" for="inputPassword"><font color="red">*</font>Password：</label>
			<div class="controls">
				<input type="text" name="model.password" id="password" onchange="checkValid(this.id);"
				maxlength="255"></input>	
			</div>
		</div>
		<div class="control-group" id="firstNameDiv">
			<label class="control-label" for="inputFirstName"><font color="red">*</font>FirstName：</label>
			<div class="controls">
				<input type="text" name="model.firstName" id="firstName" onchange="checkValid(this.id);"
				maxlength="255"></input>
			</div>
		</div>
		<div class="control-group" id="lastNameDiv">
			<label class="control-label" for="inputLastName"><font color="red">*</font>LastName：</label>
			<div class="controls">
				<input type="text" name="model.lastName" id="lastName" onchange="checkValid(this.id);"
				maxlength="255"></input>
			</div>
		</div>
		<div class="control-group" id="emailDiv">
			<label class="control-label" for="inputEmail"><font color="red">*</font>Email：</label>
			<div class="controls">
				<input type="text" name="model.email" id="email" onchange="checkValid(this.id);"
				maxlength="255"></input>
			</div>
		</div>
	</form>
</div>
