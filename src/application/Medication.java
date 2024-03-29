package application;

import java.util.ArrayList;

/**
*  The fundamental blueprint of all the data for each medication stored
*/
public class Medication extends ScheduleElements {

	private int keyId;
	private String tradeName;
	private float dosage;	
	private String dosageUnit;
	private Schedule schedule; 

	/**
	*Constructor for creating a new medication object
	*@param name the trade name of the medication
	*@param dose the dosage of the medication
	*@param dosageUnit the unit of the medication dosage
	*@param daily an array of times the medication should be taken each day
	*@param timely an array of days the medication should be taken each week
	*/
	public Medication(int keyId, String name, float dose, String dosageUnit, String[] daily, String[] timely) {
		this.keyId = keyId;
		this.tradeName = name;
		this.dosage = dose;
		this.dosageUnit = dosageUnit;
		this.schedule = new Schedule(daily, timely);
	  }
	
	/**
	*Reads the trade name of the medication.
	*@return the trade name of the medication in string format.
	*/
	public String getTradeName() {
		return tradeName;
	}

	/**
	*Returns the dosage of the medication as a string in the format of "dosage dosageUnit".
	*@return The dosage of the medication as a string in the format of "dosage dosageUnit".
	*/
	public String getDosage() {
		return "" + dosage + " "+ dosageUnit;
	}
	
	/**
	 * getter for each medication's unique identifier
	 * @return the integer that is the medication's unique identifier.
	 */
	public int getId() {
		return keyId;
	}

	/**
	 * Returns a two-dimensional ArrayList containing the medication schedule data.
	 * @return a two-dimensional ArrayList containing the medication schedule data.
	 */
	public ArrayList<ArrayList<Integer>> getSchedule() {
		return schedule.getScheduleData();
	}
	
	/**
	 * Simplifies a messy lookup process for whether a specific dose has been administered, missed, or is not due yet.
	 * @param doseIndex directly asks for which dose number of the day is being checked and relies on the dose time being verified previously
	 * @return the strings are specifically indicative of the administration status for each dose.
	 */
	public String checkAdminStatusPerDose(int doseIndex) {
		String displayStatus = "";
		Boolean adminStatus = schedule.getAdministrationStatus().get(doseIndex).get(0);
		Boolean missedStatus = schedule.getAdministrationStatus().get(doseIndex).get(1);
		
		if(adminStatus) {
			displayStatus = "Taken";
		} else if (missedStatus) {
			displayStatus = "Missed";
		} else if(!adminStatus && !missedStatus) {
			displayStatus = "Not due yet";
		}
		
		return displayStatus;
	}

	/**
	*Returns the administration record for this medication as a two-dimensional ArrayList of Booleans.
	*The outer ArrayList represents the doses of medication (e.g. first, second, third), and the inner ArrayList
	*represents the days of the week (e.g. Monday, Tuesday, etc.). The value at each element in the two-dimensional
	*ArrayList indicates whether or not the medication was administered at that dose and on that day.
	*@return The administration record for this medication
	*/
	public ArrayList<ArrayList<Boolean>> getAdministrationRecord() {
		return schedule.getAdministrationStatus();
	}
	
	/**
	 * Updates the trade name of the medication.
	 * @param newName The new trade name of the medication.
	 */
	public void editMedicationName(String newName) {
		tradeName = newName;
	}

	/**
	*Sets a new dosage and dosage unit for this medication.
	*@param newDosage an integer representing the new dosage of the medication
	*@param newDosageUnit a string representing the new dosage unit of the medication
	*/
	public void editDosage(int newDosage, String newDosageUnit) {
		dosage = newDosage;
		dosageUnit = newDosageUnit;
	}

	/**
	*Updates the weekly schedule of the medication.
	*@param weeklySchedule an array of strings containing the updated schedule for each day of the week.
	*/
	public void editWeeklySchedule(String[] weeklySchedule) {
		schedule.editWeeklySchedule(weeklySchedule);
	}

	/**
	*Edits the daily schedule of the medication.
	*@param dailySchedule an array of strings representing the new daily schedule
	*/
	public void editDailySchedule(String[] dailySchedule) {
		schedule.editDailySchedule(dailySchedule);
	}
	
	/**
	*Toggles the administration status of a dose to be either administered or not administered.
	*@param doseToToggle an integer value representing the index of the dose to toggle administration status
	*/
	public void toggleMedicationAdministered(Integer doseToToggle) {
		schedule.toggleAdministrationStatus(doseToToggle);
	}
	
	public void deleteMedDose(int doseIndex) {
		schedule.getScheduleData().get(1).remove(doseIndex);
		schedule.getAdministrationStatus().remove(doseIndex);
	}
	
	/**
	 * Updates the administration status for any missed medication doses. 
	 * If the current time is past the scheduled time for a dose and the dose has not already been administered, 
	 * the administration status is set to true for that dose.
	 */
	public void updateMissedMeds() {
		ArrayList<Integer> timesDue = schedule.getScheduleData().get(1);
		for(int i=0; i<timesDue.size(); i++) {
			if(getCurrentTimeAsInt() > timesDue.get(i)) {	
				//checks that the med has not been administered and the current time is later than the dose time.
				schedule.getAdministrationStatus().get(i).set(1, true);
			} 
		}
	}

	/**
	*Returns the time of the next dose of the medication according to its schedule.
	*@return a string indicating the time of the next dose or a message indicating that there are no more doses due today
	*/
	public String nextDose() {
		ArrayList<Integer> timesDue = schedule.getScheduleData().get(1);
		for(int i : timesDue) {
			if(getCurrentTimeAsInt() < i) {	
				return "The next dose is due at " + getTimeAsString(i);
			}
		}
		return "The next dose is not due today!";
    }
	
}
