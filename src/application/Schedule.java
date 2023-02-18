package application;

import java.time.*;
import java.util.*;

public class Schedule {


	private static LocalDate currentDate = LocalDate.now();
	private static LocalTime currentTime = LocalTime.now();
	private static int today = currentDate.getDayOfWeek().getValue();	
	private ArrayList<Integer> daysDue; 
	private ArrayList<Integer> timesDue; 
	private ArrayList<ArrayList<Boolean>> administrationStatusPerDose = new ArrayList<>();	// index 0 = administration status and 1 = missed status


	// constructor
	public Schedule(String[] dailySchedule, String[] timelySchedule) {
		daysDue = setDaysDue(dailySchedule);
		timesDue = setTimesDue(timelySchedule);
		administrationStatusPerDose = initAdministrationStatus();
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

	public ArrayList<ArrayList<Boolean>> initAdministrationStatus() {
		for (int i=0; i<timesDue.size(); i++) {
			ArrayList<Boolean> defaultStatus = new ArrayList<>();
			defaultStatus.add(false);
			defaultStatus.add(false);
			administrationStatusPerDose.add(defaultStatus);
		}
		return administrationStatusPerDose;
	}

	public void toggleAdministrationStatus(Integer doseToToggle) {
		administrationStatusPerDose.get(doseToToggle).set(0,!administrationStatusPerDose.get(doseToToggle).get(0));
	}

	public void editDailySchedule(String[] dailySchedule) {
		timesDue = setTimesDue(dailySchedule);
		administrationStatusPerDose = initAdministrationStatus();
	}

	public void editWeeklySchedule(String[] weeklySchedule) {
		daysDue = setDaysDue(weeklySchedule);
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

	public static String getTimeAsString(int timeAsInt) {

		String hourDisplay;
		String minutesDisplay;
		if (timeAsInt < 0 || timeAsInt > 2400) {
			return "Invalid input";
		}
		int hours = timeAsInt / 100;
		int minutes = timeAsInt % 100;
		if (hours < 10) {
			hourDisplay = "0" + hours;
		} else hourDisplay = "" + hours;

		if (minutes < 10) {
			minutesDisplay = "0" + minutes;
		} else minutesDisplay = "" + minutes;
		
		return (hourDisplay + ":" + minutesDisplay);
	}

	/**
	*Converts an input integer into a formatted time string.
	*@param digits the integer to be converted into time. Must be between 0 and 2400 (inclusive).
	*@param showLocalTime a boolean value indicating whether to display the local time or not.
	*@return the formatted time string in the HH:mm format, or an error message if the input is invalid.
	*/
	public static String convertToTime(int digits, boolean showLocalTime) {
		if (digits < 0 || digits > 2400) {
			return "Invalid input";
		}
		int hours = digits / 100;
		int minutes = digits % 100;
		return String.format("%02d:%02d", hours, minutes);
	}
}

