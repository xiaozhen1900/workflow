/**
 * 
 */
package cn.wsn.framework.workflow.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.wsn.framework.action.BaseAction;
import cn.wsn.framework.entity.User;
import cn.wsn.framework.util.HttpSessionControl;
import cn.wsn.framework.workflow.cache.WorkflowDefinitionCache;
import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.PageUtil;

/**
 * @author guoqiang
 *
 */
@Controller("/task")
@Scope("prototype")
public class WorkflowTaskAction extends BaseAction {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TaskService taskService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2665514909213421077L;

	/**
	 * 获取当前人待办任务列表
	 * @return
	 */
	public String getTodoListByUserId() {
		//String userId = ServletActionContext.getRequest().getParameter("userId");
		User user = (User) HttpSessionControl.get(HttpSessionControl.USER);
		String userId = user.getAccountSet().iterator().next().getUserName();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(PageUtil.PAGE_SIZE);
		int[] params = PageUtil.init(page, request);
		// 获取已经签收的任务
		TaskQuery todoQuery = taskService.createTaskQuery().taskAssignee(userId).active().orderByTaskCreateTime().desc();
		List<Task> todoList = todoQuery.listPage(params[0], params[1]);
		for(Task task : todoList) {
			ProcessDefinition processDefinition = WorkflowDefinitionCache.getWorkflowDefinition(task.getProcessDefinitionId());
			Map<String, Object>  singleTask = packageTaskInfo(sdf, task, processDefinition);
			singleTask.put("status", "todo");
			result.add(singleTask);
		}
		// 获取等待签收的任务
		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId).active().orderByTaskCreateTime().desc();
		List<Task> toClaimList = toClaimQuery.listPage(params[0], params[1]);
		for(Task task : toClaimList) {
			ProcessDefinition processDefinition = WorkflowDefinitionCache.getWorkflowDefinition(task.getProcessDefinitionId());
			Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
			singleTask.put("status", "toClaim");
			result.add(singleTask);
		}
		page.setTotalCount(todoQuery.count() + toClaimQuery.count());
		page.setResult(result);
		request.setAttribute("page", page);
		return "workflowTodoTaskList";
	}
	
	 private Map<String, Object> packageTaskInfo(SimpleDateFormat sdf, Task task, ProcessDefinition processDefinition) {
	    Map<String, Object> singleTask = new HashMap<String, Object>();
	    singleTask.put("id", task.getId());
	    singleTask.put("name", task.getName());
	    singleTask.put("createTime", sdf.format(task.getCreateTime()));
	    singleTask.put("proDefId", processDefinition.getId());
	    singleTask.put("pdname", processDefinition.getName());
	    singleTask.put("pdversion", processDefinition.getVersion());
	    singleTask.put("pid", task.getProcessInstanceId());
	    singleTask.put("assignee", task.getAssignee());
	    singleTask.put("taskDefinitionKey", task.getTaskDefinitionKey());
	    return singleTask;
	}

	/**
	 * 签收任务
	 * @return
	 */
    public String claim() {
    	JSONObject dtoData = null;
    	try {
			dtoData = new JSONObject(jsonParamsObj.getString("task"));
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	String taskId = dtoData.getString("taskId");
    	User user = (User)HttpSessionControl.get(HttpSessionControl.USER);
    	String userId = user.getAccountSet().iterator().next().getUserName();
    	taskService.claim(taskId, userId);
    	logger.debug("{}签收任务{}", userId, taskId);
    	return SUCCESS;
    }
	
    /**
     * 完成任务
     * @return
     */
    public String complete() {
    	JSONObject dtoData = null;
    	try {
			dtoData = new JSONObject(jsonParamsObj.getString("task"));
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	//String taskId = request.getParameter("taskId");
    	//String isAccept = request.getParameter("isAccept");
    	String taskId = dtoData.getString("taskId");
    	Boolean isAccept = dtoData.getBoolean("isAccept");
    	Map<String, Object> variables = new HashMap<String, Object>();
    	if(StringUtils.isNotEmpty(String.valueOf(isAccept))) {
    		variables.put("isAccept", isAccept);
    	}
    	taskService.complete(taskId, variables);
    	logger.debug("完成任务{}", taskId);
    	return SUCCESS;
    }
    
    public String delete() {
    	String taskId = request.getParameter("taskId");
    	taskService.deleteTask(taskId);
    	logger.debug("删除任务{}", taskId);
    	return SUCCESS;
    }
}
