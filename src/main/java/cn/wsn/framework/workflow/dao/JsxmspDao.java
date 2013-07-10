/** 
 * @Title: JsxmspDao.java 
 * @Package cn.wsn.framework.dao 
 * @Description: TODO
 * @author steveguoshuai 
 * @date 2013-5-27 下午10:28:12 
 * @version V1.0 
 */  
package cn.wsn.framework.workflow.dao;

import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

/** 
 * @ClassName: JsxmspDao 
 * @Description: TODO
 * @author steveguoshuai
 * @date 2013-5-27 下午10:28:12 
 *  
 */
public interface JsxmspDao {

	public ProcessInstance startWorkflow(String processDefinitionId, String businessKey, Map<String ,Object> variables);
}
