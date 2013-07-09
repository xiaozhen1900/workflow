/** 
 * @Title: WorkflowModelServiceImpl.java 
 * @Package cn.wsn.framework.workflow.service.impl 
 * @Description: TODO
 * @author steveguoshuai 
 * @date 2013-5-24 下午6:53:26 
 * @version V1.0 
 */  
package cn.wsn.framework.workflow.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wsn.framework.workflow.service.IWorkflowModelService;
import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.WorkflowConstants;

/** 
 * @ClassName: WorkflowModelServiceImpl 
 * @Description: TODO
 * @author steveguoshuai
 * @date 2013-5-24 下午6:53:26 
 *  
 */
@Service("workflowModelService")
public class WorkflowModelServiceImpl implements IWorkflowModelService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Override
	public List<Model> findAll() {
		return repositoryService.createModelQuery().list();
	}
	
	@Override
	public void findByPage(Page<Model> page, int firstResult, int maxResult) {
		ModelQuery query = repositoryService.createModelQuery().orderByModelId().asc();
		List<Model> modelList = query.listPage(firstResult, maxResult);
		page.setTotalCount(query.count());
		page.setResult(modelList);
	}
	
	@Override
	public Model create(String key, String name, String description) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode editorNode = mapper.createObjectNode();
		editorNode.put("id", "steveguo");
		editorNode.put("resourceId", "steveguo");
		
		ObjectNode stencilSetNode = mapper.createObjectNode();
	    stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
	    editorNode.put("stencilset", stencilSetNode);
		
		ObjectNode modelNode = mapper.createObjectNode();
		modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, StringUtils.defaultString(description));
		
		Model model = repositoryService.newModel();
		model.setKey(StringUtils.defaultString(key));
		model.setName(name);
		model.setMetaInfo(modelNode.toString());
		
		repositoryService.saveModel(model);
		try {
			repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return model;
	}

	@Override
	public Deployment deploy(String modelId) {
		Deployment deployment = null;
		Model model = repositoryService.getModel(modelId);
		try {
			ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelId));
			byte[] bpmnByte = null;
			BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			bpmnByte = new BpmnXMLConverter().convertToXML(bpmnModel);
			String processName = model.getKey() + WorkflowConstants.WORKFLOW_DEFINITION_FILE_RESOURCE_SUFFIX_BPMN;
			deployment = repositoryService.createDeployment().name(model.getName()).addString(processName, new String(bpmnByte)).deploy();
			logger.info("message", "部署成功，部署ID=" + deployment.getId());
		} catch (JsonProcessingException e) {
			logger.error("根据模型部署流程失败：modelId={}", modelId, e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("根据模型部署流程失败：modelId={}", modelId, e);
			e.printStackTrace();
		}
		return deployment;
	}

	@Override
	public BpmnModel export(String modelId) {
		BpmnModel bpmnModel = null;
		Model modelData = repositoryService.getModel(modelId);
		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
		JsonNode editorNode;
		try {
			editorNode = new ObjectMapper().readTree(repositoryService
					.getModelEditorSource(modelData.getId()));
			bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bpmnModel;
	}

	@Override
	public void delete(String modelId) {
		repositoryService.deleteModel(modelId);
	}
}
