import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Login.css";

function Login() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");

    const [password, setPassword] = useState("");

    const [successMessage, setSuccessMessage] = useState("");
    
    const [errors, setErrors] = useState({
    email: "",
    password: ""
});

const login = async () => {

    const newErrors = {};

    if (!email.trim())
        newErrors.email = "Email is required";

    if (!password.trim())
        newErrors.password = "Password is required";

    setErrors(newErrors);

    if (Object.keys(newErrors).length > 0)
        return;

    try {

        const response = await axios.post(
            "http://localhost:8080/auth/login",
            {
                email,
                password
            }
        );

        // Invalid credentials check
        if (response.data === "Invalid Credentials") {

            setErrors({
                email: "Invalid Email or Password",
                password: ""
            });

            return;
        }

        // Save JWT Token
        localStorage.setItem("token", response.data);

        setSuccessMessage("Login Successful!");

        setTimeout(() => {
            navigate("/home");
        }, 1000);

    }

    catch (error) {

        if (error.response) {

            setErrors({
                email: "Invalid Email or Password",
                password: ""
            });

        } else {

            setErrors({
                email: "Unable to connect to server",
                password: ""
            });

        }

    }

};

    return (

        <div className="login-container">

            <div className="login-card">

                <h2>Welcome Back</h2>

                {successMessage && (
                    <p className="success-message">
                        {successMessage}
                    </p>
                )}

                <p className="subtitle">
                    Login to Shabdha Sethu
                </p>

                <input
    type="email"
    placeholder="Email"
    value={email}
    onChange={(e) => {

        setEmail(e.target.value);

        setErrors({
            ...errors,
            email: ""
        });

    }}
/>

{errors.email && (
    <p className="error-text">
        {errors.email}
    </p>
)}

               <input
    type="password"
    placeholder="Password"
    value={password}
    onChange={(e) => {

        setPassword(e.target.value);

        setErrors({
            ...errors,
            password: ""
        });

    }}
/>

{errors.password && (
    <p className="error-text">
        {errors.password}
    </p>
)}

                <button
                    className="login-btn"
                    onClick={login}
                >
                    Login
                </button>

                <p className="register-text">
                    Don't have an account?
                </p>

                <button
                    className="register-btn"
                    onClick={() => navigate("/")}
                >
                    Register
                </button>

            </div>

        </div>

    );

}

export default Login;