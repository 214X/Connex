import axios from "axios";
import authClient from "@/lib/api/authClient";
import publicClient from "@/lib/api/publicClient";
import { ApiResponse } from "@/lib/api/types";

/* ---------- ENUMS ---------- */

export enum JobType {
    FULL_TIME = "FULL_TIME",
    PART_TIME = "PART_TIME",
    INTERN = "INTERN",
    CONTRACT = "CONTRACT"
}

export enum WorkMode {
    REMOTE = "REMOTE",
    ONSITE = "ONSITE",
    HYBRID = "HYBRID"
}

export enum JobStatus {
    DRAFT = "DRAFT",
    PUBLISHED = "PUBLISHED",
    CLOSED = "CLOSED"
}

/* ---------- INTERFACES ---------- */

export interface JobPosting {
    id: number;
    title: string;
    position: string;
    description: string;
    location: string;
    skills: string[];
    jobType: JobType;
    workMode: WorkMode;
    status: JobStatus;
    publishedAt?: string | null;
    closedAt?: string | null;
    applicationCount: number;
    companyId: number;
    companyName: string;
    companyLogo?: string | null;
    createdAt: string;
    updatedAt: string;
}

export interface CreateJobPostingRequest {
    title: string;
    position: string;
    description: string;
    location: string;
    skills: string[];
    jobType: JobType;
    workMode: WorkMode;
}

export interface UpdateJobPostingRequest {
    title?: string;
    position?: string;
    description?: string;
    location?: string;
    skills?: string[];
    jobType?: JobType;
    workMode?: WorkMode;
}

/* ---------- API METHODS ---------- */

/* --- COMPANY OWNER ACTIONS --- */

export const createJob = async (data: CreateJobPostingRequest): Promise<JobPosting> => {
    try {
        const res = await authClient.post<ApiResponse<JobPosting>>("/jobs", data);
        return res.data.data!;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const updateJob = async (id: number, data: UpdateJobPostingRequest): Promise<JobPosting> => {
    try {
        const res = await authClient.put<ApiResponse<JobPosting>>(`/jobs/${id}`, data);
        return res.data.data!;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const deleteJob = async (id: number): Promise<void> => {
    try {
        await authClient.delete<ApiResponse<void>>(`/jobs/${id}`);
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const publishJob = async (id: number): Promise<void> => {
    try {
        await authClient.post<ApiResponse<void>>(`/jobs/${id}/publish`);
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const closeJob = async (id: number): Promise<void> => {
    try {
        await authClient.post<ApiResponse<void>>(`/jobs/${id}/close`);
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const getMyCompanyJobs = async (): Promise<JobPosting[]> => {
    try {
        const res = await authClient.get<ApiResponse<JobPosting[]>>("/jobs/my");
        return res.data.data!;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

/* --- PUBLIC ACTIONS --- */

export const getPublishedJobs = async (): Promise<JobPosting[]> => {
    try {
        const res = await publicClient.get<ApiResponse<JobPosting[]>>("/jobs");
        return res.data.data!;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const getJobDetail = async (id: number): Promise<JobPosting> => {
    try {
        const res = await publicClient.get<ApiResponse<JobPosting>>(`/jobs/${id}`);
        return res.data.data!;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const getCompanyPublicJobs = async (userId: number): Promise<JobPosting[]> => {
    try {
        const res = await publicClient.get<ApiResponse<JobPosting[]>>(`/jobs/company/${userId}`);
        return res.data.data!;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const searchJobs = async (query: string): Promise<JobPosting[]> => {
    try {
        const res = await authClient.get<ApiResponse<JobPosting[]>>("/jobs/search", {
            params: { q: query }
        });
        return res.data.data!;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};
