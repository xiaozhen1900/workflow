/**
 * 
 */
package cn.wsn.framework.workflow.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wsn.framework.workflow.cache.WorkflowDefinitionCache;
import cn.wsn.framework.workflow.dao.impl.ProcdefGroupDaoImpl;
import cn.wsn.framework.workflow.dto.ProcdefGroupDto;
import cn.wsn.framework.workflow.entity.ProcdefGroup;
import cn.wsn.framework.workflow.service.IProcdefGroupService;

import com.wsn.common.exception.WsnException;

/**
 * @author guoqiang
 *
 */
@Service("procdefGroupService")
public class ProcdefGroupServiceImpl implements IProcdefGroupService {

	private static final String COUNT_HQL = "select count(*) from ProcdefGroup";
	
	@Autowired
	private ProcdefGroupDaoImpl procdefGroupdao;
	
	@Autowired
	private IdentityService identityService;
	
	@Override
	public void save(ProcdefGroup entity) throws WsnException{
		procdefGroupdao.save(entity);
	}

	@Override
	public void update(ProcdefGroup entity) throws WsnException{
		procdefGroupdao.updateInstance(entity);
	}

	@Override
	public void delete(Serializable id) throws WsnException{
		procdefGroupdao.deleteInstance(ProcdefGroup.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcdefGroupDto> findAll() throws WsnException{
		List<ProcdefGroup> procdefGroupList = (List<ProcdefGroup>) procdefGroupdao.listAll(ProcdefGroup.class);
		return convertToDtoList(procdefGroupList);
	}

	@Override
	public ProcdefGroup findById(Serializable id) throws WsnException{
		return (ProcdefGroup) procdefGroupdao.getById(ProcdefGroup.class, id);
	}

	@Override
	public List<ProcdefGroup> findByProcdefId(String processDefinitionId) throws WsnException{
		return procdefGroupdao.findByProcdefId(processDefinitionId);
	}

	@Override
	public List<ProcdefGroup> findByGroupId(String groupId) throws WsnException{
		return procdefGroupdao.findByGroupId(groupId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcdefGroupDto> findByPage(String queryHql, int firstResult,
			int maxResults) {
		List<ProcdefGroup> entityList = (List<ProcdefGroup>) procdefGroupdao.listByPage(queryHql, firstResult, maxResults);
		return convertToDtoList(entityList);
	}
	
	@Override
	public int getRowCount(List<Object> params) throws WsnException {
		return procdefGroupdao.getRowCount(COUNT_HQL, params);
	}

	public List<ProcdefGroupDto> convertToDtoList(List<ProcdefGroup> entityList) {
		if(null != entityList && entityList.size() > 0) {
			List<ProcdefGroupDto> dtoList = new ArrayList<ProcdefGroupDto>();
			for(ProcdefGroup entity : entityList) {
				dtoList.add(convertToDto(entity));
			}
			return dtoList;
		}
		return null;
	}
	
	public ProcdefGroupDto convertToDto(ProcdefGroup entity) {
		if(null != entity) {
			ProcdefGroupDto dto = new ProcdefGroupDto();
			dto.setId(entity.getId());
			dto.setGroupid(entity.getGroupid());
			dto.setProcessdefinitionid(entity.getProcessdefinitionid());
			// 最好用activiti的group，避免与业务耦合
			dto.setGroupName(identityService.createGroupQuery().groupId(entity.getGroupid()).singleResult().getName());
			dto.setProcessDefinitionName(WorkflowDefinitionCache.getWorkflowDefinition(entity.getProcessdefinitionid()).getName());
			return dto;
		}
		return null;
	}

	@Override
	public void deleteByHql(String hql, Object[] obj) throws WsnException {
		procdefGroupdao.deleteByHql(hql, obj);
	}
}
