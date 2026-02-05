import axios from "axios";
import authClient from "@/lib/api/authClient";
import publicClient from "@/lib/api/publicClient";
import { ApiResponse } from "@/lib/api/types";

/**
 * Upload avatar for the current user.
 * POST /api/profiles/me/avatar (multipart/form-data)
 */
export const uploadMyAvatar = async (file: File): Promise<ApiResponse<void>> => {
    try {
        const formData = new FormData();
        formData.append("file", file);

        const res = await authClient.post<ApiResponse<void>>(
            "/api/profiles/me/avatar",
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

/**
 * Get avatar URL for a profile.
 * GET /api/profiles/{profileId}/avatar
 * 
 * Returns the full URL to fetch the avatar image.
 * Append a cache-busting timestamp when needed.
 */
export const getAvatarUrl = (profileId: number, cacheBust?: number): string => {
    const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL || "";
    const url = `${baseUrl}/api/profiles/${profileId}/avatar`;
    if (cacheBust) {
        return `${url}?t=${cacheBust}`;
    }
    return url;
};

/**
 * Delete avatar for the current user.
 * DELETE /api/profiles/me/avatar
 */
export const deleteMyAvatar = async (): Promise<void> => {
    try {
        await authClient.delete("/api/profiles/me/avatar");
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data ?? err;
        }
        throw err;
    }
};
