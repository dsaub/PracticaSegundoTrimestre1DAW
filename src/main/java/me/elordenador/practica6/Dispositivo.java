package me.elordenador.practica6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;

public class Dispositivo {
    private static File file = new File("dispositivos.dat");
    private static RandomAccessFile randomAccessFile;
    private int id;
    private String marca, modelo;
    private boolean estado;

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

    public Dispositivo(String marca, String modelo, boolean estado) {

        try {
            randomAccessFile.seek(0);
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error");
        }
        int posContador = 0;
        boolean salida = false;
        while (!salida) {
            try {
                randomAccessFile.seek(posContador * 108);
            } catch (IOException e) {
                System.err.println("Ha ocurrido un error");
                salida = true;
            }

            try {
                id = randomAccessFile.readInt() + 1;
            } catch (IOException e) {
                salida = true;
            }
            posContador++;

        }

        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;


    }

    public Dispositivo(int id) {
        this.id = id;
        this.marca = "";
        this.modelo = "";
        this.estado = true;


    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

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

    public String toString() {
        String estado1;
        if (estado) {
            estado1 = "Sí";
        } else {
            estado1 = "No";
        }
        return "{id=" + id + ", marca=\"" + marca + "\", modelo=\"" + modelo + "\", estado=" + estado1 + "}";
    }

}
