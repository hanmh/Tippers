/* [1]
 * if you have some resource (e.g. this TemperatureSensor)
 * you have to make the resource class by using the CoAPResource class
 * and in your resource class, you have to Override the creeateResource()
 * 
 */
package wrapper.model.sensor;

import java.util.ArrayList;
import java.util.Random;

import dex.coap.DemoResource;

public class TemperatureSensor extends Sensor{

	private String label;	// "temperature"	??
	private String scale;	// "celsius" or "fahrenheit"	�늿湲�
	private String value;	// 媛�'

	public static TemperatureSensor create() {
		Random random = new Random();
		String temperature = Integer.toString(20+random.nextInt(10));
		return new TemperatureSensor("temperature", "fahrenheit", temperature);
	}
	
	public TemperatureSensor(String label, String scale, String value) {
		super();
		this.label = label;
		this.scale = scale;
		this.value = value;
	}
	
	public TemperatureSensor() {
		this("temperature", "fahrenheit", "0");
	}
	
// get set method
	public String getLabel() {return label;}
	public void setLabel(String label) {this.label = label;}
	
	public String getScale() {return scale;}
	public void setScale(String scale) {this.scale = scale;}
	
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
	
	@Override
	public ArrayList<String> getAttributeList() {
		ArrayList<String> list = super.getAttributeList();
		list.add("value");
		list.add("label");
		list.add("scale");
		return list;
	}
	
	public String createAttributeListMessage() {
		String message = "";
		for(String t : getAttributeList()) {
			message += t+"/";
		}
		return message;
	}
	
// for coap	- will be used when create virtual coapServer 
	@Override
	public DemoResource createResource() {			// [1]
		DemoResource r = super.createResource();
		r.add( new DemoResource("requests", createAttributeListMessage()));
		r.add( new DemoResource("label", label));
		r.add( new DemoResource("scale", scale));
		r.add( new DemoResource("value", value));
		return r;
	}
	
// for ws - will be used in DemoServerEndpoint
	public String response(String resource) {
		switch(resource) {
		case "label":
			return label;
		case "scale":
			return scale;
		case "value":
			return value;
		default:
			return super.response(resource);
		}
	}
}
