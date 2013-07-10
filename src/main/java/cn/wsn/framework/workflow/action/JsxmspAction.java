/** 
 * @Title: JsxmspAction.java 
 * @Package cn.wsn.framework.jsxmsp 
 * @Description: TODO
 * @author steveguoshuai 
 * @date 2013-5-27 下午9:32:44 
 * @version V1.0 
 */  
package cn.wsn.framework.workflow.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.wsn.framework.workflow.entity.Jsxmsp;
import cn.wsn.framework.workflow.service.impl.JsxmspServiceImpl;

import com.opensymphony.xwork2.ActionContext;
import com.wsn.common.action.BaseAction;

/** 
 * @ClassName: JsxmspAction 
 * @Description: TODO
 * @author steveguoshuai
 * @date 2013-5-27 下午9:32:44 
 *  
 */
@Controller("/jsxmsp")
@Scope("prototype")
public class JsxmspAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private JsxmspServiceImpl jsxmspService;
	
	private Jsxmsp model;
	
	public String save() {
		jsxmspService.save(model);
		return SUCCESS;
	}
	
	public String update() {
		jsxmspService.update(model);
		return SUCCESS;
	}
	
	public String get() {
		model = jsxmspService.get(model.getXmbh());
		return SUCCESS;
	}
	
	public String findAll() {
		List<Jsxmsp> jsxmspList = jsxmspService.findAll();
		ActionContext.getContext().put("jsxmspList", jsxmspList);
		return SUCCESS;
	}
	
	public String delete() {
		jsxmspService.delete(model.getXmbh());
		return SUCCESS;
	}
	
	public String startWorkflow() {
		String processDefinitionId = request.getParameter("processDefinitionId");
		Map<String, Object> variables = new HashMap<String, Object>();
		jsxmspService.startWorkflow(processDefinitionId, model, variables);
		return SUCCESS;
	}

	public Jsxmsp getModel() {
		return model;
	}

	public void setModel(Jsxmsp jsxmsp) {
		this.model = jsxmsp;
	}

}
