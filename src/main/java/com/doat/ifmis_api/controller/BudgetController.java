package com.doat.ifmis_api.controller;

import com.doat.ifmis_api.model.AdministrativeApproval;
import com.doat.ifmis_api.model.BudgetAllocation;
import com.doat.ifmis_api.service.BudgetService;
import com.doat.ifmis_api.service.CommonService;
import com.doat.ifmis_api.service.ValidationService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("bud")
public class BudgetController {

    private static final Logger logger = LogManager.getLogger(BudgetController.class);
    private final CommonService service;
    private final BudgetService budgetService;
    private final ValidationService validationService;

    @PostMapping("getAADetails")
    public ResponseEntity<HashMap<String, Object>> getAADetails(@RequestBody AdministrativeApproval aa) {

        logger.info("trying to get AA details");

        String deptCode = aa.deptCode() == null ? null : aa.deptCode();
        String fromDate = aa.fromDate() == null ? null : aa.fromDate();
        String toDate = aa.toDate() == null ? null : aa.toDate();
        String finYear = aa.finYear() == null ? null : aa.finYear();

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
            if (validationService.validateDateFormat(fromDate)) {
                if (validationService.validateDateFormat(toDate)) {
                    if (validationService.validateFinYearFormat(finYear)) {
                        if (!validationService.validateFromAndToDate(fromDate, toDate)) {
                            if (validationService.validateDatesWithinFinancialYear(fromDate, toDate, finYear)) {
                                List<AdministrativeApproval> aaList = budgetService.getAADetails(deptCode, finYear, fromDate, toDate);

                                if (aaList.isEmpty())
                                    return new ResponseEntity<>
                                            (
                                                    service.getResponseEntity(
                                                            "OK",
                                                            budgetService.getAADetails(deptCode, finYear, fromDate, toDate),
                                                            "AA Details Not found"
                                                    ),
                                                    HttpStatus.OK
                                            );
                                return new ResponseEntity<>
                                        (
                                                service.getResponseEntity(
                                                        "OK",
                                                        budgetService.getAADetails(deptCode, finYear, fromDate, toDate),
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
                                                        "From and To Dates need to fall inside financial year range."
                                                ),
                                                HttpStatus.BAD_REQUEST
                                        );
                            }
                        } else {
                            return new ResponseEntity<>
                                    (
                                            service.getResponseEntity(
                                                    "Error",
                                                    null,
                                                    "To date cannot be before from date"
                                            ),
                                            HttpStatus.BAD_REQUEST
                                    );
                        }
                    } else {
                        return new ResponseEntity<>
                                (
                                        service.getResponseEntity(
                                                "Error",
                                                null,
                                                "Please provide financial year in the correct format"
                                        ),
                                        HttpStatus.BAD_REQUEST
                                );
                    }
                } else {
                    return new ResponseEntity<>
                            (
                                    service.getResponseEntity(
                                            "Error",
                                            null,
                                            "Please provide to date in correct format"
                                    ),
                                    HttpStatus.BAD_REQUEST
                            );
                }
            } else {
                return new ResponseEntity<>
                        (
                                service.getResponseEntity(
                                        "Error",
                                        null,
                                        "Please provide from date in correct format"
                                ),
                                HttpStatus.BAD_REQUEST
                        );
            }
        }
    }

    @PostMapping("getBADetails")
    public ResponseEntity<HashMap<String, Object>> getBADetails(@RequestBody BudgetAllocation ba) {

        logger.info("trying to get BA details");

        String finYear = ba.finYear() == null ? null : ba.finYear();
        String deptCode = ba.deptCode() == null ? null : ba.deptCode();
        String grant = ba.grantNo() == null ? null : ba.grantNo();
        String hoa = ba.head() == null ? null : ba.head();

        if (finYear == null && deptCode == null && grant == null) {
            return new ResponseEntity<>
                    (
                            service.getResponseEntity(
                                    "Error",
                                    null,
                                    "Financial year, Department Code and Grant Number must be provided"
                            ),
                            HttpStatus.BAD_REQUEST
                    );
        } else {
            if (hoa == null) {
                return new ResponseEntity<>
                        (
                                service.getResponseEntity(
                                        "OK",
                                        null,
                                        "Data found without HOA"
                                ),
                                HttpStatus.OK
                        );
            } else {
                return new ResponseEntity<>
                        (
                                service.getResponseEntity(
                                        "OK",
                                        budgetService.getBADetails(deptCode, finYear, grant, hoa),
                                        "AA Details found"
                                ),
                                HttpStatus.OK
                        );
            }
        }

    }
}
