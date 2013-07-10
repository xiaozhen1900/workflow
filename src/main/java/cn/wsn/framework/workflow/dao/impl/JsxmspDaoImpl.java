/** 
 * @Title: JsxmspDaoImpl.java 
 * @Package cn.wsn.framework.dao.impl 
 * @Description: TODO
 * @author steveguoshuai 
 * @date 2013-5-27 下午10:30:47 
 * @version V1.0 
 */  
package cn.wsn.framework.workflow.dao.impl;

import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import cn.wsn.framework.workflow.dao.JsxmspDao;

import com.wsn.common.dao.impl.BaseDAOImpl;

/** 
 * @ClassName: JsxmspDaoImpl 
 * @Description: TODO
 * @author steveguoshuai
 * @date 2013-5-27 下午10:30:47 
 *  
 */
@Repository("jsxmspDao")
public class JsxmspDaoImpl extends BaseDAOImpl implements JsxmspDao {

	@Autowired
	@Required
	public void setHibTemplate(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@Override
	public ProcessInstance startWorkflow(String processDefinitionId,
			String businessKey, Map<String, Object> variables) {
		// TODO Auto-generated method stub
		return null;
	}

}
