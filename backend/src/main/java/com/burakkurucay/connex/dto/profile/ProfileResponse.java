package com.burakkurucay.connex.dto.profile;

import com.burakkurucay.connex.entity.profile.CompanyProfile;
import com.burakkurucay.connex.entity.profile.PersonalProfile;
import com.burakkurucay.connex.entity.user.AccountType;

import java.time.LocalDateTime;

public class ProfileResponse {

    private Long id;
    private Long userId;
    private AccountType accountType;

    private Personal personal;
    private Company company;

    /* =======================
       Factory methods
       ======================= */

    public static ProfileResponse fromPersonal(PersonalProfile profile) {
        ProfileResponse dto = new ProfileResponse();
        dto.id = profile.getId();
        dto.userId = profile.getUser().getId();
        dto.accountType = AccountType.PERSONAL;
        dto.personal = Personal.from(profile);
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

    /* =======================
       Inner DTOs
       ======================= */

    public static class Personal {

        private String firstName;
        private String lastName;
        private String profileDescription;
        private String phoneNumber;
        private String location;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private static Personal from(PersonalProfile profile) {
            Personal dto = new Personal();
            dto.firstName = profile.getFirstName();
            dto.lastName = profile.getLastName();
            dto.profileDescription = profile.getProfileDescription();
            dto.phoneNumber = profile.getPhoneNumber();
            dto.location = profile.getLocation();
            dto.createdAt = profile.getCreatedAt();
            dto.updatedAt = profile.getUpdatedAt();
            return dto;
        }

        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getProfileDescription() { return profileDescription; }
        public String getPhoneNumber() { return phoneNumber; }
        public String getLocation() { return location; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }
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

        public String getCompanyName() { return companyName; }
        public String getDescription() { return description; }
        public String getIndustry() { return industry; }
        public String getLocation() { return location; }
        public String getWebsite() { return website; }
    }

    /* =======================
       Root getters
       ======================= */

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public AccountType getAccountType() { return accountType; }
    public Personal getPersonal() { return personal; }
    public Company getCompany() { return company; }
}
