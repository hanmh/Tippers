package practice;

public class SQLConnectionManager {
	public static SQLConnectionManager _instance;
	
	public static SQLConnectionManager getInstance() {
		if (_instance == null) _instance = new SQLConnectionManager(Constants.SERVER_URL);
		return _instance;
	}
	
	public String serverUrl;
	
	private SQLConnectionManager(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	public String getData() {
		return "Data";
	}
}

public class Entry {
	public static void main(String[] args) {
		SQLConnectionManager m = SQLConnectionManager.getInstance();
		m.getData();
		
		SQLConnectionManager m2 = SQLConnectionManager.getInstance();
		m.setData(123);
		m2.getData();
	}
}