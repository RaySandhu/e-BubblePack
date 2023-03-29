package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

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
                Medication medication = new Medication(Integer.parseInt(parts[0]), parts[1], Float.parseFloat(parts[2]),
                		parts[3], parts[4].split(""), parts[5].split(","));
                MedList.addMedications(medication);
            }
            br.close();
            
            // Write medications to CSV file
            BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH));
            for (Medication medication : MedList.getMedications()) {
                StringBuilder sb = new StringBuilder();
                sb.append(medication.getId()).append(",")
                  .append(medication.getTradeName()).append(",")
                  .append(medication.getDosage()).append(",")
                  .append(medication.getDosageUnit()).append(",")
                  .append(String.join(";", medication.getSchedule()[0])).append(",")
                  .append(String.join(";", medication.getSchedule()[1]));
                bw.write(sb.toString());
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
