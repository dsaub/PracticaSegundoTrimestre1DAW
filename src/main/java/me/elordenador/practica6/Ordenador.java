package me.elordenador.practica6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Ordenador extends Dispositivo {
    private int id;
    private int ram;
    private String procesador;
    private int tamDisco;
    private Disco tipoDisco;
    private static File file = new File("ordenadores.dat");
    private static RandomAccessFile randomAccessFile;

    private int nBytesT = 174;
    public Ordenador(String marca, String modelo, boolean estado, int ram, String procesador, int tamDisco, Disco disco) {
        super(marca, modelo, estado);
        this.ram = ram;
        this.procesador = procesador;
        this.tamDisco = tamDisco;
        this.tipoDisco = disco;

    }
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

    public Ordenador(int id) {
        super(id);

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
        
        this.id = id;
    }

    public int getRam() {
        return ram;
    }

    public String getProcesador() {
        return procesador;
    }

    public int getTamDisco() {
        return tamDisco;
    }

    public Disco getTipoDisco() {
        return tipoDisco;
    }

    public void save() {
        try {
            randomAccessFile.seek(nBytesT * id);
        } catch (IOException e) {
            System.out.println("El registro no existe, creando...");

        }
        try {
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
            for (int i = 0; i < 50-bytesEscritos; i++) {
                randomAccessFile.writeByte(0);
            }
        } catch (IOException ex) {
            System.err.print("NO hemos podido escribir la info");
        }

    }
}
