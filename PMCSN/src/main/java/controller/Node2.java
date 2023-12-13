package main.java.controller;

import main.java.datastruct.*;

public class Node2 {
    private double START = 0.0;
    private double STOP = 20000.0;

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
    
    private Sum[] sumList;

    /* AGGIUNTA DEL SINGLETON*/
    private EventHandler handler;
    private RandomFunction random;
    /* --------------------- */
    private double exitProbability;

    public Node2(int num_job, String nome){
        EventList[] eventList2;

        /* ottengo l'istanza singleton*/
        this.handler = EventHandler.getInstance();
        this.random = RandomFunction.getInstance();
        /*---------*/

        this.server = this.handler.getServer2();
        this.num_job = num_job;
        this.jobServiti = 0;
        this.exitProbability = 0.1;
        
        this.sumList = new Sum[server + 3];

        // la entry 0 della event list indica l'arrivo di un job
        // la entry server + 2 indica l'abbandono di un job
        // la entry server + 3 indica l'arrivo di un job dal nodo 1
        eventList2 = new EventList[server + 3];


        
        this.num_job_left = 0;

        this.name = nome;
        
        //inizializzo la posizione 0 degli arraylist con il primo arrivo 
        double firstArrival = this.random.getJobArrival(); 
        //TODO la random.getJobArrival() non riparte da 0 ma riparte dall'istante in cui aveva finito nodo 1
        
        double firstInternalArrival = this.handler.getInternalArrivalNodo2().remove(0);
        
        this.sumList[0] = new Sum();

        /* PARTE DEL SINGLETON */
        eventList2[0] = new EventList(firstArrival,1);
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
        this.handler.setEventNodo2(eventList2);
        /* ------------------ */
    }

    public void normalWork() {

        int e;

        while ((this.handler.getEventNodo2()[0].getX() != 0) || (this.num_job > 0)) {

            EventList[] eventList = this.handler.getEventNodo2();

            e = EventList.NextEvent2(eventList, server);
            this.time.setNext(eventList[e].getT());
            this.area = this.area + (this.time.getNext() - this.time.getCurrent()) * this.num_job;
            this.time.setCurrent(this.time.getNext());
            if (e == 0) {
                this.num_job++; //incremento il numero di job presenti nel centro
                eventList[0].setT(this.random.getJobArrival()); //aggiorno il tempo di arrivo del prossimo job
                if (eventList[0].getT() > this.STOP) { //se il tempo di arrivo del prossimo job è maggiore del tempo di stop
                    eventList[0].setX(0);
                    this.handler.setEventNodo2(eventList);

                    //insert qui il passaggio di statistiche a handler
                }
                if (num_job <= server) { //se il numero di job è minore del numero di server fondamentalmente sto mettendo quel job in servizio da qualche parte
                    double service = this.random.getService(); //tempo di servizio del centro s del prossimo job
                    this.s = whatIsIdle(eventList); //cerco un servente idle
                    sumList[s].incrementService(service); //aggiorno il tempo di servizio totale del centro s
                    sumList[s].incrementServed(); //aggiorno il numero di job serviti dal centro s
                    eventList[s].setT(this.time.getCurrent() + service); //aggiorno il tempo di completamento del centro s
                    eventList[s].setX(1);  //il centro s diventa busy
                    this.handler.setEventNodo2(eventList);
                }//se non ci sono serventi liberi, aggiungo semplicemente quel job in coda. Questa verrà poi gestita dall'else sottostante
                else {
                    if(this.abbandono()){
                        //l'indice server dell'array degli eventi indica l'evento di abbandono
                        eventList[server + 1].setT(this.time.getCurrent()); //aggiorno il tempo del prossimo eevento di abbandono
                        eventList[server + 1].setX(1);  //il centro s diventa busy
                        this.handler.setEventNodo2(eventList);
                    }
                    
                }

            } else if(e == (server + 1)) {
                this.num_job_left++;
                this.num_job--;
                eventList[e].setX(0);
                this.handler.setEventNodo2(eventList);
                
            } else if(e == (server + 2)) {
                //logica di gestione del job che arriva dal nodo 1
                this.num_job++;
                if(!this.handler.getInternalArrivalNodo2().isEmpty()) {
                    eventList[e].setT(this.handler.getInternalArrivalNodo2().remove(0));
                } else {
                    eventList[e].setX(0);
                }
                eventList[e].setT(this.handler.getInternalArrivalNodo2().remove(0));//prendo il prossimo arrivo dalla lista di arrivi dal nodo 1
                if(num_job <= server) {
                    double service = this.random.getService();
                    this.s = whatIsIdle(eventList);
                    sumList[s].incrementService(service);
                    sumList[s].incrementServed();
                    eventList[s].setT(this.time.getCurrent() + service);
                    eventList[s].setX(1);
                    this.handler.setEventNodo2(eventList);
                } else {
                    this.num_job--;  //decremento il numero di job presenti nel centro
                    this.jobServiti++; //incremento il numero di job serviti
                    this.s = e;    //indica il centro che ha completato il job
                    if (this.num_job >= this.server) { //se ci sono job in coda
                        
                        if(this.num_job>this.server) {
                            if(this.abbandono()){
                                //l'indice server dell'array degli eventi indica l'evento di abbandono
                                eventList[server + 1].setT(this.time.getCurrent()); //aggiorno il tempo del prossimo eevento di abbandono
                                eventList[server + 1].setX(1);  //il centro s diventa busy
                                this.handler.setEventNodo2(eventList);
                            }
                        }

                        double service = this.random.getService();    //tempo di servizio del centro s del prossimo job
                        //this.s = whatIsIdle(eventList);
                        sumList[s].incrementService(service);         //aggiorno il tempo di servizio totale del centro s
                        sumList[s].incrementServed();                 //aggiorno il numero di job serviti dal centro s
                        //this.eventList[s].setT(this.time.getCurrent() + service); //aggiorno il tempo di completamento del centro s
                        eventList[s].setT(this.time.getCurrent() + service);
                        this.handler.setEventNodo2(eventList);

                        
                    }
                    else {
                        eventList[e].setX(0);                  //se non ci sono job in coda, il centro s diventa idle
                        this.handler.setEventNodo2(eventList);
                        
                    }

                    //inserire la gestione di inserimento dell'uscita da questo centro. Registrare l'istante di uscita che verrà utilizzato  come tempo di ingresso per il
                    //centro successivo a questo. Verrà creata quindi alla fine dell'esecuzione di questo centro, una lista con tutti i tempi di uscita dei job. Successivamente all'esecuzione
                    //di questo centro, verrà eseguito il centro successivo che avrà come tempo di ingresso il tempo di uscita di questo centro e così via
                    //aggiunta di chiamata ad un metodo dell'handler (TODO) che aggiunge il tempo di uscita di questo centro ad una lista di tempi di ingresso
                    // di uno dei centri successivi seguendo una certa probabilità
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
        
        System.out.println("for " + this.jobServiti + " jobs the service node statistics are:\n\n");
        System.out.println("  avg interarrivals .. = " + this.handler.getEventNodo2()[0].getT() / this.jobServiti);
        System.out.println("  avg wait ........... = " + this.area / this.jobServiti);
        System.out.println("  avg # in node ...... = " + this.area / this.time.getCurrent());

        for(int i = 1; i <= this.server; i++) {
            this.area -= this.sumList[i].getService();
        }
        System.out.println("  num job left ....... = " + this.num_job_left);
        System.out.println("  avg delay .......... = " + this.area / this.jobServiti);
        System.out.println("  avg # in queue ..... = " + this.area / this.time.getCurrent());
        System.out.println("\nthe server statistics are:\n\n");
        System.out.println("    server     utilization     avg service        share\n");
        for(int i = 1; i <= this.server; i++) {
            System.out.println(i + "\t" + this.sumList[i].getService() / this.time.getCurrent() + "\t" + this.sumList[i].getService() / this.sumList[i].getServed() + "\t" + this.sumList[i].getServed() / this.jobServiti);
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
