/**
 * 
 */
package application;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ScheduleElements {
	LocalDateTime currentDateTime = LocalDateTime.now();
	
	/**
	 *Gets the current day of the week as an integer, where 0 represents Sunday and 6 represents Saturday.
	 *@return An integer representing the current day of the week.
	 */
	public int getTodaysDayAsNum() {
		if(currentDateTime.getDayOfWeek().getValue() == 7) {
			return 0;
		} else return currentDateTime.getDayOfWeek().getValue();
	}
	
	/**
	 *Gets the current time as an integer, where the integer is in the format "HHMM".
	 *@return An integer representing the current time.
	 */
	public int getCurrentTimeAsInt() {
		int timeAsInt = (currentDateTime.getHour() * 100) + currentDateTime.getMinute();
		return timeAsInt;
	}
	
	/**
	 *Converts an integer in the format "HHMM" to a string in the format "HH:MM".
	 *@param timeAsInt An integer representing the time in the format "HHMM".
	 *@return A string representing the time in the format "HH:MM", or "Invalid input" if the input is not valid.
	 */
	public String getTimeAsString(int timeAsInt) {

		String hourDisplay;
		String minutesDisplay;
		if (timeAsInt < 0 || timeAsInt > 2400) {
			return "Invalid input";
		}
		int hours = timeAsInt / 100;
		int minutes = timeAsInt % 100;

		if(minutes >= 60) {
			hours+=1;
			minutes-=60;
		}
		if (hours < 10) {
			hourDisplay = "0" + hours;
		} else hourDisplay = "" + hours;

		if (minutes < 10) {
			minutesDisplay = "0" + minutes;
		} else minutesDisplay = "" + minutes;

		return (hourDisplay + ":" + minutesDisplay);
	}
	
	/**
	 *Returns a list of Medication objects scheduled for the selected day of the week.
	 *@param dayOfWeek the integer value of the selected day of the week (0 = Sunday, 1 = Monday, etc.)
	 *@return an ArrayList of Medication objects scheduled for the selected day of the week
	 */
	public ArrayList<Medication> dailyMedicationList(int dayOfWeek) {
		ArrayList<Medication> medListForSelectedDay = new ArrayList<>();

		for(Medication i: MedList.getMedications()) {
			ArrayList<Integer> medScheduleperDay = i.getSchedule().get(0);
			if (medScheduleperDay.get(dayOfWeek) != -1) {
				medListForSelectedDay.add(i);
			}
		}

		return medListForSelectedDay;
	}

}
