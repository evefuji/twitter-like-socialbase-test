package br.everton.socialbase.api;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Define as configura&ccedil;&otilde;es de startup dos webservices
 * @author everton
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ConfigContextResolver implements ContextResolver<ObjectMapper>{

	private final ObjectMapper objectMapper;

    public ConfigContextResolver() throws Exception
    {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public ObjectMapper getContext(Class<?> arg0)
    {
        return objectMapper;
    }

}