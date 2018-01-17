package br.everton.socialbase.entity.login;

import org.jboss.security.SimplePrincipal;

public class OAuthPrincipal extends SimplePrincipal{

	private static final long serialVersionUID = 1L;

	private String name;
	private SystemUser systemUser;
	
	public OAuthPrincipal(String name) {
		super(name);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SystemUser getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}
	
}
