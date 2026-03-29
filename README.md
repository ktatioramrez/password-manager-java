# 🔐 Gestor de Contraseñas Seguro - Java Web

Este proyecto es un sistema de gestión de credenciales desarrollado en Java, enfocado en la seguridad de la información. Permite a los usuarios registrarse, iniciar sesión y almacenar sus contraseñas de forma cifrada.

## 🚀 Características Principales
* **Autenticación Segura:** Registro e inicio de sesión con manejo de sesiones.
* **Cifrado de Extremo a Extremo:** Las contraseñas de los servicios (Netflix, Facebook, etc.) se cifran antes de guardarse en la base de datos.
* **Interfaz Responsiva:** Diseño moderno y limpio utilizando HTML5 y CSS3.
* **Arquitectura DAO:** Separación clara entre la lógica de negocio y el acceso a datos.

## 🛡️ Seguridad (Requerimientos No Funcionales)
El sistema implementa altos estándares de seguridad para proteger los datos del usuario:
* **BCrypt:** Se utiliza para el hashing de la contraseña maestra del usuario. Nunca almacenamos contraseñas en texto plano.
* **AES-256-GCM:** Algoritmo de cifrado simétrico avanzado utilizado para proteger las credenciales almacenadas en el Dashboard.
* **Protección contra Inyección SQL:** Uso de `PreparedStatement` en todas las consultas a la base de datos.

## 🛠️ Tecnologías Utilizadas
* **Lenguaje:** Java 8+
* **Framework/Librerías:** Java Servlets, Maven, JSON.org.
* **Base de Datos:** MySQL 8.0.
* **Frontend:** JavaScript (Fetch API), HTML5, CSS3.
* **Servidor:** Apache Tomcat 7/9.

## ⚙️ Configuración del Proyecto
1.  **Base de Datos:** Importa el archivo `database.sql` (si lo tienes) o crea la base de datos llamada `password_manager`.
2.  **Configuración de Conexión:** Revisa el archivo `DBConnection.java` y ajusta tu usuario y contraseña de MySQL.
3.  **Ejecución:**
    ```bash
    mvn clean compile
    mvn tomcat7:run
    ```
    Luego abre `http://localhost:8080` en tu navegador.

---
Desarrollado con amor por **K Tatiana O.Rmrez** - 2026
