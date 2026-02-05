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
    private final com.burakkurucay.connex.service.profile.personal.PersonalProfileEducationService educationService;
    private final com.burakkurucay.connex.service.profile.personal.PersonalProfileExperienceService experienceService;

    public ProfileController(
            ProfileQueryService profileQueryService,
            PersonalProfileContactService contactService,
            com.burakkurucay.connex.service.profile.personal.PersonalProfileEducationService educationService,
            com.burakkurucay.connex.service.profile.personal.PersonalProfileExperienceService experienceService) {
        this.profileQueryService = profileQueryService;
        this.contactService = contactService;
        this.educationService = educationService;
        this.experienceService = experienceService;
    }

    /*
     * =======================
     * Profile
     * =======================
     */

    /**
     * Public profile by userId
     */
    @GetMapping("/{userId}")
    public ApiResponse<ProfileResponse> getProfile(@PathVariable Long userId) {
        return ApiResponse.success(
                profileQueryService.getProfileByUserId(userId));
    }

    /**
     * Logged-in user's own profile
     */
    @GetMapping("/me")
    public ApiResponse<ProfileResponse> getMyProfile() {
        return ApiResponse.success(
                profileQueryService.getMyProfile());
    }

    /*
     * =======================
     * Contacts (sub-resource)
     * =======================
     */

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
            @Valid @RequestBody CreatePersonalContactRequest request) {
        contactService.addContact(
                request.getType(),
                request.getValue());
        return ApiResponse.success(null);
    }

    /**
     * Edit existing contact (partial update)
     */
    @PatchMapping("/me/contacts/{contactId}")
    public ApiResponse<Void> updateContact(
            @PathVariable Long contactId,
            @RequestBody EditPersonalContactRequest request) {
        contactService.updateContact(contactId, request);
        return ApiResponse.success(null);
    }

    /**
     * Delete contact
     */
    @DeleteMapping("/me/contacts/{contactId}")
    public ApiResponse<Void> deleteContact(
            @PathVariable Long contactId) {
        contactService.deleteContact(contactId);
        return ApiResponse.success(null);
    }
    /*
     * =======================
     * Education (sub-resource)
     * =======================
     */

    /**
     * Get my educations
     */
    @GetMapping("/me/educations")
    public ApiResponse<List<com.burakkurucay.connex.dto.profile.personal.education.EducationResponse>> getMyEducations() {
        List<com.burakkurucay.connex.entity.profile.personal.education.PersonalProfileEducation> educations = educationService
                .getMyEducations();

        List<com.burakkurucay.connex.dto.profile.personal.education.EducationResponse> response = educations.stream()
                .map(com.burakkurucay.connex.dto.profile.personal.education.EducationResponse::from)
                .toList();

        return ApiResponse.success(response);
    }

    /**
     * Add new education
     */
    @PostMapping("/me/educations")
    public ApiResponse<Void> addEducation(
            @Valid @RequestBody com.burakkurucay.connex.dto.profile.personal.education.CreatePersonalEducationRequest request) {
        educationService.addEducation(request);
        return ApiResponse.success(null);
    }

    /**
     * Edit existing education
     */
    @PatchMapping("/me/educations/{educationId}")
    public ApiResponse<Void> updateEducation(
            @PathVariable Long educationId,
            @RequestBody com.burakkurucay.connex.dto.profile.personal.education.EditPersonalEducationRequest request) {
        educationService.updateEducation(educationId, request);
        return ApiResponse.success(null);
    }

    /**
     * Delete education
     */
    @DeleteMapping("/me/educations/{educationId}")
    public ApiResponse<Void> deleteEducation(
            @PathVariable Long educationId) {
        educationService.deleteEducation(educationId);
        return ApiResponse.success(null);
    }
    /*
     * =======================
     * Experience (sub-resource)
     * =======================
     */

    /**
     * Get my experiences
     */
    @GetMapping("/me/experiences")
    public ApiResponse<List<com.burakkurucay.connex.dto.profile.personal.experience.ExperienceResponse>> getMyExperiences() {
        List<com.burakkurucay.connex.entity.profile.personal.experience.PersonalProfileExperience> experiences = experienceService
                .getMyExperiences();

        List<com.burakkurucay.connex.dto.profile.personal.experience.ExperienceResponse> response = experiences.stream()
                .map(com.burakkurucay.connex.dto.profile.personal.experience.ExperienceResponse::from)
                .toList();

        return ApiResponse.success(response);
    }

    /**
     * Add new experience
     */
    @PostMapping("/me/experiences")
    public ApiResponse<Void> addExperience(
            @Valid @RequestBody com.burakkurucay.connex.dto.profile.personal.experience.CreatePersonalExperienceRequest request) {
        experienceService.addExperience(request);
        return ApiResponse.success(null);
    }

    /**
     * Edit existing experience
     */
    @PatchMapping("/me/experiences/{experienceId}")
    public ApiResponse<Void> updateExperience(
            @PathVariable Long experienceId,
            @RequestBody com.burakkurucay.connex.dto.profile.personal.experience.EditPersonalExperienceRequest request) {
        experienceService.updateExperience(experienceId, request);
        return ApiResponse.success(null);
    }

    /**
     * Delete experience
     */
    @DeleteMapping("/me/experiences/{experienceId}")
    public ApiResponse<Void> deleteExperience(
            @PathVariable Long experienceId) {
        experienceService.deleteExperience(experienceId);
        return ApiResponse.success(null);
    }
}
