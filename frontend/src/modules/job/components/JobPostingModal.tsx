import { useState, useEffect } from "react";
import { FiX } from "react-icons/fi";
import Modal from "@/components/ui/Modal";
import {
    createJob,
    updateJob,
    JobPosting,
    JobType,
    WorkMode,
    CreateJobPostingRequest
} from "@/lib/api/job/job.api";
import styles from "./JobPostingModal.module.css";

interface JobPostingModalProps {
    isOpen: boolean;
    onClose: () => void;
    onSuccess: () => void;
    initialData?: JobPosting | null;
}

export default function JobPostingModal({
    isOpen,
    onClose,
    onSuccess,
    initialData
}: JobPostingModalProps) {
    const isEdit = !!initialData;
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const [formData, setFormData] = useState<CreateJobPostingRequest>({
        title: "",
        position: "",
        description: "",
        location: "",
        skills: [],
        jobType: JobType.FULL_TIME,
        workMode: WorkMode.ONSITE
    });

    // Skills input as comma-separated string locally
    const [skillsInput, setSkillsInput] = useState("");

    useEffect(() => {
        if (initialData) {
            setFormData({
                title: initialData.title,
                position: initialData.position,
                description: initialData.description,
                location: initialData.location,
                skills: initialData.skills,
                jobType: initialData.jobType,
                workMode: initialData.workMode
            });
            setSkillsInput(initialData.skills.join(", "));
        } else {
            // Reset
            setFormData({
                title: "",
                position: "",
                description: "",
                location: "",
                skills: [],
                jobType: JobType.FULL_TIME,
                workMode: WorkMode.ONSITE
            });
            setSkillsInput("");
        }
        setError(null);
    }, [initialData, isOpen]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);

        // Parse skills
        const skillsArray = skillsInput
            .split(",")
            .map(s => s.trim())
            .filter(s => s.length > 0);

        const payload = { ...formData, skills: skillsArray };

        try {
            if (isEdit && initialData) {
                await updateJob(initialData.id, payload);
            } else {
                await createJob(payload);
            }
            onSuccess();
            onClose();
        } catch (err: any) {
            console.error("Job save failed", err);
            setError(err.message || "Failed to save job posting");
        } finally {
            setIsLoading(false);
        }
    };

    const handleChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
    ) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value as any }));
    };

    return (
        <Modal isOpen={isOpen} onClose={onClose} title={isEdit ? "Edit Job Posting" : "Create New Job"}>
            <form onSubmit={handleSubmit} className={styles.form}>
                {error && <div className={styles.error}>{error}</div>}

                <div className={styles.formRow}>
                    <div className={styles.formGroup}>
                        <label>Job Title</label>
                        <input
                            name="title"
                            value={formData.title}
                            onChange={handleChange}
                            placeholder="e.g. Senior Software Engineer"
                            className={styles.input}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Position / Role</label>
                        <input
                            name="position"
                            value={formData.position}
                            onChange={handleChange}
                            placeholder="e.g. Backend Developer"
                            className={styles.input}
                            required
                        />
                    </div>
                </div>

                <div className={styles.formGroup}>
                    <label>Description</label>
                    <textarea
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        placeholder="Describe the role, responsibilities, and requirements..."
                        className={styles.textarea}
                        rows={5}
                        required
                    />
                </div>

                <div className={styles.formRow}>
                    <div className={styles.formGroup}>
                        <label>Location</label>
                        <input
                            name="location"
                            value={formData.location}
                            onChange={handleChange}
                            placeholder="City, Country or Remote"
                            className={styles.input}
                            required
                        />
                    </div>

                    <div className={styles.formGroup}>
                        <label>Skills (comma separated)</label>
                        <input
                            value={skillsInput}
                            onChange={(e) => setSkillsInput(e.target.value)}
                            placeholder="Java, React, SQL..."
                            className={styles.input}
                            required
                        />
                    </div>
                </div>

                <div className={styles.formRow}>
                    <div className={styles.formGroup}>
                        <label>Job Type</label>
                        <div className={styles.selectWrapper}>
                            <select
                                name="jobType"
                                value={formData.jobType}
                                onChange={handleChange}
                                className={styles.select}
                            >
                                {Object.keys(JobType).map(type => (
                                    <option key={type} value={type}>
                                        {type.replace("_", " ")}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>

                    <div className={styles.formGroup}>
                        <label>Work Mode</label>
                        <div className={styles.selectWrapper}>
                            <select
                                name="workMode"
                                value={formData.workMode}
                                onChange={handleChange}
                                className={styles.select}
                            >
                                {Object.keys(WorkMode).map(mode => (
                                    <option key={mode} value={mode}>
                                        {mode}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>
                </div>

                <div className={styles.formActions}>
                    <button type="button" onClick={onClose} className={styles.cancelButton}>
                        Cancel
                    </button>
                    <button type="submit" disabled={isLoading} className={styles.submitButton}>
                        {isLoading ? "Saving..." : (isEdit ? "Update Job" : "Create Job")}
                    </button>
                </div>
            </form>
        </Modal>
    );
}
