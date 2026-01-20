import React, { useContext, useEffect, useState } from 'react'
import { Context } from '../Context/Context';
import api from '../api/api';
import Navbar from './Navbar';
import '../Style/home.css'

const Home = () => {
    const {token,details,setDetails,request,setRequest} = useContext(Context);
    useEffect(()=>{
        api.get("/getDetails",{
            headers : {
                Authorization: `Bearer ${token}`
            }
        })
        .then(req => setDetails(req.data))
    },[token])
    useEffect(()=> {
        if (!token || !details?.username) return;
        api.get("/displayReq",
            {
                params: {username: details.username},
                headers: {
                    Authorization:`Bearer ${token}`
                }
            }
        )
        .then(res=> setRequest(res.data))
        .catch(err=> console.error(err))
    },[token,details])
  return (
    <div className='home'>
        <Navbar/>
        <div className="home-title">
            {details?
                <h1>Welcome {details.role} , {details.name}</h1> :
                <p>Loading...</p>
            }
        </div>
        <hr></hr>
        {details?.role == "User" &&
        <div className="request-created">
            {request?.map((data)=>(
                <div className='req-card' key={data.id}>
                    <div className="req-title">
                        Title : {data.reqTitle}
                    </div>
                    <div className="req-desc">
                        Description : {data.reqDesc}
                    </div>
                    <div className="req-user">
                        Name : {data.user}
                    </div>
                    <div className="username">
                        Username : {data.username}
                    </div>
                    <div className="req-type">
                        {data.reqType}
                    </div>
                    <div className={`req-status`}>
                        Status : <span className= {`${data.Status=="Pending"?"pending":data.Status=="Approved"?"approved":data.Status=="Rejected"?"rejected":""}`}>{data.Status}</span> 
                    </div>
                </div>
            ))}
        </div>
        }
    </div>
  )
}

export default Home