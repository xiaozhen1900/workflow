<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%
	request.setAttribute("title", "流程管理");
%>
<%@include file="/jsp/common/header.jsp"%>
<style type="text/css">
	.layout-sidebar {
		float:left;
		heigth:100%;
		width:10%;
		border:1px;
		background-color: #dceaf4;
	}
	
	.layout-body {
		heigth:100%;
		width:90%;
		float:left;
		background-color: #bbd8e9;
	}
</style>
<script type="text/javascript">
<!--
	 $(function(){
		$(".model").click(function(){
			var url = ctx + "/workflow/model_findByPage.action";
			$("#content").attr("src", url);
		});
		$(".definition").click(function(){
			var url = ctx + "/workflow/definition_findByPage.action";
			$("#content").attr("src", url);
		});
		$(".instance").click(function(){
			var url = ctx + "/workflow/instance_findByPage.action";
			$("#content").attr("src", url);
		});
		$(".job").click(function(){
			var url = ctx + "/workflow/job_findByPage.action";
			$("#content").attr("src", url);
		});
		$(".user").click(function(){
			var url = ctx + "/workflow/user_findByPage.action";
			$("#content").attr("src", url);
		});
		$(".group").click(function(){
			var url = ctx + "/workflow/group_findByPage.action";
			$("#content").attr("src", url);
		});
		$(".procdefGroup").click(function(){
			var url = ctx + "/workflow/procdefGroup_findByPage.action";
			$("#content").attr("src", url);
		});
		$(".newTask").click(function(){
			var url = ctx + "/workflow/definition_findByUserId.action";
			$("#content").attr("src", url);
		});
		$(".task").click(function(){
			var url = ctx + "/workflow/task_getTodoListByUserId.action";
			$("#content").attr("src", url);
		});
		$(".historyTask").click(function(){
			var url = ctx + "/workflow/history_getTaskInstanceList.action?isCompleted=false";
			$("#content").attr("src", url);
		});
		$(".historyActivity").click(function(){
			var url = ctx + "/workflow/history_getActivityInstanceList.action?isCompleted=false";
			$("#content").attr("src", url);
		});
	}); 
	
	/************************************************************************************
	* iframe 自适应高度
	************************************************************************************/
	function setCwinHeight(obj){
	  var iframeObj=obj;
	   if (document.getElementById){
	    if (iframeObj && !window.opera){
	      if (iframeObj.contentDocument && iframeObj.contentDocument.body.offsetHeight){
	        iframeObj.height = iframeObj.contentDocument.body.offsetHeight; 
	      }
	      else if(iframeObj.Document && iframeObj.Document.body.scrollHeight){
	        iframeObj.height = iframeObj.Document.body.scrollHeight;
	      }
	      if(iframeObj.height < 200){
	      	iframeObj.height = 600;
	      }
	    }
	  }
	 // obj.style.height = obj.height+"px";
	}
//-->
</script>
<body
<div class="workflow" style="width:100%;">
    <div class="layout-sidebar">
      <!--Sidebar content-->
		<li>
		<a href="#" class="model" style="color:blue;">流程模型管理</a>
		<br/>
		</li>
      	<li>
      	<a href="#" class="definition" style="color:blue;">流程定义管理</a>
		<br/>
      	</li>
		<%-- <li>		
		<a href="#" class="instance" style="color:blue;">流程实例管理</a>
		<br/>
		</li> --%>
		<li>
		<a href="#" class="job" style="color:blue;">流程JOB管理</a>
		</li>
		<li>
		<a href="#" class="user" style="color:blue;">流程用户管理</a>
		</li>
		<li>
		<a href="#" class="group" style="color:blue;">流程用户组管理</a>
		</li>
		<li>
		<a href="#" class="procdefGroup" style="color:blue;">流程启动用户组管理</a>
		</li>
		<li>
		<a href="#" class="newTask" style="color:blue;">发起新任务</a>
		<br/>
		</li>
		<li>
		<a href="#" class="task" style="color:blue;">待办任务</a>
		<br/>
		</li>
		<li>
		<a href="#" class="historyTask" style="color:blue;">已办任务</a>
		<br/>
		</li>
		<li>
		<a href="#" class="historyActivity" style="color:blue;">已结束实例</a>
		<br/>
		</li>
    </div>
    <div class="layout-body">
      <!--Body content-->
      <iframe id="content" src="${ctx}/workflow/model_findByPage.action" onload="setCwinHeight(this);" style="width:100%;margin:0px;padding:0px;scroll:auto;background-color: #bbd8e9;" scroll="auto"></iframe>
    </div>
</div>
</body>
</html>
