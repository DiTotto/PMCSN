package main.java.datastruct;

import java.util.ArrayList;

public class Time {
    private int current;
    private int next;

    //Utile ??
    /*ArrayList<Integer> lastTIME = new ArrayList<Integer>();*/

    public Time() {
        this.current = 0;
        this.next = 0;
    }

    public Time(int current, int next) {
        this.current = current;
        this.next = next;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
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
