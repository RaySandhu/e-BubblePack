package application;
	
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class Main {

	public Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		// test medications on program run
		MedList.addMedications("Tylenol3", 500, "024", "0800,1200,1600,2000");
		MedList.addMedications("Pantoprazole", 1000, "135", "0600,1000,1400,1800");
		
		for(Medication i: MedList.getMedications()) {
			System.out.println(i.getSchedule());
		}
		// time based functions to update status of medications will be necessary
			// reset of all medication statuses on Sunday at midnight
			// regular and manual checks for missed doses
	}
}
