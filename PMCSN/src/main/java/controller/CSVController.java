package main.java.controller;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVController {

    private static CSVController instance = null;

    private CSVWriter csvWriterNumJob;

    private String interarrivalTime = "interarrivalTime.csv";

    private CSVController() {
        try {
            this.csvWriterNumJob = new CSVWriter(new FileWriter("job_data.csv"));
            this.csvWriterNumJob.writeNext(new String[]{"Event", "Time", "TotalJobs"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CSVController getInstance() {
        if (instance == null) {
            instance = new CSVController();
        }
        return instance;
    }

    /*public void writeInterarrivalTime(double interarrivalTime) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(String.valueOf(interarrivalTime), true))) {
            String[] record = {String.valueOf(interarrivalTime)};
            writer.writeNext(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void writeNumJob(String event, double time, int totalJobs) {
        try {

            String[] record = {event, String.valueOf(time), String.valueOf(totalJobs)};
            this.csvWriterNumJob.writeNext(record);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeAll() {
        try {
            this.csvWriterNumJob.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
