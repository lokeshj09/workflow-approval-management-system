import React, { useContext, useState } from 'react'
import Navbar from './Navbar'
import '../Style/gen_req.css'
import api from '../api/api';
import { Context } from '../Context/Context';

const Generate_Request = () => {
  const {details} = useContext(Context);
  const [title,setTitle] = useState();
  const [desc,setDesc] = useState();
  const [reqType,setReqType] = useState();
  const handleSubmit = (e) => {
    e.preventDefault();
    if(title!=null && desc!=null && reqType!=null) {
      api.post('/genRequest',{
        "reqTitle":title,
        "reqDesc":desc,
        "user":details?.user,
        "username":details?.username,
        "reqType":reqType
      })
    }
    setTitle('');
    setDesc('');
    setReqType('');
  }

  return (
    <div className=''>
        <Navbar/>
        <div className="gen-req">
            <form className='form'>
              <h1 className='form-title'>Request Form</h1>
              <div className="input">
                Title: <input onChange={(e)=> setTitle(e.target.value)} type="text" value={title} />
              </div>
              <div className="input">
                Description: <textarea onChange={(e)=> setDesc(e.target.value)} type="text" rows="9" value={desc}/>
              </div>
              <div className="input">
                Request Type: <select onChange={(e)=> setReqType(e.target.value)} className='select' value={reqType}>
                  <option value="">Choose an option</option>
                  <option>Employee Requests</option>
                  <option>Technical Requests</option>
                  <option>Finance Requests</option>
                  <option>Project / Work Management Requests</option>
                  <option>Others</option>
                </select>
              </div>
              <div className="input">
                <input className='button' type='submit' value='Create request' onClick={(e)=>handleSubmit(e)}/>
              </div>
            </form>
        </div>
    </div>
  )
}

export default Generate_Request