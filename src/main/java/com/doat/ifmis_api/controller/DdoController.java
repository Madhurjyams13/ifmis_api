package com.doat.ifmis_api.controller;

import com.doat.ifmis_api.model.DdoModel;
import com.doat.ifmis_api.service.DdoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ddo")
public class DdoController {

    @Autowired
    DdoService ddoService;

    private static final Logger logger = LogManager.getLogger(DdoController.class);

    @GetMapping("test")
    public String ddoTest() {

        logger.info("/ddo/test");

        return "Controller Hit Testing";
    }

    @PostMapping("getDetails")
    public DdoModel getDdoDetails(@RequestBody String ddoCode) {

        logger.info("/ddo/getDetails == ddoCode Received == {}", ddoCode);

        return ddoService.getDdoDetails(ddoCode);
    }


}
