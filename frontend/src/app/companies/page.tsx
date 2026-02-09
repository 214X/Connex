"use client";

import { useState, useEffect } from "react";
import { FiSearch, FiMapPin, FiGlobe } from "react-icons/fi";
import { companyApi } from "@/lib/api/company/company.api";
import { CompanyProfileData } from "@/lib/api/profile/profile.api";
import Link from "next/link";
import styles from "./CompanySearchPage.module.css";

export default function CompanySearchPage() {
    const [query, setQuery] = useState("");
    const [companies, setCompanies] = useState<CompanyProfileData[]>([]);
    const [loading, setLoading] = useState(true);

    // Initial load: fetch random companies
    useEffect(() => {
        loadRandomCompanies();
    }, []);

    const loadRandomCompanies = async () => {
        setLoading(true);
        try {
            const data = await companyApi.getRandomCompanies();
            setCompanies(data);
        } catch (error) {
            console.error("Failed to load companies", error);
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!query.trim()) {
            loadRandomCompanies();
            return;
        }

        setLoading(true);
        try {
            const data = await companyApi.searchCompanies(query);
            setCompanies(data);
        } catch (error) {
            console.error("Search failed", error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h1 className={styles.title}>Discover Companies</h1>
                <p className={styles.subtitle}>Find your next opportunity or partner.</p>

                <form onSubmit={handleSearch} className={styles.searchForm}>
                    <div className={styles.searchBar}>
                        <FiSearch className={styles.searchIcon} />
                        <input
                            type="text"
                            className={styles.searchInput}
                            placeholder="Search by company name..."
                            value={query}
                            onChange={(e) => setQuery(e.target.value)}
                        />
                    </div>
                    <button type="submit" className={styles.searchButton}>Search</button>
                </form>
            </div>

            <div className={styles.resultsGrid}>
                {loading ? (
                    <div className={styles.loading}>Loading...</div>
                ) : companies.length > 0 ? (
                    companies.map((company) => (
                        <Link href={`/profiles/${company.userId}`} key={company.id} className={styles.cardLink}>
                            <div className={styles.companyCard}>
                                <div className={styles.cardHeader}>
                                    <h3 className={styles.companyName}>{company.companyName}</h3>
                                    <span className={styles.industryTag}>{company.industry}</span>
                                </div>

                                <p className={styles.description}>
                                    {company.description?.substring(0, 100)}
                                    {company.description && company.description.length > 100 ? "..." : ""}
                                </p>

                                <div className={styles.cardFooter}>
                                    {company.location && (
                                        <div className={styles.metaItem}>
                                            <FiMapPin /> {company.location}
                                        </div>
                                    )}
                                    {company.website && (
                                        <div className={styles.metaItem}>
                                            <FiGlobe /> Website
                                        </div>
                                    )}
                                </div>
                            </div>
                        </Link>
                    ))
                ) : (
                    <div className={styles.noResults}>No companies found.</div>
                )}
            </div>
        </div>
    );
}
