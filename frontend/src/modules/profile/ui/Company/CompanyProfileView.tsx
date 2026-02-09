"use client";

import styles from "./CompanyProfileView.module.css";
import { CompanyProfileData } from "@/lib/api/profile/profile.api";
import CompanyProfileHeader from "./components/CompanyProfileHeader";
import JobPostingList from "@/modules/job/components/JobPostingList";

interface CompanyProfileViewProps {
    company: CompanyProfileData;
    profileId: number;
    userId: number;
    isOwner?: boolean;
    onProfileRefresh?: () => void;
}

export default function CompanyProfileView({
    company,
    profileId,
    userId,
    isOwner = false,
    onProfileRefresh,
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
                    onProfileUpdate={onProfileRefresh}
                />

                <JobPostingList
                    profileId={isOwner ? profileId : userId}
                    isOwner={Boolean(isOwner)}
                />
            </div>
        </div>
    );
}
