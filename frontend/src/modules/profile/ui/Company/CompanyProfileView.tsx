"use client";

import styles from "./CompanyProfileView.module.css";
import { CompanyProfileData } from "@/lib/api/profile/profile.api";
import CompanyProfileHeader from "./components/CompanyProfileHeader";

interface CompanyProfileViewProps {
    company: CompanyProfileData;
}

export default function CompanyProfileView({
    company,
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
                />
            </div>
        </div>
    );
}
