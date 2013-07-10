/** 
 * @Title: JsxmspServiceImpl.java 
 * @Package cn.wsn.framework.jsxmsp.service.impl 
 * @Description: TODO
 * @author steveguoshuai 
 * @date 2013-5-27 下午10:11:16 
 * @version V1.0 
 */  
package cn.wsn.framework.workflow.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wsn.framework.workflow.dao.impl.JsxmspDaoImpl;
import cn.wsn.framework.workflow.entity.Jsxmsp;
import cn.wsn.framework.workflow.service.IJsxmspService;

import com.wsn.common.entity.User;
import com.wsn.common.util.HttpSessionControl;

/** 
 * @ClassName: JsxmspServiceImpl 
 * @Description: TODO
 * @author steveguoshuai
 * @date 2013-5-27 下午10:11:16 
 *  
 */
@Service("jsxmspService")
public class JsxmspServiceImpl implements IJsxmspService {

	@Autowired
	private JsxmspDaoImpl jsxmspDao;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private IdentityService identityService;
	
	@Override
	public void save(Jsxmsp jsxmsp) {
		jsxmspDao.save(jsxmsp);
	}

	@Override
	public Jsxmsp get(String xmbh) {
		Jsxmsp jsxmsp = (Jsxmsp) jsxmspDao.getById(Jsxmsp.class, xmbh);
		return jsxmsp;
	}

	@Override
	public void update(Jsxmsp jsxmsp) {
		jsxmspDao.updateInstance(jsxmsp);
	}

	@Override
	public void delete(String xmbh) {
		jsxmspDao.deleteInstance(Jsxmsp.class, xmbh);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Jsxmsp> findAll() {
		List<Jsxmsp> jsxmspList = (List<Jsxmsp>) jsxmspDao.listAll(Jsxmsp.class);
		return jsxmspList;
	}

	@Override
	public ProcessInstance startWorkflow(String processDefinitionId,
			Jsxmsp jsxmsp, Map<String, Object> variables) {
		User user = (User)HttpSessionControl.get(HttpSessionControl.USER);
    	String userId = user.getAccountSet().iterator().next().getUserName();
		jsxmsp.setCjr(userId);
		jsxmsp.setCjsj(new Date());
		jsxmspDao.save(jsxmsp);
		identityService.setAuthenticatedUserId(userId);
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, jsxmsp.getXmbh(), variables);
		jsxmsp.setProcessinstanceid(processInstance.getProcessInstanceId());
		jsxmspDao.updateInstance(jsxmsp);
		return processInstance;
	}

}
