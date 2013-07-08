package cn.wsn.framework.workflow.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;

public interface IWorkflowHistoryService {

	/**
	 * 获取历史活动实例查询
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	HistoricActivityInstanceQuery getActivityInstanceQuery(
			String processInstanceId);

	/**
	 * 获取当前人的活动实例查询
	 * @param userId 用户ID
	 * @param isCompleted 是否结束
	 * @return
	 */
	HistoricActivityInstanceQuery getActivityInstanceQuery(
			String userId, boolean isCompleted);
	
	/**
	 * 分页查找历史活动实例
	 * @param query
	 * @param firstResult
	 * @param maxResult
	 */
	List<HistoricActivityInstance> getActivityInstanceByPage(HistoricActivityInstanceQuery query, int[] pageParams);
	
	/**
	 * 获取历史任务实例查询
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	HistoricTaskInstanceQuery getHistoricTaskInstanceQuery(
			String processInstanceId);

	/**
	 * 获取当前人的任务实例查询
	 * @param userId 用户ID
	 * @param isCompleted 是否结束
	 * @return
	 */
	HistoricTaskInstanceQuery getHistoricTaskInstanceQuery(
			String userId, boolean isCompleted);

	/**
	 * 分页查找历史任务实例
	 * @param query
	 * @param pageParams
	 * @return
	 */
	List<HistoricTaskInstance> getHistoricTaskInstanceByPage(HistoricTaskInstanceQuery query, int[] pageParams);
	
	/**
	 * 流程跟踪图
	 * @param processInstanceId		流程实例ID
	 * @return	封装了各种节点信息
	 */
	List<Map<String, Object>> traceWorkflow(
			String processInstanceId) throws Exception;

}