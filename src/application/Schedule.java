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

	/**
	*Constructor for the Schedule class that initializes the Schedule object with the specified parameters.
	*@param dailySchedule An array of strings representing the daily schedule for this Schedule object.
	*@param timelySchedule An array of strings representing the timely schedule for this Schedule object.
	*/
	public Schedule(String[] dailySchedule, String[] timelySchedule) {
		daysDue = setDaysDue(dailySchedule);
		timesDue = setTimesDue(timelySchedule);
		administrationStatusPerDose = initAdministrationStatus();
	}
	
	/**
	*Returns the schedule data for this Schedule object as an ArrayList of ArrayLists of integers.
	*@return The schedule data for this Schedule object, consisting of two ArrayLists of integers: one for the days due and one for the times due.
	*/
	public ArrayList<ArrayList<Integer>> getScheduleData() {
		ArrayList<ArrayList<Integer>> scheduleData = new ArrayList<>();
		scheduleData.add(this.daysDue);
		scheduleData.add(this.timesDue);

		return scheduleData;
	}

	/**
	*Returns the administration status per dose for this Schedule object as an ArrayList of ArrayLists of Booleans.
	*@return The administration status per dose for this Schedule object, as an ArrayList of ArrayLists of Booleans.
	*/
	public ArrayList<ArrayList<Boolean>> getAdministrationStatus() {
		return administrationStatusPerDose;
	}

	/**
	*Sets the times due for this Schedule object based on the provided array of strings.
	*@param newTimes An array of strings representing the new times due for this Schedule object.
	*@return An ArrayList of integers representing the times due for this Schedule object.
	*/
	public ArrayList<Integer> setTimesDue(String[] newTimes) {
		ArrayList<Integer> schedulePerDay = new ArrayList<Integer>();
		for(String i : newTimes) {
			schedulePerDay.add(Integer.parseInt(i));
		}
		return schedulePerDay;
	}

	/**
	*Sets the days due for this Schedule object based on the provided array of strings.
	*@param newDays An array of strings representing the new days due for this Schedule object.
	*@return An ArrayList of integers representing the days due for this Schedule object.
	*/
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

	/**
	*Initializes the administration status for this Schedule object with a default status of false for each dose.
	*@return An ArrayList of ArrayLists of Booleans representing the administration status per dose for this Schedule object.
	*/
	public ArrayList<ArrayList<Boolean>> initAdministrationStatus() {
		for (int i=0; i<timesDue.size(); i++) {
			ArrayList<Boolean> defaultStatus = new ArrayList<>();
			defaultStatus.add(false);
			defaultStatus.add(false);
			administrationStatusPerDose.add(defaultStatus);
		}
		return administrationStatusPerDose;
	}

	/**
	*Toggles the administration status for a specified dose in this Schedule object.
	*@param doseToToggle The index of the dose to toggle the administration status for.
	*/
	public void toggleAdministrationStatus(Integer doseToToggle) {
		administrationStatusPerDose.get(doseToToggle).set(0,!administrationStatusPerDose.get(doseToToggle).get(0));
	}

	/**
	*Edits the daily schedule for this Schedule object and initializes the administration status for each dose to false.
	*@param dailySchedule An array of strings representing the new daily schedule for this Schedule object.
	*/
	public void editDailySchedule(String[] dailySchedule) {
		timesDue = setTimesDue(dailySchedule);
		administrationStatusPerDose = initAdministrationStatus();
	}

	/**
	*Edits the weekly schedule for this Schedule object.
	*@param weeklySchedule An array of strings representing the new weekly schedule for this Schedule object.
	*/
	public void editWeeklySchedule(String[] weeklySchedule) {
		daysDue = setDaysDue(weeklySchedule);
	}
	
	/**
	*Gets the current day of the week as an integer, where 0 represents Sunday and 6 represents Saturday.
	*@return An integer representing the current day of the week.
	*/
	public static int getTodaysDayAsNum() {
		if(today == 7) {
			return 0;
		} else return today;
	}

	/**
	*Gets the current time as an integer, where the integer is in the format "HHMM".
	*@return An integer representing the current time.
	*/
	public static int getCurrentTimeAsInt() {
        int timeAsInt = (currentTime.getHour() * 100) + currentTime.getMinute();
		return timeAsInt;
	}

	/**
	*Converts an integer in the format "HHMM" to a string in the format "HH:MM".
	*@param timeAsInt An integer representing the time in the format "HHMM".
	*@return A string representing the time in the format "HH:MM", or "Invalid input" if the input is not valid.
	*/
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
}