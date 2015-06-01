package nccu.cs.percomlab.demo;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Subscriber {
	public static void main(String[] args) {
		String clientId = "cfliao";
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			// connect
			MqttAsyncClient sampleClient = new MqttAsyncClient(
					"tcp://wearable.nccu.edu.tw:1883", clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			sampleClient.setCallback(new MQTTListener());
			IMqttToken conToken = sampleClient.connect(connOpts);
			conToken.waitForCompletion();

			// subscribe
			String topicName = "test";
			System.out.println("Subscribing to topic \"" + topicName
					+ "\" qos " + 0);
			IMqttToken subToken = sampleClient.subscribe("home/sensor/*", 0, null,
					null);
			subToken.waitForCompletion();
			System.out.println("123");
			/*while (true) {
				if (MqttListener.isReceived == true) {
					System.out.println("Get message: " + MqttListener.MESSAGE);
					MqttListener.isReceived = false;
				}
			}*/

			// disconnect
			// sampleClient.disconnect();
			// System.out.println("Disconnected");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
