package com.doat.ifmis_api.service;

import com.doat.ifmis_api.model.AdministrativeApproval;
import com.doat.ifmis_api.model.BudgetAllocation;
import com.doat.ifmis_api.repository.BudgetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    @Override
    public List<AdministrativeApproval> getAADetails(String deptCode, String fy, String fromDate, String toDate) {

        return budgetRepository.getAADetails(deptCode, fy, fromDate, toDate);


    }

    @Override
    public List<BudgetAllocation> getBADetails(String deptCode, String fy, String grant, String hoa) {

        return budgetRepository.getBADetails(deptCode, fy, grant, hoa);

    }
}
