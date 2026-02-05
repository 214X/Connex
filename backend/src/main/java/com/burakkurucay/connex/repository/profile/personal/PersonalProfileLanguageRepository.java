package com.burakkurucay.connex.repository.profile.personal;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.language.PersonalProfileLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalProfileLanguageRepository extends JpaRepository<PersonalProfileLanguage, Long> {

    List<PersonalProfileLanguage> findAllByProfile(PersonalProfile profile);

    Optional<PersonalProfileLanguage> findByIdAndProfile(Long id, PersonalProfile profile);
}
