import api from "./client";
import axios from "axios";
import { ApiResponse } from "./types";

export interface RegisterRequest {
    email: string;
    password: string;
    confirmPassword: string;
}

export interface UserResponse {
    id: number;
    email: string;
    accountType: string;
    createdAt: string;
    updatedAt: string;
}

export const register = async (
    data: RegisterRequest
): Promise<ApiResponse<UserResponse>> => {
    try {
        const res = await api.post<ApiResponse<UserResponse>>(
            "/api/auth/register",
            data
        );
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data as ApiResponse<never>;
        }
        throw err;
    }
};
