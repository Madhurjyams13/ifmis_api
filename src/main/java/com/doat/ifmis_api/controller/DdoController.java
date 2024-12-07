package com.doat.ifmis_api.controller;

import com.doat.ifmis_api.model.DdoModel;
import com.doat.ifmis_api.service.DdoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ddo")
public class DdoController {

    @Autowired
    DdoService ddoService;

    @GetMapping("getDetails/{treasuryCode}/{ddoCode}/{ddoNo}")
    public DdoModel getDdoDetails(@PathVariable String treasuryCode,
                                  @PathVariable String ddoCode,
                                  @PathVariable String ddoNo

    ) {

        String ddoCodeFormatted = treasuryCode+"/"+ddoCode+"/"+ddoNo;

        System.out.println(ddoCodeFormatted);

        return ddoService.getDdoDetails(ddoCodeFormatted);
    }
}
