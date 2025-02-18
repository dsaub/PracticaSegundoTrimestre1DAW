package me.elordenador.practica6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Clase principal para manejarse por el programa
 * @author Daniel Sánchez Úbeda
 */
public class Practica6 {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Dispositivo> dispositivos;

    /**
     * Función main, carga el menú principal
     * @param args argumentos del programa (pasados por el usuario al ejecutarlo)
     */
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

    /**
     * Metodo que abre el menú para modificar el dispositivo
     */
    private static void modificarDispositivo() {
        try {
            System.out.print("ID Dispositivo: ");
            int id = sc.nextInt(); sc.nextLine();

            Dispositivo dispositivo = dispositivos.get(id);
            if (dispositivo instanceof Ordenador) {
                modify((Ordenador) dispositivo);
            } else if (dispositivo instanceof Impresora) {
                modify((Impresora) dispositivo);
            } else {
                modify(dispositivo);
            }
        } catch (InputMismatchException e) {
            System.err.println("Hubo un error");
        }
    }

    /**
     * Función que abre el menú para modificar un Dispositivo Genérico
     * @param dispositivo El dispositivo a modificar
     */
    private static void modify(Dispositivo dispositivo) {
        try {
            boolean salida = false;
            while (!salida) {
                System.out.println("¿Que deseas modificar?\n" +
                        "  1. Marca\n" +
                        "  2. Modelo\n" +
                        "  3. Finalizar\n" +
                        "  4. Descartar");
                System.out.print("Seleccione");
                int seleccion = sc.nextInt(); sc.nextLine();

                if (seleccion == 1) {
                    System.out.print("Marca: ");
                    dispositivo.setMarca(sc.nextLine());
                }
                if (seleccion == 2) {
                    System.out.print("Modelo: ");
                    dispositivo.setModelo(sc.nextLine());
                }
                if (seleccion == 3) {
                    dispositivo.save();
                    cargarDatos();
                    salida = true;
                }
                if (seleccion == 4) {
                    cargarDatos();
                    salida = true;
                }
            }
        } catch (InputMismatchException e) {
            System.err.println("Hubo un error leyendo");
            sc.nextLine();
        }
    }

    /**
     * Función que abre el menú para modificar una Impresora
     * @param dispositivo La impresora a modificar
     */
    private static void modify(Impresora dispositivo) {

        try {
            boolean salida = false;
            while (!salida) {
                System.out.println(dispositivo.toString());

                System.out.println("¿Que desea modificar?\n" +
                        "  1. Marca\n" +
                        "  2. Modelo\n" +
                        "  3. Tipo\n" +
                        "  4. Alternar Color\n" +
                        "  5. Alternar Escaner\n" +
                        "  6. Guardar\n" +
                        "  7. Descartar\n");

                System.out.print("Opción: ");
                int opcion = sc.nextInt(); sc.nextLine();
                if (opcion == 1) {
                    System.out.print("Marca: ");
                    dispositivo.setMarca(sc.nextLine());
                }
                if (opcion == 2) {
                    System.out.print("Modelo: ");
                    dispositivo.setModelo(sc.nextLine());
                }
                if (opcion == 3) {
                    System.out.print("Tipo (LASER, INY_TINTA, OTROS): ");
                    dispositivo.setTipo(TipoImpresora.valueOf(sc.nextLine()));
                }
                if (opcion == 4) {
                    dispositivo.setColor(!dispositivo.getColor());
                }
                if (opcion == 5) {
                    dispositivo.setEscaner(!dispositivo.getEscaner());
                }
                if (opcion == 6) {
                    dispositivo.save();
                    cargarDatos();
                    salida = true;
                }
                if (opcion == 7) {
                    salida = true;
                }
            }

        } catch (InputMismatchException e) {
            System.err.println("Hubo un error leyendo el archivo");
            sc.nextLine();
        }
    }

    /**
     * Función para modificar un ordenador
     * @param dispositivo El ordenador a modificar
     */
    private static void modify(Ordenador dispositivo) {
        try {
            boolean salida = false;
            while (!salida) {
                System.out.println(dispositivo.toString());

                System.out.println("¿Que desea modificar?\n" +
                        "  1. Marca\n" +
                        "  2. Modelo\n" +
                        "  3. RAM\n" +
                        "  4. Procesador\n" +
                        "  5. Tamaño Disco\n" +
                        "  6. Tipo Disco\n" +
                        "  7. Alternar Estado\n" +
                        "  8. Guardar\n" +
                        "  9. Descartar");

                System.out.print("Opción: ");
                int opcion = sc.nextInt(); sc.nextLine();
                if (opcion == 1) {
                    System.out.print("Marca: ");
                    dispositivo.setMarca(sc.nextLine());
                }
                if (opcion == 2) {
                    System.out.print("Modelo: ");
                    dispositivo.setModelo(sc.nextLine());
                }
                if (opcion == 3) {
                    System.out.print("Cantidad de Memoria RAM: ");
                    dispositivo.setRam(sc.nextInt());
                    sc.nextLine();
                }
                if (opcion == 4) {
                    System.out.print("Procesador: ");
                    dispositivo.setProcesador(sc.nextLine());
                }
                if (opcion == 5) {
                    System.out.print("Tamaño nuevo (En GB): ");
                    dispositivo.setTamDisco(sc.nextInt());
                    sc.nextLine();
                }
                if (opcion == 6) {
                    System.out.println("Tipo Disco (HDD, SSD_SATA, SSD_NVME): ");
                    dispositivo.setTipoDisco(Disco.valueOf(sc.nextLine()));
                }
                if (opcion == 7) {
                    dispositivo.setEstado(!dispositivo.getEstado());
                }
                if (opcion == 8) {
                    dispositivo.save();
                    cargarDatos();
                    salida = true;
                }
                if (opcion == 9) {
                    salida = true;
                }
            }

        } catch (InputMismatchException e) {
            System.err.println("Hubo un error leyendo el archivo");
            sc.nextLine();
        }
    }

    /**
     * Función para cambiar el estado del dispositivo (alterna de True a False o viceversa)
     */
    private static void cambiarEstadoDispositivo() {
        try {
            System.out.print("ID Dispositivo: ");
            int id = sc.nextInt(); sc.nextLine();

            Dispositivo dispositivo = dispositivos.get(id);
            dispositivo.setEstado(!dispositivo.getEstado());
        } catch (InputMismatchException e) {
            System.err.println("Ha ocurrido un error");
            sc.nextLine();
        }
    }

    /**
     * Función para borrar un dispositivo
     */
    private static void borrarDispositivo() {
        try {
            System.out.print("ID Dispositivo: ");
            int id = sc.nextInt(); sc.nextLine();

            Dispositivo dispositivo = dispositivos.get(id);
            dispositivo.delete();
        } catch (NumberFormatException e) {
            System.err.println("Error al leer el numero");
            sc.nextLine();
        } catch (IOException e) {
            System.err.println("Hubo un error eliminando el dispositivo");
        }
    }

    /**
     * Función para buscar por ID un dispositivo
     */
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

    /**
     * Función para mostrar los dispositivos
     */
    private static void mostrarDispositivos() {
        for (int i = 0; i < dispositivos.size(); i++) {
            Dispositivo dispositivo = dispositivos.get(i);
            if (dispositivo instanceof Ordenador) {
                System.out.println(" " + i + " | Ordenador | " + dispositivo.toString());
            } else if (dispositivo instanceof Impresora) {
                System.out.println(" " + i + " | Impresora | " + dispositivo.toString());
            } else {
                System.out.println(" " + i + " | Generico | " + dispositivo.toString());
            }


        }
    }

    /**
     * Función para añadir un dispositivo
     */
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

    /**
     * Carga los datos en los archivos
     */
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
                    System.err.println("  El archivo (dispositivos.dat) esta corrupto");
                    System.err.println("  Contacte con el desarrollador de este software si regenerar el archivo no ayuda");
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
                    System.err.println("  El archivo (ordenadores.dat) esta corrupto");
                    System.err.println("  Contacte con el desarrollador de este software si regenerar el archivo no ayuda");
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
                    System.err.println("  El archivo (impresoras.dat) esta corrupto");
                    System.err.println("  Contacte con el desarrollador de este software si regenerar el archivo no ayuda");
                }
            }
        }
    }
}
