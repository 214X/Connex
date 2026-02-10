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
import ProjectList from "./components/ProjectList";

interface PersonalProfileViewProps {
    personal: PersonalProfileData;
    profileId: number;
    isOwner?: boolean;
    onProfileRefresh?: () => void;
}

export default function PersonalProfileView({
    personal: initialPersonal,
    profileId,
    isOwner = false,
    onProfileRefresh,
}: PersonalProfileViewProps) {
    const [personal, setPersonal] = useState<PersonalProfileData>(initialPersonal);
    const [isEditMode, setIsEditMode] = useState(false);

    // Function to refresh profile data after edits
    const refreshProfile = useCallback(async () => {
        try {
            if (isOwner) {
                const res = await getMyProfile();
                if (res.success && res.data && res.data.personal) {
                    setPersonal(res.data.personal);
                }
            }
            onProfileRefresh?.();
        } catch (err) {
            console.error("Failed to refresh profile", err);
        }
    }, [isOwner, onProfileRefresh]);

    return (
        <div className={styles.pageContainer}>
            {isOwner && (
                <div className={styles.editToggleWrapper}>
                    <button
                        className={`${styles.editToggleButton} ${isEditMode ? styles.active : ''}`}
                        onClick={() => setIsEditMode(!isEditMode)}
                    >
                        {isEditMode ? "Complete Editing" : "Edit Profile"}
                    </button>
                </div>
            )}

            <div className={styles.midContainer}>
                <PersonalProfileHeader
                    profile={personal}
                    profileId={profileId}
                    isOwner={isOwner}
                    isEditMode={isEditMode}
                    onProfileUpdate={refreshProfile}
                />



                <ContactList
                    contacts={personal.contacts || []}
                    isOwner={isOwner}
                    isEditMode={isEditMode}
                    onContactsChange={refreshProfile}
                />

                <EducationList
                    educations={personal.educations || []}
                    isOwner={isOwner}
                    isEditMode={isEditMode}
                    onEducationsChange={refreshProfile}
                />

                <ExperienceList
                    experiences={personal.experiences || []}
                    isOwner={isOwner}
                    isEditMode={isEditMode}
                    onExperiencesChange={refreshProfile}
                />

                <SkillList
                    skills={personal.skills || []}
                    isOwner={isOwner}
                    isEditMode={isEditMode}
                    onSkillsChange={refreshProfile}
                />

                <LanguageList
                    languages={personal.languages || []}
                    isOwner={isOwner}
                    isEditMode={isEditMode}
                    onLanguagesChange={refreshProfile}
                />

                <ProjectList
                    projects={personal.projects || []}
                    isOwner={isOwner}
                    isEditMode={isEditMode}
                    onRefresh={refreshProfile}
                />
            </div>
        </div>
    );
}
