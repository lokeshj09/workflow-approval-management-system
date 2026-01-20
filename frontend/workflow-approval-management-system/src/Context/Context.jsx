import { createContext, useEffect, useState } from "react";
import { setupInterceptors } from "../api/setUpInterceptor";
import api from "../api/api";

export const Context = createContext();

const ContextProvider = ({children}) => {
  const [details,setDetails] = useState(null);
  const [request,setRequest] = useState(null);
  const [token,setToken] = useState(null);
  const [isAuthenticated,setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);


  useEffect(() => {
    api.post("/refresh/token", {}, { withCredentials: true })
      .then(res => {
        localStorage.setItem("accessToken", res.data);
        setToken(res.data);
        setIsAuthenticated(true);
      })
      .catch(() => {
        localStorage.removeItem("accessToken");
        setIsAuthenticated(false);
      })
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    setupInterceptors(setToken, setIsAuthenticated);
  }, []);

  if (loading) return null;


  return (
    <Context.Provider value={{token,setToken,details,setDetails,
      request,setRequest,
      loading,setLoading,isAuthenticated,setIsAuthenticated
    }}>
        {children}
    </Context.Provider>
  )
}

export default ContextProvider;