/**
 * 
 */
package cn.wsn.framework.workflow.action;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.PageUtil;

import com.wsn.common.action.BaseAction;

/**
 * @author guoqiang
 *
 */
@Controller("/user")
@Scope("prototype")
public class WorkflowUserAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -447399348758851982L;
	
	private UserEntity model;
	public UserEntity getModel() {
		return model;
	}

	public void setModel(UserEntity model) {
		this.model = model;
	}
	
	@Autowired
	private IdentityService identifyService;
	
	public String save() {
		identifyService.saveUser(model);
		return findByPage();
	}

	public String update() {
		identifyService.saveUser(model);
		return findByPage();
	}
	
	public String delete() {
		JSONObject dtoData = jsonParamsObj.getJSONObject("model");
		identifyService.deleteUser(dtoData.getString("id"));
		return SUCCESS;
	}
	
	public String findById() {
		try {
			JSONObject json = jsonParamsObj.getJSONObject("model");
			UserEntity model = (UserEntity) identifyService.createUserQuery().userId(json.getString("id")).singleResult();
			results.put("model", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String findByPage() {
		Page<User> page = new Page<User>(PageUtil.PAGE_SIZE);
		int[] params = PageUtil.init(page, request);
		UserQuery query = identifyService.createUserQuery().orderByUserId().asc();
		List<User> result = query.listPage(params[0], params[1]);
		page.setTotalCount(query.count());
		page.setResult(result);
		request.setAttribute("page", page);
		return "userList";
	}
}
