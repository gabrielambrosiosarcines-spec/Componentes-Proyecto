import { useEffect, useState } from "react";
import EntityTablePage from "../components/EntityTablePage";
import api from "../api/axiosConfig";

function Usuarios() {
  const [usuarios, setUsuarios] = useState([]);

  useEffect(() => {
    cargarUsuarios();
  }, []);

  const cargarUsuarios = async () => {
    try {
      const response = await api.get("/usuarios");

      const usuariosFormateados = response.data.map((usuario) => ({
        id: usuario.idUsuario,
        usuario: usuario.username,
        rol: usuario.rol,
      }));

      setUsuarios(usuariosFormateados);
    } catch (error) {
      console.error("Error al cargar usuarios:", error);

      if (error.response?.status === 401) {
        alert("Sesión no válida o expirada.");
      } else if (error.response?.status === 403) {
        alert("No tienes permisos para consultar los usuarios.");
      } else {
        alert("No se pudieron cargar los usuarios.");
      }
    }
  };

  return (
    <EntityTablePage
      title="Usuarios"
      subtitle="Lista de usuarios registrados"
      searchPlaceholder="Buscar usuario..."
      buttonLabel="Nuevo usuario"
      buttonPath="/usuarios/nuevo"
      editBasePath="/usuarios/editar"
      deletePath="/usuarios"
      columns={[
        { key: "id", label: "ID" },
        { key: "usuario", label: "Usuario" },
        { key: "rol", label: "Rol" },
      ]}
      rows={usuarios}
    />
  );
}

export default Usuarios;