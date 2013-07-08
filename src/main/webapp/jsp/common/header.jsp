<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String ctx = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ ctx + "/";
	String serverPath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/";
	request.setAttribute("ctx", ctx);
	request.setAttribute("basePath", basePath);
	request.setAttribute("serverPath", serverPath);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${title}</title>
<link rel="shortcut icon"
	href="${basePath}/wsnJsFrameWork/image/pageicon.png" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/bootstrap/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/css/default.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/dTree/dtree.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/flexigrid/css/flexigrid.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/window/css/popPages.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/window/css/jquery.window.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/validate/validation.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/corelib/jquery-ui-1.8.18.custom.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/corelib/demos.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/progressbar/progressbar.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/jtip/global.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/qtip/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/fileUpload/css/jquery.fileupload-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/popWindow/popWindow.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/wsnJsFrameWork/plug/superfishMenu/css/superfish.css"
	media="screen" />

<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/corelib/jquery-1.7.1.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/corelib/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/corelib/jquery-ui-1.8.18.custom.js"></script>
<script type="text/javascript"
	src="${basePath}wsnJsFrameWork/corelib/jquery.progressbar.min.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/wsnCallBack.js"></script>
<script type="text/javascript" src="${basePath}/wsnJsFrameWork/wsnControl.js"></script>
<script type="text/javascript" src="${basePath}/wsnJsFrameWork/wsnHttpCom.js"></script>
<script type="text/javascript" src="${basePath}/wsnJsFrameWork/wsnMain.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/wsnRemoteInterface.js"></script>
<script type="text/javascript" src="${basePath}/wsnJsFrameWork/wsnUtil.js"></script>
<script type="text/javascript" src="${basePath}/wsnJsFrameWork/wsnGrids.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/wsnClickActions.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/wsnWindowContent.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/wsnSearchPanelContent.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/flexigrid/js/flexigrid.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/window/jquery.window.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/validate/jquery.metadata.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/validate/jquery.validate.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/html/jquery.outerhtml.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/qtip/jquery.qtip.min.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/qtip/jquery.qtip.pack.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/validate/messages_cn.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/dTree/dtree.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/jtip/jtip.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/chart/highcharts.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/chart/exporting.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/chart/wsnChart.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/deviceDistribut/addDeviceDistribute.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/deviceDistribut/showDeviceDistribute.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/deviceDistribut/building.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/fileUpload/js/jquery.fileupload.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/popWindow/popWindow.js"></script>
<%-- 
<script type="text/javascript"
	src="http://api.mapbar.com/api/mapbarapi31.2.js"></script>
	--%>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/superfishMenu/js/hoverIntent.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/superfishMenu/js/superfish.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/gis/wsnGis.js"></script>
<script type="text/javascript"
	src="${basePath}/wsnJsFrameWork/plug/fireState/fireState.js"></script>
</head>