package com.burakkurucay.connex.dto.profile.personal.contact;

import com.burakkurucay.connex.entity.profile.personal.contact.ContactType;
import com.burakkurucay.connex.entity.profile.personal.contact.PersonalProfileContact;

public class PersonalProfileContactDTO {

    private Long id;
    private ContactType type;
    private String value;

    public static PersonalProfileContactDTO from(PersonalProfileContact contact) {
        PersonalProfileContactDTO dto = new PersonalProfileContactDTO();
        dto.id = contact.getId();
        dto.type = contact.getType();
        dto.value = contact.getValue();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public ContactType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
