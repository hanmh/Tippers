package dex.webSocket;

import java.io.IOException;
import java.util.Random;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.eclipse.californium.core.CoapServer;

import wrapper.model.sensor.Sensor;
import wrapper.model.sensor.TemperatureSensor;;

@ServerEndpoint(value = "/data")
public class DemoServerEndpoint {
	/*
	 * Allows us to intercept the creation of a new session. The session class
	 * allows us to send data to the user. In the method onOpen, we'll let the user
	 * know that the handshake was successful.
	 */
	
	static TemperatureSensor sensor;
	
	public DemoServerEndpoint() {
		sensor = TemperatureSensor.create();
	}
	
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("onOpen:" + session.getId());
	}
	/*
	 * When a user sends a message to the server, this method will intercept the
	 * message and allow us to react to it. For now the message is read as a String.
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("onMessage: From = " + session.getId() + " Message... " + message);
		
		String response;
		String[] messages = message.split("/");	
	
		if(messages.length >= 2) {
			switch(messages[0]) {
			case "request":
				response = sensor.response(messages[1]);
				
				try {
					session.getBasicRemote().sendText(response);
					//session.close();
				} catch (IOException ex) {ex.printStackTrace();}

				break;
				
			case "strRequest":
				/*
				try {
					session.getBasicRemote().sendText("asdfdsafa");
					Thread.sleep(1000);
					new StreamingHelper(session, sensor, messages[1], 100, 1).run();
				} catch (IOException | InterruptedException ex) {ex.printStackTrace();}
				*/
				long duration = Integer.parseInt(messages[2]);
				long frequency = Integer.parseInt(messages[3]);
				new StreamingHelper(session, sensor, messages[1], duration, frequency).run();
				break;
				
			default:
				break;
			}
		}
	}

	/*
	 * The user closes the connection.
	 */
	@OnClose
	public void onClose(Session session) {
		System.out.println("Session " + session.getId() + " has ended");
	}
	/*
	 * An Error occurs
	 */
	@OnError
	public void onError(Throwable t) {
		System.out.println("onError::" + t.getMessage());
	}
}

class StreamingHelper extends Thread{
	
	Session session;
	TemperatureSensor sensor;
	long duration;
	long frequency;
	String resource;
	
	public StreamingHelper(Session session, TemperatureSensor sensor, String resource, long duration, long frequency) {
		this.session = session;
		this.sensor = sensor;
		this.resource = resource;
		this.duration = duration;
		this.frequency = frequency;
	}
	
	public void run(){
		
		String response;
		
		try {
			for(int i=0 ; i<duration ; i+=frequency) {
				response = sensor.response(resource);
				session.getBasicRemote().sendText(response);
				Thread.sleep(1000*frequency);
			}
		} catch (IOException | InterruptedException ex) {ex.printStackTrace();}

		
		
	}
}