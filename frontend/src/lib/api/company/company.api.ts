import axios from "axios";
import { CompanyProfileData } from "@/lib/api/profile/profile.api";

import { CompanyProfileData } from "@/lib/api/profile/profile.api";

const API_URL = "http://localhost:8080/api/search";

export const companyApi = {
    getRandomCompanies: async (): Promise<CompanyProfileData[]> => {
        const response = await axios.get(`${API_URL}/companies/random`, {
            withCredentials: true,
        });
        return response.data;
    },

    searchCompanies: async (query: string): Promise<CompanyProfileData[]> => {
        const response = await axios.get(`${API_URL}/companies`, {
            params: { query },
            withCredentials: true,
        });
        return response.data;
    },
};
