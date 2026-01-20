import React, { useContext, useEffect } from 'react'
import Navbar from './Navbar'
import '../Style/req-app.css'
import { Context } from '../Context/Context'
import api from '../api/api'

const RequestApproval = () => {
    const {token,details,request,setRequest} = useContext(Context);
    let count = 0;
    const handleApproval= (req,approval) => {
        count++;
        if(count==1) {
            api.post('/request-approval',{
            "req":req,
            "isAccepted":approval,
            "user":details
        })
        }
    }
    useEffect(()=> {
        if(!details?.role) return;
        api.get(`/${details.role.toLowerCase()}/requests`,{
            headers : {
                Authorization : `Bearer ${token}`
            }
        })
        .then(res => setRequest(res.data))
        .finally(console.log(request)
        )
    },[token])
  return (
    <div className='requests'>
        <Navbar/>
        <div className="request-approval">
            
            <div className="req-card">
                {
                    request?.map((req)=>(
                        <div className='req' key={req.id}>
                            <h3 className='req-title'><span>Title : </span>{req.reqTitle} </h3>
                            <p className='req-det'><span> Description :</span> {req.reqDesc}</p>
                            <p className='req-det'><span>Type :</span> {req.reqType}</p>
                            <p className='req-det'><span>Created by : </span>{req.user}</p>
                            <p className='req-det'><span>Username :</span> {req.username}</p>
                            <div className="buttons">
                                <button onClick={()=> handleApproval(req,true)} className='button accept'>Accept</button>
                                <button onClick={()=> handleApproval(req,false)} className='button reject'>Reject</button>
                            </div>
                        </div>
                    ))
                }
            </div>
        </div>
    </div>
  )
}

export default RequestApproval