package me.elordenador.practica6.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.elordenador.practica6.Dispositivo;
import me.elordenador.practica6.ElementNotFoundException;

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
                    }
                }

                dispositivos.add(dispositivo);

            }
            Gson gson = new Gson();
            String json = gson.toJson(dispositivos);
            System.out.println(json);
            ex.sendResponseHeaders(200, json.length());
            OutputStream out = ex.getResponseBody();
            out.write(json.getBytes());
            out.close();
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
