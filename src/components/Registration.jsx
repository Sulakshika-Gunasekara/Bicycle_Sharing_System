import { useNavigate } from "react-router-dom";
import "./Registration.css";

function Registration() {
  const navigate = useNavigate();

  const handleRegister = (e) => {
    e.preventDefault();
    // Perform registration logic (validate fields, store data)
    alert("Registration successful!");
    navigate("/");
  };

  return (
    <div className="register-container">
      <img src="/logo.jpeg" alt="My Bike Logo" className="logo" />
      <h1>Welcome New User!</h1>
      <form onSubmit={handleRegister}>
        <label>First Name:</label>
        <input type="text" required />
        <label>Last Name:</label>
        <input type="text" required />
        <label>Email:</label>
        <input type="email" required />
        <label>Mobile Number:</label>
        <input type="tel" required />
        <label>NIC No:</label>
        <input type="text" required />
        <label>Upload NIC Front:</label>
        <input type="file" accept=".png, .jpeg" required />
        <label>Upload NIC Back:</label>
        <input type="file" accept=".png, .jpeg" required />
        <label>Username:</label>
        <input type="text" required />
        <label>Password:</label>
        <input type="password" required />
        <label>Re-enter Password:</label>
        <input type="password" required />
        <label>Credit Card Details:</label>
        <input type="text" placeholder="XXXX-XXXX-XXXX-XXXX" required />
        <button type="submit">Register</button>
        <button type="button" onClick={() => navigate("/")}>
          Back
        </button>
      </form>
    </div>
  );
}

export default Registration;
