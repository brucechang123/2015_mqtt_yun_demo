package nccu.cs.percomlab.demo;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Subscriber {
	private MqttAsyncClient sampleClient;
	private MqttConnectOptions connOpts;
	private String clientName;

	public Subscriber() {
		try {
			sampleClient = new MqttAsyncClient(
					"tcp://wearable.nccu.edu.tw:1883", clientName,
					new MemoryPersistence());
			connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			sampleClient.setCallback(new MQTTListener());
			IMqttToken conToken = sampleClient.connect(connOpts);
			conToken.waitForCompletion();
			IMqttToken subToken = sampleClient.subscribe("home/sensor/*", 0, null, null);
			subToken.waitForCompletion();
		} catch (Exception e) {
		}
	}
}
