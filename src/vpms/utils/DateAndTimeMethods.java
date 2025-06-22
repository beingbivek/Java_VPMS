/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author being
 */
public class DateAndTimeMethods {
    
    private static String convert24HrInto12Hr(String time24){
        LocalTime t = LocalTime.parse(time24, DateTimeFormatter.ofPattern("HH:mm:ss"));
        String time12 = t.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        return time12;
    }
    
    public static String[] splitDateAndTime(String dateTimeString){
        String[] parts = dateTimeString.split(" ");
        parts[1] = convert24HrInto12Hr(parts[1]);
        return parts;
    }
    
    public static String getDateAndTime(){
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // Combine into LocalDateTime
        LocalDateTime dateTime = LocalDateTime.of(currentDate, currentTime);

        // Format as SQL DATETIME string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
    
}
