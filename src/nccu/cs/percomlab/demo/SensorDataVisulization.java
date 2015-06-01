package nccu.cs.percomlab.demo;

import javax.swing.JApplet;
import java.awt.*;


public class SensorDataVisulization extends JApplet implements Runnable {

    private Image offScreen;
    private Graphics gOffScreen;
    private int pos1, pos2;
    private Subscriber subscriber;

    public void init() {
        setSize(800, 600);
        offScreen = createImage(getWidth(), getHeight());
        gOffScreen = offScreen.getGraphics();
        subscriber = new Subscriber();
        pos1 = 10;
        pos2 = 10;
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
