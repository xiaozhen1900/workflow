/**
 * 
 */
package cn.wsn.framework.workflow.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.wsn.framework.workflow.dto.ProcdefGroupDto;
import cn.wsn.framework.workflow.entity.ProcdefGroup;

import com.wsn.common.exception.WsnException;

/**
 * @author guoqiang
 *
 */
public interface IProcdefGroupService {

	public void save(ProcdefGroup entity) throws WsnException;
	
	public void update(ProcdefGroup entity) throws WsnException;
	
	public void delete(Serializable id) throws WsnException;
	
	public List<ProcdefGroupDto> findAll() throws WsnException;
	
	public ProcdefGroup findById(Serializable id) throws WsnException;
	
	/**
	 * 根据流程定义ID查找
	 * @param processDefinitionId
	 * @return
	 */
	public List<ProcdefGroup> findByProcdefId(String processDefinitionId) throws WsnException;
	
	/**
	 * 根据GroupId查找
	 * @param groupId
	 * @return
	 */
	public List<ProcdefGroup> findByGroupId(String groupId) throws WsnException;
	
	/**
	 * 分页查找
	 * @param queryHql
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws WsnException
	 */
	public List<ProcdefGroupDto> findByPage(String queryHql, int firstResult, int maxResults) throws WsnException;
	
	/**
	 * 获取数据计数
	 * @param params
	 * @return
	 * @throws WsnException
	 */
	public int getRowCount(List<Object> params) throws WsnException;
	
	/**
	 * 根据hql语句删除记录
	 * @param hql
	 * @param obj
	 * @throws DataAccessException
	 */
	void deleteByHql(final String hql, final Object[] obj) throws WsnException;
	
	
}
