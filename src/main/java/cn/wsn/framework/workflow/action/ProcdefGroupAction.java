/**
 * 
 */
package cn.wsn.framework.workflow.action;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.wsn.framework.workflow.dto.ProcdefGroupDto;
import cn.wsn.framework.workflow.entity.ProcdefGroup;
import cn.wsn.framework.workflow.service.IProcdefGroupService;
import cn.wsn.framework.workflow.util.Page;
import cn.wsn.framework.workflow.util.PageUtil;

import com.wsn.common.action.BaseAction;
import com.wsn.common.exception.WsnException;

/**
 * @author guoqiang
 *
 */
@Controller("/procdefGroup")
@Scope("prototype")
public class ProcdefGroupAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7734801134384112518L;
	
	@Autowired
	private IProcdefGroupService procdefGroupService;
	
	private ProcdefGroup model;
	
	public ProcdefGroup getModel() {
		return model;
	}

	public void setModel(ProcdefGroup model) {
		this.model = model;
	}

	public String save() {
		try {
			procdefGroupService.save(model);
		} catch (WsnException e) {
			e.printStackTrace();
		}
		return findByPage();
	}
	
	public String update() {
		try {
			procdefGroupService.update(model);
		} catch (WsnException e) {
			e.printStackTrace();
		}
		return findByPage();
	}
	
	public String delete() {
		try {
			JSONObject dtoData = jsonParamsObj.getJSONObject("model");
			procdefGroupService.delete(dtoData.getInt("id"));
		} catch (WsnException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String findById() {
		try {
			JSONObject dtoData = jsonParamsObj.getJSONObject("model");
			results.put("model", procdefGroupService.findById(dtoData.getInt("id")));
		} catch (WsnException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String findAll(){
		try {
			List<ProcdefGroupDto> dtoList = procdefGroupService.findAll();
			results.put("procdefGroupList", dtoList);
		} catch (WsnException e) {
			e.printStackTrace();
		}
		return "procdefGroupList";
	}
	
	public String findByPage() {
		try {
			String queryHql = "from ProcdefGroup where 1=1";
			Page<ProcdefGroupDto> page = new Page<ProcdefGroupDto>(PageUtil.PAGE_SIZE);
			int[] pageParams = PageUtil.init(page, request);
			List<ProcdefGroupDto> dtoList = procdefGroupService.findByPage(queryHql, pageParams[0], pageParams[1]);
			page.setResult(dtoList);
			page.setTotalCount(procdefGroupService.getRowCount(null));
			request.setAttribute("page", page);
		} catch (WsnException e) {
			e.printStackTrace();
		}
		return "procdefGroupList";
	}
	
}
