package com.burakkurucay.connex.service.profile;

import com.burakkurucay.connex.dto.profile.ProfileResponse;
import com.burakkurucay.connex.entity.profile.Profile;
import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.contact.PersonalProfileContact;
import com.burakkurucay.connex.entity.profile.personal.education.PersonalProfileEducation;
import com.burakkurucay.connex.entity.profile.personal.experience.PersonalProfileExperience;
import com.burakkurucay.connex.entity.profile.personal.language.PersonalProfileLanguage;
import com.burakkurucay.connex.entity.profile.personal.project.PersonalProfileProject;
import com.burakkurucay.connex.entity.profile.personal.skill.PersonalProfileSkill;
import com.burakkurucay.connex.entity.user.AccountType;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.entity.user.UserStatus;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.repository.profile.ProfileRepository;
import com.burakkurucay.connex.repository.profile.company.CompanyProfileRepository;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileRepository;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileContactRepository;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileEducationRepository;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileExperienceRepository;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileSkillRepository;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileLanguageRepository;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileProjectRepository;
import com.burakkurucay.connex.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Central service for Profile aggregate root.
 * Manages profile lifecycle, queries, and coordinates personal/company
 * profiles.
 */
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PersonalProfileRepository personalProfileRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final PersonalProfileContactRepository contactRepository;
    private final PersonalProfileEducationRepository educationRepository;
    private final PersonalProfileExperienceRepository experienceRepository;
    private final PersonalProfileSkillRepository skillRepository;
    private final PersonalProfileLanguageRepository languageRepository;
    private final PersonalProfileProjectRepository projectRepository;
    private final UserService userService;

    public ProfileService(
            ProfileRepository profileRepository,
            PersonalProfileRepository personalProfileRepository,
            CompanyProfileRepository companyProfileRepository,
            PersonalProfileContactRepository contactRepository,
            PersonalProfileEducationRepository educationRepository,
            PersonalProfileExperienceRepository experienceRepository,
            PersonalProfileSkillRepository skillRepository,
            PersonalProfileLanguageRepository languageRepository,
            PersonalProfileProjectRepository projectRepository,
            UserService userService) {
        this.profileRepository = profileRepository;
        this.personalProfileRepository = personalProfileRepository;
        this.companyProfileRepository = companyProfileRepository;
        this.contactRepository = contactRepository;
        this.educationRepository = educationRepository;
        this.experienceRepository = experienceRepository;
        this.skillRepository = skillRepository;
        this.languageRepository = languageRepository;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    // =========================================================================
    // Profile Aggregate Root Lifecycle
    // =========================================================================

    /**
     * Get the Profile for the current authenticated user.
     */
    public Profile getMyProfile() {
        User currentUser = userService.getCurrentUser();
        return getProfileByUserId(currentUser.getId());
    }

    /**
     * Get Profile by user ID.
     */
    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(
                        "Profile not found",
                        ErrorCode.PROFILE_NOT_FOUND));
    }

    /**
     * Get Profile by profile ID.
     */
    public Profile getProfileById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException(
                        "Profile not found",
                        ErrorCode.PROFILE_NOT_FOUND));
    }

    /**
     * Create a new Profile for a user.
     */
    @Transactional
    public Profile createProfile(User user) {
        if (profileRepository.existsByUserId(user.getId())) {
            throw new BusinessException(
                    "Profile already exists",
                    ErrorCode.PROFILE_ALREADY_EXISTS);
        }
        return profileRepository.save(new Profile(user));
    }

    /**
     * Check if a profile exists for the given user ID.
     */
    public boolean existsByUserId(Long userId) {
        return profileRepository.existsByUserId(userId);
    }

    /**
     * Validate that the profile owner is active.
     */
    public void validateActiveProfile(Profile profile) {
        if (profile.getUser().getStatus() != UserStatus.ACTIVE) {
            throw new BusinessException(
                    "Profile owner is not active",
                    ErrorCode.PROFILE_NOT_FOUND);
        }
    }

    // =========================================================================
    // Profile Queries (merged from ProfileQueryService)
    // =========================================================================

    /**
     * Get the current user's full profile response with all sub-resources.
     */
    public ProfileResponse getMyProfileResponse() {
        User currentUser = userService.getCurrentUser();

        if (currentUser.getAccountType() == AccountType.PERSONAL) {
            return buildPersonalProfileResponse(getMyPersonalProfile());
        }

        if (currentUser.getAccountType() == AccountType.COMPANY) {
            return ProfileResponse.fromCompany(getMyCompanyProfile());
        }

        throw new BusinessException("Profile not found", ErrorCode.PROFILE_NOT_FOUND);
    }

    /**
     * Get a public profile by user ID.
     */
    public ProfileResponse getPublicProfileResponse(Long userId) {
        User user = userService.getUserById(userId);

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BusinessException("Profile not found", ErrorCode.PROFILE_NOT_FOUND);
        }

        if (user.getAccountType() == AccountType.PERSONAL) {
            PersonalProfile personalProfile = getPublicPersonalProfileByUserId(userId);
            return buildPersonalProfileResponse(personalProfile);
        }

        if (user.getAccountType() == AccountType.COMPANY) {
            CompanyProfile companyProfile = getPublicCompanyProfileByUserId(userId);
            return ProfileResponse.fromCompany(companyProfile);
        }

        throw new BusinessException("Profile not found", ErrorCode.PROFILE_NOT_FOUND);
    }

    private ProfileResponse buildPersonalProfileResponse(PersonalProfile profile) {
        List<PersonalProfileContact> contacts = contactRepository.findAllByProfile(profile);
        List<PersonalProfileEducation> educations = educationRepository.findAllByProfile(profile);
        List<PersonalProfileExperience> experiences = experienceRepository.findAllByProfile(profile);
        List<PersonalProfileSkill> skills = skillRepository.findAllByProfile(profile);
        List<PersonalProfileLanguage> languages = languageRepository.findAllByProfile(profile);
        List<PersonalProfileProject> projects = projectRepository.findAllByProfile(profile);

        return ProfileResponse.fromPersonal(profile, contacts, educations, experiences, skills, languages, projects);
    }

    // =========================================================================
    // Personal Profile Operations (merged from PersonalProfileService)
    // =========================================================================

    public PersonalProfile getMyPersonalProfile() {
        Profile profile = getMyProfile();
        return personalProfileRepository.findByProfileId(profile.getId())
                .orElseThrow(() -> new BusinessException(
                        "Personal profile not found",
                        ErrorCode.PROFILE_NOT_FOUND));
    }

    public PersonalProfile getPublicPersonalProfileByUserId(Long userId) {
        Profile profile = getProfileByUserId(userId);
        validateActiveProfile(profile);

        return personalProfileRepository.findByProfileId(profile.getId())
                .orElseThrow(() -> new BusinessException(
                        "Personal profile not found",
                        ErrorCode.PROFILE_NOT_FOUND));
    }

    @Transactional
    public PersonalProfile completePersonalOnboarding(String firstName, String lastName, String description) {
        User currentUser = userService.getCurrentUser();

        if (currentUser.getAccountType() != AccountType.PERSONAL) {
            throw new BusinessException("User is not a personal account", ErrorCode.AUTH_FORBIDDEN);
        }

        if (existsByUserId(currentUser.getId())) {
            throw new BusinessException("Profile already exists", ErrorCode.PROFILE_ALREADY_EXISTS);
        }

        // Create Profile aggregate root
        Profile profile = profileRepository.save(new Profile(currentUser));

        // Create PersonalProfile
        PersonalProfile personalProfile = new PersonalProfile(profile);
        personalProfile.setFirstName(firstName);
        personalProfile.setLastName(lastName);
        personalProfile.setProfileDescription(description);

        PersonalProfile savedProfile = personalProfileRepository.save(personalProfile);

        // Activate user
        userService.activateUser(currentUser.getId());

        return savedProfile;
    }

    @Transactional
    public PersonalProfile updatePersonalProfile(String firstName, String lastName, String description,
            String phoneNumber, String location) {
        PersonalProfile profile = getMyPersonalProfile();
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setProfileDescription(description);
        profile.setPhoneNumber(phoneNumber);
        profile.setLocation(location);
        return profile;
    }

    public List<PersonalProfile> searchPersonalProfiles(String query) {
        return personalProfileRepository.searchProfiles(query);
    }

    // =========================================================================
    // Company Profile Operations (merged from CompanyProfileService)
    // =========================================================================

    public CompanyProfile getMyCompanyProfile() {
        Profile profile = getMyProfile();
        return companyProfileRepository.findByProfileId(profile.getId())
                .orElseThrow(() -> new BusinessException(
                        "Company profile not found",
                        ErrorCode.PROFILE_NOT_FOUND));
    }

    public CompanyProfile getPublicCompanyProfileByUserId(Long userId) {
        Profile profile = getProfileByUserId(userId);
        validateActiveProfile(profile);

        return companyProfileRepository.findByProfileId(profile.getId())
                .orElseThrow(() -> new BusinessException(
                        "Company profile not found",
                        ErrorCode.PROFILE_NOT_FOUND));
    }

    @Transactional
    public CompanyProfile completeCompanyOnboarding(String name, String industry, String description) {
        User currentUser = userService.getCurrentUser();

        if (currentUser.getAccountType() != AccountType.COMPANY) {
            throw new BusinessException("User is not a company account", ErrorCode.AUTH_FORBIDDEN);
        }

        if (existsByUserId(currentUser.getId())) {
            throw new BusinessException("Profile already exists", ErrorCode.PROFILE_ALREADY_EXISTS);
        }

        // Create Profile aggregate root
        Profile profile = profileRepository.save(new Profile(currentUser));

        // Create CompanyProfile
        CompanyProfile companyProfile = new CompanyProfile(profile);
        companyProfile.setCompanyName(name);
        companyProfile.setIndustry(industry);
        companyProfile.setDescription(description);

        CompanyProfile savedProfile = companyProfileRepository.save(companyProfile);

        // Activate user
        userService.activateUser(currentUser.getId());

        return savedProfile;
    }

    @Transactional
    public CompanyProfile updateCompanyProfile(String companyName, String description, String industry,
            String location, String website) {
        CompanyProfile profile = getMyCompanyProfile();
        profile.setCompanyName(companyName);
        profile.setDescription(description);
        profile.setIndustry(industry);
        profile.setLocation(location);
        profile.setWebsite(website);
        return profile;
    }
}
