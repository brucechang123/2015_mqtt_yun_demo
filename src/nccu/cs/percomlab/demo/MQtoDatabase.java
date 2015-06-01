package nccu.cs.percomlab.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;


public class MQtoDatabase {
	public static void main(String[] args) {
		//SensorSubscriber sensorSubscriber = new SensorSubscriber();
		for (int i = 0; i < 10; i++){
			System.out.println("temperature = ");
			//System.out.println(sensorSubscriber.getTemperature());
			System.out.println("humidity = ");
			//System.out.println(sensorSubscriber.getHumidity());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		String dbName = "2015_mqtt_yun_demo";
//		String user = "guest";
//		String password = "nccutest";
//		String tableName = "temperature";
//		
//		// Load JDBC driver
//        Class.forName("com.mysql.jdbc.Driver").newInstance();
//        // Get connection
//        String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + user + "&password=" + password;
//        Connection connection = DriverManager.getConnection(url);
//        // Create Statement Object
//        Statement stmt = connection.createStatement();
//        
//        // Declaration
//        double temperature;
//        String timestamp;
//        
//        while(true){
//            // Convert the analog input SensorValue into temperature 
//            temperature = (sensorValue * 0.22222) - 61.11; 
//            // Adjust the temperature value
//            temperature = Double.parseDouble(Utils.getRoundedString(temperature, 2));
//            // Get system time
//            timestamp = Utils.getDateString(new Date());
//            // Output all values 
//            System.out.println("temperature = " + temperature);
//            System.out.println(Utils.getDateString(new Date()));
//            
//            //Insert all values into database 
//            stmt.executeUpdate("INSERT INTO "+tableName+" VALUES('"+temperature+"', '"+timestamp+"')");
//            
//            //Sleep 5 sec
//            Thread.sleep(5000);
//        }     
	}
}
