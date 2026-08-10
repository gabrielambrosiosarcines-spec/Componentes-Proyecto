import { useEffect, useMemo, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { FiArrowLeft } from "react-icons/fi";
import Layout from "../components/Layout";
import api from "../api/axiosConfig";

const config = {
  votante: {
    path: "/votantes",
    titulo: "Nuevo Votante",
    campos: [
      ["usuario", "Usuario", "select"],
      ["dni", "DNI", "text"],
      ["nombre", "Nombre", "text"],
      ["apellido", "Apellido", "text"],
      ["correo", "Correo", "email"],
    ],
  },

  "elección": {
    path: "/elecciones",
    titulo: "Nueva Elección",
    campos: [
      ["titulo", "Título", "text"],
      ["fechaInicio", "Fecha de inicio", "date"],
      ["fechaFin", "Fecha de fin", "date"],
      ["estado", "Estado", "select"],
    ],
  },

  candidato: {
    path: "/candidatos",
    titulo: "Nuevo Candidato",
    campos: [
      ["nombres", "Nombres", "text"],
      ["apellidos", "Apellidos", "text"],
      ["eleccion", "Elección", "select"],
      ["descripcion", "Descripción", "text"],
    ],
  },

  voto: {
    path: "/votos",
    titulo: "Nuevo Voto",
    campos: [
      ["eleccion", "Elección", "select"],
      ["candidato", "Candidato", "select"],
      ["fecha", "Fecha y hora", "datetime-local"],
    ],
  },

  registro: {
    path: "/participacion",
    titulo: "Nuevo Registro",
    campos: [
      ["votante", "Votante", "select"],
      ["eleccion", "Elección", "select"],
      ["fecha", "Fecha de participación", "datetime-local"],
    ],
  },
};

function FormularioGenerico({ tipo }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const current = config[tipo];

  const initialState = useMemo(
    () =>
      Object.fromEntries(
        current.campos.map(([key]) => [key, ""])
      ),
    [current]
  );

  const [form, setForm] = useState(initialState);
  const [usuarios, setUsuarios] = useState([]);

  // Cargar usuarios solamente para Votantes
  useEffect(() => {
    if (tipo === "votante") {
      cargarUsuarios();
    }
  }, [tipo]);

  // Si estamos editando, cargar el votante
  useEffect(() => {
    if (tipo === "votante" && id) {
      cargarVotante();
    }
  }, [tipo, id]);

  const cargarUsuarios = async () => {
    try {
      const response = await api.get("/usuarios");
      setUsuarios(response.data);
    } catch (error) {
      console.error("Error al cargar usuarios:", error);
      alert("No se pudieron cargar los usuarios.");
    }
  };

  const cargarVotante = async () => {
    try {
      const response = await api.get(`/votantes/${id}`);
      const votante = response.data;

      setForm({
        usuario: votante.usuario?.idUsuario
          ? String(votante.usuario.idUsuario)
          : "",
        dni: votante.dni || "",
        nombre: votante.nombre || "",
        apellido: votante.apellido || "",
        correo: votante.correo || "",
      });
    } catch (error) {
      console.error("Error al cargar votante:", error);
      alert("No se pudo cargar el votante.");
      navigate("/votantes");
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (Object.values(form).some((value) => !value)) {
      alert("Completa todos los campos.");
      return;
    }

    try {
      if (tipo === "votante") {
        const datos = {
          usuario: {
            idUsuario: Number(form.usuario),
          },
          dni: form.dni,
          nombre: form.nombre,
          apellido: form.apellido,
          correo: form.correo,
        };

        if (id) {
          await api.put(`/votantes/${id}`, datos);
          alert("Votante actualizado correctamente.");
        } else {
          await api.post("/votantes", datos);
          alert("Votante guardado correctamente.");
        }

        navigate("/votantes");
        return;
      }

      // Comportamiento provisional de las demás entidades
      alert(`${current.titulo} guardado.`);
      navigate(current.path);

    } catch (error) {
      console.error("Error al guardar:", error);

      if (error.response?.status === 403) {
        alert("No tienes permisos para realizar esta operación.");
      } else if (error.response?.status === 400) {
        alert("Los datos enviados no son válidos.");
      } else if (error.response?.status === 500) {
        alert(
          "Error del servidor. Verifica que el usuario seleccionado no esté asociado a otro votante."
        );
      } else {
        alert("No se pudo guardar el registro.");
      }
    }
  };

  return (
    <Layout>
      <button
        className="back-button"
        onClick={() => navigate(current.path)}
      >
        <FiArrowLeft />
        Volver
      </button>

      <div className="form-heading">
        <h1>
          {id ? `Editar ${tipo === "votante" ? "Votante" : current.titulo}` : current.titulo}
        </h1>

        <p>Complete los datos solicitados</p>
      </div>

      <form className="entity-form" onSubmit={handleSubmit}>
        {current.campos.map(([key, label, type]) => (
          <label key={key}>
            {label}

            {type === "select" ? (
              <select
                value={form[key]}
                onChange={(event) =>
                  setForm({
                    ...form,
                    [key]: event.target.value,
                  })
                }
              >
                <option value="">Seleccione una opción</option>

                {tipo === "votante" && key === "usuario" ? (
                  usuarios.map((usuario) => (
                    <option
                      key={usuario.idUsuario}
                      value={usuario.idUsuario}
                    >
                      {usuario.username} — {usuario.rol}
                    </option>
                  ))
                ) : (
                  <>
                    <option value="Opción 1">Opción 1</option>
                    <option value="Opción 2">Opción 2</option>
                    <option value="Opción 3">Opción 3</option>
                  </>
                )}
              </select>
            ) : (
              <input
                type={type}
                value={form[key]}
                placeholder={`Ingrese ${label.toLowerCase()}`}
                onChange={(event) =>
                  setForm({
                    ...form,
                    [key]: event.target.value,
                  })
                }
              />
            )}
          </label>
        ))}

        <div className="form-actions">
          <button
            type="button"
            className="secondary-button"
            onClick={() => navigate(current.path)}
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

export default FormularioGenerico;