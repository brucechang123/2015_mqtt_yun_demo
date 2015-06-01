package nccu.cs.percomlab.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQtoDB {
	private static String dbName = "2015_mqtt_yun_demo";
	private static String user = "guest";
	private static String password = "nccutest";

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

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		subscribe();
		// for (int i = 0; i < 10; i++){
		// System.out.println("temperature = ");
		// System.out.println(MQTTListener.temperature);
		// System.out.println("humidity = ");
		// System.out.println(MQTTListener.humidity);
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// Load JDBC driver
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		// Get connection
		String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + user
				+ "&password=" + password;
		Connection connection = DriverManager.getConnection(url);
		// Create Statement Object
		Statement stmt = connection.createStatement();

		// Declaration
		int temperature;
		String timestamp;

		while (true) {
			timestamp = Utils.getDateString(new Date());
			if (MQTTListener.isReceived == true) {
				// Insert all values into database
				try {
					stmt.executeUpdate("INSERT INTO temperature VALUES('"
							+ timestamp + "', '" + MQTTListener.temperature
							+ "')");
					stmt.executeUpdate("INSERT INTO humidity VALUES('"
							+ timestamp + "', '" + MQTTListener.humidity + "')");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Sleep 5 sec
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
