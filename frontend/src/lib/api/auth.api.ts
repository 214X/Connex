import api from "./api";

export interface RegisterRequest {
    email: string;
    password: string;
    confirmPassword: string;
}

export interface RegisterResponse {
    id: number;
    email: string;
    accountType: string;
    createdAt: string;
    updatedAt: String;
}

export const register = async (
    data: RegisterRequest
): Promise<RegisterResponse> => {
    const response = await api.post("/api/auth/register", data);
    return response.data;
};