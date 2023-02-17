package application;
import java.util.*;

public class MedList {
	private static ArrayList<Medication> medications = new ArrayList<Medication>();
	
	// Create
	public static void addMedications(String nameOfMed, int dosageOfMed, String dosageUnit, String dailySchedule, String timelySchedule) {
		String[] usableDailySchedule = dailySchedule.split("");
		String[] usableTimelySchedule = timelySchedule.split(",");
		Medication newMed = new Medication(nameOfMed, dosageOfMed, dosageUnit , usableDailySchedule, usableTimelySchedule);
		medications.add(newMed);
	}
	
	//Read
	public static ArrayList<Medication> getMedications() {
		return medications;
	}
	
	public static void listAllMedicationNames() {
		for(Medication i : medications) {
			System.out.print("All the medications currently in the catalog are:");
			System.out.println(i.getTradeName());
		}
	}

	// Delete
	public static void deleteMedicationFromList(Medication medToDelete) {
		medications.remove(medToDelete);
	}
}
