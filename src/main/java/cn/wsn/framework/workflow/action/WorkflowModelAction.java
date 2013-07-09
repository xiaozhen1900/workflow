/**
 * 
 */
package cn.wsn.framework.workflow.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.wsn.framework.workflow.service.IWorkflowModelService;
import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.PageUtil;
import cn.wsn.framework.workflow.util.WorkflowConstants;

import com.opensymphony.xwork2.ActionContext;
import com.wsn.common.action.BaseAction;

/**
 * @author guoqiang
 *
 */
@Controller("/model")
@Scope("prototype")
public class WorkflowModelAction extends BaseAction {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private IWorkflowModelService workflowModelService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3693716865229000428L;
	
	/**
	 * 获取工作流模型列表
	 * @return
	 */
	public String findAll() {
		ActionContext.getContext().put("modelList", workflowModelService.findAll());
		return "workflowModelList";
	}
	
	/**
	 * 获取工作流模型列表
	 * @return
	 */
	public String findByPage() {
		Page<Model> page = new Page<Model>(PageUtil.PAGE_SIZE);
	    int[] pageParams = PageUtil.init(page, request);
	    workflowModelService.findByPage(page, pageParams[0], pageParams[1]);
	    request.setAttribute("page", page);
		return "workflowModelList";
	}
	
	/**
	 * 创建工作流模型
	 * @return
	 */
	public void create() {
		String key = request.getParameter("key");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		Model model = workflowModelService.create(key, name, description);
		try {
			// model编辑器通过restlet跳转过去
			response.sendRedirect(request.getContextPath() + "/service/editor?id=" + model.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据modelId部署流程
	 * @return
	 */
	public String deploy() {
		String modelId = request.getParameter("modelId");
		workflowModelService.deploy(modelId);
		return findByPage();
	}
	
	/**
	 * 导出model的xml文件
	 * @return
	 */
	public void export() {
		String modelId = request.getParameter("modelId");
		try {
			BpmnModel bpmnModel = workflowModelService.export(modelId);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			String filename = bpmnModel.getMainProcess().getId()
					+ WorkflowConstants.WORKFLOW_DEFINITION_FILE_RESOURCE_SUFFIX_BPMN;
			IOUtils.copy(in, response.getOutputStream());
			response.setHeader("Content-Disposition", "attachment; filename="
					+ filename);
			response.flushBuffer();
		} catch (IOException e) {
			logger.error("导出model的xml文件失败：modelId={}", modelId, e);
			e.printStackTrace();
		}
	}

	/**
	 * 删除流程model
	 * @return
	 */
	public String delete() {
		String modelId = request.getParameter("modelId");
		workflowModelService.delete(modelId);
		return findByPage();
	}

	public IWorkflowModelService getWorkflowModelService() {
		return workflowModelService;
	}

	@Autowired
	public void setWorkflowModelService(IWorkflowModelService workflowModelService) {
		this.workflowModelService = workflowModelService;
	}
	
	
}
