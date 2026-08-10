import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { FiArrowLeft, FiEye, FiEyeOff } from "react-icons/fi";
import Layout from "../components/Layout";
import api from "../api/axiosConfig";

function FormularioUsuario() {
  const navigate = useNavigate();
  const { id } = useParams();

  const [showPassword, setShowPassword] = useState(false);

  const [form, setForm] = useState({
    usuario: "",
    contrasena: "",
    rol: "",
  });

  useEffect(() => {
    if (id) {
      cargarUsuario();
    }
  }, [id]);

  const cargarUsuario = async () => {
    try {
      const response = await api.get(`/usuarios/${id}`);

      const usuario = response.data;

      setForm({
        usuario: usuario.username,
        contrasena: "",
        rol: usuario.rol,
      });
    } catch (error) {
      console.error("Error al cargar usuario:", error);
      alert("No se pudo cargar el usuario.");
      navigate("/usuarios");
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!form.usuario || !form.rol) {
      alert("Completa el usuario y el rol.");
      return;
    }

    try {
      if (id) {
        await api.put(`/usuarios/${id}`, {
          username: form.usuario,
          password: form.contrasena || null,
          rol: form.rol,
          estado: true,
        });

        alert("Usuario actualizado correctamente.");
      } else {
        if (!form.contrasena) {
          alert("La contraseña es obligatoria.");
          return;
        }

        await api.post("/usuarios", {
          username: form.usuario,
          password: form.contrasena,
          rol: form.rol,
          estado: true,
        });

        alert("Usuario guardado correctamente.");
      }

      navigate("/usuarios");
    } catch (error) {
      console.error("Error al guardar usuario:", error);

      if (error.response?.status === 403) {
        alert("No tienes permisos para realizar esta operación.");
      } else if (error.response?.status === 400) {
        alert("Los datos enviados no son válidos.");
      } else {
        alert("No se pudo guardar el usuario.");
      }
    }
  };

  return (
    <Layout>
      <button
        className="back-button"
        onClick={() => navigate("/usuarios")}
      >
        <FiArrowLeft />
        Volver
      </button>

      <div className="form-heading">
        <h1>{id ? "Editar Usuario" : "Nuevo Usuario"}</h1>
        <p>Complete los datos del usuario</p>
      </div>

      <form className="entity-form" onSubmit={handleSubmit}>
        <label>
          Usuario
          <input
            type="text"
            placeholder="Ingrese el usuario"
            value={form.usuario}
            onChange={(event) =>
              setForm({
                ...form,
                usuario: event.target.value,
              })
            }
          />
        </label>

        <label>
          Contraseña
          <div className="password-input">
            <input
              type={showPassword ? "text" : "password"}
              placeholder={
                id
                  ? "Dejar vacío para mantener la actual"
                  : "Ingrese la contraseña"
              }
              value={form.contrasena}
              onChange={(event) =>
                setForm({
                  ...form,
                  contrasena: event.target.value,
                })
              }
            />

            <button
              type="button"
              onClick={() =>
                setShowPassword((value) => !value)
              }
            >
              {showPassword ? <FiEyeOff /> : <FiEye />}
            </button>
          </div>
        </label>

        <label>
          Rol
          <select
            value={form.rol}
            onChange={(event) =>
              setForm({
                ...form,
                rol: event.target.value,
              })
            }
          >
            <option value="">Seleccione un rol</option>
            <option value="ADMIN">ADMIN</option>
            <option value="USER">USER</option>
            <option value="OPERADOR">OPERADOR</option>
            <option value="VISOR">VISOR</option>
          </select>
        </label>

        <div className="form-actions">
          <button
            type="button"
            className="secondary-button"
            onClick={() => navigate("/usuarios")}
          >
            Cancelar
          </button>

          <button
            type="submit"
            className="primary-button"
          >
            Guardar
          </button>
        </div>
      </form>
    </Layout>
  );
}

export default FormularioUsuario;