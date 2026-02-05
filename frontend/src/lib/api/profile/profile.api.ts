import axios from "axios";
import publicClient from "@/lib/api/publicClient";
import authClient from "@/lib/api/authClient";
import { ApiResponse } from "@/lib/api/types";

/* ---------- TYPES ---------- */

export type AccountType = "PERSONAL" | "COMPANY";

export interface ProfileResponse {
    id: number;
    userId: number;
    accountType: AccountType;

    personal?: PersonalProfileData | null;
    company?: CompanyProfileData | null;
}

export interface PersonalProfileData {
    firstName: string;
    lastName: string;
    profileDescription?: string | null;
    phoneNumber?: string | null;
    location?: string | null;
    contacts?: PersonalProfileContact[];
    educations?: PersonalProfileEducation[];
    createdAt: string;
    updatedAt: string;
}

export interface CompanyProfileData {
    companyName: string;
    description?: string | null;
    industry?: string | null;
    location?: string | null;
    website?: string | null;
}

/* ---------- PUBLIC ---------- */
/**
 * Public profile (any user can see)
 * GET /api/profiles/{userId}
 */
export const getProfileByUserId = async (
    userId: number
): Promise<ApiResponse<ProfileResponse>> => {
    try {
        const res = await publicClient.get<ApiResponse<ProfileResponse>>(
            `/api/profiles/${userId}`
        );
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export interface PersonalProfileEditRequest {
    firstName: string;
    lastName: string;
    profileDescription?: string;
    phoneNumber?: string;
    location?: string;
}

export const updateMyProfile = async (
    data: PersonalProfileEditRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.patch<ApiResponse<void>>("/api/profiles/personal/me", data);
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

/* ---------- AUTH (CLIENT SIDE ONLY) ---------- */
/**
 * Logged-in user's own profile
 * GET /api/profiles/me
 *
 * ⚠️ CSR ONLY
 */
export const getMyProfile = async (): Promise<ApiResponse<ProfileResponse>> => {
    try {
        const res = await authClient.get<ApiResponse<ProfileResponse>>(
            "/api/profiles/me"
        );
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

/* ---------- CONTACTS ---------- */

export enum ContactType {
    PHONE = "PHONE",
    EMAIL = "EMAIL",
    WEBSITE = "WEBSITE",
}

export interface PersonalProfileContact {
    id: number;
    type: ContactType;
    value: string;
}

export const addPersonalContact = async (
    type: ContactType,
    value: string
): Promise<ApiResponse<PersonalProfileContact>> => {
    try {
        // Mapped in ProfileController: @PostMapping("/me/contacts") -> /api/profiles/me/contacts
        const res = await authClient.post<ApiResponse<PersonalProfileContact>>(
            "/api/profiles/me/contacts",
            { type, value }
        );
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const updatePersonalContact = async (
    contactId: number,
    data: { type?: ContactType; value?: string }
): Promise<ApiResponse<PersonalProfileContact>> => {
    try {
        // Mapped in ProfileController: @PatchMapping("/me/contacts/{contactId}")
        const res = await authClient.patch<ApiResponse<PersonalProfileContact>>(
            `/api/profiles/me/contacts/${contactId}`,
            data
        );
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const deletePersonalContact = async (
    contactId: number
): Promise<void> => {
    try {
        // Mapped in ProfileController: @DeleteMapping("/me/contacts/{contactId}")
        await authClient.delete(`/api/profiles/me/contacts/${contactId}`);
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

/* ---------- EDUCATION ---------- */

export interface PersonalProfileEducation {
    id: number;
    schoolName: string;
    department?: string | null;
    startDate?: string | null;
    endDate?: string | null;
}

export interface CreatePersonalEducationRequest {
    schoolName: string;
    department?: string;
    startDate?: string;
    endDate?: string;
}

export interface EditPersonalEducationRequest {
    schoolName?: string;
    department?: string;
    startDate?: string;
    endDate?: string;
}

export const addEducation = async (
    data: CreatePersonalEducationRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.post<ApiResponse<void>>("/api/profiles/me/educations", data);
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const updateEducation = async (
    educationId: number,
    data: EditPersonalEducationRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.patch<ApiResponse<void>>(
            `/api/profiles/me/educations/${educationId}`,
            data
        );
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const deleteEducation = async (
    educationId: number
): Promise<void> => {
    try {
        await authClient.delete(`/api/profiles/me/educations/${educationId}`);
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};
