package me.elordenador.practica6;

public class ElementNotFoundException extends Exception {
    public String error;
    public final Boolean logicaldelete;
    public ElementNotFoundException(String deviceNotFound, boolean logicaldelete) {
        error = deviceNotFound;
        this.logicaldelete = logicaldelete;
    }
}
