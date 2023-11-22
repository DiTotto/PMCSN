package main.java.controller;

import main.java.datastruct.*;
import java.util.ArrayList;

public class Node1 {

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

    //private ArrayList<EventList> eventList = new ArrayList<EventList>();
    //private ArrayList<Sum> sumList = new ArrayList<Sum>();
    private EventList[] eventList;
    private Sum[] sumList;



    /* -------------DA MODIFICARE DA INSERIRE TEMPI DI INTERARRIVO ETC--------------------- */
    private RandomFunction random = new RandomFunction();

    
    public Node1(int server, int num_job, String nome) {
        this.server = server;
        this.num_job = num_job;

        this.num_job_feedback = 0;
        this.num_job_left = 0;
        this.num_job_out = 0;
        this.num_job_in = 0;

        this.name = nome;

        //ciclo che istanzia i singoli componenti degli arraylist EventList e SumList
        for(int i = 0; i < server; i++) {
            //eventList.add(new EventList(0, 0));
            //sumList.add(new Sum());
            this.eventList[i] = new EventList(0,0);
            this.sumList[i] = new Sum();
        }

        for (int i = 0; i < server; i++) {
            this.served[i] = 0;
            this.idleServer[i] = true;
            /*this.sumService[i] = 0.0;*/
        }

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


    public void arrival() {
        //incremento contatore dei job entranti
        this.num_job_in++;

        //se sono presenti job nel sistema
        if(this.num_job > 0){
            this.checkQueueService();
        }

        //integrali sono da gestire??

        int index = this.whatIsIdle(eventList);

        if(index > -1) {
            this.idleServer[index] = false;
        } else {
            this.jobCoda++;
        }

        // e nel caso in cui non si sono serventi liberi dobbiamo gestire l'inserimento in coda? ??

        /*if(this.isThereServerIdle() > 0) {
            int index = this.whatIsIdle();
            this.idleServer[index] = false;
        }*/

        this.num_job++;

        //time event


    }

    public void completition(int e) {
        //handler

        if(this.num_job > 0) {
            this.checkQueueService();

            //cerco un servente non libero
            //int index = this.whatIsNotIdle();
            int index = 0;

            this.jobServiti++;
            this.num_job--;

            if(this.num_job >= this.server) {
                double service = this.random.getService();
                this.sumList[e].incrementService(service);
                this.sumList[e].incrementServed();

            }


            //se è presente un servente non libero lo libero
            // ??ma ne libero uno a caso? non è più giusto liberare esattamente quello che ha completato ai fini statistici?  ??
            // if(index > -1) {
            //     this.idleServer[index] = true;
            // }

            //se ci sono job in coda devo servirli SE un servente è libero
            if(jobCoda > 0){
                index = this.whatIsIdle(eventList);
                if(index > -1) {
                    this.idleServer[index] = false;
                }
            }

            this.num_job--;
            this.num_job_out++;
            //event time
        }


    }

    public void abandon() {
        //handler

        if(this.num_job > 0) {
            this.checkQueueService();
        }

        this.num_job--;
        this.num_job_left++;

        /*qui bisogna capire se l'abbandono avviene in coda o in servizio. Nel caso un abbandono
        * avvenga in coda allota:
        * this.jobCoda--;
        * altrimenti
        * this.jobServizio--; ed in questo caso bisogna liberare il servente*/

        //event time
    }




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
        for(int i=0; i<server;i++){
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
            if((eventList[i].getX() == 0) && (eventList[i].getT() < eventList[i].getT())) {
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

}


