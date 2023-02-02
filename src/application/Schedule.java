package application;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public class Schedule {
	
	private LocalDate date = LocalDate.now();
	private int today = date.getDayOfWeek().getValue();
	SimpleDateFormat simpleDateFormatArrivals = new SimpleDateFormat("HH:mm", Locale.CANADA); //attemtping to standardize current time to 24hr clock
	
												//localTime.parse can be used.
	
	private int[] daysDue; // days of the week indexed from 0-6, -1 representing days medication is not due
	private LocalTime[] timesDue; //will likely convert to 24hr time before developing further
	
	public String stringDOW(int relevantDayOfWeek) {
		//to display the name for relevant day of the week
		return DayOfWeek.of( relevantDayOfWeek ).getDisplayName(TextStyle.FULL, Locale.CANADA);
	}
	
	public int getTodaysDayAsNum() {
//		cycles through each week with int values per day - 0-6
		if(today == 7) {
			return 0;
		} else return today;
	}
	
	public int[] getDaysDue() {
		return daysDue;
	}
	
	public void setDaysDue(String[] newDays) {

		int[] newArray = {-1,-1,-1,-1,-1,-1,-1}; //works backward from medication not being due any day
		this.daysDue = newArray;
		
		for(int i=0; i< newDays.length; i++) {
			
			int numericalDayOfWeek = Integer.parseInt(newDays[i]);
			this.daysDue[numericalDayOfWeek] = numericalDayOfWeek;
		}
	}
	
}
