/* CoAP AIP have resource Class
 * this can have children resource
 * to use this you can override the handler and attribute e.g. below like value(String) 
 */

package dex.coap;

import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.CoapResource;

public class DemoResource extends CoapResource{

	String value;
	
	public DemoResource(String name, String value) {
		super(name);
		this.value = value;
		
		// set display name
		getAttributes().setTitle("sensorResource");
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		exchange.respond(value);

	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		String msg = exchange.getRequestText();
		String preValue = value;
		value = msg;
		String curValue = value;
		
		String responseTxt = getName()+"@"+preValue+"@"+curValue;
		exchange.respond(ResponseCode.CREATED, responseTxt);
	}
}
