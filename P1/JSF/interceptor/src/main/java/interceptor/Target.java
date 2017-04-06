package interceptor;

public class Target {

	public void execute(Double request) {
		System.out.println("Executing request: " + request);
	}
}
