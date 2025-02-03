package com.doat.ifmis_api.model;

import java.util.Date;

public record AdministrativeApproval(
        String aaType,
        String fundingAgency,
        String sanctionYear,
        String startYear,
        String aaRefNo,
        Date aaDate,
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
