/**
 * 
 */
package br.everton.socialbase.controller.message;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.everton.socialbase.entity.message.Message;
import br.everton.socialbase.service.message.MessageService;

/**
 * Controller do webservice (tambem conhecido como Resource)
 * @author Everton
 *
 */
@Path("message")
@DeclareRoles({"user"})
@RolesAllowed({"user"})
public class MessageController {

	@Inject
	private MessageService messageService;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Message> findLastMessages(){
		return messageService.find(0, 10);
	}
	

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Message> findMessages(@QueryParam("start") int start){
		return messageService.find(start*10, 10);
	}
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Message create(@Valid Message message) {
		return messageService.persist(message);
	}
}
