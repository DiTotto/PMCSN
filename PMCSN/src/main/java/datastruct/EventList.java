package main.java.datastruct;

public class EventList {
    private double t;
    private int x;

    public EventList(double t, int x) {
        this.t = t;
        this.x = x;
    }

    public double getT() {
        return t;
    }
    public int getX() {
        return x;
    }
    public void setT(double t) {
        this.t = t;
    }
    public void setX(int x) {
        this.x = x;
    }

    public static int NextEvent(EventList[] event, int servers) {
        int e;
        int i = 0;
    
        while (event[i].x == 0)
            i++;
        e = i;
        while (i < (servers + 1)) {
            i++;
            if ((event[i].x == 1) && (event[i].t < event[e].t))
                e = i;
        }
        return (e);
    }

    public static int NextEvent2(EventList[] event, int servers) {
        int e;
        int i = 0;
    
        while (event[i].x == 0) {
            i++;
            if (i == event.length) {
                System.out.println("No event found in the range.");
                return -1; // Handle this case appropriately
            }
        }
        e = i;
        while (i < (servers + 2)) {
            i++;
            if (i == event.length) {
                System.out.println("No event found in the range." + i);
                return -1; // Handle this case appropriately
            }
            if ((event[i].x == 1) && (event[i].t < event[e].t))
                e = i;
        }
        return (e);
    }

}
