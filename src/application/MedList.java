package application;
import java.util.*;

public class MedList {
	// !!! need to build csv read/write functionality for persistence
	private static ArrayList<Medication> medications = new ArrayList<Medication>();
	private static Scanner medUpdate = new Scanner(System.in);

	public static ArrayList<Medication> getMedications() {
		return medications;
	}

	// Create
	public static void addMedications(String nameOfMed, int dosageOfMed, String dailySchedule, String timelySchedule) {
		String[] usableDailySchedule = dailySchedule.split("");
		String[] usableTimelySchedule = timelySchedule.split(",");
		Medication newMed = new Medication(nameOfMed, dosageOfMed, usableDailySchedule, usableTimelySchedule);
		medications.add(newMed);
	}

	//Read

	public static void listAllMedicationNames() {
		for(Medication i : medications) {
			System.out.print("All the medications currently in the catalog are:");
			System.out.println(i.getTradeName());
			System.out.println("-------------");
		}
	}

	// Update

	// should just take all parameters for the step in the display first and pass them in one go instead of function calling for info
	public static void editMedication(String medNameToEdit) {
		int indexToEdit = -1;
		for (Medication i : medications) {
			if (i.getTradeName().equals(medNameToEdit)) {
				indexToEdit = medications.indexOf(i);
				break;
			}
		}
		if (indexToEdit != -1) {
			Medication medToEdit = medications.get(indexToEdit);
			System.out.println("Would you like to change: ");
			System.out.println("""
					1. Name of Medication
					2. Dosage of Medication
					3. Schedule of Medication
					""");

			int updateChoice = Integer.parseInt(medUpdate.nextLine());
			switch(updateChoice){
				case 1:
					System.out.println("Please enter a new name for the medication");
					medToEdit.editTradeName(medUpdate.nextLine());
				case 2:
					System.out.println("Please enter the new dosage for this medication");
					medToEdit.editDosage(medUpdate.nextInt());
				case 3:
					System.out.println("""
						Currently medications can only have a consistent timely schedule across their daily schedules
						For example, 
						Medication A that is due 3 times a day on Monday and 2 times a day on Tuesday would need to be entered separately for each schedule.

						Please enter the new daily schedule for %s with Sunday as 0 and Saturday as 6.
						Example: A Monday-Wednesday-Friday schedule would be input as 024

							""");
					String[] dailySchedule = medUpdate.nextLine().split("");

					System.out.print("""
							Now please enter the timely schedule for each of the days the medication is due in 24hr format separated by commas.

							Example: 
							A medication due at 8am, 12pm, 4pm and 8pm would be input as
												
							0800,1200,1600,2000

							""");
					String[] timelySchedule = medUpdate.nextLine().split(",");

					medToEdit.editSchedule(dailySchedule, timelySchedule); 
			}
		} else {
			System.out.println("Medication not found");
		}
	}

	// Delete
}
