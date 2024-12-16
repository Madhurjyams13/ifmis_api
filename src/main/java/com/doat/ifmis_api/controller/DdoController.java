package com.doat.ifmis_api.controller;

import com.doat.ifmis_api.service.DdoService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("ddo")
public class DdoController {

    private final DdoService ddoService;

    private static final Logger logger = LogManager.getLogger(DdoController.class);

    @GetMapping("test")
    public String ddoTest() {
        logger.info("/ddo/test");
        return "Controller Hit Testing";
    }

    @PostMapping("getDetails")
    public ResponseEntity<Map<String, Object>> getDdoDetails(@RequestBody String ddoCode) {
        logger.info("/ddo/getDetails == ddoCode Received == {}", ddoCode);
        Map<String, Object> res = new HashMap<>();
        if (ddoCode == null || ddoCode.isEmpty()) {
            res.put("status", 0);
            res.put("data", null);
            res.put("message", "DDO Code cannot be empty");
        } else {
            try {
                res.put("status", 1);
                res.put("data", ddoService.getDdoDetails(ddoCode));
                res.put("message", "DDO Details successfully found");
            } catch (Exception e) {
                res.put("status", 0);
                res.put("data", null);
                res.put("message", "Something went wrong with exception " + e.getMessage());
            }
        }
        return ResponseEntity.ok(res);
    }


}
