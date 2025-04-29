/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.exp3_s7_francisco_parra;

/**
 *
 * @author fparraa
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Exp3_S7_Francisco_Parra {

    private static Scanner scanner = new Scanner(System.in);

    private static final int FILAS = 3;
    private static final int COLUMNAS = 10;
    private static String[][] zona1 = new String[FILAS][COLUMNAS];
    private static String[][] zona2 = new String[FILAS][COLUMNAS];
    private static String[][] zona3 = new String[FILAS][COLUMNAS];

    private static final double PRECIO_1 = 20000;
    private static final double PRECIO_2 = 15000;
    private static final double PRECIO_3 = 10000;

    private static List<Entrada> carritoCompras = new ArrayList<>();
    private static List<Entrada> reservas = new ArrayList<>();

    private static double ingresosTotales = 0;

    private static Timer timer = new Timer();

    public static void main(String[] args) {
        inicializarTeatro();
        menu();
    }

    private static void inicializarTeatro() {
        for (int i = 0; i < FILAS; i++) {
            Arrays.fill(zona1[i], "Disponible");
            Arrays.fill(zona2[i], "Disponible");
            Arrays.fill(zona3[i], "Disponible");
        }
    }

    private static void menu() {
        int opcion;
        do {
            System.out.println("\n=== Sistema de Gestión de Entradas ===");
            System.out.println("1. Comprar Entrada");
            System.out.println("2. Reservar Entrada");
            System.out.println("3. Confirmar Reserva");
            System.out.println("4. Modificar Entrada del Carrito");
            System.out.println("5. Visualizar Resumen de Ventas");
            System.out.println("6. Imprimir Boleta y Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> comprarEntrada(false);
                case 2 -> comprarEntrada(true);
                case 3 -> confirmarReserva();
                case 4 -> modificarEntrada();
                case 5 -> visualizarResumenVentas();
                case 6 -> imprimirBoleta();
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 6);
    }

    private static void comprarEntrada(boolean esReserva) {
        int zona = solicitarZona();
        if (zona == -1) return;

        mostrarAsientos(zona);

        int fila = solicitarFila(zona);
        if (fila == -1) return;

        int columna = solicitarColumna();
        if (columna == -1) return;

        if (!asientoDisponible(zona, fila, columna)) {
            System.out.println("Asiento no disponible.");
            return;
        }

        double precio = obtenerPrecioZona(zona);

        int edad = -1;
int intentosEdad = 0;
while (intentosEdad < 2) {
    System.out.print("Ingrese su edad: ");
    String edadInput = scanner.nextLine();
    try {
        edad = Integer.parseInt(edadInput);
        if (edad >= 0 && edad <= 120) { // Edad razonable
            break;
        } else {
            System.out.println("Edad fuera de rango válido. Intente nuevamente.");
        }
    } catch (NumberFormatException e) {
        System.out.println("Entrada inválida. Debe ingresar un número. Intente nuevamente.");
    }
    intentosEdad++;
}

if (edad == -1 || edad < 0 || edad > 120) {
    System.out.println("Demasiados intentos inválidos. Volviendo al menú principal...");
    return;
}


        boolean estudiante = false;
int intentosEstudiante = 0;
while (intentosEstudiante < 2) {
    System.out.print("¿Es estudiante? (s/n): ");
    String respuesta = scanner.nextLine().trim().toLowerCase();
    if (respuesta.equals("s")) {
        estudiante = true;
        break;
    } else if (respuesta.equals("n")) {
        estudiante = false;
        break;
    } else {
        System.out.println("Entrada inválida. Debe ingresar 's' o 'n'. Intente nuevamente.");
        intentosEstudiante++;
    }
}

if (intentosEstudiante == 2) {
    System.out.println("Demasiados intentos inválidos. Volviendo al menú principal...");
    return;
}


        double descuento = 0;
        if (edad >= 60) {
            descuento = 0.15;
        } else if (estudiante) {
            descuento = 0.10;
        }

        double precioFinal = precio - (precio * descuento);

        Entrada entrada = new Entrada(zona, fila, columna, precio, descuento, precioFinal);

        if (esReserva) {
            reservas.add(entrada);
            bloquearAsiento(zona, fila, columna, "Reservado");
            programarExpiracionReserva(entrada);
            System.out.println("Entrada reservada exitosamente por 2 minutos.");
            System.out.println("=== Resumen de la Reserva ===");
System.out.println("Zona: " + zona);
System.out.println("Fila: " + obtenerLetraFila(zona, fila));
System.out.println("Columna: " + (columna + 1));
System.out.println("Precio Base: $" + precio);
System.out.println("Descuento Aplicado: $" + (precio * descuento));
System.out.println("Precio Final: $" + precioFinal);
System.out.println("============================");
        } else {
            carritoCompras.add(entrada);
            bloquearAsiento(zona, fila, columna, "Comprado");
            ingresosTotales += precioFinal;
            System.out.println("Entrada comprada exitosamente.");
            System.out.println("=== Resumen de la Compra ===");
            System.out.println("Ubicación: " + obtenerNombreZona(zona));
            System.out.println("Fila: " + obtenerLetraFila(zona, fila));
            System.out.println("Columna: " + (columna + 1));
            System.out.println("Precio Base: $" + precio);
            System.out.println("Descuento Aplicado: $" + (precio * descuento));
            System.out.println("Precio Final: $" + precioFinal);
            System.out.println("============================");
        }
    }
  private static int solicitarZona() {
    int intentos = 0;
    while (intentos < 2) {
        System.out.print("Seleccione Ubicación (1. VIP, 2. Platea, 3. Balcón): ");
        String zonaInput = scanner.nextLine();
        try {
            int zona = Integer.parseInt(zonaInput);
            if (zona >= 1 && zona <= 3) {
                return zona;
            } else {
                System.out.println("Ubicación inválida. Intente nuevamente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Debe ingresar un número válido. Intente nuevamente.");
        }
        intentos++;
    }
    System.out.println("Demasiados intentos inválidos. Volviendo al menú principal...");
    return -1;
}

    private static int solicitarFila(int zona) {
        int intentos = 0;
        while (intentos < 2) {
            System.out.print("Seleccione fila (letra): ");
            String filaLetra = scanner.nextLine().toUpperCase();
            int fila = convertirFilaALetra(zona, filaLetra);
            if (fila != -1) {
                return fila;
            } else {
                System.out.println("Fila inválida. Intente nuevamente.");
                intentos++;
            }
        }
        System.out.println("Demasiados intentos fallidos. Volviendo al menú principal...");
        return -1;
    }

      private static int solicitarColumna() {
    int intentos = 0;
    while (intentos < 2) {
        System.out.print("Seleccione número de columna (1-10): ");
        String columnaInput = scanner.nextLine();
        try {
            int columna = Integer.parseInt(columnaInput) - 1;
            if (columna >= 0 && columna < COLUMNAS) {
                return columna;
            } else {
                System.out.println("Columna inválida. Debe estar entre 1 y 10. Intente nuevamente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Debe ingresar un número. Intente nuevamente.");
        }
        intentos++;
    }
    System.out.println("Demasiados intentos inválidos. Volviendo al menú principal...");
    return -1;
}


    private static int convertirFilaALetra(int zona, String letra) {
        switch (zona) {
            case 1 -> { if ("A".equals(letra)) return 0; if ("B".equals(letra)) return 1; if ("C".equals(letra)) return 2; }
            case 2 -> { if ("D".equals(letra)) return 0; if ("E".equals(letra)) return 1; if ("F".equals(letra)) return 2; }
            case 3 -> { if ("G".equals(letra)) return 0; if ("H".equals(letra)) return 1; if ("I".equals(letra)) return 2; }
        }
        return -1;
    }

    private static void mostrarAsientos(int zona) {
        String[][] area = obtenerZona(zona);
        System.out.println("\nEstado de los Asientos (Ubicación " + zona + "):");
        for (int i = 0; i < FILAS; i++) {
            String filaLetra = obtenerLetraFila(zona, i);
            System.out.print("Fila " + filaLetra + ": ");
            for (int j = 0; j < COLUMNAS; j++) {
                if (area[i][j].equals("Disponible")) {
                    System.out.print("[" + filaLetra + (j+1) + "] ");
                } else {
                    System.out.print("[" + area[i][j] + "] ");
                }
            }
            System.out.println();
        }
    }
      
    private static String obtenerLetraFila(int zona, int fila) {
        return switch (zona) {
            case 1 -> String.valueOf((char) ('A' + fila));
            case 2 -> String.valueOf((char) ('D' + fila));
            case 3 -> String.valueOf((char) ('G' + fila));
            default -> "?";
        };
    }

    private static boolean asientoDisponible(int zona, int fila, int columna) {
        String[][] area = obtenerZona(zona);
        return area[fila][columna].equals("Disponible");
    }

    private static double obtenerPrecioZona(int zona) {
        return switch (zona) {
            case 1 -> PRECIO_1;
            case 2 -> PRECIO_2;
            case 3 -> PRECIO_3;
            default -> 0;
        };
    }

    private static String[][] obtenerZona(int zona) {
        return switch (zona) {
            case 1 -> zona1;
            case 2 -> zona2;
            case 3 -> zona3;
            default -> null;
        };
    }
    
    private static String obtenerNombreZona(int zona) {
        return switch (zona) {
            case 1 -> "VIP";
            case 2 -> "PLATEA";
            case 3 -> "BALCON";
            default -> "DESCONOCIDA";
        };
    }


    private static void bloquearAsiento(int zona, int fila, int columna, String estado) {
        obtenerZona(zona)[fila][columna] = estado;
    }

    private static void programarExpiracionReserva(Entrada entrada) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (reservas.contains(entrada)) {
                    reservas.remove(entrada);
                    bloquearAsiento(entrada.zona, entrada.fila, entrada.columna, "Disponible");
                    System.out.println("\nReserva expirada para Ubicación " + obtenerNombreZona(entrada.zona) + ", fila " + entrada.fila + ", asiento " + entrada.columna);
                }
            }
        }, 120000);
    }

    private static void confirmarReserva() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas activas.");
            return;
        }

        System.out.println("Reservas activas:");
        for (int i = 0; i < reservas.size(); i++) {
            Entrada e = reservas.get(i);
            System.out.println((i + 1) + ". Ubicación: " + obtenerNombreZona(e.zona) + " , Fila: " + obtenerLetraFila(e.zona, e.fila) + " , Columna: " + (e.columna + 1)+ " , Precio Base: $"+e.precioBase+ " , Descuento: #"+(e.precioBase*e.descuento)+ ", Precio Final: $"+e.precioFinal);
        }

        System.out.print("Seleccione número de reserva a confirmar: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        if (seleccion < 1 || seleccion > reservas.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Entrada entradaConfirmada = reservas.remove(seleccion - 1);
        carritoCompras.add(entradaConfirmada);
        bloquearAsiento(entradaConfirmada.zona, entradaConfirmada.fila, entradaConfirmada.columna, "Comprado");
        ingresosTotales += entradaConfirmada.precioFinal;
        System.out.println("Reserva confirmada como compra.");
    }
    private static void modificarEntrada() {
        System.out.println("Función de modificar entrada aún no implementada.");
    }

    private static void imprimirBoleta() {
        System.out.println("\n=== Boleta ===");
        if (carritoCompras.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        int boleta = 0;
        for (Entrada e : carritoCompras) {
            System.out.println("Boleta N°" + (boleta+1) );
            System.out.println("Ubicación: " + obtenerNombreZona(e.zona) + ", Fila: " + obtenerLetraFila(e.zona, e.fila) + ", Columna: " + (e.columna + 1));
            System.out.println("Precio Base: $" + e.precioBase);
            System.out.println("Descuento Aplicado: $" + (e.precioBase * e.descuento));
            System.out.println("Precio Final: $" + e.precioFinal);
            System.out.println("----------------------------");
            boleta = boleta+1;
             }
        System.out.println("Gracias por su compra. Hasta pronto!");
        System.exit(0);
       
    }

    private static void visualizarResumenVentas() {
        System.out.println("\n=== Resumen Ventas  ===");
        
        for (Entrada e : carritoCompras) {
            System.out.println("Ubicación: " + obtenerNombreZona(e.zona) + ", Fila: " + obtenerLetraFila(e.zona, e.fila) + ", Columna: " + (e.columna + 1) + ", Precio: $" + e.precioFinal);
        }
        System.out.println("Total Ventas: $" + ingresosTotales);
    }

    static class Entrada {
        int zona;
        int fila;
        int columna;
        double precioBase;
        double descuento;
        double precioFinal;

        Entrada(int zona, int fila, int columna, double precioBase, double descuento, double precioFinal) {
            this.zona = zona;
            this.fila = fila;
            this.columna = columna;
            this.precioBase = precioBase;
            this.descuento = descuento;
            this.precioFinal = precioFinal;
        }
    }
}
