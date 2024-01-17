package main.java.controller;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVController {

    private CSVWriter csvWriterNumJob;

    private String path = "outputCSV/";

    public CSVController(String relativePath) {
        try {
            this.csvWriterNumJob = new CSVWriter(new FileWriter((path + relativePath + "jobServiti.csv"), false));
            this.csvWriterNumJob.writeNext(new String[]{"Event", "Time", "TotalJobs"});
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            this.csvWriterNumJob.flush();

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
