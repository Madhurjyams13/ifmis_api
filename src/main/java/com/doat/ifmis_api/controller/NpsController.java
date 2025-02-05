package com.doat.ifmis_api.controller;

import com.doat.ifmis_api.model.NpsModel;
import com.doat.ifmis_api.service.CommonService;
import com.doat.ifmis_api.service.NpsService;
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

@RestController
@AllArgsConstructor
@RequestMapping("nps")
public class NpsController {

    private static final Logger logger = LogManager.getLogger(NpsController.class);
    private final CommonService service;
    private final NpsService npsService;

    @PostMapping("getEmpDetails")
    public ResponseEntity<HashMap<String, Object>> getEmpDetails(@RequestBody NpsModel request) {

        logger.info("/nps/getEmpDetails == ppan Received == {}", request.ppan());

        if (request.ppan() == null || request.ppan().isEmpty()) return new ResponseEntity<>
                (
                        service.getResponseEntity(
                                "OK",
                                null,
                                "PPAN Not Provided"
                        ),
                        HttpStatus.OK
                );
        else if (npsService.getEmpDetails(request.ppan()) == null) return new ResponseEntity<>
                (
                        service.getResponseEntity(
                                "OK",
                                null,
                                "PPAN Details Not Found"
                        ),
                        HttpStatus.OK
                );
        else
            return new ResponseEntity<>
                    (
                            service.getResponseEntity(
                                    "OK",
                                    npsService.getEmpDetails(request.ppan()),
                                    "Details Found correctly"
                            ),
                            HttpStatus.OK
                    );

    }

    @PostMapping("getConDetails")
    public ResponseEntity<HashMap<String, Object>> getConDetails(@RequestBody NpsModel request) {

        logger.info("/nps/getConDetails == ppan Received == {}", request.ppan());

        if (request.ppan() == null || request.ppan().isEmpty()) return new ResponseEntity<>
                (
                        service.getResponseEntity(
                                "OK",
                                null,
                                "PPAN Not Provided"
                        ),
                        HttpStatus.OK
                );

        else if (npsService.getConDetails(request.ppan()) == null) return new ResponseEntity<>
                (
                        service.getResponseEntity(
                                "OK",
                                null,
                                "Contribution Details Not Found"
                        ),
                        HttpStatus.OK
                );

        return new ResponseEntity<>
                (
                        service.getResponseEntity(
                                "OK",
                                npsService.getConDetails(request.ppan()),
                                "Details Found"
                        ),
                        HttpStatus.OK
                );

    }

}
