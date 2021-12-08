package Entities;

public class Clock {

    public Clock(){}

    //initiate clock state
    public String getStartTime(){
        return "0 : 0 : 0";
    }

    //convert time info to string in format to be shown on game window
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
