package rendezvous.federator.api;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

public class Federator {

	public static void main(String[] args) throws IOException {

		System.out.println("Starting Federator Embedded Jersey HTTPServer...\n");
		HttpServer crunchifyHTTPServer = createHttpServer();
		crunchifyHTTPServer.start();
		System.out.println(
				String.format("\nJersey Application Server started with WADL available at " + "%sapplication.wadl\n",
						getFederatorURI()));
		System.out.println("Started Federator Embedded Jersey HTTPServer Successfully !!!");
	}

	private static HttpServer createHttpServer() throws IOException {
		ResourceConfig federatorResourceConfig = new PackagesResourceConfig("rendezvous.federator.api.endpoint.impl");
		// This tutorial required and then enable below line:
		// http://crunfy.me/1DZIui5
		// crunchifyResourceConfig.getContainerResponseFilters().add(CrunchifyCORSFilter.class);
		return HttpServerFactory.create(getFederatorURI(), federatorResourceConfig);
	}

	private static URI getFederatorURI() {
		return UriBuilder.fromUri("http://" + federatorGetHostName() + "/").port(8085).build();
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
