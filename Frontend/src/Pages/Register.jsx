    import { useState } from "react";
    import { useNavigate } from "react-router-dom";
    import axios from "axios";
    import "./Register.css";

    function Register() {

        const navigate = useNavigate();

        const [form, setForm] = useState({
            name: "",
            email: "",
            phone: "",
            password: ""
        });

        const [successMessage, setSuccessMessage] = useState("");

        const handleChange = (e) => {

        setForm({
            ...form,
            [e.target.name]: e.target.value
        });

        setErrors({
            ...errors,
            [e.target.name]: ""
        });

    };

        const [errors, setErrors] = useState({
            name: "",
            email: "",
            phone: "",
            password: ""
        });

        const register = async () => {

            const newErrors = {};

    if (!form.name.trim())
        newErrors.name = "Full Name is required";

    if (!form.email.trim())
        newErrors.email = "Email is required";

    if (!form.phone.trim())
        newErrors.phone = "Phone Number is required";

    if (!form.password.trim())
        newErrors.password = "Password is required";

    setErrors(newErrors);

    if (Object.keys(newErrors).length > 0)
        return;
            try {

                const response = await axios.post(
                    "http://localhost:8080/auth/register",
                    form
                );

                setSuccessMessage(response.data);

                setTimeout(() => {
                    navigate("/login");
                }, 1500);

            }

            catch (error) {

                if (error.response) {

                    alert(error.response.data);

                } else {

                    alert("Unable to connect to server");

                }

            }

        };

        return (

            <div className="register-container">

                <div className="register-card">

                    <h2>Create Account</h2>

                    {successMessage && (
                        <p className="success-message">
                            {successMessage}
                        </p>
                    )}

                    <p className="subtitle">
                        Register to Shabdha Sethu
                    </p>

                    <input
        type="text"
        name="name"
        placeholder="Full Name"
        value={form.name}
        onChange={handleChange}
    />

    {errors.name && (
        <p className="error-text">{errors.name}</p>
    )}
                <input
        type="email"
        name="email"
        placeholder="Email"
        value={form.email}
        onChange={handleChange}
    />

    {errors.email && (
        <p className="error-text">{errors.email}</p>
    )}
                    <input
        type="text"
        name="phone"
        placeholder="Phone Number"
        value={form.phone}
        onChange={handleChange}
    />

    {errors.phone && (
        <p className="error-text">{errors.phone}</p>
    )}

                    <input
        type="password"
        name="password"
        placeholder="Password"
        value={form.password}
        onChange={handleChange}
    />

    {errors.password && (
        <p className="error-text">{errors.password}</p>
    )}

                    <button
                        className="register-btn"
                        onClick={register}
                    >
                        Register
                    </button>

                    <p className="login-text">
                        Already have an account?
                    </p>

                    <button
                        className="login-btn"
                        onClick={() => navigate("/login")}
                    >
                        Login
                    </button>

                </div>

            </div>

        );

    }

    export default Register;