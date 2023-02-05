package application;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class Schedule {
	
	private LocalDate currentDate = LocalDate.now();
	private LocalTime currentTime = LocalTime.now();
	private int today = currentDate.getDayOfWeek().getValue();	
	private int[] daysDue; // days of the week indexed from 0-6, -1 representing days medication is not due
	private ArrayList<Integer> timesDue; //will likely convert to 24hr time before developing further

	// constructor
	public Schedule(String[] dailySchedule, String[] timelySchedule) {
		daysDue = setDaysDue(dailySchedule);
		timesDue = setTimesDue(timelySchedule);
	}
	
	public ArrayList<Integer> setTimesDue(String[] newTimes) {
		ArrayList<Integer> schedulePerDay = new ArrayList<Integer>();
		for(String i : newTimes) {
			schedulePerDay.add(Integer.parseInt(i));
			System.out.println(Integer.parseInt(i));
		}
		return schedulePerDay;
	}

	public int[] setDaysDue(String[] newDays) {
		
		int[] startingDailySchedule = {-1,-1,-1,-1,-1,-1,-1}; //works backward from medication not being due any day		
		for(int i=0; i< newDays.length; i++) {
			
			int numericalDayOfWeek = Integer.parseInt(newDays[i]);
			startingDailySchedule[numericalDayOfWeek] = numericalDayOfWeek;
		}
		daysDue = startingDailySchedule;

		return startingDailySchedule;
	}

	//utility methods - part of the data display part of the project
	
	public ArrayList<String> parseToStringDailySchedule() {
		ArrayList<String> weeklyScheduleinWords = new ArrayList<String>();
		for(int i : daysDue) {
			if(i != -1) {
				weeklyScheduleinWords.add(DayOfWeek.of( i+1 ).getDisplayName(TextStyle.FULL, Locale.CANADA));
			}
		}
		return weeklyScheduleinWords;
	}

	public String parseToStringTimelySchedule() {
		String timelyScheduleinString = "";
		for(int i : timesDue) {
			String timeSlot = String.valueOf(i);
			if(i<1000) {
				timelyScheduleinString += ("0" + timeSlot + ", ");	
			} else timelyScheduleinString += (timeSlot + ", ");
		}
		return timelyScheduleinString;
	}
	
	public int getTodaysDayAsNum() {
	//		cycles through each week with int values per day - 0-6
		if(today == 7) {
			return 0;
		} else return today;
	}
	
	//getter for usable data
	public int[] getDaysDue() {
		return daysDue;
	}
}

