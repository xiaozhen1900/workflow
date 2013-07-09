/**
 * 
 */
package cn.wsn.framework.workflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wsn.framework.workflow.service.IWorkflowHistoryService;
import cn.wsn.framework.workflow.util.WorkflowUtils;

/**
 * @author guoqiang
 *
 */
@Service("workflowHistoryService")
public class WorkflowHistoryServiceImpl implements IWorkflowHistoryService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private HistoryService historyService;

	/**
	 * 获取历史流程实例
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	@Override
	public HistoricActivityInstanceQuery getActivityInstanceQuery(String processInstanceId) {
		return historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc();
	}
	
	/**
	 * 获取当前人的流程实例
	 * @param userId 用户ID
	 * @param isCompleted 是否结束
	 * @return
	 */
	@Override
	public HistoricActivityInstanceQuery getActivityInstanceQuery(String userId, boolean isCompleted) {
		HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery().taskAssignee(userId);
		if(isCompleted) {
			instanceQuery = instanceQuery.finished();
		}
		return instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
	}
	
	@Override
	public List<HistoricActivityInstance> getActivityInstanceByPage(
			HistoricActivityInstanceQuery query, int[] pageParams) {
		return query.listPage(pageParams[0], pageParams[1]);
	}
	
	/**
	 * 获取历史任务实例
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	@Override
	public HistoricTaskInstanceQuery getHistoricTaskInstanceQuery(String processInstanceId) {
		return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByHistoricTaskInstanceStartTime().asc();
	}
	
	/**
	 * 获取当前人的任务实例
	 * @param userId 用户ID
	 * @param isCompleted 是否结束
	 * @return
	 */
	@Override
	public HistoricTaskInstanceQuery getHistoricTaskInstanceQuery(String userId, boolean isCompleted) {
		HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId);
		if(isCompleted) {
			taskQuery = taskQuery.finished();
		}
		return taskQuery.orderByHistoricTaskInstanceStartTime().asc();
	}
	
	@Override
	public List<HistoricTaskInstance> getHistoricTaskInstanceByPage(
			HistoricTaskInstanceQuery query, int[] pageParams) {
		return query.listPage(pageParams[0], pageParams[1]);
	}
	
	/**
	 * 流程跟踪图
	 * @param processInstanceId		流程实例ID
	 * @return	封装了各种节点信息
	 */
	@Override
	public List<Map<String, Object>> traceWorkflow(String processInstanceId) throws Exception {
		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();//执行实例
		Object property = PropertyUtils.getProperty(execution, "activityId");
		String activityId = "";
		if (property != null) {
			activityId = property.toString();
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();//获得当前任务的所有节点

		List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
		for (ActivityImpl activity : activitiList) {
			boolean currentActiviti = false;
			String id = activity.getId();
			// 当前节点
			if (id.equals(activityId)) {
				currentActiviti = true;
			}
			Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance, currentActiviti);
			activityInfos.add(activityImageInfo);
		}
		return activityInfos;
	}
	
	/**
	 * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
	 * @param activity
	 * @param processInstance
	 * @param currentActiviti
	 * @return
	 */
	private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
			boolean currentActiviti) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> activityInfo = new HashMap<String, Object>();
		activityInfo.put("currentActiviti", currentActiviti);
		setPosition(activity, activityInfo);
		setWidthAndHeight(activity, activityInfo);

		Map<String, Object> properties = activity.getProperties();
		vars.put("任务类型", WorkflowUtils.parseToZhType(properties.get("type").toString()));
		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		logger.debug("activityBehavior={}", activityBehavior);
		if (activityBehavior instanceof UserTaskActivityBehavior) {
			Task currentTask = null;
			/*
			 * 当前节点的task
			 */
			if (currentActiviti) {
				currentTask = getCurrentTaskInfo(processInstance);
			}
			/*
			 * 当前任务的分配角色
			 */
			UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
			TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
			Expression  assigneeExpression = taskDefinition.getAssigneeExpression();
			if(null != assigneeExpression) {
				seTaskUserByVariable(vars, assigneeExpression, processInstance.getProcessInstanceId());
			}
			Set<Expression> candidateUserIdExpressions = taskDefinition.getCandidateUserIdExpressions();
			if(!candidateUserIdExpressions.isEmpty()) {
				setTaskUser(vars, candidateUserIdExpressions);
			}
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			if (!candidateGroupIdExpressions.isEmpty()) {
				// 任务的处理角色
				setTaskGroup(vars, candidateGroupIdExpressions);
				// 当前处理人
				if (currentTask != null) {
					setCurrentTaskAssignee(vars, currentTask);
				}
			}
		}
		
		vars.put("节点说明", properties.get("documentation"));
		String description = activity.getProcessDefinition().getDescription();
		vars.put("描述", description);
		logger.debug("trace variables: {}", vars);
		activityInfo.put("vars", vars);
		return activityInfo;
	}
	
	private void seTaskUserByVariable(Map<String, Object> vars, Expression assigneeExpression, String executionId) {
		String expressionText = assigneeExpression.getExpressionText();
		if(expressionText.startsWith("${") && expressionText.endsWith("}")) {
			String variableName = expressionText.replace("${", "").replace("}", "");
			String variableValue = String.valueOf(runtimeService.getVariable(executionId, variableName));
			User user = identityService.createUserQuery().userId(variableValue).singleResult();
  		    String userName = user.getFirstName() + user.getLastName();
			vars.put("处理人", userName);
		}
	}
	
	private void setTaskUser(Map<String, Object> vars, Set<Expression> candidateuserIdExpressions) {
		String userNames = "";
		for(Expression expression : candidateuserIdExpressions) {
			String expressionText = expression.getExpressionText();
			User user = identityService.createUserQuery().userId(expressionText).singleResult();
  		    String userName = user.getFirstName() + user.getLastName();
			userNames += userName;
		}
		vars.put("处理人", userNames);
	}
	
	private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
		String roles = "";
		for (Expression expression : candidateGroupIdExpressions) {
			String expressionText = expression.getExpressionText();
			String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
			roles += roleName;
		}
		vars.put("任务所属角色", roles);
	}

	/**
	 * 设置当前处理人信息
	 * @param vars
	 * @param currentTask
	 */
	private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
		String assignee = currentTask.getAssignee();
		if (assignee != null) {
			User assigneeUser = identityService.createUserQuery().userId(assignee).singleResult();
			String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
			vars.put("当前处理人", userInfo);
		}
	}

	/**
	 * 获取当前节点信息
	 * @param processInstance
	 * @return
	 */
	private Task getCurrentTaskInfo(ProcessInstance processInstance) {
		Task currentTask = null;
		try {
			String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");
			logger.debug("current activity id: {}", activitiId);

			currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey(activitiId)
					.singleResult();
			logger.debug("current task for processInstance: {}", ToStringBuilder.reflectionToString(currentTask));

		} catch (Exception e) {
			logger.error("can not get property activityId from processInstance: {}", processInstance);
		}
		return currentTask;
	}
	
	/**
	 * 设置宽度、高度属性
	 * @param activity
	 * @param activityInfo
	 */
	private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("width", activity.getWidth());
		activityInfo.put("height", activity.getHeight());
	}

	/**
	 * 设置坐标位置
	 * @param activity
	 * @param activityInfo
	 */
	private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("x", activity.getX());
		activityInfo.put("y", activity.getY());
	}
}
