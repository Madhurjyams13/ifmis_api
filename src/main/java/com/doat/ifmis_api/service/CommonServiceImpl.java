package com.doat.ifmis_api.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public HashMap<String, Object> getResponseEntity(String status, Object data, String message) {
        HashMap<String, Object> response = new HashMap<>();

        response.put("status", status);
        response.put("data", data);
        response.put("message", message);

        return response;
    }
}
