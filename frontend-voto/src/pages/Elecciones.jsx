import EntityTablePage from "../components/EntityTablePage";
import { elecciones } from "../data/mockData";

function Elecciones() {
  return (
    <EntityTablePage
      title="Elecciones"
      subtitle="Lista de elecciones registradas"
      searchPlaceholder="Buscar elección..."
      buttonLabel="Nueva elección"
      buttonPath="/elecciones/nueva"
      columns={[
        { key: "id", label: "ID" },
        { key: "titulo", label: "Título" },
        { key: "inicio", label: "Fecha inicio" },
        { key: "fin", label: "Fecha fin" },
        { key: "estado", label: "Estado" }
      ]}
      rows={elecciones}
    />
  );
}

export default Elecciones;
