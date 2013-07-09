/**
 * 
 */
package cn.wsn.framework.workflow.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.wsn.framework.workflow.dao.ProcdefGroupDao;
import cn.wsn.framework.workflow.entity.ProcdefGroup;

import com.wsn.common.dao.impl.BaseDAOImpl;

/**
 * @author guoqiang
 *
 */
@Repository("procdefGroupDao")
public class ProcdefGroupDaoImpl extends BaseDAOImpl implements ProcdefGroupDao {

	@Autowired
	@Required
	public void setHibTemplate(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcdefGroup> findByProcdefId(String processDefinitionId)
			throws DataAccessException {
		return getHibernateTemplate().find("from ProcdefGroup where processDefinitionId=?", processDefinitionId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcdefGroup> findByGroupId(String groupId)
			throws DataAccessException {
		return getHibernateTemplate().find("from ProcdefGroup where groupId=?", groupId);
	}

	@Override
	public int getRowCount(String hql, List<Object> params) {
		int count = 0;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if(null != params && params.size() > 0) {
			int size = params.size();
			for (int i = 0; i < size; i++) {
				query.setParameter(i, params.get(i));
			}
		}
		Long countLong = (Long) query.uniqueResult();
		count = countLong.intValue();
		return count;
	}

	@Override
	public void deleteByHql(final String hql, final Object[] obj)
			throws DataAccessException {
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				if(null != obj) {
					int length = obj.length;
					for(int i=0;i<length;i++) {
						query.setParameter(i, obj[i]);
					}
				}
				return query.executeUpdate();
			}
		});

	}

}