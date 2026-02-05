"use client";

import styles from "./CompanyProfileView.module.css";
import { CompanyProfileData } from "@/lib/api/profile/profile.api";
import CompanyProfileHeader from "./components/CompanyProfileHeader";

interface CompanyProfileViewProps {
    company: CompanyProfileData;
    profileId: number;
    isOwner?: boolean;
}

export default function CompanyProfileView({
    company,
    profileId,
    isOwner = false,
}: CompanyProfileViewProps) {
    const {
        companyName,
        description,
        industry,
        location,
        website,
    } = company;

    return (
        <div className={styles.pageContainer}>
            <div className={styles.midContainer}>
                <CompanyProfileHeader
                    companyName={companyName}
                    description={description}
                    industry={industry}
                    location={location}
                    website={website}
                    profileId={profileId}
                    isOwner={isOwner}
                />
            </div>
        </div>
    );
}
