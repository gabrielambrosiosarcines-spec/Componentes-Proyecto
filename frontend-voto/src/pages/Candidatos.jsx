import EntityTablePage from "../components/EntityTablePage";
import { candidatos } from "../data/mockData";

function Candidatos() {
  return (
    <EntityTablePage
      title="Candidatos"
      subtitle="Lista de candidatos registrados"
      searchPlaceholder="Buscar candidato..."
      buttonLabel="Nuevo candidato"
      buttonPath="/candidatos/nuevo"
      columns={[
        { key: "id", label: "ID" },
        { key: "nombres", label: "Nombres" },
        { key: "apellidos", label: "Apellidos" },
        { key: "eleccion", label: "Elección" },
        { key: "descripcion", label: "Descripción" }
      ]}
      rows={candidatos}
    />
  );
}

export default Candidatos;
