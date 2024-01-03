package main.java.org.example;

import main.java.controller.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("START");


        Node1 centralino = new Node1(0, "Centralino", 1);
        centralino.normalWork();
        centralino.printStats();

        Node2 node2 = new Node2(0, "CentroProva", 2);
        node2.normalWork();
        node2.printStats();
    }
}