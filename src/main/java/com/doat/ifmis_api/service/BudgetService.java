package com.doat.ifmis_api.service;

import com.doat.ifmis_api.model.AdministrativeApproval;

import java.util.List;

public interface BudgetService {

    public List<AdministrativeApproval> getAADetails(String deptCode, String fy, String fromDate, String toDate);

}
