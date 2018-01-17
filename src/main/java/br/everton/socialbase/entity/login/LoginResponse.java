package br.everton.socialbase.entity.login;

/**
 * usado para responder ao login do usuario
 * @author Everton
 *
 */
public class LoginResponse {

	private String accessToken;
	private String userName;
	private String userId;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
