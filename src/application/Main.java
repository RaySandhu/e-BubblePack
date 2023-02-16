package application;
	
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {

		// test medications on program run
		MedList.addMedications("Tylenol3", 500,"g", "024", "0800,1200,1600,2000");
		MedList.addMedications("Pantoprazole", 200,"mg", "0123456", "0600,1000");
		MedList.addMedications("Ibuprofen", 5,"ml", "5", "1800");
		MedList.addMedications("Dexamethasone", 400, "g", "246", "0901,1312,2023,2355");
		MedList.addMedications("Maxeran", 200, "g", "0135", "0610,1050,");
		MedList.addMedications("Zofran", 700, "mg", "1356", "1900,2222,2323,2424");
		MedList.addMedications("Propofol", 1000, "g", "235", "0000,1000,1400,1800");
		MedList.addMedications("Ketamine", 25,"g", "145", "0600,1000,1400,2400");

		Display.introScene();
		// Schedule.getCurrentTimeAsInt();


		// time based functions to update status of medications will be necessary
			// reset of all medication statuses on Sunday at midnight
			// regular and manual checks for missed doses
	}
}
