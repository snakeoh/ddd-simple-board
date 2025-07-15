import axios from "axios";
import { getAccessToken } from "../auth/token";

const api = axios.create({
    baseURL: "http://localhost:8080",
});

api.interceptors.request.use(
    (config) => {
        const token = getAccessToken();
        // const token = localStorage.getItem("accessToken");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default api;
