package application;
	
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class Main {

	public Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		// testing medication (C)RUD function
		MedList.addMedications("Tylenol3", 500, "024", "0800,1200,1600,2000");
		MedList.addMedications("Pantoprazole", 1000, "135", "0600,1000,1400,1800");


		MedList.listAllMedicationNames();
		// for (Medication i: MedList.getMedications()) {
		// 	i.getAllScheduleInformation();
		// }

		// time based functions to update status of medications will be necessary
			// reset of all medication statuses on Sunday at midnight
			// regular and manual checks for missed doses
	}
	
	public static void ShowLocalTime(String[] args) {
        	Calendar calendar = Calendar.getInstance();
	        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        	int minutes = calendar.get(Calendar.MINUTE);
	        int currentTime = hours * 100 + minutes;
        	System.out.println("Current time in integer format: " + currentTime);    
	}
}
