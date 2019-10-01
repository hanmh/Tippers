package wrapper.model.sensor;

import java.util.ArrayList;
import dex.coap.DemoResource;
import dex.webSocket.DemoServerEndpoint;

public abstract class Sensor {

	// sensor's basic attribute
	private String sensorID;
	
	//constructor	-> sensor ID is created automatically
	
	public Sensor() {
		this.sensorID = IdCreator.createID();
	}
		
	// get set method
	public String getSensorID() {return sensorID;}
	public void setSensorID(String sensorID) {this.sensorID = sensorID;} // can't use
	
	// method
	public ArrayList<String> getAttributeList(){	// return - message : which attribute there is
		ArrayList<String> list = new ArrayList<String>();
		return list;
	}

	// for coap	- will be used when create virtual coapServer 
	public DemoResource createResource() {	// must override to include inserted attribute
		
		DemoResource r = new DemoResource(getSensorID(), "sensor" ); 
		r.add(new DemoResource("sensorID", getSensorID()));
		return r;
	}
	
// for webSocket - will be used when create virtual coapServer
	public String response(String resource) {
			switch(resource) {
			case "id":
				return sensorID;
			default:
				return "there is not "+resource;
			}
		}
}

class IdCreator {
	private static int id= 1;
	
	public static String createID() {
		return Integer.toString(id++);
	}
}