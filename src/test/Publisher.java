package test;

import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Publisher implements Runnable {
	private MqttClient sampleClient;
	private String clientName;
	private String topic;
	private boolean isRunning;
	private Random random;
	private boolean h;
	private boolean t;
	private String content;

	public Publisher(String clientName, String topic) {
		random = new Random();
		this.clientName = clientName;
		this.topic = topic;
		h = false;
		t = false;
		isRunning = true;
		try {
			sampleClient = new MqttClient("tcp://wearable.nccu.edu.tw:1883",
					this.clientName, new MemoryPersistence());

			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			sampleClient.connect(connOpts);

		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public void setHumidity(boolean flag) {
		this.h = flag;
	}

	public void setTemperature(boolean flag) {
		this.t = flag;
	}

	public void stop() {
		try {
			isRunning = false;
			sampleClient.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		//System.out.println("test");
		//System.out.println(isRunning);
		while (isRunning == true) {
			if (this.h == true) {
				int temp = random.nextInt(15) + 40;
				content = "humidity = " + String.valueOf(temp);
			} else if (this.t == true) {
				int temp = random.nextInt(10) + 20;
				content = "temperature = " + String.valueOf(temp);
			} else {
				// do nothing
			}
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(0);

			try {
				sampleClient.publish(this.topic, message);
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
}
