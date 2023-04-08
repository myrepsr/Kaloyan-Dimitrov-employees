package com.assignment.backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDateTimeFormatter {
    private final String[] dateFromats =
            {"MM/dd/yyyy", "MM/dd/yy", "MM-dd-yyyy",
                    "MM-dd-yy", "MM dd yyyy", "MM dd yy",
                    "MM:dd:yyyy", "MM:dd:yy", "MM.dd.yyyy",
                    "MM.dd.yy", "MMddyyyy", "MMddyy"};

    private DateTimeFormatter dateTimeFormatter;
    private LocalDate localDate;

    public LocalDate parseFromStringToDate(String date){

        for(String dateFormat : dateFromats){
            dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);

            try {
                localDate = LocalDate.parse(date, dateTimeFormatter);
            } catch(Exception e) {
                //System.out.println("Date could not be parsed.");
            }
        }
        return localDate;
    }
}
