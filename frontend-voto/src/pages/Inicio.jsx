import { useNavigate } from "react-router-dom";
import Layout from "../components/Layout";
import {
  FiUsers,
  FiUserCheck,
  FiCalendar,
  FiUserPlus,
  FiCheckSquare,
  FiClipboard,
  FiClock
} from "react-icons/fi";

const options = [
  { label: "Usuarios", icon: <FiUsers />, path: "/usuarios" },
  { label: "Votantes", icon: <FiUserCheck />, path: "/votantes" },
  { label: "Elecciones", icon: <FiCalendar />, path: "/elecciones" },
  { label: "Candidatos", icon: <FiUserPlus />, path: "/candidatos" },
  { label: "Votos", icon: <FiCheckSquare />, path: "/votos" },
  { label: "Registro de Participación", icon: <FiClipboard />, path: "/participacion" },
  { label: "Auditoría", icon: <FiClock />, path: "/auditoria" }
];

function Inicio() {
  const navigate = useNavigate();

  return (
    <Layout title="Menú Principal" subtitle="Seleccione una opción para administrar el sistema">
      <div className="menu-grid">
        {options.map((option) => (
          <button
            key={option.path}
            className="menu-card"
            onClick={() => navigate(option.path)}
          >
            <span>{option.icon}</span>
            <strong>{option.label}</strong>
          </button>
        ))}
      </div>
    </Layout>
  );
}

export default Inicio;
