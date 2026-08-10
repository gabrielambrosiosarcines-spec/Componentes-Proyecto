import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FiCheckSquare, FiEye, FiEyeOff } from "react-icons/fi";
import api from "../api/axiosConfig";

function Login() {
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const [form, setForm] = useState({ usuario: "", contrasena: "" });

 const handleSubmit = async (event) => {
  event.preventDefault();

  if (!form.usuario.trim() || !form.contrasena.trim()) {
    alert("Completa el usuario y la contraseña.");
    return;
  }

  try {
    const response = await api.post("/auth/login", {
      username: form.usuario,
      password: form.contrasena,
    });

    const { token, username, rol } = response.data;

    localStorage.setItem("token", token);
    localStorage.setItem("username", username);
    localStorage.setItem("rol", rol);

    navigate("/inicio");

  } catch (error) {
    console.error("Error en login:", error);

    if (error.response?.status === 401) {
      alert("Usuario o contraseña incorrectos.");
    } else {
      alert("No se pudo conectar con el servidor.");
    }
  }
};

  return (
    <div className="login-page">
      <form className="login-card" onSubmit={handleSubmit}>
        <div className="login-logo">
          <FiCheckSquare />
        </div>

        <h1>SISTEMA DE VOTACIÓN</h1>
        <p>Inicia sesión para continuar</p>

        <label>
          Usuario
          <input
            type="text"
            placeholder="Ingrese su usuario"
            value={form.usuario}
            onChange={(event) =>
              setForm({ ...form, usuario: event.target.value })
            }
          />
        </label>

        <label>
          Contraseña
          <div className="password-input">
            <input
              type={showPassword ? "text" : "password"}
              placeholder="Ingrese su contraseña"
              value={form.contrasena}
              onChange={(event) =>
                setForm({ ...form, contrasena: event.target.value })
              }
            />
            <button
              type="button"
              onClick={() => setShowPassword((value) => !value)}
            >
              {showPassword ? <FiEyeOff /> : <FiEye />}
            </button>
          </div>
        </label>

        <button className="login-button" type="submit">
          Iniciar sesión
        </button>
      </form>

      <footer>© 2026 Sistema de Votación</footer>
    </div>
  );
}

export default Login;
