package br.everton.socialbase.entity.login;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * usado para requisitar o token de autenticacao
 * @author Everton
 *
 */
@XmlRootElement
public class LoginRequest {

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
