package application;
import java.util.*;

/**
 * Handles the current method of storage for all medication data in an arraylist with methods for 
 * manipulating the same list per user needs
 */
public class MedList {
	// !!! need to build csv read/write functionality for persistence
	/**
	 *Static ArrayList of Medication objects representing the catalog of all medications.
	 *This list is initialized as empty and can be modified through the methods of the class.
	 */
	private static ArrayList<Medication> medications = new ArrayList<Medication>();
	private static int idCounter = 0;

	// Create
	/**
	 *Adds a new Medication object to the catalog with the specified parameters.
	 *Assumes that the catalog is stored as a static ArrayList of Medication objects.
	 *@param nameOfMed The trade name of the new medication to be added to the catalog.
	 *@param dosageOfMed The dosage amount of the new medication to be added to the catalog.
	 *@param dosageUnit The unit of dosage of the new medication to be added to the catalog.
	 *@param dailySchedule The daily schedule of the new medication to be added to the catalog, as a string of characters.
	 */
	public static void addMedications(String nameOfMed, int dosageOfMed, String dosageUnit, String dailySchedule, String timelySchedule) {
		idCounter++;
		String[] usableDailySchedule = dailySchedule.split("");
		String[] usableTimelySchedule = timelySchedule.split(",");
		Medication newMed = new Medication(idCounter, nameOfMed, dosageOfMed, dosageUnit , usableDailySchedule, usableTimelySchedule);
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
	 * A basic linear search lookup of the medication in medlist by its keyId field
	 * @param searchId the id that is being looked for through the medlist
	 * @return the entire medication that is associated with the unique keyId
	 */
	public static Medication retrieveMedById(int searchId) {
		Medication targetMed = null;
		for (Medication m : medications) {
			if (m.getId() == searchId) {
				targetMed = m;
			}
		}
		return targetMed;
		
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

	/**
	 *Removes the specified Medication object from the catalog.
	 *Assumes that the catalog is stored as a static ArrayList of Medication objects.
	 *@param medToDelete The Medication object to be removed from the catalog.
	 */
	public static void deleteMedicationFromList(Medication medToDelete) {
		medications.remove(medToDelete);
	}
}
