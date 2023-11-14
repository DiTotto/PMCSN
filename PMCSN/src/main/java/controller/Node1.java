package main.java.controller;

import main.java.datastruct.*;

public class Node1 {

    ///NodeState state = new NodeState();
    //int num_job;

    //num job nel centro
    private int num_job;
    //num job che entrano nel centro
    private int num_job_in;
    //num job che escono dal centro
    private int num_job_left;
    //num job che escono dal centro per completamento
    private int num_job_out;
    //num job che entrano in feedback
    private int num_job_feedback;
    //numero di serventi
    private int server;

    public Node1(int server) {
        this.num_job = 0;
        this.num_job_in = 0;
        this.num_job_left = 0;
        this.num_job_out = 0;
        this.num_job_feedback = 0;

        this.server = server;

        for(int i = 0; i < server; i++) {

        }
    }

    /*for(int i=0; i<serverNumber;i++){
        this.served[i] = 0;
        this.idleServer[i] = true;
        this.sumService[i] = 0.0;
    }*/




    public void arrival() {
        /*Incremento numero di job (popolazione) al centro*/
        //num_job = state.getJobIN();
        //state.setJobIN(num_job+1);

        /*Genero prossimo istante di arrivo prossimo job*/


        /*Gestisco istante di arrivo attuale*/





    }
}
