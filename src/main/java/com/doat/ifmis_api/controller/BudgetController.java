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

import java.util.*;

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
        String toDate = aa.fromDate() == null ? null: aa.fromDate();
        String fromDate = aa.toDate() == null ? null: aa.toDate();
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
            String dateFormat = "yyyy-MM-dd";
            String finYearFormat = "xxxx-yyyy";
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
                    1.00,
                    1.00,
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
                    2.00,
                    2.00,
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
        }
    }
}
