package com.burakkurucay.connex.service.profile.personal;

import com.burakkurucay.connex.dto.profile.personal.skill.CreatePersonalSkillRequest;
import com.burakkurucay.connex.dto.profile.personal.skill.EditPersonalSkillRequest;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.skill.PersonalProfileSkill;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileSkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonalProfileSkillService {

    private final PersonalProfileSkillRepository skillRepository;
    private final PersonalProfileService personalProfileService;

    public PersonalProfileSkillService(PersonalProfileSkillRepository skillRepository,
            PersonalProfileService personalProfileService) {
        this.skillRepository = skillRepository;
        this.personalProfileService = personalProfileService;
    }

    public List<PersonalProfileSkill> getMySkills() {
        PersonalProfile profile = personalProfileService.getMyProfile();
        return skillRepository.findAllByProfile(profile);
    }

    public List<PersonalProfileSkill> getSkillsByProfile(PersonalProfile profile) {
        return skillRepository.findAllByProfile(profile);
    }

    @Transactional
    public PersonalProfileSkill addSkill(CreatePersonalSkillRequest request) {
        PersonalProfile profile = personalProfileService.getMyProfile();

        PersonalProfileSkill skill = PersonalProfileSkill.builder()
                .profile(profile)
                .name(request.getName())
                .description(request.getDescription())
                .level(request.getLevel())
                .build();

        return skillRepository.save(skill);
    }

    @Transactional
    public PersonalProfileSkill updateSkill(Long skillId, EditPersonalSkillRequest request) {
        PersonalProfile profile = personalProfileService.getMyProfile();

        PersonalProfileSkill skill = skillRepository.findByIdAndProfile(skillId, profile)
                .orElseThrow(() -> new BusinessException("Skill not found", ErrorCode.PROFILE_SKILL_NOT_FOUND));

        if (request.getName() != null) {
            skill.setName(request.getName());
        }
        if (request.getDescription() != null) {
            skill.setDescription(request.getDescription());
        }
        if (request.getLevel() != null) {
            skill.setLevel(request.getLevel());
        }

        return skillRepository.save(skill);
    }

    @Transactional
    public void deleteSkill(Long skillId) {
        PersonalProfile profile = personalProfileService.getMyProfile();

        PersonalProfileSkill skill = skillRepository.findByIdAndProfile(skillId, profile)
                .orElseThrow(() -> new BusinessException("Skill not found", ErrorCode.PROFILE_SKILL_NOT_FOUND));

        skillRepository.delete(skill);
    }
}
