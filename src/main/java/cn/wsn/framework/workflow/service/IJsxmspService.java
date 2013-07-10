/** 
 * @Title: IJsxmspService.java 
 * @Package cn.wsn.framework.jsxmsp 
 * @Description: TODO
 * @author steveguoshuai 
 * @date 2013-5-27 下午10:10:19 
 * @version V1.0 
 */  
package cn.wsn.framework.workflow.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

import cn.wsn.framework.workflow.entity.Jsxmsp;

/** 
 * @ClassName: IJsxmspService 
 * @Description: TODO
 * @author steveguoshuai
 * @date 2013-5-27 下午10:10:19 
 *  
 */
public interface IJsxmspService {

	public void save(Jsxmsp jsxmsp);
	
	public Jsxmsp get(String xmbh);
	
	public void update(Jsxmsp jsxmsp);
	
	public void delete(String xmbh);
	
	public List<Jsxmsp> findAll();
	
	public ProcessInstance startWorkflow(String processDefinitionId, Jsxmsp jsxmsp, Map<String, Object> variables);
}
