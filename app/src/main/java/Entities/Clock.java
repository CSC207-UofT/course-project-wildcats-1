package Entities;

public class Clock {

    public Clock(){}

    //initiate clock state white
    public String getStartTimeWhite(){
        return "White's Timer  00 : 00 : 00";
    }

    //initiate clock state black
    public String getStartTimeBlack(){
        return "Black's Timer 00 : 00 : 00";
    }

    //convert time info to string in format to be shown on game window
    public String clockStringConverterWhite(int hours, int mins, int secs){

        StringBuilder s = new StringBuilder("White's Timer\n");
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

    //convert time info to string in format to be shown on game window
    public String clockStringConverterBlack(int hours, int mins, int secs){

        StringBuilder s = new StringBuilder("Black's Timer\n");
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
