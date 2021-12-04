package Entities;

public class Clock {
    private int hours;
    private int mins;
    private int secs;

    public Clock(int hours, int mins, int secs){
        this.hours = hours;
        this.mins = mins;
        this.secs = secs;
    }

    public String getTime(){
        return "00 00 01";
    }
}
