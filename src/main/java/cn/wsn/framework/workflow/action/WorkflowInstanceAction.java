/**
 * 
 */
package cn.wsn.framework.workflow.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.wsn.framework.action.BaseAction;
import cn.wsn.framework.workflow.service.IWorkflowInstanceService;
import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.PageUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author guoqiang
 *
 */
@Controller("/instance")
@Scope("prototype")
public class WorkflowInstanceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7304920957865725531L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected IWorkflowInstanceService workflowInstanceService;

	/**
	 * 查找工作流程实例列表
	 * @return
	 */
	public String findAll() {
		List<ProcessInstance> processInstanceList = workflowInstanceService.findAll();
		ActionContext.getContext().put("instanceList", processInstanceList);
		return "workflowInstanceList";
	}
	
	public String findByPage() {
		Page<ProcessInstance> page = new Page<ProcessInstance>(PageUtil.PAGE_SIZE);
	    int[] pageParams = PageUtil.init(page, request);
	    workflowInstanceService.findByPage(page, pageParams[0], pageParams[1]);
	    request.setAttribute("page", page);
	    return "workflowInstanceList";
	}
	
	/**
	 * 根据流程实例编号加载流程实例
	 * @return
	 */
	public String load() {
		String processInstanceId = request.getParameter("processInstanceId");
		ProcessInstance processInstance = workflowInstanceService.load(processInstanceId);
		ActionContext.getContext().put("processInstance", processInstance);
		return SUCCESS;
	}
	
	/**
	 * 激活或者挂起流程实例
	 * @return
	 */
	public String updateState() {
		String processInstanceId = request.getParameter("processInstanceId");
		String state = request.getParameter("state");
		workflowInstanceService.updateState(processInstanceId, state);
		return SUCCESS;
	}
	
	/**
	 * 删除流程实例
	 * @return
	 */
	public String delete() {
		String processInstanceId = request.getParameter("processInstanceId");
		String deleteReason = request.getParameter("deleteReason");
		workflowInstanceService.delete(processInstanceId, deleteReason);
		return SUCCESS;
	}
	
	/**
	 * 生成一个流程实例
	 * @return
	 */
	public String startWorkflowInstance() {
		String processDefinitionId = request.getParameter("processDefinitionId");
		ProcessInstance processInstance = workflowInstanceService.startProcessInstanceById(processDefinitionId);
		ActionContext.getContext().put("processInstance", processInstance);
		return SUCCESS;
	}
	
	/**
	 * 加载流程实例资源
	 */
	public void loadResource() {
		String processInstanceId = request.getParameter("processInstanceId");
		InputStream inputStream = workflowInstanceService.loadResource(processInstanceId);
		 // 输出资源内容到相应对象
	    byte[] b = new byte[1024];
	    int len;
	    try {
			while ((len = inputStream.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public IWorkflowInstanceService getWorkflowInstanceService() {
		return workflowInstanceService;
	}
	
	@Autowired
	public void setWorkflowInstanceService(
			IWorkflowInstanceService workflowInstanceService) {
		this.workflowInstanceService = workflowInstanceService;
	}
}
