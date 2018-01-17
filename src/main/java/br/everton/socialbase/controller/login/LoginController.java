package br.everton.socialbase.controller.login;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import br.everton.socialbase.entity.login.LoginRequest;
import br.everton.socialbase.entity.login.LoginResponse;
import br.everton.socialbase.entity.login.TokenResponse;
import br.everton.socialbase.entity.login.UserResponse;
import br.everton.socialbase.service.login.LoginService;

@Path("login")
public class LoginController {

	@Inject
	private LoginService loginService;
    
	/**
	 * Consulta o servico de autenticacao OAuth informando o code authorization 
	 * @param tokenRequest
	 * @return
	 */
	@POST
	public LoginResponse login(LoginRequest tokenRequest) {
		TokenResponse tokenResponse = loginService.findToken(tokenRequest.getCode());
		String token = tokenResponse.getAccessToken();
		UserResponse user = loginService.getUserAndPersist(token);
		LoginResponse response = new LoginResponse();
		response.setAccessToken(token);
		response.setUserId(user.getId());
		response.setUserName(user.getName());
		return response;
	}
	
}
