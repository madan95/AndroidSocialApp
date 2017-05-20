package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
	private final String inputFormat = "yyyy-MM-dd HH:mm";
	private SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.UK);
	
	public Date parseDate(String time) throws ParseException{
		return inputParser.parse(time);
	}
	
	public int[] tweakTime(Date dateToChange) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToChange);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int[] times ={year, month, dayOfMonth, hour, minute};
		return times;
		//String tempTime = inputParser.format(cal.getTime());
		//return inputParser.parse(tempTime);
	}
	
}
