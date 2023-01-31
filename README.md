# Clientes App - Spring Boot
Este es un desarrollo para construir un servidor Rest usando Spring Boot y así servir a los clientes, [tales como Angular en este caso](https://github.com/felipejoq/front-angular-clients).

### 🧑‍💻 Requiere
* Java (Spring Boot)
* SQL (MariaDB)
* Servicio Host Media (Cloudinary)

### 🔗 Servicios creados

| Ruta                              | Args                                       | Type                  | Método | Respuesta | Descripción                                                                                                |
|-----------------------------------|--------------------------------------------|-----------------------|--------|-----------|------------------------------------------------------------------------------------------------------------|
| /oauth/token                      | username, password, grant_type, Basic Auth | x-www-form-urlencoded | POST   | JSON      | Retorna datos de credenciales para autenticación y autorización.                                           |
| /api/clients                      | -                                          | -                     | GET    | JSON      | Lista de Clientes registrados en la Base de Datos                                                          |
| /api/clients/{id}                 | id                                         | number                | GET    | JSON      | Obtiene un Cliente buscando por su ID                                                                      |
| /api/clients                      | Client                                     | Object                | POST   | JSON      | Recibe un JSON con estructura Cliente y lo salva en la DB                                                  |
| /api/clients/{id}                 | id                                         | number                | PUT    | JSON      | Edita propiedades de un Cliente a través de su ID                                                          |
| /api/clients/{id}                 | id                                         | number                | DELETE | JSON      | Elimina un cliente mediante la Id que se pasa por URL (id del cliente)                                     |
| /api/clients/photo/upload         | photo, id                                  | file, number          | POST   | JSON      | Recibe un archivo "photo" y "id" de un cliente existente para agregar o actualizar su foto de perfil.      |
| /api/clients/invoices/page/{page} | page                                       | number                | GET    | JSON      | Lista completa de facturas con 10 resultados y paginadas.                                                  |
| /api/clients/{id}/invoices        | id                                         | number                | GET    | JSON      | Lista de facturas asociadas a un cliente. (id de cliente por URL)                                          |
| /api/clients/invoices             | Invoice                                    | Object                | POST   | JSON      | Recibe la factura creada con todos sus atributos en formato JSON                                           |
| /api/clients/invoices/{id}        | id                                         | number                | GET    | JSON      | Obtiene una factura por su id (id de la factura).                                                          |
| /api/clients/invoices/{id}        | id                                         | number                | DELETE | JSON      | Elimina una factura por su id (id de la factura).                                                          |
| /api/countries/{term}             | term                                       | string                | GET    | JSON      | El parámetro "term" que se envía por URL es la búsqueda para obtener una lista de países con coincidencia. |
___

### 📋 TODO List:
- Validaciones para Factura ⏳
- ...
- Añadir seguridad con JWT, OAuth. ✅
- Asociar la entidad Client con Factura. ✅
- Asociar Factura con Items. ✅
- Asociar Cliente con Items. ✅
- Asociar un cliente con un país. ✅
- Subir imágenes y asociarlas a un cliente. ✅
- Interactuar con el servicio de "Cloudinary" (CRUD de recursos) ✅
- Paginar el resultado de todos los Clientes. ✅
- Obtener un Cliente por ID. ✅
- Editar un objeto Cliente. ✅
- Eliminar un objeto Cliente. ✅