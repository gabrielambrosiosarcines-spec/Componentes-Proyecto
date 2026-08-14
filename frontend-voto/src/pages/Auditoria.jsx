import { useEffect, useState } from "react";
import EntityTablePage from "../components/EntityTablePage";
import api from "../api/axiosConfig";

function Auditoria() {
  const [auditorias, setAuditorias] = useState([]);

  useEffect(() => {
    cargarAuditorias();
  }, []);

  const cargarAuditorias = async () => {
    try {
      const response = await api.get("/auditoria");

      const auditoriasFormateadas = response.data.map((auditoria) => ({
        id: auditoria.idAuditoria,
        usuario: auditoria.usuario?.username || "Sin usuario",
        accion: auditoria.accion || "",
        fecha: auditoria.fechaEvento || "",
        detalle: auditoria.detalles || "",
      }));

      setAuditorias(auditoriasFormateadas);
    } catch (error) {
      console.error("Error al cargar auditorías:", error);

      if (error.response?.status === 401) {
        alert("Sesión no válida o expirada.");
      } else if (error.response?.status === 403) {
        alert("No tienes permisos para consultar la auditoría.");
      } else {
        alert("No se pudieron cargar los registros de auditoría.");
      }
    }
  };

  return (
    <EntityTablePage
      title="Auditoría"
      subtitle="Lista de acciones registradas en el sistema"
      searchPlaceholder="Buscar auditoría..."
      columns={[
        { key: "id", label: "ID" },
        { key: "usuario", label: "Usuario" },
        { key: "accion", label: "Acción" },
        { key: "fecha", label: "Fecha" },
        { key: "detalle", label: "Detalle" },
      ]}
      rows={auditorias}
    />
  );
}

export default Auditoria;