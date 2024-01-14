package main.java.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Acs {

    public static void calculate(String nomeFile) {
        final int K = 50; // K is the maximum lag
        final int SIZE = K + 1;

        long i = 0; // data point index
        double x; // current x[i] data point
        double sum = 0.0; // sums x[i]
        long n; // number of data points
        long j; // lag index
        double[] hold = new double[SIZE]; // K + 1 most recent data points
        long p = 0; // points to the head of 'hold'
        double[] cosum = new double[SIZE]; // cosum[j] sums x[i] * x[i+j]
        double mean;

        File file = new File(nomeFile);

        try {
            Scanner scanner = new Scanner(new FileInputStream(file));

            while (i < SIZE) { // initialize the hold array with
                //x = scanner.nextDouble(); // the first K + 1 data values
                String s = scanner.next();
                x = Double.parseDouble(s);
                sum += x;
                hold[(int) i] = x;
                i++;
            }

            while (scanner.hasNext()) {
                for (j = 0; j < SIZE; j++)
                    cosum[(int) j] += hold[(int) p] * hold[(int) ((p + j) % SIZE)];
                String s = scanner.next();
                x = Double.parseDouble(s);
                sum += x;
                hold[(int) p] = x;
                p = (p + 1) % SIZE;
                i++;
            }
            n = i;

            while (i < n + SIZE) { // empty the circular array
                for (j = 0; j < SIZE; j++)
                    cosum[(int) j] += hold[(int) p] * hold[(int) ((p + j) % SIZE)];
                hold[(int) p] = 0.0;
                p = (p + 1) % SIZE;
                i++;
            }

            mean = sum / n;
            for (j = 0; j <= K; j++)
                cosum[(int) j] = (cosum[(int) j] / (n - j)) - (mean * mean);

            System.out.println("for " + n + " data points");
            System.out.printf("the mean is ... %8.2f\n", mean);
            System.out.printf("the stdev is .. %8.2f\n\n", Math.sqrt(cosum[0]));
            System.out.println("  j (lag)   r[j] (autocorrelation)");
            for (j = 1; j < SIZE; j++)
                System.out.printf("%3d  %11.3f\n", j, cosum[(int) j] / cosum[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }



}
