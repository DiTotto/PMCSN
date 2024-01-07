package main.java.controller;

import main.java.datastruct.EventList;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.w3c.dom.events.Event;


/* LA CLASSE DEVE ESSERE SINGLETON*/

/* Node1 prende ingressi dall'esterno e può sia uscire fuori che andare al Node2
   Node2 prende ingressi da Node1 e dall'esterno e fa uscire solo fuori
   Node 3 prende ingressi solo dall'interno e può sia uscire fuori che andare al Node4
*/
public class EventHandler {

    private static EventHandler instance = null;
    private EventList[] eventNodo1;
    private ArrayList<Double> internalArrivalNodo1;
    private static int server1 = 1;

    private EventList[] eventNodo2;
    private ArrayList<Double> internalArrivalNodo2;
    private static int server2 = 4;

    private EventList[] eventNodo3;
    private ArrayList<Double> internalArrivalNodo3;
    private static int server3 = 4;

    private EventList[] eventNodo4;
    private ArrayList<Double> internalArrivalNodo4;
    private static int server4 = 4;

    private static int numCenters = 8;

    private double[][] routingMatrix = new double[numCenters][numCenters];

    private RandomFunction random;


    private EventHandler(int server1, int server2, int server3, int server4) {
        this.eventNodo1 = new EventList[server1 + 2];
        this.eventNodo2 = new EventList[server2 + 3];
        this.eventNodo3 = new EventList[server3 + 3];
        this.eventNodo4 = new EventList[server4 + 3];

        this.internalArrivalNodo1 = new ArrayList<Double>();
        this.internalArrivalNodo2 = new ArrayList<Double>();
        this.internalArrivalNodo3 = new ArrayList<Double>();
        this.internalArrivalNodo4 = new ArrayList<Double>();
        this.random = RandomFunction.getInstance();

        initializeRoutingMatrix();
        //printMatrix();

        
    }

    public static EventHandler getInstance() {
        if (instance == null) {
            instance = new EventHandler(server1, server2, server3, server4);
        }
        return instance;
    }

    /*public int getServer1(){
        return server1;
    }

    public EventList[] getEventNodo1() {
        return eventNodo1;
    }
    public void setEventNodo1(EventList[] eventNodo1) {
        this.eventNodo1 = eventNodo1;
    }
    public void setFirstArrival(EventList event) {
        this.eventNodo1[0] = event;
    }
    
    public ArrayList<Double> getInternalArrivalNodo1() {
        return internalArrivalNodo1;
    }
    public void setInternalArrivalNodo1(ArrayList<Double> internalArrivalNodo1) {
        this.internalArrivalNodo1 = internalArrivalNodo1;
    }

    public int getServer2(){
        return server2;
    }

    public EventList[] getEventNodo2() {
        return eventNodo2;
    }
    public void setEventNodo2(EventList[] eventNodo2) {
        this.eventNodo2 = eventNodo2;
    }
    public void setFirstArrival2(EventList event) {
        this.eventNodo2[0] = event;
    }
    
    public ArrayList<Double> getInternalArrivalNodo2() {
        return this.internalArrivalNodo2;
    }
    public void addInternalArrivalNodo2(Double element) {
        this.internalArrivalNodo2.add(element);
    }
    
    public int getServer3(){
        return server3;
    }

    public EventList[] getEventNodo3() {
        return eventNodo3;
    }
    public void setEventNodo3(EventList[] eventNodo3) {
        this.eventNodo3 = eventNodo3;
    }
    public void setFirstArrival3(EventList event) {
        this.eventNodo3[0] = event;
    }
    
    public ArrayList<Double> getInternalArrivalNodo3() {
        return this.internalArrivalNodo3;
    }
    public void addInternalArrivalNodo3(Double element) {
        this.internalArrivalNodo3.add(element);
    }

    public int getServer4(){
        return server4;
    }

    public EventList[] getEventNodo4() {
        return eventNodo4;
    }
    public void setEventNodo4(EventList[] eventNodo4) {
        this.eventNodo4 = eventNodo4;
    }
    public void setFirstArrival4(EventList event) {
        this.eventNodo4[0] = event;
    }

    public ArrayList<Double> getInternalArrivalNodo4() {
        return this.internalArrivalNodo4;
    }
    public void addInternalArrivalNodo4(Double element) {
        this.internalArrivalNodo4.add(element);
    }*/
    
    public EventList[] getEventNodo(int number) {
        switch(number) {
            case 1: 
                return eventNodo1;
            case 2:
                return eventNodo2;
            case 3:
                return eventNodo3;
            case 4:
                return eventNodo4;
            default:
                return eventNodo1;
        }
    }

    public void setEventNodo(int number, EventList[] eventLists){
        switch(number) {
            case 1:
                this.eventNodo1 = eventLists;
                break;
            case 2:
                this.eventNodo2 = eventLists;
                break;
            case 3:
                this.eventNodo3 = eventLists;
                break;
            case 4:
                this.eventNodo4 = eventLists;
                break;
            default:
                break;
                
        }
    }

    public void setFirstArrival(int number, EventList event) {
        switch(number) {
            case 1:
                this.eventNodo1[0] = event;
                break;
            case 2:
                this.eventNodo2[0] = event;
                break;
            case 3:
                this.eventNodo3[0] = event;
                break;
            case 4:
                this.eventNodo4[0] = event;
                break;
            default:
                break;
        }
    }

    public ArrayList<Double> getInternalArrivalNodo(int number) {
        switch(number) {
            case 1:
                return this.internalArrivalNodo1;
            case 2:
                return this.internalArrivalNodo2;
            case 3:
                return this.internalArrivalNodo3;
            case 4:
                return this.internalArrivalNodo4;
            default:
                return this.internalArrivalNodo1;
        }
    }

    public void addInternalArrivalNodo(int number, Double element) {
        switch(number) {
            case 1:
                this.internalArrivalNodo1.add(element);
                break;
            case 2:
                this.internalArrivalNodo2.add(element);
                break;
            case 3:
                this.internalArrivalNodo3.add(element);
                break;
            case 4:
                this.internalArrivalNodo4.add(element);
                break;
            default:
                break;
        }
    }



    public int getServer(int number){
        switch(number){
            case 1:
                return server1;
            case 2: 
                return server2;
            case 3:
                return server3;
            case 4:
                return server4;
            default:
                return -1;
        }
    }

    //si può pensare come un metodo che prende in input numero di centro di partenza (riga) e di arrivo (colonna)
    //l'incrocio dei due è la probabilità di routing
    //se il centro di partenza è uguale a quello di arrivo, la probabilità di routing è 0 perché abbiamo
    //escluso il feedback
    private void initializeRoutingMatrix() {
        for(int i = 0; i < numCenters; i++) {
            for(int j = 0; j < numCenters; j++) {
                /*if(i == j){
                    routingMatrix[i][j] = 0;
                } else if (i > j) {
                    //questo perché i job non possono tornare indietro (indietro rispetto i centri - cioé da 1 a 0)
                    routingMatrix[i][j] = 0;
                }*/
                routingMatrix[i][j] = 0;
            }
        }

        routingMatrix[0][1] = 0.25;
        routingMatrix[0][2] = 0.25;
        routingMatrix[0][3] = 0.25;
        routingMatrix[0][4] = 0.25;

        routingMatrix[1][5] = 0.75;
        routingMatrix[2][6] = 0.5; //è la probabilità x
        routingMatrix[6][7] = 0.5; //è la probabilità y

    }

    private void printMatrix(){
        for(int i = 0; i < numCenters; i++) {
            for(int j = 0; j < numCenters; j++) {
                System.out.print(routingMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }


   





}
