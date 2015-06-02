package nccu.cs.percomlab.demo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Date;

import javax.swing.JApplet;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class Demo extends JApplet implements Runnable {
	private Image offScreen;
	private Graphics gOffScreen;
	private static MQTTListener mqttListener;

	public void init() {
		subscribeMqttServer();
		setSize(600, 600);
		offScreen = createImage(getWidth(), getHeight());
		gOffScreen = offScreen.getGraphics();
	}

	public static void subscribeMqttServer() {
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			MqttAsyncClient sampleClient = new MqttAsyncClient(
					"tcp://wearable.nccu.edu.tw:1883", "mqtt_yun_demo",
					persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			mqttListener = new MQTTListener();
			sampleClient.setCallback(mqttListener);
			IMqttToken conToken = sampleClient.connect(connOpts);
			conToken.waitForCompletion();

			IMqttToken subToken = sampleClient.subscribe("home/sensor/*", 0,
					null, null);
			subToken.waitForCompletion();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void start() {
		(new Thread(this)).start();
	}

	public void run() {
		// 動畫迴圈
		while (true) {
			showTwoMeters();
			repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void showTwoMeters() {
		clearScreen();
		showThermometer();
		showHygrometer();
		showTime();
	}

	private void clearScreen() {
		gOffScreen.clearRect(0, 0, 600, 600);
	}

	private void showThermometer() {
		thermometerText();
		temperatureValueIsReceived();
		drawThermometerFrame();
		drawThermometerScale();
	}

	private void thermometerText() {
		gOffScreen.setColor(Color.black);
		gOffScreen.setFont(new Font("default", Font.BOLD, 16));
		gOffScreen.drawString("Thermometer", 120, 100);
	}

	private void temperatureValueIsReceived() {
		gOffScreen.setColor(Color.red);
		gOffScreen.setFont(new Font("default", Font.CENTER_BASELINE, 24));
		if (mqttListener.getTemperatureFlag() == true) {
			int temp = mqttListener.getTemperature();
			gOffScreen.drawString(String.valueOf(temp), 150, 425);
			gOffScreen.drawString("C", 190, 425);
			gOffScreen.setFont(new Font("default", Font.CENTER_BASELINE, 12));
			gOffScreen.drawString("o", 183, 412);
			drawTemperatureBar(temp);
		} else {
			gOffScreen.drawString("NAN", 150, 425);
		}
	}

	private void drawTemperatureBar(int temperature) {
		gOffScreen.setColor(Color.red);
		gOffScreen.fillRect(160, 350 - (temperature - 20) * 20, 30,
				(temperature - 20) * 20 + 20);
	}

	private void drawThermometerFrame() {
		gOffScreen.setColor(Color.gray);
		gOffScreen.drawLine(160, 357, 160, 150);
		gOffScreen.drawLine(190, 357, 190, 150);
		gOffScreen.drawArc(160, 135, 30, 30, 0, 180);
		gOffScreen.setColor(Color.red);
		gOffScreen.fillOval(150, 350, 50, 50);
	}

	private void drawThermometerScale() {
		gOffScreen.setColor(Color.gray);
		gOffScreen.setFont(new Font("default", Font.CENTER_BASELINE, 12));
		gOffScreen.drawLine(185, 350, 195, 350);
		gOffScreen.drawString("20", 200, 355);
		gOffScreen.drawLine(185, 310, 195, 310);
		gOffScreen.drawString("22", 200, 315);
		gOffScreen.drawLine(185, 270, 195, 270);
		gOffScreen.drawString("24", 200, 275);
		gOffScreen.drawLine(185, 230, 195, 230);
		gOffScreen.drawString("26", 200, 235);
		gOffScreen.drawLine(185, 190, 195, 190);
		gOffScreen.drawString("28", 200, 195);
		gOffScreen.drawLine(185, 150, 195, 150);
		gOffScreen.drawString("30", 200, 155);
	}

	private void showHygrometer() {
		hygrometerText();
		humidityValueIsReceived();
		drawHygromometerFrame();
		drawHygrometerScale();
	}

	private void hygrometerText() {
		gOffScreen.setColor(Color.black);
		gOffScreen.setFont(new Font("default", Font.BOLD, 16));
		gOffScreen.drawString("Hygromometer", 270, 100);
	}

	private void humidityValueIsReceived() {
		gOffScreen.setColor(Color.blue);
		gOffScreen.setFont(new Font("default", Font.CENTER_BASELINE, 24));
		if (mqttListener.getHumidityFlag() == true) {
			int temp = mqttListener.getHumidity();
			gOffScreen.drawString(String.valueOf(temp), 300, 425);
			gOffScreen.drawString("%", 340, 425);
			drawHumidityBar(temp);
		} else {
			gOffScreen.drawString("NAN", 300, 425);
		}
	}

	private void drawHumidityBar(int humidity) {
		gOffScreen.setColor(Color.blue);
		gOffScreen.fillRect(310, 350 - humidity * 2, 30, humidity * 2 + 20);
	}

	private void drawHygromometerFrame() {
		gOffScreen.setColor(Color.gray);
		gOffScreen.drawLine(310, 357, 310, 150);
		gOffScreen.drawLine(340, 357, 340, 150);
		gOffScreen.drawArc(310, 135, 30, 30, 0, 180);
		gOffScreen.setColor(Color.blue);
		gOffScreen.fillOval(300, 350, 50, 50);
	}

	private void drawHygrometerScale() {
		gOffScreen.setColor(Color.gray);
		gOffScreen.setFont(new Font("default", Font.CENTER_BASELINE, 12));
		gOffScreen.drawString("0", 350, 355);
		gOffScreen.drawLine(335, 350, 345, 350);
		gOffScreen.drawString("20", 350, 315);
		gOffScreen.drawLine(335, 310, 345, 310);
		gOffScreen.drawString("40", 350, 275);
		gOffScreen.drawLine(335, 270, 345, 270);
		gOffScreen.drawString("60", 350, 235);
		gOffScreen.drawLine(335, 230, 345, 230);
		gOffScreen.drawString("80", 350, 195);
		gOffScreen.drawLine(335, 190, 345, 190);
		gOffScreen.drawString("100", 350, 155);
		gOffScreen.drawLine(335, 150, 345, 150);
	}

	private void showTime() {
		String timestamp = Utils.getDateString(new Date());
		gOffScreen.setColor(Color.black);
		gOffScreen.setFont(new Font("default", Font.BOLD, 16));
		gOffScreen.drawString(timestamp, 170, 500);
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		g.drawImage(offScreen, 0, 0, this);
	}
}
