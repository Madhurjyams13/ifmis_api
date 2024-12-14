package com.doat.ifmis_api.controller;

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

    DdoService ddoService;

    CommonService service;

    private static final Logger logger = LogManager.getLogger(DdoController.class);

    @GetMapping("test")
    public String ddoTest() {

        logger.info("/ddo/test");

        return "Controller Hit Testing";
    }

    @PostMapping("getDetails")
    public ResponseEntity<HashMap<String, Object>> getDdoDetails(@RequestParam String ddoCode) {

        logger.info("/ddo/getDetails == ddoCode Received == {}", ddoCode);

        if(ddoCode==null || ddoCode.equals(""))
        {
            return new ResponseEntity<>
                    (
                            service.getResponseEntity(
                                    "OK",
                                    null,
                                    "DDO Code Not Provided"
                            ),
                            HttpStatus.OK
                    ) ;
        }
        else if (ddoService.getDdoDetails(ddoCode)==null)
        {
            return new ResponseEntity<>
                    (
                            service.getResponseEntity(
                                    "OK",
                                    null,
                                    "DDO Details Not Found"
                            ),
                            HttpStatus.OK
                    );
        }

        return new ResponseEntity<>
                (
                        service.getResponseEntity(
                                "OK",
                                ddoService.getDdoDetails(ddoCode),
                                "Details Found"
                        ),
                        HttpStatus.OK
                ) ;
    }

}
