package com.doat.ifmis_api.model;

public record NpsModel(
        String ppan,
        String status,
        String pran,
        String pran_gen_date,
        String dob,
        String ret_date,
        String name,
        String treasury_code,
        String office,
        String dto,
        String ddo,
        String ira_status,
        String first_capture,
        String last_capture,
        String total_capture,
        String first_upload,
        String last_upload,
        String total_upload,
        String treasuryName
) {
}
