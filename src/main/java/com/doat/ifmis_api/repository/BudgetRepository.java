package com.doat.ifmis_api.repository;


import com.doat.ifmis_api.model.AdministrativeApproval;
import com.doat.ifmis_api.model.BudgetAllocation;
import com.doat.ifmis_api.service.QueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BudgetRepository {

    private static final Logger logger = LogManager.getLogger(BudgetRepository.class);
    private final EntityManager em;
    private final QueryService queryService;

    public List<AdministrativeApproval> getAADetails(String deptCode, String fy, String fromDate, String toDate) {

        String queryString = queryService.getAADetails(deptCode, fy, fromDate, toDate);
        Query query = em.createNativeQuery(queryString);

        query.setParameter("deptCode", deptCode);
        query.setParameter("fy", fy);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        List<Object[]> aaObjectList = query.getResultList();

        return aaObjectList.stream()
                .map(data -> mapToAAModel(data, fromDate, toDate)).toList();


    }

    public List<BudgetAllocation> getBADetails(String deptCode, String fy, String grant, String HOA) {

        String queryString = queryService.getBADetails(deptCode, fy, grant, HOA);

        Query query = em.createNativeQuery(queryString);

//        query.setParameter("deptCode", deptCode);
        query.setParameter("finYear", fy);
//        query.setParameter("grant", grant);
//        query.setParameter("hoa", HOA);

        List<Object[]> baObjectList = query.getResultList();

        return baObjectList.stream()
                .map(this::mapToBAModel).toList();


    }

    private AdministrativeApproval mapToAAModel(Object[] data, String fromDate, String toDate) {


        return  AdministrativeApproval.builder()
                .uniqueId(data[0] == null ? "" : data[0].toString())
                .deptCode(data[1] == null ? "" : data[1].toString())
                .aaType(data[2] == null ? "" : data[2].toString())
                .scheme(data[3] == null ? "" : data[3].toString())
                .aaRefNo(data[4] == null ? "" : data[4].toString())
                .aaDate(data[5] == null ? "" : data[5].toString())
                .hoa(data[6] == null ? "" : data[6].toString())
                .fromDate(fromDate)
                .toDate(toDate)
                .financialYear(data[7] == null ? "" : data[7].toString())
                .aaName(data[8] == null ? "" : data[8].toString())
                .amount(data[9] == null ? 0.00 : Double.parseDouble(data[9].toString()))
                .headAmount(data[10] == null ? 0.00 : Double.parseDouble(data[10].toString()))
                .desc(data[11] == null ? "" : data[11].toString())
                .filePath(data[12] == null ? "" : data[12].toString())
                .ddoCode(data[13] == null ? "" : data[13].toString())
                .ddoName(data[14] == null ? "" : data[14].toString())
                .build();
    }




    private BudgetAllocation mapToBAModel(Object[] data) {

        return BudgetAllocation.builder()
                .schemeDescription(data[0] == null ? "" : data[0].toString())
                .finYear(data[1] == null ? "" : data[1].toString())
                .grantNo(data[2] == null ? "" : data[2].toString())
                .grantDescription(data[3] == null ? "" : data[3].toString())
                .grantAssamese(data[4] == null ? "" : data[4].toString())
                .deptCode(data[5] == null ? "" : data[5].toString())
                .department(data[6] == null ? "" : data[6].toString())
                .directorateCode(data[7] == null ? "" : data[7].toString())
                .directorate(data[8] == null ? "" : data[8].toString())
                .head(data[9] == null ? "" : data[9].toString())
                .majorDescription(data[10] == null ? "" : data[10].toString())
                .majorUnicode(data[11] == null ? "" : data[11].toString())
                .subMajorDescription(data[12] == null ? "" : data[12].toString())
                .subMajorUnicode(data[13] == null ? "" : data[13].toString())
                .minorDescription(data[14] == null ? "" : data[14].toString())
                .minorUnicode(data[15] == null ? "" : data[15].toString())
                .subDescription(data[16] == null ? "" : data[16].toString())
                .subUnicode(data[17] == null ? "" : data[17].toString())
                .subSubDescription(data[18] == null ? "" : data[18].toString())
                .subSubUnicode(data[19] == null ? "" : data[19].toString())
                .detailDescription(data[20] == null ? "" : data[20].toString())
                .detailUnicode(data[21] == null ? "" : data[21].toString())
                .subDetailDescription(data[22] == null ? "" : data[22].toString())
                .subDetailUnicode(data[23] == null ? "" : data[23].toString())
                .scheme(data[24] == null ? "" : data[24].toString())
                .budgetAmount(data[25] == null ? 0.00 : Double.parseDouble(data[25].toString()))
                .build();

    }
}
