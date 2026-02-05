"use client";

import { useState, useCallback } from "react";
import styles from "./PersonalProfileView.module.css";
import { PersonalProfileData, getMyProfile } from "@/lib/api/profile/profile.api";

import PersonalProfileHeader from "./components/PersonalProfileHeader";
import ContactList from "./components/ContactList";
import EducationList from "./components/EducationList";
import ExperienceList from "./components/ExperienceList";
import SkillList from "./components/SkillList";
import LanguageList from "./components/LanguageList";

interface PersonalProfileViewProps {
    personal: PersonalProfileData;
    isOwner?: boolean;
}

export default function PersonalProfileView({
    personal: initialPersonal,
    isOwner = false,
}: PersonalProfileViewProps) {
    const [personal, setPersonal] = useState<PersonalProfileData>(initialPersonal);

    // Function to refresh profile data after edits
    const refreshProfile = useCallback(async () => {
        try {
            // Re-fetch profile data. 
            // Note: If this is a public view, we'd need userId. 
            // If it's the "me" view, getMyProfile works.
            // Since we don't have userId easily here for public view, we assume getMyProfile for owner.
            // For public view updates (if they ever happen?), we'd need a different strategy.
            if (isOwner) {
                const res = await getMyProfile();
                // Check if success and data exists
                if (res.success && res.data && res.data.personal) {
                    setPersonal(res.data.personal);
                }
            }
        } catch (err) {
            console.error("Failed to refresh profile", err);
        }
    }, [isOwner]);

    return (
        <div className={styles.pageContainer}>
            <div className={styles.midContainer}>
                <PersonalProfileHeader
                    profile={personal}
                    isOwner={isOwner}
                    onProfileUpdate={refreshProfile}
                />

                <ContactList
                    contacts={personal.contacts || []}
                    isOwner={isOwner}
                    onContactsChange={refreshProfile}
                />

                <EducationList
                    educations={personal.educations || []}
                    isOwner={isOwner}
                    onEducationsChange={refreshProfile}
                />

                <ExperienceList
                    experiences={personal.experiences || []}
                    isOwner={isOwner}
                    onExperiencesChange={refreshProfile}
                />

                <SkillList
                    skills={personal.skills || []}
                    isOwner={isOwner}
                    onSkillsChange={refreshProfile}
                />

                <LanguageList
                    languages={personal.languages || []}
                    isOwner={isOwner}
                    onLanguagesChange={refreshProfile}
                />
            </div>
        </div>
    );
}
