import { NavLink, useNavigate } from "react-router-dom";
import {
  FiHome,
  FiUsers,
  FiUserCheck,
  FiCalendar,
  FiUserPlus,
  FiCheckSquare,
  FiClipboard,
  FiClock,
  FiLogOut,
  FiBox
} from "react-icons/fi";

const links = [
  { to: "/inicio", label: "Inicio", icon: <FiHome /> },
  { to: "/usuarios", label: "Usuarios", icon: <FiUsers /> },
  { to: "/votantes", label: "Votantes", icon: <FiUserCheck /> },
  { to: "/elecciones", label: "Elecciones", icon: <FiCalendar /> },
  { to: "/candidatos", label: "Candidatos", icon: <FiUserPlus /> },
  { to: "/votos", label: "Votos", icon: <FiCheckSquare /> },
  { to: "/participacion", label: "Registro Participación", icon: <FiClipboard /> },
  { to: "/auditoria", label: "Auditoría", icon: <FiClock /> }
];

function Sidebar() {
  const navigate = useNavigate();

  return (
    <aside className="sidebar">
      <div className="brand">
        <div className="brand-icon"><FiBox /></div>
        <div>
          <strong>SISTEMA</strong>
          <span>DE VOTACIÓN</span>
        </div>
      </div>

      <nav className="sidebar-nav">
        {links.map((link) => (
          <NavLink
            key={link.to}
            to={link.to}
            className={({ isActive }) => `nav-item ${isActive ? "active" : ""}`}
          >
            <span className="nav-icon">{link.icon}</span>
            <span>{link.label}</span>
          </NavLink>
        ))}
      </nav>

      <button className="logout-button" onClick={() => navigate("/login")}>
        <FiLogOut />
        Cerrar sesión
      </button>
    </aside>
  );
}

export default Sidebar;
