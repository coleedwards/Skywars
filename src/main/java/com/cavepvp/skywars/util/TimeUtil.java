package com.cavepvp.skywars.util;

public class TimeUtil {

    public static String parseToTime(int seconds) {
        if (seconds < 60) {
            return "00:" + seconds;
        } else {
            int minutes = (seconds%3600 - seconds%3600%60)/60;
            int seconds1 = seconds%3600%60;

            if (seconds < 10 && minutes == 0) {
                return "00:0" + seconds;
            }

            if (seconds1 < 10 && minutes != 0) {
                return minutes + ":0" + seconds1;
            }

            return minutes + ":" + seconds1;
        }
    }
}
