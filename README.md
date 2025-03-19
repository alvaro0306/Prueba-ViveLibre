# Prueba ViveLibre


Este proyecto contiene dos APIs desarrolladas en Java con Spring Boot:

- API Consumidora: Se encarga de consumir información de libros y consumir el servicio externo.
- API Simulada: Simula un servicio de autenticación que genera un token.

## Instalación
-  Clonar el repositorio:
```sh
git clone https://github.com/alvaro0306/Prueba-ViveLibre.git
cd Prueba-ViveLibre
```
- Construir el proyecto:
```sh
mvn clean install
```
## Ejecución
- Api Consumidora
```sh
cd api-consumidora
mvn spring-boot:run
```
- API Simulada
```sh
cd api-simulada
mvn spring-boot:run
```

## Endpoints

API consumidora
-  GET /api/books/filer
-  GET /api/books/jk-rowling
-  GET /api/books/author-count
-  GET /api/books/export
-  GET /api/token

API simulada
- POST /token


## Pruebas
```sh
mvn test
```
