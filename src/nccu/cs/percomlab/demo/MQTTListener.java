package nccu.cs.percomlab.demo;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTListener implements MqttCallback
{
	public static int temperature;
	public static int humidity;
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
    	
    	String[] arrayString = new String[2];
    	arrayString = tempString.split(" = ");
    	//arrayString = tempString.split(": ");
    	
    	if (arrayString[0].equals("Temperature")){
    		MQTTListener.temperature = Integer.parseInt(arrayString[1]);
    		System.out.println(Integer.parseInt(arrayString[1]));
    		
    	}
    	else if (arrayString[0].equals("Humidity")){
    		MQTTListener.humidity = Integer.parseInt(arrayString[1]);
    		System.out.println(Integer.parseInt(arrayString[1]));
    	}
    	else{
    		// do nothing
    	}
    }

}