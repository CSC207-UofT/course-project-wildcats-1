package Entities;

import java.util.Timer;
import java.util.TimerTask;

public class Clock {

    public Clock(){}

    public String getStartTime(){
        return "00 00 00";
    }

    public String clockLoop(){
        int hours = 0;
        int mins = 0;
        int secs = 0;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 0, 1000);//wait 0 ms before doing the action and do it evry 1000ms (1second)

        timer.cancel();//stop the timer

        StringBuilder s = new StringBuilder("");
        s.append(hours);
        s.append(" ");
        s.append(mins);
        s.append(" ");
        s.append(secs);
        return s.toString();

    }
}
