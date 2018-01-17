package br.everton.socialbase.api;

import java.util.Set;

import br.everton.socialbase.controller.handler.ExceptionHandler;
import br.everton.socialbase.controller.login.LoginController;
import br.everton.socialbase.controller.message.MessageController;

/**
*
* @author everton
*/
@javax.ws.rs.ApplicationPath("/rest")
public class Application extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {

    	resources.add(CORSFilter.class);
    	resources.add(ConfigContextResolver.class);
    	resources.add(ExceptionHandler.class);
    	resources.add(MessageController.class);
    	resources.add(LoginController.class);
    	
    }
}
