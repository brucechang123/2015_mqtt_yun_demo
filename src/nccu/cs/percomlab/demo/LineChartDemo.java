package nccu.cs.percomlab.demo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JApplet;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class LineChartDemo extends JApplet implements Runnable {
	private Image offScreen;
	private Graphics gOffScreen;
	private static MQTTListener mqttListener;
	private Point lastTemperaturePoint, lastHumidityPoint;

	public void init() {
		subscribeMqttServer();
		setSize(1200, 600);
		offScreen = createImage(getWidth(), getHeight());
		gOffScreen = offScreen.getGraphics();
		lastTemperaturePoint = new Point(-1, -1);
		lastHumidityPoint = new Point(-1, -1);
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
			if (lastTemperaturePoint.getX() >= 1060){
				clearScreen();
				lastTemperaturePoint.setX(550);
				lastHumidityPoint.setX(550);
			}
			showLineChart();
			repaint();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void showLineChart() {
		drawBound();
		drawAxes();
		drawThermometerLineChart();
		drawHygrometerLineChart();

	}

	private void drawBound() {
		gOffScreen.setColor(Color.gray);
		gOffScreen.drawRect(500, 50, 650, 500);
	}

	private void drawAxes() {
		drawThermometerAxes();
		drawHygrometerAxes();
	}

	private void drawThermometerAxes() {
		gOffScreen.setColor(Color.black);
		gOffScreen.drawLine(550, 270, 1080, 270);
		gOffScreen.drawLine(550, 270, 550, 100);
		gOffScreen.drawString("Time", 1100, 275);
		gOffScreen.drawString("Value", 530, 80);
		gOffScreen.drawString("Thermometer", 800, 80);
		
		gOffScreen.setColor(Color.gray);
		gOffScreen.drawLine(545, 250, 555, 250);
		gOffScreen.drawString("25", 528, 255);
		gOffScreen.drawLine(545, 220, 555, 220);
		gOffScreen.drawString("27", 528, 225);
		gOffScreen.drawLine(545, 190, 555, 190);
		gOffScreen.drawString("29", 528, 195);
		gOffScreen.drawLine(545, 160, 555, 160);
		gOffScreen.drawString("31", 528, 165);
		gOffScreen.drawLine(545, 130, 555, 130);
		gOffScreen.drawString("33", 528, 135);
		gOffScreen.drawLine(545, 100, 555, 100);
		gOffScreen.drawString("35", 528, 105);
	}

	private void drawHygrometerAxes() {
		gOffScreen.setColor(Color.black);
		gOffScreen.drawLine(550, 530, 1080, 530);
		gOffScreen.drawLine(550, 530, 550, 310);
		gOffScreen.drawString("Time", 1100, 505);
		gOffScreen.drawString("Value", 530, 290);
		gOffScreen.drawString("Hygrometer", 800, 300);

		gOffScreen.setColor(Color.gray);
		gOffScreen.drawLine(545, 520, 555, 520);
		gOffScreen.drawString("0", 535, 525);
		gOffScreen.drawLine(545, 480, 555, 480);
		gOffScreen.drawString("20", 528, 485);
		gOffScreen.drawLine(545, 440, 555, 440);
		gOffScreen.drawString("40", 528, 445);
		gOffScreen.drawLine(545, 400, 555, 400);
		gOffScreen.drawString("60", 528, 405);
		gOffScreen.drawLine(545, 360, 555, 360);
		gOffScreen.drawString("80", 528, 365);
		gOffScreen.drawLine(545, 320, 555, 320);
		gOffScreen.drawString("100", 520, 325);
	}

	private void drawThermometerLineChart() {
		gOffScreen.setColor(Color.red);
		if (mqttListener.getTemperatureFlag() == true) {
			int temp = mqttListener.getTemperature();
			//System.out.println(temp);
			if (lastTemperaturePoint.getX() == -1) {
				lastTemperaturePoint.setX(570);
				lastTemperaturePoint.setY(temp * (-1) * 15 + 625);
				gOffScreen.setColor(Color.red);
				gOffScreen.drawLine(550, temp * (-1) * 15 + 625,
						lastTemperaturePoint.getX(),
						lastTemperaturePoint.getY());
			} else {
				gOffScreen.setColor(Color.red);
				gOffScreen.drawLine(lastTemperaturePoint.getX(),
						lastTemperaturePoint.getY(),
						lastTemperaturePoint.getX() + 20, temp * (-1) * 15 + 625);
				lastTemperaturePoint.setX(lastTemperaturePoint.getX() + 20);
				lastTemperaturePoint.setY(temp * (-1) * 15 + 625);
			}
		}
	}

	private void drawHygrometerLineChart() {
		gOffScreen.setColor(Color.blue);
		if (mqttListener.getHumidityFlag() == true) {
			int temp = mqttListener.getHumidity();
			//System.out.println(temp);
			if (lastHumidityPoint.getX() == -1) {
				lastHumidityPoint.setX(570);
				lastHumidityPoint.setY(temp * (-1) * 2 + 520);
				gOffScreen.setColor(Color.blue);
				gOffScreen.drawLine(550, temp * (-1) * 2 + 520,
						lastHumidityPoint.getX(),
						lastHumidityPoint.getY());
			} else {
				gOffScreen.setColor(Color.blue);
				gOffScreen.drawLine(lastHumidityPoint.getX(),
						lastHumidityPoint.getY(),
						lastHumidityPoint.getX() + 20, temp * (-1) * 2 + 520);
				lastHumidityPoint.setX(lastHumidityPoint.getX() + 20);
				lastHumidityPoint.setY(temp * (-1) * 2 + 520);
			}
		}
	}
	private void clearScreen() {
		gOffScreen.clearRect(0, 0, 1200, 600);
	}
	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		g.drawImage(offScreen, 0, 0, this);
	}
}
