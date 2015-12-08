/* This class is the start point for the hole architecture.
 * Its responsibility is creating the endpoints so the clients can call the federator.
 */
package rendezvous.federator.api;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class Federator {

	final static Logger logger = Logger.getLogger(Federator.class);

	@SuppressWarnings("restriction")
	public static void main(String[] args) throws IOException {

		logger.info("Starting Federator Server");
		
		HttpServer crunchifyHTTPServer = createHttpServer();
		crunchifyHTTPServer.start();
		
		logger.info(String.format(
				"\nFederator Server started with WADL available at %sapplication.wadl ",
				getFederatorURI())
		);
	}

	private static HttpServer createHttpServer() throws IOException {
		ResourceConfig federatorResourceConfig = new PackagesResourceConfig(
				"rendezvous.federator.api.endpoint.impl");
		return HttpServerFactory.create(getFederatorURI(),
				federatorResourceConfig);
	}

	private static URI getFederatorURI() {
		return UriBuilder.fromUri("http://" + federatorGetHostName() + "/")
				.port(8666).build();
	}

	private static String federatorGetHostName() {
		String hostName = "localhost";
		try {
			hostName = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return hostName;
	}
}
