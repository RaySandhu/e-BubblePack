package application;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class Schedule {
	
	private static LocalDate currentDate = LocalDate.now();
	private static LocalTime currentTime = LocalTime.now();
	private static int today = currentDate.getDayOfWeek().getValue();	
	private ArrayList<Integer> daysDue; 
	private ArrayList<Integer> timesDue; 
	private ArrayList<ArrayList<Boolean>> administrationStatusPerDose = new ArrayList<>();			// index 0 = administration status and 1 = missed status

	// constructor
	public Schedule(String[] dailySchedule, String[] timelySchedule) {
		daysDue = setDaysDue(dailySchedule);
		timesDue = setTimesDue(timelySchedule);
		administrationStatusPerDose = setAdministrationStatus();
	}
	
	//read

	public ArrayList<ArrayList<Integer>> getScheduleData() {
		ArrayList<ArrayList<Integer>> scheduleData = new ArrayList<>();
		scheduleData.add(this.daysDue);
		scheduleData.add(this.timesDue);

		return scheduleData;
	}

	public ArrayList<ArrayList<Boolean>> getAdministrationStatus() {
		return administrationStatusPerDose;
	}


	//update

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

	public ArrayList<ArrayList<Boolean>> setAdministrationStatus() {
		ArrayList<Boolean> defaultStatus = new ArrayList<>();
		defaultStatus.add(false);
		defaultStatus.add(false);
		for (int i=0; i<timesDue.size(); i++) {
			administrationStatusPerDose.add(defaultStatus);
		}
		return administrationStatusPerDose;
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
	
	//time based functions

	public static int getTodaysDayAsNum() {
		if(today == 7) {
			return 0;
		} else return today;
	}

	public static int getCurrentTimeAsInt() {
        int timeAsInt = (currentTime.getHour() * 100) + currentTime.getMinute();
		return timeAsInt;
	}

	public static String getCurrentTimeAsString(int timeAsInt) {

		if (timeAsInt < 0 || timeAsInt > 2400) {
			return "Invalid input";
		}
		int hours = timeAsInt / 100;
		int minutes = timeAsInt % 100;
		return ("" + hours + ":" + minutes);
	}

}

