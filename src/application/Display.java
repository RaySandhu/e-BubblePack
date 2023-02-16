package application;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;

public class Display {
    //takes in parameters and data from Medlist and will be running until executed in main

    		// schedule retrieval code

		// for(Medication i: MedList.getMedications()) {
		// 	ArrayList<Integer> timeData = i.getSchedule().get(1);
		// 	for(Integer j: timeData) {
		// 		System.out.println(j);
		// 	}
		// 	System.out.println("--------End of time data for " + i.getTradeName());
		// }

    public static Scanner mainInputScanner = new Scanner(System.in);

    private static String daySelectedString(Integer daySelectedInt) {
        return DayOfWeek.of(daySelectedInt).getDisplayName(TextStyle.FULL, Locale.CANADA);
    } 

    public static ArrayList<Medication> dailyMedicationList(Integer dayOfWeek) {
        ArrayList<Medication> medListForSelectedDay = new ArrayList<>();

        for(Medication i: MedList.getMedications()) {
            ArrayList<Integer> medScheduleperDay = i.getSchedule().get(0);
            if (medScheduleperDay.get(dayOfWeek) != -1) {
                medListForSelectedDay.add(i);
            }
        }

        return medListForSelectedDay;
    }

    

    public static void introScene() {

        Boolean validInput = false;
        Integer userMainMenuInput; 
        
        System.out.printf("""
            Welcome to the e-BubblePack, your one stop medication tracker!

            The current time is %d

            Select a option to view medication for the day:

            Press '0' to view Sunday medications
            Press '1' to view Monday medications
            Press '2' to view Tuesday medications
            Press '3' to view Wednesday medications
            Press '4' to view Thursday medications
            Press '5' to view Friday medications
            Press '6' to view Saturday medications
            Press '7' to exit the application 

                """, 10); // !!! put in the correct time variable

        while (!validInput) {
            try {    
                userMainMenuInput = mainInputScanner.nextInt();
                if (userMainMenuInput >= 0 && userMainMenuInput < 7) {
                    validInput = true;
                    System.out.println("This is working");
                    medicationDisplayScene(userMainMenuInput, dailyMedicationList(userMainMenuInput));
                } else if(userMainMenuInput == 7) {
                    System.out.println("Closing Application");
                    return;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch(Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                mainInputScanner.nextLine(); 
            }
        }
    }

    public static void medicationDisplayScene(Integer selectedDay, ArrayList<Medication> medicationsForSelectedDay) {
        for(Medication i : medicationsForSelectedDay) {
            System.out.println(i.getTradeName());
        }
    }
}
