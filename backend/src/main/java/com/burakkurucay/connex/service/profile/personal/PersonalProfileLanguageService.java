package com.burakkurucay.connex.service.profile.personal;

import com.burakkurucay.connex.dto.profile.personal.language.CreatePersonalLanguageRequest;
import com.burakkurucay.connex.dto.profile.personal.language.EditPersonalLanguageRequest;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.language.PersonalProfileLanguage;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileLanguageRepository;
import com.burakkurucay.connex.service.profile.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class PersonalProfileLanguageService {

    private final PersonalProfileLanguageRepository languageRepository;
    private final ProfileService profileService;
    private static final Set<String> ALLOWED_LEVELS = Set.of("A1", "A2", "B1", "B2", "C1", "C2", "Native");

    public PersonalProfileLanguageService(
            PersonalProfileLanguageRepository languageRepository,
            ProfileService profileService) {
        this.languageRepository = languageRepository;
        this.profileService = profileService;
    }

    public List<PersonalProfileLanguage> getMyLanguages() {
        PersonalProfile profile = profileService.getMyPersonalProfile();
        return languageRepository.findAllByProfile(profile);
    }

    public List<PersonalProfileLanguage> getLanguagesByProfile(PersonalProfile profile) {
        return languageRepository.findAllByProfile(profile);
    }

    @Transactional
    public PersonalProfileLanguage addLanguage(CreatePersonalLanguageRequest request) {
        validateLevel(request.getLevel());

        PersonalProfile profile = profileService.getMyPersonalProfile();

        PersonalProfileLanguage language = PersonalProfileLanguage.builder()
                .profile(profile)
                .language(request.getLanguage())
                .level(request.getLevel())
                .build();

        return languageRepository.save(language);
    }

    @Transactional
    public PersonalProfileLanguage updateLanguage(Long languageId, EditPersonalLanguageRequest request) {
        PersonalProfile profile = profileService.getMyPersonalProfile();

        PersonalProfileLanguage language = languageRepository.findByIdAndProfile(languageId, profile)
                .orElseThrow(() -> new BusinessException("Language not found", ErrorCode.PROFILE_LANGUAGE_NOT_FOUND));

        if (request.getLanguage() != null) {
            language.setLanguage(request.getLanguage());
        }
        if (request.getLevel() != null) {
            validateLevel(request.getLevel());
            language.setLevel(request.getLevel());
        }

        return languageRepository.save(language);
    }

    @Transactional
    public void deleteLanguage(Long languageId) {
        PersonalProfile profile = profileService.getMyPersonalProfile();

        PersonalProfileLanguage language = languageRepository.findByIdAndProfile(languageId, profile)
                .orElseThrow(() -> new BusinessException("Language not found", ErrorCode.PROFILE_LANGUAGE_NOT_FOUND));

        languageRepository.delete(language);
    }

    private void validateLevel(String level) {
        if (!ALLOWED_LEVELS.contains(level)) {
            throw new BusinessException("Invalid language level: " + level, ErrorCode.VALIDATION_ERROR);
        }
    }
}
