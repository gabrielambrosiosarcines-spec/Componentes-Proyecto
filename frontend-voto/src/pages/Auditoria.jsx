import EntityTablePage from "../components/EntityTablePage";
import { auditorias } from "../data/mockData";

function Auditoria() {
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
        { key: "detalle", label: "Detalle" }
      ]}
      rows={auditorias}
    />
  );
}

export default Auditoria;
