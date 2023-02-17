package application;

import java.util.ArrayList;

public class Medication {

	private String tradeName;
	private int dosage;	
	private String dosageUnit;
	private Schedule schedule; 

	//constructor
	public Medication(String name, int dose, String dosageUnit, String[] daily, String[] timely) {
		this.tradeName = name;
		this.dosage = dose;
		this.dosageUnit = dosageUnit;
		this.schedule = new Schedule(daily, timely);
	  }
	
	// Read
	public String getTradeName() {
		return tradeName;
	}

	public String getDosage() {
		return "" + dosage + dosageUnit;
	}

	public ArrayList<ArrayList<Integer>> getSchedule() {
		return schedule.getScheduleData();
	}

	public ArrayList<ArrayList<Boolean>> getAdministrationRecord() {
		return schedule.getAdministrationStatus();
	}
	
	//Update
	public void editMedicationName(String newName) {
		tradeName = newName;
	}

	public void editDosage(int newDosage, String newDosageUnit) {
		dosage = newDosage;
		dosageUnit = newDosageUnit;
	}

	public void editWeeklySchedule(String[] weeklySchedule) {
		schedule.editWeeklySchedule(weeklySchedule);
	}

	public void editDailySchedule(String[] dailySchedule) {
		schedule.editDailySchedule(dailySchedule);
	}
	
	public void toggleMedicationAdministered() {
		// !!!
	}
	
	public void updateMissedMeds() {
		ArrayList<Integer> timesDue = schedule.getScheduleData().get(1);
		for(int i=0; i<timesDue.size(); i++) {
			if(Schedule.getCurrentTimeAsInt() > timesDue.get(i) && !schedule.getAdministrationStatus().get(i).get(0)) {	
				//checks that the med has not been administered and the current time is later than the dose time.
				schedule.getAdministrationStatus().get(i).set(1, true);
			}
		}
	}

	public String nextDose() {
		ArrayList<Integer> timesDue = schedule.getScheduleData().get(1);
		for(int i : timesDue) {
			if(Schedule.getCurrentTimeAsInt() < i) {	
				return "The next dose is due at " + Schedule.getTimeAsString(i);
			}
		}
		return "The next dose is not due today!";
    }
	
}
