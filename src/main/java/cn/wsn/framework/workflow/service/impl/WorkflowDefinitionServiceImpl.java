/**
 * 
 */
package cn.wsn.framework.workflow.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.wsn.framework.workflow.cache.WorkflowDefinitionCache;
import cn.wsn.framework.workflow.entity.ProcdefGroup;
import cn.wsn.framework.workflow.service.IWorkflowDefinitionService;
import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.WorkflowConstants;
import cn.wsn.framework.workflow.util.WorkflowState;
import cn.wsn.framework.workflow.util.WorkflowUtils;

import com.wsn.common.exception.WsnException;

/**
 * @author guoqiang
 * 
 */
@Service("workflowDefinitionService")
public class WorkflowDefinitionServiceImpl implements
		IWorkflowDefinitionService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private ProcdefGroupServiceImpl procdefGroupService;
	
	@Value("#{ACTIVITI_PROPERTIES['export.diagram.path']}")
	private String exportDir;

	/**
	 * 部署流程定义
	 * 
	 * @return
	 */
	public void deploy(File deployFile,
			String deployFileFileName) {
		Deployment deployment = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(deployFile);
			String suffix = FilenameUtils.getExtension(deployFileFileName);
			if (WorkflowConstants.WORKFLOW_DEPLOY_FILE_RESOURCE_SUFFIX_ZIP
					.equalsIgnoreCase(suffix)
					|| WorkflowConstants.WORKFLOW_DEPLOY_FILE_RESOURCE_SUFFIX_BAR
							.equalsIgnoreCase(suffix)) {
				ZipInputStream zipInputStream = new ZipInputStream(fis);
				deployment = repositoryService.createDeployment()
						.addZipInputStream(zipInputStream).deploy();
			} else {
				deployment = repositoryService.createDeployment()
						.addInputStream(deployFileFileName, fis).deploy();
			}

			// 通过部署ID加载流程定义并导出图片到磁盘上
			List<ProcessDefinition> processDefinitionList = repositoryService
					.createProcessDefinitionQuery()
					.deploymentId(deployment.getId()).list();
			for (ProcessDefinition processDefinition : processDefinitionList) {
				try {
					WorkflowUtils.exportDiagramToFile(repositoryService,
							processDefinition, exportDir);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			logger.debug("部署流程文件{}成功！", deployFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 加载流程定义资源
	 */
	public InputStream loadResource(String processDefinitionId, String resourceType) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		String resourceName = "";
		if(WorkflowConstants.WORKFLOW_DEFINITION_FILE_RESOURCE_SUFFIX_XML.equals(resourceType)) {
			resourceName = processDefinition.getResourceName();
		} else if(WorkflowConstants.WORKFLOW_DEFINITION_FILE_RESOURCE_SUFFIX_IMAGE.equals(resourceType)) {
			resourceName = processDefinition.getDiagramResourceName();
		}
		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		return resourceAsStream;
	}

	@Override
	public List<ProcessDefinition> findAll() {
		return repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionId().desc().list();
	}

	
	@Override
	public ProcessDefinition load(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		return processDefinition;
	}

	@Override
	public void updateState(String processDefinitionId, String state) {
		if(WorkflowState.ACTIVE.toString().equalsIgnoreCase(state)) {
			repositoryService.activateProcessDefinitionById(processDefinitionId);
		} else if(WorkflowState.SUSPENDED.toString().equalsIgnoreCase(state)) {
			repositoryService.suspendProcessDefinitionById(processDefinitionId);
		}
	}

	@Override
	public void delete(String deploymentId, String processDefinitionId, boolean cascade) {
		repositoryService.deleteDeployment(deploymentId, cascade);
		if(cascade) {
			String hql = "delete from ProcdefGroup where processdefinitionid=?";
			try {
				procdefGroupService.deleteByHql(hql, new Object[]{processDefinitionId});
			} catch (WsnException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 需要处理启动人员用户组与流程定义的关系，
	 * 1.新建一张流程定义与用户组的关系表，添加一个管理流程启动权限的功能。
	 * 2.根据当前用户所在用户组查询出有哪些可以发起的流程。
	 * 
	 */
	@Override
	public List<ProcessDefinition> findByUserId(String userId) throws WsnException {
		List<ProcessDefinition> processDefinitionList = new ArrayList<ProcessDefinition>();
		Set<String> processDefinitionIdSet = new HashSet<String>();
		List<Group>  groupList = identityService.createGroupQuery().groupMember(userId).orderByGroupId().asc().list();
		for(Group group : groupList) {
			List<ProcdefGroup> procdefGroupList = procdefGroupService.findByGroupId(group.getId());
			if(null != procdefGroupList && procdefGroupList.size() > 0) {
				for(ProcdefGroup procdef : procdefGroupList) {
					processDefinitionIdSet.add(procdef.getProcessdefinitionid());
				}
			}
		}
		if(null != processDefinitionIdSet && processDefinitionIdSet.size() > 0) {
			for(String processDefinitionId : processDefinitionIdSet) {
				processDefinitionList.add(WorkflowDefinitionCache.getWorkflowDefinition(processDefinitionId));
			}
		}
		return processDefinitionList;
	}

	@Override
	public void findByPage(Page<ProcessDefinition> page, int firstResult, int maxResult) {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionId().asc();
		List<ProcessDefinition> definitionList = query.listPage(firstResult, maxResult);
		page.setTotalCount(query.count());
		page.setResult(definitionList);
	}

	@Override
	public void convert2Model(String processDefinitionId) throws UnsupportedEncodingException,XMLStreamException {
	    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
	            .processDefinitionId(processDefinitionId).singleResult();
	    InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
	            processDefinition.getResourceName());
	    XMLInputFactory xif = XMLInputFactory.newInstance();
	    InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
	    XMLStreamReader xtr = xif.createXMLStreamReader(in);
	    BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

	    BpmnJsonConverter converter = new BpmnJsonConverter();
	    ObjectNode modelNode = converter.convertToJson(bpmnModel);
	    Model modelData = repositoryService.newModel();
	    modelData.setKey(processDefinition.getKey());
	    modelData.setName(processDefinition.getResourceName());
	    modelData.setCategory(processDefinition.getDeploymentId());

	    ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
	    modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
	    modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, processDefinition.getVersion());
	    modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
	    modelData.setMetaInfo(modelObjectNode.toString());

	    repositoryService.saveModel(modelData);
	    repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
	}
}
