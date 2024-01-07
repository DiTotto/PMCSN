package main.java.controller;

import main.java.datastruct.EventList;
import main.java.datastruct.Sum;
import main.java.datastruct.Time;

public class Node3 {

    //questa dovrebbe essere la classe che non prende nessun ingresso dall'esterno ma
    //solo da un centro interno

    //bisogna aggiungere la logica del set della sua arraylist degli arrvi interni
    //altrimenti sta cosa non fa nulla. La logica sta su Node1. La logica però va aggiunta
    //nel nodo precedente a Node3

    private double START = 0.0;
    private double STOP = 480.0;

    private int s;
    private double area = 0.0;


    //num job presenti nel centro
    private int num_job;

    //num job che hanno abbandonato il centro
    private int num_job_left;

    //num job che arrivano dall'esterno
    private int num_external_job;

    //num job che arrivano dal nodo 1
    private int num_internal_job;

    //numero di serventi
    private int server;

    //numero di job serviti
    private int jobServiti;

    //indica il nome del centro - sportello del comune
    private String name;

    private Time time = new Time();

    private Sum[] sumList;

    /* AGGIUNTA DEL SINGLETON*/
    private EventHandler handler;
    private RandomFunction random;
    /* --------------------- */
    private double exitProbability;

    private int id;

    public Node3(int num_job, String nome, int id){
        EventList[] eventList2;

        this.id = id;

        /* ottengo l'istanza singleton*/
        this.handler = EventHandler.getInstance();
        this.random = RandomFunction.getInstance();
        /*---------*/

        this.server = this.handler.getServer(id);
        this.num_job = num_job;
        this.jobServiti = 0;
        this.exitProbability = 0.08;
        //this.num_external_job = 0;
        this.num_internal_job = 0;

        this.sumList = new Sum[server + 3];

        // la entry 0 della event list indica l'arrivo di un job
        // la entry server + 2 indica l'abbandono di un job
        // la entry server + 3 indica l'arrivo di un job dal nodo 1
        eventList2 = new EventList[server + 3];



        this.num_job_left = 0;

        this.name = nome;

        //inizializzo la posizione 0 degli arraylist con il primo arrivo
        //double firstArrival = this.random.getJobArrival2();

        //in questo modo pero non stiamo parametrizzando la classe, cosi funzionerà solo per lo specifico nodo che deve vedere getInternalArrivalNodo2.
        double firstInternalArrival = this.handler.getInternalArrivalNodo(id).remove(0);

        this.sumList[0] = new Sum();

        /* PARTE DEL SINGLETON */
        //eventList2[0] = new EventList(firstArrival,1);
        eventList2[0] = new EventList(0,0);
        eventList2[server + 2] = new EventList(firstInternalArrival,1);
        /*  ---------------   */



        //ciclo che istanzia i singoli componenti degli arraylist EventList e SumList
        //rappresentati i serventi del nodo e li pone a 0 e idle
        for(int i = 1; i <= (server + 1); i++) {
            eventList2[i] = new EventList(0,0);
        }
        for(int i = 1; i <= server; i++) {
            this.sumList[i] = new Sum();
        }
        /* SETTO LA NUOVA EVENTLIST MODIFICATA VERSO L'HANDLER*/
        this.handler.setEventNodo(id, eventList2);
        /* ------------------ */
    }

    public void normalWork() {

        int e;

        while ((this.handler.getEventNodo(id)[server + 2].getX() != 0) || (this.num_job > 0)) {
            EventList[] eventList = this.handler.getEventNodo(id);

            e = EventList.NextEvent2(eventList, server);
            this.time.setNext(eventList[e].getT());
            this.area = this.area + (this.time.getNext() - this.time.getCurrent()) * this.num_job;
            this.time.setCurrent(this.time.getNext());
            /*if (e == 0) {
                this.num_external_job++;
                this.num_job++; //incremento il numero di job presenti nel centro
                eventList[0].setT(this.random.getJobArrival2()); //aggiorno il tempo di arrivo del prossimo job
                if (eventList[0].getT() > this.STOP) { //se il tempo di arrivo del prossimo job è maggiore del tempo di stop
                    eventList[0].setX(0);
                    this.handler.setEventNodo(id, eventList);

                    //insert qui il passaggio di statistiche a handler
                }
                if (num_job <= server) { //se il numero di job è minore del numero di server fondamentalmente sto mettendo quel job in servizio da qualche parte
                    double service = this.random.getService2(); //tempo di servizio del centro s del prossimo job
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

            } else */
            if(e == (server + 1)) {
                this.num_job_left++;
                this.num_job--;
                eventList[e].setX(0);
                this.handler.setEventNodo(id, eventList);

            } else if(e == (server + 2)) {
                //logica di gestione del job che arriva dal nodo 1
                this.num_job++;
                this.num_internal_job++;
                if(!this.handler.getInternalArrivalNodo(id).isEmpty()) {
                    eventList[e].setT(this.handler.getInternalArrivalNodo(id).remove(0));//prendo il prossimo arrivo dalla lista di arrivi dal nodo 1
                } else {
                    eventList[e].setX(0);
                }
                //eventList[e].setT(this.handler.getInternalArrivalNodo2().remove(0));
                if(num_job <= server) {
                    double service = this.random.getService2();
                    this.s = whatIsIdle(eventList);
                    sumList[s].incrementService(service);
                    sumList[s].incrementServed();
                    eventList[s].setT(this.time.getCurrent() + service);
                    eventList[s].setX(1);
                    this.handler.setEventNodo(id, eventList);
                }
            } else {
                this.num_job--;  //decremento il numero di job presenti nel centro
                this.jobServiti++; //incremento il numero di job serviti
                this.s = e;    //indica il centro che ha completato il job

                /*if(this.random.extractProb() < 0.5) {
                    //this.handler.addInternalArrivalNodo(2, this.time.getCurrent());
                    this.handler.addInternalArrivalNodo(3, this.time.getCurrent());
                }*/

                if (this.num_job >= this.server) { //se ci sono job in coda

                    if(this.num_job>this.server) {
                        if(this.abbandono()){
                            //l'indice server + 1 dell'array degli eventi indica l'evento di abbandono
                            eventList[server + 1].setT(this.time.getCurrent()); //aggiorno il tempo del prossimo eevento di abbandono
                            eventList[server + 1].setX(1);  //il centro s diventa busy
                            this.handler.setEventNodo(id, eventList);
                        }
                    }

                    double service = this.random.getService2();    //tempo di servizio del centro s del prossimo job
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
        System.out.println("  avg interarrivals .. = " + this.handler.getEventNodo(id)[server+2].getT() / this.jobServiti);
        System.out.println("  avg wait ........... = " + this.area / this.jobServiti);
        System.out.println("  avg # in node ...... = " + this.area / this.time.getCurrent());
        System.out.println("  number of internal jobs = " + this.num_internal_job);
        System.out.println("  number of external jobs = " + this.num_external_job);

        System.out.println("  AREA: " + this.area );

        System.out.println("NODO 4: " + this.sumList[4].getServed() + " " + this.sumList[4].getService());

        for(int i = 1; i <= this.server; i++) {
            this.area -= this.sumList[i].getService();
            System.out.println("  avg service time " + i + " ... = " + this.sumList[i].getService());
            System.out.println("  AREA: " + i + " " + this.area );
        }
        System.out.println("  area e time " + this.area + " " + this.time.getCurrent() + "\n");
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
}
