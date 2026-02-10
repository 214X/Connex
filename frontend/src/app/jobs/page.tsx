"use client";

import { useState, useEffect, useCallback } from "react";
import { useSelector } from "react-redux";
import { useRouter } from "next/navigation";
import { RootState } from "@/store";
import { JobPosting, searchJobs, getPublishedJobs } from "@/lib/api/job/job.api";
import JobPostingCard from "@/modules/job/components/JobPostingCard";
import JobDetailModal from "@/modules/job/components/JobDetailModal";
import JobApplicationModal from "@/modules/job/components/JobApplicationModal";
import { FiSearch, FiBriefcase } from "react-icons/fi";
import styles from "./page.module.css";
import Navbar from "@/modules/navbar/ui/Navbar";

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

export default function JobsPage() {
    const { user } = useSelector((state: RootState) => state.auth);
    const router = useRouter();

    const [jobs, setJobs] = useState<JobPosting[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [searchQuery, setSearchQuery] = useState("");
    const debouncedSearchQuery = useDebounce(searchQuery, 300);

    const [selectedJob, setSelectedJob] = useState<JobPosting | null>(null);
    const [applyingJob, setApplyingJob] = useState<JobPosting | null>(null);

    const isPersonalUser = user?.accountType === "PERSONAL";

    const fetchJobs = useCallback(async (query: string) => {
        setIsLoading(true);
        setError(null);
        try {
            let data: JobPosting[];
            if (query.trim()) {
                data = await searchJobs(query);
            } else {
                data = await getPublishedJobs();
            }
            setJobs(data);
        } catch (err) {
            console.error("Failed to fetch jobs", err);
            setError("Failed to load jobs. Please try again.");
        } finally {
            setIsLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchJobs(debouncedSearchQuery);
    }, [debouncedSearchQuery, fetchJobs]);

    const handleCardClick = (job: JobPosting) => {
        setSelectedJob(job);
    };

    const handleApplyClick = (job: JobPosting) => {
        setSelectedJob(null); // Close detail modal
        setApplyingJob(job);  // Open application modal
    };

    return (
        <>
            <div className={styles.container}>
                <div className={styles.header}>
                    <h1 className={styles.title}>Find Your Next Opportunity</h1>
                    <p className={styles.subtitle}>Browse thousands of job openings from top companies.</p>

                    <div className={styles.searchWrapper}>
                        <FiSearch className={styles.searchIcon} />
                        <input
                            type="text"
                            placeholder="Search by title, description, or role..."
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
                            <p>Searching jobs...</p>
                        </div>
                    ) : error ? (
                        <div className={styles.error}>
                            <p>{error}</p>
                            <button onClick={() => fetchJobs(debouncedSearchQuery)} className={styles.retryButton}>
                                Retry
                            </button>
                        </div>
                    ) : jobs.length === 0 ? (
                        <div className={styles.empty}>
                            <FiBriefcase size={48} />
                            <h3>No jobs found</h3>
                            <p>Try adjusting your search terms or browse all jobs.</p>
                        </div>
                    ) : (
                        <div className={styles.grid}>
                            {jobs.map((job) => (
                                <JobPostingCard
                                    key={job.id}
                                    job={job}
                                    isOwner={false}
                                    onEdit={() => { }}
                                    onDelete={() => { }}
                                    onToggleStatus={() => { }}
                                    onClick={handleCardClick}
                                />
                            ))}
                        </div>
                    )}
                </div>

                {/* Job Detail Modal */}
                {selectedJob && (
                    <JobDetailModal
                        isOpen={!!selectedJob}
                        onClose={() => setSelectedJob(null)}
                        job={selectedJob}
                        onApply={handleApplyClick}
                        isPersonalUser={isPersonalUser}
                    />
                )}

                {/* Apply Modal */}
                {applyingJob && (
                    <JobApplicationModal
                        isOpen={!!applyingJob}
                        onClose={() => setApplyingJob(null)}
                        jobId={applyingJob.id}
                        jobTitle={applyingJob.title}
                        onSuccess={() => {
                            setApplyingJob(null);
                        }}
                    />
                )}
            </div>
        </>
    );
}
