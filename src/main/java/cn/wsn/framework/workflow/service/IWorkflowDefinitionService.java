/**
 * 
 */
package cn.wsn.framework.workflow.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

import cn.wsn.framework.workflow.util.Page;

import com.wsn.common.exception.WsnException;

/**
 * @author guoqiang
 *
 */
public interface IWorkflowDefinitionService {
	
	public List<ProcessDefinition> findAll();
	
	public void findByPage(Page<ProcessDefinition> page, int firstResult, int maxResult);
	
	public List<ProcessDefinition> findByUserId(String userId) throws WsnException;

	public void deploy(File deployFile, String deployFileFileName);
	
	public void convert2Model(String processDefinitionId) throws Exception;
	
	public InputStream loadResource(String processDefinitionId, String resourceType);
	
	public ProcessDefinition load(String processDefinitionId);
	
	public void updateState(String processDefinitionId, String state);
	
	public void delete(String deploymentId, String processDefinitionId, boolean cascade);

}
