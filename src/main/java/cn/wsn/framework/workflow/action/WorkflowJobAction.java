/**
 * 
 */
package cn.wsn.framework.workflow.action;

import java.util.List;

import org.activiti.engine.ManagementService;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.JobQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.wsn.framework.action.BaseAction;
import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.PageUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author guoqiang
 *
 */
@Controller("/job")
@Scope("prototype")
public class WorkflowJobAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5780032986656909369L;
	
	@Autowired
	private ManagementService managementService;
	
	public String findAll() {
		List<Job> jobList = managementService.createJobQuery().orderByJobId().asc().list();
		ActionContext.getContext().put("jobList", jobList);
		return "workflowJobList";
	}
	
	public String findByPage() {
		Page<Job> page = new Page<Job>(PageUtil.PAGE_SIZE);
	    int[] pageParams = PageUtil.init(page, request);
	    JobQuery query = managementService.createJobQuery().orderByJobId().asc();
	    List<Job> jobList = query.listPage(pageParams[0], pageParams[1]);
	    page.setTotalCount(query.count());
	    page.setResult(jobList);
	    request.setAttribute("page", page);
	    return "workflowJobList";
	}
	
	public String getJobById() {
		String jobId = request.getParameter("jobId");
		Job job = managementService.createJobQuery().jobId(jobId).singleResult();
		ActionContext.getContext().put("job", job);
		return SUCCESS;
	}
	
	public String executeJob(){
		String jobId = request.getParameter("jobId");
		managementService.executeJob(jobId);
		return SUCCESS;
	}
	
	public String deleteJob(){
		String jobId = request.getParameter("jobId");
		managementService.deleteJob(jobId);
		return SUCCESS;
	}

}
