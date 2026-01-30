package com.burakkurucay.connex.dto.profile.personal;

import com.burakkurucay.connex.entity.profile.PersonalProfile;

public class PersonalProfileResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String profileDescription;
    private String phoneNumber;
    private String location;

    public static PersonalProfileResponse from(PersonalProfile profile) {
        PersonalProfileResponse dto = new PersonalProfileResponse();
        dto.id = profile.getId();
        dto.firstName = profile.getFirstName();
        dto.lastName = profile.getLastName();
        dto.profileDescription = profile.getProfileDescription();
        dto.phoneNumber = profile.getPhoneNumber();
        dto.location = profile.getLocation();
        return dto;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getProfileDescription() { return profileDescription; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getLocation() { return location; }
}
