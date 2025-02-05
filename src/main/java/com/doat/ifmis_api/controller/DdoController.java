package com.doat.ifmis_api.controller;

import com.doat.ifmis_api.model.DdoModel;
import com.doat.ifmis_api.service.CommonService;
import com.doat.ifmis_api.service.DdoService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("ddo")
public class DdoController {

    private static final Logger logger = LogManager.getLogger(DdoController.class);
    private final DdoService ddoService;
    private final CommonService service;

    @GetMapping("test")
    public String ddoTest() {
        logger.info("/ddo/test");
        return "Controller Hit Testing";
    }

    @PostMapping("getDetails")
    public ResponseEntity<HashMap<String, Object>> getDdoDetails(@RequestBody DdoModel request) {
        logger.info("/ddo/getDetails == ddoCode Received == {}", request.ddoCode());
        if (request.ddoCode() == null || request.ddoCode().isEmpty()) return new ResponseEntity<>
                (
                        service.getResponseEntity(
                                "OK",
                                null,
                                "DDO Code Not Provided"
                        ),
                        HttpStatus.OK
                );
        else if (ddoService.getDdoDetails(request.ddoCode()) == null) return new ResponseEntity<>
                (
                        service.getResponseEntity(
                                "OK",
                                null,
                                "DDO Details Not Found"
                        ),
                        HttpStatus.OK
                );
        else return new ResponseEntity<>
                    (
                            service.getResponseEntity(
                                    "OK",
                                    ddoService.getDdoDetails(request.ddoCode()),
                                    "Details Found"
                            ),
                            HttpStatus.OK
                    );
    }

}
