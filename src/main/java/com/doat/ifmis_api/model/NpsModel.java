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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String ppan;
        private String status;
        private String pran;
        private String pran_gen_date;
        private String dob;
        private String ret_date;
        private String name;
        private String treasury_code;
        private String office;
        private String dto;
        private String ddo;
        private String ira_status;
        private String first_capture;
        private String last_capture;
        private String total_capture;
        private String first_upload;
        private String last_upload;
        private String total_upload;
        private String treasuryName;

        public Builder ppan(String ppan) {
            this.ppan = ppan;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder pran(String pran) {
            this.pran = pran;
            return this;
        }

        public Builder pran_gen_date(String pran_gen_date) {
            this.pran_gen_date = pran_gen_date;
            return this;
        }

        public Builder dob(String dob) {
            this.dob = dob;
            return this;
        }

        public Builder ret_date(String ret_date) {
            this.ret_date = ret_date;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder treasury_code(String treasury_code) {
            this.treasury_code = treasury_code;
            return this;
        }

        public Builder office(String office) {
            this.office = office;
            return this;
        }

        public Builder dto(String dto) {
            this.dto = dto;
            return this;
        }

        public Builder ddo(String ddo) {
            this.ddo = ddo;
            return this;
        }

        public Builder ira_status(String ira_status) {
            this.ira_status = ira_status;
            return this;
        }

        public Builder first_capture(String first_capture) {
            this.first_capture = first_capture;
            return this;
        }

        public Builder last_capture(String last_capture) {
            this.last_capture = last_capture;
            return this;
        }

        public Builder total_capture(String total_capture) {
            this.total_capture = total_capture;
            return this;
        }

        public Builder first_upload(String first_upload) {
            this.first_upload = first_upload;
            return this;
        }

        public Builder last_upload(String last_upload) {
            this.last_upload = last_upload;
            return this;
        }

        public Builder total_upload(String total_upload) {
            this.total_upload = total_upload;
            return this;
        }

        public Builder treasuryName(String treasuryName) {
            this.treasuryName = treasuryName;
            return this;
        }

        public NpsModel build() {
            return new NpsModel(
                    ppan,
                    status,
                    pran,
                    pran_gen_date,
                    dob,
                    ret_date,
                    name,
                    treasury_code,
                    office,
                    dto,
                    ddo,
                    ira_status,
                    first_capture,
                    last_capture,
                    total_capture,
                    first_upload,
                    last_upload,
                    total_upload,
                    treasuryName
            );
        }
    }

}
