package dex.webSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.eclipse.californium.core.CoapClient;
import org.glassfish.tyrus.client.ClientManager;

public class WebSocketConnector {
	private static CountDownLatch latch; // allows one or more threads to wait until a set of operations being performed
										 	// in other threads completes.
	ClientManager client;
	
	public WebSocketConnector() {
		latch = new CountDownLatch(1);
		client = ClientManager.createClient();
	}
	
	public String Crequest(String address, String port, String ID, String message) {	// connect and request
		try{
			String url = "ws://"+address+":"+port+"/"+ID+"/data";
			Session s = client.connectToServer(DemoClientEndpoint.class, new URI(url));
			s.getBasicRemote().sendText(message);
			//latch.await(100, TimeUnit.SECONDS);
			//latch.countDown();
		} catch (DeploymentException | URISyntaxException | /*InterruptedException |*/ IOException e ) {
			throw new RuntimeException(e);
		}
		return "";
	}
	
	public String request(String address, String port, String ID, String message) {
		message = "request"+"/"+message;
		return Crequest(address, port, ID, message);
	}
	
	public String strRequest(String address, String port, String ID, String message, long duration, long frequency) {
		message = "strRequest"+"/"+message+"/"+duration+"/"+frequency;
		return Crequest(address, port, ID, message);
	}
	// test ****************************************************************************************
	
	public static void main(String[] args) throws InterruptedException {
		WebSocketConnector connector = new WebSocketConnector();
		connector.request("localhost", "8080", "sensor", "value");
		connector.strRequest("localhost", "8080", "sensor", "value", 10, 1);
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please press a key to stop the server.");
			reader.readLine();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
}

