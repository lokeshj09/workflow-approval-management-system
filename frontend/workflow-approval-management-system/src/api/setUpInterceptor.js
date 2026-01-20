import api from "./api";

export const setupInterceptors = (setToken, setIsAuthenticated) => {

  api.interceptors.request.use(config => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  });

  api.interceptors.response.use(
    res => res,
    async err => {
      if (err.response?.status === 401) {
        try {
          const res = await api.post("/refresh/token", {}, { withCredentials: true });
          localStorage.setItem("accessToken", res.data);

          setToken(res.data);
          setIsAuthenticated(true);

          err.config.headers.Authorization = `Bearer ${res.data}`;
          return api(err.config);
        } catch {
          setToken(null);
          setIsAuthenticated(false);
        }
      }
      return Promise.reject(err);
    }
  );
};
