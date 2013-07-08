/**
 * 
 */
package cn.wsn.framework.workflow.action;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.wsn.framework.action.BaseAction;
import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.PageUtil;

/**
 * @author guoqiang
 *
 */
@Controller("/group")
@Scope("prototype")
public class WorkflowGroupAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -447399348758851982L;
	
	private GroupEntity model;
	public GroupEntity getModel() {
		return model;
	}

	public void setModel(GroupEntity model) {
		this.model = model;
	}
	
	@Autowired
	private IdentityService identifyService;
	
	public String save() {
		identifyService.saveGroup(model);
		return findByPage();
	}

	public String update() {
		identifyService.saveGroup(model);
		return findByPage();
	}
	
	public String delete() {
		JSONObject dtoData = jsonParamsObj.getJSONObject("model");
		identifyService.deleteGroup(dtoData.getString("id"));
		return SUCCESS;
	}
	
	public String findById() {
		try {
			JSONObject json = jsonParamsObj.getJSONObject("model");
			GroupEntity model = (GroupEntity) identifyService.createGroupQuery().groupId(json.getString("id")).singleResult();
			results.put("model", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String findByPage() {
		Page<Group> page = new Page<Group>(PageUtil.PAGE_SIZE);
		int[] params = PageUtil.init(page, request);
		GroupQuery query = identifyService.createGroupQuery().orderByGroupId().asc();
		List<Group> result = query.listPage(params[0], params[1]);
		page.setTotalCount(query.count());
		page.setResult(result);
		request.setAttribute("page", page);
		return "groupList";
	}
}
