package csumb.otterlibrarysystem;

/**
 * Created by jazzb_000 on 5/8/2015.
 */
public class Time {
    private Integer minutes;

    public Time(Integer month, Integer day, Integer hour, Integer min, String ampm){
        if(ampm.equals("PM")){
            hour+=12;
        }
        if(ampm.equals("AM")){
            hour %= 12;
        }
        minutes = min + hour*60 + day*24*60 + month*24*60*30;

    }

    public Time(Integer minutes){
        this.minutes = minutes;
    }

    public Time(){
        minutes = -1;

    }

    public Integer getMinutes(){
        return minutes;
    }

    public String toString(){
        Integer day = minutes / 1440 % 30;
        Integer month = (minutes / 43200) + 1;
        Integer min = minutes % 60;
        Integer hour = minutes/60%24;
        String ampm = "AM";
        if(hour == 0){
            hour = 12;
        }
        if(hour > 12){
            ampm = "PM";
            hour -= 12;
        }
        String hourS = "";
        if(hour < 10){
            hourS += "0";
        }
        hourS += hour.toString();
        String minS = "";
        if(min < 10){
            minS += "0";
        }
        minS += min.toString();

        String dayS = "";
        if(day < 10){
            dayS = "0";
        }
        dayS += day.toString();
        String monthS = "";
        if(month < 10){
            monthS = "0";
        }
        monthS += month.toString();
        String date = (monthS + "/" + dayS + "/2015 " + hourS + ":" + minS + " (" + ampm + ")");
        return date;
    }

}
