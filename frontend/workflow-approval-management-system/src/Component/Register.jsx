import { useState } from "react"
import api from "../api/api";
import { useNavigate } from "react-router-dom";
import "../Style/register.css"

const Register = () => {
    const navigate = useNavigate();
    const [name,setName] = useState();
    const [username,setUsername] = useState();
    const [email,setEmail] = useState();
    const [pass,setPass] = useState();
    const [type,setType] = useState();
    const [error,setError] = useState();
    const handleSubmit = (e) => {
        e.preventDefault();
        if(name==null || username==null || email==null || pass==null || type!=null) {
            setError("Please fill all the above fields");
        }
        if(name!=null && username!=null && email!=null && pass!=null && type!=null) {
            const user = {
                name : name,
                username : username,
                email : email,
                password : pass,
                active : true,
                userRole : {
                    type : type
                }
            }
            console.log(user);
            api.post("/register",user)
            .finally(
                navigate('/login')
            )
        }
    }
  return (
    <div className="register">
        <div className="register-form">
            <h1>Registration Page</h1>
            <form>
                <div className="input-field">
                    Name:<input type="text" value={name} onChange={(e)=>setName(e.target.value)} required/> 
                </div>
                <div className="input-field">
                    Username:<input type="text" value={username} onChange={(e)=>setUsername(e.target.value)} required/> 
                </div>
                <div className="input-field">
                    E-mail:<input type="email" value={email} onChange={(e)=>setEmail(e.target.value)}/> 
                </div>
                <div className="input-field">
                    Pass:<input type="text" value={pass} onChange={(e)=>setPass(e.target.value)}/> 
                </div>
                <div className="input-field">
                    Role:<select  onChange={(e)=>setType(e.target.value)}>
                            <option value="">Select role</option>
                            <option value="User">User</option>
                            <option value="Manager">Manager</option>
                            <option value="H.R.">H.R.</option>
                            <option value="Admin">Admin</option>
                        </select> 
                </div>
                <div className="error">
                    {error && (error)}
                </div>
                    <input className="register-button" type="submit" value="Register" onClick={(e)=> handleSubmit(e)}/>
                    <input className="register-button" type="submit" value="Login" onClick={()=> navigate('/login')}/>
            </form>
        </div>
    </div>
  )
}

export default Register