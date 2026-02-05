package com.burakkurucay.connex.service.profile.personal;

import com.burakkurucay.connex.dto.profile.personal.contact.EditPersonalContactRequest;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.contact.ContactType;
import com.burakkurucay.connex.entity.profile.personal.contact.PersonalProfileContact;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileContactRepository;
import com.burakkurucay.connex.service.profile.ProfileService;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalProfileContactService {

    private final PersonalProfileContactRepository contactRepository;
    private final ProfileService profileService;

    public PersonalProfileContactService(
            PersonalProfileContactRepository contactRepository,
            ProfileService profileService) {
        this.contactRepository = contactRepository;
        this.profileService = profileService;
    }

    // READ (owner)

    public List<PersonalProfileContact> getMyContacts() {
        PersonalProfile profile = profileService.getMyPersonalProfile();
        return contactRepository.findAllByProfile(profile);
    }

    public List<PersonalProfileContact> getMyContactsByType(ContactType type) {
        PersonalProfile profile = profileService.getMyPersonalProfile();
        return contactRepository.findAllByProfileAndType(profile, type);
    }

    // READ (public)

    public List<PersonalProfileContact> getContactsByProfile(PersonalProfile profile) {
        return contactRepository.findAllByProfile(profile);
    }

    // CREATE

    @Transactional
    public PersonalProfileContact addContact(ContactType type, String value) {
        PersonalProfile profile = profileService.getMyPersonalProfile();
        PersonalProfileContact contact = new PersonalProfileContact(profile, type, value);
        return contactRepository.save(contact);
    }

    // UPDATE

    @Transactional
    public PersonalProfileContact updateContact(Long contactId, @Nonnull EditPersonalContactRequest request) {
        PersonalProfile profile = profileService.getMyPersonalProfile();

        PersonalProfileContact contact = contactRepository
                .findByIdAndProfile(contactId, profile)
                .orElseThrow(() -> new BusinessException("Contact not found", ErrorCode.PROFILE_CONTACT_NOT_FOUND));

        if (request.getType() != null) {
            contact.setType(request.getType());
        }
        if (request.getValue() != null && !request.getValue().isBlank()) {
            contact.setValue(request.getValue());
        }

        return contact;
    }

    // DELETE

    @Transactional
    public void deleteContact(Long contactId) {
        PersonalProfile profile = profileService.getMyPersonalProfile();

        PersonalProfileContact contact = contactRepository
                .findByIdAndProfile(contactId, profile)
                .orElseThrow(() -> new BusinessException("Contact not found", ErrorCode.PROFILE_CONTACT_NOT_FOUND));

        contactRepository.delete(contact);
    }
}
