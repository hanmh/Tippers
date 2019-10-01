package dex.webSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.server.Server;

public class TestWebSocketServer {

	private static final int NUM_OF_SERVER = 1;
	
	public static void main(String[] args) {

		
		Server[] servers = new Server[NUM_OF_SERVER];
		
		for(int i=0 ; i<NUM_OF_SERVER ; i++) {
			servers[i] = new Server("localhost", 8080, "/sensor", DemoServerEndpoint.class);
			try {
				servers[i].start();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please press a key to stop the server.");
			reader.readLine();
			servers[0].stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

