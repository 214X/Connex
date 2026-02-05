package com.burakkurucay.connex.dto.profile;

import com.burakkurucay.connex.dto.profile.personal.education.EducationResponse;
import com.burakkurucay.connex.dto.profile.personal.experience.ExperienceResponse;
import com.burakkurucay.connex.dto.profile.personal.skill.SkillResponse;
import com.burakkurucay.connex.entity.profile.company.CompanyProfile;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.contact.PersonalProfileContact;
import com.burakkurucay.connex.entity.profile.personal.contact.ContactType;
import com.burakkurucay.connex.entity.profile.personal.education.PersonalProfileEducation;
import com.burakkurucay.connex.entity.profile.personal.experience.PersonalProfileExperience;
import com.burakkurucay.connex.entity.profile.personal.skill.PersonalProfileSkill;
import com.burakkurucay.connex.entity.profile.personal.language.PersonalProfileLanguage;
import com.burakkurucay.connex.entity.user.AccountType;
import com.burakkurucay.connex.dto.profile.personal.language.LanguageResponse;

import java.time.LocalDateTime;
import java.util.List;

public class ProfileResponse {

    private Long id;
    private Long userId;
    private AccountType accountType;

    private Personal personal;
    private Company company;

    /*
     * =======================
     * Factory methods
     * =======================
     */

    public static ProfileResponse fromPersonal(
            PersonalProfile profile,
            List<PersonalProfileContact> contacts,
            List<PersonalProfileEducation> educations,
            List<PersonalProfileExperience> experiences,
            List<PersonalProfileSkill> skills,
            List<PersonalProfileLanguage> languages) {
        ProfileResponse dto = new ProfileResponse();
        dto.id = profile.getId();
        dto.userId = profile.getUser().getId();
        dto.accountType = AccountType.PERSONAL;
        dto.personal = Personal.from(profile, contacts, educations, experiences, skills, languages);
        return dto;
    }

    public static ProfileResponse fromCompany(CompanyProfile profile) {
        ProfileResponse dto = new ProfileResponse();
        dto.id = profile.getId();
        dto.userId = profile.getUser().getId();
        dto.accountType = AccountType.COMPANY;
        dto.company = Company.from(profile);
        return dto;
    }

    /*
     * =======================
     * Inner DTOs
     * =======================
     */

    public static class Personal {

        private String firstName;
        private String lastName;
        private String profileDescription;
        private String phoneNumber;
        private String location;

        private List<Contact> contacts;
        private List<EducationResponse> educations;
        private List<ExperienceResponse> experiences;
        private List<SkillResponse> skills;
        private List<LanguageResponse> languages;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private static Personal from(
                PersonalProfile profile,
                List<PersonalProfileContact> contacts,
                List<PersonalProfileEducation> educations,
                List<PersonalProfileExperience> experiences,
                List<PersonalProfileSkill> skills,
                List<PersonalProfileLanguage> languages) {
            Personal dto = new Personal();
            dto.firstName = profile.getFirstName();
            dto.lastName = profile.getLastName();
            dto.profileDescription = profile.getProfileDescription();
            dto.phoneNumber = profile.getPhoneNumber();
            dto.location = profile.getLocation();
            dto.createdAt = profile.getCreatedAt();
            dto.updatedAt = profile.getUpdatedAt();
            dto.contacts = contacts.stream()
                    .map(Contact::from)
                    .toList();
            dto.educations = educations.stream()
                    .map(EducationResponse::from)
                    .toList();
            dto.experiences = experiences.stream()
                    .map(ExperienceResponse::from)
                    .toList();
            dto.skills = skills.stream()
                    .map(SkillResponse::from)
                    .toList();
            dto.languages = languages.stream()
                    .map(LanguageResponse::from)
                    .toList();
            return dto;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getProfileDescription() {
            return profileDescription;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getLocation() {
            return location;
        }

        public List<Contact> getContacts() {
            return contacts;
        }

        public List<EducationResponse> getEducations() {
            return educations;
        }

        public List<ExperienceResponse> getExperiences() {
            return experiences;
        }

        public List<SkillResponse> getSkills() {
            return skills;
        }

        public List<LanguageResponse> getLanguages() {
            return languages;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }
    }

    public static class Contact {

        private Long id;
        private ContactType type;
        private String value;

        public static Contact from(PersonalProfileContact contact) {
            Contact dto = new Contact();
            dto.id = contact.getId();
            dto.type = contact.getType();
            dto.value = contact.getValue();
            return dto;
        }

        public Long getId() {
            return id;
        }

        public ContactType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }

    public static class Company {

        private String companyName;
        private String description;
        private String industry;
        private String location;
        private String website;

        private static Company from(CompanyProfile profile) {
            Company dto = new Company();
            dto.companyName = profile.getCompanyName();
            dto.description = profile.getDescription();
            dto.industry = profile.getIndustry();
            dto.location = profile.getLocation();
            dto.website = profile.getWebsite();
            return dto;
        }

        public String getCompanyName() {
            return companyName;
        }

        public String getDescription() {
            return description;
        }

        public String getIndustry() {
            return industry;
        }

        public String getLocation() {
            return location;
        }

        public String getWebsite() {
            return website;
        }
    }

    /*
     * =======================
     * Root getters
     * =======================
     */

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Personal getPersonal() {
        return personal;
    }

    public Company getCompany() {
        return company;
    }
}
