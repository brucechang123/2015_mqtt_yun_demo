package test;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class AdapterDemo extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}

public class SensorDataSender {
	public static void main(String[] args) {
		Frame frame = new Frame("AWTDemo");
		frame.addWindowListener(new AdapterDemo());
		frame.setSize(200, 220);
		frame.setVisible(true);
		Publisher temperaturePublisher = new Publisher("temperature_publisher",
				"home/sensor/temperature");
		Publisher humidityPublisher = new Publisher("humidity_publisher",
				"home/sensor/humidity");
		temperaturePublisher.setTemperature(true);
		humidityPublisher.setHumidity(true);
		Thread temperatureThread = new Thread(temperaturePublisher);
		Thread humidityThread = new Thread(humidityPublisher);
		temperatureThread.start();
		humidityThread.start();
	}
}
