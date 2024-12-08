import { Link, useNavigate } from "react-router-dom";
import "./Login.css";

function Login() {
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    // Perform login logic (validate username/password)
    navigate("/");
  };

  return (
    <div className="login-container">
      <img src="/logo.jpeg" alt="My Bike Logo" className="logo" />
      <h1>Welcome to MyBike</h1>
      <form onSubmit={handleLogin}>
        <label>Username:</label>
        <input type="text" placeholder="Enter your username" required />
        <label>Password:</label>
        <input type="password" placeholder="Enter your password" required />
        <button type="submit">Login</button>
      </form>
      <div className="links">
        <Link to="/register">Don't have an account? Sign up</Link>
        <p>Forgot Password?</p>
      </div>
    </div>
  );
}

export default Login;
