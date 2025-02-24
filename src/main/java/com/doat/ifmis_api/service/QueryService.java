package com.doat.ifmis_api.service;

public interface QueryService {

    String getDdoDetails(String ddoCode);

    String getNpsEmpDetails(String ppan);

    String getNpsConDetails(String ppan);

    String getAADetails(String deptCode, String fy, String fromDate, String toDate);

    String getBADetails(String deptCode, String fy, String grant, String HOA);

}

