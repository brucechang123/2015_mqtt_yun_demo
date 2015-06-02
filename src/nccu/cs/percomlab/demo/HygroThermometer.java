package nccu.cs.percomlab.demo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JApplet;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class HygroThermometer extends JApplet implements Runnable {
	private Image offScreen;
    private Graphics gOffScreen;
    private int pos1, pos2;

    public void init() {
        setSize(800, 600);
        offScreen = createImage(getWidth(), getHeight());
        gOffScreen = offScreen.getGraphics();
       // subscriber = new SensorSubscriber();
        pos1 = 10;
        pos2 = 10;
    }
    public static void subscribe() {
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			MqttAsyncClient sampleClient = new MqttAsyncClient(
					"tcp://wearable.nccu.edu.tw:1883", "mqtt_yun_demo",
					persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			sampleClient.setCallback(new MQTTListener());
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
        	gOffScreen.setColor(Color.black);
        	gOffScreen.drawString("test", 300, 300);
        	gOffScreen.setColor(Color.red);
            gOffScreen.fillRect(pos1, 10, 20, 2);
            pos1 += 10;
            gOffScreen.setColor(Color.blue);
            gOffScreen.fillRect(pos2, 50, 20, 2);
            pos2 += 5;
            if (pos1 > 800){
            	gOffScreen.clearRect(0, 0, 800, 40);
            	pos1 = 0;
            }
            else if (pos2 > 800){
            	gOffScreen.clearRect(0, 40, 800, 40);
            	pos2 = 0;
            }
            else{
            	
            }
            repaint();  
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // override update()
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        // 將緩衝區畫面繪到前景
        g.drawImage(offScreen, 0, 0, this);
    }
}
