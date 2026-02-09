"use client";

import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import type { RootState } from "@/store";
import styles from "./HomeScreen.module.css";
import Link from "next/link";
import { FiUser, FiBriefcase, FiSearch, FiArrowRight } from "react-icons/fi";
import { getMyProfile, ProfileResponse } from "@/lib/api/profile/profile.api";

export default function HomeScreen() {
    const user = useSelector((state: RootState) => state.auth.user);
    const [profile, setProfile] = useState<ProfileResponse | null>(null);

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const res = await getMyProfile();
                if (res.success && res.data) {
                    setProfile(res.data);
                }
            } catch (err) {
                console.error("Failed to load profile for homescreen", err);
            }
        };

        if (user) {
            fetchProfile();
        }
    }, [user]);

    // Personalize welcome message
    const firstName = profile?.accountType === "PERSONAL" ? profile.personal?.firstName : profile?.company?.companyName;
    const displayName = firstName || user?.email || "User";

    return (
        <div className={styles.container}>
            {/* Hero Section */}
            <div className={styles.hero}>
                <div className={styles.heroContent}>
                    <h1 className={styles.welcomeTitle}>Welcome back, {displayName}!</h1>
                    <p className={styles.welcomeSubtitle}>
                        Your professional network starts here. Discover opportunities, connect with peers, and grow your career.
                    </p>
                </div>
            </div>

            {/* Quick Actions / Tips */}
            <h2 className={styles.sectionTitle}>Get Started with Connex</h2>

            <div className={styles.tipsGrid}>
                {/* Tip 1: Profile */}
                <div className={styles.tipCard}>
                    <div className={styles.iconWrapper}>
                        <FiUser />
                    </div>
                    <h3 className={styles.cardTitle}>Complete Your Profile</h3>
                    <p className={styles.cardText}>
                        A complete profile increases your visibility. Add your skills, experience, and photo.
                    </p>
                    <Link href={`/profiles/me`} className={styles.cardAction}>
                        Go to Profile <FiArrowRight />
                    </Link>
                </div>

                {/* Tip 2: Jobs */}
                <div className={styles.tipCard}>
                    <div className={styles.iconWrapper}>
                        <FiBriefcase />
                    </div>
                    <h3 className={styles.cardTitle}>Find Your Dream Job</h3>
                    <p className={styles.cardText}>
                        Browse the latest job openings from top companies and apply today.
                    </p>
                    <Link href="/jobs" className={styles.cardAction}>
                        Browse Jobs <FiArrowRight />
                    </Link>
                </div>

                {/* Tip 3: Network */}
                <div className={styles.tipCard}>
                    <div className={styles.iconWrapper}>
                        <FiSearch />
                    </div>
                    <h3 className={styles.cardTitle}>Explore Companies</h3>
                    <p className={styles.cardText}>
                        Discover innovative companies and see what they have to offer.
                    </p>
                    <Link href="/search" className={styles.cardAction}>
                        Search Companies <FiArrowRight />
                    </Link>
                </div>
            </div>
        </div>
    );
}
