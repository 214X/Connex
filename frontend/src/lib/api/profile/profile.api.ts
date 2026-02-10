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
    experiences?: PersonalProfileExperience[];
    skills?: PersonalProfileSkill[];
    languages?: PersonalProfileLanguage[];
    projects?: PersonalProfileProject[];
    cvFileName?: string | null;
    createdAt: string;
    updatedAt: string;
}

export interface CompanyProfileData {
    id: number;
    userId: number;
    companyName: string;
    description?: string | null;
    industry?: string | null;
    location?: string | null;
    website?: string | null;
}

export interface CompanyProfileEditRequest {
    companyName: string;
    description?: string;
    industry?: string;
    location?: string;
    website?: string;
}

export const updateMyCompanyProfile = async (
    data: CompanyProfileEditRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.put<ApiResponse<void>>("/api/profiles/company/me", data);
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

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
        // Mapped in PersonalProfileController: @PostMapping("/me/contacts")
        const res = await authClient.post<ApiResponse<PersonalProfileContact>>(
            "/api/profiles/personal/me/contacts",
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
        // Mapped in PersonalProfileController: @PatchMapping("/me/contacts/{contactId}")
        const res = await authClient.patch<ApiResponse<PersonalProfileContact>>(
            `/api/profiles/personal/me/contacts/${contactId}`,
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
        // Mapped in PersonalProfileController: @DeleteMapping("/me/contacts/{contactId}")
        await authClient.delete(`/api/profiles/personal/me/contacts/${contactId}`);
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
        const res = await authClient.post<ApiResponse<void>>("/api/profiles/personal/me/educations", data);
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
            `/api/profiles/personal/me/educations/${educationId}`,
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
        await authClient.delete(`/api/profiles/personal/me/educations/${educationId}`);
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

/* ---------- EXPERIENCE ---------- */

export interface PersonalProfileExperience {
    id: number;
    title: string;
    organization: string;
    startDate?: string | null;
    endDate?: string | null;
    description?: string | null;
}

export interface CreatePersonalExperienceRequest {
    title: string;
    organization: string;
    startDate?: string;
    endDate?: string;
    description?: string;
}

export interface EditPersonalExperienceRequest {
    title?: string;
    organization?: string;
    startDate?: string;
    endDate?: string;
    description?: string;
}

export const addExperience = async (
    data: CreatePersonalExperienceRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.post<ApiResponse<void>>("/api/profiles/personal/me/experiences", data);
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const updateExperience = async (
    experienceId: number,
    data: EditPersonalExperienceRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.patch<ApiResponse<void>>(
            `/api/profiles/personal/me/experiences/${experienceId}`,
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

export const deleteExperience = async (
    experienceId: number
): Promise<void> => {
    try {
        await authClient.delete(`/api/profiles/personal/me/experiences/${experienceId}`);
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};


/* ---------- SKILLS ---------- */

export interface PersonalProfileSkill {
    id: number;
    name: string;
    description?: string | null;
    level?: number | null; // 0-10
}

export interface CreatePersonalSkillRequest {
    name: string;
    description?: string;
    level?: number;
}

export interface EditPersonalSkillRequest {
    name?: string;
    description?: string;
    level?: number;
}

export const addSkill = async (
    data: CreatePersonalSkillRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.post<ApiResponse<void>>("/api/profiles/personal/me/skills", data);
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const updateSkill = async (
    skillId: number,
    data: EditPersonalSkillRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.patch<ApiResponse<void>>(
            `/api/profiles/personal/me/skills/${skillId}`,
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

export const deleteSkill = async (
    skillId: number
): Promise<void> => {
    try {
        await authClient.delete(`/api/profiles/personal/me/skills/${skillId}`);
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

/* ---------- LANGUAGES ---------- */

export interface PersonalProfileLanguage {
    id: number;
    language: string;
    level: string; // A1, A2, B1, B2, C1, C2, Native
}

export interface CreatePersonalLanguageRequest {
    language: string;
    level: string;
}

export interface EditPersonalLanguageRequest {
    language?: string;
    level?: string;
}

export const addLanguage = async (
    data: CreatePersonalLanguageRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.post<ApiResponse<void>>("/api/profiles/personal/me/languages", data);
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const updateLanguage = async (
    languageId: number,
    data: EditPersonalLanguageRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.patch<ApiResponse<void>>(
            `/api/profiles/personal/me/languages/${languageId}`,
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

export const deleteLanguage = async (
    languageId: number
): Promise<void> => {
    try {
        await authClient.delete(`/api/profiles/personal/me/languages/${languageId}`);
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

/* ---------- PROJECTS ---------- */

export interface PersonalProfileProject {
    id: number;
    name: string;
    shortDescription?: string | null;
    description?: string | null;
    link?: string | null;
    startDate?: string | null;
    endDate?: string | null;
}

export interface CreatePersonalProjectRequest {
    name: string;
    shortDescription?: string;
    description?: string;
    link?: string;
    startDate?: string;
    endDate?: string;
}

export interface EditPersonalProjectRequest {
    name?: string;
    shortDescription?: string;
    description?: string;
    link?: string;
    startDate?: string;
    endDate?: string;
}

export const addProject = async (
    data: CreatePersonalProjectRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.post<ApiResponse<void>>("/api/profiles/personal/me/projects", data);
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const updateProject = async (
    projectId: number,
    data: EditPersonalProjectRequest
): Promise<ApiResponse<void>> => {
    try {
        const res = await authClient.patch<ApiResponse<void>>(
            `/api/profiles/personal/me/projects/${projectId}`,
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

export const deleteProject = async (
    projectId: number
): Promise<void> => {
    try {
        await authClient.delete(`/api/profiles/personal/me/projects/${projectId}`);
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

/* ---------- CV ---------- */

export const uploadCv = async (file: File): Promise<ApiResponse<void>> => {
    try {
        const formData = new FormData();
        formData.append("file", file);

        const res = await authClient.post<ApiResponse<void>>(
            "/api/profiles/personal/me/cv",
            formData,
            {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            }
        );
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const deleteCv = async (): Promise<void> => {
    try {
        await authClient.delete("/api/profiles/personal/me/cv");
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};

export const getCvUrl = (profileId: number): string => {
    // Assuming backend URL is relative or proxied. If absolute needed, prepend BASE_URL.
    // For now, return relative path which Axios/Browser handles regarding proxy.
    // But for <a href> download, we might need full URL if not on same domain,
    // or relative if served from same origin (Next.js proxy).
    // Let's assume relative path works with Next.js proxy setup or same origin.
    // If using external backend URL, strictly speaking we should prepend it.
    // Given authClient setup usually has baseURL, we might need to access it or just hardcode relative.
    return `${process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080"}/api/profiles/personal/${profileId}/cv`;
};
