```markdown
# Foro-Alura
Desarrollo de foro para el challenge de Alura Latam


Foro-Alura es una API REST diseñada para gestionar temas de discusión en un foro. Permite a los usuarios crear, listar, actualizar, eliminar y cambiar el estado de los temas. Además, incluye medidas de seguridad para proteger el acceso y manejo de los datos.

## Endpoints

### 1. **Crear un Tema**

**POST** `/temas`

Crea un nuevo tema en el foro.

**Body:**
```json
{
  "titulo": "Problemas con la instalación de Hibernate",
  "mensaje": "Intenté instalar Hibernate pero estoy recibiendo un error. ¿Alguien sabe cómo solucionarlo?",
  "autorId": 1,
  "cursoId": 2,
  "status": "ABIERTO"
}
```

**Response:**
```json
{
  "id": 1,
  "titulo": "Problemas con la instalación de Hibernate",
  "mensaje": "Intenté instalar Hibernate pero estoy recibiendo un error. ¿Alguien sabe cómo solucionarlo?",
  "fechaCreacion": "2025-01-17T12:04:33.799043",
  "status": "ABIERTO",
  "autorNombre": "Laura Vega",
  "cursoNombre": "Curso de Java"
}
```

---

### 2. **Listar Temas**

**GET** `/temas`

Obtiene la lista de todos los temas disponibles.

**Response:**
```json
[
  {
    "id": 1,
    "titulo": "Problemas con la instalación de Hibernate",
    "mensaje": "Intenté instalar Hibernate pero estoy recibiendo un error. ¿Alguien sabe cómo solucionarlo?",
    "fechaCreacion": "2025-01-17T12:04:33.799043",
    "status": "ABIERTO",
    "autorNombre": "Laura Vega",
    "cursoNombre": "Curso de Java"
  },
  {
    "id": 2,
    "titulo": "Error al cargar la página de inicio",
    "mensaje": "Mi página de inicio está mostrando un error 404.",
    "fechaCreacion": "2025-01-16T14:45:25.123456",
    "status": "CERRADO",
    "autorNombre": "Carlos Pérez",
    "cursoNombre": "Curso de React"
  }
]
```

---

### 3. **Obtener un Tema por ID**

**GET** `/temas/{id}`

Obtiene los detalles de un tema específico por su ID.

**Response:**
```json
{
  "id": 1,
  "titulo": "Problemas con la instalación de Hibernate",
  "mensaje": "Intenté instalar Hibernate pero estoy recibiendo un error. ¿Alguien sabe cómo solucionarlo?",
  "fechaCreacion": "2025-01-17T12:04:33.799043",
  "status": "ABIERTO",
  "autorNombre": "Laura Vega",
  "cursoNombre": "Curso de Java"
}
```

---

### 4. **Actualizar un Tema**

**PUT** `/temas/{id}`

Actualiza un tema existente por su ID.

**Body:**
```json
{
  "titulo": "Problema resuelto con Spring Boot",
  "mensaje": "El problema ya fue solucionado, gracias por la ayuda...",
  "autorId": 1,
  "cursoId": 2,
  "status": "RESUELTO"
}
```

**Response:**
```json
{
  "id": 1,
  "titulo": "Problema resuelto con Spring Boot",
  "mensaje": "El problema ya fue solucionado, gracias por la ayuda...",
  "fechaCreacion": "2025-01-17T12:04:33.799043",
  "status": "RESUELTO",
  "autorNombre": "Laura Vega",
  "cursoNombre": "Curso de Java"
}
```

---

### 5. **Cambiar el Estado de un Tema**

**PUT** `/temas/{id}/estado`

Actualiza el estado de un tema. El estado puede ser `ABIERTO`, `CERRADO`, o `RESUELTO`.

**Request Param:**
- `nuevoEstado`: El nuevo estado del tema (puede ser `ABIERTO`, `CERRADO`, o `RESUELTO`).

**Response:**
```json
{
  "id": 1,
  "titulo": "Problema resuelto con Spring Boot",
  "mensaje": "El problema ya fue solucionado, gracias por la ayuda...",
  "fechaCreacion": "2025-01-17T12:04:33.799043",
  "status": "RESUELTO",
  "autorNombre": "Laura Vega",
  "cursoNombre": "Curso de Java"
}
```

---

### 6. **Eliminar un Tema**

**DELETE** `/temas/{id}`

Elimina un tema específico por su ID.

**Response:**
```json
{
  "message": "Tema eliminado exitosamente"
}
```

---

## Tecnologías Utilizadas

Este proyecto utiliza las siguientes tecnologías para su desarrollo:

- **Spring Boot**: Framework principal para construir aplicaciones Java basadas en Spring. Nos permite crear y configurar de manera sencilla las APIs REST.
  
- **Spring Security**: Proporciona las herramientas necesarias para gestionar la seguridad, la autenticación y la autorización de los usuarios dentro de la API.
  
- **JPA/Hibernate**: Hibernate es una implementación de Java Persistence API (JPA), que nos permite gestionar las interacciones con la base de datos de manera eficiente, utilizando entidades Java que se mapean a tablas en la base de datos.

- **PostgreSQL**: Es el sistema de gestión de bases de datos relacionales utilizado para almacenar la información de los temas, usuarios y cursos.
  
- **JSON Web Tokens (JWT)**: Utilizado para la autenticación de usuarios y gestión de sesiones de manera segura.

- **Maven**: Herramienta de gestión de dependencias y construcción del proyecto.

---

 ## Instrucciones de instalación
 
## Clonar el repositorio

**1**.Abre IntelliJ IDEA.
**2**.En la pantalla de inicio, selecciona "Get from Version Control".
**3**.En la URL de Git, ingresa el siguiente enlace de tu repositorio de GitHub:
https://github.com/usuario/foro-hub.git
**4**.Haz clic en Clone.

## Configuración del proyecto en IntelliJ IDEA

**1.Abrir el proyecto**: Una vez descargado el proyecto, IntelliJ IDEA te preguntará si deseas abrirlo. Haz clic en Yes.
**2.Configurar el JDK**: Asegúrate de tener el JDK 17 configurado para el proyecto. Si no lo tienes, puedes instalarlo y configurarlo desde File > Project Structure > Project.
**3.Construcción del proyecto**: El proyecto utiliza Maven. IntelliJ IDEA descargará todas las dependencias automáticamente al abrirlo.
**4.Ejecutar el proyecto**: Haz clic en Run > Run 'Application' para ejecutar la aplicación.

## Seguridad
La aplicación utiliza Spring Security para proteger la API. Si deseas acceder a recursos protegidos, necesitarás autenticarte mediante un token JWT.

## Autenticación
**Método de autenticación**: JWT (JSON Web Token)
**Endpoint para autenticación**: /auth/login
**Cuerpo de la solicitud**:

## json
{
  "username": "usuario",
  "password": "contraseña"
}

Una vez autenticado, recibirás un token JWT que deberás incluir en el encabezado Authorization en las siguientes solicitudes:

**Authorization**: Bearer <token>

## Base de datos
El proyecto está configurado con H2 Database en memoria, lo que facilita la configuración y las pruebas locales. Si deseas utilizar una base de datos persistente como MySQL o PostgreSQL, solo necesitas ajustar las configuraciones de la base de datos en el archivo application.properties o application.yml.

--

## Contribuciones

Si deseas contribuir al proyecto, realiza un fork del repositorio y envía un pull request con tus cambios. Asegúrate de seguir las mejores prácticas de desarrollo y de realizar pruebas antes de enviar tu contribución.

---

## Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.
```

---
