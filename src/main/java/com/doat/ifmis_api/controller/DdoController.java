package com.doat.ifmis_api.controller;

import com.doat.ifmis_api.model.DdoModel;
import com.doat.ifmis_api.service.DdoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ddo")
public class DdoController {

    @Autowired
    DdoService ddoService;

    @PostMapping("getDetails")
    public DdoModel getDdoDetails(@RequestParam("ddoCode") String ddoCode) {

        System.out.println(ddoCode);

        return ddoService.getDdoDetails(ddoCode);
    }
}
