package dex.coap;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapClient;

public class CoapConnector {
	
	boolean isReceiving;
	String message;
	
	final public CoapHandler getHandler = new CoapHandler() {		// get
		public void onLoad(CoapResponse response) {
			String content = response.getResponseText();
			
			message = content;
			isReceiving = false;
		}

		public void onError() {
			System.err.println("Error");
		}
	};
	
	final public CoapHandler postHandler = new CoapHandler() {		// post
		public void onLoad(CoapResponse response) {
			String content = response.getResponseText();
			
			String[] contents = content.split("@",3);
			String name = contents[0];
			String preValue = contents[1];
			String curValue = contents[2];
			
			System.out.println("POST RESPONSE : "+name+" was changed from "+preValue+" to "+curValue);
		}

		public void onError() {
			System.err.println("OBSERVING FAILED (press enter to exit)");
		}
	};

	public synchronized String request(String address, String port, String ID, String resource) {
		CoapClient client = new CoapClient();
		client.setURI("coap://"+address+":"+port+"/"+ID+"/"+resource);
		isReceiving = true;
		client.get(getHandler);
		
		while(isReceiving) {
			
			try{
				Thread.sleep(10);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		return message;
	}
	// test ****************************************************************************************
	public static void main (String arg[]) {
			CoapConnector connector = new CoapConnector();
			String response = connector.request("localhost", "10000", "1", "value");
			System.out.println(response);
	}
}
