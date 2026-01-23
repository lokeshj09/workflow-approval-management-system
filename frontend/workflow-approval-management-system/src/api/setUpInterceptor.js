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
      const originalRequest = err.config;
      if (err.response?.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;
        try {
          const res = await api.post(
            "/refresh/token",
            {},
            { withCredentials: true }
          );
          const newToken = res.data;
          setToken(newToken);
          setIsAuthenticated(true);
          if(token && token.includes("."))
            originalRequest.headers.Authorization = `Bearer ${newToken}`;
          else
            delete originalRequest.headers.Authorization;
          return api(originalRequest);
        } catch {
          setToken(null);
          setIsAuthenticated(false);
        }
      }
      return Promise.reject(err);
    }
  );
};
