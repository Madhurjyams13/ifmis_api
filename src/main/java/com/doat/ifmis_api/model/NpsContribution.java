package com.doat.ifmis_api.model;

public record NpsContribution (
    String accMonth,
    String accYear,
    String uploadDate,
    String empID,
    String PRAN,
    String payYear,
    String payMonth,
    String amount,
    String type,
    String transactionID,
    String uploadStatus,
    String govtContri
) {
}
