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
