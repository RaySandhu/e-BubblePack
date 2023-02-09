package application;

public class Medication {

	
	private String tradeName;
	private int dosage;	
	private Schedule schedule; 
	private Boolean isMissed;
	private Boolean isAdministered;

	//constructor
	public Medication(String name, int dose, String[] daily, String[] timely) {
		this.tradeName = name;
		this.dosage = dose;
		this.schedule = new Schedule(daily, timely);
		this.isMissed = false;
		this.isAdministered = false;
	  }
	
	// Read
	public String getTradeName() {
		return tradeName;
	}

	public void getAllScheduleInformation() {
		System.out.printf("The schedule information for ", tradeName);
		schedule.parseToStringDailySchedule();
		schedule.parseToStringTimelySchedule();
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
	
	public void tookMedication() {
		if(!this.isAdministered) {
			this.isAdministered = true;
		}
	}
	
	public void checkMissedMed() {
		// if current day = day due, check time
			//wif current time after time due, isMissed = true
		// check if current day is greater than previous days and if isAdministered = false, isMissed = true
		//else dose is not yet missed
	}
	
}
