import { useContext } from "react"
import { Context } from "../Context/Context"
import "../Style/navbar.css"
import { Link } from "react-router-dom";

const Navbar = () => {
    const {details} = useContext(Context);
  return (
    <div className="navbar">
        <div className="links">
            <Link className="link" to="/">Home</Link>
        </div>
        {
            details?.role=="User" &&
            <div className="links">
                <Link className="link" to="/generate-request">Generate Request</Link>
            </div>
        }
        {
            details?.role!="User" &&
            <div className="links">
                <Link className="link" to='/request-approval'>Request Approval</Link>
            </div>
        }
        <div className="links links-logout">
            <Link className="link link-logout">Logout</Link>
        </div>
    </div>
  )
}

export default Navbar