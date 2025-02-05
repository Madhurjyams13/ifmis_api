package com.doat.ifmis_api.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public Boolean validateDateFormat(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dateStr);

            return Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dateStr);
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public Boolean validateFinYearFormat(String financialYear) {
        if (!Pattern.matches("\\d{4}-\\d{4}", financialYear)) {
            return false;
        }

        String[] years = financialYear.split("-");
        int startYear = Integer.parseInt(years[0]);
        int endYear = Integer.parseInt(years[1]);

        return endYear == startYear + 1;
    }

    @Override
    public Boolean validateFromAndToDate(String fromDateStr, String toDateStr) {
        String expectedFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(expectedFormat);
        dateFormat.setLenient(false); // Strictly enforce the format

        try {
            Date fromDate = dateFormat.parse(fromDateStr);
            Date toDate = dateFormat.parse(toDateStr);

            return toDate.before(fromDate);
        } catch (ParseException e) {
            return false;
        }
    }
}
