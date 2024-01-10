package main.java.controller;

import static java.lang.Math.log;
import main.java.utils.*;


public class RandomFunction {

    //tempo di interarrivo del centro
    private double intTime;
    //tempo di servizio del centro
    private double serviceTime;
    //tempo di abbandono dal centro
    private double abbandonTime;

    private static RandomFunction instance = null; 

    
    private Rngs rngs = new Rngs();
    private Rvms rvms = new Rvms();

    private final double start = 0;
    //private double arrival = this.start;
    //private double arrival2 = this.start;

    private double[] arrival = new double[8];

    private int[] interarrivalTime = {7, 12, 7, 5, 5, 5, 5, 5};

    private RandomFunction() {
        this.intTime = 0;
        this.abbandonTime = 0;
        this.serviceTime = 0;
        this.rngs.plantSeeds(0);

        for(int i = 0; i < 8; i++) {
            arrival[i] = this.start;
        }

    }

    public static RandomFunction getInstance() {
        if (instance == null) {
            instance = new RandomFunction();
        }
        return instance;
    }
    
    private RandomFunction(double intTime, double abbandonTime, double serviceTime) {
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

    public double NormalTruncated(double m, double s, double a, double b) throws Exception {
        // Genera un numero casuale dalla distribuzione normale standard
        // m indica la media e s la deviazione standard

        if (a >= b) {
            throw new Exception("Il valore di a deve essere minore di b");
        }

        double u;
        double z;

        while(true) {
            u = rngs.random();
            z = rvms.idfNormal(m, s, u);

            if (z >= a && z <= b){
                return z;
            }
        }

        // Scala e trasla il numero secondo la media e la deviazione standard
        

        // Verifica se il numero è all'interno dell'intervallo desiderato
        
    }

    /*public double getJobArrival() {
        //arrival += this.start;
        rngs.selectStream(0);
        //arrival += Exponential(intTime);
        arrival += Exponential(7); //ipotizziamo che all'URP arrivino 70 richieste in media al giorno, quindi una ogni circa 7 minuti
        return arrival;

    }*/

    /*public double getService() {
        rngs.selectStream(1);
        //NEL NOSTRO CASO ANDREBBE USATA LA ERLANG, NON ESPONENZIALE
        //double departure = Exponential(abbandonTime);
        double prob = extractProb();
        double departure = 0.0;
        if (prob <= 0.33) {
            departure = Exponential((double)5);
        }else if (prob > 0.33 && prob <= 0.4){
            departure = Exponential((double)15);
        }else if (prob > 0.4 && prob <= 0.69){
            departure = Exponential((double)3);
        }else if (prob > 0.69 && prob <= 0.97){
            departure = Uniform(5, 10);
        }else if (prob > 0.97){
            departure = Uniform(15,20);    
        }
        //double departure = Uniform(2.0, 5.0);
        return departure;
    }

    public double getJobArrival2() {
        //arrival += this.start;
        rngs.selectStream(2);
        //arrival += Exponential(intTime);
        arrival2 += Exponential(5);
        return arrival2;

    }

    public double getService2() {
        rngs.selectStream(3);
        //NEL NOSTRO CASO ANDREBBE USATA LA ERLANG, NON ESPONENZIALE
        //double departure = Exponential(abbandonTime);
        double departure = Uniform(2.0, 10.0);
        return departure;
    }*/

    public double getJobArrival(int id) {
        //arrival += this.start;
        rngs.selectStream(id);
        //arrival += Exponential(intTime);
        arrival[id] += Exponential(7);
        return arrival[id];

    }

    public double getService(int id) {
        double departure = 0.0;
        double prob;
        switch(id) {
            case 0:
                prob = extractProb();
                rngs.selectStream(id+8);
                if (prob <= 0.33) {
                    departure = Exponential((double)5);
                }else if (prob > 0.33 && prob <= 0.4){
                    departure = Exponential((double)15);
                }else if (prob > 0.4 && prob <= 0.69){
                    departure = Exponential((double)3);
                }else if (prob > 0.69 && prob <= 0.97){
                    departure = Uniform(5, 10);
                }else if (prob > 0.97){
                    departure = Uniform(15,20);
                }
                //double departure = Uniform(2.0, 5.0);
                return departure;
            case 1:
                try {
                    rngs.selectStream(id+8);
                    departure = NormalTruncated(17.5, 2.5, 15, 20);
                    return departure;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case 2:
                try {
                    prob = extractProb();
                    rngs.selectStream(id+8);
                    if (prob <= 0.30) {
                        departure = NormalTruncated(5, 2, 3, 7);
                    }else if (prob > 0.30 && prob <= 0.60){
                        departure = NormalTruncated(3, 2, 1, 5);
                    } else if (prob > 0.60 && prob <= 0.66){
                        departure = NormalTruncated(15, 2, 13, 17);
                    } else if (prob > 0.66 && prob <= 0.70){
                        departure = NormalTruncated(17.5, 2.5, 15, 20);
                    } else if (prob > 0.70) {
                        departure = NormalTruncated(7.5, 2.5, 5, 10);
                    }
                    return departure;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            default:
                rngs.selectStream(id+8);
                departure = Uniform(2.0, 10.0);
                return departure;
        }
    }



    public double extractProb() {
        rngs.selectStream(20);
        return rngs.random();
    }


}
