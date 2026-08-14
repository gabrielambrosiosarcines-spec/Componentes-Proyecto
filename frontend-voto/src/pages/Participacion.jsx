import { useEffect, useState } from "react";
import EntityTablePage from "../components/EntityTablePage";
import api from "../api/axiosConfig";

function Participacion() {
  const [participaciones, setParticipaciones] = useState([]);

  useEffect(() => {
    cargarParticipaciones();
  }, []);

  const cargarParticipaciones = async () => {
    try {
      const response = await api.get("/registro-participacion");

      const participacionesFormateadas = response.data.map((registro) => ({
        id: registro.id,
        votante: registro.votante
          ? `${registro.votante.nombre} ${registro.votante.apellido}`
          : "Sin votante",
        eleccion: registro.eleccion?.titulo || "Sin elección",
        fecha: registro.fechaVoto,
      }));

      setParticipaciones(participacionesFormateadas);
    } catch (error) {
      console.error("Error al cargar registros:", error);

      if (error.response?.status === 401) {
        alert("Sesión no válida o expirada.");
      } else if (error.response?.status === 403) {
        alert("No tienes permisos para consultar los registros.");
      } else {
        alert("No se pudieron cargar los registros de participación.");
      }
    }
  };

  return (
    <EntityTablePage
      title="Registro de Participación"
      subtitle="Lista de registros de participación"
      searchPlaceholder="Buscar registro..."
      buttonLabel="Nuevo registro"
      buttonPath="/participacion/nuevo"
      editBasePath="/participacion/editar"
      deletePath="/registro-participacion"
      columns={[
        { key: "id", label: "ID" },
        { key: "votante", label: "Votante" },
        { key: "eleccion", label: "Elección" },
        { key: "fecha", label: "Fecha participación" },
      ]}
      rows={participaciones}
    />
  );
}

export default Participacion;