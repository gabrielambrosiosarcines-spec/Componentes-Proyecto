import { useEffect, useState } from "react";
import EntityTablePage from "../components/EntityTablePage";
import api from "../api/axiosConfig";

function Votos() {
  const [votos, setVotos] = useState([]);

  useEffect(() => {
    cargarVotos();
  }, []);

  const cargarVotos = async () => {
    try {
      const response = await api.get("/votos");

      const votosFormateados = response.data.map((voto) => ({
        id: voto.idVoto,
        eleccion: voto.eleccion?.titulo || "Sin elección",
        candidato: voto.candidato
          ? `${voto.candidato.nombre} ${voto.candidato.apellido}`
          : "Sin candidato",
        fecha: voto.fechaRegistro,
      }));

      setVotos(votosFormateados);
    } catch (error) {
      console.error("Error al cargar votos:", error);

      if (error.response?.status === 401) {
        alert("Sesión no válida o expirada.");
      } else if (error.response?.status === 403) {
        alert("No tienes permisos para consultar los votos.");
      } else {
        alert("No se pudieron cargar los votos.");
      }
    }
  };

  return (
    <EntityTablePage
      title="Votos"
      subtitle="Lista de votos registrados"
      searchPlaceholder="Buscar voto..."
      buttonLabel="Nuevo voto"
      buttonPath="/votos/nuevo"
      editBasePath="/votos/editar"
      deletePath="/votos"
      columns={[
        { key: "id", label: "ID" },
        { key: "eleccion", label: "Elección" },
        { key: "candidato", label: "Candidato" },
        { key: "fecha", label: "Fecha" },
      ]}
      rows={votos}
    />
  );
}

export default Votos;