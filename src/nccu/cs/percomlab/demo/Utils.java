package nccu.cs.percomlab.demo;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils
{
    private final static SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    public static String getRoundedString(double input, int numberOfDigits)
    {
        NumberFormat format = NumberFormat.getInstance();
        format.setRoundingMode(RoundingMode.HALF_UP);
        format.setMaximumFractionDigits(numberOfDigits);
        return format.format(input);
    }
    
    public static String getDateString(Date date)
    {
        return df.format(date);
    }
    
}
