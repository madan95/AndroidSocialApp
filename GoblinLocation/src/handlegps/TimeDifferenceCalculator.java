package handlegps;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeDifferenceCalculator {
	private Date beforeRange, afterRange, currentTime;
	private final String inputFormat = "yyyy-MM-dd HH:mm";
	private SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.UK);
	
	public TimeDifferenceCalculator(String logged_in_time, String other_time) throws ParseException{
		currentTime = inputParser.parse(logged_in_time);
		beforeRange = inputParser.parse(other_time);
		afterRange = inputParser.parse(other_time);
	}
	
	public boolean is_within_time() throws ParseException{
			boolean answer = false;
			beforeRange = tweakTime(beforeRange);
			afterRange = tweakTime(afterRange);
			if((!currentTime.before(beforeRange)&&(!currentTime.after(afterRange)))){
				//they meet
				System.out.println("They Meeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeet");
				answer = true;
				}
		return answer;
	}
	
	
	public Date tweakTime(Date dateToChange) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToChange);
		if(dateToChange.equals(beforeRange)){
			cal.add(Calendar.MINUTE, -15);
		}else if(dateToChange.equals(afterRange)){
			cal.add(Calendar.MINUTE, 15);
		}
		String tempTime = inputParser.format(cal.getTime());
		return inputParser.parse(tempTime);
	}
	

}
