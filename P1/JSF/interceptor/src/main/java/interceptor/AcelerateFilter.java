package interceptor;

import java.util.Random;

public class AcelerateFilter implements Filter {

	public void execute(Double request) {
		Random rnd = new Random();
		System.out.println("Acelerate request: " + (request + rnd.nextDouble() * 10 + 1));
	}
}