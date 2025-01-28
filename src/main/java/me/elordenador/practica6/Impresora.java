package me.elordenador.practica6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Impresora extends Dispositivo {
    private static  RandomAccessFile randomAccessFile;

    public static void init() {
        File file = new File("impresoras.dat");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al cargar el fichero");
            }
        }
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            System.err.println("Error al crear el flujo");
        }
    }

    private TipoImpresora tipo;
    private boolean color, escaner;
    private int nBytesT = 118;
    public Impresora(String marca, String modelo, boolean estado, TipoImpresora tipo, boolean color, boolean escaner) {
        super(marca, modelo, estado);
        this.tipo = tipo;
        this.color = color;
        this.escaner = escaner;

        try {
            randomAccessFile.seek(randomAccessFile.length() - nBytesT);
            id = randomAccessFile.readInt() + 1;
        } catch (IOException e) {
            id = 0;
        }
    }

    public Impresora(int id) {
        super(id);
    }

    public TipoImpresora getTipo() {
        return tipo;
    }

    public boolean getColor() {
        return color;
    }

    public boolean getEscaner() {
        return escaner;
    }

    public void setTipo(TipoImpresora tipo) {
        this.tipo = tipo;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public void setEscaner(boolean escaner) {
        this.escaner = escaner;
    }

    public void save() {
        long posIni, posFin, bytesEscritos;
        try {
            randomAccessFile.seek(nBytesT * id);

            // GUARDAR: ID
            randomAccessFile.writeInt(id);

            // GUARDAR: MARCA
            posIni = randomAccessFile.getFilePointer();
            randomAccessFile.writeUTF(getMarca());
            posFin = randomAccessFile.getFilePointer();
            bytesEscritos = posFin - posIni;
            for (int j = 0; j < 50-bytesEscritos; j++) {
                randomAccessFile.writeByte(0);
            }

            // GUARDAR: MODELO
            posIni = randomAccessFile.getFilePointer();
            randomAccessFile.writeUTF(getModelo());
            posFin = randomAccessFile.getFilePointer();
            bytesEscritos = posFin - posIni;
            for (int j = 0; j < 50-bytesEscritos; j++) {
                randomAccessFile.writeByte(0);
            }

            // GUARDAR: ESTADO
            randomAccessFile.writeBoolean(getEstado());

            // GUARDAR: BORRADO
            randomAccessFile.writeBoolean(false);

            // GUARDAR: TIPO
            posIni = randomAccessFile.getFilePointer();
            randomAccessFile.writeUTF(tipo.name());
            posFin = randomAccessFile.getFilePointer();
            bytesEscritos = posFin - posIni;
            for (int j = 0; j < 10-bytesEscritos; j++) {
                randomAccessFile.writeByte(0);
            }

            // GUARDAR: COLOR
            randomAccessFile.writeBoolean(color);

            // GUARDAR: ESCANER
            randomAccessFile.writeBoolean(escaner);


        } catch (IOException e) {
            System.err.println("Ha habido un error guardando el archivo.");
        }
    }


}
