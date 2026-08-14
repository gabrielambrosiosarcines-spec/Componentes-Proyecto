import { useEffect, useState } from "react";
import EntityTablePage from "../components/EntityTablePage";
import api from "../api/axiosConfig";

function Elecciones() {
  const [elecciones, setElecciones] = useState([]);

  useEffect(() => {
    cargarElecciones();
  }, []);

  const cargarElecciones = async () => {
    try {
      const response = await api.get("/elecciones");

      const eleccionesFormateadas = response.data.map((eleccion) => ({
        id: eleccion.idEleccion,
        titulo: eleccion.titulo,
        inicio: eleccion.fechaInicio,
        fin: eleccion.fechaFin,
        estado: eleccion.estado,
      }));

      setElecciones(eleccionesFormateadas);
    } catch (error) {
      console.error("Error al cargar elecciones:", error);

      if (error.response?.status === 401) {
        alert("Sesión no válida o expirada.");
      } else if (error.response?.status === 403) {
        alert("No tienes permisos para consultar las elecciones.");
      } else {
        alert("No se pudieron cargar las elecciones.");
      }
    }
  };

  return (
    <EntityTablePage
      title="Elecciones"
      subtitle="Lista de elecciones registradas"
      searchPlaceholder="Buscar elección..."
      buttonLabel="Nueva elección"
      buttonPath="/elecciones/nueva"
      editBasePath="/elecciones/editar"
      deletePath="/elecciones"
      columns={[
        { key: "id", label: "ID" },
        { key: "titulo", label: "Título" },
        { key: "inicio", label: "Fecha inicio" },
        { key: "fin", label: "Fecha fin" },
        { key: "estado", label: "Estado" },
      ]}
      rows={elecciones}
    />
  );
}

export default Elecciones;