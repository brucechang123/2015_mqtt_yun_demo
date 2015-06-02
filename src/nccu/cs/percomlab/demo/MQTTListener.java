package nccu.cs.percomlab.demo;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTListener implements MqttCallback
{
	public int temperature;
	public int humidity;
	public boolean humidityIsReceived = false;
	public boolean temperatureIsReceived = false;
	public int getTemperature() {
		return this.temperature;
	}
	public int getHumidity() {
		return this.humidity;
	}
	public boolean getHumidityFlag(){
		return this.humidityIsReceived;
	}
	public boolean getTemperatureFlag(){
		return this.temperatureIsReceived;
	}
    @Override
    public void connectionLost(Throwable arg0)
    {
        // Do nothing
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0)
    {
        // Do nothing
    }
    
    // Called when delivery for a message has been completed
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception
    {
    	String tempString = mqttMessage.toString();
    	System.out.println(tempString);
    	String[] arrayString = new String[2];
    	arrayString = tempString.split(":  ");
    	
    	if (arrayString[0].equals("Temperature")){
    		//temperatureIsReceived = true;
    		this.temperature = (int) Double.parseDouble(arrayString[1]);
    		//System.out.println(Integer.parseInt(arrayString[1]));
    		temperatureIsReceived = true;
    		
    	}
    	else if (arrayString[0].equals("Humidity")){
    		//humidityIsReceived = true;
    		this.humidity = (int) Double.parseDouble(arrayString[1]);
			// System.out.println(Integer.parseInt(arrayString[1]));
			humidityIsReceived = true;
    	}
    	else{
    		// do nothing
    	}
    }

}