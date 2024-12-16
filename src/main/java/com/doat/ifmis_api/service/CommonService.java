package com.doat.ifmis_api.service;

import java.util.HashMap;

public interface CommonService {
    HashMap<String, Object> getResponseEntity(String status, Object data, String message);
}
