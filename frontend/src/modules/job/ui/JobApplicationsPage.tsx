"use client";

import { useEffect, useState } from "react";
import { FiBriefcase } from "react-icons/fi";
import Link from "next/link";
import { getMyApplications, MyJobApplicationResponse } from "@/lib/api/job/jobApplication.api";
import JobApplicationCard from "../components/JobApplicationCard";
import styles from "./JobApplicationsPage.module.css";
import JobDetailModal from "../components/JobDetailModal";
import { JobPosting, getJobDetail } from "@/lib/api/job/job.api";

export default function JobApplicationsPage() {
    const [applications, setApplications] = useState<MyJobApplicationResponse[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    // Modal state
    const [selectedJob, setSelectedJob] = useState<JobPosting | null>(null);
    const [isDetailLoading, setIsDetailLoading] = useState(false);

    useEffect(() => {
        const fetchApplications = async () => {
            try {
                const data = await getMyApplications();
                setApplications(data);
            } catch (err) {
                console.error("Failed to load applications", err);
                setError("Failed to load applications.");
            } finally {
                setIsLoading(false);
            }
        };

        fetchApplications();
    }, []);

    const handleViewJob = async (jobId: number) => {
        setIsDetailLoading(true);
        try {
            const job = await getJobDetail(jobId);
            setSelectedJob(job);
        } catch (err) {
            console.error("Failed to load job details", err);
            // Optionally show error toast
        } finally {
            setIsDetailLoading(false);
        }
    };

    if (isLoading) {
        return (
            <div className={styles.pageContainer}>
                <div className={styles.loading}>Loading applications...</div>
            </div>
        );
    }

    if (error) {
        return (
            <div className={styles.pageContainer}>
                <div className={styles.error}>{error}</div>
            </div>
        );
    }

    return (
        <div className={styles.pageContainer}>
            <div className={styles.contentWrapper}>
                <div className={styles.header}>
                    <h1 className={styles.title}>My Job Applications</h1>
                    <p className={styles.subtitle}>Track the status of your applications.</p>
                </div>

                {applications.length === 0 ? (
                    <div className={styles.emptyState}>
                        <FiBriefcase className={styles.emptyIcon} />
                        <p className={styles.emptyText}>You haven't applied to any jobs yet.</p>
                        <Link href="/jobs" className={styles.browseButton}>
                            Browse Jobs
                        </Link>
                    </div>
                ) : (
                    <div className={styles.list}>
                        {applications.map(app => (
                            <JobApplicationCard
                                key={app.id}
                                application={app}
                                onClick={() => handleViewJob(app.jobPostingId)}
                            />
                        ))}
                    </div>
                )}

                {/* Loading Detail Overlay (Optional) */}
                {isDetailLoading && (
                    <div className={styles.loadingOverlay}>
                        <div className={styles.spinner}></div>
                    </div>
                )}

                {/* Job Detail Modal */}
                {selectedJob && (
                    <JobDetailModal
                        isOpen={!!selectedJob}
                        onClose={() => setSelectedJob(null)}
                        job={selectedJob}
                        isPersonalUser={true}
                    // We can pass a dummy onApply since user has already applied, 
                    // or handle apply logic if they can apply again (unlikely)
                    // Actually JobDetailModal handles isPersonalUser check to show apply button.
                    // But since this is specific to applications page, we know they applied.
                    // We might want to disable apply button in modal or show "Applied".
                    // JobDetailModal currently has logic: {isPersonalUser && onApply && ... Apply Button}
                    // If we don't pass onApply, buttons won't show, which is correct for "View Only".
                    />
                )}
            </div>
        </div>
    );
}
