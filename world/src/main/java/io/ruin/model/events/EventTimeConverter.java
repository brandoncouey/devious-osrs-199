package io.ruin.model.events;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventTimeConverter {

    public static final String START_DATE = "19/07/2022 05:24:00 PM";
    public static final String END_DATE = "19/07/2022 05:27:00 PM";

    public static void main(String[] args)
    {
        //Specifying the pattern of input date and time
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        try{
            //formatting the dateString to convert it into a Date
            Date date = sdf.parse(START_DATE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            System.out.println("Start Date Time Milis: " + calendar.getTimeInMillis());
            date = sdf.parse(END_DATE);
            calendar.setTime(date);
            System.out.println("End Date Time Milis: " + calendar.getTimeInMillis());
            System.out.println("================= FORMATTED FOR COMMAND =================");
            System.out.println(START_DATE + " " + END_DATE);
        }catch(ParseException e){
            e.printStackTrace();
        }
    }
}
