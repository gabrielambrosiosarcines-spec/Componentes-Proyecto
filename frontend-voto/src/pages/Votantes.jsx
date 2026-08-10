import { useEffect, useState } from "react";
import EntityTablePage from "../components/EntityTablePage";
import api from "../api/axiosConfig";

function Votantes() {
  const [votantes, setVotantes] = useState([]);

  useEffect(() => {
    cargarVotantes();
  }, []);

  const cargarVotantes = async () => {
    try {
      const response = await api.get("/votantes");

      const votantesFormateados = response.data.map((votante) => ({
        id: votante.idVotante,
        dni: votante.dni,
        nombre: votante.nombre,
        apellido: votante.apellido,
        correo: votante.correo,
      }));

      setVotantes(votantesFormateados);
    } catch (error) {
      console.error("Error al cargar votantes:", error);

      if (error.response?.status === 401) {
        alert("Sesión no válida o expirada.");
      } else if (error.response?.status === 403) {
        alert("No tienes permisos para consultar los votantes.");
      } else {
        alert("No se pudieron cargar los votantes.");
      }
    }
  };

  return (
    <EntityTablePage
      title="Votantes"
      subtitle="Lista de votantes registrados"
      searchPlaceholder="Buscar votante..."
      buttonLabel="Nuevo votante"
      buttonPath="/votantes/nuevo"
      editBasePath="/votantes/editar"
      deletePath="/votantes"
      columns={[
        { key: "id", label: "ID" },
        { key: "dni", label: "DNI" },
        { key: "nombre", label: "Nombre" },
        { key: "apellido", label: "Apellido" },
        { key: "correo", label: "Correo" },
      ]}
      rows={votantes}
    />
  );
}

export default Votantes;