package main.java.controller;

import main.java.datastruct.EventList;

import java.lang.reflect.Array;
import java.util.ArrayList;


/* LA CLASSE DEVE ESSERE SINGLETON*/
public class EventHandler {

    private static EventHandler instance = null;
    //array di gestione per eventi su nodo1
    private EventList[] eventNodo1;
    //in questo array list gestiamo i feedback: se c'è feedback facciamo una push come ultimo
    //se il feedback viene eseguito allora facciamo una pop del primo e uno shift dei valori
    private ArrayList<EventList> feedbackNodo1;
    private ArrayList<EventList> externalArrivalNodo1;
    private boolean isFeedbackNodo1 = false;
    private boolean isExternalArrivalNodo1 = false;
    //array di gestione per eventi su nodo2
    //private EventList[] eventNodo2;
    //private ArrayList<EventList> feedbackNodo2;
    //private ArrayList<EventList> externalArrivalNodo2;

    private RandomFunction random = new RandomFunction();


    private EventHandler(int server1) {
        this.eventNodo1 = new EventList[server1 + 1];
        this.feedbackNodo1 = new ArrayList<EventList>();
        this.externalArrivalNodo1 = new ArrayList<EventList>();
        //this.eventNodo2 = new EventList[server2 + 1];
        //this.feedbackNodo2 = new ArrayList<EventList>();
        //this.externalArrivalNodo2 = new ArrayList<EventList>();
    }

    public static EventHandler getInstance(int server1) {
        if (instance == null) {
            instance = new EventHandler(server1);
        }
        return instance;
    }

    public double generateDelay() {
        return this.random.extractProb() * 10;
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
    public ArrayList<EventList> getFeedbackNodo1() {
        return feedbackNodo1;
    }
    public void setFeedbackNodo1(ArrayList<EventList> feedbackNodo1) {
        this.feedbackNodo1 = feedbackNodo1;
    }
    public void removeFeedbackNodo1() {
        this.feedbackNodo1.remove(0);
    }
    public ArrayList<EventList> getExternalArrivalNodo1() {
        return externalArrivalNodo1;
    }
    public void setExternalArrivalNodo1(ArrayList<EventList> externalArrivalNodo1) {
        this.externalArrivalNodo1 = externalArrivalNodo1;
    }

    public boolean isFeedbackNodo1() {
        return isFeedbackNodo1;
    }
    public void setFeedbackNodo1(boolean feedbackNodo1) {
        isFeedbackNodo1 = feedbackNodo1;
    }
    public boolean isExternalArrivalNodo1() {
        return isExternalArrivalNodo1;
    }
    public void setExternalArrivalNodo1(boolean externalArrivalNodo1) {
        isExternalArrivalNodo1 = externalArrivalNodo1;
    }
    /*public EventList[] getEventNodo2() {
        return eventNodo2;
    }
    public void setEventNodo2(EventList[] eventNodo2) {
        this.eventNodo2 = eventNodo2;
    }*/
    /*public ArrayList<EventList> getFeedbackNodo2() {
        return feedbackNodo2;
    }
    public void setFeedbackNodo2(ArrayList<EventList> feedbackNodo2) {
        this.feedbackNodo2 = feedbackNodo2;
    }*/





}
