package com.doat.ifmis_api.model;

public record AdministrativeApproval(
        String deptCode,
        String aaType,
        String scheme,
        String aaRefNo,
        String aaDate,
        String fromDate,
        String toDate,
        String finYear,
        String hoa,
        String aaName,
        Double amount,
        Double headAmount,
        String desc,
        String filePath
) {

    public static AdministrativeApproval.Builder builder() {
        return new AdministrativeApproval.Builder();
    }

    public static class Builder {
        private String deptCode;
        private String aaType;
        private String scheme;
        private String aaRefNo;
        private String aaDate;
        private String fromDate;
        private String toDate;
        private String financialYear;
        private String hoa;
        private String aaName;
        private Double amount;
        private Double headAmount;
        private String desc;
        private String filePath;

        public AdministrativeApproval.Builder deptCode(String deptCode) {
            this.deptCode = deptCode;
            return this;
        }

        public AdministrativeApproval.Builder aaType(String aaType) {
            this.aaType = aaType;
            return this;
        }

        public AdministrativeApproval.Builder scheme(String scheme) {
        this.scheme = scheme;
        return this;
        }

        public AdministrativeApproval.Builder aaRefNo(String aaRefNo) {
            this.aaRefNo = aaRefNo;
            return this;
        }

        public AdministrativeApproval.Builder aaDate(String aaDate) {
            this.aaDate = aaDate;
            return this;
        }

        public AdministrativeApproval.Builder fromDate(String fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public AdministrativeApproval.Builder toDate(String toDate) {
            this.toDate = toDate;
            return this;
        }

        public AdministrativeApproval.Builder financialYear(String financialYear) {
            this.financialYear = financialYear;
            return this;
        }

        public AdministrativeApproval.Builder hoa(String hoa) {
            this.hoa = hoa;
            return this;
        }

        public AdministrativeApproval.Builder aaName(String aaName) {
            this.aaName = aaName;
            return this;
        }

        public AdministrativeApproval.Builder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public AdministrativeApproval.Builder headAmount(Double headAmount) {
            this.headAmount = headAmount;
            return this;
        }

        public AdministrativeApproval.Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public AdministrativeApproval.Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public AdministrativeApproval build() {
            return new AdministrativeApproval(
                    deptCode,
                    aaType,
                    scheme,
                    aaRefNo,
                    aaDate,
                    fromDate,
                    toDate,
                    financialYear,
                    hoa,
                    aaName,
                    amount,
                    headAmount,
                    desc,
                    filePath
            );
        }
    }

}
