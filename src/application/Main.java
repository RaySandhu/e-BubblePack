package application;
public class Main {

	public static void main(String[] args) {

		// test medications on program run
		MedList.addMedications("Tylenol3 - mock", 500,"g", "024", "0800,1200,1600,2000");
		MedList.addMedications("Pantoprazole - mock", 200,"mg", "0123456", "0600,1000");
		MedList.addMedications("Ibuprofen - mock", 5,"ml", "5", "1800");
		MedList.addMedications("Dexamethasone - mock", 400, "g", "246", "0901,1312,2023,2355");
		MedList.addMedications("Maxeran - mock", 200, "g", "0135", "0610,1050,");
		MedList.addMedications("Zofran - mock", 700, "mg", "1356", "1900,2222,2323,2400");
		MedList.addMedications("Propofol - mock", 1000, "g", "235", "0000,1000,1400,1800");
		MedList.addMedications("Ketamine - mock", 25,"g", "145", "0600,1000,1400,2400");

		Display.introScene();
	}
}
