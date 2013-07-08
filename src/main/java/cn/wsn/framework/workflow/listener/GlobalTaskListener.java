/**
 * 
 */
package cn.wsn.framework.workflow.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author guoqiang
 *
 */
public class GlobalTaskListener implements TaskListener {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6670555987158123018L;

	@Override
	public void notify(DelegateTask delegateTask) {
		 logger.debug("触发了全局监听器, pid={}, tid={}, event={}", new Object[]{
		            delegateTask.getProcessInstanceId(), delegateTask.getId(), delegateTask.getEventName()
		    });
		
	}

}
