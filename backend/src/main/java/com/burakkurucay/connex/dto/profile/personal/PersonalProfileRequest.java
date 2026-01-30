package com.burakkurucay.connex.dto.profile.personal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class PersonalProfileRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name can be at most 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name can be at most 100 characters")
    private String lastName;

    @Size(max = 5000, message = "Profile description can be at most 5000 characters")
    private String profileDescription;

    @Pattern(
        regexp = "^$|^[0-9+()\\-\\s]{7,30}$",
        message = "Phone number format is invalid"
    )
    private String phoneNumber;

    @Size(max = 255, message = "Location can be at most 255 characters")
    private String location;

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getProfileDescription() { return profileDescription; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getLocation() { return location; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setProfileDescription(String profileDescription) { this.profileDescription = profileDescription; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setLocation(String location) { this.location = location; }
}
