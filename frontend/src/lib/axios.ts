import axios from "axios";


const api = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_BASE_URL,
    headers: {
        "Content-Type" : "Application/json"
    }
})

export default api;

