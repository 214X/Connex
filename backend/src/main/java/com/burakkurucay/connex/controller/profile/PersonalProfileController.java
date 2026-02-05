package com.burakkurucay.connex.controller.profile;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.dto.profile.personal.PersonalProfileEditRequest;
import com.burakkurucay.connex.dto.profile.personal.PersonalProfileRequest;
import com.burakkurucay.connex.dto.profile.personal.PersonalProfileResponse;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.service.profile.personal.PersonalProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/personal")
public class PersonalProfileController {

    private final PersonalProfileService profileService;
    private final com.burakkurucay.connex.service.profile.personal.PersonalProfileContactService contactService;

    public PersonalProfileController(
            PersonalProfileService profileService,
            com.burakkurucay.connex.service.profile.personal.PersonalProfileContactService contactService) {
        this.profileService = profileService;
        this.contactService = contactService;
    }

    /**
     * Create personal profile for current user
     */
    @PostMapping("/me")
    public PersonalProfileResponse createMyProfile() {
        PersonalProfile profile = profileService.createMyProfile();
        return PersonalProfileResponse.from(profile, java.util.Collections.emptyList());
    }

    /**
     * Get current user's personal profile
     */
    @GetMapping("/me")
    public PersonalProfileResponse getMyProfile() {
        PersonalProfile profile = profileService.getMyProfile();
        return PersonalProfileResponse.from(
                profile,
                contactService.getMyContacts());
    }

    /**
     * Update current user's personal profile
     */
    @PutMapping("/me")
    public PersonalProfileResponse updateMyProfile(
            @RequestBody PersonalProfileRequest request) {
        PersonalProfile updated = profileService.updateMyProfile(
                request.getFirstName(),
                request.getLastName(),
                request.getProfileDescription(),
                request.getPhoneNumber(),
                request.getLocation());

        return PersonalProfileResponse.from(
                updated,
                contactService.getMyContacts());
    }

    @PatchMapping("/me")
    public ApiResponse<Void> updateMyProfile(
            @Valid @RequestBody PersonalProfileEditRequest request) {
        profileService.updateMyProfile(request);
        return ApiResponse.success(null);
    }

    /**
     * Get public personal profile by profile id
     */
    @GetMapping("/{profileId}")
    public PersonalProfileResponse getPublicProfile(
            @PathVariable Long profileId) {
        PersonalProfile profile = profileService.getPublicProfile(profileId);
        return PersonalProfileResponse.from(
                profile,
                contactService.getContactsByProfile(profile));
    }
}
