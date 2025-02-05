package com.doat.ifmis_api.model;

public record DdoModel(
        String district,
        String treasuryName,
        String treasuryCode,
        String ddoCode,
        String officeName,
        String title,
        String ddoFirstName,
        String ddoLastName,
        String email,
        String mobile

) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String district;
        private String treasuryName;
        private String treasuryCode;
        private String ddoCode;
        private String officeName;
        private String title;
        private String ddoFirstName;
        private String ddoLastName;
        private String email;
        private String mobile;

        public Builder district(String district) {
            this.district = district;
            return this;
        }

        public Builder treasuryName(String treasuryName) {
            this.treasuryName = treasuryName;
            return this;
        }

        public Builder treasuryCode(String treasuryCode) {
            this.treasuryCode = treasuryCode;
            return this;
        }

        public Builder ddoCode(String ddoCode) {
            this.ddoCode = ddoCode;
            return this;
        }

        public Builder officeName(String officeName) {
            this.officeName = officeName;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder ddoFirstName(String ddoFirstName) {
            this.ddoFirstName = ddoFirstName;
            return this;
        }

        public Builder ddoLastName(String ddoLastName) {
            this.ddoLastName = ddoLastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }


        public DdoModel build() {
            return new DdoModel(
                    district,
                    treasuryName,
                    treasuryCode,
                    ddoCode,
                    officeName,
                    title,
                    ddoFirstName,
                    ddoLastName,
                    email,
                    mobile
            );
        }
    }
}
