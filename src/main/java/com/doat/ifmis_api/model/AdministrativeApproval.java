package com.doat.ifmis_api.model;

import java.util.Date;

public record AdministrativeApproval(
        String deptCode,
        String aaType,
        String fundingAgency,
        String sanctionYear,
        String startYear,
        String aaRefNo,
        String aaDate,
        String fromDate,
        String toDate,
        String finYear,
        String hoa,
        String aaName,
        Integer cca,
        Integer estPotentialNIA,
        Integer estPotentialAIA,
        Integer potentialCreatedAIA,
        Double stateAmt,
        Double centralAmt,
        String desc,
        String filePath
) {
}
