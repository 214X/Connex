package com.burakkurucay.connex.dto.profile.personal;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;

public class PersonalProfileResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String profileDescription;
    private String phoneNumber;
    private String location;
    private String cvFileName;
    private java.util.List<com.burakkurucay.connex.dto.profile.personal.contact.PersonalProfileContactDTO> contacts;

    public static PersonalProfileResponse from(PersonalProfile profile) {
        return from(profile, java.util.Collections.emptyList());
    }

    public static PersonalProfileResponse from(
            PersonalProfile profile,
            java.util.List<com.burakkurucay.connex.entity.profile.personal.contact.PersonalProfileContact> contactEntities) {
        PersonalProfileResponse dto = new PersonalProfileResponse();
        dto.id = profile.getId();
        dto.firstName = profile.getFirstName();
        dto.lastName = profile.getLastName();
        dto.profileDescription = profile.getProfileDescription();
        dto.phoneNumber = profile.getPhoneNumber();
        dto.location = profile.getLocation();
        if (profile.getCv() != null) {
            dto.cvFileName = profile.getCv().getOriginalFileName();
        }

        dto.contacts = contactEntities.stream()
                .map(com.burakkurucay.connex.dto.profile.personal.contact.PersonalProfileContactDTO::from)
                .toList();
        return dto;
    }

    public Long getId() {
        return id;
    }

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

    public String getCvFileName() {
        return cvFileName;
    }

    public java.util.List<com.burakkurucay.connex.dto.profile.personal.contact.PersonalProfileContactDTO> getContacts() {
        return contacts;
    }
}
