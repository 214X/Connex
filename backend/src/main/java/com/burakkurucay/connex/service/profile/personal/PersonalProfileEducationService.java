package com.burakkurucay.connex.service.profile.personal;

import com.burakkurucay.connex.dto.profile.personal.education.CreatePersonalEducationRequest;
import com.burakkurucay.connex.dto.profile.personal.education.EditPersonalEducationRequest;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.education.PersonalProfileEducation;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileEducationRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalProfileEducationService {

    private final PersonalProfileEducationRepository educationRepository;
    private final PersonalProfileService profileService;

    public PersonalProfileEducationService(
            PersonalProfileEducationRepository educationRepository,
            PersonalProfileService profileService) {
        this.educationRepository = educationRepository;
        this.profileService = profileService;
    }

    /*
     * =======================
     * READ (owner)
     * =======================
     */

    public List<PersonalProfileEducation> getMyEducations() {
        PersonalProfile profile = profileService.getMyProfile();
        return educationRepository.findAllByProfile(profile);
    }

    /*
     * =======================
     * READ (public / CV view)
     * =======================
     */

    public List<PersonalProfileEducation> getEducationsByProfile(PersonalProfile profile) {
        return educationRepository.findAllByProfile(profile);
    }

    /*
     * =======================
     * CREATE
     * =======================
     */

    @Transactional
    public PersonalProfileEducation addEducation(CreatePersonalEducationRequest request) {
        PersonalProfile profile = profileService.getMyProfile();

        PersonalProfileEducation education = new PersonalProfileEducation(
                profile,
                request.getSchoolName(),
                request.getDepartment(),
                request.getStartDate(),
                request.getEndDate());

        return educationRepository.save(education);
    }

    /*
     * =======================
     * UPDATE
     * =======================
     */

    @Transactional
    public PersonalProfileEducation updateEducation(
            Long educationId,
            @Nonnull EditPersonalEducationRequest request) {
        PersonalProfile profile = profileService.getMyProfile();

        PersonalProfileEducation education = educationRepository
                .findByIdAndProfile(educationId, profile)
                .orElseThrow(() -> new BusinessException(
                        "Education not found",
                        ErrorCode.PROFILE_EDUCATION_NOT_FOUND));

        if (request.getSchoolName() != null) {
            education.setSchoolName(request.getSchoolName());
        }

        if (request.getDepartment() != null) {
            education.setDepartment(request.getDepartment());
        }

        if (request.getStartDate() != null) {
            education.setStartDate(request.getStartDate());
        }

        if (request.getEndDate() != null) {
            education.setEndDate(request.getEndDate());
        }

        return educationRepository.save(education);
    }

    /*
     * =======================
     * DELETE
     * =======================
     */

    @Transactional
    public void deleteEducation(Long educationId) {
        PersonalProfile profile = profileService.getMyProfile();

        PersonalProfileEducation education = educationRepository
                .findByIdAndProfile(educationId, profile)
                .orElseThrow(() -> new BusinessException(
                        "Education not found",
                        ErrorCode.PROFILE_EDUCATION_NOT_FOUND));

        educationRepository.delete(education);
    }
}
