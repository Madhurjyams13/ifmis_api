package com.doat.ifmis_api.service;

public interface QueryService {

    String getDdoDetails(String ddoCode);

    String getNpsEmpDetails(String ppan);

    String getNpsConDetails(String ppan);

    String getAADetaisls(String deptCode, String fy, String fromDate, String toDate);

}

