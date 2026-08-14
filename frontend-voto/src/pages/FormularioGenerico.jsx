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
    ["descripcion", "Descripción", "text"],
    ["fechaInicio", "Fecha de inicio", "datetime-local"],
    ["fechaFin", "Fecha de fin", "datetime-local"],
    ["estado", "Estado", "select"],
  ],
},

  candidato: {
  path: "/candidatos",
  titulo: "Nuevo Candidato",
  campos: [
    ["eleccion", "Elección", "select"],
    ["nombre", "Nombre", "text"],
    ["apellido", "Apellido", "text"],
    ["partidoPolitico", "Partido Político", "text"],
    ["fotoUrl", "URL de foto", "text"],
  ],
},

  voto: {
    path: "/votos",
    titulo: "Emitir Voto",
    campos: [
      ["eleccion", "Elección", "select"],
      ["candidato", "Candidato", "select"],
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
  const [elecciones, setElecciones] = useState([]);
  const [candidatos, setCandidatos] = useState([]);
  const [votantes, setVotantes] = useState([]);


//RegistroParticipacion
useEffect(() => {
  if (tipo === "registro") {
    cargarDatosRegistro();
  }
}, [tipo, id]);

//Candidatos
  useEffect(() => {
  if (tipo === "candidato") {
    cargarElecciones();
  }
}, [tipo]);

  // Cargar usuarios solamente para Votantes
  useEffect(() => {
    if (tipo === "votante") {
      cargarUsuarios();
    }
  }, [tipo]);

  useEffect(() => {
    if (tipo === "voto") {
      cargarDatosVoto();
    }
  }, [tipo]);

  // Si estamos editando, cargar el votante
  useEffect(() => {
    if (tipo === "votante" && id) {
      cargarVotante();
    }
  }, [tipo, id]);

  useEffect(() => {
  if (tipo === "registro" && id) {
    cargarRegistro();
  }
}, [tipo, id]);

useEffect(() => {
  if (tipo === "voto" && id) {
    cargarVoto();
  }
}, [tipo, id]);


const cargarRegistro = async () => {
  try {
    const response = await api.get(`/registro-participacion/${id}`);
    const registro = response.data;

    setForm({
      votante: registro.votante?.idVotante
        ? String(registro.votante.idVotante)
        : "",
      eleccion: registro.eleccion?.idEleccion
        ? String(registro.eleccion.idEleccion)
        : "",
      fecha: registro.fechaVoto
        ? registro.fechaVoto.slice(0, 16)
        : "",
    });
  } catch (error) {
    console.error("Error al cargar registro:", error);
    alert("No se pudo cargar el registro.");
    navigate("/participacion");
  }
};



  const cargarDatosRegistro = async () => {
  try {
    const [votantesResponse, eleccionesResponse] = await Promise.all([
      api.get("/votantes"),
      api.get("/elecciones"),
    ]);

    setVotantes(votantesResponse.data);
    setElecciones(eleccionesResponse.data);
  } catch (error) {
    console.error("Error al cargar datos del registro:", error);
    alert("No se pudieron cargar los datos del registro.");
  }
};


  const cargarElecciones = async () => {
  try {
    const response = await api.get("/elecciones");
    setElecciones(response.data);
  } catch (error) {
    console.error("Error al cargar elecciones:", error);
    alert("No se pudieron cargar las elecciones.");
  }
};

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


const cargarDatosVoto = async () => {
  try {
    const [eleccionesResponse, candidatosResponse] = await Promise.all([
      api.get("/elecciones"),
      api.get("/candidatos"),
    ]);

    setElecciones(eleccionesResponse.data);
    setCandidatos(candidatosResponse.data);
  } catch (error) {
    console.error("Error al cargar datos del voto:", error);
    alert("No se pudieron cargar las elecciones y candidatos.");
  }
};

const cargarVoto = async () => {
  try {
    const response = await api.get(`/votos/${id}`);
    const voto = response.data;

    setForm({
      eleccion: voto.eleccion?.idEleccion
        ? String(voto.eleccion.idEleccion)
        : "",
      candidato: voto.candidato?.idCandidato
        ? String(voto.candidato.idCandidato)
        : "",
    });
  } catch (error) {
    console.error("Error al cargar voto:", error);
    alert("No se pudo cargar el voto.");
    navigate("/votos");
  }
};


  const handleSubmit = async (event) => {
    event.preventDefault();

    if (Object.values(form).some((value) => !value)) {
      alert("Completa todos los campos.");
      return;
    }

    try {
          if (tipo === "voto") {
            if (id) {
              const datos = {
                eleccion: {
                  idEleccion: Number(form.eleccion),
                },
                candidato: {
                  idCandidato: Number(form.candidato),
                },
              };

              await api.put(`/votos/${id}`, datos);

              alert("Voto actualizado correctamente.");
              navigate("/votos");
              return;
            }

            const datos = {
              idEleccion: Number(form.eleccion),
              idCandidato: Number(form.candidato),
            };

            try {
              await api.post("/votos/emitir", datos);

              alert("Voto emitido correctamente.");
              navigate("/votos");
              return;
            } catch (error) {
              console.error("Error al emitir voto:", error);

              if (error.response?.status === 409) {
                alert("El votante ya participó en esta elección.");
              } else if (error.response?.status === 400) {
                alert(
                  error.response.data ||
                  "Los datos del voto no son válidos."
                );
              } else if (error.response?.status === 403) {
                alert("No tienes permisos para emitir el voto.");
              } else {
                alert("No se pudo emitir el voto.");
              }

              return;
            }
          }

          if (tipo === "registro") {
            const datos = {
              votante: {
                idVotante: Number(form.votante),
              },
              eleccion: {
                idEleccion: Number(form.eleccion),
              },
              fechaVoto: form.fecha,
            };

            if (id) {
              await api.put(`/registro-participacion/${id}`, datos);
              alert("Registro actualizado correctamente.");
            } else {
              await api.post("/registro-participacion", datos);
              alert("Registro guardado correctamente.");
            }

            navigate("/participacion");
            return;
          }

          if (tipo === "candidato") {
          const datos = {
            eleccion: {
              idEleccion: Number(form.eleccion),
            },
            nombre: form.nombre,
            apellido: form.apellido,
            partidoPolitico: form.partidoPolitico,
            fotoUrl: form.fotoUrl,
          };

          if (id) {
            await api.put(`/candidatos/${id}`, datos);
            alert("Candidato actualizado correctamente.");
          } else {
            await api.post("/candidatos", datos);
            alert("Candidato guardado correctamente.");
          }

          navigate("/candidatos");
          return;
        }


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

      if (tipo === "elección") {
        const datos = {
          titulo: form.titulo,
          descripcion: form.descripcion,
          fechaInicio: form.fechaInicio,
          fechaFin: form.fechaFin,
          estado: form.estado,
        };

        if (id) {
          await api.put(`/elecciones/${id}`, datos);
          alert("Elección actualizada correctamente.");
        } else {
          await api.post("/elecciones", datos);
          alert("Elección guardada correctamente.");
        }

        navigate("/elecciones");
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
                  ...(tipo === "voto" && key === "eleccion"
                    ? { candidato: "" }
                    : {}),
                })
              }
            >
              <option value="">Seleccione una opción</option>

              {tipo === "voto" && key === "eleccion" ? (
                elecciones.map((eleccion) => (
                  <option
                    key={eleccion.idEleccion}
                    value={eleccion.idEleccion}
                  >
                    {eleccion.titulo}
                  </option>
                ))
              ) : tipo === "voto" && key === "candidato" ? (
                candidatos
                  .filter(
                    (candidato) =>
                      String(candidato.eleccion?.idEleccion) ===
                      String(form.eleccion)
                  )
                  .map((candidato) => (
                    <option
                      key={candidato.idCandidato}
                      value={candidato.idCandidato}
                    >
                      {candidato.nombre} {candidato.apellido}
                    </option>
                  ))
              ) : tipo === "votante" && key === "usuario" ? (
                usuarios.map((usuario) => (
                  <option
                    key={usuario.idUsuario}
                    value={usuario.idUsuario}
                  >
                    {usuario.username} — {usuario.rol}
                  </option>
                ))
              ) : tipo === "registro" && key === "votante" ? (
                votantes.map((votante) => (
                  <option
                    key={votante.idVotante}
                    value={votante.idVotante}
                  >
                    {votante.nombre} {votante.apellido}
                  </option>
                ))
              ) : tipo === "registro" && key === "eleccion" ? (
                elecciones.map((eleccion) => (
                  <option
                    key={eleccion.idEleccion}
                    value={eleccion.idEleccion}
                  >
                    {eleccion.titulo}
                  </option>
                ))
              ):( 
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