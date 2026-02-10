import axios from "axios";
import authClient from "@/lib/api/authClient";
import { ApiResponse } from "@/lib/api/types";

/* ---------- ENUMS ---------- */

export enum ApplicationStatus {
    APPLIED = "APPLIED",
    REVIEWING = "REVIEWING",
    REJECTED = "REJECTED",
    ACCEPTED = "ACCEPTED"
}

/* ---------- INTERFACES ---------- */

export interface ApplyJobRequest {
    message?: string;
}

export interface JobApplicationResponse {
    id: number;
    jobPostingId: number;
    applicantProfileId: number;
    status: ApplicationStatus;
    message?: string | null;
    createdAt: string;
}

export interface CompanyJobApplicationItem {
    applicationId: number;
    appliedAt: string;
    status: ApplicationStatus;
    message?: string | null;
    applicantProfileId: number;
    firstName: string;
    lastName: string;
    location: string | null;
}

export interface CompanyJobApplicationsResponse {
    jobPostingId: number;
    applications: CompanyJobApplicationItem[];
}

/* ---------- API METHODS ---------- */

/**
 * Apply to a job posting (Personal User)
 */
export const applyToJob = async (jobPostingId: number, data: ApplyJobRequest): Promise<JobApplicationResponse> => {
    try {
        const res = await authClient.post<ApiResponse<JobApplicationResponse>>(
            `/job-postings/${jobPostingId}/apply`,
            data
        );
        return res.data.data!;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

/**
 * Get applications for a job posting (Company Owner)
 */
export const getJobApplications = async (jobPostingId: number): Promise<CompanyJobApplicationsResponse> => {
    try {
        const res = await authClient.get<ApiResponse<CompanyJobApplicationsResponse>>(
            `/company/job-postings/${jobPostingId}/applications`
        );
        return res.data.data!;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

/**
 * Update application status (Company Owner)
 */
export const updateApplicationStatus = async (applicationId: number, status: ApplicationStatus): Promise<void> => {
    try {
        await authClient.put(`/company/applications/${applicationId}/status`, { status });
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};
