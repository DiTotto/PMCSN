package main.java.org.example;

import main.java.controller.*;

public class Main {

    private final static CSVController csvController = CSVController.getInstance();
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

        Node1 centralino = new Node1(0, "Centralino", 0);
        centralino.bathMeans();
        //centralino.printStats();






    }
}