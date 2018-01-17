package br.everton.socialbase.entity.login;

/**
 * Usado para consultar o usuario no servidor de autenticacao ao informar um token
 * @author Everton
 *
 */
public class UserResponse {
	
	private String name;
	private String id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
