import EntityTablePage from "../components/EntityTablePage";
import { participaciones } from "../data/mockData";

function Participacion() {
  return (
    <EntityTablePage
      title="Registro de Participación"
      subtitle="Lista de registros de participación"
      searchPlaceholder="Buscar registro..."
      buttonLabel="Nuevo registro"
      buttonPath="/participacion/nuevo"
      columns={[
        { key: "id", label: "ID" },
        { key: "votante", label: "Votante" },
        { key: "eleccion", label: "Elección" },
        { key: "fecha", label: "Fecha participación" }
      ]}
      rows={participaciones}
    />
  );
}

export default Participacion;
