package com.burakkurucay.connex.controller.profile;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.dto.profile.ProfileResponse;
import com.burakkurucay.connex.dto.profile.personal.PersonalProfileEditRequest;
import com.burakkurucay.connex.dto.profile.personal.PersonalProfileRequest;
import com.burakkurucay.connex.dto.profile.personal.PersonalProfileResponse;
import com.burakkurucay.connex.dto.profile.personal.contact.CreatePersonalContactRequest;
import com.burakkurucay.connex.dto.profile.personal.contact.EditPersonalContactRequest;
import com.burakkurucay.connex.dto.profile.personal.education.CreatePersonalEducationRequest;
import com.burakkurucay.connex.dto.profile.personal.education.EditPersonalEducationRequest;
import com.burakkurucay.connex.dto.profile.personal.education.EducationResponse;
import com.burakkurucay.connex.dto.profile.personal.experience.CreatePersonalExperienceRequest;
import com.burakkurucay.connex.dto.profile.personal.experience.EditPersonalExperienceRequest;
import com.burakkurucay.connex.dto.profile.personal.experience.ExperienceResponse;
import com.burakkurucay.connex.dto.profile.personal.language.CreatePersonalLanguageRequest;
import com.burakkurucay.connex.dto.profile.personal.language.EditPersonalLanguageRequest;
import com.burakkurucay.connex.dto.profile.personal.language.LanguageResponse;
import com.burakkurucay.connex.dto.profile.personal.project.CreatePersonalProjectRequest;
import com.burakkurucay.connex.dto.profile.personal.project.EditPersonalProjectRequest;
import com.burakkurucay.connex.dto.profile.personal.project.ProjectResponse;
import com.burakkurucay.connex.dto.profile.personal.skill.CreatePersonalSkillRequest;
import com.burakkurucay.connex.dto.profile.personal.skill.EditPersonalSkillRequest;
import com.burakkurucay.connex.dto.profile.personal.skill.SkillResponse;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.service.profile.ProfileService;
import com.burakkurucay.connex.service.profile.personal.PersonalProfileContactService;
import com.burakkurucay.connex.service.profile.personal.PersonalProfileEducationService;
import com.burakkurucay.connex.service.profile.personal.PersonalProfileExperienceService;
import com.burakkurucay.connex.service.profile.personal.PersonalProfileLanguageService;
import com.burakkurucay.connex.service.profile.personal.PersonalProfileProjectService;
import com.burakkurucay.connex.service.profile.personal.PersonalProfileSkillService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for the entire PersonalProfile domain.
 * Owns all personal-specific functionality including subdomains.
 */
@RestController
@RequestMapping("/api/profiles/personal")
public class PersonalProfileController {

    private final ProfileService profileService;
    private final PersonalProfileContactService contactService;
    private final PersonalProfileEducationService educationService;
    private final PersonalProfileExperienceService experienceService;
    private final PersonalProfileSkillService skillService;
    private final PersonalProfileLanguageService languageService;
    private final PersonalProfileProjectService projectService;

    public PersonalProfileController(
            ProfileService profileService,
            PersonalProfileContactService contactService,
            PersonalProfileEducationService educationService,
            PersonalProfileExperienceService experienceService,
            PersonalProfileSkillService skillService,
            PersonalProfileLanguageService languageService,
            PersonalProfileProjectService projectService) {
        this.profileService = profileService;
        this.contactService = contactService;
        this.educationService = educationService;
        this.experienceService = experienceService;
        this.skillService = skillService;
        this.languageService = languageService;
        this.projectService = projectService;
    }

    // =========================================================================
    // Core Personal Profile
    // =========================================================================

    @GetMapping("/me")
    public PersonalProfileResponse getMyProfile() {
        PersonalProfile profile = profileService.getMyPersonalProfile();
        return PersonalProfileResponse.from(profile, contactService.getMyContacts());
    }

    @PutMapping("/me")
    public PersonalProfileResponse updateMyProfile(@RequestBody PersonalProfileRequest request) {
        PersonalProfile updated = profileService.updatePersonalProfile(
                request.getFirstName(),
                request.getLastName(),
                request.getProfileDescription(),
                request.getPhoneNumber(),
                request.getLocation());
        return PersonalProfileResponse.from(updated, contactService.getMyContacts());
    }

    @PatchMapping("/me")
    public ApiResponse<Void> patchMyProfile(@Valid @RequestBody PersonalProfileEditRequest request) {
        profileService.updatePersonalProfile(
                request.getFirstName(),
                request.getLastName(),
                request.getProfileDescription(),
                request.getPhoneNumber(),
                request.getLocation());
        return ApiResponse.success(null);
    }

    @GetMapping("/{userId}")
    public PersonalProfileResponse getPublicProfile(@PathVariable Long userId) {
        PersonalProfile profile = profileService.getPublicPersonalProfileByUserId(userId);
        return PersonalProfileResponse.from(profile, contactService.getContactsByProfile(profile));
    }

    @GetMapping("/search")
    public ApiResponse<List<PersonalProfileResponse>> searchProfiles(@RequestParam(defaultValue = "") String query) {
        List<PersonalProfile> profiles = profileService.searchPersonalProfiles(query);
        return ApiResponse.success(profiles.stream()
                .map(PersonalProfileResponse::from)
                .toList());
    }

    // =========================================================================
    // Contacts
    // =========================================================================

    @GetMapping("/me/contacts")
    public ApiResponse<List<ProfileResponse.Contact>> getMyContacts() {
        return ApiResponse.success(contactService.getMyContacts().stream()
                .map(ProfileResponse.Contact::from)
                .toList());
    }

    @PostMapping("/me/contacts")
    public ApiResponse<Void> addContact(@Valid @RequestBody CreatePersonalContactRequest request) {
        contactService.addContact(request.getType(), request.getValue());
        return ApiResponse.success(null);
    }

    @PatchMapping("/me/contacts/{contactId}")
    public ApiResponse<Void> updateContact(@PathVariable Long contactId,
            @RequestBody EditPersonalContactRequest request) {
        contactService.updateContact(contactId, request);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/me/contacts/{contactId}")
    public ApiResponse<Void> deleteContact(@PathVariable Long contactId) {
        contactService.deleteContact(contactId);
        return ApiResponse.success(null);
    }

    // =========================================================================
    // Education
    // =========================================================================

    @GetMapping("/me/educations")
    public ApiResponse<List<EducationResponse>> getMyEducations() {
        return ApiResponse.success(educationService.getMyEducations().stream()
                .map(EducationResponse::from)
                .toList());
    }

    @PostMapping("/me/educations")
    public ApiResponse<Void> addEducation(@Valid @RequestBody CreatePersonalEducationRequest request) {
        educationService.addEducation(request);
        return ApiResponse.success(null);
    }

    @PatchMapping("/me/educations/{educationId}")
    public ApiResponse<Void> updateEducation(@PathVariable Long educationId,
            @RequestBody EditPersonalEducationRequest request) {
        educationService.updateEducation(educationId, request);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/me/educations/{educationId}")
    public ApiResponse<Void> deleteEducation(@PathVariable Long educationId) {
        educationService.deleteEducation(educationId);
        return ApiResponse.success(null);
    }

    // =========================================================================
    // Experience
    // =========================================================================

    @GetMapping("/me/experiences")
    public ApiResponse<List<ExperienceResponse>> getMyExperiences() {
        return ApiResponse.success(experienceService.getMyExperiences().stream()
                .map(ExperienceResponse::from)
                .toList());
    }

    @PostMapping("/me/experiences")
    public ApiResponse<Void> addExperience(@Valid @RequestBody CreatePersonalExperienceRequest request) {
        experienceService.addExperience(request);
        return ApiResponse.success(null);
    }

    @PatchMapping("/me/experiences/{experienceId}")
    public ApiResponse<Void> updateExperience(@PathVariable Long experienceId,
            @RequestBody EditPersonalExperienceRequest request) {
        experienceService.updateExperience(experienceId, request);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/me/experiences/{experienceId}")
    public ApiResponse<Void> deleteExperience(@PathVariable Long experienceId) {
        experienceService.deleteExperience(experienceId);
        return ApiResponse.success(null);
    }

    // =========================================================================
    // Skills
    // =========================================================================

    @GetMapping("/me/skills")
    public ApiResponse<List<SkillResponse>> getMySkills() {
        return ApiResponse.success(skillService.getMySkills().stream()
                .map(SkillResponse::from)
                .toList());
    }

    @PostMapping("/me/skills")
    public ApiResponse<Void> addSkill(@Valid @RequestBody CreatePersonalSkillRequest request) {
        skillService.addSkill(request);
        return ApiResponse.success(null);
    }

    @PatchMapping("/me/skills/{skillId}")
    public ApiResponse<Void> updateSkill(@PathVariable Long skillId, @RequestBody EditPersonalSkillRequest request) {
        skillService.updateSkill(skillId, request);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/me/skills/{skillId}")
    public ApiResponse<Void> deleteSkill(@PathVariable Long skillId) {
        skillService.deleteSkill(skillId);
        return ApiResponse.success(null);
    }

    // =========================================================================
    // Languages
    // =========================================================================

    @GetMapping("/me/languages")
    public ApiResponse<List<LanguageResponse>> getMyLanguages() {
        return ApiResponse.success(languageService.getMyLanguages().stream()
                .map(LanguageResponse::from)
                .toList());
    }

    @PostMapping("/me/languages")
    public ApiResponse<LanguageResponse> addLanguage(@RequestBody @Valid CreatePersonalLanguageRequest request) {
        return ApiResponse.success(LanguageResponse.from(languageService.addLanguage(request)));
    }

    @PatchMapping("/me/languages/{languageId}")
    public ApiResponse<LanguageResponse> updateLanguage(@PathVariable Long languageId,
            @RequestBody @Valid EditPersonalLanguageRequest request) {
        return ApiResponse.success(LanguageResponse.from(languageService.updateLanguage(languageId, request)));
    }

    @DeleteMapping("/me/languages/{languageId}")
    public ApiResponse<Void> deleteLanguage(@PathVariable Long languageId) {
        languageService.deleteLanguage(languageId);
        return ApiResponse.success(null);
    }

    // =========================================================================
    // Projects
    // =========================================================================

    @GetMapping("/me/projects")
    public ApiResponse<List<ProjectResponse>> getMyProjects() {
        return ApiResponse.success(projectService.getMyProjects().stream()
                .map(ProjectResponse::from)
                .toList());
    }

    @PostMapping("/me/projects")
    public ApiResponse<ProjectResponse> addProject(@RequestBody @Valid CreatePersonalProjectRequest request) {
        return ApiResponse.success(ProjectResponse.from(projectService.addProject(request)));
    }

    @PatchMapping("/me/projects/{projectId}")
    public ApiResponse<ProjectResponse> updateProject(@PathVariable Long projectId,
            @RequestBody @Valid EditPersonalProjectRequest request) {
        return ApiResponse.success(ProjectResponse.from(projectService.updateProject(projectId, request)));
    }

    @DeleteMapping("/me/projects/{projectId}")
    public ApiResponse<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ApiResponse.success(null);
    }
}
