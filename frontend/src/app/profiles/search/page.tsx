"use client";

import { useState, useEffect, useCallback } from "react";
import { FiSearch, FiMapPin, FiUser, FiGrid } from "react-icons/fi";
import { searchPersonalProfiles, PersonalProfileSearchResult } from "@/lib/api/profile/profile.api";
import Link from "next/link";
import styles from "./PersonalProfileSearchPage.module.css";

// Debounce hook
function useDebounce<T>(value: T, delay: number): T {
    const [debouncedValue, setDebouncedValue] = useState<T>(value);

    useEffect(() => {
        const timer = setTimeout(() => {
            setDebouncedValue(value);
        }, delay);

        return () => {
            clearTimeout(timer);
        };
    }, [value, delay]);

    return debouncedValue;
}

export default function PersonalProfileSearchPage() {
    const [searchQuery, setSearchQuery] = useState("");
    const debouncedSearchQuery = useDebounce(searchQuery, 300);
    const [profiles, setProfiles] = useState<PersonalProfileSearchResult[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const fetchProfiles = useCallback(async (query: string) => {
        setIsLoading(true);
        setError(null);
        try {
            // If query is empty, we might want to return empty or all? 
            // Backend defaults to empty query -> implementation specific.
            // Let's assume empty query returns all or random (like companies).
            // But our backend LIKE implementation with '' matches everything.
            const data = await searchPersonalProfiles(query);
            setProfiles(data);
        } catch (err) {
            console.error("Failed to fetch profiles", err);
            setError("Failed to load profiles. Please try again.");
        } finally {
            setIsLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchProfiles(debouncedSearchQuery);
    }, [debouncedSearchQuery, fetchProfiles]);

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h1 className={styles.title}>Find People</h1>
                <p className={styles.subtitle}>Discover talented professionals and expand your network.</p>

                <div className={styles.searchWrapper}>
                    <FiSearch className={styles.searchIcon} />
                    <input
                        type="text"
                        placeholder="Search by name..."
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                        className={styles.searchInput}
                    />
                </div>
            </div>

            <div className={styles.content}>
                {isLoading ? (
                    <div className={styles.loading}>
                        <div className={styles.spinner}></div>
                        <p>Searching profiles...</p>
                    </div>
                ) : error ? (
                    <div className={styles.error}>
                        <p>{error}</p>
                        <button onClick={() => fetchProfiles(debouncedSearchQuery)} className={styles.retryButton}>
                            Retry
                        </button>
                    </div>
                ) : profiles.length === 0 ? (
                    <div className={styles.empty}>
                        <FiGrid size={48} />
                        <h3>No profiles found</h3>
                        <p>Try adjusting your search terms.</p>
                    </div>
                ) : (
                    <div className={styles.resultsGrid}>
                        {profiles.map((profile) => (
                            <Link href={`/profiles/${profile.userId}`} key={profile.id} className={styles.cardLink}>
                                <div className={styles.profileCard}>
                                    <div className={styles.cardHeader}>
                                        <div className={styles.avatarPlaceholder}>
                                            {profile.firstName.charAt(0)}
                                            {profile.lastName.charAt(0)}
                                        </div>
                                        <div className={styles.headerInfo}>
                                            <h3 className={styles.profileName}>
                                                {profile.firstName} {profile.lastName}
                                            </h3>
                                            {profile.location && (
                                                <div className={styles.location}>
                                                    <FiMapPin size={12} /> <span>{profile.location}</span>
                                                </div>
                                            )}
                                        </div>
                                    </div>

                                    <p className={styles.description}>
                                        {profile.profileDescription?.substring(0, 100)}
                                        {profile.profileDescription && profile.profileDescription.length > 100 ? "..." : ""}
                                    </p>
                                </div>
                            </Link>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}
