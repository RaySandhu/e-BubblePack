package application;
import java.util.*;

public class MedList {
	// !!! need to build csv read/write functionality for persistence
	/**
	*Static ArrayList of Medication objects representing the catalog of all medications.
	*This list is initialized as empty and can be modified through the methods of the class.
	*/
	private static ArrayList<Medication> medications = new ArrayList<Medication>();

	// Create
	public static void addMedications(String nameOfMed, int dosageOfMed, String dosageUnit, String dailySchedule, String timelySchedule) {
		String[] usableDailySchedule = dailySchedule.split("");
		String[] usableTimelySchedule = timelySchedule.split(",");
		Medication newMed = new Medication(nameOfMed, dosageOfMed, dosageUnit , usableDailySchedule, usableTimelySchedule);
		medications.add(newMed);
	}
	
	/**
	*Returns an ArrayList of all medications currently in the catalog.
	*Assumes that the catalog is stored as a static ArrayList of Medication objects.
	*@return the ArrayList of Medication objects representing all medications in the catalog.
	*/
	public static ArrayList<Medication> getMedications() {
		return medications;
	}
	
	/**
	*Prints a list of all medication names currently in the catalog.
	*Assumes that the catalog is stored as a static list of Medication objects.
	*/
	public static void listAllMedicationNames() {
		for(Medication i : medications) {
			System.out.print("All the medications currently in the catalog are:");
			System.out.println(i.getTradeName());
		}
	}

	// Delete
}
