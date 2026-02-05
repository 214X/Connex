package com.burakkurucay.connex.dto.profile.personal.contact;

import com.burakkurucay.connex.entity.profile.personal.contact.ContactType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePersonalContactRequest {

    @NotNull
    private ContactType type;

    @NotBlank
    private String value;

    public ContactType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
