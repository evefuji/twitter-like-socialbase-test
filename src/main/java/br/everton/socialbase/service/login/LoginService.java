package br.everton.socialbase.service.login;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.everton.socialbase.controller.exception.RestForbiddenException;
import br.everton.socialbase.entity.login.SystemUser;
import br.everton.socialbase.entity.login.TokenRequest;
import br.everton.socialbase.entity.login.TokenResponse;
import br.everton.socialbase.entity.login.UserResponse;

@Stateless
public class LoginService {

	@PersistenceContext(unitName = "TwitterLikePU")
	private EntityManager em;
	
	private LoginAccessData accessData = new LoginAccessData();

	public TokenResponse findToken(String code) {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(accessData.getAccessTokenUri()); 
		
		TokenRequest oAuthRequest = new TokenRequest();
		oAuthRequest.setClientId(accessData.getClientId());
		oAuthRequest.setClientSecret(accessData.getClientSecret());
		oAuthRequest.setCode(code);
		oAuthRequest.setGrantType("authorization_code");
		oAuthRequest.setRedirectUri(accessData.getRedirectUri());
		
		Builder request = target.request();
		request.accept(MediaType.APPLICATION_JSON);
		Response response = request.post(Entity.entity(oAuthRequest, MediaType.APPLICATION_JSON));
		if(response.getStatus()==200) {
			return response.readEntity(TokenResponse.class);
		}else {
			throw new RestForbiddenException(response.readEntity(String.class));
		}
	}
	
	public UserResponse getUser(String token) {

		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(accessData.getSearchUserFromTokenUri());
		
		Builder request = target.queryParam("access_token", token).request();
		request.accept(MediaType.APPLICATION_JSON);
		Response response = request.get();
		if(response.getStatus()==200) {
			return response.readEntity(UserResponse.class);
		}else {
			throw new RestForbiddenException(response.readEntity(String.class));
		}
	}
	
	public UserResponse getUserAndPersist(String token) {
		
		UserResponse response = getUser(token);
		String uid = response.getId();
		persistUUID(uid);
		return response;
		
	}

	private void persistUUID(String uuid) {
		SystemUser users = findUserByUUID(uuid);
		if(users == null) {
			SystemUser su = new SystemUser();
			su.setUuid(uuid);
			em.persist(su);
		}
	}

	public SystemUser findUserByUUID(String uuid) {
		TypedQuery<SystemUser> tq = 
			em.createQuery(
				"SELECT su FROM SystemUser su WHERE su.uuid = :uuid", SystemUser.class
			);
		
		tq.setParameter("uuid", uuid);
		tq.setMaxResults(1);
		
		List<SystemUser> users = tq.getResultList();
		if(!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}
}
