package pl.mariodev.creditapp.core.model;

import pl.mariodev.creditapp.core.Constants;
import pl.mariodev.creditapp.core.annotation.NotNull;
import pl.mariodev.creditapp.core.annotation.Regex;

import java.util.Optional;

public class ContactData {
    @NotNull
    @Regex(Constants.EMAIL_REGEX)
    private String email;
    @NotNull
    @Regex(Constants.PHONE_REGEX)
    private String phoneNumber;
    @NotNull
    private Address homeAddress;
    @NotNull
    private Optional<Address> correspondenceAddress;

    private ContactData(String email, String phoneNumber, Address homeAddress, Optional<Address> correspondenceAddress) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.correspondenceAddress = correspondenceAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public Optional<Address> getCorrespondenceAddress() {
        return correspondenceAddress;
    }

    public static class Builder {
        private String email;
        private String phoneNumber;
        private Address homeAddress;
        private Address correspondenceAddress;

//        private Builder(){}

        public static Builder create() {
            return new Builder();
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withHomeAddress(Address homeAddress) {
            this.homeAddress = homeAddress;
            return this;
        }

        public Builder withCorrespondenceAddress(Address correspondenceAddress) {
            this.correspondenceAddress = correspondenceAddress;
            return this;
        }

        public ContactData build() {
            Optional<Address> correspondenceAddress = this.homeAddress.equals(this.correspondenceAddress) ?
                    Optional.empty() : Optional.of(this.correspondenceAddress);
            return new ContactData(this.email, this.phoneNumber, this.homeAddress, correspondenceAddress);
         }
    }
}
