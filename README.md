workflow
========

基于Activiti的工作流管理系统

引用workflow的步骤
1.拷贝src/main/webapp目录下的所有文件到对应工程目录下包括WEB-INF/目录下的web.xml，直接覆盖掉

2.如果是开发就直接引用workflow工程就行了，如果是生产就把workflow打包成jar。

3.在工程的spring配置文件引入workflowContext.xml文件，在struts.xml文件引入workflowStruts.xml文件
