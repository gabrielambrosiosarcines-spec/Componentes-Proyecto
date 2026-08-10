import EntityTablePage from "../components/EntityTablePage";
import { votos } from "../data/mockData";

function Votos() {
  return (
    <EntityTablePage
      title="Votos"
      subtitle="Lista de votos registrados"
      searchPlaceholder="Buscar voto..."
      buttonLabel="Nuevo voto"
      buttonPath="/votos/nuevo"
      columns={[
        { key: "id", label: "ID" },
        { key: "eleccion", label: "Elección" },
        { key: "candidato", label: "Candidato" },
        { key: "fecha", label: "Fecha" }
      ]}
      rows={votos}
    />
  );
}

export default Votos;
