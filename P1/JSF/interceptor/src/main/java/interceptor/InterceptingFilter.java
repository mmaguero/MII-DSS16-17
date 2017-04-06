package interceptor;

import java.util.Random;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class InterceptingFilter {

	public static void main(String[] args) throws URISyntaxException, IOException {
		String url = "http://localhost:8080/interceptor/home.jsf";

		FilterManager filterManager = new FilterManager(new Target());
		filterManager.setFilter(new AcelerateFilter());

		Client client = new Client();
		client.setFilterManager(filterManager);
		Random rnd = new Random();
		Double random = rnd.nextDouble() * 150 + 40;
		client.sendRequest(random);

		System.out.println("Main request: " + random.toString());

		if (Desktop.isDesktopSupported()) {
			// Windows
			URI urlW = new URI(url);
			Desktop.getDesktop().browse(urlW);
		} else {
			// Ubuntu
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("/usr/bin/firefox -new-window " + url);
		}

	}

}