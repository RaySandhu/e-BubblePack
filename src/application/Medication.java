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

	public String getAllScheduleInformation() {
		return schedule.parseToStringDailySchedule() + "\n" + schedule.parseToStringTimelySchedule();
	}

	public ArrayList<ArrayList<Integer>> getSchedule() {
		return schedule.getScheduleData();
	}
	
	//Update
	public void editTradeName(String newTradeName) {
		tradeName = newTradeName;
	}

	public void editDosage(int newDosage) {
		dosage = newDosage;
	}

	public void editSchedule(String[] dailySchedule, String[] timeSchedule) {
		schedule.setDaysDue(dailySchedule);
		schedule.setTimesDue(timeSchedule);
	}
	
	public void toggleMedicationAdministered() {
		
	}
	
	public void updateMissedMeds() {
		ArrayList<Integer> timesDue = schedule.getScheduleData().get(1);
		for(int i=0; i<timesDue.size(); i++) {
			if(Schedule.getCurrentTimeAsInt() > timesDue.get(i)) {
				System.out.println(schedule.getAdministrationStatus().get(i).get(1));
			}
		}
	}
	
}
