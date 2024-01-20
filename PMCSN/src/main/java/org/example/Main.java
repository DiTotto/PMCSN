package main.java.org.example;

import main.java.controller.*;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

//seed 12345

public class Main {

    public static void main(String[] args) {

        try {


            boolean batch = false;

            boolean finiteHorizon = true;

            if (!finiteHorizon) {
                File directory = new File("PMCSN/output/centralino/");
                FileUtils.cleanDirectory(directory);

                File directory2 = new File("PMCSN/output/anagrafe/");
                FileUtils.cleanDirectory(directory2);

                File directory3 = new File("PMCSN/output/scolastico/");
                FileUtils.cleanDirectory(directory3);

                File directory4 = new File("PMCSN/output/serviziSociali/");
                FileUtils.cleanDirectory(directory4);

                File directory5 = new File("PMCSN/output/URP/");
                FileUtils.cleanDirectory(directory5);

                File directory6 = new File("PMCSN/output/statoCivile/");
                FileUtils.cleanDirectory(directory6);

                File directory7 = new File("PMCSN/output/protocollo/");
                FileUtils.cleanDirectory(directory7);

                File directory8 = new File("PMCSN/output/cultura/");
                FileUtils.cleanDirectory(directory8);

                Node1 centralino = new Node1(0, "Centralino", 0, "PMCSN/output/centralino/", batch, "centralino/");
                centralino.bathMeans();

                System.out.println("------------------END CENTRALINO--------------------");

                Node4 anagrafe = new Node4(0, "Anagrafe", 1, 5, "PMCSN/output/anagrafe/", batch, "anagrafe/");
                anagrafe.bathMeans();

                System.out.println("------------------END ANAGRAFE--------------------");

                Node4 URP = new Node4(0, "URP", 2, 6, "PMCSN/output/URP/", batch, "URP/");
                URP.bathMeans();

                System.out.println("------------------END URP--------------------");

                Node2 scolastico = new Node2(0, "Scolastico", 3, "PMCSN/output/scolastico/", batch, "scolastico/");
                scolastico.bathMeans();

                System.out.println("------------------END SCOLASTICO--------------------");


                Node2 serviziSociali = new Node2(0, "Servizi Sociali", 4, "PMCSN/output/serviziSociali/", batch, "serviziSociali/");
                serviziSociali.bathMeans();

                System.out.println("------------------END SERVIZI SOCIALI--------------------");

                Node3 statoCivile = new Node3(0, "StatoCivile", 5, -1, false, "PMCSN/output/statoCivile/", batch, "statoCivile/");
                statoCivile.bathMeans();

                System.out.println("------------------END STATO CIVILE--------------------");

                Node3 protocollo = new Node3(0, "Protocollo", 6, 7, true, "PMCSN/output/protocollo/", batch, "protocollo/");
                protocollo.bathMeans();

                System.out.println("------------------END PROTOCOLLO--------------------");

                Node3 cultura = new Node3(0, "Cultura", 7, -1, false, "PMCSN/output/cultura/", batch, "cultura/");
                cultura.bathMeans();

                System.out.println("------------------END CULTURA--------------------");
            } else{
                File directory = new File("PMCSN/output/centralino/transient/");
                FileUtils.cleanDirectory(directory);

                File directory2 = new File("PMCSN/output/anagrafe/transient/");
                FileUtils.cleanDirectory(directory2);

                File directory3 = new File("PMCSN/output/scolastico/transient/");
                FileUtils.cleanDirectory(directory3);

                File directory4 = new File("PMCSN/output/serviziSociali/transient/");
                FileUtils.cleanDirectory(directory4);

                File directory5 = new File("PMCSN/output/URP/transient/");
                FileUtils.cleanDirectory(directory5);

                File directory6 = new File("PMCSN/output/statoCivile/transient/");
                FileUtils.cleanDirectory(directory6);

                File directory7 = new File("PMCSN/output/protocollo/transient/");
                FileUtils.cleanDirectory(directory7);

                File directory8 = new File("PMCSN/output/cultura/transient/");
                FileUtils.cleanDirectory(directory8);

                Node1 centralino = new Node1(0, "Centralino", 0, "PMCSN/output/centralino/", batch, "centralino/");
                Node4 anagrafe = new Node4(0, "Anagrafe", 1, 5, "PMCSN/output/anagrafe/", batch, "anagrafe/");
                Node4 URP = new Node4(0, "URP", 2, 6, "PMCSN/output/URP/", batch, "URP/");
                Node2 scolastico = new Node2(0, "Scolastico", 3, "PMCSN/output/scolastico/", batch, "scolastico/");
                Node2 serviziSociali = new Node2(0, "Servizi Sociali", 4, "PMCSN/output/serviziSociali/", batch, "serviziSociali/");
                Node3 statoCivile = new Node3(0, "StatoCivile", 5, -1, false, "PMCSN/output/statoCivile/", batch, "statoCivile/");
                Node3 protocollo = new Node3(0, "Protocollo", 6, 7, true, "PMCSN/output/protocollo/", batch, "protocollo/");
                Node3 cultura = new Node3(0, "Cultura", 7, -1, false, "PMCSN/output/cultura/", batch, "cultura/");

                for (int i = 0; i < 256; i++) {
                    centralino.finiteHorizon();
                    centralino.printFinitHorizonStats();
                    anagrafe.finiteHorizon();
                    anagrafe.printFinitHorizonStats();
                    URP.finiteHorizon();
                    URP.printFinitHorizonStats();
                    scolastico.finiteHorizon();
                    scolastico.printFinitHorizonStats();
                    serviziSociali.finiteHorizon();
                    serviziSociali.printFinitHorizonStats();
                    statoCivile.finiteHorizon();
                    statoCivile.printFinitHorizonStats();
                    protocollo.finiteHorizon();
                    protocollo.printFinitHorizonStats();
                    cultura.finiteHorizon();
                    cultura.printFinitHorizonStats();
                }
                centralino.finiteHorizonCalculate();
                System.out.println("------------------END CENTRALINO--------------------");
                anagrafe.finiteHorizonCalculate();
                System.out.println("------------------END ANAGRAFE--------------------");
                URP.finiteHorizonCalculate();
                System.out.println("------------------END URP--------------------");
                scolastico.finiteHorizonCalculate();
                System.out.println("------------------END SCOLASTICO--------------------");
                serviziSociali.finiteHorizonCalculate();
                System.out.println("------------------END SERVIZI SOCIALI--------------------");
                statoCivile.finiteHorizonCalculate();
                System.out.println("------------------END STATO CIVILE--------------------");
                protocollo.finiteHorizonCalculate();
                System.out.println("------------------END PROTOCOLLO--------------------");
                cultura.finiteHorizonCalculate();
                System.out.println("------------------END CULTURA--------------------");

            }


            //centralino.printStats();
        } catch (IOException e) {
            e.printStackTrace();
        }








    }
}