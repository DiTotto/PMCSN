package main.java.org.example;

import main.java.controller.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("START");

        Node1 node1 = new Node1(0, "Centralino");

        node1.normalWork();
        node1.printStats();
        Node2 node2 = new Node2(0, "CentrodiDio");
        node2.normalWork();
        node2.printStats();
    }
}