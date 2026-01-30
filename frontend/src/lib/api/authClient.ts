// src/lib/api/authClient.ts
import axios from "axios";
import { getAccessToken, clearAccessToken } from "@/lib/api/auth/token";

const authClient = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
});

/**
 * Request interceptor -> add token
 * Interceptor for refreshed situations
 */
authClient.interceptors.request.use((config) => {
    const token = getAccessToken();

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

export default authClient;
