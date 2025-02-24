package com.doat.ifmis_api.service;

public interface ValidationService {

    Boolean validateDateFormat(String dateStr);

    Boolean validateFinYearFormat(String financialYear);

    Boolean validateFromAndToDate(String fromDateStr, String toDateStr);

    Boolean validateDatesWithinFinancialYear(String fromDateStr, String toDateStr, String financialYear);
}
