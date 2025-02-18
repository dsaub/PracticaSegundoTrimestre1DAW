package me.elordenador.practica6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase para manejar ordenadores
 * @version 1.0
 * @author Daniel Sánchez Úbeda
 */
public class Ordenador extends Dispositivo {
    private int ram;
    private String procesador;
    private int tamDisco;
    private Disco tipoDisco;
    private static File file = new File("ordenadores.dat");
    private static RandomAccessFile randomAccessFile;

    private static int nBytesT = 174;

    /**
     * Constructor de la clase, cogiendo los datos del dispositivo
     * @param marca La marca del ordenador
     * @param modelo El modelo del ordenador
     * @param estado Si el ordenador funciona o no
     * @param ram La RAM del ordenador (En Gigabytes)
     * @param procesador El Procesador del ordenador
     * @param tamDisco El Tamaño de Disco del ordenador
     * @param disco El tipo de Disco del ordenador
     */
    public Ordenador(String marca, String modelo, boolean estado, int ram, String procesador, int tamDisco, Disco disco) {
        super(marca, modelo, estado);
        this.ram = ram;
        this.procesador = procesador;
        this.tamDisco = tamDisco;
        this.tipoDisco = disco;

        try {
            randomAccessFile.seek(randomAccessFile.length() - nBytesT);
            id = randomAccessFile.readInt() + 1;
        } catch (IOException e) {
            id = 0;
        }

    }

    /**
     * Inicializa el flujo de datos
     */
    public static void init() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("No hemos podido encontrar el archivo especificado.");
            }
        }

        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            System.err.println("No hemos podido encontrar el archivo especificado.");
        }
    }

    /**
     * Constructor por ID, usado para cargar datos existentes
     * @param id El ID del dispositivo
     */
    public Ordenador(int id) {
        super(id);
    }

    /**
     * Metodo que devuelve la RAM del ordenador
     * @return La RAM del ordenador
     */
    public int getRam() {
        return ram;
    }

    /**
     * Metodo que establece la RAM del ordenador
     * @param ram LA RAM del ordenador
     */
    public void setRam(int ram) {
        this.ram = ram;
    }

    /**
     * Devuelve el procesador del ordenador
     * @return El procesador del ordenador
     */
    public String getProcesador() {
        return procesador;
    }

    /**
     * Establece el procesador del ordenador
     * @param procesador El procesador del ordenador
     */
    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    /**
     * Devuelve el tamaño del disco (En GB)
     * @return El tamaño del disco.
     */
    public int getTamDisco() {
        return tamDisco;
    }

    /**
     * Establece el tamaño del disco (En GB)
     * @param tamDisco El tamaño del disco
     */
    public void setTamDisco(int tamDisco) {
        this.tamDisco = tamDisco;
    }

    /**
     * Devuelve el tipo del disco
     * @return El tipo del disco
     */
    public Disco getTipoDisco() {
        return tipoDisco;
    }

    /**
     * Establece el tipo del disco
     * @param tipoDisco el tipo del disco
     */
    public void setTipoDisco(Disco tipoDisco) {
        this.tipoDisco = tipoDisco;
    }

    /**
     * Guarda los cambios
     */
    public void save() {
        try {
            randomAccessFile.seek(nBytesT * id);
        } catch (IOException e) {
            System.out.println("El registro no existe, creando...");

        }
        try {
            // Comenzamos a guardar la info de vuelta al archivo
            long posIniF = randomAccessFile.getFilePointer();

            randomAccessFile.writeInt(id);

            long posIni = randomAccessFile.getFilePointer();
            randomAccessFile.writeUTF(getMarca());
            long posFin = randomAccessFile.getFilePointer();
            long bytesEscritos = posFin - posIni;
            for (int j = 0; j < 50-bytesEscritos; j++) {
                randomAccessFile.writeByte(0);
            }

            posIni = randomAccessFile.getFilePointer();
            randomAccessFile.writeUTF(getModelo());
            posFin = randomAccessFile.getFilePointer();
            bytesEscritos = posFin - posIni;
            for (int i = 0; i < 50-bytesEscritos; i++) {
                randomAccessFile.writeByte(0);
            }

            randomAccessFile.writeBoolean(getEstado());

            randomAccessFile.writeBoolean(false);

            randomAccessFile.writeInt(ram);

            posIni = randomAccessFile.getFilePointer();
            randomAccessFile.writeUTF(procesador);
            posFin = randomAccessFile.getFilePointer();
            bytesEscritos = posFin - posIni;
            for (int i = 0; i < 50-bytesEscritos; i++) {
                randomAccessFile.writeByte(0);
            }

            randomAccessFile.writeInt(tamDisco);

            posIni = randomAccessFile.getFilePointer();
            randomAccessFile.writeUTF(tipoDisco.name());
            posFin = randomAccessFile.getFilePointer();
            bytesEscritos = posFin - posIni;
            for (int i = 0; i < 10-bytesEscritos; i++) {
                randomAccessFile.writeByte(0);
            }

            long posFinal = randomAccessFile.getFilePointer();
            // Finalizamos el proceso
        } catch (IOException ex) {
            System.err.println("No hemos podido escribir la info.");
            System.err.println("  Es posible que el archivo este corrupto");
            System.err.println("  Contacte con el desarrollador de este software si regenerar el archivo no ayuda");
        }

    }

    public void delete() throws IOException {
        randomAccessFile.seek(id*nBytesT+115);
        randomAccessFile.writeBoolean(true);
    }

    /**
     * Carga los cambios
     * @throws ElementNotFoundException Si el elemento no existe
     */
    public void load() throws ElementNotFoundException {
        try {
            randomAccessFile.seek(id * nBytesT);

            id = randomAccessFile.readInt();

            long pos = randomAccessFile.getFilePointer();
            setMarca(randomAccessFile.readUTF());
            randomAccessFile.seek(pos + 50);

            pos = randomAccessFile.getFilePointer();
            setModelo(randomAccessFile.readUTF());
            randomAccessFile.seek(pos + 50);

            setEstado(randomAccessFile.readBoolean());

            boolean borrado = randomAccessFile.readBoolean();
            if (borrado) {
                throw new ElementNotFoundException("Element not found", true);
            }

            ram = randomAccessFile.readInt();

            pos = randomAccessFile.getFilePointer();
            procesador = randomAccessFile.readUTF();
            randomAccessFile.seek(pos + 50);

            tamDisco = randomAccessFile.readInt();
            pos = randomAccessFile.getFilePointer();
            tipoDisco = Disco.valueOf(randomAccessFile.readUTF());
            randomAccessFile.seek(pos + 10);
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error cargando la información");
            System.err.println("  Es posible que el archivo esté corrupto.");
            System.err.println("  Contacte con el desarrollador de este software si regenerar el archivo no ayuda");
        }


    }

    /**
     * Te devuelve un string bonito del objeto
     * @return El String bonito del objeto.
     */
    public String toString() {
        String estado1;
        if (getEstado()) {
            estado1 = "Funciona";
        } else {
            estado1 = "No funciona";
        }
        return "Marca: " + getMarca() + ", Modelo: " + getModelo() + ", Estado: " + getEstado() + ", RAM: " + ram + ", Procesador: " + procesador + ", Tamaño Disco: " + tamDisco + "Tipo Disco: " + tipoDisco.name();
    }

    /**
     * Devuelve la cantidad de ordenadores existentes (Incluyendo los "borrados")
     * @return
     */
    public static int length() {
        try {
            randomAccessFile.seek(randomAccessFile.length() - nBytesT);
            return randomAccessFile.readInt() + 1;
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error");
            System.err.println("  Unable to get ID");
            System.err.println("  Contacte con el desarrollador de este software si regenerar el archivo no ayuda");

            return 0;
        }
    }

    /**
     * Borra el archivo donde se almacenan los ordenadores
     */
    public static void cleanup() {
        file.delete();
    }
}
