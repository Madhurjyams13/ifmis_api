package com.doat.ifmis_api.controller;

import com.doat.ifmis_api.model.AdministrativeApproval;
import com.doat.ifmis_api.service.CommonService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@RestController
@AllArgsConstructor
@RequestMapping("bud")
public class BudgetController {

    private static final Logger logger = LogManager.getLogger(BudgetController.class);
    private final CommonService service;

    @PostMapping("getAADetails")
    public ResponseEntity<HashMap<String, Object>> getAADetails(@RequestBody AdministrativeApproval aa) {

        logger.info("trying to get AA details");

        String deptCode = aa.deptCode() == null ? null: aa.deptCode();
        String fromDate = aa.fromDate() == null ? null: aa.fromDate();
        String toDate = aa.toDate() == null ? null: aa.toDate();
        String finYear =aa.finYear() == null ? null: aa.finYear();

        if (deptCode == null || toDate == null || fromDate == null || finYear == null) {
            return new ResponseEntity<>
                    (
                            service.getResponseEntity(
                                    "Error",
                                    null,
                                    "Please provide all the parameters"
                            ),
                            HttpStatus.BAD_REQUEST
                    );
        } else {
            switch (validateDateFormat(fromDate, toDate)) {
                case 0 :
                    return new ResponseEntity<>
                            (
                                    service.getResponseEntity(
                                            "Error",
                                            null,
                                            "Exception occurred"
                                    ),
                                    HttpStatus.BAD_REQUEST
                            );
                case 1 :
                    return new ResponseEntity<>
                            (
                                    service.getResponseEntity(
                                            "Error",
                                            null,
                                            "Please provide from date in correct format"
                                    ),
                                    HttpStatus.BAD_REQUEST
                            );
                case 2 :
                    return new ResponseEntity<>
                            (
                                    service.getResponseEntity(
                                            "Error",
                                            null,
                                            "Please provide to date in correct format"
                                    ),
                                    HttpStatus.BAD_REQUEST
                            );
                case 3 :
                    return new ResponseEntity<>
                            (
                                    service.getResponseEntity(
                                            "Error",
                                            null,
                                            "To date cannot be before from date"
                                    ),
                                    HttpStatus.BAD_REQUEST
                            );
                case 4 :
                    if (finYear(finYear)) {
                        //Dummy data
                        AdministrativeApproval aa1 = new AdministrativeApproval(
                                "dC1",
                                "type1",
                                "fundingAgency1",
                                "xxxx-yyyy",
                                "xxxx-yyyy",
                                "refNo1",
                                "yyyy-mm-dd",
                                "yyyy-mm-dd",
                                "yyyy-mm-dd",
                                "xxxx-yyyy",
                                "hoa",
                                "aaName1",
                                1,
                                1,
                                1,
                                1,
                                10.00,
                                10.00,
                                "desc1",
                                "filePath1"

                        );
                        AdministrativeApproval aa2 = new AdministrativeApproval(
                                "dC2",
                                "type2",
                                "fundingAgency2",
                                "xxxx-yyyy",
                                "xxxx-yyyy",
                                "refNo2",
                                "yyyy-mm-dd",
                                "yyyy-mm-dd",
                                "yyyy-mm-dd",
                                "xxxx-yyyy",
                                "hoa",
                                "aaName2",
                                2,
                                2,
                                2,
                                2,
                                20.00,
                                20.00,
                                "desc2",
                                "filePath2"

                        );
                        //Dummy data end

                        List<AdministrativeApproval> aaList = Arrays.asList(aa1, aa2);
                        return new ResponseEntity<>
                                (
                                        service.getResponseEntity(
                                                "OK",
                                                aaList,
                                                "AA Details found"
                                        ),
                                        HttpStatus.OK
                                );
                    } else {
                        return new ResponseEntity<>
                                (
                                        service.getResponseEntity(
                                                "Error",
                                                null,
                                                "Please provide a valid financial year"
                                        ),
                                        HttpStatus.BAD_REQUEST
                                );
                    }
                default:
                    return new ResponseEntity<>
                            (
                                    service.getResponseEntity(
                                            "error",
                                            null,
                                            "Something went wrong. Please contact administrator"
                                    ),
                                    HttpStatus.BAD_REQUEST
                            );
            }
        }
    }

    // Validation to check if dates are in correct format, if to-date is before from-date and if financial year is valid
    public static Integer validateDateFormat(String fromDateStr, String toDateStr) {
        // 0 == exception
        // 1 == wrong fromDate
        // 2 == wrong toDate
        // 3 == toDate before fromDate
        // 4 == validation passed
        String expectedFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(expectedFormat);
        dateFormat.setLenient(false); // Strictly enforce the format

        try {
            if (isValidDateFormat(fromDateStr, expectedFormat)) {
                if (isValidDateFormat(toDateStr, expectedFormat)) {
                    Date fromDate = dateFormat.parse(fromDateStr);
                    Date toDate = dateFormat.parse(toDateStr);

                    if (toDate.before(fromDate)) {
                        return 3;
                    } else return 4;
                } else return 2;
            } else return 1;
        } catch (ParseException e) {
            return 0;
        }
    }

    private static boolean isValidDateFormat(String dateStr, String expectedFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(expectedFormat);
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dateStr);

            return Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dateStr);
        } catch (ParseException e) {
            return false;
        }
    }

    public static Boolean finYear(String financialYear) {
        if (!Pattern.matches("\\d{4}-\\d{4}", financialYear)) {
            return false;
        }

        String[] years = financialYear.split("-");
        int startYear = Integer.parseInt(years[0]);
        int endYear = Integer.parseInt(years[1]);

        return endYear == startYear + 1;
    }
}
