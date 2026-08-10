export const usuarios = [
  { id: 1, usuario: "admin", contrasena: "••••••••••", rol: "ADMIN" },
  { id: 2, usuario: "mlopez", contrasena: "••••••••••", rol: "OPERADOR" },
  { id: 3, usuario: "cperez", contrasena: "••••••••••", rol: "OPERADOR" },
  { id: 4, usuario: "atorres", contrasena: "••••••••••", rol: "VISOR" },
  { id: 5, usuario: "lgomez", contrasena: "••••••••••", rol: "VISOR" }
];

export const votantes = [
  { id: 1, dni: "12345678", nombres: "Juan", apellidos: "Pérez García", nacimiento: "10/05/1990" },
  { id: 2, dni: "87654321", nombres: "María", apellidos: "López Ruiz", nacimiento: "22/08/1992" },
  { id: 3, dni: "11223344", nombres: "Pedro", apellidos: "Torres Díaz", nacimiento: "15/03/1988" },
  { id: 4, dni: "44332211", nombres: "Ana", apellidos: "Gómez Lima", nacimiento: "30/11/1995" },
  { id: 5, dni: "99887766", nombres: "Luis", apellidos: "Ramírez Soto", nacimiento: "05/07/1991" }
];

export const elecciones = [
  { id: 1, titulo: "Elección Municipal 2025", inicio: "01/06/2025", fin: "30/06/2025", estado: "Activa" },
  { id: 2, titulo: "Elección Estudiantil 2025", inicio: "15/05/2025", fin: "15/06/2025", estado: "Activa" },
  { id: 3, titulo: "Elección Comité 2025", inicio: "01/04/2025", fin: "30/04/2025", estado: "Finalizada" },
  { id: 4, titulo: "Elección Escolar 2024", inicio: "01/12/2024", fin: "15/12/2024", estado: "Finalizada" },
  { id: 5, titulo: "Elección General 2024", inicio: "01/08/2024", fin: "30/09/2024", estado: "Finalizada" }
];

export const candidatos = [
  { id: 1, nombres: "Juan", apellidos: "Pérez", eleccion: "Elección Municipal 2025", descripcion: "Partido Verde" },
  { id: 2, nombres: "María", apellidos: "González", eleccion: "Elección Municipal 2025", descripcion: "Partido Progreso" },
  { id: 3, nombres: "Luis", apellidos: "Ramírez", eleccion: "Elección Municipal 2025", descripcion: "Partido Unidad" },
  { id: 4, nombres: "Ana", apellidos: "Martínez", eleccion: "Elección Estudiantil 2025", descripcion: "Partido Futuro" },
  { id: 5, nombres: "Pedro", apellidos: "Díaz", eleccion: "Elección Estudiantil 2025", descripcion: "Lista A" }
];

export const votos = [
  { id: 1, eleccion: "Elección Municipal 2025", candidato: "Juan Pérez", fecha: "01/06/2025 10:30:45" },
  { id: 2, eleccion: "Elección Municipal 2025", candidato: "María González", fecha: "01/06/2025 10:32:10" },
  { id: 3, eleccion: "Elección Estudiantil 2025", candidato: "Pedro Díaz", fecha: "15/05/2025 08:18:52" },
  { id: 4, eleccion: "Elección Estudiantil 2025", candidato: "Ana Martínez", fecha: "15/05/2025 09:20:33" },
  { id: 5, eleccion: "Elección Comité 2025", candidato: "Luis Ramírez", fecha: "01/04/2025 11:05:12" }
];

export const participaciones = [
  { id: 1, votante: "Juan Pérez García", eleccion: "Elección Municipal 2025", fecha: "01/06/2025 10:30:45" },
  { id: 2, votante: "María López Ruiz", eleccion: "Elección Municipal 2025", fecha: "01/06/2025 10:32:10" },
  { id: 3, votante: "Pedro Torres Díaz", eleccion: "Elección Estudiantil 2025", fecha: "15/05/2025 08:18:52" },
  { id: 4, votante: "Ana Gómez Lima", eleccion: "Elección Estudiantil 2025", fecha: "15/05/2025 09:20:33" },
  { id: 5, votante: "Luis Ramírez Soto", eleccion: "Elección Comité 2025", fecha: "01/04/2025 11:05:12" }
];

export const auditorias = [
  { id: 1, usuario: "admin", accion: "Inicio de sesión", fecha: "01/06/2025 08:00:15", detalle: "Sesión iniciada correctamente" },
  { id: 2, usuario: "admin", accion: "Creación de elección", fecha: "01/06/2025 08:10:22", detalle: "Elección Municipal 2025" },
  { id: 3, usuario: "mlopez", accion: "Actualización de votante", fecha: "01/06/2025 08:20:31", detalle: "Votante ID 2 actualizado" },
  { id: 4, usuario: "cperez", accion: "Registro de voto", fecha: "01/06/2025 10:30:45", detalle: "Voto registrado" },
  { id: 5, usuario: "admin", accion: "Eliminación de usuario", fecha: "01/06/2025 11:00:12", detalle: "Usuario ID 3 eliminado" }
];
