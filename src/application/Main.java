package application;

public class Main {

	public static void main(String[] args) {

		// test medications on program run
		MedList.addMedications("Tylenol3", 500,"g", "024", "0800,1200,1600,2000");
		MedList.addMedications("Pantoprazole", 200,"mg", "0123456", "0600,1000");
		MedList.addMedications("Ibuprofen", 5,"ml", "5", "1800");
		MedList.addMedications("Dexamethasone", 400, "g", "246", "0901,1312,2023,2355");
		MedList.addMedications("Maxeran", 200, "g", "0135", "0610,1050,");
		MedList.addMedications("Zofran", 700, "mg", "1356", "1900,2222,2323,2400");
		MedList.addMedications("Propofol", 1000, "g", "235", "0000,1000,1400,1800");
		MedList.addMedications("Ketamine", 25,"g", "145", "0600,1000,1400,2400");

		Display.introScene();
	}
	
	public static void ShowLocalTime(String[] args) {
		/**
		*displays the current local time in an integer format
		*@param args an array of command-line arguments that are not used by this function 
		*/
        	Calendar calendar = Calendar.getInstance();
	        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        	int minutes = calendar.get(Calendar.MINUTE);
	        int currentTime = hours * 100 + minutes;
        	System.out.println("Current time in integer format: " + currentTime);    
	}
}
