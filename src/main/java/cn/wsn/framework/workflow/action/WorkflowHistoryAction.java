/**
 * 
 */
package cn.wsn.framework.workflow.action;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.wsn.framework.workflow.service.IWorkflowHistoryService;
import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.PageUtil;

import com.wsn.common.action.BaseAction;
import com.wsn.common.util.HttpSessionControl;

/**
 * @author guoqiang
 *
 */
@Controller("/history")
@Scope("prototype")
public class WorkflowHistoryAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3416840218501223988L;

	@Autowired
	private IWorkflowHistoryService workflowHistoryServiceImpl;
	
	public String getActivityInstanceList(){
		//JSONObject dtoData = jsonParamsObj.getJSONObject("history");
		//String processInstanceId = dtoData.getString("processInstanceId");
		String processInstanceId = request.getParameter("processInstanceId");
		List<HistoricActivityInstance> historyInstanceList = null;
		HistoricActivityInstanceQuery query = null;
		if(StringUtils.isNotEmpty(processInstanceId)) {
			query = workflowHistoryServiceImpl.getActivityInstanceQuery(processInstanceId);
		} else {
			com.wsn.common.entity.User user = (com.wsn.common.entity.User) HttpSessionControl.get(HttpSessionControl.USER);
			String userId = user.getAccountSet().iterator().next().getUserName();
			boolean isCompleted = Boolean.valueOf(request.getParameter("isCompleted"));
			query = workflowHistoryServiceImpl.getActivityInstanceQuery(userId, isCompleted);
		}
		Page<HistoricActivityInstance> page = new Page<HistoricActivityInstance>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		historyInstanceList = workflowHistoryServiceImpl.getActivityInstanceByPage(query, pageParams);
		page.setTotalCount(query.count());
		page.setResult(historyInstanceList);
		request.setAttribute("page", page);
		return "workflowActivityInstanceList";
	}
	
	public String getTaskInstanceList() {
		//JSONObject dtoData = jsonParamsObj.getJSONObject("history");
		//String processInstanceId = dtoData.getString("processInstanceId");
		String processInstanceId = request.getParameter("processInstanceId");
		List<HistoricTaskInstance> historyTaskList = null;
		HistoricTaskInstanceQuery query = null;
		if(StringUtils.isNotEmpty(processInstanceId)) {
			query = workflowHistoryServiceImpl.getHistoricTaskInstanceQuery(processInstanceId);
		} else {
			com.wsn.common.entity.User user = (com.wsn.common.entity.User) HttpSessionControl.get(HttpSessionControl.USER);
			String userId = user.getAccountSet().iterator().next().getUserName();
			boolean isCompleted = Boolean.valueOf(request.getParameter("isCompleted"));
			query = workflowHistoryServiceImpl.getHistoricTaskInstanceQuery(userId, isCompleted);
		}
		Page<HistoricTaskInstance> page = new Page<HistoricTaskInstance>(PageUtil.PAGE_SIZE);
		int[] pageParams = PageUtil.init(page, request);
		historyTaskList = workflowHistoryServiceImpl.getHistoricTaskInstanceByPage(query, pageParams);
		page.setTotalCount(query.count());
		page.setResult(historyTaskList);
		request.setAttribute("page", page);
		return "workflowCompletedTaskList";
	}
	
	public String traceWorkflow() {
		JSONObject dtoData = jsonParamsObj.getJSONObject("history");
		String processInstanceId = dtoData.getString("processInstanceId");
		List<Map<String, Object>> activitiInfo = null;
		try {
			activitiInfo = workflowHistoryServiceImpl.traceWorkflow(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		results.put("history", activitiInfo);
		return SUCCESS;
	}
}
