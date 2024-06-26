package main.java.controller;

import main.java.datastruct.EventList;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.w3c.dom.events.Event;


/* Node1 prende ingressi dall'esterno e può sia uscire fuori che andare al Node2
   Node2 prende ingressi da Node1 e dall'esterno e fa uscire solo fuori
   Node 3 prende ingressi solo dall'interno e può sia uscire fuori che andare al successivo
   Node 4 prende ingressi da precedente e dall'esterno e fa uscire sia fuori che andare al successivo
*/
public class EventHandler {

    //CENTRALINO
    private static EventHandler instance = null;
    private EventList[] eventNodo0;
    private ArrayList<Double> internalArrivalNodo0;
    private static int server0 = 1;

    //ANAGRAFE
    private EventList[] eventNodo1;
    private ArrayList<Double> internalArrivalNodo1;
    private static int server1 = 3;

    //URP
    private EventList[] eventNodo2;
    private ArrayList<Double> internalArrivalNodo2;
    private static int server2 = 1;

    //SCOLASTICO
    private EventList[] eventNodo3;
    private ArrayList<Double> internalArrivalNodo3;
    private static int server3 = 4;
    //private static int server3 = 6;

    //SERVIZI SOCIALI
    private EventList[] eventNodo4;
    private ArrayList<Double> internalArrivalNodo4;
    private static int server4 = 3;
    //private static int server4 = 4;

    //STATO CIVILE
    private EventList[] eventNodo5;
    private ArrayList<Double> internalArrivalNodo5;
    private static int server5 = 2;

    //PROTOCOLLO
    private EventList[] eventNodo6;
    private ArrayList<Double> internalArrivalNodo6;
    private static int server6 = 3;

    //CULTURA
    private EventList[] eventNodo7;
    private ArrayList<Double> internalArrivalNodo7;
    private static int server7 = 2;
    //private static int server7 = 4;

    private static int numCenters = 8;

    private double[][] routingMatrix = new double[numCenters][numCenters];

    private RandomFunction random;

    private double[] exitProb = {0.02, 0.05, 0.01, 0.03, 0.04, 0.02, 0.01, 0.02};


    private EventHandler(int server0, int server1, int server2, int server3, int server4, int server5, int server6, int server7) {

        this.eventNodo0 = new EventList[server0 + 2];
        this.eventNodo1 = new EventList[server1 + 3];
        this.eventNodo2 = new EventList[server2 + 3];
        this.eventNodo3 = new EventList[server3 + 3];
        this.eventNodo4 = new EventList[server4 + 3];
        this.eventNodo5 = new EventList[server5 + 3];
        this.eventNodo6 = new EventList[server6 + 3];
        this.eventNodo7 = new EventList[server7 + 3];

        this.internalArrivalNodo0 = new ArrayList<Double>();
        this.internalArrivalNodo1 = new ArrayList<Double>();
        this.internalArrivalNodo2 = new ArrayList<Double>();
        this.internalArrivalNodo3 = new ArrayList<Double>();
        this.internalArrivalNodo4 = new ArrayList<Double>();
        this.internalArrivalNodo5 = new ArrayList<Double>();
        this.internalArrivalNodo6 = new ArrayList<Double>();
        this.internalArrivalNodo7 = new ArrayList<Double>();


        this.random = RandomFunction.getInstance();

        initializeRoutingMatrix();

        
    }

    public static EventHandler getInstance() {
        if (instance == null) {
            instance = new EventHandler(server0, server1, server2, server3, server4, server5, server6, server7);
        }
        return instance;
    }
    
    public EventList[] getEventNodo(int number) {
        switch(number) {
            case 0:
                return eventNodo0;
            case 1:
                return eventNodo1;
            case 2:
                return eventNodo2;
            case 3:
                return eventNodo3;
            case 4:
                return eventNodo4;
            case 5:
                return eventNodo5;
            case 6:
                return eventNodo6;
            case 7:
                return eventNodo7;
            default:
                return eventNodo1;
        }
    }

    public void setEventNodo(int number, EventList[] eventLists){
        switch(number) {
            case 0:
                this.eventNodo0 = eventLists;
                break;
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
            case 5:
                this.eventNodo5 = eventLists;
                break;
            case 6:
                this.eventNodo6 = eventLists;
                break;
            case 7:
                this.eventNodo7 = eventLists;
                break;
            default:
                break;
                
        }
    }

    public void resetInternal(int id) {
        switch(id) {
            case 0:
                this.internalArrivalNodo0 = new ArrayList<Double>();
                break;
            case 1:
                this.internalArrivalNodo1 = new ArrayList<Double>();
                break;
            case 2:
                this.internalArrivalNodo2 = new ArrayList<Double>();
                break;
            case 3:
                this.internalArrivalNodo3 = new ArrayList<Double>();
                break;
            case 4:
                this.internalArrivalNodo4 = new ArrayList<Double>();
                break;
            case 5:
                this.internalArrivalNodo5 = new ArrayList<Double>();
                break;
            case 6:
                this.internalArrivalNodo6 = new ArrayList<Double>();
                break;
            case 7:
                this.internalArrivalNodo7 = new ArrayList<Double>();
                break;
            default:
                break;
        }
    }

    public void resetAll() {
        this.internalArrivalNodo0.clear();
        this.internalArrivalNodo1.clear();
        this.internalArrivalNodo2.clear();
        this.internalArrivalNodo3.clear();
        this.internalArrivalNodo4.clear();
    }

    public void resetID(int id) {
        switch(id) {
            case 1:
                this.internalArrivalNodo5.clear();
                break;
            case 2:
                this.internalArrivalNodo6.clear();
                break;
            case 6:
                this.internalArrivalNodo7.clear();
                break;
        }
    }

    public void orderList(int id){
        switch(id) {
            case(0):
                if(!this.internalArrivalNodo0.isEmpty()) {
                    this.internalArrivalNodo0.sort(null);
                    break;
                }
            case(1):
                if(!this.internalArrivalNodo1.isEmpty()) {
                    this.internalArrivalNodo1.sort(null);
                    break;
                }
            case(2):
                if(!this.internalArrivalNodo2.isEmpty()) {
                    this.internalArrivalNodo2.sort(null);
                    break;
                }
            case(3):
                if(!this.internalArrivalNodo3.isEmpty()) {
                    this.internalArrivalNodo3.sort(null);
                    break;
                }
            case(4):
                if(!this.internalArrivalNodo4.isEmpty()) {
                    this.internalArrivalNodo4.sort(null);
                    break;
                }
            case(5):
                if(!this.internalArrivalNodo5.isEmpty()) {
                    this.internalArrivalNodo5.sort(null);
                    break;
                }
            case(6):
                if(!this.internalArrivalNodo6.isEmpty()) {
                    this.internalArrivalNodo6.sort(null);
                    break;
                }
            case(7):
                if(!this.internalArrivalNodo7.isEmpty()) {
                    this.internalArrivalNodo7.sort(null);
                    break;
                }
        }

    }

    public void setFirstArrival(int number, EventList event) {
        switch(number) {
            case 0:
                this.eventNodo0[0] = event;
                break;
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
            case 5:
                this.eventNodo5[0] = event;
                break;
            case 6:
                this.eventNodo6[0] = event;
                break;
            case 7:
                this.eventNodo7[0] = event;
                break;
            default:
                break;
        }
    }

    public ArrayList<Double> getInternalArrivalNodo(int number) {
        switch(number) {
            case 0:
                return this.internalArrivalNodo0;
            case 1:
                return this.internalArrivalNodo1;
            case 2:
                return this.internalArrivalNodo2;
            case 3:
                return this.internalArrivalNodo3;
            case 4:
                return this.internalArrivalNodo4;
            case 5:
                return this.internalArrivalNodo5;
            case 6:
                return this.internalArrivalNodo6;
            case 7:
                return this.internalArrivalNodo7;
            default:
                return this.internalArrivalNodo1;
        }
    }

    public void addInternalArrivalNodo(int number, Double element) {
        switch(number) {
            case 0:
                this.internalArrivalNodo0.add(element);
                break;
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
            case 5:
                this.internalArrivalNodo5.add(element);
                break;
            case 6:
                this.internalArrivalNodo6.add(element);
                break;
            case 7:
                this.internalArrivalNodo7.add(element);
                break;
            default:
                break;
        }
    }



    public int getServer(int number){
        switch(number){
            case 0:
                return server0;
            case 1:
                return server1;
            case 2: 
                return server2;
            case 3:
                return server3;
            case 4:
                return server4;
            case 5:
                return server5;
            case 6:
                return server6;
            case 7:
                return server7;
            default:
                return -1;
        }
    }

    private void initializeRoutingMatrix() {
        for(int i = 0; i < numCenters; i++) {
            for(int j = 0; j < numCenters; j++) {
                routingMatrix[i][j] = 0;
            }
        }

        routingMatrix[0][1] = 0.1;
        routingMatrix[0][2] = 0.15;
        routingMatrix[0][3] = 0.4;
        routingMatrix[0][4] = 0.125;

        routingMatrix[1][5] = 0.625;
        routingMatrix[2][6] = 0.7575;
        routingMatrix[6][7] = 0.35;

    }

    public double getRoutingProbability(int i, int j) {
        return routingMatrix[i][j];
    }

    public double getExitProbability(int i) {
        return exitProb[i];
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
