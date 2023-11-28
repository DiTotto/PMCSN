package main.java.controller;

import static java.lang.Math.log;
import main.java.utils.Rngs;

public class RandomFunction {

    //tempo di interarrivo del centro
    private double intTime;
    //tempo di servizio del centro
    private double serviceTime;
    //tempo di abbandono dal centro
    private double abbandonTime;

    
    private Rngs rngs = new Rngs();

    private final double start = 0;
    private double arrival = this.start;


    public RandomFunction() {
        this.intTime = 0;
        this.abbandonTime = 0;
        this.serviceTime = 0;
        this.rngs.plantSeeds(0);

    }
    public RandomFunction(double intTime, double abbandonTime, double serviceTime) {
        this.intTime = intTime;
        this.serviceTime = serviceTime;
        this.abbandonTime = abbandonTime;
    }

    


    public double Exponential(double mu) {
        return (-mu * log(1.0 - rngs.random()));
    }
    public double Uniform(double a, double b) {
        return (a + (b - a) * rngs.random());
    }  

    public double getJobArrival() {
        //arrival += this.start;
        rngs.selectStream(0);
        //arrival += Exponential(intTime);
        arrival += Exponential(2.0);
        return arrival;

    }

    public double getService() {
        rngs.selectStream(1);
        //NEL NOSTRO CASO ANDREBBE USATA LA ERLANG, NON ESPONENZIALE
        //double departure = Exponential(abbandonTime);
        double departure = Uniform(2.0, 10.0);
        return departure;
    }

    public double extractProb() {
        rngs.selectStream(2);
        return rngs.random();
    }

    /*public double getAbandon(double arrival) {
        rngs.selectStream(2);
        double abandon = arrival + Exponential(tempo di abbandono dal centro);
        return abandon;
    }*/


}
