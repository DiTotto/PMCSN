package main.java.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class Estimate {
    private static final double CONFIDENCE_LEVEL = 0.95; // Level of confidence

    public static void estimate(String nomeFile) {
        int n = 0;                  // counts data points
        double sum = 0.0;
        double mean = 0.0;
        double data;
        double stdev;
        double u, t, w;
        double diff;

        Rvms rvms = new Rvms();

        File file = new File(nomeFile);

        try {
            Scanner scanner = new Scanner(new FileInputStream(file));

            while (scanner.hasNext()) {  // use Welford's one-pass method
                String s = scanner.next();
                data = Double.parseDouble(s);
                //data = scanner.nextDouble(); // to calculate the sample mean
                n++;
                diff = data - mean;
                sum += diff * diff * (n - 1.0) / n;
                mean += diff / n;
            }
            stdev = Math.sqrt(sum / n);

            if (n > 1) {
                u = 1.0 - 0.5 * (1.0 - CONFIDENCE_LEVEL); // interval parameter
                t = rvms.idfStudent(n - 1, u); // critical value of t
                w = t * stdev / Math.sqrt(n - 1); // interval half width


                System.out.printf("\nbased upon %d data points", n);
                System.out.printf(" and with %d%% confidence\n", (int) (100.0 * CONFIDENCE_LEVEL + 0.5));
                System.out.println("the expected value is in the interval");
                System.out.printf("%10.2f +/- %6.2f\n", mean, w);
            } else {
                System.out.println("ERROR - insufficient data");
            }

        } catch (Exception e) {
            System.out.println("Errore nell'apertura del file");
        }


    }

    // Assume that idfStudent is a method to calculate the critical value of t
    // You need to implement or find this method
    private static double idfStudent(int degreesOfFreedom, double probability) {
        // Implement the calculation of the critical value of t here
        // This method's implementation depends on your requirements
        // and the specific statistics library you may use.
        // Return a dummy value for illustration purposes.
        return 0.0;
    }
}
