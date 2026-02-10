"use client";

import { useState, useEffect, useCallback } from "react";
import { FiSearch, FiMapPin, FiGlobe, FiGrid } from "react-icons/fi";
import { companyApi } from "@/lib/api/company/company.api";
import { CompanyProfileData } from "@/lib/api/profile/profile.api";
import Link from "next/link";
import styles from "./CompanySearchPage.module.css";

// Debounce hook (same as jobs page)
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

export default function CompanySearchPage() {
    const [searchQuery, setSearchQuery] = useState("");
    const debouncedSearchQuery = useDebounce(searchQuery, 300);
    const [companies, setCompanies] = useState<CompanyProfileData[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const fetchCompanies = useCallback(async (query: string) => {
        setIsLoading(true);
        setError(null);
        try {
            let data: CompanyProfileData[];
            if (query.trim()) {
                data = await companyApi.searchCompanies(query);
            } else {
                data = await companyApi.getRandomCompanies();
            }
            setCompanies(data);
        } catch (err) {
            console.error("Failed to fetch companies", err);
            setError("Failed to load companies. Please try again.");
        } finally {
            setIsLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchCompanies(debouncedSearchQuery);
    }, [debouncedSearchQuery, fetchCompanies]);

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h1 className={styles.title}>Discover Companies</h1>
                <p className={styles.subtitle}>Explore innovative companies and find your next opportunity.</p>

                <div className={styles.searchWrapper}>
                    <FiSearch className={styles.searchIcon} />
                    <input
                        type="text"
                        placeholder="Search by company name..."
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
                        <p>Searching companies...</p>
                    </div>
                ) : error ? (
                    <div className={styles.error}>
                        <p>{error}</p>
                        <button onClick={() => fetchCompanies(debouncedSearchQuery)} className={styles.retryButton}>
                            Retry
                        </button>
                    </div>
                ) : companies.length === 0 ? (
                    <div className={styles.empty}>
                        <FiGrid size={48} />
                        <h3>No companies found</h3>
                        <p>Try adjusting your search terms or browse all companies.</p>
                    </div>
                ) : (
                    <div className={styles.resultsGrid}>
                        {companies.map((company) => (
                            <Link href={`/profiles/${company.userId}`} key={company.id} className={styles.cardLink}>
                                <div className={styles.companyCard}>
                                    <div className={styles.cardHeader}>
                                        <h3 className={styles.companyName}>{company.companyName}</h3>
                                        {company.industry && (
                                            <span className={styles.industryTag}>{company.industry}</span>
                                        )}
                                    </div>

                                    <p className={styles.description}>
                                        {company.description?.substring(0, 120)}
                                        {company.description && company.description.length > 120 ? "..." : ""}
                                    </p>

                                    <div className={styles.cardFooter}>
                                        {company.location && (
                                            <div className={styles.metaItem}>
                                                <FiMapPin size={14} /> <span>{company.location}</span>
                                            </div>
                                        )}
                                        {company.website && (
                                            <div className={styles.metaItem}>
                                                <FiGlobe size={14} /> <span>Website</span>
                                            </div>
                                        )}
                                    </div>
                                </div>
                            </Link>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}
