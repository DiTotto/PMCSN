package main.java.org.example;

import main.java.controller.*;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

//seed 12345

public class Main {

    public static void main(String[] args) {

        /*System.out.println("START");

        //CENTRALINO
        Node1 centralino = new Node1(0, "Centralino", 0);
        centralino.normalWork();
        centralino.printStats();

        System.out.println("------------------END CENTRALINO--------------------");

        //ANAGRAFE
        Node4 anagrafe = new Node4(0, "Anagrafe", 1, 5);
        anagrafe.normalWork();
        anagrafe.printStats();

        System.out.println("------------------END ANAGRAFE--------------------");

        Node3 statoCivile = new Node3(0, "StatoCivile", 5, -1 , false);
        statoCivile.normalWork();
        statoCivile.printStats();

        System.out.println("------------------END STATO CIVILE--------------------");

        //URP
        Node4 urp = new Node4(0, "URP", 2, 6);
        urp.normalWork();
        urp.printStats();

        System.out.println("------------------END URP--------------------");

        //PROTOCOLLO
        Node3 protocollo = new Node3(0, "Protocollo", 6, 7, true);
        protocollo.normalWork();
        protocollo.printStats();

        System.out.println("------------------END PROTOCOLLO--------------------");

        //CULTURA
        Node3 cultura = new Node3(0, "Cultura", 7, -1, false);
        cultura.normalWork();
        cultura.printStats();

        System.out.println("------------------END CULTURA--------------------");

        //SCOLASTICO
        Node2 scolastico = new Node2(0, "Scolastico", 3);
        scolastico.normalWork();
        scolastico.printStats();

        System.out.println("------------------END SCOLASTICO--------------------");

        //SERVIZI SOCIALI
        Node2 serviziSociali = new Node2(0, "ServiziSociali", 4);
        serviziSociali.normalWork();
        serviziSociali.printStats();

        System.out.println("------------------END SERVIZI SOCIALI--------------------");

        csvController.closeAll();

        System.out.println("END");*/

        try {
            File directory = new File("output/centralino/");
            FileUtils.cleanDirectory(directory);

            File directory2 = new File("output/anagrafe/");
            FileUtils.cleanDirectory(directory2);

            File directory3 = new File("output/scolastico/");
            FileUtils.cleanDirectory(directory3);

            File directory4 = new File("output/serviziSociali/");
            FileUtils.cleanDirectory(directory4);

            File directory5 = new File("output/URP/");
            FileUtils.cleanDirectory(directory5);

            File directory6 = new File("output/statoCivile/");
            FileUtils.cleanDirectory(directory6);

            File directory7 = new File("output/protocollo/");
            FileUtils.cleanDirectory(directory7);

            File directory8 = new File("output/cultura/");
            FileUtils.cleanDirectory(directory8);

            boolean batch = false;

            Node1 centralino = new Node1(0, "Centralino", 0, "output/centralino/", batch, "centralino/");
            centralino.bathMeans();

            System.out.println("------------------END CENTRALINO--------------------");

            Node4 anagrafe = new Node4(0, "Anagrafe", 1, 5, "output/anagrafe/", batch);
            anagrafe.bathMeans();

            System.out.println("------------------END ANAGRAFE--------------------");

            Node4 URP = new Node4(0, "URP", 2, 6, "output/URP/", batch);
            URP.bathMeans();

            System.out.println("------------------END URP--------------------");

            Node2 scolastico = new Node2(0, "Scolastico", 3, "output/scolastico/", batch);
            scolastico.bathMeans();

            System.out.println("------------------END SCOLASTICO--------------------");


            Node2 serviziSociali = new Node2(0, "Servizi Sociali", 4, "output/serviziSociali/", batch);
            serviziSociali.bathMeans();

            System.out.println("------------------END SERVIZI SOCIALI--------------------");

            Node3 statoCivile = new Node3(0, "StatoCivile", 5, -1 , false, "output/statoCivile/", batch);
            statoCivile.bathMeans();

            System.out.println("------------------END STATO CIVILE--------------------");

            Node3 protocollo = new Node3(0, "Protocollo", 6, 7, true, "output/protocollo/", batch);
            protocollo.bathMeans();

            System.out.println("------------------END PROTOCOLLO--------------------");

            Node3 cultura = new Node3(0, "Cultura", 7, -1, false, "output/cultura/", batch);
            cultura.bathMeans();

            System.out.println("------------------END CULTURA--------------------");



            //centralino.printStats();
        } catch (IOException e) {
            e.printStackTrace();
        }








    }
}