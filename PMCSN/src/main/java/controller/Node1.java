package main.java.controller;

import main.java.datastruct.*;

public class Node1 {

    NodeState state = new NodeState();

    int num_job;


    public void arrival() {
        /*Incremento numero di job (popolazione) al centro*/
        num_job = state.getJobIN();
        state.setJobIN(num_job+1);

        /*Genero prossimo istante di arrivo prossimo job*/


        /*Gestisco istante di arrivo attuale*/





    }
}
