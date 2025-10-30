import React, { useState } from "react";
import api from "../api/axiosConfig";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const [form, setForm] = useState({ username: "", password: "", role: "ROLE_USER" });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post("/auth/register", form);
      setMessage("Registration successful! Redirecting...");
      setTimeout(() => navigate("/login"), 1500);
    } catch {
      setMessage("Error registering user");
    }
  };

  return (
    <div className="auth-container">
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Username"
          onChange={(e) => setForm({ ...form, username: e.target.value })}
        />
        <input type="password" placeholder="Password"
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />
        <button type="submit">Register</button>
        <p>{message}</p>
      </form>
    </div>
  );
};

export default Register;