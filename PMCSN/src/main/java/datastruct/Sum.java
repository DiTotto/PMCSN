package main.java.datastruct;

public class Sum {
    double service; /*service times*/
    long served; /*number served*/

    public Sum() {
        this.service = 0;
        this.served = 0;
    }

    public double getService() {
        return this.service;
    }

    public void setService(double x) {
        this.service = x;
    }

    public long getServed() {
        return this.served;
    }

    public void setServed(long x) {
        this.served = x;
    }
    
    public void incrementService(double service) {
        this.service += service;
    }
    public void incrementServed() {
        this.served++;
    }

}