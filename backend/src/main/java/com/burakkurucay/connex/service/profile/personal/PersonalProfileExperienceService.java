package com.burakkurucay.connex.service.profile.personal;

import com.burakkurucay.connex.dto.profile.personal.experience.CreatePersonalExperienceRequest;
import com.burakkurucay.connex.dto.profile.personal.experience.EditPersonalExperienceRequest;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.experience.PersonalProfileExperience;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileExperienceRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalProfileExperienceService {

    private final PersonalProfileExperienceRepository experienceRepository;
    private final PersonalProfileService profileService;

    public PersonalProfileExperienceService(
            PersonalProfileExperienceRepository experienceRepository,
            PersonalProfileService profileService) {
        this.experienceRepository = experienceRepository;
        this.profileService = profileService;
    }

    /*
     * =======================
     * READ (owner)
     * =======================
     */

    public List<PersonalProfileExperience> getMyExperiences() {
        PersonalProfile profile = profileService.getMyProfile();
        return experienceRepository.findAllByProfile(profile);
    }

    /*
     * =======================
     * READ (public / CV view)
     * =======================
     */

    public List<PersonalProfileExperience> getExperiencesByProfile(PersonalProfile profile) {
        return experienceRepository.findAllByProfile(profile);
    }

    /*
     * =======================
     * CREATE
     * =======================
     */

    @Transactional
    public PersonalProfileExperience addExperience(CreatePersonalExperienceRequest request) {
        PersonalProfile profile = profileService.getMyProfile();

        PersonalProfileExperience experience = new PersonalProfileExperience(
                profile,
                request.getTitle(),
                request.getOrganization(),
                request.getStartDate(),
                request.getEndDate(),
                request.getDescription());

        return experienceRepository.save(experience);
    }

    /*
     * =======================
     * UPDATE
     * =======================
     */

    @Transactional
    public PersonalProfileExperience updateExperience(
            Long experienceId,
            @Nonnull EditPersonalExperienceRequest request) {
        PersonalProfile profile = profileService.getMyProfile();

        PersonalProfileExperience experience = experienceRepository
                .findByIdAndProfile(experienceId, profile)
                .orElseThrow(() -> new BusinessException(
                        "Experience not found",
                        ErrorCode.PROFILE_EXPERIENCE_NOT_FOUND));

        if (request.getTitle() != null) {
            experience.setTitle(request.getTitle());
        }

        if (request.getOrganization() != null) {
            experience.setOrganization(request.getOrganization());
        }

        if (request.getStartDate() != null) {
            experience.setStartDate(request.getStartDate());
        }

        if (request.getEndDate() != null) {
            experience.setEndDate(request.getEndDate());
        }

        if (request.getDescription() != null) {
            experience.setDescription(request.getDescription());
        }

        return experienceRepository.save(experience);
    }

    /*
     * =======================
     * DELETE
     * =======================
     */

    @Transactional
    public void deleteExperience(Long experienceId) {
        PersonalProfile profile = profileService.getMyProfile();

        PersonalProfileExperience experience = experienceRepository
                .findByIdAndProfile(experienceId, profile)
                .orElseThrow(() -> new BusinessException(
                        "Experience not found",
                        ErrorCode.PROFILE_EXPERIENCE_NOT_FOUND));

        experienceRepository.delete(experience);
    }
}
