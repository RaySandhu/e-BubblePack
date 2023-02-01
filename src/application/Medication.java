package application;

public class Medication {
	
	private String tradeName;
	private int dosage;
	//note of interest: using 24hr clock is not simple because of leading 0
	private Schedule schedule;
	
	private Boolean isMissed;
	private Boolean isAdministered;
	
	public String getTradeName() {
		return tradeName;
	}
	
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
	public void tookMedication() {
		if(!this.isAdministered) {
			this.isAdministered = true;
		}
	}
	
	public void missedDose() {
		// if current day = day due, check time
			//wif current time after time due, isMissed = true
		// check if current day is greater than previous days and if isAdministered = false, isMissed = true
		//else dose is not yet missed
	}
	
}
