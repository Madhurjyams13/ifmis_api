package com.doat.ifmis_api.controller;

import com.doat.ifmis_api.model.DdoModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ddo")
public class DdoController {

    @GetMapping("/getDetails")
    public DdoModel getDdoDetails(@RequestParam String ddoCode) {

        DdoModel ddoModel =new DdoModel(null,"Haao Ki HAAAAAAAAAAAAAAA");

        return ddoModel;
    }
}
