package com.burakkurucay.connex.repository.profile.personal;

import com.burakkurucay.connex.entity.profile.personal.contact.PersonalProfileContact;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.contact.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalProfileContactRepository extends JpaRepository<PersonalProfileContact, Long> {

    List<PersonalProfileContact> findAllByProfile(PersonalProfile profile);

    List<PersonalProfileContact> findAllByProfileAndType(
        PersonalProfile profile,
        ContactType type
    );

    Optional<PersonalProfileContact> findByIdAndProfile(
        Long id,
        PersonalProfile profile
    );
}
