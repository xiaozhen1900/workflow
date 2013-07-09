/**
 * 
 */
package cn.wsn.framework.workflow.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.wsn.framework.workflow.service.IWorkflowDefinitionService;
import cn.wsn.framework.workflow.service.IWorkflowModelService;
import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.PageUtil;

import com.opensymphony.xwork2.ActionContext;
import com.wsn.common.action.BaseAction;
import com.wsn.common.entity.User;
import com.wsn.common.exception.WsnException;
import com.wsn.common.util.HttpSessionControl;

/**
 * @author guoqiang
 *
 */
@Controller("/definition")
@Scope("prototype")
public class WorkflowDefinitionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8031029607995563562L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private IWorkflowDefinitionService workflowDefinitionService;

	@Autowired
	private IWorkflowModelService workflowModelService;
	
    /**
     * 部署文件上传
     */
    private File deployFile;
    
    private String deployFileFileName;
    
    private String deployFileContentType;
    
    /**
	 * 部署流程定义
	 * @return
	 */
	public String deploy() {
		workflowDefinitionService.deploy(deployFile, deployFileFileName);
		return findByPage();
	}
    
	/**
     * 查询流程定义列表
     * @return
     */
	public String findAll() {
		List<ProcessDefinition> processDefinitionList = workflowDefinitionService.findAll();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		return "workflowDefinitionList";
	}
	
	public String findByPage() {
		Page<ProcessDefinition> page = new Page<ProcessDefinition>(PageUtil.PAGE_SIZE);
	    int[] pageParams = PageUtil.init(page, request);
	    workflowDefinitionService.findByPage(page, pageParams[0], pageParams[1]);
	    request.setAttribute("page", page);
	    return "workflowDefinitionList";
	}
	
	/**
	 * 加载当前人能够启动的流程定义
	 * @return
	 */
	public String findByUserId() {
		User user = (User)HttpSessionControl.get(HttpSessionControl.USER);
    	String userId = user.getAccountSet().iterator().next().getUserName();
		try {
			List<ProcessDefinition> processDefinitionList = workflowDefinitionService.findByUserId(userId);
			results.put("processDefinitionList", processDefinitionList);
		} catch (WsnException e) {
			e.printStackTrace();
		}
		return "newTaskList";
	}
	
	/**
	 * 加载流程定义
	 * @return
	 */
	public String load() {
		String processDefinitionId = ServletActionContext.getRequest().getParameter("processDefinitionId");
		ProcessDefinition processDefinition = workflowDefinitionService.load(processDefinitionId);
		ActionContext.getContext().put("processDefinition", processDefinition);
		return SUCCESS;
	}

	/**
	 * 加载流程定义资源
	 */
	public void loadResource() {
		String processDefinitionId = ServletActionContext.getRequest().getParameter("processDefinitionId");
		String resourceType = ServletActionContext.getRequest().getParameter("resourceType");
		InputStream inpuStream = workflowDefinitionService.loadResource(processDefinitionId, resourceType);
		byte[] bytes = new byte[1024];
		int len = -1;
		try {
			while((len = inpuStream.read(bytes, 0, 1024)) != -1) {
				response.getOutputStream().write(bytes, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inpuStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 转换成model
	 */
	public String convert2Model() {
		String processDefinitionId = request.getParameter("processDefinitionId");
		try {
			workflowDefinitionService.convert2Model(processDefinitionId);
		} catch (Exception e) {
			logger.error("流程定义Id为{}的流程转换model失败!", processDefinitionId);
			e.printStackTrace();
		}
		Page<Model> page = new Page<Model>(PageUtil.PAGE_SIZE);
	    int[] pageParams = PageUtil.init(page, request);
	    workflowModelService.findByPage(page, pageParams[0], pageParams[1]);
	    request.setAttribute("page", page);
		return "workflowModelList";
	}
	
	/**
	 * 激活或者挂起流程实例
	 * @return
	 */
	public String updateState() {
		String state = ServletActionContext.getRequest().getParameter("state");
		String processDefinitionId = ServletActionContext.getRequest().getParameter("processDefinitionId");
		workflowDefinitionService.updateState(processDefinitionId, state);
		return findByPage();
	}
	
	/**
	 * 删除流程定义
	 * 必须配置customFormTypes
	 * @return
	 */
	public String delete() {
		String deploymentId = ServletActionContext.getRequest().getParameter("deploymentId");
		String processDefinitionId = ServletActionContext.getRequest().getParameter("processDefinitionId");
		workflowDefinitionService.delete(deploymentId, processDefinitionId, true);
		return findByPage();
	}
	
	public IWorkflowDefinitionService getWorkflowDefinitionService() {
		return workflowDefinitionService;
	}

	@Autowired
	public void setWorkflowDefinitionService(
			IWorkflowDefinitionService workflowDefinitionService) {
		this.workflowDefinitionService = workflowDefinitionService;
	}

	public File getDeployFile() {
		return deployFile;
	}

	public void setDeployFile(File deployFile) {
		this.deployFile = deployFile;
	}

	public String getDeployFileFileName() {
		return deployFileFileName;
	}

	public void setDeployFileFileName(String deployFileFileName) {
		this.deployFileFileName = deployFileFileName;
	}

	public String getDeployFileContentType() {
		return deployFileContentType;
	}

	public void setDeployFileContentType(String deployFileContentType) {
		this.deployFileContentType = deployFileContentType;
	}


	
}
