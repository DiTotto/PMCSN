package main.java.controller;

import jdk.jfr.Event;
import main.java.datastruct.*;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import main.java.controller.EventHandler;
import main.java.utils.Acs;
import main.java.utils.Estimate;

public class Node4 {
    private double START = 0.0;
    private double STOP = 390.0;

    private int s;
    private double area = 0.0;


    //num job presenti nel centro
    private int num_job;

    //num job che hanno abbandonato il centro
    private int num_job_left;


    //numero di serventi
    private int server;

    //numero di job serviti
    private int jobServiti;

    //indica il nome del centro - sportello del comune
    private String name;

    private Time time = new Time();

    //private ArrayList<EventList> eventList = new ArrayList<EventList>();
    //private ArrayList<Sum> sumList = new ArrayList<Sum>();

    private Sum[] sumList;

    /* AGGIUNTA DEL SINGLETON*/
    private EventHandler handler;
    private RandomFunction random;
    /* --------------------- */
    private double exitProbability;

    private int id; //id del nodo
    private int num_internal_job = 0;
    private int num_external_job = 0;

    private int the_next;

    private String path;

    private boolean batch;



    /* -------------DA MODIFICARE DA INSERIRE TEMPI DI INTERARRIVO ETC--------------------- */
    //private RandomFunction random = new RandomFunction();


    public Node4(int num_job, String nome, int id, int the_next, String path, boolean batch) {

        EventList[] eventList1;

        this.path = path;

        this.batch = batch;

        this.id = id;

        this.the_next = the_next;

        /* ottengo l'istanza singleton*/
        this.handler = EventHandler.getInstance();
        this.random = RandomFunction.getInstance();
        /*---------*/

        this.num_job = num_job;
        this.jobServiti = 0;
        this.exitProbability = this.handler.getExitProbability(id);
        this.server = this.handler.getServer(id);

        this.sumList = new Sum[server + 3];

        // la entry 0 della event list indica l'arrivo di un job
        // la entry server+2 indica l'abbandono di un job
        eventList1 = new EventList[server + 3];



        this.num_job_left = 0;

        this.name = nome;

        //inizializzo la posizione 0 degli arraylist con il primo arrivo
        double firstArrival = this.random.getJobArrival(this.id);
        double firstInternalArrival = this.handler.getInternalArrivalNodo(id).remove(0);

        this.sumList[0] = new Sum();

        /* PARTE DEL SINGLETON */
        eventList1[0] = new EventList(firstArrival,1);
        eventList1[server + 2] = new EventList(firstInternalArrival,1);
        /*  ---------------   */



        //ciclo che istanzia i singoli componenti degli arraylist EventList e SumList
        //rappresentati i serventi del nodo e li pone a 0 e idle
        for(int i = 1; i <= (server + 1); i++) {
            eventList1[i] = new EventList(0,0);
        }
        for(int i = 1; i <= server; i++) {
            this.sumList[i] = new Sum();
        }
        /* SETTO LA NUOVA EVENTLIST MODIFICATA VERSO L'HANDLER*/
        this.handler.setEventNodo(id, eventList1);
        /* ------------------ */

    }



    public void normalWork() {

        int e;
        int job_batch = 0;
        double timeLimit = this.time.getCurrent();

        //while ((this.handler.getEventNodo(id)[0].getX() != 0) || (this.num_job > 0)) {
        while ((batch) ? (job_batch < 655) : ((this.handler.getEventNodo(id)[0].getX() != 0) || (this.num_job > 0))) {

            EventList[] eventList = this.handler.getEventNodo(id);

            e = EventList.NextEvent2(eventList, server);
            this.time.setNext(eventList[e].getT());
            this.area = this.area + (this.time.getNext() - this.time.getCurrent()) * this.num_job;
            this.time.setCurrent(this.time.getNext());
            if (e == 0) {
                if(batch) {
                    job_batch++;
                }
                this.num_external_job++;
                this.num_job++; //incremento il numero di job presenti nel centro
                eventList[0].setT(this.random.getJobArrival(this.id)); //aggiorno il tempo di arrivo del prossimo job
                if(!batch) {
                    if (eventList[0].getT() > this.STOP) { //se il tempo di arrivo del prossimo job è maggiore del tempo di stop
                        eventList[0].setX(0);
                        this.handler.setEventNodo(id, eventList);

                        //insert qui il passaggio di statistiche a handler
                    }
                }
                if (num_job <= server) { //se il numero di job è minore del numero di server fondamentalmente sto mettendo quel job in servizio da qualche parte
                    double service = 0.0;
                    if(batch) {
                        service = this.random.getServiceBatch(this.id); //tempo di servizio del centro s del prossimo job
                    } else {
                        service = this.random.getService(this.id); //tempo di servizio del centro s del prossimo job
                    }
                    //double service = this.random.getService(this.id); //tempo di servizio del centro s del prossimo job
                    this.s = whatIsIdle(eventList); //cerco un servente idle
                    sumList[s].incrementService(service); //aggiorno il tempo di servizio totale del centro s
                    sumList[s].incrementServed(); //aggiorno il numero di job serviti dal centro s
                    eventList[s].setT(this.time.getCurrent() + service); //aggiorno il tempo di completamento del centro s
                    eventList[s].setX(1);  //il centro s diventa busy
                    this.handler.setEventNodo(id, eventList);
                }//se non ci sono serventi liberi, aggiungo semplicemente quel job in coda. Questa verrà poi gestita dall'else sottostante
                else {
                    if(this.abbandono()){
                        //l'indice server dell'array degli eventi indica l'evento di abbandono
                        eventList[server + 1].setT(this.time.getCurrent()); //aggiorno il tempo del prossimo eevento di abbandono
                        eventList[server + 1].setX(1);  //il centro s diventa busy
                        this.handler.setEventNodo(id, eventList);
                    }

                }

            } else if(e == (server + 1)) {
                this.num_job_left++;
                this.num_job--;
                eventList[e].setX(0);
                this.handler.setEventNodo(id, eventList);

            } else if(e == (server + 2)) {
                //logica di gestione del job che arriva dal nodo 1
                if(batch) {
                    job_batch++;
                }
                this.num_job++;
                this.num_internal_job++;
                if (!this.handler.getInternalArrivalNodo(id).isEmpty()) {
                    eventList[e].setT(this.handler.getInternalArrivalNodo(id).remove(0));//prendo il prossimo arrivo dalla lista di arrivi dal nodo 1
                } else {
                    eventList[e].setX(0);
                }
                //eventList[e].setT(this.handler.getInternalArrivalNodo2().remove(0));
                if (num_job <= server) {
                    //double service = this.random.getService(this.id);
                    double service = 0.0;
                    if(batch) {
                        service = this.random.getServiceBatch(this.id); //tempo di servizio del centro s del prossimo job
                    } else {
                        service = this.random.getService(this.id); //tempo di servizio del centro s del prossimo job
                    }
                    this.s = whatIsIdle(eventList);
                    sumList[s].incrementService(service);
                    sumList[s].incrementServed();
                    eventList[s].setT(this.time.getCurrent() + service);
                    eventList[s].setX(1);
                    this.handler.setEventNodo(id, eventList);
                }
            }
            else{
                this.num_job--;  //decremento il numero di job presenti nel centro
                this.jobServiti++; //incremento il numero di job serviti
                this.s = e;    //indica il centro che ha completato il job

                double prob = this.random.extractProb();

                //implemento logica di routing - HO USATO LA MATRICE DI ROUTING
                if(prob <= this.handler.getRoutingProbability(id, this.the_next)) {
                    this.handler.addInternalArrivalNodo(this.the_next, this.time.getCurrent());
                }

                //fine logica di routing


                if (this.num_job >= this.server) { //se ci sono job in coda

                    if(this.num_job>this.server) {
                        if(this.abbandono()){
                            //l'indice server dell'array degli eventi indica l'evento di abbandono
                            eventList[server + 1].setT(this.time.getCurrent()); //aggiorno il tempo del prossimo eevento di abbandono
                            eventList[server + 1].setX(1);  //il centro s diventa busy
                            this.handler.setEventNodo(id, eventList);
                        }
                    }

                    //double service = this.random.getService(this.id);    //tempo di servizio del centro s del prossimo job
                    double service = 0.0;
                    if(batch) {
                        service = this.random.getServiceBatch(this.id); //tempo di servizio del centro s del prossimo job
                    } else {
                        service = this.random.getService(this.id); //tempo di servizio del centro s del prossimo job
                    }
                    //this.s = whatIsIdle(eventList);
                    sumList[s].incrementService(service);         //aggiorno il tempo di servizio totale del centro s
                    sumList[s].incrementServed();                 //aggiorno il numero di job serviti dal centro s
                    //this.eventList[s].setT(this.time.getCurrent() + service); //aggiorno il tempo di completamento del centro s
                    eventList[s].setT(this.time.getCurrent() + service);
                    this.handler.setEventNodo(id, eventList);


                }
                else {
                    eventList[e].setX(0);                  //se non ci sono job in coda, il centro s diventa idle
                    this.handler.setEventNodo(id, eventList);

                }

            }
        }

        if (batch) {
            printStatsBatch(timeLimit);
            //System.out.println("Area: " + this.area);

            this.area = 0.0;
            this.jobServiti = 0;
            this.num_job_left = 0;
            this.num_external_job = 0;

            for(int i = 1; i <= server; i++) {
                this.sumList[i].setService(0.0);
                this.sumList[i].setServed(0);
            }
        }
    }

    public void bathMeans(){
        //k = 64
        //ipotizzo b = 1028

        for(int i = 0; i < 100; i++) {
            //this.workforBatch();
            this.normalWork();
        }

        System.out.println("Calcolo delle autocorrelazioni...");
        System.out.println("Calcolo E[Tq]...");
        Acs.calculate(this.path + "outputWait.txt");
        System.out.println("Calcolo UTILIZZAZIONE...");
        Acs.calculate(this.path + "outputRoh.txt");
        System.out.println("Calcolo E[Nq]...");
        Acs.calculate(this.path + "outputPopolazioneInCoda.txt");
        System.out.println("Calcolo E[Ns]...");
        Acs.calculate(this.path + "outputPopolazioneNelCentro.txt");
        System.out.println("Calcolo E[Ts]...");
        Acs.calculate(this.path + "outputTempoMedioRisposta.txt");

        System.out.println("Calcolo delle stime...");
        System.out.println("Calcolo AVG WAIT...");
        Estimate.estimate(this.path + "outputWait.txt");
        System.out.println("Calcolo UTILIZZAZIONE...");
        Estimate.estimate(this.path + "outputRoh.txt");
        System.out.println("Calcolo E[Nq]...");
        Estimate.estimate(this.path + "outputPopolazioneInCoda.txt");
        System.out.println("Calcolo E[Ns]...");
        Estimate.estimate(this.path + "outputPopolazioneNelCentro.txt");
        System.out.println("Calcolo E[Ts]...");
        Estimate.estimate(this.path + "outputTempoMedioRisposta.txt");
    }




    private int whatIsIdle(EventList[] eventList) {
        int s;
        int i = 1;

        while(eventList[i].getX() == 1) {
            i++;
        }
        s = i;
        while(i < this.server) {
            i++;
            if((eventList[i].getX() == 0) && (eventList[i].getT() < eventList[s].getT())) {
                s = i;
            }
        }
        return s;

    }

    private String returnNameOfCenter() {
        return this.name;
    }

    private boolean abbandono() {
        double abbandono = this.random.extractProb();
        if(abbandono < this.exitProbability) {
            return true;
        } else {
            return false;
        }

    }




    public void printStats() {
        System.out.println("Hi, I'm " + this.returnNameOfCenter() + " and I'm done!\n\n");
        System.out.println("for " + this.jobServiti + " jobs the service node statistics are:\n\n");
        System.out.println("  avg interarrivals .. = " + this.handler.getEventNodo(id)[0].getT() / this.jobServiti);
        System.out.println("  avg wait ........... = " + this.area / this.jobServiti);
        System.out.println("  avg # in node ...... = " + this.area / this.time.getCurrent());
        System.out.println("  number of internal jobs = " + this.num_internal_job);
        System.out.println("  number of external jobs = " + this.num_external_job);


        for(int i = 1; i <= this.server; i++) {
            this.area -= this.sumList[i].getService();
        }
        System.out.println("  num job left ....... = " + this.num_job_left);
        System.out.println("  avg delay .......... = " + this.area / this.jobServiti);
        System.out.println("  avg # in queue ..... = " + this.area / this.time.getCurrent());
        System.out.println("\nthe server statistics are:\n\n");
        System.out.println("    server     utilization     avg service        share\n");
        for(int i = 1; i <= this.server; i++) {
            System.out.println(i + "\t" + this.sumList[i].getService() / this.time.getCurrent() + "\t" + this.sumList[i].getService() / this.sumList[i].getServed() + "\t" + (double)this.sumList[i].getServed() / (double)this.jobServiti);
            // System.out.println(i+"\t");
            // System.out.println("get service" + this.sumList[i].getService() + "\n");
            // System.out.println("getCurrent" + this.time.getCurrent() + "\n");
            // System.out.println("getserved"+this.sumList[i].getServed() + "\n");
            // System.out.println("jobServiti"+this.jobServiti + "\n");
            //System.out.println(i + "\t" + sumList[i].getService() / this.time.getCurrent() + "\t" + this.sumList[i].getService() / this.sumList[i].getServed() + "\t" + this.sumList[i].getServed() / this.jobServiti);
            System.out.println("\n");
            //System.out.println("jobServiti"+this.num_job_feedback + "\n");

        }
        System.out.println("\n");
    }

    public void printStatsBatch(double limitTime) {

        //E[Tq]
        String output = this.path + "outputWait.txt";
        File file = new File(output);
        //p
        String output2 = this.path + "outputRoh.txt";
        File file2 = new File(output2);
        //E[Nq]
        String output3 = this.path + "outputPopolazioneInCoda.txt";
        File file3 = new File(output3);
        //E[Ns]
        String output4 = this.path + "outputPopolazioneNelCentro.txt";
        File file4 = new File(output4);
        //E[Ts]
        String output5 = this.path + "outputTempoMedioRisposta.txt";
        File file5 = new File(output5);

        try{


            //p
            FileWriter fw2 = new FileWriter(file2, true);
            PrintWriter writer2 = new PrintWriter(fw2);
            writer2.println(this.sumList[1].getService() / (this.time.getCurrent() - limitTime));
            writer2.close();
            fw2.close();



            //E[Ns]
            FileWriter fw4 = new FileWriter(file4, true);
            PrintWriter writer4 = new PrintWriter(fw4);
            writer4.println(this.area / (this.time.getCurrent() - limitTime));
            writer4.close();
            fw4.close();

            //E[Ts]
            FileWriter fw5 = new FileWriter(file5, true);
            PrintWriter writer5 = new PrintWriter(fw5);
            writer5.println(this.area / this.jobServiti);
            writer5.close();
            fw5.close();

            for(int i = 1; i <= this.server; i++) {
                this.area -= this.sumList[i].getService();
            }


            //E[Tq]
            FileWriter fw = new FileWriter(file, true);
            PrintWriter writer = new PrintWriter(fw);
            double totalQueueTime = this.area / this.jobServiti;
            writer.println(this.area / this.jobServiti);
            writer.close();
            fw.close();

            //E[Nq]
            FileWriter fw3 = new FileWriter(file3, true);
            PrintWriter writer3 = new PrintWriter(fw3);
            writer3.println(this.area / (this.time.getCurrent() - limitTime));
            writer3.close();
            fw3.close();

            /*double totalServiceTime = 0;
            for(int i = 1; i <= this.server; i++) {
                totalServiceTime += ((this.sumList[i].getService()/this.sumList[i].getServed())*((double)this.sumList[i].getServed() / (double)this.jobServiti));
            }

            //E[Ts]
            FileWriter fw5 = new FileWriter(file5, true);
            PrintWriter writer5 = new PrintWriter(fw5);
            writer5.println(totalServiceTime + totalQueueTime);
            writer5.close();
            fw5.close();*/
        } catch (Exception e) {
            System.out.println("Errore nella creazione del file");
        }

        /*System.out.println("Hi, I'm " + this.returnNameOfCenter() + " and I'm done!\n\n");
        System.out.println("for " + this.jobServiti + " jobs the service node statistics are:\n\n");
        System.out.println("  avg interarrivals .. = " + (this.handler.getEventNodo(id)[0].getT() - limitTime) / this.jobServiti);
        System.out.println("  avg wait ........... = " + this.area / this.jobServiti);
        System.out.println("  avg # in node ...... = " + this.area / (this.time.getCurrent() - limitTime));
        System.out.println("  external num_job ..... = " + this.external_num_job);

        for(int i = 1; i <= this.server; i++) {
            this.area -= this.sumList[i].getService();
        }
        System.out.println("  num job left ....... = " + this.num_job_left);
        System.out.println("  avg delay .......... = " + this.area / this.jobServiti);
        System.out.println("  avg # in queue ..... = " + this.area / (this.time.getCurrent() - limitTime));
        System.out.println("\nthe server statistics are:\n\n");
        System.out.println("    server     utilization     avg service        share\n");
        for(int i = 1; i <= this.server; i++) {
            System.out.println(i + "\t" + this.sumList[i].getService() / (this.time.getCurrent() - limitTime) + "\t" + this.sumList[i].getService() / this.sumList[i].getServed() + "\t" + (double)this.sumList[i].getServed() / (double)this.jobServiti);

        }
        System.out.println("\n");*/
    }
}
