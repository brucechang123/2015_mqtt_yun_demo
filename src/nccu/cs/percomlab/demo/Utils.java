package nccu.cs.percomlab.demo;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Utils
{
    private final static DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG, Locale.TAIWAN);
    
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
