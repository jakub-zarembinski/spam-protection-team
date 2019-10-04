package ai.sygnet.spt;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 * Enable Cross-Origin Resource Sharing (CORS) required by the React client
 */
@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class CORSFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		String origin = requestContext.getHeaderString("Origin");
		if (origin == null) {
			return;
		}
		String method = requestContext.getMethod();
		MultivaluedMap<String, Object> headers = responseContext.getHeaders();
		headers.add("Access-Control-Allow-Origin", origin);
		if (method.equals("OPTIONS")) {
			headers.add("Access-Control-Allow-Methods", "PUT");
			headers.add("Access-Control-Allow-Headers", "Content-Type");
		}
	}
}
