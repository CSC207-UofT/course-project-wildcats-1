package Entities;

public class Clock {

    public Clock(){}


    public String getStartTime(){
        return "0 : 0 : 0";
    }

    public String clockStringConverter(int hours, int mins, int secs){

        StringBuilder s = new StringBuilder("");
        s.append(hours);
        s.append(" : ");
        s.append(mins);
        s.append(" : ");
        s.append(secs);
        return s.toString();

    }
}
