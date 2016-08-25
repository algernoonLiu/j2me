package com.algernoon.date;

import java.util.Calendar;

import org.apache.commons.lang3.time.DateFormatUtils;

public class LoopDate {
    
    public static void main(String[] args) {
	Calendar reportCalendar = Calendar.getInstance();  
	reportCalendar.set(2015, 2, 1);  
	Calendar endCalendar = Calendar.getInstance();  
	endCalendar.set(2015, 3, 1); 
	String startDate = DateFormatUtils.format(reportCalendar.getTime(), "yyyyMMdd");
	String endDate = DateFormatUtils.format(endCalendar.getTime(), "yyyyMMdd");
	System.out.println(startDate);
	System.out.println(endDate);
	System.out.println("================================");
	while(reportCalendar.compareTo(endCalendar)<=0){
	    String reportDate = DateFormatUtils.format(reportCalendar.getTime(), "yyyyMMdd");
	    System.out.println(reportDate);
	    reportCalendar.add(Calendar.DATE, 1);
	}
    }
}
