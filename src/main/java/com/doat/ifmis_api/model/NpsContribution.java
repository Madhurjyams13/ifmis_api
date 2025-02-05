package com.doat.ifmis_api.model;

public record NpsContribution(
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String accMonth;
        private String accYear;
        private String uploadDate;
        private String empID;
        private String PRAN;
        private String payYear;
        private String payMonth;
        private String amount;
        private String type;
        private String transactionID;
        private String uploadStatus;
        private String govtContri;

        public Builder accMonth(String accMonth) {
            this.accMonth = accMonth;
            return this;
        }

        public Builder accYear(String accYear) {
            this.accYear = accYear;
            return this;
        }

        public Builder uploadDate(String uploadDate) {
            this.uploadDate = uploadDate;
            return this;
        }

        public Builder empID(String empID) {
            this.empID = empID;
            return this;
        }

        public Builder PRAN(String PRAN) {
            this.PRAN = PRAN;
            return this;
        }

        public Builder payYear(String payYear) {
            this.payYear = payYear;
            return this;
        }

        public Builder payMonth(String payMonth) {
            this.payMonth = payMonth;
            return this;
        }

        public Builder amount(String amount) {
            this.amount = amount;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder transactionID(String transactionID) {
            this.transactionID = transactionID;
            return this;
        }

        public Builder uploadStatus(String uploadStatus) {
            this.uploadStatus = uploadStatus;
            return this;
        }

        public Builder govtContri(String govtContri) {
            this.govtContri = govtContri;
            return this;
        }

        public NpsContribution build() {
            return new NpsContribution(
                    accMonth,
                    accYear,
                    uploadDate,
                    empID,
                    PRAN,
                    payYear,
                    payMonth,
                    amount,
                    type,
                    transactionID,
                    uploadStatus,
                    govtContri
            );
        }
    }

}
