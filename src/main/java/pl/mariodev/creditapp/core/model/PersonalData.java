package pl.mariodev.creditapp.core.model;

import pl.mariodev.creditapp.core.Constants;
import pl.mariodev.creditapp.core.annotation.NotNull;
import pl.mariodev.creditapp.core.annotation.Regex;

public class PersonalData {
    @NotNull
    @Regex(Constants.NAME_REGEX)
    private final String name;
    @NotNull
    @Regex(Constants.LAST_NAME_REGEX)
    private final String lastName;
    @NotNull
    @Regex(Constants.LAST_NAME_REGEX)
    private final String mothersMaidenName;
    @NotNull
    private final MaritalStatus maritalStatus;
    @NotNull
    private final Education education;


    private PersonalData(String name, String lastName, String mothersMaidenName, MaritalStatus maritalStatus, Education education) {
        this.name = name;
        this.lastName = lastName;
        this.mothersMaidenName = mothersMaidenName;
        this.maritalStatus = maritalStatus;
        this.education = education;

    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMothersMaidenName() {
        return mothersMaidenName;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public Education getEducation() {
        return education;
    }



    public static class Builder {
        private String name;
        private String lastName;
        private String mothersMaidenName;
        private MaritalStatus maritalStatus;
        private Education education;

//        private Builder(){}

        public static Builder create() {
            return new Builder();
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder withMothersMaidenName(String mothersMaidenName){
            this.mothersMaidenName = mothersMaidenName;
            return this;
        }

        public Builder withMartialStatus(MaritalStatus maritalStatus){
            this.maritalStatus = maritalStatus;
            return this;
        }

        public Builder withEducation(Education education){
            this.education = education;
            return this;
        }

        public PersonalData build() {
            return new PersonalData(name, lastName, mothersMaidenName, maritalStatus, education);
        }
    }
}
