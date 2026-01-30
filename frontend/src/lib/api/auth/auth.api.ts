// src/lib/api/auth.ts
import axios from "axios";
import publicClient from "../publicClient";
import authClient from "../authClient";
import { ApiResponse } from "../types";

/* ---------- TYPES ---------- */

export interface RegisterRequest {
    email: string;
    password: string;
    confirmPassword: string;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface LoginResponse {
    token: string;
    expiresIn: number;
}

export interface UserResponse {
    id: number;
    email: string;
    accountType: "PERSONAL" | "COMPANY";
    createdAt: string;
    updatedAt: string;
}

/* ---------- PUBLIC ---------- */

export const register = async (
    data: RegisterRequest
): Promise<ApiResponse<UserResponse>> => {
    try {
        const res = await publicClient.post<ApiResponse<UserResponse>>(
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

export const login = async (
    data: LoginRequest
): Promise<ApiResponse<LoginResponse>> => {
    try {
        const res = await publicClient.post<ApiResponse<LoginResponse>>(
            "/api/auth/login",
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

/* ---------- AUTH REQUIRED ---------- */

export const getMe = async (): Promise<ApiResponse<UserResponse>> => {
    try {
        const res = await authClient.get<ApiResponse<UserResponse>>(
            "/api/users/me"
        );
        return res.data;
    } catch (err) {
        if (axios.isAxiosError(err)) {
            throw err.response?.data as ApiResponse<never>;
        }
        throw err;
    }
};
