package com.doat.ifmis_api.model;

public record AdministrativeApproval(
        String deptCode,
        String aaType,
        String scheme,
        String aaRefNo,
        String aaDate,
        String fromDate,
        String toDate,
        String financialYear,
        String hoa,
        String aaName,
        Double amount,
        String desc,
        String filePath
) {
}
