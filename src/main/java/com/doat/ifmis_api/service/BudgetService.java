package com.doat.ifmis_api.service;

import com.doat.ifmis_api.model.AdministrativeApproval;
import com.doat.ifmis_api.model.BudgetAllocation;

import java.util.List;

public interface BudgetService {

    List<AdministrativeApproval> getAADetails(String deptCode, String fy, String fromDate, String toDate);

    List<BudgetAllocation> getBADetails(String deptCode, String fy, String grant, String hoa);

}
