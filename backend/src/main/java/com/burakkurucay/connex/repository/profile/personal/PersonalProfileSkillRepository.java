package com.burakkurucay.connex.repository.profile.personal;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.skill.PersonalProfileSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalProfileSkillRepository extends JpaRepository<PersonalProfileSkill, Long> {
    List<PersonalProfileSkill> findAllByProfile(PersonalProfile profile);

    Optional<PersonalProfileSkill> findByIdAndProfile(Long id, PersonalProfile profile);
}
