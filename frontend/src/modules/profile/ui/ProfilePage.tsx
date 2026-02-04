"use client";

import { ProfileResponse } from "@/lib/api/profile/profile.api";
import PersonalProfileView from "./Personal/PersonalProfileView";
import CompanyProfileView from "./Company/CompanyProfileView";

interface ProfilePageProps {
    profile: ProfileResponse;
}

export default function ProfilePage({ profile }: ProfilePageProps) {
    if (profile.accountType === "PERSONAL" && profile.personal) {
        return <PersonalProfileView personal={profile.personal} />;
    }

    if (profile.accountType === "COMPANY" && profile.company) {
        return <CompanyProfileView company={profile.company} />;
    }

    // Defensive fallback (should never happen)
    return (
        <div style={{ padding: "2rem" }}>
            <p>Profile data is not available.</p>
        </div>
    );
}
