import { useState, useEffect, useCallback } from "react";
import { FiPlus } from "react-icons/fi";
import { useSelector } from "react-redux";
import { RootState } from "@/store";
import {
    getMyCompanyJobs,
    getCompanyPublicJobs,
    deleteJob,
    publishJob,
    closeJob,
    JobPosting,
    JobStatus
} from "@/lib/api/job/job.api";
import JobPostingCard from "./JobPostingCard";
import JobPostingModal from "./JobPostingModal";
import JobApplicationModal from "./JobApplicationModal";
import CompanyJobApplicationsModal from "./CompanyJobApplicationsModal";
import styles from "./JobPostingList.module.css";

interface JobPostingListProps {
    profileId: number;
    isOwner: boolean;
}

export default function JobPostingList({ profileId, isOwner }: JobPostingListProps) {
    const { user } = useSelector((state: RootState) => state.auth);
    const isPersonal = user?.accountType === "PERSONAL";

    const [jobs, setJobs] = useState<JobPosting[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    // Modal State
    const [iscreateEditModalOpen, setIsCreateEditModalOpen] = useState(false);
    const [editingJob, setEditingJob] = useState<JobPosting | null>(null);

    const [applyingJob, setApplyingJob] = useState<JobPosting | null>(null);
    const [viewingApplicantsJob, setViewingApplicantsJob] = useState<JobPosting | null>(null);

    const fetchJobs = useCallback(async () => {
        setIsLoading(true);
        setError(null);
        try {
            const data = isOwner
                ? await getMyCompanyJobs()
                : await getCompanyPublicJobs(profileId);
            setJobs(data);
        } catch (err: any) {
            console.error("Failed to fetch jobs", err);
            setError("Failed to load job postings.");
        } finally {
            setIsLoading(false);
        }
    }, [isOwner, profileId]);

    useEffect(() => {
        fetchJobs();
    }, [fetchJobs]);

    const handleCreateClick = () => {
        setEditingJob(null);
        setIsCreateEditModalOpen(true);
    };

    const handleEditClick = (job: JobPosting) => {
        setEditingJob(job);
        setIsCreateEditModalOpen(true);
    };

    const handleDeleteClick = async (id: number) => {
        if (!window.confirm("Are you sure you want to delete this job posting?")) return;

        try {
            await deleteJob(id);
            // Optimistic update
            setJobs(prev => prev.filter(j => j.id !== id));
        } catch (err) {
            console.error("Failed to delete job", err);
            alert("Failed to delete job.");
        }
    };

    const handleToggleStatus = async (job: JobPosting) => {
        try {
            if (job.status === JobStatus.PUBLISHED) {
                await closeJob(job.id);
                // Optimistic update
                setJobs(prev => prev.map(j => j.id === job.id ? { ...j, status: JobStatus.CLOSED } : j));
            } else {
                await publishJob(job.id);
                // Optimistic update
                setJobs(prev => prev.map(j => j.id === job.id ? { ...j, status: JobStatus.PUBLISHED } : j));
            }
        } catch (err) {
            console.error("Failed to update status", err);
            alert("Failed to update job status.");
        }
    };

    const handleApplyClick = (job: JobPosting) => {
        setApplyingJob(job);
    };

    const handleViewApplicantsClick = (job: JobPosting) => {
        setViewingApplicantsJob(job);
    };

    const handleModalSuccess = () => {
        fetchJobs();
    };

    if (isLoading && jobs.length === 0) {
        return <div className={styles.loading}>Loading jobs...</div>;
    }

    if (error) {
        return <div className={styles.error}>{error}</div>;
    }

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h2 className={styles.title}>Job Postings</h2>
                {isOwner && (
                    <button onClick={handleCreateClick} className={styles.createButton}>
                        <FiPlus size={20} /> Create Job
                    </button>
                )}
            </div>

            {jobs.length === 0 ? (
                <div className={styles.empty}>
                    <p>No job postings found.</p>
                </div>
            ) : (
                <div className={styles.list}>
                    {jobs.map(job => (
                        <JobPostingCard
                            key={job.id}
                            job={job}
                            isOwner={isOwner}
                            onEdit={handleEditClick}
                            onDelete={handleDeleteClick}
                            onToggleStatus={handleToggleStatus}
                            onApply={isPersonal ? handleApplyClick : undefined}
                            onViewApplicants={isOwner ? handleViewApplicantsClick : undefined}
                        />
                    ))}
                </div>
            )}

            {isOwner && (
                <JobPostingModal
                    isOpen={iscreateEditModalOpen}
                    onClose={() => setIsCreateEditModalOpen(false)}
                    onSuccess={handleModalSuccess}
                    initialData={editingJob}
                />
            )}

            {applyingJob && (
                <JobApplicationModal
                    isOpen={!!applyingJob}
                    onClose={() => setApplyingJob(null)}
                    jobId={applyingJob.id}
                    jobTitle={applyingJob.title}
                    onSuccess={() => {
                        // Optionally refresh jobs to update application count if we display it
                        fetchJobs();
                    }}
                />
            )}

            {viewingApplicantsJob && (
                <CompanyJobApplicationsModal
                    isOpen={!!viewingApplicantsJob}
                    onClose={() => setViewingApplicantsJob(null)}
                    jobId={viewingApplicantsJob.id}
                    jobTitle={viewingApplicantsJob.title}
                />
            )}
        </div>
    );
}
