package Entities;

public class Clock {

    public Clock(){}


    public String getStartTime(){
        return "00 : 00 : 00";
    }

    public String clockStringConverter(int hours, int mins, int secs){

        StringBuilder s = new StringBuilder("");
        if (String.valueOf(hours).length() == 1) {
            s.append("0");
        }
        s.append(hours);
        s.append(" : ");
        if (String.valueOf(mins).length() == 1) {
            s.append("0");
        }
        s.append(mins);
        s.append(" : ");
        if (String.valueOf(secs).length() == 1) {
            s.append("0");
        }
        s.append(secs);
        return s.toString();

    }
}
