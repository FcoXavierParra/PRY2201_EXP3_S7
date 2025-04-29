🎟️ Sistema de Gestión de Entradas de Teatro
Este proyecto es una aplicación de consola en Java que simula la compra, reserva y gestión de entradas de un teatro. Permite a los usuarios seleccionar asientos en diferentes zonas (VIP, Platea, Balcón), aplicar descuentos según edad o condición de estudiante, y manejar un carrito de compras.

📂 Estructura del Proyecto
Paquete principal: com.mycompany.exp3_s7_francisco_parra
Clase principal: Exp3_S7_Francisco_Parra
Clases internas:
Entrada: representa una entrada comprada o reservada.

🛠️ Funcionalidades
Compra de entradas: Selección de zona, fila y columna, con aplicación automática de descuentos por edad (mayores de 60) o estudiantes.
Reserva de entradas: Permite reservar asientos durante 2 minutos antes de que se liberen automáticamente si no se confirman.
Confirmación de reservas: Los usuarios pueden confirmar sus reservas y agregar las entradas al carrito de compras.
Visualización del resumen de ventas: Permite revisar las entradas vendidas y el total de ingresos.
Generación de boleta: Al finalizar, se imprime un resumen de todas las entradas compradas.
Manejo de errores: Validación robusta para datos ingresados por el usuario.

🧩 Tecnologías Utilizadas
Lenguaje: Java
Entorno de Desarrollo: NetBeans / cualquier IDE compatible con Java SE
Funciones Java estándar:
Scanner
Timer y TimerTask
ArrayList


🎯 Mejoras Futuras
Implementar modificación de entradas en el carrito.
Agregar persistencia de datos en archivos (por ejemplo, guardar ventas en CSV o Base de Datos).
Mejorar la interfaz de usuario utilizando interfaces gráficas (JavaFX o Swing).
Manejar múltiples sesiones de usuario.

📋 Notas
El sistema permite máximo 2 intentos para ingresar datos válidos en cada campo (edad, condición de estudiante, zona, fila, columna).
Los asientos se representan en una matriz de 3 filas y 10 columnas por cada zona.
El tiempo de expiración para una reserva no confirmada es de 2 minutos.
