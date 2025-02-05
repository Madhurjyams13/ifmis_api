package com.doat.ifmis_api.service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QueryServiceImpl implements QueryService {

    @Autowired
    public QueryServiceImpl(EntityManager entityManager) {
    }

    @Override
    public String getDdoDetails(String ddoCode) {
        return QueryConstants.DDO_DETAILS_QUERY;
    }

    @Override
    public String getNpsEmpDetails(String ppan) {
        return QueryConstants.NPS_EMP_DETAILS_QUERY;
    }

    @Override
    public String getNpsConDetails(String ppan) {
        return QueryConstants.NPS_CON_DETAILS_QUERY;
    }

    @Override
    public String getAADetaisls(String deptCode, String fy, String fromDate, String toDate) {
        return QueryConstants.AA_DETAILS_QUERY;
    }
}
