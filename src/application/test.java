import java.text.SimpleDateFormat;
import java.util.*;

public class MedicineBubblePack {
   static Scanner input = new Scanner(System.in);
   static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
   static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
   static List<Medication> medications = new ArrayList<Medication>();

   static class Medication {
      String name;
      List<String> days;
      String time;
      String info;
      boolean taken;

      Medication(String name, List<String> days, String time, String info) {
         this.name = name;
         this.days = days;
         this.time = time;
         this.info = info;
         this.taken = false;
      }

      void markTaken() {
         taken = true;
      }

      boolean isDue() {
         Calendar now = Calendar.getInstance();
         if (days.contains(now.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()))) {
            try {
               Date dueTime = timeFormat.parse(time);
               Calendar dueCal = Calendar.getInstance();
               dueCal.setTime(dueTime);
               if (now.get(Calendar.HOUR_OF_DAY) == dueCal.get(Calendar.HOUR_OF_DAY)
                     && now.get(Calendar.MINUTE) == dueCal.get(Calendar.MINUTE)) {
                  return true;
               }
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         return false;
      }
   }

   public static void main(String[] args) {
      // Add default medications
      List<String> tylenolDays = Arrays.asList("Monday", "Tuesday", "Wednesday");
      Medication tylenol = new Medication("Tylenol", tylenolDays, "08:00", "to prevent headaches");
      medications.add(tylenol);

      List<String> advilDays = Arrays.asList("Thursday", "Friday", "Saturday", "Sunday");
      Medication advil = new Medication("Advil", advilDays, "18:00", "to prevent fever");
      medications.add(advil);

      int choice = 0;
      do {
         System.out.println("\nMedication Bubble Pack");
         System.out.println("1. Display Medications");
         System.out.println("2. Mark Medication Taken");
         System.out.println("3. Edit Medication");
         System.out.println("4. Add Medication");
         System.out.println("5. Remove Medication");
         System.out.println("6. Exit");
         System.out.print("Enter choice: ");
         choice = input.nextInt();

         switch (choice) {
            case 1:
               displayMedications();
               break;
            case 2:
               markTaken();
               break;
            case 3:
               editMedication();
               break;
            case 4:
            	addMedication();
            	break;
            case 5:
            	removeMedication();
            	break;
            case 6:
            	System.out.println("Exiting Medicine Bubble Pack");
            	break;
            default:
            	System.out.println("Invalid choice. Please enter a valid option.");
            	break;
            		}
            	} while (choice != 6);
            }
   static void displayMedications() {
	   System.out.println("\nDisplaying Medications: ");
	   System.out.println("-----------------------------");
	   System.out.println("Name\tDays\tTime\tInfo\tTaken");
	   System.out.println("-----------------------------");
	   for (Medication medication : medications) {
		   String days = String.join(", ", medication.days);
		   System.out.println(medication.name + "\t" + days + "\t" + medication.time + "\t" + medication.info + "\t" + (medication.taken ? "Yes" : "No"));
	   		}
	   }

   static void markTaken() {
	   System.out.print("Enter medication name: ");
	   String name = input.next();
	   for (Medication medication : medications) {
		   if (medication.name.equalsIgnoreCase(name)) {
			   if (medication.isDue()) {
				   medication.markTaken();
				   System.out.println("Medication marked taken.");
			   } 
			   else {
				   System.out.println("Medication not due yet.");
			   }
			   return;
		   }
	   	}
	   System.out.println("Medication not found.");
   }

   static void editMedication() {
	   System.out.print("Enter medication name: ");
	   String name = input.next();
	   for (Medication medication : medications) {
		   if (medication.name.equalsIgnoreCase(name)) {
			   System.out.print("Enter new name: ");
			   medication.name = input.next();
			   System.out.print("Enter new days (comma separated): ");
			   String days = input.next();
			   medication.days = Arrays.asList(days.split(","));
			   System.out.print("Enter new time: ");
			   medication.time = input.next();
			   System.out.print("Enter new info: ");
			   medication.info = input.next();
			   System.out.println("Medication edited successfully.");
			   return;
		   }
	   }
	   System.out.println("Medication not found.");
   }

   static void addMedication() {
	   System.out.print("Enter medication name: ");
	   String name = input.next();
	   System.out.print("Enter days (comma separated): ");
	   String days = input.next();
	   System.out.print("Enter time: ");
	   String time = input.next();
	   System.out.print("Enter info: ");
	   String info = input.next();
	   Medication medication = new Medication(name, Arrays.asList(days.split(",")), time, info);
	   medications.add(medication);
	   System.out.println("Medication added successfully.");
   }
