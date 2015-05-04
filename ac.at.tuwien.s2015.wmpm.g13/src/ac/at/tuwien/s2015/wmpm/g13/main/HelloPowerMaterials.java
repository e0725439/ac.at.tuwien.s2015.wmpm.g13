/**
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.main;

import java.io.IOException;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

/**
 * @author thunder
 *
 */
public class HelloPowerMaterials extends AbstractHandler {
	
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().println("<h1>188.924 Workflow Modeling and Process Management</h1>");
		response.getWriter().println("<h2>Project: POWERMATERIALS</h2>");
	}
}
