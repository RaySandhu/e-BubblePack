package application;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;

public class Display {

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
        System.out.println("Intro scene triggered");

        Boolean validInput = false;
        Integer userMainMenuInput; 
        
        System.out.printf("""
            Welcome to the e-BubblePack, your one stop medication tracker!

            The current time is %d %d

            Select an option to view medication for the day:

            Press '0' to view Sunday medications
            Press '1' to view Monday medications
            Press '2' to view Tuesday medications
            Press '3' to view Wednesday medications
            Press '4' to view Thursday medications
            Press '5' to view Friday medications
            Press '6' to view Saturday medications
            Press '7' to exit the application 

                """, 10, Schedule.getTodaysDayAsNum()); // !!! put in the correct time variable

        while (!validInput) {
            try {    
                System.out.println("""

                        Your selection:
                        """);
                userMainMenuInput = mainInputScanner.nextInt();
                mainInputScanner.nextLine(); // consume next line
                if (userMainMenuInput >= 0 && userMainMenuInput < 7) {
                    validInput = true;
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
        System.out.println("Medication Display Scene triggered");

        Integer medIndex = 1;
        Boolean validInput = false;
        Integer userMainMenuInput; 
        Integer numberOfMedsThisDay = medicationsForSelectedDay.size();


        System.out.println("These are the medications that are due today: ");
        for(Medication i : medicationsForSelectedDay) {
            System.out.println(medIndex + ". " +i.getTradeName());
            medIndex++;
        }
        System.out.println(numberOfMedsThisDay+1 + ". Return to the main menu");

        while (!validInput) {
            try {
                System.out.println("""

                        Your selection:
                        """);
                userMainMenuInput = mainInputScanner.nextInt();
                mainInputScanner.nextLine(); // consume next line
                if (userMainMenuInput >= 1 && userMainMenuInput <= numberOfMedsThisDay) {
                    validInput = true;
                    medicationInformationDisplay(selectedDay ,medicationsForSelectedDay.get(userMainMenuInput-1));
                    // need empty return?
                } else if(userMainMenuInput == numberOfMedsThisDay+1) {
                    introScene();
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

    public static void medicationInformationDisplay(Integer selectedDay , Medication medToDisplay) {
        System.out.println("Medication Information Display triggered");

        Boolean validInput = false;
        Integer userMainMenuInput; 
        medToDisplay.updateMissedMeds();

        System.out.printf("""
           
        Here is all the information about %s:

        Dosage to be taken: %s
        %s

        Your previous doses for today are as follows:
            Scheduled Time          Taken?
        """, medToDisplay.getTradeName(), medToDisplay.getDosage(), medToDisplay.nextDose());

        ArrayList<Integer> timesDue = medToDisplay.getSchedule().get(1);
		for(int i=0; i<timesDue.size(); i++) {
            Boolean adminStatus = medToDisplay.getAdministrationRecord().get(i).get(0);
            Boolean missedStatus = medToDisplay.getAdministrationRecord().get(i).get(1);
            String displayStatus;

            if(adminStatus) {
                displayStatus = "Taken";
            } else if (missedStatus) {
                displayStatus = "Missed";
            } else displayStatus = "Something went wrong";

			if(Schedule.getCurrentTimeAsInt() > timesDue.get(i)) {	
				System.out.printf("""
                        %d.        %s              %s
                        """, i+1, Schedule.getTimeAsString(timesDue.get(i)), displayStatus);
			} else {
                System.out.printf("""
                        %d.        %s              Not due yet
                        """, i+1, Schedule.getTimeAsString(timesDue.get(i)));
            }

		}
        System.out.printf("""
            Select one of the following actions:

            Enter '1' to edit the medication name
            Enter '2' to edit the medication dosage
            Enter '3' to edit which days of the week this medication is due
            Enter '4' to edit the times this medication is due each day
            Enter '5' to return to %s's schedule
            Enter '6' to return to the main menu
                """, daySelectedString(selectedDay));

        while (!validInput) {
            try {
                System.out.println("""

                        Your selection:
                        """);
                userMainMenuInput = mainInputScanner.nextInt();
                mainInputScanner.nextLine(); // consume next line
                if (userMainMenuInput >= 1 && userMainMenuInput <= 4) {
                    validInput = true;
                    //handle options !!!
                    switch(userMainMenuInput) {
                        case 1:
                            System.out.println("Please enter the new name for this medication...");
                            String newName = mainInputScanner.nextLine();
                            medToDisplay.editMedicationName(newName);
                            System.out.println("The medication name has been changed to " + medToDisplay.getTradeName());
                            medicationInformationDisplay(selectedDay, medToDisplay);
                            return;
                        case 2:
                            Boolean validDosageInput = false;
                            Integer newDosage = 0;
                            String newDosageUnit = "Something went really wrong, edit again";
                            while(!validDosageInput) {
                                try {
                                    System.out.println("Please enter the new dosage in integers for this medication...");
                                    newDosage = mainInputScanner.nextInt();
                                    mainInputScanner.nextLine(); // consume next line
                                    System.out.println("Now enter the units in which you are measuring the dosage...");
                                    newDosageUnit = mainInputScanner.nextLine();
                                    validDosageInput = true;
                                } catch(Exception e) {
                                    System.out.println("Invalid input. Please enter a valid integer.");
                                    mainInputScanner.nextLine(); 
                                }
                            }
                            medToDisplay.editDosage(newDosage, newDosageUnit);
                            System.out.println("The medication dosage has been changed to " + medToDisplay.getDosage());
                            medicationInformationDisplay(selectedDay, medToDisplay);
                            return;
                        case 3:
                            System.out.println("Currently a circular option");
                            medicationInformationDisplay(selectedDay, medToDisplay);
                            return;
                        case 4:
                            System.out.println("Currently a circular option");
                                medicationInformationDisplay(selectedDay, medToDisplay);
                                return;
                    }
                } else if (userMainMenuInput == 5) {
                    medicationDisplayScene(selectedDay, dailyMedicationList(selectedDay));
                    return;
                } else if(userMainMenuInput == 6) {
                    introScene();
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
}
