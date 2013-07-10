/**
 * 
 */
package cn.wsn.framework.workflow.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author guoqiang
 * 流程定义缓存
 */
@Component
@Scope("singleton")
public class WorkflowDefinitionCache {

	private static RepositoryService repositoryService;
	@Autowired
	@Required
	public void setRepositoryService(RepositoryService repositoryService) {
		WorkflowDefinitionCache.repositoryService = repositoryService;
	}

	private static Map<String, ProcessDefinition> CACHE = new HashMap<String, ProcessDefinition>();

	@PostConstruct
	public void init() {
		reload();
	}
	
	/*private WorkflowDefinitionCache(){
	}
	
	private static class WorkflowDefinitionCacheHolder {
		private static final WorkflowDefinitionCache INSTANCE = new WorkflowDefinitionCache();
	}
	
	public static WorkflowDefinitionCache getInstance() {
		 return WorkflowDefinitionCacheHolder.INSTANCE;
	}*/
	
	public static ProcessDefinition getWorkflowDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = null;
		if(CACHE.containsKey(processDefinitionId)) {
			processDefinition = CACHE.get(processDefinitionId);
		}
		if(processDefinition == null) {
			processDefinition = reload(processDefinitionId);
		}
		return processDefinition;
	}
	
	public static void setWorkflowDefinition(String processDefinitionId, ProcessDefinition processDefinition) {
		if(!CACHE.containsKey(processDefinitionId)) {
			CACHE.put(processDefinitionId, processDefinition);
		}
	}
	
	public static void load() {
		List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().active().list();
		for(ProcessDefinition processDefinition : processDefinitionList) {
			CACHE.put(processDefinition.getId(), processDefinition);
		}
	}
	
	public static void reload() {
		CACHE.clear();
		load();
	}
	
	public static ProcessDefinition load(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).active().singleResult();
		setWorkflowDefinition(processDefinitionId, processDefinition);
		return processDefinition;
	}
	
	public static ProcessDefinition reload(String processDefinitionId) {
		CACHE.remove(processDefinitionId);
		return load(processDefinitionId);
	}
	
	@PreDestroy
	public void destory() {
		CACHE.clear();
		CACHE = null;
	}
}
