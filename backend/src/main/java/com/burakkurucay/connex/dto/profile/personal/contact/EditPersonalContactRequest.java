package com.burakkurucay.connex.dto.profile.personal.contact;

import com.burakkurucay.connex.entity.profile.personal.contact.ContactType;

public class EditPersonalContactRequest {

    private ContactType type;
    private String value;

    public ContactType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
