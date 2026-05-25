# BrainCache

Aplicación de gestión de notas con autenticación JWT y búsqueda de notas públicas.

## Características

- Autenticación JWT con tokens de 24 horas
- Gestión de notas privadas y públicas
- Búsqueda de notas públicas con interfaz moderna
- Filtrado de notas personales (todas, públicas, privadas)
- Interfaz responsiva con diseño moderno
- REST API documentada con Swagger
- Base de datos PostgreSQL

## Tecnologías

**Backend:**
- Java 21
- Spring Boot 4.0.6
- Spring Data JPA
- Spring Security
- JWT (io.jsonwebtoken 0.11.5)
- PostgreSQL
- Maven
- Swagger/OpenAPI 2.3.0

**Frontend:**
- React
- Vite 8.0.14
- JavaScript
- CSS
- Fetch API

## Requisitos

- Java 21+
- Maven 3.6+
- Node.js 18+
- PostgreSQL 13+

## Instalación

### Backend

1. Clonar el repositorio:
```bash
git clone <repository-url>
cd braincache-api
```

2. Crear base de datos PostgreSQL:
```sql
CREATE DATABASE braincache;
```

3. Configurar variables de entorno:
```bash
export DB_USERNAME=your_db_username
export DB_PASSWORD=your_db_password
export JWT_SECRET=your-secret-key-here
```

4. Ejecutar:
```bash
./mvnw spring-boot:run
```

API disponible en `http://localhost:8080`

### Frontend

1. Navegar al directorio:
```bash
cd braincache-frontend
```

2. Configurar variables de entorno:
```bash
cp .env.example .env
```

3. Instalar dependencias:
```bash
npm install
```

4. Ejecutar:
```bash
npm run dev
```

Aplicación disponible en `http://localhost:5173`

## API

Documentación disponible en `http://localhost:8080/swagger-ui.html`

**Endpoints principales:**
- `POST /api/users/register` - Registro
- `POST /api/users/login` - Login (devuelve JWT)
- `GET /api/notepages/public` - Notas públicas
- `GET /api/notepages/user/{username}` - Notas de usuario (requiere auth)
- `POST /api/notepages` - Crear nota (requiere auth)
- `PUT /api/notepages/{id}` - Actualizar nota (requiere auth)
- `DELETE /api/notepages/{id}` - Eliminar nota (requiere auth)

## Seguridad

- JWT con expiración de 24 horas
- Contraseñas encriptadas con BCrypt
- Validación de entrada
- CORS configurado
- Variables de entorno para credenciales

## Estructura

```
braincache-api/
├── src/main/java/com/braincache_api/
│   ├── config/
│   ├── controller/
│   ├── entity/
│   ├── repository/
│   ├── security/
│   ├── service/
│   └── util/
├── src/main/resources/
│   └── application.properties
└── pom.xml

braincache-frontend/
├── src/
│   ├── components/
│   ├── services/
│   ├── App.jsx
│   └── App.css
├── .env.example
└── package.json
```

## Contribución

Las contribuciones son bienvenidas. Abre un issue para discutir cambios.

## Licencia

MIT

## Autor

Javier Moreno
