package me.elordenador.practica6;

public class Ordenador extends Dispositivo {
    private int ram;
    private String procesador;
    private int tamDisco;
    private Disco tipoDisco;
    public Ordenador(String marca, String modelo, boolean estado, int ram, String procesador, int tamDisco, Disco disco) {
        super(marca, modelo, estado);
        this.ram = ram;
        this.procesador = procesador;
        this.tamDisco = tamDisco;
        this.tipoDisco = disco;

    }

    public Ordenador(int id) {
        super(id);
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
}
