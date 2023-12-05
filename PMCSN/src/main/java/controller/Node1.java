package main.java.controller;

import jdk.jfr.Event;
import main.java.datastruct.*;
import java.util.ArrayList;
import main.java.controller.EventHandler;

public class Node1 {

    private double START = 0.0;
    private double STOP = 20000.0;

    private int s;
    private double area = 0.0;

    private  double feedbackProbability = 0.5; //da cambiare, per test
    

    //num job presenti nel centro
    private int num_job;
    //num job che entrano nel centro
    private int num_job_in;
    //num job che abbandonano dal centro
    private int num_job_left;
    //num job che escono dal centro per completamento
    private int num_job_out;
    //num job che entrano in feedback
    private int num_job_feedback;
    //numero di serventi
    private int server;
    //numero di job serviti per ogni centro
    //utie?? indica il numero di job serviti dal centro i-esimo??
    private int[] served;
    private boolean[] idleServer;
    //indica i job in coda
    private int jobCoda;
    //indica i job in servizio in un centro qualasiasi
    private int jobServizio;
    //indica il num d job serviti in totale
    private int jobServiti = 0;

    //indica il nome del centro - sportello del comune
    private String name;

    private Time time = new Time();

    //private ArrayList<EventList> eventList = new ArrayList<EventList>();
    //private ArrayList<Sum> sumList = new ArrayList<Sum>();
    private EventList[] eventList;
    private Sum[] sumList;

    private boolean isFeedback = false;

    /* AGGIUNTA DEL SINGLETON*/
    private EventHandler handler;
    private EventList[] eventList1;
    /* --------------------- */



    /* -------------DA MODIFICARE DA INSERIRE TEMPI DI INTERARRIVO ETC--------------------- */
    private RandomFunction random = new RandomFunction();

    
    public Node1(int server, int num_job, String nome) {

        EventList[] eventList1;

        /* ottengo l'istanza singleton*/
        this.handler = EventHandler.getInstance(server);
        /*---------*/

        this.server = server;
        this.num_job = num_job;

        this.served = new int[server + 1];
        this.idleServer = new boolean[server + 1];
        this.eventList = new EventList[server + 1];
        this.sumList = new Sum[server + 1];

        eventList1 = new EventList[server + 1];


        this.num_job_feedback = 0;
        this.num_job_left = 0;
        this.num_job_out = 0;
        this.num_job_in = 0;

        this.name = nome;


        
        //inizializzo la posizione 0 degli arraylist con il primo arrivo 
        double firstArrival = this.random.getJobArrival();
        this.eventList[0] = new EventList(firstArrival,1);
        this.sumList[0] = new Sum();
        this.served[0] = 0;
        this.idleServer[0] = true;

        /* PARTE DEL SINGLETON */
        eventList1[0] = new EventList(firstArrival,1);
        //this.handler.setEventNodo1(this.eventList1);
        /*  ---------------   */

        

        //ciclo che istanzia i singoli componenti degli arraylist EventList e SumList
        //rappresentati i serventi del nodo e li pone a 0 e idle
        for(int i = 1; i <= server; i++) {
            //eventList.add(new EventList(0, 0));
            //sumList.add(new Sum());
            this.eventList[i] = new EventList(0,0);
            this.sumList[i] = new Sum();
            this.served[i] = 0;
            this.idleServer[i] = true;

            /* PARTE DEL SINGLETON*/
            eventList1[i] = new EventList(0,0);
            /* ------------------ */
        }
        /* PARTE DEL SINGLETON*/
        this.handler.setEventNodo1(eventList1);
        /* ------------------ */


        if(num_job > server) {
            //indica il fatto che se il numero di job entranti nel centro è maggiore del numero di server
            //non sono presenti server inattivi
            for(int i = 0; i < server; i++) {
                this.idleServer[i] = false;
            }
            // ?? non è necessario mettere i restanti job in coda ??
        } else if(num_job > 0) {
            //indica che se il numero di job entranti nel centro è minore del numero di server, allora verranno
            //occupati solo i server necessari
            for(int i = 0; i < num_job; i++) {
                this.idleServer[i] = false;
            }
        }

        //statsHandller???

    }

    public void startToWork() {
        //DA AGGIUNGERE DI CONTINUARE A LAVORARE SE CI SONO ANCORA JOB DELLA CODA FEEDBACK CHE DELLA CODA ARRIVI ESTERNI
        while ((this.handler.getEventNodo1()[0].getX() != 0) || (this.num_job > 0)) {
            if (!this.handler.isFeedbackNodo1() && !this.handler.isExternalArrivalNodo1()) {
                this.normalWork();
            } else if (this.handler.isFeedbackNodo1() && !this.handler.isExternalArrivalNodo1()) {
                this.checkWorkOnlyFeedback();
            } else if (!this.handler.isFeedbackNodo1() && this.handler.isExternalArrivalNodo1()) {
                this.checkWorkOnlyExternalArrival();
            } else {
                this.checkWorkAll();
            }
        }

    }

    private void checkWorkOnlyFeedback() {
        EventList[] event = this.handler.getEventNodo1();
        EventList feedback = this.handler.getFeedbackNodo1().get(0);

        if(event[0].getT() < feedback.getT()) {
            this.normalWork();
        } else {
            this.processFeedback(feedback);
        }

    }
    private void checkWorkOnlyExternalArrival() {
        /* DA IMPLEMENTARE LA LOGICA DEL SE ESEGUIRE ARRIVO ESTERNO, FEEDBACK O LAVORO NORMALE*/
    }

    private void checkWorkAll() {
        /* DA IMPLEMENTARE LA LOGICA DEL SE ESEGUIRE ARRIVO ESTERNO, FEEDBACK O LAVORO NORMALE*/
    }

    private void normalWork() {
        int e;

        EventList[] eventList = this.handler.getEventNodo1();

        e = EventList.NextEvent(eventList, server);
        this.time.setNext(eventList[e].getT());

        this.area = this.area + (this.time.getNext() - this.time.getCurrent()) * this.num_job;
        this.time.setCurrent(this.time.getNext());
        if (e == 0) {
            this.num_job++; //incremento il numero di job presenti nel centro
            eventList[0].setT(this.random.getJobArrival()); //aggiorno il tempo di arrivo del prossimo job
            if (eventList[0].getT() > this.STOP) { //se il tempo di arrivo del prossimo job è maggiore del tempo di stop
                eventList[0].setX(0);
                this.handler.setEventNodo1(eventList);
            }
            if (num_job <= server) { //se il numero di job è minore del numero di server
                double service = this.random.getService(); //tempo di servizio del centro s del prossimo job
                this.s = whatIsIdle(eventList); //cerco un servente idle
                sumList[s].incrementService(service); //aggiorno il tempo di servizio totale del centro s
                sumList[s].incrementServed(); //aggiorno il numero di job serviti dal centro s
                eventList[s].setT(this.time.getCurrent() + service); //aggiorno il tempo di completamento del centro s
                eventList[s].setX(1);  //il centro s diventa busy
                this.handler.setEventNodo1(eventList);
            }

        } else {
            this.num_job--;  //decremento il numero di job presenti nel centro
            this.jobServiti++; //incremento il numero di job serviti
            //this.served[e]++;
            this.s = e;    //indica il centro che ha completato il job
            if (this.num_job >= this.server) { //se ci sono job in coda
                double service = this.random.getService();    //tempo di servizio del centro s del prossimo job
                //this.s = whatIsIdle(eventList);
                sumList[s].incrementService(service);         //aggiorno il tempo di servizio totale del centro s
                sumList[s].incrementServed();                 //aggiorno il numero di job serviti dal centro s
                //this.eventList[s].setT(this.time.getCurrent() + service); //aggiorno il tempo di completamento del centro s
                eventList[s].setT(this.time.getCurrent() + service);
                this.handler.setEventNodo1(eventList);

                this.generateFeedback();
            }
            else {
                eventList[e].setX(0);                  //se non ci sono job in coda, il centro s diventa idle
                this.handler.setEventNodo1(eventList);
                this.generateFeedback();
            }
        }
    }

    private void processFeedback(EventList feedback) {

        EventList[] eventList = this.handler.getEventNodo1();

        //this.time.setNext(eventList[e].getT());
        this.area = this.area + (feedback.getT() - this.time.getCurrent()) * this.num_job;
        this.time.setCurrent(this.time.getNext());

        if (feedback.getT() > this.STOP) { //se il tempo di arrivo del prossimo job è maggiore del tempo di stop
            eventList[0].setX(0);
        }
        if (num_job <= server) { //se il numero di job è minore del numero di server
            double service = this.random.getService(); //tempo di servizio del centro s del prossimo job
            this.s = whatIsIdle(this.handler.getEventNodo1()); //cerco un servente idle
            sumList[s].incrementService(service); //aggiorno il tempo di servizio totale del centro s
            sumList[s].incrementServed(); //aggiorno il numero di job serviti dal centro s
            eventList[s].setT(this.time.getCurrent() + service); //aggiorno il tempo di completamento del centro s
            eventList[s].setX(1);  //il centro s diventa busy
            this.handler.setEventNodo1(eventList);
            this.handler.removeFeedbackNodo1();
        }

    }

    private void generateFeedback() {
        double feedbackRandom = this.random.extractProb();
        if(feedbackRandom <= this.feedbackProbability) {
            double delay = this.handler.generateDelay();
            EventList event = new EventList(this.time.getCurrent() + delay, 1);
            this.handler.getFeedbackNodo1().add(event);
            this.num_job++; //incremento il numero di job presenti nel centro
        }
    }


    public void work() {

        int e = 0;
        double tTemporary = -1;
        while((this.eventList[0].getX() != 0) || (this.num_job > 0)) {
            //System.out.println("debug");

            /* CREARE NUOVA ENTRY PER EVENTLIST IN CUI INSERIRE FEEDBACK - evento piu vicino temporalmente e da processare */
            
            if(!this.isFeedback){
                e = EventList.NextEvent(eventList, server);
                this.time.setNext(eventList[e].getT());
            } else {
                //this.isFeedback = false;
                e = 0;
                this.time.setNext(eventList[e].getT());
            }
            
            //gestire AREA
            this.area = this.area + (this.time.getNext() - this.time.getCurrent()) * this.num_job;
            this.time.setCurrent(this.time.getNext());
            if(e == 0 && !this.isFeedback ) {
                //System.out.println(eventList[0].getT());
                this.num_job++; //incremento il numero di job presenti nel centro
                this.eventList[0].setT(this.random.getJobArrival()); //aggiorno il tempo di arrivo del prossimo job
                if(eventList[0].getT() > this.STOP) { //se il tempo di arrivo del prossimo job è maggiore del tempo di stop
                    this.eventList[0].setX(0);
                }
                if(num_job <= server) { //se il numero di job è minore del numero di server
                    double service = this.random.getService(); //tempo di servizio del centro s del prossimo job
                    this.s = whatIsIdle(eventList); //cerco un servente idle
                    sumList[s].incrementService(service); //aggiorno il tempo di servizio totale del centro s
                    sumList[s].incrementServed(); //aggiorno il numero di job serviti dal centro s
                    this.eventList[s].setT(this.time.getCurrent() + service); //aggiorno il tempo di completamento del centro s
                    this.eventList[s].setX(1);  //il centro s diventa busy
                }
            } else if(e == 0 && this.isFeedback){
                this.isFeedback = false;
                this.num_job++; //incremento il numero di job presenti nel centro
                if(eventList[0].getT() > this.STOP) { //se il tempo di arrivo del prossimo job è maggiore del tempo di stop
                    this.eventList[0].setX(0);
                }
                if(num_job <= server) { //se il numero di job è minore del numero di server
                    double service = this.random.getService(); //tempo di servizio del centro s del prossimo job
                    this.s = whatIsIdle(eventList); //cerco un servente idle
                    sumList[s].incrementService(service); //aggiorno il tempo di servizio totale del centro s
                    sumList[s].incrementServed(); //aggiorno il numero di job serviti dal centro s
                    this.eventList[s].setT(this.time.getCurrent() + service); //aggiorno il tempo di completamento del centro s
                    this.eventList[s].setX(1);  //il centro s diventa busy
                }
                //ora devo rimettere come prossimo arrivo, quello che si aveva prima di avere il feedback
                this.eventList[0].setT(tTemporary);
            }
            else {

                this.num_job--;  //decremento il numero di job presenti nel centro
                this.jobServiti++; //incremento il numero di job serviti
                //this.served[e]++;
                this.s = e;    //indica il centro che ha completato il job
                if(this.num_job >= this.server) { //se ci sono job in coda
                    double service = this.random.getService();    //tempo di servizio del centro s del prossimo job
                    //this.s = whatIsIdle(eventList);
                    sumList[s].incrementService(service);         //aggiorno il tempo di servizio totale del centro s
                    sumList[s].incrementServed();                 //aggiorno il numero di job serviti dal centro s
                    //this.eventList[s].setT(this.time.getCurrent() + service); //aggiorno il tempo di completamento del centro s
                    /* ------------------------------------- */

                    double feedbackRandom = this.random.extractProb();
                    if(feedbackRandom <= this.feedbackProbability){
                        this.isFeedback = true;
                        this.num_job_feedback++;

                        
                        //int feedbackIndex = whatIsIdle(eventList);
                        // if(feedbackIndex > 0) {
                            //this.idleServer[feedbackIndex] = false;
                        //tempEvent = new EventList(this.time.getCurrent(), 1);
                        //tTemporary = this.eventList[0].getT() + service;
                        tTemporary = this.time.getCurrent() + service;
                        this.eventList[0].setT(this.time.getCurrent() + service); //settiamo un nuovo arrivo nel centro stesso
                        this.eventList[0].setX(1);
                        this.eventList[s].setT(this.time.getCurrent() + service);
                        //this.eventList[s].setX(0);
                        //} else {
                           // this.jobCoda++;
                        //}
                    } else {
                        // Il job continua il suo flusso normale
                        this.eventList[s].setT(this.time.getCurrent() + service);
                    }


                    /* ------------------------------------- */
                } else {
                    this.eventList[e].setX(0);                  //se non ci sono job in coda, il centro s diventa idle
                }
            }

        }
    }


    // public void arrival() {
    //     //incremento contatore dei job entranti
    //     this.num_job_in++;

    //     //se sono presenti job nel sistema
    //     if(this.num_job > 0){
    //         this.checkQueueService();
    //     }

    //     //integrali sono da gestire??

    //     int index = this.whatIsIdle(eventList);

    //     if(index > -1) {
    //         this.idleServer[index] = false;
    //     } else {
    //         this.jobCoda++;
    //     }

    //     this.num_job++;

    //     //time event


    // }

    // public void completition(int e) {
    //     //handler

    //     if(this.num_job > 0) {
    //         this.checkQueueService();

    //         //cerco un servente non libero
    //         //int index = this.whatIsNotIdle();
    //         int index = 0;

    //         this.jobServiti++;
    //         this.num_job--;

    //         if(this.num_job >= this.server) {
    //             double service = this.random.getService();
    //             this.sumList[e].incrementService(service);
    //             this.sumList[e].incrementServed();

    //         }


    //         //se è presente un servente non libero lo libero
    //         // ??ma ne libero uno a caso? non è più giusto liberare esattamente quello che ha completato ai fini statistici?  ??
    //         // if(index > -1) {
    //         //     this.idleServer[index] = true;
    //         // }

    //         //se ci sono job in coda devo servirli SE un servente è libero
    //         if(jobCoda > 0){
    //             index = this.whatIsIdle(eventList);
    //             if(index > -1) {
    //                 this.idleServer[index] = false;
    //             }
    //         }

    //         this.num_job--;
    //         this.num_job_out++;
    //         //event time
    //     }


    // }

    // public void abandon() {
    //     //handler

    //     if(this.num_job > 0) {
    //         this.checkQueueService();
    //     }

    //     this.num_job--;
    //     this.num_job_left++;

    //     /*qui bisogna capire se l'abbandono avviene in coda o in servizio. Nel caso un abbandono
    //     * avvenga in coda allota:
    //     * this.jobCoda--;
    //     * altrimenti
    //     * this.jobServizio--; ed in questo caso bisogna liberare il servente*/

    //     //event time
    // }




    /* --------- */

    private void checkQueueService() {
        //se il numero di job è minore del numero di server allora in coda non ho job
        if(this.num_job < this.server) {
            this.jobCoda = 0;
            //altrimenti ho job in coda
        } else {
            this.jobCoda = this.num_job - this.server;
        }

        if(this.num_job - this.jobCoda > 0) {
            //se entro nell'if, significa che ci sono job in servizio
            this.jobServizio = this.num_job - this.jobCoda;
        } else {
            this.jobServizio = 0;
        }
    }



    /*private int isThereServerIdle() {
        int counter=0;
        for(int i=0; i<server;i++){
            if(this.idleServer[i])
                counter += counter;
        }
        return counter;
    }*/

    private int whatIsNotIdle() {
        int index = -1;
        for(int i=1; i<=server;i++){
            if(!this.idleServer[i]) {
                index = i;
                break;
            }
        }

        return index;
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



    // private int whatIsIdle() {
    //     int index = -1;
    //     for(int i=0; i<server;i++){
    //         if(this.idleServer[i]) {
    //             index = i;
    //             break;
    //         }
    //     }

    //     return index;
    // }


    private String returnNameOfCenter() {
        return this.name;
    }




    public void printStats() {
        
        System.out.println("for " + this.jobServiti + " jobs the service node statistics are:\n\n");
        System.out.println("  avg interarrivals .. = " + this.eventList[0].getT() / this.jobServiti);
        System.out.println("  avg wait ........... = " + this.area / this.jobServiti);
        System.out.println("  avg # in node ...... = " + this.area / this.time.getCurrent());

        for(int i = 1; i <= this.server; i++) {
            this.area -= this.sumList[i].getService();
        }

        System.out.println("  avg delay .......... = " + this.area / this.jobServiti);
        System.out.println("  avg # in queue ..... = " + this.area / this.time.getCurrent());
        System.out.println("\nthe server statistics are:\n\n");
        System.out.println("    server     utilization     avg service        share\n");
        for(int i = 1; i <= this.server; i++) {
            //System.out.println(i + "\t" + this.sumList[i].getService() / this.time.getCurrent() + "\t" + this.sumList[i].getService() / this.sumList[i].getServed() + "\t" + this.sumList[i].getServed() / this.jobServiti);
            //System.out.println(i+"\t");
            /*System.out.println("get service" + this.sumList[i].getService() + "\n");
            System.out.println("getCurrent" + this.time.getCurrent() + "\n");
            System.out.println("getserved"+this.sumList[i].getServed() + "\n");
            System.out.println("jobServiti"+this.jobServiti + "\n");*/
            System.out.println(i + "\t" + sumList[i].getService() / this.time.getCurrent() + "\t" + this.sumList[i].getService() / this.sumList[i].getServed() + "\t" + this.sumList[i].getServed() / this.jobServiti);
            System.out.println("\n");
            //System.out.println("jobServiti"+this.num_job_feedback + "\n");
             
        }
        System.out.println("\n");
    }

}


