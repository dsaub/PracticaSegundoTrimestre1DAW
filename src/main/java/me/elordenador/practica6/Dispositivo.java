package me.elordenador.practica6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;

/**
 * Clase para manejar dispositivos
 */
public class Dispositivo {
    private static File file = new File("dispositivos.dat");
    private static RandomAccessFile randomAccessFile;
    protected int id;
    private String marca, modelo;
    private boolean estado;

    private static int nBytesT = 106;

    /**
     * Función usada para inicializar la conexión con el archivo, es necesario ejecutar esta antes de usar esta clase.
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
     * Constructor para crear un dispositivo
     * @param marca La marca del dispositivo
     * @param modelo El modelo del dispositivo
     * @param estado Un boolean de que si funciona o no el dispositivo
     */
    public Dispositivo(String marca, String modelo, boolean estado) {

        try {
            randomAccessFile.seek(0);
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error");
        }

        try {
            randomAccessFile.seek(randomAccessFile.length() - nBytesT);
            id = randomAccessFile.readInt() + 1;
        } catch (IOException e) {
            id = 0;
        }

        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;


    }

    /**
     * Constructor para buscar un dispositivo por ID
     * @param id El ID del dispositivo
     */
    public Dispositivo(int id) {
        this.id = id;
        this.marca = "";
        this.modelo = "";
        this.estado = true;


    }

    /**
     * Elimina el archivo donde se almacenan los dispositivos
     */
    public static void cleanup() {
        file.delete();
    }

    /**
     * Devuelve el ID del dispositivo
     * @return El ID del dispositivo
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve la marca del dispositivo
     * @return La Marca del dispositivo
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Devuelve el modelo del dispositivo
     * @return El Modelo del dispositivo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Devuelve el estado del dispositivo
     * @return SI funciona o no.
     */
    public boolean getEstado() {
        return estado;
    }

    /**
     * Establece la marca del dispositivo
     * @param marca la marca del dispositivo
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Establece el modelo del dispositivo
     * @param modelo El Modelo del dispositivo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /** Establece el estado del dispositivo
     * @param estado El Estado del dispositivo
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * Guarda el objeto en el archivo.
     */
    public void save() {
        try {
            randomAccessFile.seek(108 * id);
        } catch (IOException e) {
            System.out.println("El registro no existe, creando...");

        }
        try {
            randomAccessFile.writeInt(id);

            long posIni = randomAccessFile.getFilePointer();
            randomAccessFile.writeUTF(marca);
            long posFin = randomAccessFile.getFilePointer();
            long bytesEscritos = posFin - posIni;
            for (int j = 0; j < 50-bytesEscritos; j++) {
                randomAccessFile.writeByte(0);
            }

            posIni = randomAccessFile.getFilePointer();
            randomAccessFile.writeUTF(modelo);
            posFin = randomAccessFile.getFilePointer();
            bytesEscritos = posFin - posIni;
            for (int i = 0; i < 50-bytesEscritos; i++) {
                randomAccessFile.writeByte(0);
            }

            randomAccessFile.writeBoolean(estado);

            randomAccessFile.writeBoolean(false);
        } catch (IOException ex) {
            System.err.print("NO hemos podido escribir la info");
        }

    }

    /**
     * Carga el objeto del dispositivo desde el archivo.
     * @throws ElementNotFoundException En caso de que no se encuentre el dispositivo
     */
    public void load() throws ElementNotFoundException {
        // TODO: Implementar función load.
        try {
            randomAccessFile.seek(108 * id);
            if (randomAccessFile.getFilePointer() >= randomAccessFile.length()) {
                throw new ElementNotFoundException("Device not found", false);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo");
        }

        try {
            id = randomAccessFile.readInt();
            long pos = randomAccessFile.getFilePointer();
            marca = randomAccessFile.readUTF();
            randomAccessFile.seek(pos + 50);
            pos = randomAccessFile.getFilePointer();
            modelo = randomAccessFile.readUTF();
            randomAccessFile.seek(pos + 50);

            estado = randomAccessFile.readBoolean();
            boolean borrado = randomAccessFile.readBoolean();
            if (borrado) {
                throw new ElementNotFoundException("Device not found", true);
            }
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error.");
        }

    }

    /**
     * Elimina el dispositivo
     */
    public void delete() {
        // TODO: Implementar función delete.
        try {
            randomAccessFile.seek(id*108);

            randomAccessFile.writeInt(id);

            long posIni = randomAccessFile.getFilePointer();
            randomAccessFile.writeUTF(marca);
            long posFin = randomAccessFile.getFilePointer();
            long bytesEscritos = posFin - posIni;
            for (int j = 0; j < 50-bytesEscritos; j++) {
                randomAccessFile.writeByte(0);
            }

            posIni = randomAccessFile.getFilePointer();
            randomAccessFile.writeUTF(modelo);
            posFin = randomAccessFile.getFilePointer();
            bytesEscritos = posFin - posIni;
            for (int i = 0; i < 50-bytesEscritos; i++) {
                randomAccessFile.writeByte(0);
            }

            randomAccessFile.writeBoolean(estado);

            randomAccessFile.writeBoolean(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Muestra un String bonito del dispositivo
     * @return
     */
    public String toString() {
        String estado1;
        if (estado) {
            estado1 = "Funciona";
        } else {
            estado1 = "No funciona";
        }
        return "Marca: " + marca + ", Modelo: " + modelo + ", Estado: " + estado;
    }

    /**
     * Devuelve la cantidad de elementos existentes
     * @return La cantidad de elementos existentes
     */
    public static int length() {
        try {
            randomAccessFile.seek(randomAccessFile.length() - nBytesT);
            return randomAccessFile.readInt() + 1;
        } catch (IOException e) {
            System.err.println("Unable to get ID");

            return 0;
        }
    }


}
