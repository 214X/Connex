"use client";

import { FiMapPin, FiBriefcase, FiClock, FiCalendar, FiSend, FiX, FiUsers } from "react-icons/fi";
import { JobPosting } from "@/lib/api/job/job.api";
import Modal from "@/components/ui/Modal";
import styles from "./JobDetailModal.module.css";

interface JobDetailModalProps {
    isOpen: boolean;
    onClose: () => void;
    job: JobPosting;
    onApply?: (job: JobPosting) => void;
    isPersonalUser: boolean;
}

export default function JobDetailModal({ isOpen, onClose, job, onApply, isPersonalUser }: JobDetailModalProps) {
    const formatDate = (dateStr?: string | null) => {
        if (!dateStr) return "";
        return new Date(dateStr).toLocaleDateString(undefined, {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
    };

    const formatJobType = (type: string) => type.replace(/_/g, " ");

    return (
        <Modal isOpen={isOpen} onClose={onClose} title="">
            <div className={styles.container}>
                {/* Hero Banner */}
                <div className={styles.heroBanner}>
                    <h2 className={styles.title}>{job.title}</h2>
                    <p className={styles.company}>{job.companyName}</p>
                    <p className={styles.position}>{job.position}</p>
                </div>

                {/* Meta Tags */}
                <div className={styles.metaRow}>
                    <div className={styles.metaTag}>
                        <FiMapPin size={14} />
                        <span>{job.location}</span>
                    </div>
                    <div className={styles.metaTag}>
                        <FiBriefcase size={14} />
                        <span>{formatJobType(job.jobType)}</span>
                    </div>
                    <div className={styles.metaTag}>
                        <FiClock size={14} />
                        <span>{job.workMode}</span>
                    </div>
                    {(job.publishedAt || job.createdAt) && (
                        <div className={styles.metaTag}>
                            <FiCalendar size={14} />
                            <span>{formatDate(job.publishedAt || job.createdAt)}</span>
                        </div>
                    )}
                </div>

                {/* Description */}
                <div className={styles.section}>
                    <h3 className={styles.sectionTitle}>About this role</h3>
                    <p className={styles.description}>{job.description}</p>
                </div>

                {/* Skills */}
                {job.skills.length > 0 && (
                    <div className={styles.section}>
                        <h3 className={styles.sectionTitle}>Required Skills</h3>
                        <div className={styles.skills}>
                            {job.skills.map((skill, index) => (
                                <span key={index} className={styles.skillTag}>{skill}</span>
                            ))}
                        </div>
                    </div>
                )}

                {/* Applicants info */}
                {job.applicationCount > 0 && (
                    <div className={styles.applicantInfo}>
                        <FiUsers size={14} />
                        <span>{job.applicationCount} {job.applicationCount === 1 ? 'person has' : 'people have'} applied</span>
                    </div>
                )}

                {/* Apply Action */}
                {isPersonalUser && onApply && (
                    <div className={styles.applySection}>
                        <button
                            className={styles.applyButton}
                            onClick={() => onApply(job)}
                        >
                            <FiSend size={16} />
                            <span>Apply Now</span>
                        </button>
                    </div>
                )}
            </div>
        </Modal>
    );
}
