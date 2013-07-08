/** 
 * @Title: IWorkflowModelService.java 
 * @Package cn.wsn.framework.workflow.service 
 * @Description: TODO
 * @author steveguoshuai 
 * @date 2013-5-24 下午6:51:26 
 * @version V1.0 
 */  
package cn.wsn.framework.workflow.service;

import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;

import cn.wsn.framework.workflow.util.Page;

/** 
 * @ClassName: IWorkflowModelService 
 * @Description: TODO
 * @author steveguoshuai
 * @date 2013-5-24 下午6:51:26 
 *  
 */
public interface IWorkflowModelService {

	public List<Model> findAll();
	
	public void findByPage(Page<Model> page, int firstResult, int maxResult);
	
	public Model create(String key, String name, String description);
	
	public Deployment deploy(String modelId);
	
	public BpmnModel export(String modelId);
	
	public void delete(String modelId);
}
