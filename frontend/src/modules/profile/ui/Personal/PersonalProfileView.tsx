"use client";

import styles from "./PersonalProfileView.module.css";
import { PersonalProfileData } from "@/lib/api/profile/profile.api";

import PersonalProfileHeader from "./components/PersonalProfileHeader";

interface PersonalProfileViewProps {
    personal: PersonalProfileData;
}

export default function PersonalProfileView({
    personal,
}: PersonalProfileViewProps) {
    const {
        firstName,
        lastName,
        profileDescription,
        phoneNumber,
        location,
    } = personal;

    return (
        <div className={styles.pageContainer}>
            <div className={styles.midContainer}>
                <PersonalProfileHeader
                    firstName={firstName}
                    lastName={lastName}
                    headline={profileDescription ?? undefined}
                    location={location ?? undefined}
                    phoneNumber={phoneNumber ?? undefined}
                />
            </div>
        </div>
    );
}
