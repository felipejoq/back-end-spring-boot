# Clientes App - Spring Boot
Este es un desarrollo para construir un servidor Rest usando Spring Boot y así servir a los clientes, tales como Angular en este caso.

### 🧑‍💻 Requiere
* Java (Spring Boot)
* SQL (MySQL)

### 🔗 Servicios creados

| Ruta                      | Args      | Type         | Método | Respuesta | Descripción                                                                                           |
|---------------------------|-----------|--------------|--------|-----------|-------------------------------------------------------------------------------------------------------|
| /api/clients              | -         | -            | GET    | JSON      | Lista de Clientes registrados en la Base de Datos                                                     |
| /api/clients/{id}         | id        | number       | GET    | JSON      | Obtiene un Cliente buscando por su ID                                                                 |
| /api/clients              | -         | -            | POST   | JSON      | Recibe un JSON con estructura Cliente y lo salva en la DB                                             |
| /api/clients/{id}         | id        | number       | PUT    | JSON      | Edita propiedades de un Cliente a través de su ID                                                     |
| /api/clients/{id}         | id        | number       | DELETE | JSON      | Recibe un JSON con structura Cliente luego de eliminarlo de la DB                                     |
| /api/clients/photo/upload | photo, id | file, number | POST   | JSON      | Recibe un archivo "photo" y "id" de un cliente existente para agregar o actualizar su foto de perfil. |
___

### 📋 TODO List:
- Asociar la entidad Client con Factura. ⏳
- Añadir seguridad con JWT, OAuth.
- ...
- Subir imágenes y asociarlas a un cliente. ✅
- Interactuar con el servicio de "Cloudinary" (CRUD de recursos)
- Paginar el resultado de todos los Clientes. ✅
- Obtener un Cliente por ID. ✅
- Editar un objeto Cliente. ✅
- Eliminar un objeto Cliente. ✅