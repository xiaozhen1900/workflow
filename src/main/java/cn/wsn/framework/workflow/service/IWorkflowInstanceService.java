/**
 * 
 */
package cn.wsn.framework.workflow.service;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;

import cn.wsn.framework.workflow.util.Page;

/**
 * @author guoqiang
 *
 */
public interface IWorkflowInstanceService {
	
	public List<ProcessInstance> findAll();
	
	public void findByPage(Page<ProcessInstance> page, int firstResult, int maxResult);
	
	public InputStream loadResource(String processInstanceId);
	
	public ProcessInstance load(String processInstanceId);
	
	public void updateState(String processInstanceId, String state);
	
	public void delete(String processInstanceId, String deleteReason);
	
	public ProcessInstance startProcessInstanceById(String processDefinitionId);

}
