/**
 * 
 */
package cn.wsn.framework.workflow.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.wsn.framework.workflow.entity.ProcdefGroup;

/**
 * @author guoqiang
 *
 */
public interface ProcdefGroupDao {
	
	/**
	 * 根据流程定义ID查找
	 * @param processDefinitionId
	 * @return
	 * @throws DataAccessException
	 */
	List<ProcdefGroup> findByProcdefId(String processDefinitionId)
			throws DataAccessException;

	/**
	 * 根据用户组ID查找
	 * @param groupId
	 * @return
	 * @throws DataAccessException
	 */
	List<ProcdefGroup> findByGroupId(String groupId)
			throws DataAccessException;
	
	/**
	 * 获得数据库表的总记录数
	 * @param  params	   查询参数
	 * @return int		   数据库表的总记录数 
	 * */
	int getRowCount(String hql, List<Object> params);
	
	/**
	 * 根据hql语句删除记录
	 * @param hql
	 * @param obj
	 * @throws DataAccessException
	 */
	void deleteByHql(final String hql, final Object[] obj)
			throws DataAccessException ;
	
}
