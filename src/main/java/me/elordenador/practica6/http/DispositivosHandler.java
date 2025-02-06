package me.elordenador.practica6.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.elordenador.practica6.Dispositivo;
import me.elordenador.practica6.ElementNotFoundException;
import me.elordenador.practica6.Ordenador;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class DispositivosHandler implements HttpHandler {
    private String response;
    public void handle(HttpExchange ex) throws IOException {
        String path = ex.getHttpContext().getPath();

        System.out.println("HTTP Request: " + ex.getLocalAddress().getHostName() + " " + ex.getHttpContext().getPath());
        if (path.equals("/api/v1/getDispositivos")) {
            ArrayList<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
            for (int i = 0; i < Dispositivo.length(); i++) {
                Dispositivo dispositivo = new Dispositivo(i);
                try {
                    dispositivo.load();
                } catch (ElementNotFoundException e) {
                    if (!e.logicaldelete) {
                        System.err.println("File is corrupt");
                        String response = "{\"error\":\"File is corrupt\"}";
                        ex.sendResponseHeaders(500, response.length());
                        OutputStream out = ex.getResponseBody();
                        out.write(response.getBytes());
                        out.close();
                        return;
                    }
                }

                dispositivos.add(dispositivo);

            }
            Gson gson = new Gson();
            TypeToken<ArrayList<Dispositivo>> listOfMyClassObject = new TypeToken<ArrayList<Dispositivo>>() {};
            String json = gson.toJson(dispositivos, listOfMyClassObject.getType());
            System.out.println(json);

            ex.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            ex.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            ex.sendResponseHeaders(200, json.length());
            OutputStream out = ex.getResponseBody();
            out.write(json.getBytes());
            out.close();
        } else if (path.equals("/api/v1/getOrdenadores")) {
            ArrayList<Ordenador> ordenadores = new ArrayList<Ordenador>();
            for (int i = 0; i < Ordenador.length(); i++) {
                Ordenador ordenador = new Ordenador(i);
                try {
                    ordenador.load();
                } catch (ElementNotFoundException e) {
                    if (!e.logicaldelete) {
                        System.err.println("File is corrupt");
                        String response = "{\"error\":\"File is corrupt\"}";
                        ex.sendResponseHeaders(500, response.length());
                        OutputStream out = ex.getResponseBody();
                        out.write(response.getBytes());
                        out.close();
                        return;
                    }
                }

                ordenadores.add(ordenador);
                Gson gson = new Gson();
                TypeToken<ArrayList<Ordenador>> type = new TypeToken<ArrayList<Ordenador>>() {};
                String json = gson.toJson(ordenadores, type.getType());
                ex.sendResponseHeaders(200,json.length());
                OutputStream out = ex.getResponseBody();
                out.write(json.getBytes());
                out.close();
            }
        } else {
            ex.sendResponseHeaders(200, path.length());
            OutputStream out = ex.getResponseBody();
            out.write(path.getBytes());
            out.close();
        }

    }

    public void generateHTMLRoot() {

    }
}
