package me.elordenador.practica6;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Practica6 {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Dispositivo> dispositivos;
    public static void main(String[] args) {
        cargarDatos();
        boolean salida = false;
        while (!salida) {
            System.out.println("MENU PRINCIPAL:\n" +
                    "1. Añadir Dispositivo\n" +
                    "2. Mostrar Dispositivos\n" +
                    "3. Buscar Dispositivo\n" +
                    "4. Borrar Dispositivo\n" +
                    "5. Cambiar Estado Dispositivo\n" +
                    "6. Modificar Dispositivo\n" +
                    "7. Salir");

            try {
                int option = sc.nextInt(); sc.nextLine();
                switch (option) {
                    case 1: anadirDispositivo(); break;
                    case 2: mostrarDispositivos(); break;
                    case 3: buscarDispositivo(); break;
                    case 4: borrarDispositivo(); break;
                    case 5: cambiarEstadoDispositivo(); break;
                    case 6: modificarDispositivo(); break;
                    case 7: salida = true; break;
                }

            } catch (Exception e) {
                System.err.println("Ha ocurrido un error al leer la opción.");
                sc.nextLine();
            }
        }

    }

    private static void modificarDispositivo() {
        // TODO: Implement function
    }

    private static void cambiarEstadoDispositivo() {
        // TODO: Implement function
    }

    private static void borrarDispositivo() {
        try {
            System.out.print("ID Dispositivo: ");
            int id = sc.nextInt(); sc.nextLine();

            Dispositivo dispositivo = dispositivos.get(id);
            dispositivo.delete();
        } catch (NumberFormatException e) {
            System.err.println("Error al leer el numero");
            sc.nextLine();
        }
    }

    private static void buscarDispositivo() {
        try {
            System.out.print("ID Dispositivo: ");
            int id = sc.nextInt(); sc.nextLine();

            Dispositivo dispositivo = dispositivos.get(id);

            if (dispositivo instanceof Ordenador) {
                Ordenador ordenador = (Ordenador) dispositivo;
                System.out.println(" " + id + " | ORDENADOR | " + ordenador.toString());
            } else if (dispositivo instanceof Impresora) {
                Impresora impresora = (Impresora) dispositivo;
                System.out.println(" " + id + " | IMPRESORA | " + impresora.toString());
            } else {
                System.out.println(" " + id + " | GENERICO | " + dispositivo.toString());
            }
        } catch (InputMismatchException e) {
            System.err.println("Error validando los datos");
            sc.nextLine();
        }
    }

    private static void mostrarDispositivos() {
        for (int i = 0; i < dispositivos.size(); i++) {
            Dispositivo dispositivo = dispositivos.get(i);
            if (dispositivo instanceof Ordenador) {
                System.out.println(" " + i + " | Ordenador | " + dispositivo.toString());
            } else if (dispositivo instanceof Impresora) {
                System.out.println(" " + i + " | Impresora | " + dispositivo.toString());
            } else {
                System.out.println(" " + i + " | " + dispositivo.toString());
            }


        }
    }

    private static void anadirDispositivo() {
        System.out.println("Que tipo de dispositivo: \n" +
                "1. Dispositivo Generico\n" +
                "2. Ordenador\n" +
                "3. Impresora");

        try {
            int option = sc.nextInt(); sc.nextLine();
            if (option == 1) {
                System.out.print("Marca: ");
                String marca = sc.nextLine();
                System.out.print("Modelo: ");
                String modelo = sc.nextLine();
                System.out.print("¿Funciona?: ");
                String funcionaS = sc.nextLine();
                boolean funciona = !funcionaS.toLowerCase().equals("n");

                Dispositivo dispositivo = new Dispositivo(marca, modelo, funciona);
                dispositivo.save();
                cargarDatos();

            } else if (option == 2) {
                System.out.print("Marca: ");
                String marca = sc.nextLine();
                System.out.print("Modelo: ");
                String modelo = sc.nextLine();
                System.out.print("¿Funciona? ");
                String funcionaS = sc.nextLine();
                boolean funciona = !funcionaS.toLowerCase().equals("n");
                System.out.print("Cantidad RAM (En GB): ");
                int ram = sc.nextInt(); sc.nextLine();
                System.out.print("Procesador: ");
                String procesador = sc.nextLine();
                System.out.print("Tamaño de disco (En GB): ");
                int tamDisco = sc.nextInt(); sc.nextLine();
                System.out.print("Tipo de Disco (HDD, SSD_SATA, SSD_NVME)");
                Disco disco = Disco.valueOf(sc.nextLine().toUpperCase());

                Ordenador ordenador = new Ordenador(marca, modelo, funciona, ram, procesador, tamDisco, disco);
                ordenador.save();
                cargarDatos();
            } else if (option == 3) {
                System.out.print("Marca: ");
                String marca = sc.nextLine();
                System.out.print("Modelo: ");
                String modelo = sc.nextLine();
                System.out.print("¿Funciona? ");
                boolean funciona = !sc.nextLine().toLowerCase().equals("n");
                System.out.print("Tipo Impresora (LASER, INY_TINTA, OTROS): ");
                TipoImpresora tipo = TipoImpresora.valueOf(sc.nextLine().toUpperCase());

                System.out.print("¿Es de Color? [Y:n]: ");
                boolean color = !sc.nextLine().toLowerCase().equals("n");

                System.out.print("¿Dispone Escaner? [Y:n]: ");
                boolean scanner = !sc.nextLine().toLowerCase().equals("n");

                Impresora impresora = new Impresora(marca, modelo, funciona, tipo, color, scanner);
                impresora.save();
                cargarDatos();


            }
        } catch (NumberFormatException e) {
            sc.nextLine();
            System.err.println("Hubo un error leyendo el numero");
        }
    }

    public static void cargarDatos() {
        dispositivos = new ArrayList<Dispositivo>();
        Dispositivo.init();
        Ordenador.init();
        Impresora.init();

        for (int i = 0; i < Dispositivo.length(); i++) {
            Dispositivo dispositivo = new Dispositivo(i);
            try {
                dispositivo.load();
                dispositivos.add(dispositivo);
            } catch (ElementNotFoundException e) {
                if (!e.logicaldelete) {
                    System.err.println("Ha ocurrido un error");
                }
            }

        }

        for (int i = 0; i < Ordenador.length(); i++) {
            Ordenador ordenador = new Ordenador(i);
            try {
                ordenador.load();
                dispositivos.add((Dispositivo) ordenador);
            } catch (ElementNotFoundException e) {
                if (!e.logicaldelete) {
                    System.err.println("Ha ocurrido un error");
                }
            }
        }

        for (int i = 0; i < Impresora.length(); i++) {
            Impresora impresora = new Impresora(i);
            try {
                impresora.load();
                dispositivos.add((Dispositivo) impresora);
            } catch (ElementNotFoundException e) {
                if (!e.logicaldelete) {
                    System.err.println("Ha ocurrido un error");
                }
            }
        }
    }
}
