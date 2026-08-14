import { useEffect, useState } from "react";
import EntityTablePage from "../components/EntityTablePage";
import api from "../api/axiosConfig";

function Candidatos() {
  const [candidatos, setCandidatos] = useState([]);

  useEffect(() => {
    cargarCandidatos();
  }, []);

  const cargarCandidatos = async () => {
    try {
      const response = await api.get("/candidatos");

      const candidatosFormateados = response.data.map((candidato) => ({
        id: candidato.idCandidato,
        nombre: candidato.nombre,
        apellido: candidato.apellido,
        eleccion: candidato.eleccion?.titulo || "Sin elección",
        partido: candidato.partidoPolitico,
      }));

      setCandidatos(candidatosFormateados);
    } catch (error) {
      console.error("Error al cargar candidatos:", error);

      if (error.response?.status === 401) {
        alert("Sesión no válida o expirada.");
      } else if (error.response?.status === 403) {
        alert("No tienes permisos para consultar los candidatos.");
      } else {
        alert("No se pudieron cargar los candidatos.");
      }
    }
  };

  return (
    <EntityTablePage
      title="Candidatos"
      subtitle="Lista de candidatos registrados"
      searchPlaceholder="Buscar candidato..."
      buttonLabel="Nuevo candidato"
      buttonPath="/candidatos/nuevo"
      editBasePath="/candidatos/editar"
      deletePath="/candidatos"
      columns={[
        { key: "id", label: "ID" },
        { key: "nombre", label: "Nombre" },
        { key: "apellido", label: "Apellido" },
        { key: "eleccion", label: "Elección" },
        { key: "partido", label: "Partido Político" },
      ]}
      rows={candidatos}
    />
  );
}

export default Candidatos;