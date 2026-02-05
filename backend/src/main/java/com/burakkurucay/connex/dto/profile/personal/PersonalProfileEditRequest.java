package com.burakkurucay.connex.dto.profile.personal;

import jakarta.validation.constraints.NotBlank;

public class PersonalProfileEditRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String profileDescription;
    private String phoneNumber;
    private String location;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
