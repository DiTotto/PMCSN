package controller;

import static java.lang.Math.log;
import utils.Rngs;

public class RandomFunction {

    //tempo di interarrivo del centro
    private double intTime;
    //tempo di servizio del centro
    private double serviceTime;
    //tempo di abbandono dal centro
    private double abbandonTime;

    public RandomFunction() {
        this.intTime = 0;
        this.abbandonTime = 0;
        this.serviceTime = 0;
    }
    public RandomFunction(double intTime, double abbandonTime, double serviceTime) {
        this.intTime = intTime;
        this.serviceTime = serviceTime;
        this.abbandonTime = abbandonTime;
    }

    private Rngs rngs = new Rngs();


    public double Exponential(double mu) {
        return (-mu * log(1.0 - rngs.random()));
    }

    public double getJobArrival(double arrival) {
        rngs.selectStream(0);
        arrival = arrival + Exponential(intTime);
        return arrival;

    }

    public double getService(double start) {
        rngs.selectStream(1);
        //NEL NOSTRO CASO ANDREBBE USATA LA ERLANG, NON ESPONENZIALE
        double departure = start + Exponential(abbandonTime);
        return departure;
    }

    public double getAbandon(double arrival) {
        rngs.selectStream(2);
        double abandon = arrival + Exponential(/*tempo di abbandono dal centro*/);
        return abandon;
    }


}
