package com.doat.ifmis_api.model;

public record BudgetAllocation(
    String finYear,
    String grantNo,
    String grantDescription,
    String grantAssamese,
    String deptCode,
    String department,
    String directorateCode,
    String directorate,
    String head,
    String majorDescription,
    String majorUnicode,
    String subMajorDescription,
    String subMajorUnicode,
    String minorDescription,
    String minorUnicode,
    String subDescription,
    String subUnicode,
    String subSubDescription,
    String subSubUnicode,
    String detailDescription,
    String detailUnicode,
    String subDetailDescription,
    String subDetailUnicode,
    String scheme,
    String schemeDescription,
    Double budgetAmount
) {
    public static BudgetAllocation.Builder builder() {
        return new BudgetAllocation.Builder();
    }

    public static class Builder {
        private String finYear;
        private String grantNo;
        private String grantDescription;
        private String grantAssamese;
        private String deptCode;
        private String department;
        private String directorateCode;
        private String directorate;
        private String head;
        private String majorDescription;
        private String majorUnicode;
        private String subMajorDescription;
        private String subMajorUnicode;
        private String minorDescription;
        private String minorUnicode;
        private String subDescription;
        private String subUnicode;
        private String subSubDescription;
        private String subSubUnicode;
        private String detailDescription;
        private String detailUnicode;
        private String subDetailDescription;
        private String subDetailUnicode;
        private String scheme;
        private String schemeDescription;
        private Double budgetAmount;

        public BudgetAllocation.Builder finYear(String finYear) {
            this.finYear = finYear;
            return this;
        }

        public BudgetAllocation.Builder grantNo(String grantNo) {
            this.grantNo = grantNo;
            return this;
        }

        public BudgetAllocation.Builder grantDescription(String grantDescription) {
            this.grantDescription = grantDescription;
            return this;
        }

        public BudgetAllocation.Builder grantAssamese(String grantAssamese) {
            this.grantAssamese = grantAssamese;
            return this;
        }

        public BudgetAllocation.Builder deptCode(String deptCode) {
            this.deptCode = deptCode;
            return this;
        }

        public BudgetAllocation.Builder department(String department) {
            this.department = department;
            return this;
        }

        public BudgetAllocation.Builder directorateCode(String directorateCode) {
            this.directorateCode = directorateCode;
            return this;
        }

        public BudgetAllocation.Builder directorate(String directorate) {
            this.directorate = directorate;
            return this;
        }

        public BudgetAllocation.Builder head(String head) {
            this.head = head;
            return this;
        }

        public BudgetAllocation.Builder majorDescription(String majorDescription) {
            this.majorDescription = majorDescription;
            return this;
        }

        public BudgetAllocation.Builder majorUnicode(String majorUnicode) {
            this.majorUnicode = majorUnicode;
            return this;
        }

        public BudgetAllocation.Builder subMajorDescription(String subMajorDescription) {
            this.subMajorDescription = subMajorDescription;
            return this;
        }

        public BudgetAllocation.Builder subMajorUnicode(String subMajorUnicode) {
            this.subMajorUnicode = subMajorUnicode;
            return this;
        }

        public BudgetAllocation.Builder minorDescription(String minorDescription) {
            this.minorDescription = minorDescription;
            return this;
        }

        public BudgetAllocation.Builder minorUnicode(String minorUnicode) {
            this.minorUnicode = minorUnicode;
            return this;
        }

        public BudgetAllocation.Builder subDescription(String subDescription) {
            this.subDescription = subDescription;
            return this;
        }

        public BudgetAllocation.Builder subUnicode(String subUnicode) {
            this.subUnicode = subUnicode;
            return this;
        }

        public BudgetAllocation.Builder subSubDescription(String subSubDescription) {
            this.subSubDescription = subSubDescription;
            return this;
        }

        public BudgetAllocation.Builder subSubUnicode(String subSubUnicode) {
            this.subSubUnicode = subSubUnicode;
            return this;
        }

        public BudgetAllocation.Builder detailDescription(String detailDescription) {
            this.detailDescription = detailDescription;
            return this;
        }

        public BudgetAllocation.Builder detailUnicode(String detailUnicode) {
            this.detailUnicode = detailUnicode;
            return this;
        }

        public BudgetAllocation.Builder subDetailDescription(String subDetailDescription) {
            this.subDetailDescription = subDetailDescription;
            return this;
        }

        public BudgetAllocation.Builder subDetailUnicode(String subDetailUnicode) {
            this.subDetailUnicode = subDetailUnicode;
            return this;
        }

        public BudgetAllocation.Builder scheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public BudgetAllocation.Builder schemeDescription(String schemeDescription) {
            this.schemeDescription = schemeDescription;
            return this;
        }

        public BudgetAllocation.Builder budgetAmount(Double budgetAmount) {
            this.budgetAmount = budgetAmount;
            return this;
        }

        public BudgetAllocation build() {
            return new BudgetAllocation(
                    finYear,
                    grantNo,
                    grantDescription,
                    grantAssamese,
                    deptCode,
                    department,
                    directorateCode,
                    directorate,
                    head,
                    majorDescription,
                    majorUnicode,
                    subMajorDescription,
                    subMajorUnicode,
                    minorDescription,
                    minorUnicode,
                    subDescription,
                    subUnicode,
                    subSubDescription,
                    subSubUnicode,
                    detailDescription,
                    detailUnicode,
                    subDetailDescription,
                    subDetailUnicode,
                    scheme,
                    schemeDescription,
                    budgetAmount
            );
        }
    }
}
