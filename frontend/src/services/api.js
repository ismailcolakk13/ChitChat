import axios from "axios";

export const apiURL = import.meta.env.VITE_API_URL;

const api = axios.create({
  // baseURL: "https://chitchat-qe8b.onrender.com/api",
  baseURL: apiURL+"/api",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
  