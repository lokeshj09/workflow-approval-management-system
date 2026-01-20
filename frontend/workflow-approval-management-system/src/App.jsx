import {BrowserRouter, Route, Routes } from 'react-router-dom'
import Home from './Component/Home'
import Login from './Component/Login'
import ProtectedRoute from './Component/ProtectedRoute'
import Navbar from './Component/Navbar'
import Register from './Component/Register'
import Generate_Request from './Component/Generate_Request'
import RequestApproval from './Component/RequestApproval'


function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/login' element={<Login/>}/>
        <Route path='/register' element={<Register/>}/>
        <Route path='/' element={
          <ProtectedRoute>
            <Home/>
          </ProtectedRoute>
        }/>
        <Route path='/generate-request' element={
          <ProtectedRoute>
            <Generate_Request/>
          </ProtectedRoute>
        }/>
        <Route path='/request-approval' element={
          <ProtectedRoute>
            <RequestApproval/>
          </ProtectedRoute>
        } />
      </Routes>
    </BrowserRouter>
  )
}

export default App
