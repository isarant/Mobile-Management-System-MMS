

package mmspcclient;

import java.util.Date;
public class MyDate {
    
    private long days;
    private int year;
    private int month;
    private long minutes;
    private long seconds;
    private long hours;
    
    public  MyDate(long date) {
        Date as=new Date();
        as.setTime((date));
        String aa=as.toString();
        
        long total_seconds=date/1000;
        long total_minutes=total_seconds/60;
        seconds=total_minutes%60;
        long total_hours=total_minutes/60;
        minutes=minutes%60;
        long total_days=total_hours/24;
        hours=total_hours%24;
        long hlptotal_days=total_hours/24;
        int hlpyear=1970;
        int hlpmonth=0;
        boolean loopflag=true;
        while(loopflag) {
            
            if(date>0)
            {hlpmonth++;}else{hlpmonth--;}
            if(hlpmonth==13) {
                hlpyear++;
                hlpmonth=1;
            }
            if(hlpmonth==-1) {
                hlpyear--;
                hlpmonth=12;
            }
            int prosimo;
            if(date>0)
            {prosimo=1;}else{prosimo=-1;}
            switch(hlpmonth) {
                case 1 :{
                    if(hlptotal_days>=31*prosimo) {
                        hlptotal_days=hlptotal_days-31*prosimo;
                    }
                    else
                    {loopflag=false;}
                    break;
                }
                case 2 :{
                    if((hlpyear / 4==0 )|| (hlpyear / 10==0)) {
                        if(hlptotal_days>=29*prosimo) {
                            hlptotal_days=hlptotal_days-29*prosimo;
                        }
                        else
                        {loopflag=false;}
                    }
                    else {
                        if(hlptotal_days>=28*prosimo) {
                            hlptotal_days=hlptotal_days-28*prosimo;
                        }
                        else
                        {loopflag=false;}
                        
                    }
                    break;
                }
                case 3 :{
                    if(hlptotal_days>=31*prosimo) {
                        hlptotal_days=hlptotal_days-31*prosimo;
                    }
                    else
                    {loopflag=false;}
                    break;
                }
                case 4 :{
                    if(hlptotal_days>=30*prosimo) {
                        hlptotal_days=hlptotal_days-31*prosimo;
                    }
                    else
                    {loopflag=false;}
                    break;
                }
                case 5 :{
                    if(hlptotal_days>=31*prosimo) {
                        hlptotal_days=hlptotal_days-31*prosimo;
                    }
                    else
                    {loopflag=false;}
                    break;
                }
                case 6 :{
                    if(hlptotal_days>=30*prosimo) {
                        hlptotal_days=hlptotal_days-31*prosimo;
                    }
                    else
                    {loopflag=false;}
                    break;
                }
                case 7 :{
                    if(hlptotal_days>=31*prosimo) {
                        hlptotal_days=hlptotal_days-31*prosimo;
                    }
                    else
                    {loopflag=false;}
                    break;
                }
                case 8 :{
                    if(hlptotal_days>=31*prosimo) {
                        hlptotal_days=hlptotal_days-31*prosimo;
                    }
                    else
                    {loopflag=false;}
                    break;
                }
                case 9 :{
                    if(hlptotal_days>=30*prosimo) {
                        hlptotal_days=hlptotal_days-31*prosimo;
                    }
                    else
                    {loopflag=false;}
                    break;
                }
                case 10 :{
                    if(hlptotal_days>=31*prosimo) {
                        hlptotal_days=hlptotal_days-31*prosimo;
                    }
                    else
                    {loopflag=false;}
                    break;
                }
                case 11:{
                    if(hlptotal_days>=30*prosimo) {
                        hlptotal_days=hlptotal_days-31*prosimo;
                    }
                    else
                    {loopflag=false;}
                    break;
                }
                case 12 :{
                    if(hlptotal_days>=31*prosimo) {
                        hlptotal_days=hlptotal_days-31*prosimo;
                    }
                    else
                    {loopflag=false;}
                    break;
                }
            }
        }
        year=hlpyear;
        month=hlpmonth;
        days=hlptotal_days;
        
        
    }
    
    public long GetSeconds() {
        return seconds;
    }
    
    public String GetSecondsStr() {
        return String.valueOf(seconds);
    }
    
    public long GetMinutes() {
        return minutes;
    }
    
    public String GetMinutesStr() {
        return String.valueOf(minutes);
    }
    
    public long GetHours() {
        return hours;
    }
    
    public String GetHoursStr() {
        return String.valueOf(hours);
    }
    
    public long GetDays() {
        return days;
    }
    
    public String GetDaysStr() {
        return String.valueOf(days);
    }
    
    public int GetMonth() {
        return month;
    }
    
    public String GetMonthStr() {
        return String.valueOf(month);
    }
    
    public int GetYear() {
        return year;
    }
    
    public String GetYearStr() {
        return String.valueOf(month);
    }
    
    public String GetSortDate() {
        String StrGetSortDate="";
        if(GetDays()<10)
        { StrGetSortDate="0"+GetDaysStr()+"/";}else{StrGetSortDate=GetDaysStr()+"/";}
        if(GetMonth()<10)
        { StrGetSortDate=StrGetSortDate+"0"+GetMonthStr()+"/";}else{StrGetSortDate=StrGetSortDate+GetMonthStr()+"/";}
        StrGetSortDate=StrGetSortDate+GetYearStr();
        return StrGetSortDate;
    }
    
    public String GetSortDateWithTime() {
        String StrGetSortDate="";
        if(GetDays()<10)
        { StrGetSortDate="0"+GetDaysStr()+"/";}else{StrGetSortDate=GetDaysStr()+"/";}
        if(GetMonth()<10)
        { StrGetSortDate=StrGetSortDate+"0"+GetMonthStr()+"/";}else{StrGetSortDate=StrGetSortDate+GetMonthStr()+"/";}
        StrGetSortDate=StrGetSortDate+GetYearStr() + "  ";
        if(GetHours()<10)
        { StrGetSortDate=StrGetSortDate+"0"+GetHoursStr()+":";}else{StrGetSortDate=StrGetSortDate+GetHoursStr()+":";}
        if(GetMinutes()<10)
        { StrGetSortDate=StrGetSortDate+"0"+GetMinutesStr()+":";}else{StrGetSortDate=StrGetSortDate+GetMinutesStr()+":";}
        if(GetSeconds()<10)
        { StrGetSortDate=StrGetSortDate+"0"+GetSecondsStr()+":";}else{StrGetSortDate=StrGetSortDate+GetSecondsStr()+":";}
        return StrGetSortDate;
    }
}
