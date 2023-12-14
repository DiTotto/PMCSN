package main.java.controller;

import main.java.datastruct.EventList;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.w3c.dom.events.Event;


/* LA CLASSE DEVE ESSERE SINGLETON*/
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

    private RandomFunction random;


    private EventHandler(int server1, int server2) {
        this.eventNodo1 = new EventList[server1 + 2];
        this.eventNodo2 = new EventList[server2 + 3];
        this.eventNodo3 = new EventList[server3 + 3];

        this.internalArrivalNodo1 = new ArrayList<Double>();
        this.internalArrivalNodo2 = new ArrayList<Double>();
        this.internalArrivalNodo3 = new ArrayList<Double>();
        this.random = RandomFunction.getInstance();

        
    }

    public static EventHandler getInstance() {
        if (instance == null) {
            instance = new EventHandler(server1, server2);
        }
        return instance;
    }

    public int getServer1(){
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
    
    public EventList[] getEventNodo(int number) {
        switch(number) {
            case 1: 
                return eventNodo1;                      
            case 2:
                return eventNodo2;
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
            default:
                return -1;
        }
    }
   





}
