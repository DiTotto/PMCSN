package main.java.controller;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVController {

    private CSVWriter csvWriterNumJob;
    private CSVWriter csvWriterRho;

    private CSVWriter csvWriterAttesa;

    private String path = "outputCSV/";

    public CSVController(String relativePath) {
        try {
            this.csvWriterNumJob = new CSVWriter(new FileWriter((path + relativePath + "jobServiti.csv"), false));
            this.csvWriterNumJob.writeNext(new String[]{"Event", "Time", "TotalJobs"});

            this.csvWriterRho = new CSVWriter(new FileWriter((path + relativePath + "rho.csv"), false));
            this.csvWriterRho.writeNext(new String[]{"Time", "Rho"});

            this.csvWriterAttesa = new CSVWriter(new FileWriter((path + relativePath + "attesa.csv"), false));
            this.csvWriterAttesa.writeNext(new String[]{"Time", "Attesa"});
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

    public void writeRho(double time, double rho) {
        try {

            String[] record = {String.valueOf(time), String.valueOf(rho)};
            this.csvWriterRho.writeNext(record);
            this.csvWriterRho.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeAttesa(double time, double attesa) {
        try {

            String[] record = {String.valueOf(time), String.valueOf(attesa)};
            this.csvWriterAttesa.writeNext(record);
            this.csvWriterAttesa.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeAll() {
        try {
            this.csvWriterNumJob.close();
            this.csvWriterRho.close();
            this.csvWriterAttesa.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
