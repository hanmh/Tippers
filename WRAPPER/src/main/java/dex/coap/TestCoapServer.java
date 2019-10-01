package dex.coap;

import java.util.Random;
import org.eclipse.californium.core.CoapServer;

import wrapper.model.sensor.TemperatureSensor;

public class TestCoapServer {

	private static CoapServer server;
	
	public static void main(String[] args) throws InterruptedException{
		Random random = new Random();
		String temperature;

		server = new CoapServer(10000);
		temperature = Integer.toString(20+random.nextInt(10));
		server.add(TemperatureSensor.create().createResource());
		server.start();
		//Thread.sleep(50000);
		
		//for( int i=0 ; i<NUM_OF_SERVER ; i++ )
			//servers[i].stop();
	}
}



