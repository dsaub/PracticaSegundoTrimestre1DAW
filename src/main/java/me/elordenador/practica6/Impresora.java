package me.elordenador.practica6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase para manejar las impresoras
 * @author Daniel Sánchez Úbeda
 * @version 1.0
 */
public class Impresora extends Dispositivo {
    private static  RandomAccessFile randomAccessFile;

    /**
     * Inicializa el archivo
     */
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
    private static int nBytesT = 118;

    /**
     * Constructor para crear un objeto con información
     * @param marca La marca de la impresora
     * @param modelo El modelo de la impresora
     * @param estado El estado de la impresora
     * @param tipo El tipo de la impresora
     * @param color Es de color?
     * @param escaner Tiene escaner?
     */
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

    /**
     * Constructor para crear un objeto en base a información ya existente (usando un ID)
     * @param id El ID de la impresora
     */
    public Impresora(int id) {
        super(id);
    }

    /**
     * Devuelve el tipo de la impresora
     * @return EL tipo de la impresora
     */
    public TipoImpresora getTipo() {
        return tipo;
    }

    /**
     * Devuelve si la impresora es de color o no
     * @return Si la impresora es de color o no
     */
    public boolean getColor() {
        return color;
    }

    /**
     * Devuelve si la impresora posee escaner o no
     * @return Si la impresora posee escaner o no
     */
    public boolean getEscaner() {
        return escaner;
    }

    /**
     * Establece el tipo de la impresora
     * @param tipo El tipo de la impresora
     */
    public void setTipo(TipoImpresora tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece si la impresora es a color o no
     * @param color Si la impresora es a color o no
     */
    public void setColor(boolean color) {
        this.color = color;
    }

    /**
     * Establece si la impresora posee escaner
     * @param escaner Si posee escaner o no
     */
    public void setEscaner(boolean escaner) {
        this.escaner = escaner;
    }

    /**
     * Guarda el elemento en el archivo
     */
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

    /**
     * Carga el elemento del archivo.
     * @throws ElementNotFoundException
     */
    public void load() throws ElementNotFoundException {
        long pos;
        try {
            randomAccessFile.seek(nBytesT * id);
            id = randomAccessFile.readInt();

            pos = randomAccessFile.getFilePointer();
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

            pos = randomAccessFile.getFilePointer();
            tipo = TipoImpresora.valueOf(randomAccessFile.readUTF());
            randomAccessFile.seek(pos + 10);

            color = randomAccessFile.readBoolean();

            escaner = randomAccessFile.readBoolean();


        } catch (IOException e) {
            System.err.println("Hubo un error al leer el archivo");
        }


    }

    /**
     * Devuelve un String bonito
     * @return El String bonito
     */
    public String toString() {
        String estado1;
        if (getEstado()) {
            estado1 = "Funciona";
        } else {
            estado1 = "No funciona";
        }
        return "Marca: " + getMarca() + ", Modelo: " + getModelo() + ", Estado: " + estado1 + ", Tipo: " + tipo.name() + ", Color: " + color + ", Scanner: " + escaner;
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
