import { useEffect, useMemo, useState } from "react";
import {
  FiEdit2,
  FiTrash2,
  FiChevronLeft,
  FiChevronRight,
} from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";

function DataTable({
  title,
  subtitle,
  searchPlaceholder,
  buttonLabel,
  buttonPath,
  columns,
  rows,
  editBasePath,
  deletePath,
}) {
  const navigate = useNavigate();

  const [search, setSearch] = useState("");
  const [page, setPage] = useState(1);
  const [localRows, setLocalRows] = useState(rows);

  const pageSize = 5;

  // Actualiza la tabla cuando cambian los datos recibidos
  useEffect(() => {
    setLocalRows(rows);
  }, [rows]);

  const filteredRows = useMemo(() => {
    const value = search.trim().toLowerCase();

    if (!value) return localRows;

    return localRows.filter((row) =>
      Object.values(row).some((cell) =>
        String(cell).toLowerCase().includes(value)
      )
    );
  }, [localRows, search]);

  const totalPages = Math.max(
    1,
    Math.ceil(filteredRows.length / pageSize)
  );

  const visibleRows = filteredRows.slice(
    (page - 1) * pageSize,
    page * pageSize
  );

  const handleDelete = async (id) => {
    const accepted = window.confirm(
      `¿Eliminar el registro ${id}?`
    );

    if (!accepted) return;

    // Si todavía no tiene DELETE conectado,
    // mantenemos el comportamiento anterior.
    if (!deletePath) {
      alert("DELETE del backend pendiente de conectar.");
      return;
    }

    try {
      await api.delete(`${deletePath}/${id}`);

      // Quitamos el registro de la tabla inmediatamente
      setLocalRows((currentRows) =>
        currentRows.filter((row) => row.id !== id)
      );

      alert("Registro eliminado correctamente.");

      // Evita quedarse en una página inexistente
      setPage(1);
    } catch (error) {
      console.error("Error al eliminar:", error);

      if (error.response?.status === 403) {
        alert("No tienes permisos para eliminar este registro.");
      } else if (error.response?.status === 404) {
        alert("El registro no existe.");
      } else {
        alert("No se pudo eliminar el registro.");
      }
    }
  };

  return (
    <div className="entity-page">
      <div className="page-header">
        <div>
          <h1>{title}</h1>
          <p>{subtitle}</p>
        </div>

        {buttonLabel && (
          <button
            className="primary-button"
            onClick={() => navigate(buttonPath)}
          >
            + {buttonLabel}
          </button>
        )}
      </div>

      <div className="table-tools">
        <input
          type="text"
          placeholder={searchPlaceholder}
          value={search}
          onChange={(event) => {
            setSearch(event.target.value);
            setPage(1);
          }}
        />
      </div>

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              {columns.map((column) => (
                <th key={column.key}>{column.label}</th>
              ))}

              <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            {visibleRows.length > 0 ? (
              visibleRows.map((row) => (
                <tr key={row.id}>
                  {columns.map((column) => (
                    <td key={column.key}>
                      {row[column.key]}
                    </td>
                  ))}

                  <td>
                    <div className="action-buttons">
                      <button
                        className="icon-button edit"
                        onClick={() =>
                          editBasePath
                            ? navigate(
                                `${editBasePath}/${row.id}`
                              )
                            : alert(
                                "Formulario de edición pendiente de conectar."
                              )
                        }
                        aria-label="Editar"
                      >
                        <FiEdit2 />
                      </button>

                      <button
                        className="icon-button delete"
                        onClick={() => handleDelete(row.id)}
                        aria-label="Eliminar"
                      >
                        <FiTrash2 />
                      </button>
                    </div>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td
                  colSpan={columns.length + 1}
                  className="empty-state"
                >
                  No se encontraron registros.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <div className="pagination">
        <button
          disabled={page === 1}
          onClick={() =>
            setPage((current) => current - 1)
          }
        >
          <FiChevronLeft />
        </button>

        <span>{page}</span>

        <button
          disabled={page === totalPages}
          onClick={() =>
            setPage((current) => current + 1)
          }
        >
          <FiChevronRight />
        </button>
      </div>
    </div>
  );
}

export default DataTable;