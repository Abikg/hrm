package com.book.converter;

import com.book.constant.StringConstant;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class CommonConverter {

    public Date parseDate(String date, String format) throws ParseException {
        try{
            SimpleDateFormat sd = new SimpleDateFormat(format);
            return sd.parse(date);
        }catch (ParseException ex){
            log.error("error on parseDate " + date + " => " + format + " =>", ex);
            throw ex;
        }
    }

    public Date parseDate(LocalDate date, String format) throws Exception {
        try{
            return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }catch (Exception ex){
            log.error("error on parseDate " + date + " => " + format + " =>", ex);
            throw ex;
        }
    }

    public String format(Date date){
        try {
            if (date == null){
                return null;
            }
            SimpleDateFormat sd = new SimpleDateFormat(StringConstant.DEFAULT_DATE_FORMATE);
            return sd.format(date);
        }catch (Exception ex){
            log.error("error on parseDate " + date + " => " + StringConstant.DEFAULT_DATE_FORMATE + " =>", ex);
            throw ex;
        }
    }

    public Date addHoursFromNow(int hours){
        DateTime dateTime = new DateTime();
        dateTime.plusHours(hours);
        return dateTime.toDate();
    }

    public Date addDaysFromNow(int days){
        DateTime dateTime = new DateTime();
        dateTime.plusDays(days);
        return dateTime.toDate();
    }

    public Date addMonthFromNow(int months){
        DateTime dateTime = new DateTime();
        dateTime.plusMonths(months);
        return dateTime.toDate();
    }

    public Date addHours(Date date , int hours){
        DateTime dateTime = new DateTime(date.getTime());
        dateTime.plusHours(hours);
        return dateTime.toDate();
    }
}
