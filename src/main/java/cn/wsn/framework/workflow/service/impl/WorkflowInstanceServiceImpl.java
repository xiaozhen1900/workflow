/**
 * 
 */
package cn.wsn.framework.workflow.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wsn.framework.workflow.service.IWorkflowInstanceService;
import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.WorkflowState;

/**
 * @author guoqiang
 *
 */
@Service("workflowInstanceService")
public class WorkflowInstanceServiceImpl implements IWorkflowInstanceService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private ProcessEngineFactoryBean processEngine;
	
	/**
	 * 加载流程实例资源
	 */
	public InputStream loadResource(String processInstanceId) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		List<String> activeActivitiIds = runtimeService.getActiveActivityIds(processInstanceId);
		Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());
		InputStream inputStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivitiIds);
		return inputStream;
	}

	@Override
	public List<ProcessInstance> findAll() {
		ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
		List<ProcessInstance> processInstanceList = processInstanceQuery.list();
		return processInstanceList;
	}

	@Override
	public void findByPage(Page<ProcessInstance> page, int firstResult,
			int maxResult) {
		ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery().active().orderByProcessInstanceId().asc();
		List<ProcessInstance> processInstanceList = processInstanceQuery.listPage(firstResult, maxResult);
		page.setTotalCount(processInstanceQuery.count());
		page.setResult(processInstanceList);
	}
	
	@Override
	public ProcessInstance load(String processInstanceId) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		return processInstance;
	}

	@Override
	public void updateState(String processInstanceId, String state) {
		if(WorkflowState.ACTIVE.toString().equalsIgnoreCase(state)) {
			runtimeService.activateProcessInstanceById(processInstanceId);
		} else if(WorkflowState.SUSPENDED.toString().equalsIgnoreCase(state)) {
			runtimeService.suspendProcessInstanceById(processInstanceId);
		}
		logger.debug("更新流程实例编号为{}的状态为{}", processInstanceId, state);
	}

	@Override
	public void delete(String processInstanceId, String deleteReason) {
		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
	}

	@Override
	public ProcessInstance startProcessInstanceById(String processDefinitionId) {
		return startProcessInstanceById(processDefinitionId, null, null);
	}
	
	public ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey) {
		return startProcessInstanceById(processDefinitionId, businessKey, null);
	}
	
	public ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> variables) {
		return startProcessInstanceById(processDefinitionId, null, variables);
	}
	
	public ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey, Map<String, Object> variables) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables);
		return processInstance;
	}
}
