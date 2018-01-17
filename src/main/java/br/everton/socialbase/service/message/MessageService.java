/**
 * 
 */
package br.everton.socialbase.service.message;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.everton.socialbase.entity.login.OAuthPrincipal;
import br.everton.socialbase.entity.login.SystemUser;
import br.everton.socialbase.entity.message.Message;

/**
 * @author Everton
 *
 */
@Stateless
public class MessageService {

	
	@PersistenceContext(unitName = "TwitterLikePU")
	private EntityManager em;
	
	@Resource 
	private EJBContext ejbContext;

	public List<Message> find(int page, int pageSize) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Message> cq = cb.createQuery(Message.class);
		Root<Message> root = cq.from(Message.class);
		cq.orderBy(cb.desc(root.get("id")));
		
		TypedQuery<Message> tq = em.createQuery(cq);
		tq.setFirstResult(page);
		tq.setMaxResults(pageSize);
		return tq.getResultList();
	}

	public Message persist(Message message) {
		OAuthPrincipal principal = (OAuthPrincipal) ejbContext.getCallerPrincipal();
		SystemUser user = em.find(SystemUser.class, principal.getSystemUser().getId());
		message.setSystemUser(user);
		em.persist(message);
		return message;
	}
	
}
