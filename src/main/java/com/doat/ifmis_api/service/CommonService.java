package com.doat.ifmis_api.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface CommonService {

    HashMap<String, Object> getResponseEntity(String status, Object Data, String message);

}
