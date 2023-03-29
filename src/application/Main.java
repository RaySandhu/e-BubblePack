package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String CSV_FILE_PATH = "medications.csv";

    @Override
    public void start(Stage primaryStage) {
        try {
            // Read medications from CSV file
            BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                MedList.addMedications(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3], parts[4]);
            }
            br.close();

            // Add new medication
            MedList.addMedications("Ketamine - mock", 25,"g", "145", "0600,1000,1400,2400");

            // Write medications to CSV file
            BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH));
            for (Medication med : MedList.getMedications()) {
                bw.write(String.join(",", Arrays.asList(
                        med.getName(),
                        String.valueOf(med.getDose()),
                        med.getUnit(),
                        med.getBarcode(),
                        med.getSchedule()
                )));
                bw.newLine();
            }
            bw.close();

            // Load FXML file
            FXMLLoader loader = new FXMLLoader();
            VBox root = loader.load(getClass().getResourceAsStream("MainDisplayPane.fxml"));
            Scene scene = new Scene(root, 1200, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("E-BubblePack");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
