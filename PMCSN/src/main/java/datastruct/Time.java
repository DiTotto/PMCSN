package main.java.datastruct;

import java.util.ArrayList;

public class Time {
    private double current;
    private double next;

    //Utile ??
    /*ArrayList<Integer> lastTIME = new ArrayList<Integer>();*/

    public Time() {
        this.current = 0;
        this.next = 0;
    }

    public Time(double current, double next) {
        this.current = current;
        this.next = next;
    }



    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getNext() {
        return next;
    }

    public void setNext(double next) {
        this.next = next;
    }

    /* DA RISCRIVERE ??
    public ArrayList<Integer> getLastTIME() {
        return lastTIME;
    }

    public void setLastTIME(ArrayList<Integer> lastTIME) {
        this.lastTIME = lastTIME;
    }
    */

}
