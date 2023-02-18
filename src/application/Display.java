package application;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;

public class Display {

    /**
    *A Scanner object for reading input from the console
    */
    public static Scanner mainInputScanner = new Scanner(System.in);

    /**
    *Returns a string representation of the day of the week corresponding to the input day number.
    *@param daySelectedInt an Integer representing the day of the week, indexed as 0-6 representing Sunday through Satursday.
    *@return a String representing the full name of the day of the week in Canadian English.
    */
    private static String daySelectedString(Integer daySelectedInt) {
        if (daySelectedInt == 0) daySelectedInt = 7;
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

            The current time is %s

            Select an option to view medication for the day:

            Press '0' to view Sunday medications
            Press '1' to view Monday medications
            Press '2' to view Tuesday medications
            Press '3' to view Wednesday medications
            Press '4' to view Thursday medications
            Press '5' to view Friday medications
            Press '6' to view Saturday medications
            Press '7' to exit the application 

                """, Schedule.getTimeAsString(Schedule.getCurrentTimeAsInt()));

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
                System.out.println("Invalid input. Please enter a valid integer. --main menu");
                mainInputScanner.nextLine(); 
            }
        }
    }

    public static void medicationDisplayScene(Integer selectedDay, ArrayList<Medication> medicationsForSelectedDay) {
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
                } else if(userMainMenuInput == numberOfMedsThisDay+1) {
                    introScene();
                    return;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch(Exception e) {
                System.out.println("Invalid input. Please enter a valid integer. --med display scene error");
                mainInputScanner.nextLine(); 
            }
        }
    }

    public static void medicationInformationDisplay(Integer selectedDay , Medication medToDisplay) {
        Boolean validInput = false;
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
            String displayStatus = "";

            if(adminStatus) {
                displayStatus = "Taken";
            } else if (missedStatus) {
                displayStatus = "Missed";
            } else if(!adminStatus && !missedStatus) {
                displayStatus = "Not due yet";
            }

				System.out.printf("""
                        %d.        %s              %s
                        """, i+1, Schedule.getTimeAsString(timesDue.get(i)), displayStatus);
		}
        
        while (!validInput) {
            try {
                Integer userMainMenuInput; 
                System.out.printf("""
                    Select one of the following actions:

                    Enter '0' to toggle the indicate if a scheduled dose is taken/not taken
                    Enter '1' to edit the medication name
                    Enter '2' to edit the medication dosage
                    Enter '3' to edit which days of the week this medication is due
                    Enter '4' to edit the times this medication is due each day
                    Enter '5' to delete this medication entirely from your scheduled medications
                    Enter '6' to return to %s's schedule
                    Enter '7' to return to the main menu
                        """, daySelectedString(selectedDay));

                userMainMenuInput = mainInputScanner.nextInt();
                mainInputScanner.nextLine(); // consume next line
                if (userMainMenuInput >= 0 && userMainMenuInput <= 5) {
                    switch(userMainMenuInput) {
                        case 0:
                            System.out.println("Enter the number of the medication you would like to toggle the 'taken' status for...");
                            Boolean validChosenDose = false;
                            Integer chosenDose = 0;

                            while(!validChosenDose) {
                                try {
                                    chosenDose = mainInputScanner.nextInt();
                                    mainInputScanner.nextLine(); // consume next line
                                    if (chosenDose > timesDue.size() || chosenDose <= 0) {
                                        throw new Exception("Please select one of the doses above as numbered.");
                                    }
                                    validChosenDose = true;
                                } catch (Exception i) {
                                    i.getMessage();
                                }
                            }
                            medToDisplay.toggleMedicationAdministered(chosenDose-1);
                            medicationInformationDisplay(selectedDay, medToDisplay);
                            return;
                        case 1:
                            System.out.println("Please enter the new name for this medication...");
                            String newName = mainInputScanner.nextLine();
                            medToDisplay.editMedicationName(newName);
                            System.out.println("The medication name has been changed to " + medToDisplay.getTradeName());
                            validInput = true;
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
                                } catch(Exception e) {
                                    System.out.println("Invalid input. Please enter a valid integer. --new dosage error");
                                    mainInputScanner.nextLine(); 
                                }
                            }
                            validDosageInput = true;
                            validInput = true;
                            medToDisplay.editDosage(newDosage, newDosageUnit);
                            System.out.println("The medication dosage has been changed to " + medToDisplay.getDosage());
                            medicationInformationDisplay(selectedDay, medToDisplay);
                            return;
                        case 3:
                            String newWeeklySchedule = "";
                            Boolean validDayInput;
                            validDayInput = false;

                            System.out.println("With Sunday as 0 and Saturday as 6, enter the weekly schedule for this medication in ascending order...");
                            
                            while(!validDayInput){
                                newWeeklySchedule = mainInputScanner.nextLine();
                                try {
                                    for(String m : newWeeklySchedule.split("")){
                                        Integer enteredDay = Integer.parseInt(m);
                                        if(enteredDay > 6 || enteredDay < 0) {
                                            throw new Exception("Please enter valid days of the week per the descriptions above");
                                        }
                                        validDayInput = true;
                                    }
                                } catch(Exception h) {
                                    System.out.println("Please enter the days for the schedule as per above. Ex/ '135' = Monday,Wednesday, Friday");
                                }
                            }
                            System.out.printf("""

                                    The weekly schedule has been updated, you will be returned to the schedule for %s...

                                    """,daySelectedString(selectedDay));
                            medToDisplay.editWeeklySchedule(newWeeklySchedule.split(""));
                            validInput = true;
                            medicationDisplayScene(selectedDay, dailyMedicationList(selectedDay));
                            return;
                        case 4:
                            Boolean validTimeInput = false;
                            while(!validTimeInput)
                                try {
                                    String stringTimeInput = "";
                                    System.out.println("""
                                        Please enter this medication's new daily schedule in 24hr format separated by commas (no spaces)...
                                        Ex. 0800,1200,1600,0000 = 8am, noon, 4pm and midnight
                                        """);
                                    stringTimeInput = mainInputScanner.nextLine();
                                    for(String k : stringTimeInput.split(",")) {
                                        if(Integer.parseInt(k) < 0 || Integer.parseInt(k) >=2400) {
                                            throw new Exception("Enter a valid input according to the instructions and example above");
                                        }   
                                    }
                                    medToDisplay.editDailySchedule(stringTimeInput.split(","));
                                    validInput = true;
                                    medicationInformationDisplay(selectedDay, medToDisplay);
                                    return;
                                } catch(Exception f) {
                                    System.out.println(f.getMessage());
                                }
                        case 5:
                            Boolean validConfirmationInput = false;
                            String confirmationInput = "";
                            while(!validConfirmationInput) {
                                try {
                                    System.out.println("Are you absolutely sure you would like to delete this medication? (Y/N)");
                                    confirmationInput = mainInputScanner.nextLine().toLowerCase();
                                    if (!confirmationInput.equals("Y".toLowerCase()) && !confirmationInput.equals("N".toLowerCase())) {
                                        throw new Exception("Please select either Y to delete or N to return to medication information");
                                    } else {
                                        validConfirmationInput = true;
                                    }
                                } catch(Exception k) {
                                    System.out.println(k.getMessage());
                                }
                            }
                            validInput = true;
                            if(confirmationInput.equals("Y".toLowerCase())) {
                                MedList.deleteMedicationFromList(medToDisplay);
                                medicationDisplayScene(selectedDay, dailyMedicationList(selectedDay));
                            } else {
                                System.out.println("Did not delete the medication");
                                medicationInformationDisplay(selectedDay, medToDisplay);
                            }
                            return;
                    }
                } else if (userMainMenuInput == 6) {
                    validInput = true;
                    medicationDisplayScene(selectedDay, dailyMedicationList(selectedDay));
                    return;
                } else if(userMainMenuInput == 7) {
                    validInput = true;
                    introScene();
                    return;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch(Exception e) {
                validInput =false;
                System.out.println("""
                    Invalid input.
                        """);
                mainInputScanner.nextLine();
            }
        }
    }
}
