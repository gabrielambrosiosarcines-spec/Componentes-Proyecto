import { Navigate, Route, Routes } from "react-router-dom";

import Login from "./pages/Login";
import Inicio from "./pages/Inicio";
import Usuarios from "./pages/Usuarios";
import Votantes from "./pages/Votantes";
import Elecciones from "./pages/Elecciones";
import Candidatos from "./pages/Candidatos";
import Votos from "./pages/Votos";
import Participacion from "./pages/Participacion";
import Auditoria from "./pages/Auditoria";
import FormularioUsuario from "./pages/FormularioUsuario";
import FormularioGenerico from "./pages/FormularioGenerico";

import RutaProtegida from "./components/RutaProtegida";

function App() {
  return (
    <Routes>

      {/* Ruta pública */}
      <Route
        path="/"
        element={<Navigate to="/login" replace />}
      />

      <Route
        path="/login"
        element={<Login />}
      />

      {/* Rutas protegidas */}
      <Route element={<RutaProtegida />}>

        <Route
          path="/inicio"
          element={<Inicio />}
        />

        <Route
          path="/usuarios"
          element={<Usuarios />}
        />

        <Route
          path="/usuarios/nuevo"
          element={<FormularioUsuario />}
        />

        <Route
          path="/usuarios/editar/:id"
          element={<FormularioUsuario />}
        />

        <Route
          path="/votantes"
          element={<Votantes />}
        />

        <Route
          path="/votantes/nuevo"
          element={<FormularioGenerico tipo="votante" />}
        />

        <Route
          path="/elecciones"
          element={<Elecciones />}
        />

        <Route
          path="/elecciones/nueva"
          element={<FormularioGenerico tipo="elección" />}
        />

        <Route
          path="/candidatos"
          element={<Candidatos />}
        />

        <Route
          path="/candidatos/nuevo"
          element={<FormularioGenerico tipo="candidato" />}
        />

        <Route
          path="/votos"
          element={<Votos />}
        />

        <Route
          path="/votos/nuevo"
          element={<FormularioGenerico tipo="voto" />}
        />

        <Route
          path="/participacion"
          element={<Participacion />}
        />

        <Route
          path="/participacion/nuevo"
          element={<FormularioGenerico tipo="registro" />}
        />

        <Route
          path="/auditoria"
          element={<Auditoria />}
        />

      </Route>

      {/* Cualquier ruta inexistente */}
      <Route
        path="*"
        element={<Navigate to="/login" replace />}
      />

      <Route
        path="/votantes/nuevo"
        element={<FormularioGenerico tipo="votante" />}
      />

      <Route
        path="/votantes/editar/:id"
        element={<FormularioGenerico tipo="votante" />}
      />

    </Routes>
  );
}

export default App;