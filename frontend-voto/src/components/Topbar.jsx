import { FiUser, FiMoreVertical } from "react-icons/fi";

function Topbar() {
  return (
    <header className="topbar">
      <div />
      <div className="topbar-user">
        <FiUser />
        <span>Administrador</span>
        <FiMoreVertical />
      </div>
    </header>
  );
}

export default Topbar;
