import { useContext, useEffect, useState } from "react"
import { Context } from "../Context/Context"
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({children}) => {
    const {isAuthenticated,loading} = useContext(Context);

    if (loading) return <div>Loading...</div>;
    
  return isAuthenticated? children : <Navigate to='/login' replace/>
}

export default ProtectedRoute