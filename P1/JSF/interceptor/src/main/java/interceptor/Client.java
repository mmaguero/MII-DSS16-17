package interceptor;

public class Client {

	FilterManager filterManager;

	public void setFilterManager(FilterManager filterManager) {
		this.filterManager = filterManager;
	}

	public void sendRequest(Double request) {
		filterManager.filterRequest(request);
	}
}