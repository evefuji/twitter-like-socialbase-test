package br.everton.socialbase.controller.login;

import java.io.IOException;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimpleGroup;
import org.jboss.security.auth.spi.AbstractServerLoginModule;

import br.everton.socialbase.entity.login.OAuthPrincipal;
import br.everton.socialbase.entity.login.SystemUser;
import br.everton.socialbase.entity.login.UserResponse;
import br.everton.socialbase.service.login.LoginService;

public class LoginModule extends AbstractServerLoginModule implements javax.security.auth.spi.LoginModule {
    
	private static final String AUTH_TYPE = "Beerus ";
	private OAuthPrincipal principal;
	private UserResponse user;
	private String token;

	public LoginModule() {
	}

	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {

		this.callbackHandler = callbackHandler;
		this.subject = subject;
		this.sharedState = sharedState;
		this.options = options;

		loginOk = false;
		super.initialize(subject, callbackHandler, sharedState, options);
	}

	public boolean login() throws LoginException {
		setLoginModuleParameters();
		
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("name:");
		callbacks[1] = new PasswordCallback("token:", false);

		try {
			callbackHandler.handle(callbacks);
		} catch (IOException e) {
			throw new LoginException("Oops, IOException calling handle on callbackHandler");
		} catch (UnsupportedCallbackException e) {
			throw new LoginException("Oops, UnsupportedCallbackException calling handle on callbackHandler");
		}
		
		NameCallback nameCallback = (NameCallback) callbacks[0];
		PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];

		String name = nameCallback.getName();
		token = new String(passwordCallback.getPassword());

		if(name.equals("OAuth") && token.startsWith(AUTH_TYPE)) {

			token = token.substring(AUTH_TYPE.length());
			
			LoginService loginService = lookupLoginService();

			user = loginService.getUser(token);
			SystemUser su = loginService.findUserByUUID(user.getId());
			
			principal = new OAuthPrincipal(user.getName());
			principal.setSystemUser(su);

			subject.getPrincipals().add(principal);
			
			if (su != null) {
				loginOk = true;
				return loginOk;
			} else {
				loginOk = false;
			}
		}
		loginOk = false;
		throw new FailedLoginException("Invalid Name.");
	}

	private LoginService lookupLoginService() {
		try {
			InitialContext ic = new InitialContext();
			return (LoginService) ic.lookup("java:global/twitterLike/LoginService");
		} catch (NamingException e) {
			return null;
		}
	}

	private void setLoginModuleParameters() throws LoginException {
		if (callbackHandler == null) {
			throw new LoginException("CallbackHandler is null");
		}
	}

	public boolean logout() throws LoginException {
		return false;
	}

	@Override
	protected Principal getIdentity() {
		return principal;
	}

	@Override
	protected Group[] getRoleSets() throws LoginException {
		Group[] groups = new Group[2];
		Group roles = new SimpleGroup("Roles");
		groups[1] = roles;
		Group user = new SimpleGroup("user");
		groups[0] = user;
		roles.addMember(user);
		return groups;
	}

}