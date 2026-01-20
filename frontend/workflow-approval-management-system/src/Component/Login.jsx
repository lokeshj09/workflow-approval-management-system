import { useContext, useState } from 'react'
import '../Style/login.css'
import ContextProvider, { Context } from '../Context/Context';
import { useNavigate } from 'react-router-dom';
import api from '../api/api';

const Login = () => {
    const navigate = useNavigate();
    const {token,setToken,setIsAuthenticated} = useContext(Context);
    const [name , setName] = useState('');
    const [pass , setPass] = useState('');
    const [error , setError] = useState('');
    
    const handleSubmit = (e) => {
        e.preventDefault();
        if(name!=null && pass!=null) {
            api.post('/login',{
                "username":name,
                "password":pass
            })
            .then(res=> {
            setToken(res.data);
            setName('');
            setPass('');
            setError('');
            setIsAuthenticated(true);
            navigate("/");
            })
            .catch(error=> {
                setError("Invalid username or password")
            });
        }
    }
  return (
    <div className='login'>
        <div className="login-form">
            <h1 className='login-head'>Login Page</h1>
            <form>
                <div className="login-input">
                    Username:<input className='login-input' type='text' value={name} onChange={(e)=>setName(e.target.value)}/>
                </div>
                <div className="login-input">
                    Password:<input className='login-input' type='password' value={pass} onChange={(e)=>setPass(e.target.value)}/>
                </div>
                
                <div className='error'>{error}</div>
                <input className='login-button' type='submit' onClick={(e)=>{handleSubmit(e)}}/>
                <input className='login-button' type='submit' value="Register" onClick={()=>navigate("/register")}/>
                
            </form>
        </div>
    </div>
  )
}

export default Login