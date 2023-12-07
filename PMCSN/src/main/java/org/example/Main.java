package main.java.org.example;

import main.java.controller.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("START");

        Node1 node1 = new Node1(4, 0, "test");

        node1.normalWork();

        node1.printStats();

    }
}