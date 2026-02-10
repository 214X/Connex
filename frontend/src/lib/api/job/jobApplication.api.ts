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
    applicantUserId: number;
    firstName: string;
    lastName: string;
    location: string | null;
    responseNote?: string;
}

export interface CompanyJobApplicationsResponse {
    jobPostingId: number;
    applications: CompanyJobApplicationItem[];
}

export interface MyJobApplicationResponse {
    id: number;
    jobPostingId: number;
    jobTitle: string;
    companyName: string;
    companyUserId?: number; // Added for profile navigation
    companyLogoUrl?: string;
    status: ApplicationStatus;
    responseNote?: string;
    appliedAt: string;
}

/* ---------- API METHODS ---------- */

/**
 * Get current user's job applications
 */
export const getMyApplications = async (): Promise<MyJobApplicationResponse[]> => {
    try {
        const res = await authClient.get<ApiResponse<MyJobApplicationResponse[]>>("/my-applications");
        return res.data.data!;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

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
export const updateApplicationStatus = async (applicationId: number, status: ApplicationStatus, note?: string): Promise<void> => {
    try {
        await authClient.put(`/company/applications/${applicationId}/status`, { status, note });
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};
