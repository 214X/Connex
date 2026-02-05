package com.burakkurucay.connex.controller.profile;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.dto.profile.ProfileResponse;
import com.burakkurucay.connex.dto.profile.personal.contact.CreatePersonalContactRequest;
import com.burakkurucay.connex.dto.profile.personal.contact.EditPersonalContactRequest;
import com.burakkurucay.connex.entity.profile.personal.contact.PersonalProfileContact;
import com.burakkurucay.connex.service.profile.ProfileQueryService;
import com.burakkurucay.connex.service.profile.personal.PersonalProfileContactService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileQueryService profileQueryService;
    private final PersonalProfileContactService contactService;

    public ProfileController(
        ProfileQueryService profileQueryService,
        PersonalProfileContactService contactService
    ) {
        this.profileQueryService = profileQueryService;
        this.contactService = contactService;
    }

    /* =======================
       Profile
       ======================= */

    /**
     * Public profile by userId
     */
    @GetMapping("/{userId}")
    public ApiResponse<ProfileResponse> getProfile(@PathVariable Long userId) {
        return ApiResponse.success(
            profileQueryService.getProfileByUserId(userId)
        );
    }

    /**
     * Logged-in user's own profile
     */
    @GetMapping("/me")
    public ApiResponse<ProfileResponse> getMyProfile() {
        return ApiResponse.success(
            profileQueryService.getMyProfile()
        );
    }

    /* =======================
       Contacts (sub-resource)
       ======================= */

    /**
     * Get my contacts
     */
    @GetMapping("/me/contacts")
    public ApiResponse<List<ProfileResponse.Contact>> getMyContacts() {

        List<PersonalProfileContact> contacts = contactService.getMyContacts();

        List<ProfileResponse.Contact> response = contacts.stream()
            .map(ProfileResponse.Contact::from)
            .toList();

        return ApiResponse.success(response);
    }

    /**
     * Add new contact
     */
    @PostMapping("/me/contacts")
    public ApiResponse<Void> addContact(
        @Valid @RequestBody CreatePersonalContactRequest request
    ) {
        contactService.addContact(
            request.getType(),
            request.getValue()
        );
        return ApiResponse.success(null);
    }

    /**
     * Edit existing contact (partial update)
     */
    @PatchMapping("/me/contacts/{contactId}")
    public ApiResponse<Void> updateContact(
        @PathVariable Long contactId,
        @RequestBody EditPersonalContactRequest request
    ) {
        contactService.updateContact(contactId, request);
        return ApiResponse.success(null);
    }

    /**
     * Delete contact
     */
    @DeleteMapping("/me/contacts/{contactId}")
    public ApiResponse<Void> deleteContact(
        @PathVariable Long contactId
    ) {
        contactService.deleteContact(contactId);
        return ApiResponse.success(null);
    }
}
