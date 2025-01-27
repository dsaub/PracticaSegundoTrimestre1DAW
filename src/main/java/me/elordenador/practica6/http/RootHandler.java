package me.elordenador.practica6.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RootHandler implements HttpHandler {
    private String response = "";
    public void handle(HttpExchange he) throws IOException {
        response = "";
        buildResponse();
        he.sendResponseHeaders(200, response.length());


        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void buildResponse() {
        response += "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Inicio</title>\n" +
                "<meta charset=\"utf-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Inicio</h1>\n" +
                "<ul>\n" +
                "<li><a href=\"/dispositivos\">Dispositivos</a>\n" +
                "</body>\n" +
                "</html>";
    }
}
