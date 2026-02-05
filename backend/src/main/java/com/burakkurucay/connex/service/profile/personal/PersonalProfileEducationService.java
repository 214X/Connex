package com.burakkurucay.connex.service.profile.personal;

import com.burakkurucay.connex.dto.profile.personal.education.CreatePersonalEducationRequest;
import com.burakkurucay.connex.dto.profile.personal.education.EditPersonalEducationRequest;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.education.PersonalProfileEducation;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileEducationRepository;
import com.burakkurucay.connex.service.profile.ProfileService;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalProfileEducationService {

    private final PersonalProfileEducationRepository educationRepository;
    private final ProfileService profileService;

    public PersonalProfileEducationService(
            PersonalProfileEducationRepository educationRepository,
            ProfileService profileService) {
        this.educationRepository = educationRepository;
        this.profileService = profileService;
    }

    public List<PersonalProfileEducation> getMyEducations() {
        PersonalProfile profile = profileService.getMyPersonalProfile();
        return educationRepository.findAllByProfile(profile);
    }

    public List<PersonalProfileEducation> getEducationsByProfile(PersonalProfile profile) {
        return educationRepository.findAllByProfile(profile);
    }

    @Transactional
    public PersonalProfileEducation addEducation(CreatePersonalEducationRequest request) {
        PersonalProfile profile = profileService.getMyPersonalProfile();

        PersonalProfileEducation education = new PersonalProfileEducation(
                profile,
                request.getSchoolName(),
                request.getDepartment(),
                request.getStartDate(),
                request.getEndDate());

        return educationRepository.save(education);
    }

    @Transactional
    public PersonalProfileEducation updateEducation(Long educationId, @Nonnull EditPersonalEducationRequest request) {
        PersonalProfile profile = profileService.getMyPersonalProfile();

        PersonalProfileEducation education = educationRepository
                .findByIdAndProfile(educationId, profile)
                .orElseThrow(() -> new BusinessException("Education not found", ErrorCode.PROFILE_EDUCATION_NOT_FOUND));

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

    @Transactional
    public void deleteEducation(Long educationId) {
        PersonalProfile profile = profileService.getMyPersonalProfile();

        PersonalProfileEducation education = educationRepository
                .findByIdAndProfile(educationId, profile)
                .orElseThrow(() -> new BusinessException("Education not found", ErrorCode.PROFILE_EDUCATION_NOT_FOUND));

        educationRepository.delete(education);
    }
}
