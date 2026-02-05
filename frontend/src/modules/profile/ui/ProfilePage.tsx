"use client";

import { ProfileResponse } from "@/lib/api/profile/profile.api";
import PersonalProfileView from "./Personal/PersonalProfileView";
import CompanyProfileView from "./Company/CompanyProfileView";

interface ProfilePageProps {
    profile: ProfileResponse;
    isOwner?: boolean;
    onProfileRefresh?: () => void;
}

export default function ProfilePage({ profile, isOwner = false, onProfileRefresh }: ProfilePageProps) {
    if (profile.accountType === "PERSONAL" && profile.personal) {
        return <PersonalProfileView personal={profile.personal} profileId={profile.id} isOwner={isOwner} onProfileRefresh={onProfileRefresh} />;
    }

    if (profile.accountType === "COMPANY" && profile.company) {
        return <CompanyProfileView company={profile.company} profileId={profile.id} isOwner={isOwner} onProfileRefresh={onProfileRefresh} />;
    }

    // Defensive fallback (should never happen)
    return (
        <div style={{ padding: "2rem" }}>
            <p>Profile data is not available.</p>
        </div>
    );
}
