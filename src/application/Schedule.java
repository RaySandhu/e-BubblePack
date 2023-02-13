package application;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class Schedule {
	
	private LocalDate currentDate = LocalDate.now();
	private LocalTime currentTime = LocalTime.now();
	private int today = currentDate.getDayOfWeek().getValue();	
	private ArrayList<Integer> daysDue; 
	private ArrayList<Integer> timesDue; 

	// constructor
	public Schedule(String[] dailySchedule, String[] timelySchedule) {
		daysDue = setDaysDue(dailySchedule);
		timesDue = setTimesDue(timelySchedule);
	}
	
	//read

	public static ArrayList<ArrayList<Integer>> getScheduleData(Schedule medSchedule) {
		ArrayList<ArrayList<Integer>> scheduleData = new ArrayList<>();
		scheduleData.add(medSchedule.daysDue);
		scheduleData.add(medSchedule.timesDue);

		return scheduleData;
	}

	public ArrayList<Integer> setTimesDue(String[] newTimes) {
		ArrayList<Integer> schedulePerDay = new ArrayList<Integer>();
		for(String i : newTimes) {
			schedulePerDay.add(Integer.parseInt(i));
		}
		return schedulePerDay;
	}

	public ArrayList<Integer> setDaysDue(String[] newDays) {
		
		ArrayList<Integer> startingDailySchedule = new ArrayList<>(); //works backward from medication not being due any day	
		for(int i=0; i<7; i++) {
			startingDailySchedule.add(-1);
		}	

		for(int i=0; i< newDays.length; i++) {
			int numericalDayOfWeek = Integer.parseInt(newDays[i]);
			startingDailySchedule.set(numericalDayOfWeek, numericalDayOfWeek);
		}
		return startingDailySchedule;
	}
	
	public String parseToStringDailySchedule() {
		String weeklyScheduleinWords = "";
		for(int i : daysDue) {
			if(i != -1) {
				weeklyScheduleinWords += (DayOfWeek.of( i+1 ).getDisplayName(TextStyle.FULL, Locale.CANADA));
				weeklyScheduleinWords += ", ";
			}
		}
		return weeklyScheduleinWords.substring(0, weeklyScheduleinWords.length()-2);
	}

	public String parseToStringTimelySchedule() {
		String timelyScheduleinString = "";
		for(int i : timesDue) {
			String timeSlot = String.valueOf(i);
			if(i<1000) {
				timelyScheduleinString += ("0" + timeSlot + ", ");	
			} else timelyScheduleinString += (timeSlot + ", ");
		}
		return timelyScheduleinString.substring(0, timelyScheduleinString.length()-2);
	}
	
	public int getTodaysDayAsNum() {
		if(today == 7) {
			return 0;
		} else return today;
	}
}

