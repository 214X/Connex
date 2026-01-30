// src/lib/api/publicClient.ts
import axios from "axios";

/**
 * Simple api request
 * URL and header (for content type)
 */
const publicClient = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
});

export default publicClient;
