package br.everton.socialbase.service.login;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class LoginAccessData {
	
	private String clientId = System.getProperty("login.oauth2.client_id");
	private String clientSecret = System.getProperty("login.oauth2.client_secret");
	private String redirectUri = System.getProperty("login.oauth2.redirect_uri");
	private String accessTokenUri = System.getProperty("login.oauth2.access_token_uri");
	private String searchUserFromTokenUri = System.getProperty("login.oauth2.search_user_from_token_uri");
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	public String getAccessTokenUri() {
		return accessTokenUri;
	}
	public void setAccessTokenUri(String accessTokenUri) {
		this.accessTokenUri = accessTokenUri;
	}
	public String getSearchUserFromTokenUri() {
		return searchUserFromTokenUri;
	}
	public void setSearchUserFromTokenUri(String searchUserFromTokenUri) {
		this.searchUserFromTokenUri = searchUserFromTokenUri;
	}
}