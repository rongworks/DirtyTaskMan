package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PresentationUtil {
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
	
	public static String getCurrentTimeString(){
		return dateFormat.format(new Date());
	}
	
	
}
